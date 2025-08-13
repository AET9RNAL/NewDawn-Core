package com.aeternal.newdawn.proxy;

import com.aeternal.newdawn.Constants;
import com.aeternal.newdawn.api.IModelRegister;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;

public class ClientProxy extends CommonProxy {

    public static final ArrayList<IModelRegister> modelList = new ArrayList<>();
    public boolean addIModelRegister(IModelRegister modelRegister) {
        return modelList.add(modelRegister);
    }

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        for (IModelRegister register : modelList) {
            register.registerModels();
        }


        if(Constants.AS_LOADED){

        }
        if(Constants.DIV_LOADED){

        }

    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

    }
}
