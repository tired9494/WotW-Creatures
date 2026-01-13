package tired9494.wotw_creatures.entities.ai_goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import tired9494.wotw_creatures.WotwCreatures;
import tired9494.wotw_creatures.entities.MartianSapiens;

import java.util.EnumSet;
import java.util.UUID;

public class MartianSapiensChargeGoal extends Goal {
    private static final UUID SPEED_MODIFIER_WINDUP_UUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier SPEED_MODIFIER_WINDUP = new AttributeModifier(SPEED_MODIFIER_WINDUP_UUID, "Charge attack windup speed freeze", 0.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static final UUID SPEED_MODIFIER_CHARGE_UUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier SPEED_MODIFIER_CHARGE = new AttributeModifier(SPEED_MODIFIER_CHARGE_UUID, "Charge attack speed boost", 1.5D, AttributeModifier.Operation.MULTIPLY_BASE);

    private final double speedModifier;
    private Path path;
    private int ticksUntilNextAttack;
    private final MartianSapiens martianSapiens;
    private long lastChargeTick;
    private long chargingTicks;

    public MartianSapiensChargeGoal(MartianSapiens martianSapiens, double speedModifier) {
        this.martianSapiens = martianSapiens;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public void tick() {
        this.chargingTicks++;
        WotwCreatures.LOGGER.debug("Ticking MartianSapiensCharge Goal {}",  this.chargingTicks);
        if (this.chargingTicks >= 80) {
            WotwCreatures.LOGGER.debug("Stopping charge at {} ticks",  this.chargingTicks);
            this.stop();
        }
        else if (this.chargingTicks >= 40) {
            WotwCreatures.LOGGER.debug("Finished windup at {} ticks",  this.chargingTicks);
            this.martianSapiens.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_WINDUP);
            this.martianSapiens.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(SPEED_MODIFIER_CHARGE);
        }
    }

    public boolean canUse() {
        long gameTime = this.martianSapiens.level().getGameTime();
        if (gameTime - this.lastChargeTick < 500L) {
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

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.martianSapiens.getTarget();
        if (livingentity == null) {
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
        this.martianSapiens.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_WINDUP);
        this.martianSapiens.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_CHARGE);
        this.martianSapiens.setAggressive(false);
        this.martianSapiens.getNavigation().stop();
    }

    public void start() {
        this.martianSapiens.getNavigation().moveTo(this.path, this.speedModifier);
        this.martianSapiens.setAggressive(true);
        this.ticksUntilNextAttack = 0;
        this.martianSapiens.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(SPEED_MODIFIER_WINDUP);
        this.martianSapiens.setSprinting(true);
        LivingEntity livingentity = this.martianSapiens.getTarget();
        this.martianSapiens.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
        this.martianSapiens.playChargeSound();
        this.lastChargeTick = this.martianSapiens.level().getGameTime();
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
