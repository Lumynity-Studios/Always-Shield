package net.justmili.alwaysshield.core.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.jetbrains.annotations.Nullable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow @Nullable protected ItemStack useItem;

    @Inject(method = "getItemBlockingWith", at = @At("HEAD"), cancellable = true)
    private void removeShieldDelay(CallbackInfoReturnable<ItemStack> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!self.isUsingItem()) return;
        if (this.useItem == null || this.useItem.isEmpty()) return;

        Item item = this.useItem.getItem();
        if (item.getUseAnimation(this.useItem) != ItemUseAnimation.BLOCK) return;

        cir.setReturnValue(this.useItem);
    }
}
