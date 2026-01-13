package tired9494.wotw_creatures.entities.ai_goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import tired9494.wotw_creatures.entities.MartianSapiens;

import java.util.EnumSet;

public class MartianSapiensChargeGoal extends Goal {
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
    private final MartianSapiens martianSapiens;
    private long lastChargeTick;
    private long chargingTicks;

    public MartianSapiensChargeGoal(MartianSapiens martianSapiens, double speedModifier) {
        this.martianSapiens = martianSapiens;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = true;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public void tick() {
        this.chargingTicks++;
        if (this.chargingTicks >= 80) {
            this.stop();
        }
        if (this.chargingTicks >= 40) {
            this.martianSapiens.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.speedModifier);
        }
    }

    public boolean canUse() {
        long gameTime = this.martianSapiens.level().getGameTime();
        if (gameTime - this.lastChargeTick < 200L) {
            return false;
        } else {
            LivingEntity livingentity = this.martianSapiens.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                this.path = this.martianSapiens.getNavigation().createPath(livingentity, 0);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackReachSqr(livingentity) >= this.martianSapiens.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                }
            }
        }
    }

    protected void checkAndPerformAttack(LivingEntity martianGrunt, double distance) {
        double d0 = this.getAttackReachSqr(martianGrunt);
        if (distance <= d0) {
            this.resetAttackCooldown();
            this.martianSapiens.swing(InteractionHand.MAIN_HAND);
            this.martianSapiens.doHurtTarget(martianGrunt);
            this.lastChargeTick = this.martianSapiens.level().getGameTime();
            this.stop();
        }

    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.martianSapiens.getTarget();
        long gameTime = this.martianSapiens.level().getGameTime();
        if (livingentity == null) {
            return false;
        } else if (gameTime - this.lastChargeTick < 200L) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (!this.martianSapiens.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }
    }

    public void stop() {
        LivingEntity livingentity = this.martianSapiens.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.martianSapiens.setTarget((LivingEntity)null);
        }

        this.martianSapiens.setSprinting(false);
        this.chargingTicks = 0;
        this.martianSapiens.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.23F);
        this.martianSapiens.setAggressive(false);
        this.martianSapiens.getNavigation().stop();
    }

    public void start() {
        this.martianSapiens.getNavigation().moveTo(this.path, this.speedModifier);
        this.martianSapiens.setAggressive(true);
        this.ticksUntilNextAttack = 0;
        this.martianSapiens.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0F);
        this.martianSapiens.setSprinting(true);
        LivingEntity livingentity = this.martianSapiens.getTarget();
        this.martianSapiens.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
        this.martianSapiens.playChargeSound();
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
        return (double)(this.martianSapiens.getBbWidth() * 2.0F * this.martianSapiens.getBbWidth() * 2.0F + pAttackTarget.getBbWidth());
    }
}
