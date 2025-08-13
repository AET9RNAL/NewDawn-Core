package com.aeternal.newdawn.items;

import com.aeternal.newdawn.Constants;
import com.aeternal.newdawn.Core;
import com.aeternal.newdawn.api.IModelRegister;
import com.aeternal.newdawn.register.ItemHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

public class NdItemBase extends Item implements IModelRegister {

    private final String name;
    private final String path;

    public NdItemBase(String name) {
        this(name, "");
    }

    public NdItemBase(String name, String path) {
        super();
       // this.setCreativeTab(Core.IUATab);
        this.setMaxStackSize(64);

        this.name = name;
        this.path = path;
        setUnlocalizedName(name);
        ItemHandler.registerItem((Item) this, Core.getIdentifier(name)).setUnlocalizedName(name);
        Core.proxy.addIModelRegister(this);
    }

    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal(this.getUnlocalizedName(stack).replace("item.", "iua.") + ".name");
    }

    public String getUnlocalizedName() {
        return super.getUnlocalizedName() + ".name";
    }

    @Override
    public boolean hasEffect(@Nonnull final ItemStack stack) {
        return false;
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(
                this,
                0,
                new ModelResourceLocation(Constants.MOD_ID + ":" + path + this.name, null)
        );
    }

}
