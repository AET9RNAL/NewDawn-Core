package com.aeternal.newdawn.recipes;
import com.denfop.blocks.FluidName;
import mekanism.api.gas.GasStack;
import mekanism.common.MekanismFluids;
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


        FluidStack test = new FluidStack(FluidName.fluidethanol.getInstance(),1);
        GasStack test1 = new GasStack(MekanismFluids.SpentNuclearWaste,1);

        //Space Research table
        new RecipeHelper.DATRecipe()
                .inputs(
                        getIUElement(274,4), getIUElement(44,1), getIUElement(274,4),
                        getIUElement(683,1), getIUBr("machine",1), getIUElement(678,1),
                        getIUElement(274,4), getIUGear(4,3), getIUElement(274,4))
                .fluidIn(getIUfluid("fluidindustrialoil",5000))
                .gasIn(getMekGas("Hydrogen", 10000))
                .output(getIUBaseMachine("research_table_space",1))
                .fluidOut(getIUfluid("fluidcreosote",1000))
                .gasOut(getMekGas("Water",1000))
                .powerPerTick(16666)        // 5M EF/RF 20M RF
                .duration(1200)           // 60s @ 20tps
                .register();

        //Rover Assembler
        new RecipeHelper.DATRecipe()
                .inputs(
                        getIUElement(501,4), getIUElement(52,4), getIUElement(501,4),
                        getIUElement(681,2), getIUBr("advanced_machine",3), getIUElement(243,2),
                        getIUElement(501,4), getIUItemBase("motors_with_improved_bearings_", 2), getIUElement(501,4))
                .fluidIn(getIUfluid("fluiddimethylhydrazine",15000))
                .gasIn(getMekGas("Lithium",5000))
                .output(getIUBaseMachine("rover_assembler",1))
                .fluidOut(getIUfluid("fluidnitricoxide",3000))
                .gasOut(getMekGas("Oxygen",1000))
                .powerPerTick(6000)        // 9M EF/RF 36M
                .duration(6000)           // 300s @ 20tps
                .register();

        //Probe Assembler
        new RecipeHelper.DATRecipe()
                .inputs(getIUElement(501,4), getIUElement(52,4), getIUElement(501,4),
                        getIUElement(677,2), getIUBr("advanced_machine",3), getIUElement(243,2),
                        getIUElement(501,4), getIUItemBase("motors_with_improved_bearings_", 3),getIUElement(501,4))
                .fluidIn(getIUfluid("fluiddimethylhydrazine",25000))
                .gasIn(getMekGas("Lithium",5000))
                .output(getIUBaseMachine("probe_assembler", 1))
                .fluidOut(getIUfluid("fluidnitricoxide",5000))   // leftover
                .gasOut(getMekGas("Oxygen",1000))          // byproduct
                .powerPerTick(10000)        // 18M EF/RF 72M
                .duration(7200)        // 360s @ 20tps
                .register();

        //Rocket Assembler
        new RecipeHelper.DATRecipe()
                .inputs(
                        getIUDoublePlate(26,4), getIUElement(52,4), getIUDoublePlate(26,4),
                        getIUElement(684,2), getIUBr("advanced_machine",3), getIUElement(243,2),
                        getIUDoublePlate(26,4), getIUItemBase("motors_with_improved_bearings_", 2), getIUDoublePlate(26,4))
                .fluidIn(getIUfluid("fluidmotoroil",60000))
                .gasIn(getMekGas("FusionFuel",30000))
                .output(getIUBaseMachine("rocket_assembler",1))
                .fluidOut(getIUfluid("fluidblackoil",3000))
                .gasOut(getMekGas("SpentNuclearWaste",3000))
                .powerPerTick(12500)        // 30M EF/RF 120M
                .duration(9600)           // 480s @ 20tps
                .register();

        //Satellite Assembler
        new RecipeHelper.DATRecipe()
                .inputs(
                        getIUAlloysDoublePlate(22,4), getIUElement(52,4), getIUAlloysDoublePlate(10,8),
                        getIUElement(688,2), getIUBr("advanced_machine",3), getIUElement(243,2),
                        getIUAlloysDoublePlate(8,8), getIUItemBase("motors_with_improved_bearings_", 4), getIUAlloysDoublePlate(22,4))
                .fluidIn(getIUfluid("fluidethanol",60000))
                .gasIn(getMekGas("FusionFuel",5000))
                .output(getIUBaseMachine("satellite_assembler",1))
                .fluidOut(new FluidStack(FluidRegistry.WATER, 12000))
                .gasOut(getMekGas("SpentNuclearWaste",500))
                .powerPerTick(12500)        // 30M EF/RF 120M
                .duration(9600)           // 480s @ 20tps
                .register();

        //Launch Pad
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




    }

    private static @NotNull ItemStack getIuElement() {
        return getIUElement(501, 4);
    }

}
