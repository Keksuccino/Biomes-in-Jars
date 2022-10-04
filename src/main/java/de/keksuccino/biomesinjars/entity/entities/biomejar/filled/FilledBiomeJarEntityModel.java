package de.keksuccino.biomesinjars.entity.entities.biomejar.filled;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FilledBiomeJarEntityModel extends HierarchicalModel<FilledBiomeJarEntity> {

    private final ModelPart root;
    private final ModelPart jar;

    public FilledBiomeJarEntityModel(ModelPart root) {
        this.root = root.getChild("root");
        this.jar = this.root.getChild("jar");
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition jar = root.addOrReplaceChild("jar", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tree = jar.addOrReplaceChild("tree", CubeListBuilder.create().texOffs(8, 8).addBox(-1.45F, -2.3F, -4.9F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.1F, -3.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.6F, -4.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.55F, -3.0F, -4.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.45F, -3.0F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.1F, -3.0F, -4.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.85F, -3.0F, -4.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.35F, -4.0F, -4.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.35F, -3.0F, -4.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.85F, -3.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.45F, -3.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, 0.25F, 4.75F));

        PartDefinition bush = jar.addOrReplaceChild("bush", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(0.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, 0.0F, 3.25F));

        return LayerDefinition.create(meshdefinition, 16, 16);

    }

    @Override
    public void setupAnim(FilledBiomeJarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.rotationAnimationState, FilledBiomeJarEntityAnimation.ROTATION, ageInTicks);
        this.animate(entity.hoverAnimationState, FilledBiomeJarEntityAnimation.HOVER, ageInTicks);
        this.animate(entity.vibrateAnimationState, FilledBiomeJarEntityAnimation.VIBRATE, ageInTicks);
        this.animate(entity.flyDownAnimationState, FilledBiomeJarEntityAnimation.FLY_DOWN, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

}
