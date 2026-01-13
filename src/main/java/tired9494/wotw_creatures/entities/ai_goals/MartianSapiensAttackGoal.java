package tired9494.wotw_creatures.entities.ai_goals;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import tired9494.wotw_creatures.entities.MartianSapiens;

public class MartianSapiensAttackGoal extends MeleeAttackGoal {
    private final MartianSapiens martianSapiens;
    private int raiseArmTicks;

    public MartianSapiensAttackGoal(MartianSapiens martianSapiens, double speedModifier) {
        super(martianSapiens, speedModifier, true);
        this.martianSapiens = martianSapiens;
    }

    public void start() {
        super.start();
        this.raiseArmTicks = 0;
    }

    public void stop() {
        super.stop();
        this.martianSapiens.setAggressive(false);
    }

    public void tick() {
        super.tick();
        ++this.raiseArmTicks;
        if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
            this.martianSapiens.setAggressive(true);
        } else {
            this.martianSapiens.setAggressive(false);
        }

    }


}
