package de.keksuccino.biomesinjars.entity.entities.biomejar.empty;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class EmptyBiomeJarEntityModel extends HierarchicalModel<EmptyBiomeJarEntity> {

    private final ModelPart root;
    private final ModelPart jar;

    public EmptyBiomeJarEntityModel(ModelPart root) {
        this.root = root.getChild("root");
        this.jar = this.root.getChild("jar");
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition jar = root.addOrReplaceChild("jar", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);

    }

    @Override
    public void setupAnim(EmptyBiomeJarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.rotationAnimationState, EmptyBiomeJarEntityAnimation.ROTATION, ageInTicks);
        this.animate(entity.hoverAnimationState, EmptyBiomeJarEntityAnimation.HOVER, ageInTicks);
        this.animate(entity.vibrateAnimationState, EmptyBiomeJarEntityAnimation.VIBRATE, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

}
