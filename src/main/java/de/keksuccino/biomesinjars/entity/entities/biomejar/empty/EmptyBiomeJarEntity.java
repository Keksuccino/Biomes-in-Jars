package de.keksuccino.biomesinjars.entity.entities.biomejar.empty;

import de.keksuccino.biomesinjars.BiomesInJars;
import de.keksuccino.biomesinjars.biome.Biomes;
import de.keksuccino.biomesinjars.entity.Entities;
import de.keksuccino.biomesinjars.entity.entities.biomejar.filled.FilledBiomeJarEntity;
import de.keksuccino.biomesinjars.item.Items;
import de.keksuccino.biomesinjars.mixin.both.IMixinChunkMap;
import de.keksuccino.biomesinjars.util.ItemUtils;
import de.keksuccino.biomesinjars.util.WorldUtils;
import de.keksuccino.konkrete.math.MathUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.network.IPacket;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.stream.Stream;

public class EmptyBiomeJarEntity extends MobEntity implements IAnimatable {

    private AnimationFactory animationFactory = new AnimationFactory(this);

    protected boolean isTicking = true;
    protected boolean vibratingSoundPlayed = false;
    protected int vibratingParticleLastTick = -1000;

    public EmptyBiomeJarEntity(EntityType<? extends MobEntity> entityType, World level) {
        super(entityType, level);
    }

    public static AttributeModifierMap.MutableAttribute createEmptyBiomeJarEntityAttributes() {
        return createMobAttributes();
    }

    @Override
    public void push(double d, double e, double f) {
        // no pushing! (> - <)
    }

    @Override
    public void knockback(float p_233627_1_, double p_233627_2_, double p_233627_4_) {
        // no knocking!!!!
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        // no hurting! (._.)
        return false;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public void tick() {
        Biome biomeHolder = this.level.getBiome(this.blockPosition());
        if (biomeHolder != this.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).get(Biomes.DEAD_LAND.getId())) {
            if (this.level.isClientSide) {
//                this.hoverAnimationState.startIfStopped(this.tickCount);
//                this.rotationAnimationState.startIfStopped(this.tickCount);
                if (this.tickCount >= 200) {
//                    this.vibrateAnimationState.startIfStopped(this.tickCount);
                    if (this.vibratingParticleLastTick <= (this.tickCount + 10)) {
                        this.vibratingParticleLastTick = this.tickCount;
                        this.level.addParticle(ParticleTypes.DRAGON_BREATH, this.getX() + MathUtils.getRandomNumberInRange(-2, 2), this.getY() + MathUtils.getRandomNumberInRange(-2, 2), this.getZ() + MathUtils.getRandomNumberInRange(-2, 2), 0.0, 0.0, 0.0);
                    }
                }
            } else {
                if (isTicking) {
                    if (this.tickCount >= 200) {
                        if (!this.vibratingSoundPlayed) {
                            level.playSound(null, this.position().x, this.position().y, this.position().z, SoundEvents.ENDER_DRAGON_DEATH, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            this.vibratingSoundPlayed = true;
                        }
                    }
                    if (this.tickCount >= 350) {
                        FilledBiomeJarEntity.spawnAt((ServerWorld) this.level, this.position(), this.xRot, this.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getResourceKey(biomeHolder).get(), true);
                        if (BiomesInJars.config.getOrDefault("convert_to_dead_land", true)) {
                            setDeadLandBiomeAt(this.blockPosition());
                            setDeadLandBiomeAt(this.blockPosition().east(16));
                            setDeadLandBiomeAt(this.blockPosition().west(16));
                            setDeadLandBiomeAt(this.blockPosition().north(16));
                            setDeadLandBiomeAt(this.blockPosition().south(16));
                            setDeadLandBiomeAt(this.blockPosition().north(16).west(16));
                            setDeadLandBiomeAt(this.blockPosition().north(16).east(16));
                            setDeadLandBiomeAt(this.blockPosition().south(16).west(16));
                            setDeadLandBiomeAt(this.blockPosition().south(16).east(16));
                        }
                        level.playSound(null, this.position().x, this.position().y, this.position().z, SoundEvents.ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        this.kill();
                        this.remove(false);
                        this.isTicking = false;
                    }
                }
            }
        }
        super.tick();
    }

    protected void setDeadLandBiomeAt(BlockPos blockPos) {
        RegistryKey<Biome> b = RegistryKey.create(Registry.BIOME_REGISTRY, Biomes.DEAD_LAND.getId());
        WorldUtils.setChunkBiomeAtBlockPos(this.level, blockPos, b);
        this.level.getServer().getPlayerList().getPlayers().forEach((player) -> {
            try {
                ((IMixinChunkMap)((ServerWorld)this.level).getChunkSource().chunkMap).playerLoadedChunkBiomesInJars(player, new IPacket[2], this.level.getChunkAt(blockPos));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected boolean updateInWaterStateAndDoFluidPushing() {
        return false;
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand interactionHand) {
        if (!this.level.isClientSide) {
            this.kill();
            this.remove(false);
            level.playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
            int slot = player.inventory.getFreeSlot();
            if (slot != -1) {
                player.inventory.setItem(slot, new ItemStack(Items.EMPTY_BIOME_JAR_ITEM.get()));
            } else {
                ItemUtils.dropItemStack(this.level, new ItemStack(Items.EMPTY_BIOME_JAR_ITEM.get()), this.blockPosition());
            }
        }
        return ActionResultType.sidedSuccess(this.level.isClientSide);
    }

    public static EmptyBiomeJarEntity spawnAt(ServerWorld serverLevel, BlockPos blockPos, float xRot, boolean b) {
        EmptyBiomeJarEntity entity = Entities.EMPTY_BIOME_JAR_ENTITY.get().create(serverLevel);
        if (entity == null) {
            return null;
        }
        entity.setPos((double)blockPos.getX() + 0.5, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5);
        double d = getYOffset(serverLevel, blockPos, b, entity.getBoundingBox());
        entity.moveTo((double)blockPos.getX() + 0.5, (double)blockPos.getY() + d, (double)blockPos.getZ() + 0.5, xRot, 0.0f);
        entity.yHeadRot = entity.yRot;
        entity.yBodyRot = entity.yRot;
        entity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.SPAWN_EGG, null, null);
        entity.playAmbientSound();
        EntityType.updateCustomEntityTag(serverLevel, null, entity, null);
        serverLevel.addFreshEntityWithPassengers(entity);
        return entity;
    }

    protected static double getYOffset(IWorldReader levelReader, BlockPos blockPos, boolean bl, AxisAlignedBB aABB) {
        AxisAlignedBB aABB2 = new AxisAlignedBB(blockPos);
        if (bl) {
            aABB2 = aABB2.expandTowards(0.0, -1.0, 0.0);
        }
        Stream<VoxelShape> iterable = levelReader.getCollisions(null, aABB2, (e) -> {
            return true;
        });
        return 1.0 + VoxelShapes.collide(Direction.Axis.Y, aABB, iterable, bl ? -2.0 : -1.0);
    }

    private <E extends IAnimatable> PlayState hoverAnimationPredicate(AnimationEvent<E> e) {
        if (this.level.isClientSide) {
            Biome biomeHolder = this.level.getBiome(this.blockPosition());
            if (biomeHolder != this.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).get(Biomes.DEAD_LAND.getId())) {
                AnimationBuilder builder = new AnimationBuilder();
                builder.addAnimation("animation.biome_jar.hover", true);
                e.getController().setAnimation(builder);
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState rotateAnimationPredicate(AnimationEvent<E> e) {
        if (this.level.isClientSide) {
            Biome biomeHolder = this.level.getBiome(this.blockPosition());
            if (biomeHolder != Biomes.DEAD_LAND.get()) {
                AnimationBuilder builder = new AnimationBuilder();
                builder.addAnimation("animation.biome_jar.rotate", true);
                e.getController().setAnimationSpeed(2.3D);
                e.getController().setAnimation(builder);
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState vibrateAnimationPredicate(AnimationEvent<E> e) {
        if (this.level.isClientSide) {
            Biome biomeHolder = this.level.getBiome(this.blockPosition());
            if (biomeHolder != this.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).get(Biomes.DEAD_LAND.getId())) {
                AnimationBuilder builder = new AnimationBuilder();
                if (this.tickCount >= 200) {
                    builder.addAnimation("animation.biome_jar.vibrate", true);
                    e.getController().setAnimation(builder);
                    return PlayState.CONTINUE;
                }
            }
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "hover_controller", 0, this::hoverAnimationPredicate));
        animationData.addAnimationController(new AnimationController(this, "rotate_controller", 0, this::rotateAnimationPredicate));
        animationData.addAnimationController(new AnimationController(this, "vibrate_controller", 0, this::vibrateAnimationPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

}
