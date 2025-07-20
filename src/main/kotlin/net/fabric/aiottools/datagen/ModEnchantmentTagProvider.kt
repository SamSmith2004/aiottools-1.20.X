package net.fabric.aiottools.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture

class ModEnchantmentTagProvider(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider.EnchantmentTagProvider(output, registriesFuture) {
//    companion object {
//        val ENCHANTABLE_DURABILITY = TagKey.of(RegistryKeys.ENCHANTMENT, Identifier("enchantable/durability"))
//    }

    override fun configure(arg: RegistryWrapper.WrapperLookup) {}
}