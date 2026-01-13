package tired9494.wotw_creatures.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class MartianGrunt extends Zombie {
    public MartianGrunt(EntityType<? extends MartianGrunt> entityType, Level level) {
        super(entityType, level);
    }

    public static boolean canSpawn(EntityType<MartianGrunt> entityType, LevelAccessor level, MobSpawnType type, BlockPos pos, RandomSource random) {
        return Zombie.checkMobSpawnRules(entityType, level, type, pos, random);
    }
}
