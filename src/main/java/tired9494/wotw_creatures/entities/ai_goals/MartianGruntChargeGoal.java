package tired9494.wotw_creatures.entities.ai_goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import tired9494.wotw_creatures.entities.MartianGrunt;

public class MartianGruntChargeGoal extends MeleeAttackGoal {
    private final MartianGrunt martianGrunt;
    private int lastChargeTicks;
    private Path path;
    public MartianGruntChargeGoal(MartianGrunt martianGrunt, double speedModifier) {
        super(martianGrunt, speedModifier, false);
        this.martianGrunt = martianGrunt;
    }

    public void start() {
        super.start();
        this.lastChargeTicks = 0;
    }

    public void stop() {
        super.stop();
        this.martianGrunt.setAggressive(false);
    }

    public void tick() {
        super.tick();
        ++this.lastChargeTicks;
    }

    public boolean canUse() {
        long gameTime = this.mob.level().getGameTime();
        if (gameTime - this.lastChargeTicks < 200L) {
            return false;
        } else {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                this.path = this.mob.getNavigation().createPath(livingentity, 0);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                }
            }
        }
    }

    protected void checkAndPerformAttack(LivingEntity martianGrunt, double distance) {
        double d0 = this.getAttackReachSqr(martianGrunt);
        if (distance <= d0) {
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(martianGrunt);
            this.lastChargeTicks = 0;
            this.martianGrunt.setAggressive(false);
        }

    }
}
