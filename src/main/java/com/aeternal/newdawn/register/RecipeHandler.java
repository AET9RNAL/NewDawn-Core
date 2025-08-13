package com.aeternal.newdawn.register;

import com.aeternal.newdawn.Constants;
import com.aeternal.newdawn.recipes.MekanismRecipes;

public class RecipeHandler {

    public static void init() {

        if (Constants.AS_LOADED) {

        }
        if (Constants.DIV_LOADED) {

        }
        if (Constants.FO_LOADED && Constants.EXBEES_LOADED) {

        }
        if (Constants.DE_LOADED) {

        }
        if (Constants.ME_LOADED) {
            MekanismRecipes.initDigitalAssembly();
        }

    }

}
