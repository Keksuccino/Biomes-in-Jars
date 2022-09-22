package de.keksuccino.biomesinjars.entity.entities.biomejar.filled;

import de.keksuccino.biomesinjars.entity.Entities;
import de.keksuccino.biomesinjars.item.Items;
import de.keksuccino.biomesinjars.util.ItemUtils;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.phys.Vec3;

public class FilledBiomeJarEntity extends Mob {

    public AnimationState rotationAnimationState = new AnimationState();
    public AnimationState hoverAnimationState = new AnimationState();
    public AnimationState vibrateAnimationState = new AnimationState();
    public AnimationState flyDownAnimationState = new AnimationState();

    public ResourceKey<Biome> biome;
    public boolean readyToPickup = false;

    protected boolean readySoundPlayed = false;

    public FilledBiomeJarEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createFilledBiomeJarEntityAttributes() {
        return createMobAttributes();
    }

    @Override
    public void push(double d, double e, double f) {
        // no pushing! (> - <)
    }

    @Override
    public void knockback(double d, double e, double f) {
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
            if (!this.readyToPickup) {
                this.rotationAnimationState.startIfStopped(this.tickCount);
                if (this.tickCount < 30) {
                    this.hoverAnimationState.startIfStopped(this.tickCount);
                    this.vibrateAnimationState.startIfStopped(this.tickCount);
                } else {
                    this.hoverAnimationState.stop();
                    this.vibrateAnimationState.stop();
                    this.flyDownAnimationState.startIfStopped(this.tickCount);
                }
            } else {
                this.hoverAnimationState.stop();
                this.vibrateAnimationState.stop();
                this.flyDownAnimationState.stop();
                this.rotationAnimationState.stop();
            }
        }
        if (this.tickCount >= 130) {
            this.readyToPickup = true;
            if (!this.level.isClientSide) {
                if (!this.readySoundPlayed) {
                    level.playSound(null, this.position().x, this.position().y, this.position().z, SoundEvents.ILLUSIONER_PREPARE_MIRROR, SoundSource.PLAYERS, 1.0F, 1.0F);
                    this.readySoundPlayed = true;
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
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("biomesinjars_biome", this.biome.location().toString());
        compoundTag.putBoolean("biomesinjars_ready_to_pickup", this.readyToPickup);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("biomesinjars_biome")) {
            ResourceKey<Biome> biomeResourceKey = null;
            try {
                biomeResourceKey = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(compoundTag.getString("biomesinjars_biome")));
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
            this.readyToPickup = compoundTag.getBoolean("biomesinjars_ready_to_pickup");
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        if (!this.level.isClientSide) {
            if (this.readyToPickup) {
                this.kill();
                this.remove(RemovalReason.KILLED);
                level.playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                Item biomeJarItem = Items.getJarItemByBiome(this.biome);
                if (biomeJarItem == null) {
                    biomeJarItem = Items.getJarItemByBiome(Biomes.PLAINS);
                }
                int slot = player.getInventory().getFreeSlot();
                if (slot != -1) {
                    player.getInventory().setItem(slot, new ItemStack(biomeJarItem));
                } else {
                    ItemUtils.dropItemStack(this.level, new ItemStack(biomeJarItem), this.blockPosition());
                }
            }
        }
        return InteractionResult.sidedSuccess(this.level.isClientSide);
    }

    public static FilledBiomeJarEntity spawnAt(ServerLevel serverLevel, Vec3 position, float xRot, ResourceKey<Biome> biome) {
        FilledBiomeJarEntity entity = Entities.FILLED_BIOME_JAR_ENTITY.create(serverLevel);
        if (entity == null) {
            return null;
        }
        entity.biome = biome;
        entity.setPos(position.x, position.y, position.z);
        entity.moveTo(position.x, position.y, position.z, xRot, 0.0f);
        entity.yHeadRot = entity.getYRot();
        entity.yBodyRot = entity.getYRot();
        entity.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWN_EGG, null, null);
        entity.playAmbientSound();
        EntityType.updateCustomEntityTag(serverLevel, null, entity, null);
        serverLevel.addFreshEntityWithPassengers(entity);
        return entity;
    }

}
