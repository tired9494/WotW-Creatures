package tired9494.wotw_creatures.entities.ai_goals;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import tired9494.wotw_creatures.entities.MartianGrunt;

public class MartianGruntAttackGoal extends MeleeAttackGoal {
    private final MartianGrunt martianGrunt;
    private int raiseArmTicks;

    public MartianGruntAttackGoal(MartianGrunt martianGrunt, double speedModifier) {
        super(martianGrunt, speedModifier, true);
        this.martianGrunt = martianGrunt;
    }

    public void start() {
        super.start();
        this.raiseArmTicks = 0;
    }

    public void stop() {
        super.stop();
        this.martianGrunt.setAggressive(false);
    }

    public void tick() {
        super.tick();
        ++this.raiseArmTicks;
        if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
            this.martianGrunt.setAggressive(true);
        } else {
            this.martianGrunt.setAggressive(false);
        }

    }


}
