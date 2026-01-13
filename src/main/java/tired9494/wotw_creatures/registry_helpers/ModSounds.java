package tired9494.wotw_creatures.registry_helpers;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tired9494.wotw_creatures.WotwCreatures;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WotwCreatures.MODID);

    public static final RegistryObject<SoundEvent> MARTIAN_GRUNT_IDLE = SOUND_EVENTS.register("martian_grunt_idle",
            () -> SoundEvent.createVariableRangeEvent(WotwCreatures.ID("martian_grunt_idle")));

    public static final RegistryObject<SoundEvent> MARTIAN_GRUNT_HURT = SOUND_EVENTS.register("martian_grunt_hurt",
            () -> SoundEvent.createVariableRangeEvent(WotwCreatures.ID("martian_grunt_hurt")));

    public static final RegistryObject<SoundEvent> MARTIAN_GRUNT_DEATH = SOUND_EVENTS.register("martian_grunt_death",
            () -> SoundEvent.createVariableRangeEvent(WotwCreatures.ID("martian_grunt_death")));
}
