package net.fabric.aiottools

import net.fabric.aiottools.item.ModItemGroups
import net.fabric.aiottools.item.ModItems
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object AiotTools : ModInitializer {
	val MOD_ID: String = "aiottools"
    val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ModItemGroups.registerItemGroups()
		ModItems.registerModItems()

		LOGGER.info("Hello Fabric world!")
	}
}