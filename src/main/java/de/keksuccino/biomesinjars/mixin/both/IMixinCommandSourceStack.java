package de.keksuccino.biomesinjars.mixin.both;

import net.minecraft.commands.CommandSourceStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CommandSourceStack.class)
public interface IMixinCommandSourceStack {

    @Accessor("permissionLevel") public int getPermissionLevelBiomesInJars();

}
