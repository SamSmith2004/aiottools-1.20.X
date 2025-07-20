package net.fabric.aiottools.mixin.enchantment;

import net.fabric.aiottools.item.tool.AiotToolItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EfficiencyEnchantment.class)
public abstract class EfficiencyEnchantmentMixin extends Enchantment {
    protected EfficiencyEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    public void injectedIsAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cbireturn) {
        cbireturn.setReturnValue(
                stack.getItem() instanceof AiotToolItem || stack.isOf(Items.SHEARS)  || super.isAcceptableItem(stack)
        );
    }
}
