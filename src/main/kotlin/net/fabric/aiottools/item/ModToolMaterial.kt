package net.fabric.aiottools.item

import net.minecraft.item.Items
import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient

enum class ModToolMaterial(
    private val durability: Int,
    private val miningSpeedMultiplier: Float,
    private val attackDamage: Float,
    private val miningLevel: Int,
    private val enchantability: Int,
    private val repairIngredient: Ingredient = Ingredient.EMPTY
) : ToolMaterial {
    NETHERITE_AIOT(
        durability = 2000,
        miningSpeedMultiplier = 10.0f,
        attackDamage = 5.0f,
        miningLevel = 5,
        enchantability = 26,
        repairIngredient = Ingredient.ofItems(Items.NETHERITE_INGOT),
    ),
    DIAMOND_AIOT(
        durability = 2000,
        miningSpeedMultiplier = 10.0f,
        attackDamage = 5.0f,
        miningLevel = 5,
        enchantability = 26,
        repairIngredient = Ingredient.ofItems(Items.DIAMOND),
    ),
    GOLD_AIOT(
        durability = 2000,
        miningSpeedMultiplier = 10.0f,
        attackDamage = 5.0f,
        miningLevel = 5,
        enchantability = 26,
        repairIngredient = Ingredient.ofItems(Items.GOLD_INGOT),
    ),
    IRON_AIOT(
        durability = 2000,
        miningSpeedMultiplier = 10.0f,
        attackDamage = 5.0f,
        miningLevel = 5,
        enchantability = 26,
        repairIngredient = Ingredient.ofItems(Items.IRON_INGOT),
    );

    override fun getDurability() = durability
    override fun getMiningSpeedMultiplier() = miningSpeedMultiplier
    override fun getAttackDamage() = attackDamage
    override fun getMiningLevel() = miningLevel
    override fun getEnchantability() = enchantability
    override fun getRepairIngredient() = repairIngredient
}