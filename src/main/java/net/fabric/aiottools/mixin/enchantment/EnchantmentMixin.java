package net.fabric.aiottools.mixin.enchantment;

import net.fabric.aiottools.item.tool.AiotToolItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void allowFortuneOnAiotTools(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if ((Enchantment)(Object)this == Enchantments.FORTUNE) {
            if (stack.getItem() instanceof AiotToolItem) {
                cir.setReturnValue(true);
            }
        }
    }
}