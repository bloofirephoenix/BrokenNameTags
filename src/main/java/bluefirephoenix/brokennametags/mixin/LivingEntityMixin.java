package bluefirephoenix.brokennametags.mixin;

import bluefirephoenix.brokennametags.BrokenNameTagItem;
import bluefirephoenix.brokennametags.BrokenNameTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Clock;
import java.time.LocalDateTime;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow protected boolean dead;


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci) {
        if (!this.removed && !this.dead) {
            // check if the entity has a custom name
            if (hasCustomName()) {
                ItemStack tag = new ItemStack(BrokenNameTags.BROKEN_NAMETAG, 1);
                BrokenNameTagItem item = (BrokenNameTagItem) tag.getItem();
                item.petName = getCustomName().asString();
                item.mobType = this.getType();
                item.deathTime = LocalDateTime.now(Clock.systemUTC());

                dropStack(tag);
            }
        }
    }
}
