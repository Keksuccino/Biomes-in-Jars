package de.keksuccino.biomesinjars.mixin.client;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelBakery.class)
public abstract class MixinModelBakery {

    @Shadow public abstract UnbakedModel getModel(ResourceLocation resourceLocation);

    @Inject(at = @At("HEAD"), method = "getModel", cancellable = true)
    private void onGetModel(ResourceLocation resourceLocation, CallbackInfoReturnable<UnbakedModel> info) {
        try {
            if (resourceLocation.toString().startsWith("biomesinjars:") && resourceLocation.toString().endsWith("_in_a_jar#inventory") && !resourceLocation.toString().startsWith("biomesinjars:minecraft_plains_in_a_jar")) {
                UnbakedModel model = this.getModel(new ModelResourceLocation("biomesinjars:minecraft_plains_in_a_jar#inventory"));
                if (model != null) {
                    info.setReturnValue(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
