package tired9494.wotw_creatures.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import tired9494.wotw_creatures.entities.ai_goals.MartianGruntAttackGoal;
import tired9494.wotw_creatures.entities.ai_goals.MartianGruntChargeGoal;

public class MartianGrunt extends Monster {
    public MartianGrunt(EntityType<? extends MartianGrunt> entityType, Level level) {
        super(entityType, level);
    }

    public static boolean canSpawn(EntityType<MartianGrunt> entityType, LevelAccessor level, MobSpawnType type, BlockPos pos, RandomSource random) {
        return Zombie.checkMobSpawnRules(entityType, level, type, pos, random);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(2, new MartianGruntChargeGoal(this, 2.0F));
        this.goalSelector.addGoal(3, new MartianGruntAttackGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(MartianGrunt.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
}
