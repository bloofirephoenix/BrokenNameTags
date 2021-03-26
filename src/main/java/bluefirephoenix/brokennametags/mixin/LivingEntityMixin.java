package bluefirephoenix.brokennametags.mixin;

import bluefirephoenix.brokennametags.BrokenNameTagItem;
import bluefirephoenix.brokennametags.BrokenNameTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow protected boolean dead;
    LivingEntity entity;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci) {

        if (!this.removed && !this.dead) {

            TameableEntity pet = null;

            if (((LivingEntity) (Object) this) instanceof TameableEntity) {
                pet = ((TameableEntity) (Object) this);
            }

            // check if the entity has a custom name
            if (hasCustomName() || (pet != null && pet.isTamed())) { // the order of this if statement matters

                ItemStack tag = new ItemStack(BrokenNameTags.BROKEN_NAMETAG, 1);
                BrokenNameTagItem item = (BrokenNameTagItem) tag.getItem();
                CompoundTag nbt = new CompoundTag();

                String name = getDefaultName().getString();

                if (hasCustomName()) {
                    name = getCustomName().getString();
                }

                if (source.getDeathMessage((LivingEntity) (Object) this) != null) {
                    nbt.putString("deathMessage",source.getDeathMessage((LivingEntity) (Object) this).getString());
                }

                tag.setCustomName(Text.of(name));
                tag.putSubTag("data",nbt);

                dropStack(tag);
            }
        }
    }
}
