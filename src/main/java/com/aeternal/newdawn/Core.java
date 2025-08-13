package com.aeternal.newdawn;


import com.aeternal.newdawn.proxy.CommonProxy;
import com.aeternal.newdawn.register.RecipeHandler;
import mekanism.multiblockmachine.common.MekanismMultiblockMachineRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"ALL", "UnnecessaryFullyQualifiedName"})
@Mod.EventBusSubscriber
@Mod(modid = Constants.MOD_ID,
        name = Constants.MOD_NAME,
        dependencies = Constants.MOD_DEPS,
        version = Constants.MOD_VERSION,
        acceptedMinecraftVersions = "[1.12,1.12.2]")
public final class Core {

    //public static final CreativeTabs NDTab = new TabCore(0, "IU:AdditionsTab");

    public static final List<ItemStack> list = new ArrayList<>();


    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID);

    @SidedProxy(
            clientSide = "com.aeternal.newdawn.proxy.ClientProxy",
            serverSide = "com.aeternal.newdawn.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance("newdawn")
    public static Core instance;

    public static ResourceLocation getIdentifier(final String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    @Mod.EventHandler
    public void load(final FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        Config.loadNormalConfig(event.getSuggestedConfigurationFile());
        proxy.preInit(event);
        if(Constants.DE_LOADED) {
        }
        if(Constants.BA_LOADED) {
        }
        if (event.getSide() == Side.CLIENT) {
            if(Constants.AS_LOADED) {
            }
            if(Constants.DIV_LOADED) {
            }
            if(Constants.FO_LOADED) {
            }
            if(Constants.DE_LOADED) {
            }
            if(Constants.BA_LOADED) {
            }

        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        registerOreDict();
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        proxy.postInit(event);
        RecipeHandler.init();
    }

    public static void registerOreDict() {

        if (Constants.DIV_LOADED) {

        }
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

    }
}











