package de.keksuccino.biomesinjars.mixin.client;

import de.keksuccino.biomesinjars.item.items.BiomeInAJarItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Shadow @Final private ItemModelShaper itemModelShaper;

    @Inject(at = @At("HEAD"), method = "getModel", cancellable = true)
    private void onGetModel(ItemStack itemStack, Level level, LivingEntity livingEntity, int i, CallbackInfoReturnable<BakedModel> info) {
        if (itemStack.getItem() instanceof BiomeInAJarItem) {
            BakedModel bakedModel = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("biomesinjars:minecraft_plains_in_a_jar#inventory"));
            ClientLevel clientLevel = level instanceof ClientLevel ? (ClientLevel)level : null;
            BakedModel bakedModel2 = bakedModel.getOverrides().resolve(bakedModel, itemStack, clientLevel, livingEntity, i);
            info.setReturnValue(bakedModel2 == null ? this.itemModelShaper.getModelManager().getMissingModel() : bakedModel2);
        }
    }

}
