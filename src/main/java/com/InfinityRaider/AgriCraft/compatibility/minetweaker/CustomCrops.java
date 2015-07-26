package com.InfinityRaider.AgriCraft.compatibility.minetweaker;

import com.InfinityRaider.AgriCraft.api.v1.BlockWithMeta;
import com.InfinityRaider.AgriCraft.api.v1.RenderMethod;
import com.InfinityRaider.AgriCraft.blocks.BlockModPlant;
import com.InfinityRaider.AgriCraft.items.ItemModSeed;
import com.InfinityRaider.AgriCraft.utility.exception.MissingArgumentsException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.block.IBlock;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.client.renderer.entity.Render;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * Support for adding custom crops using MT instead of the config files.
 * All of the text
 * @author ben
 *
 */
@ZenClass("mods.agricraft.CustomCrops")
public class CustomCrops {
	private static final class AddCustomCropAction extends OneWayAction {
		private String name;
		private IItemStack fruit;
		private IBlock soil;
		private IBlock base;
		private int tier;
		private int renderMethod;
		private IItemStack shearGain;
		private String info;

		public AddCustomCropAction(String name, IItemStack fruit, IBlock soil, IBlock base,
				int tier, int renderMethod, IItemStack shearGain, String info) {
					this.name = name;
			// TODO Auto-generated constructor stub
					this.fruit = fruit;
					this.soil = soil;
					this.base = base;
					this.tier = tier;
					this.renderMethod = renderMethod;
					this.shearGain = shearGain;
					this.info = info;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding custom crop " + name;
		}

		@Override
		public void apply() {
			try {
				// TODO Make sure this actually works
				BlockModPlant bmp = new BlockModPlant(new Object[] {
						name, MineTweakerMC.getItemStack(fruit), MineTweakerMC.getBlock(soil),
						new BlockWithMeta(MineTweakerMC.getBlock(base), base.getMeta()), tier, RenderMethod.getRenderMethod(renderMethod),
						MineTweakerMC.getItemStack(shearGain)
				});
				ItemModSeed ims = bmp.getSeed();
				LanguageRegistry.addName(bmp, Character.toUpperCase(name.charAt(0))+name.substring(1));
                LanguageRegistry.addName(ims, Character.toUpperCase(name.charAt(0))+name.substring(1) + " Seeds");
                if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                    ims.setInformation(info);
                }
			} catch (MissingArgumentsException e) {
				MineTweakerAPI.logWarning("missing arguments to adding custom agricraft plant");
			}
		}
	}

	public static void addCrop(String name, IItemStack fruit, @Optional IBlock soil,
			@Optional IBlock base, int tier, int renderMethod, @Optional IItemStack shearGain, String info) {
		MineTweakerAPI.apply(new AddCustomCropAction(name, fruit, soil, base, tier, renderMethod, shearGain, info));
	}
}
