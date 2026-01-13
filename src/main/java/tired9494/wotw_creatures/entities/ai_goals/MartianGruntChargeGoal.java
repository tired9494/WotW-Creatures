package tired9494.wotw_creatures.entities.ai_goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import tired9494.wotw_creatures.entities.MartianGrunt;

import java.util.EnumSet;

public class MartianGruntChargeGoal extends Goal {
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private final int attackInterval = 20;
    private long lastCanUseCheck;
    private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;
    private final MartianGrunt martianGrunt;
    private long lastChargeTick;

    public MartianGruntChargeGoal(MartianGrunt martianGrunt, double speedModifier) {
        this.martianGrunt = martianGrunt;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = true;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public void tick() {
        LivingEntity livingentity = this.martianGrunt.getTarget();
        if (livingentity != null) {
            this.martianGrunt.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            double d0 = this.martianGrunt.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if ((this.followingTargetEvenIfNotSeen || this.martianGrunt.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.martianGrunt.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.martianGrunt.getRandom().nextInt(7);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                    if (this.martianGrunt.getNavigation().getPath() != null) {
                        net.minecraft.world.level.pathfinder.Node finalPathPoint = this.martianGrunt.getNavigation().getPath().getEndNode();
                        if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                            failedPathFindingPenalty = 0;
                        else
                            failedPathFindingPenalty += 10;
                    } else {
                        failedPathFindingPenalty += 10;
                    }
                }
                if (d0 > 1024.0D) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (d0 > 256.0D) {
                    this.ticksUntilNextPathRecalculation += 5;
                }

                if (!this.martianGrunt.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }

            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAttack(livingentity, d0);
        }
    }

    public boolean canUse() {
        long gameTime = this.martianGrunt.level().getGameTime();
        if (gameTime - this.lastChargeTick < 200L) {
            return false;
        } else {
            LivingEntity livingentity = this.martianGrunt.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                this.path = this.martianGrunt.getNavigation().createPath(livingentity, 0);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackReachSqr(livingentity) >= this.martianGrunt.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                }
            }
        }
    }

    protected void checkAndPerformAttack(LivingEntity martianGrunt, double distance) {
        double d0 = this.getAttackReachSqr(martianGrunt);
        if (distance <= d0) {
            this.resetAttackCooldown();
            this.martianGrunt.swing(InteractionHand.MAIN_HAND);
            this.martianGrunt.doHurtTarget(martianGrunt);
            this.lastChargeTick = this.martianGrunt.level().getGameTime();
            this.stop();
        }

    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.martianGrunt.getTarget();
        long gameTime = this.martianGrunt.level().getGameTime();
        if (livingentity == null) {
            return false;
        } else if (gameTime - this.lastChargeTick < 200L) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (!this.martianGrunt.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }
    }

    public void stop() {
        LivingEntity livingentity = this.martianGrunt.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.martianGrunt.setTarget((LivingEntity)null);
        }

        this.martianGrunt.setAggressive(false);
        this.martianGrunt.getNavigation().stop();
    }

    public void start() {
        this.martianGrunt.getNavigation().moveTo(this.path, this.speedModifier);
        this.martianGrunt.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 500;
        this.ticksUntilNextAttack = 0;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(20);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected int getAttackInterval() {
        return this.adjustedTickDelay(20);
    }

    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return (double)(this.martianGrunt.getBbWidth() * 2.0F * this.martianGrunt.getBbWidth() * 2.0F + pAttackTarget.getBbWidth());
    }
}
