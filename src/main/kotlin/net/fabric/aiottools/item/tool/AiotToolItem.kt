@file:Suppress("PrivatePropertyName")
package net.fabric.aiottools.item.tool

import com.google.common.collect.*
import com.mojang.datafixers.util.Pair
import net.fabric.aiottools.AiotTools.MOD_ID
import net.fabricmc.yarn.constants.MiningLevels
import net.minecraft.advancement.criterion.Criteria
import net.minecraft.block.*
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldEvents
import net.minecraft.world.event.GameEvent
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.CampfireBlock

class AiotToolItem(
    material: ToolMaterial,
    attackDamageBonus: Float,
    attackSpeed: Float,
    settings: Settings,
): SwordItem(
    material,
    attackDamageBonus.toInt(),
    attackSpeed,
    settings,
) {
    private val attackDamage: Float = attackDamageBonus + material.attackDamage
    private val miningSpeed: Float = material.miningSpeedMultiplier
    private val effectiveBlocks: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, Identifier(MOD_ID, "aiot_effective"))
    private val attributeModifiers: ImmutableMultimap<EntityAttribute, EntityAttributeModifier> =
        ImmutableMultimap.builder<EntityAttribute, EntityAttributeModifier>()
            .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier(
                    "Weapon modifier",
                    attackDamage.toDouble(),
                    EntityAttributeModifier.Operation.ADDITION))
            .put(EntityAttributes.GENERIC_ATTACK_SPEED, EntityAttributeModifier(
                    "Weapon modifier",
                    attackSpeed.toDouble(),
                    EntityAttributeModifier.Operation.ADDITION))
            .build()
    
    private val TILLING_ACTIONS: MutableMap<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> =
        Maps.newHashMap<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>>(
            ImmutableMap.of<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>>(
                Blocks.GRASS_BLOCK,
                Pair.of<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>(Predicate { context: ItemUsageContext ->
                    HoeItem.canTillFarmland(context)
                }, HoeItem.createTillAction(Blocks.FARMLAND.defaultState)),
                Blocks.DIRT_PATH,
                Pair.of<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>(Predicate { context: ItemUsageContext ->
                    HoeItem.canTillFarmland(context)
                }, HoeItem.createTillAction(Blocks.FARMLAND.defaultState)),
                Blocks.DIRT,
                Pair.of<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>(Predicate { context: ItemUsageContext ->
                    HoeItem.canTillFarmland(context)
                }, HoeItem.createTillAction(Blocks.FARMLAND.defaultState)),
                Blocks.COARSE_DIRT,
                Pair.of<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>(Predicate { context: ItemUsageContext ->
                    HoeItem.canTillFarmland(context)
                }, HoeItem.createTillAction(Blocks.DIRT.defaultState)),
                Blocks.ROOTED_DIRT,
                Pair.of<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>(
                    Predicate { itemUsageContext: ItemUsageContext -> true },
                    HoeItem.createTillAndDropAction(Blocks.DIRT.defaultState, Items.HANGING_ROOTS)
                )
            )
        )

    private val STRIPPED_BLOCKS: Map<Block, Block> = mapOf(
        Blocks.OAK_WOOD to Blocks.STRIPPED_OAK_WOOD,
        Blocks.OAK_LOG to Blocks.STRIPPED_OAK_LOG,
        Blocks.DARK_OAK_WOOD to Blocks.STRIPPED_DARK_OAK_WOOD,
        Blocks.DARK_OAK_LOG to Blocks.STRIPPED_DARK_OAK_LOG,
        Blocks.ACACIA_WOOD to Blocks.STRIPPED_ACACIA_WOOD,
        Blocks.ACACIA_LOG to Blocks.STRIPPED_ACACIA_LOG,
        Blocks.CHERRY_WOOD to Blocks.STRIPPED_CHERRY_WOOD,
        Blocks.CHERRY_LOG to Blocks.STRIPPED_CHERRY_LOG,
        Blocks.BIRCH_WOOD to Blocks.STRIPPED_BIRCH_WOOD,
        Blocks.BIRCH_LOG to Blocks.STRIPPED_BIRCH_LOG,
        Blocks.JUNGLE_WOOD to Blocks.STRIPPED_JUNGLE_WOOD,
        Blocks.JUNGLE_LOG to Blocks.STRIPPED_JUNGLE_LOG,
        Blocks.SPRUCE_WOOD to Blocks.STRIPPED_SPRUCE_WOOD,
        Blocks.SPRUCE_LOG to Blocks.STRIPPED_SPRUCE_LOG,
        Blocks.WARPED_STEM to Blocks.STRIPPED_WARPED_STEM,
        Blocks.WARPED_HYPHAE to Blocks.STRIPPED_WARPED_HYPHAE,
        Blocks.CRIMSON_STEM to Blocks.STRIPPED_CRIMSON_STEM,
        Blocks.CRIMSON_HYPHAE to Blocks.STRIPPED_CRIMSON_HYPHAE,
        Blocks.MANGROVE_WOOD to Blocks.STRIPPED_MANGROVE_WOOD,
        Blocks.MANGROVE_LOG to Blocks.STRIPPED_MANGROVE_LOG,
        Blocks.BAMBOO_BLOCK to Blocks.STRIPPED_BAMBOO_BLOCK
    )

    private val PATH_STATES = mapOf(
        Blocks.GRASS_BLOCK to Blocks.DIRT_PATH.defaultState,
        Blocks.DIRT to Blocks.DIRT_PATH.defaultState,
        Blocks.COARSE_DIRT to Blocks.DIRT_PATH.defaultState,
        Blocks.MYCELIUM to Blocks.DIRT_PATH.defaultState,
        Blocks.ROOTED_DIRT to Blocks.DIRT_PATH.defaultState
    )

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        return when {
            state.isOf(Blocks.COBWEB) -> 15.0f
            state.isIn(BlockTags.SWORD_EFFICIENT) -> 1.5f
            state.isIn(effectiveBlocks) -> miningSpeed
            else -> 1.0f
        }
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        stack.damage<LivingEntity>(
            2,
            attacker
        ) { e: LivingEntity -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }
        return true
    }

    override fun postMine(
        stack: ItemStack,
        world: World,
        state: BlockState,
        pos: BlockPos,
        miner: LivingEntity
    ): Boolean {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            stack.damage<LivingEntity>(
                1,
                miner
            ) { e: LivingEntity -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }
        }

        return true
    }

    override fun getAttributeModifiers(slot: EquipmentSlot): Multimap<EntityAttribute, EntityAttributeModifier> {
        return if (slot == EquipmentSlot.MAINHAND) this.attributeModifiers else super.getAttributeModifiers(slot)
    }

    override fun isSuitableFor(state: BlockState): Boolean {
        val miningLevel = this.material.miningLevel
        return when {
            miningLevel < MiningLevels.DIAMOND && state.isIn(BlockTags.NEEDS_DIAMOND_TOOL) -> false
            miningLevel < MiningLevels.IRON && state.isIn(BlockTags.NEEDS_IRON_TOOL) -> false
            miningLevel < MiningLevels.STONE && state.isIn(BlockTags.NEEDS_STONE_TOOL) -> false
            else -> state.isIn(effectiveBlocks)
        }
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val blockPos = context.blockPos
        val blockState = world.getBlockState(blockPos)
        if (checkSurroundingForWater(world, blockPos)) {
            if (hoeCheck(context, world, blockPos)) return ActionResult.success(world.isClient)
        }
        if (shovelCheck(context, world, blockPos, blockState)) return ActionResult.success(world.isClient)

        val playerEntity = context.player
        val itemStack = context.stack

        val optional: Optional<BlockState>  = this.getStrippedState(blockState)
        val optional2: Optional<BlockState>  = Oxidizable.getDecreasedOxidationState(blockState)
        val optional3: Optional<BlockState>  = Optional.ofNullable<Block?>((HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get() as BiMap<*, *>)[blockState.block] as Block?)
            .map(Function { block: Block? -> block?.getStateWithProperties(blockState) })
        var optional4: Optional<BlockState>? = null

        if (optional.isPresent) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f)
            optional4 = optional
        } else if (optional2.isPresent) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.0f)
            world.syncWorldEvent(playerEntity, WorldEvents.BLOCK_SCRAPED, blockPos, 0)
            optional4 = optional2
        } else if (optional3.isPresent) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0f, 1.0f)
            world.syncWorldEvent(playerEntity, WorldEvents.WAX_REMOVED, blockPos, 0)
            optional4 = optional3
        }

        if (optional4 != null && optional4.isPresent) {
            if (playerEntity is ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger(playerEntity, blockPos, itemStack)
            }

            world.setBlockState(blockPos, optional4.get(), Block.NOTIFY_ALL or Block.REDRAW_ON_MAIN_THREAD)
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, optional4.get()))
            if (playerEntity != null) {
                itemStack.damage<PlayerEntity>(
                    1,
                    playerEntity
                ) { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) }
            }

            return ActionResult.success(world.isClient)
        } else {
            return ActionResult.PASS
        }
    }

    private fun hoeCheck(context: ItemUsageContext, world: World, blockPos: BlockPos): Boolean {
        val pair: Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>? = TILLING_ACTIONS[world.getBlockState(blockPos).block]
        if (pair == null) { return false
        } else {
            val predicate: Predicate<ItemUsageContext> = pair.getFirst()
            val consumer: Consumer<ItemUsageContext> = pair.getSecond()
            if (predicate.test(context)) {
                val playerEntity = context.player
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f)
                if (!world.isClient) {
                    consumer.accept(context)
                    if (playerEntity != null) {
                        context.stack.damage(
                            1,
                            playerEntity
                        ) { p: PlayerEntity? -> p!!.sendToolBreakStatus(context.hand) }
                    }
                }
                return true
            } else {
                return false
            }
        }
    }

    private fun checkSurroundingForWater(world: World, blockPos: BlockPos): Boolean {
        val surroundingBlocks = listOf(
            world.getBlockState(blockPos.north()),
            world.getBlockState(blockPos.south()),
            world.getBlockState(blockPos.west()),
            world.getBlockState(blockPos.east())
        )
        for (block in surroundingBlocks) {
            if (block.isOf(Blocks.WATER)) return true
        }
        return false
    }

    private fun shovelCheck(context: ItemUsageContext, world: World, blockPos: BlockPos, blockState: BlockState): Boolean {
        if (context.side == Direction.DOWN) {
            return false
        } else {
            val playerEntity = context.player
            val blockState2 = PATH_STATES[blockState.block]
            var blockState3: BlockState? = null
            if (blockState2 != null && world.getBlockState(blockPos.up()).isAir) {
                world.playSound(
                    playerEntity,
                    blockPos,
                    SoundEvents.ITEM_SHOVEL_FLATTEN,
                    SoundCategory.BLOCKS,
                    1.0f,
                    1.0f
                )
                blockState3 = blockState2
            } else if (blockState.block is CampfireBlock && blockState.get(CampfireBlock.LIT)) {
                if (!world.isClient()) {
                    world.syncWorldEvent(null, WorldEvents.FIRE_EXTINGUISHED, blockPos, 0)
                }

                CampfireBlock.extinguish(context.player, world, blockPos, blockState)
                blockState3 = blockState.with(CampfireBlock.LIT, false)
            }

            if (blockState3 != null) {
                if (!world.isClient) {
                    world.setBlockState(blockPos, blockState3, Block.NOTIFY_ALL or Block.REDRAW_ON_MAIN_THREAD)
                    world.emitGameEvent(
                        GameEvent.BLOCK_CHANGE,
                        blockPos,
                        GameEvent.Emitter.of(playerEntity, blockState3)
                    )
                    if (playerEntity != null) {
                        context.stack.damage(
                            1,
                            playerEntity
                        ) { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) }
                    }
                }

                return true
            } else {
                return false
            }
        }
    }

    private fun getStrippedState(state: BlockState): Optional<BlockState> {
        return Optional.ofNullable<Block?>(STRIPPED_BLOCKS[state.block])
            .map(Function { block: Block? ->
                block!!.defaultState.with(
                    PillarBlock.AXIS,
                    state.get(PillarBlock.AXIS)
                )
            })
    }
}