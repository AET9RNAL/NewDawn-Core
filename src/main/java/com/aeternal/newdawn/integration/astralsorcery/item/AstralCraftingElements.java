package com.aeternal.newdawn.integration.astralsorcery.item;

import com.aeternal.newdawn.Constants;
import com.aeternal.newdawn.Core;
import com.aeternal.newdawn.api.IModelRegister;
import com.aeternal.newdawn.api.block.ISubEnum;
import com.aeternal.newdawn.items.resource.ItemSubTypes;
import com.aeternal.newdawn.register.ItemHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

public class AstralCraftingElements extends ItemSubTypes<AstralCraftingElements.AstralCraftingElementsTypes> implements IModelRegister {

    protected static final String NAME = "astral_element";

    public AstralCraftingElements() {
        super(AstralCraftingElements.AstralCraftingElementsTypes.class);
       // this.setCreativeTab(Core.IUATab);
        this.setMaxStackSize(64);
        ItemHandler.registerItem((Item) this, Core.getIdentifier(NAME)).setUnlocalizedName(NAME);
        Core.proxy.addIModelRegister(this);
    }

    public String getUnlocalizedName() {
        return Constants.MOD_ID + "." + super.getUnlocalizedName().substring(3);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel(Item stack, final int meta, final String extraName) {
        ModelLoader.setCustomModelResourceLocation(
                this,
                meta,
                new ModelResourceLocation(
                        Constants.MOD_ID + ":" + NAME + "_" + AstralCraftingElements.AstralCraftingElementsTypes.getFromID(meta).getName(),
                        null
                )
        );
    }

    public enum AstralCraftingElementsTypes implements ISubEnum {

        ;

        private final String name;
        private final int ID;

        AstralCraftingElementsTypes(final int ID) {
            this.name = this.name().toLowerCase(Locale.US);
            this.ID = ID;
        }

        public static AstralCraftingElements.AstralCraftingElementsTypes getFromID(final int ID) {
            return values()[ID % values().length];
        }

        public String getName() {
            return this.name;
        }

        public int getId() {
            return this.ID;
        }

        public static int getLength() {
            return values().length;
        }


    }
}
