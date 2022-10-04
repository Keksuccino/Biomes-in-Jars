package de.keksuccino.biomesinjars.entity.entities.biomejar.filled;

import de.keksuccino.biomesinjars.entity.Entities;
import de.keksuccino.biomesinjars.item.Items;
import de.keksuccino.biomesinjars.item.items.FilledBiomeJarItem;
import de.keksuccino.biomesinjars.util.ItemUtils;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.util.math.vector.Vector3d;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;

public class FilledBiomeJarEntity extends MobEntity implements IAnimatable {

    private static final DataParameter<Boolean> DATA_READY_TO_PICKUP = EntityDataManager.defineId(FilledBiomeJarEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_SPAWNED_BY_EMPTY_JAR = EntityDataManager.defineId(FilledBiomeJarEntity.class, DataSerializers.BOOLEAN);

    private AnimationFactory animationFactory = new AnimationFactory(this);

    public RegistryKey<Biome> biome;

    public FilledBiomeJarEntity(EntityType<? extends MobEntity> entityType, World level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_READY_TO_PICKUP, false);
        this.entityData.define(DATA_SPAWNED_BY_EMPTY_JAR, false);
    }

    public void setReadyToPickup(boolean b) {
        this.entityData.set(DATA_READY_TO_PICKUP, b);
    }

    public boolean isReadyToPickup() {
        return this.entityData.get(DATA_READY_TO_PICKUP);
    }

    public void setSpawnedByEmptyJar(boolean b) {
        this.entityData.set(DATA_SPAWNED_BY_EMPTY_JAR, b);
    }

    public boolean isSpawnedByEmptyJar() {
        return this.entityData.get(DATA_SPAWNED_BY_EMPTY_JAR);
    }

    public static AttributeModifierMap.MutableAttribute createFilledBiomeJarEntityAttributes() {
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
        if (this.level.isClientSide) {
            if (!this.isReadyToPickup() && this.isSpawnedByEmptyJar()) {
//                this.rotationAnimationState.startIfStopped(this.tickCount);
                if (this.tickCount < 30) {
//                    this.hoverAnimationState.startIfStopped(this.tickCount);
//                    this.vibrateAnimationState.startIfStopped(this.tickCount);
                } else {
//                    this.hoverAnimationState.stop();
//                    this.vibrateAnimationState.stop();
//                    this.flyDownAnimationState.startIfStopped(this.tickCount);
                }
            } else {
//                this.hoverAnimationState.stop();
//                this.vibrateAnimationState.stop();
//                this.flyDownAnimationState.stop();
//                this.rotationAnimationState.stop();
            }
        }
        if (this.tickCount >= 130) {
            if (!this.isReadyToPickup()) {
                this.setReadyToPickup(true);
                if (!this.level.isClientSide) {
                    level.playSound(null, this.position().x, this.position().y, this.position().z, SoundEvents.ILLUSIONER_PREPARE_MIRROR, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
        super.tick();
    }

    @Override
    protected boolean updateInWaterStateAndDoFluidPushing() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("biomesinjars_biome", this.biome.location().toString());
        compoundTag.putBoolean("biomesinjars_ready_to_pickup", this.isReadyToPickup());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("biomesinjars_biome")) {
            RegistryKey<Biome> biomeResourceKey = null;
            try {
                biomeResourceKey = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(compoundTag.getString("biomesinjars_biome")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (biomeResourceKey != null) {
                this.biome = biomeResourceKey;
            } else {
                this.biome = Biomes.PLAINS;
            }
        }
        if (compoundTag.contains("biomesinjars_ready_to_pickup")) {
            this.setReadyToPickup(compoundTag.getBoolean("biomesinjars_ready_to_pickup"));
        }
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand interactionHand) {
        if (!this.level.isClientSide) {
            if (this.isReadyToPickup()) {
                this.kill();
                this.remove(false);
                level.playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
                int slot = player.inventory.getFreeSlot();
                ItemStack filledJarStack;
                if (this.biome != null) {
                    filledJarStack = FilledBiomeJarItem.createStack(this.biome);
                } else {
                    filledJarStack = new ItemStack(Items.FILLED_BIOME_JAR_ITEM.get());
                }
                if (slot != -1) {
                    player.inventory.setItem(slot, filledJarStack);
                } else {
                    ItemUtils.dropItemStack(this.level, filledJarStack, this.blockPosition());
                }
            }
        }
        return ActionResultType.sidedSuccess(this.level.isClientSide);
    }

    public static FilledBiomeJarEntity spawnAt(ServerWorld serverLevel, Vector3d position, float xRot, RegistryKey<Biome> biome, boolean spawnedByEmptyJarEntity) {
        FilledBiomeJarEntity entity = Entities.FILLED_BIOME_JAR_ENTITY.get().create(serverLevel);
        if (entity == null) {
            return null;
        }
        entity.setSpawnedByEmptyJar(spawnedByEmptyJarEntity);
        entity.biome = biome;
        entity.setPos(position.x, position.y, position.z);
        entity.moveTo(position.x, position.y, position.z, xRot, 0.0f);
        entity.yHeadRot = entity.yRot;
        entity.yBodyRot = entity.yRot;
        entity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.SPAWN_EGG, null, null);
        entity.playAmbientSound();
        EntityType.updateCustomEntityTag(serverLevel, null, entity, null);
        serverLevel.addFreshEntityWithPassengers(entity);
        return entity;
    }

    private <E extends IAnimatable> PlayState rotateAnimationPredicate(AnimationEvent<E> e) {
        if (this.level.isClientSide) {
            if (!this.isReadyToPickup() && this.isSpawnedByEmptyJar()) {
                AnimationBuilder builder = new AnimationBuilder();
                builder.addAnimation("animation.biome_jar.rotate");
                e.getController().setAnimationSpeed(2.3D);
                e.getController().setAnimation(builder);
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState hoverAnimationPredicate(AnimationEvent<E> e) {
        if (this.level.isClientSide) {
            if (!this.isReadyToPickup() && this.isSpawnedByEmptyJar()) {
                AnimationBuilder builder = new AnimationBuilder();
                if (this.tickCount < 30) {
                    builder.addAnimation("animation.biome_jar.hover");
                    e.getController().setAnimation(builder);
                    return PlayState.CONTINUE;
                }
            }
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState vibrateAnimationPredicate(AnimationEvent<E> e) {
        if (this.level.isClientSide) {
            if (!this.isReadyToPickup() && this.isSpawnedByEmptyJar()) {
                AnimationBuilder builder = new AnimationBuilder();
                if (this.tickCount < 30) {
                    builder.addAnimation("animation.biome_jar.vibrate");
                    e.getController().setAnimation(builder);
                    return PlayState.CONTINUE;
                }
            }
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState downAnimationPredicate(AnimationEvent<E> e) {
        if (this.level.isClientSide) {
            if (!this.isReadyToPickup() && this.isSpawnedByEmptyJar()) {
                AnimationBuilder builder = new AnimationBuilder();
                if (this.tickCount > 30) {
                    builder.addAnimation("animation.biome_jar.down");
                    e.getController().setAnimation(builder);
                    return PlayState.CONTINUE;
                }
            }
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "down_controller", 0, this::downAnimationPredicate));
        animationData.addAnimationController(new AnimationController(this, "rotate_controller", 0, this::rotateAnimationPredicate));
        animationData.addAnimationController(new AnimationController(this, "vibrate_controller", 0, this::vibrateAnimationPredicate));
        animationData.addAnimationController(new AnimationController(this, "hover_controller", 0, this::hoverAnimationPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

}
