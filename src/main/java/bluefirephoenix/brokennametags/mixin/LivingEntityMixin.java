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

            // check if the entity has a custom name
            if (hasCustomName()) { // the order of this if statement matters
                ItemStack tag = new ItemStack(BrokenNameTags.BROKEN_NAMETAG, 1);
                CompoundTag nbt = new CompoundTag();

                Text name = getDefaultName();

                if (hasCustomName()) {
                    name = getCustomName();
                }

                if (source.getDeathMessage((LivingEntity) (Object) this) != null) {
                    nbt.putString("deathMessage", source.getDeathMessage((LivingEntity) (Object) this).getString());
                }
                tag.setCustomName(name); // todo this is causing the crash
                tag.putSubTag("data",nbt);
                dropStack(tag);
            }
        }
    }
}
