package de.keksuccino.biomesinjars.entity.entities.biomejar.empty;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class EmptyBiomeJarEntityAnimation {

    public static final AnimationDefinition ROTATION = AnimationDefinition.Builder.withLength(2f).looping().addAnimation("jar", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 90f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 180f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 270f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 357.5f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition HOVER = AnimationDefinition.Builder.withLength(2.5416666666666665f).looping().addAnimation("jar", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 10f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2083333333333333f, KeyframeAnimations.posVec(0f, 11f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.5416666666666665f, KeyframeAnimations.posVec(0f, 10f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition VIBRATE = AnimationDefinition.Builder.withLength(0.16f).looping().addAnimation("jar", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.02f, KeyframeAnimations.posVec(0f, 0f, 1f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.04f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.06f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.08f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.1f, KeyframeAnimations.posVec(1f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.14f, KeyframeAnimations.posVec(-1f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.16f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();

}
