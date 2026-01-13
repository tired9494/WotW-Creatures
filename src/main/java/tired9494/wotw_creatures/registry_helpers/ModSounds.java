package tired9494.wotw_creatures.registry_helpers;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tired9494.wotw_creatures.WotwCreatures;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WotwCreatures.MODID);

    public static final RegistryObject<SoundEvent> MARTIAN_SAPIENS_IDLE = SOUND_EVENTS.register("martian_sapiens_idle",
            () -> SoundEvent.createVariableRangeEvent(WotwCreatures.ID("martian_sapiens_idle")));

    public static final RegistryObject<SoundEvent> MARTIAN_SAPIENS_CHARGE = SOUND_EVENTS.register("martian_sapiens_charge",
            () -> SoundEvent.createVariableRangeEvent(WotwCreatures.ID("martian_sapiens_charge")));

    public static final RegistryObject<SoundEvent> MARTIAN_SAPIENS_HURT = SOUND_EVENTS.register("martian_sapiens_hurt",
            () -> SoundEvent.createVariableRangeEvent(WotwCreatures.ID("martian_sapiens_hurt")));

    public static final RegistryObject<SoundEvent> MARTIAN_SAPIENS_DEATH = SOUND_EVENTS.register("martian_sapiens_death",
            () -> SoundEvent.createVariableRangeEvent(WotwCreatures.ID("martian_sapiens_death")));
}
