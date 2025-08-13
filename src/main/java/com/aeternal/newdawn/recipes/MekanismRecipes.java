package com.aeternal.newdawn.recipes;
import com.denfop.IUItem;
import com.denfop.blocks.FluidName;
import com.denfop.blocks.mechanism.BlockBaseMachine3;
import com.denfop.items.resource.ItemCraftingElements;
import mekanism.api.gas.GasStack;
import mekanism.common.MekanismFluids;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import static com.aeternal.newdawn.recipes.RecipeHelper.*;
import static com.aeternal.newdawn.recipes.RecipeHelper.getIUfluid;

public class MekanismRecipes {



    public static void initDigitalAssembly() {
//        new RecipeHelper.DATRecipe()
//                .inputs(
//                        slot1, slot2, slot3,
//                        slot4, slot5, slot6,
//                        slot7, slot8, slot9)
//                .fluidIn()
//                .gasIn()
//                .output()
//                .fluidOut()
//                .gasOut()
//                .powerPerTick()        //  EF/RF
//                .duration()           // s @ 20tps
//                .register();
        //FluidStack  = new FluidStack(FluidName..getInstance(),);
        //GasStack = new GasStack(MekanismFluids.,);

        FluidStack waterIn = new FluidStack(FluidRegistry.WATER, 2000);
        FluidStack lavaOut = new FluidStack(FluidRegistry.LAVA,  500);


        FluidStack test = new FluidStack(FluidName.fluidcryogen.getInstance(),1);
        GasStack test1 = new GasStack(MekanismFluids.Water,1);

        new RecipeHelper.DATRecipe()
                .inputs(getIUElement(501,4), getIUElement(52,4), getIUElement(501,4),
                        getIUElement(677,2), getIUBr("advanced_machine",3), getIUElement(243,2),
                        getIUElement(501,4), getIUItemBase("motors_with_improved_bearings_", 3),getIUElement(501,4))
                .fluidIn(getIUfluid("fluiddimethylhydrazine",25000))
                .gasIn(getMekGas("Lithium",5000))
                .output(getIUBaseMachine("probe_assembler", 1))
                .fluidOut(getIUfluid("fluidnitricoxide",5000))   // leftover
                .gasOut(getMekGas("Oxygen",1000))          // byproduct
                .powerPerTick(6000)
                .duration(6000)
                .register();




        new RecipeHelper.DATRecipe()
                .inputs(
                        getIUElement(501,4), getIUItemBase("motors_with_improved_bearings_", 1), getIUElement(501,4),
                        getIUElement(684,1), getIUBr("advanced_machine",3), getIUElement(680,2),
                        getIUElement(501,4), getIUItemBase("motors_with_improved_bearings_", 1), getIUElement(501,4))
                .fluidIn(getIUfluid("fluidcryogen",10000))
                .gasIn(getMekGas("Hydrogen",100000))
                .output(getIUBaseMachine("rocket_launch_pad",1))
                .fluidOut(new FluidStack(FluidRegistry.WATER, 2000))
                .gasOut(getMekGas("Water",20000))
                .powerPerTick(13333)        // 20M EF/ 80M RF
                .duration(6000)           // 300s @ 20tps
                .register();

        new RecipeHelper.DATRecipe()
                .inputs(
                        slot1, slot2, slot3,
                        slot4, slot5, slot6,
                        slot7, slot8, slot9)
                .fluidIn()
                .gasIn()
                .output()
                .fluidOut()
                .gasOut()
                .powerPerTick()        //  EF/RF
                .duration()           // s @ 20tps
                .register();

        new RecipeHelper.DATRecipe()
                .inputs(
                        slot1, slot2, slot3,
                        slot4, slot5, slot6,
                        slot7, slot8, slot9)
                .fluidIn()
                .gasIn()
                .output()
                .fluidOut()
                .gasOut()
                .powerPerTick()        //  EF/RF
                .duration()           // s @ 20tps
                .register();
    }

    private static @NotNull ItemStack getIuElement() {
        return getIUElement(501, 4);
    }

}
