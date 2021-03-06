package com.InfinityRaider.AgriCraft.compatibility.witchery;

import com.InfinityRaider.AgriCraft.apiimpl.v1.cropplant.CropPlantGeneric;
import com.emoniph.witchery.Witchery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemSeeds;

public class CropPlantWitchery extends CropPlantGeneric {
    public CropPlantWitchery(ItemSeeds seed) {
        super(seed);
    }

    @Override
    public int transformMeta(int growthStage) {
        return growthStage==7?4:(int) (((float) growthStage)/2.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderAsFlower() {
        return getSeed().getItem()!= Witchery.Items.SEEDS_BELLADONNA;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getInformation() {
        String name = getSeed().getUnlocalizedName();
        name = name.substring(name.indexOf(':')+1);
        int index = name.indexOf("seeds");
        name = index<0?name:name.substring(index+"seeds".length());
        return "agricraft_journal.wi_"+Character.toUpperCase(name.charAt(0))+name.substring(1);
    }
}
