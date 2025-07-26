package net.fabric.aiottools.mixin.enchantment;

import net.fabric.aiottools.item.tool.AiotToolItem;
import net.minecraft.item.Item;
import net.minecraft.item.MiningToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.enchantment.EnchantmentTarget$7") // 7 == DIGGER
public class EnchantmentTargetMixin {

//    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
//    private void modifyDiggerTarget(Item item, CallbackInfoReturnable<Boolean> cir) {
//        if (item instanceof AiotToolItem || item instanceof MiningToolItem) {
//            cir.setReturnValue(true);
//        }
//    }
}
