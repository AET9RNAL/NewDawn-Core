package com.aeternal.newdawn.recipes;

import com.denfop.IUItem;
import com.denfop.blocks.BlockResource;
import com.denfop.blocks.FluidName;
import com.denfop.blocks.IUFluid;
import com.denfop.blocks.mechanism.BlockBaseMachine3;
import com.denfop.items.IUItemBase;
import com.denfop.items.resource.ItemCraftingElements;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * General-purpose helpers for Mekanism's Digital Assembly Table recipes.
 * - Accepts ItemStack, null/EMPTY, or ore dict key String per slot.
 * - Expands ore dict keys into all variants (cross-product if multiple slots are ores).
 * - Pads to 9 slots automatically.
 *
 * Usage example (register during postInit or later):
 *
 *   new RecipeHelper.DATRecipe()
 *       .inputs("ingotSteel", new ItemStack(Items.DIAMOND), null, null, null, null, null, null, null)
 *       .fluidIn(new FluidStack(FluidRegistry.WATER, 1000))
 *       .gasIn(new GasStack(MyGases.OXYGEN, 500))
 *       .output(new ItemStack(MyModItems.ASSEMBLED_CORE))
 *       .powerPerTick(120)
 *       .duration(2000)
 *       .register();
 */
public final class RecipeHelper {
    private RecipeHelper() {}

    public static GasStack getMekGas(String name, int amount) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("getMekGas: gas name is empty");
        }
        Gas gas = GasRegistry.getGas(name.toLowerCase(Locale.ROOT)); // e.g. "lithium"
        if (gas == null) {
            throw new IllegalArgumentException("Unknown Mekanism gas: " + name);
        }
        return new GasStack(gas, Math.max(1, amount));
    }

    // (optional) default amount = 1
    public static GasStack getMekGas(String name) {
        return getMekGas(name, 1);
    }

    public static FluidStack getIUfluid(String fieldName, int amount) {
        try {
            Field f = FluidName.class.getField(fieldName);       // e.g. "fluiddimethylhydrazine"
            Object holder = f.get(null);                         // enum/holder instance
            Method getInstance = holder.getClass().getMethod("getInstance");
            Fluid fluid = (Fluid) getInstance.invoke(holder);    // FluidName.*.getInstance()
            return new FluidStack(fluid, Math.max(1, amount));
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown IU FluidName field: " + fieldName, e);
        }
    }
    public static ItemStack getIUBr(String name, int count) {
        BlockResource.Type type = BlockResource.Type.valueOf(name.toLowerCase(Locale.US));
        return new ItemStack(Item.getItemFromBlock(IUItem.blockResource), count, type.getMetadata());
    }

    public static ItemStack getIUItemBase(String name, int count) {
        try {
            IUItemBase item = (IUItemBase) IUItem.class.getField(name).get(null);
            return new ItemStack(item, count);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
public static ItemStack getIUBaseMachine(String name, int count) {
    if (name == null || name.trim().isEmpty() || IUItem.basemachine2 == null) return ItemStack.EMPTY;

    // Resolve enum constant by normalized name
    final BlockBaseMachine3 variant = resolveEnum(BlockBaseMachine3.class, name);
    if (variant == null) {
        throw new IllegalArgumentException("Unknown IU basemachine2 variant: \"" + name + "\"");
    }

    final Object block = IUItem.basemachine2; // BlockTileEntity (the block instance)

    // 1) Try: getItemStack(BlockBaseMachine3)
    ItemStack s = invokeItemStack(block, "getItemStack", new Class<?>[]{BlockBaseMachine3.class}, new Object[]{variant});
    if (s != null) { s.setCount(Math.max(1, count)); return s; }

    // 2) Try: getState(BlockBaseMachine3) -> getItemStack(IBlockState)
    IBlockState st = invokeState(block, "getState", new Class<?>[]{BlockBaseMachine3.class}, new Object[]{variant});
    if (st != null) {
        s = invokeItemStack(block, "getItemStack", new Class<?>[]{IBlockState.class}, new Object[]{st});
        if (s != null) { s.setCount(Math.max(1, count)); return s; }
    }

    // 3) Fallback: meta
    return new ItemStack(Item.getItemFromBlock(IUItem.basemachine2), Math.max(1, count), variant.getId());
}

    @Nullable
    private static ItemStack invokeItemStack(Object target, String name, Class<?>[] sig, Object[] args) {
        try {
            Method m = target.getClass().getMethod(name, sig);
            Object v = m.invoke(target, args);
            return (v instanceof ItemStack) ? ((ItemStack) v).copy() : null;
        } catch (Exception ignore) {
            return null;
        }
    }

    @Nullable
    private static IBlockState invokeState(Object target, String name, Class<?>[] sig, Object[] args) {
        try {
            Method m = target.getClass().getMethod(name, sig);
            Object v = m.invoke(target, args);
            return (v instanceof IBlockState) ? (IBlockState) v : null;
        } catch (Exception ignore) {
            return null;
        }
    }

    @Nullable
    private static <E extends Enum<E>> E resolveEnum(Class<E> enumCls, String raw) {
        String key = raw.trim().toUpperCase(Locale.ROOT).replace('-', '_').replace(' ', '_').replaceAll("^_+|_+$", "");
        for (E e : enumCls.getEnumConstants()) {
            if (e.name().equalsIgnoreCase(key)) return e;
        }
        return null;
    }


    public static ItemStack getIUElement(int n) {
        ItemStack s = new ItemStack(IUItem.crafting_elements, 1,
                ItemCraftingElements.Types.getFromID(n).getId());
        return s;
    }

    public static ItemStack getIUElement(int n, int count) {
        ItemStack s = getIUElement(n);
        if (!s.isEmpty()) s.setCount(Math.max(1, count));
        return s;
    }
    /** Fluent builder for a single Digital Assembly Table recipe (with ore expansion). */
    public static final class DATRecipe {
        // Each slot can be: ItemStack, String (ore key), List<ItemStack>, or null/EMPTY
        private final List<Object> slots = new ArrayList<>(9);
        private @Nullable FluidStack fluidIn;
        private @Nullable GasStack gasIn;
        private ItemStack output = ItemStack.EMPTY;
        private @Nullable FluidStack fluidOut;
        private @Nullable GasStack gasOut;
        private int powerPerTick = 100;
        private int durationTicks = 200;

        /** Provide up to 9 inputs; missing ones are padded as EMPTY. */
        public DATRecipe inputs(Object... nineOrFewer) {
            slots.clear();
            if (nineOrFewer != null) {
                Collections.addAll(slots, nineOrFewer);
            }
            return this;
        }

        /** Set a single slot (0..8). Accepts ItemStack, String(ore key), List<ItemStack>, or null. */
        public DATRecipe slot(int indexZeroToEight, @Nullable Object spec) {
            ensureCapacity(slots, 9);
            slots.set(indexZeroToEight, spec);
            return this;
        }

        public DATRecipe fluidIn(@Nullable FluidStack f)  { this.fluidIn = cloneOrNull(f); return this; }
        public DATRecipe gasIn(@Nullable GasStack g)      { this.gasIn   = cloneOrNull(g); return this; }
        public DATRecipe output(ItemStack out)            { this.output  = safeCopy(out);  return this; }
        public DATRecipe fluidOut(@Nullable FluidStack f) { this.fluidOut= cloneOrNull(f); return this; }
        public DATRecipe gasOut(@Nullable GasStack g)     { this.gasOut  = cloneOrNull(g); return this; }
        public DATRecipe powerPerTick(int p)              { this.powerPerTick = p;         return this; }
        public DATRecipe duration(int ticks)              { this.durationTicks = ticks;    return this; }

        /** Expands ore-keys (cartesian) and registers 1 recipe per concrete variant. */
        public void register() {
            if (output == null || output.isEmpty()) {
                throw new IllegalArgumentException("DATRecipe.output must be a non-empty ItemStack");
            }
            List<List<ItemStack>> expanded = expandAllSlotsToStacks(padToNine(slots));
            // Cartesian product across 9 slots:
            backtrackRegister(0, new ItemStack[9], expanded, fluidIn, gasIn, output, fluidOut, gasOut, powerPerTick, durationTicks);
        }
    }

    // ---------- Internals ----------

    private static void backtrackRegister(
            int idx,
            ItemStack[] chosen,
            List<List<ItemStack>> expanded,
            @Nullable FluidStack fin, @Nullable GasStack gin,
            ItemStack out,
            @Nullable FluidStack fout, @Nullable GasStack gout,
            int ppt, int dur
    ) {
        if (idx == 9) {
            // Call Mekanism’s recipe registration
            RecipeHandler.addDigitalAssemblyTableRecipe(
                    chosen[0], chosen[1], chosen[2], chosen[3], chosen[4], chosen[5], chosen[6], chosen[7], chosen[8],
                    cloneOrNull(fin), cloneOrNull(gin),
                    safeCopy(out),
                    cloneOrNull(fout), cloneOrNull(gout),
                    ppt, dur
            );
            return;
        }
        for (ItemStack option : expanded.get(idx)) {
            chosen[idx] = option;
            backtrackRegister(idx + 1, chosen, expanded, fin, gin, out, fout, gout, ppt, dur);
        }
    }

    /** Expand each slot spec to a list of concrete ItemStacks. */
    private static List<List<ItemStack>> expandAllSlotsToStacks(List<Object> nine) {
        List<List<ItemStack>> out = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            Object spec = (i < nine.size()) ? nine.get(i) : null;
            out.add(expandSlot(spec));
        }
        return out;
    }

    /** One slot expansion: ItemStack -> [copy]; String oreKey -> OreDictionary variants; List<ItemStack> -> copies; null/EMPTY -> [EMPTY]. */
    private static List<ItemStack> expandSlot(@Nullable Object spec) {
        if (spec == null) return Collections.singletonList(ItemStack.EMPTY);

        if (spec instanceof ItemStack) {
            ItemStack s = (ItemStack) spec;
            return Collections.singletonList(safeCopy(s));
        }
        if (spec instanceof String) {
            String oreKey = (String) spec;
            List<ItemStack> ores = OreDictionary.getOres(oreKey, false);
            List<ItemStack> copies = new ArrayList<>(Math.max(1, ores.size()));
            if (ores.isEmpty()) {
                // Keep it EMPTY so missing ores don't crash registration
                copies.add(ItemStack.EMPTY);
            } else {
                for (ItemStack s : ores) {
                    if (s != null && !s.isEmpty()) copies.add(safeCopy(s));
                }
            }
            return copies.isEmpty() ? Collections.singletonList(ItemStack.EMPTY) : copies;
        }
        if (spec instanceof List) {
            @SuppressWarnings("unchecked")
            List<ItemStack> list = (List<ItemStack>) spec;
            List<ItemStack> copies = new ArrayList<>(list.size());
            for (ItemStack s : list) {
                if (s == null) continue;
                copies.add(safeCopy(s));
            }
            return copies.isEmpty() ? Collections.singletonList(ItemStack.EMPTY) : copies;
        }

        throw new IllegalArgumentException("Unsupported slot spec type: " + spec.getClass());
    }

    private static List<Object> padToNine(List<Object> src) {
        List<Object> dst = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            dst.add(i < (src == null ? 0 : src.size()) ? src.get(i) : null);
        }
        return dst;
    }

    private static void ensureCapacity(List<?> list, int size) {
        while (list.size() < size) {
            //noinspection unchecked
            ((List<Object>) list).add(null);
        }
    }

    private static ItemStack safeCopy(@Nullable ItemStack in) {
        if (in == null) return ItemStack.EMPTY;
        if (in.isEmpty()) return ItemStack.EMPTY;
        ItemStack copy = in.copy();
        // Defensive: copy NBT if present (copy() already does, but keep explicit when mutating)
        if (copy.getTagCompound() != null) {
            copy.setTagCompound(copy.getTagCompound().copy());
        }
        return copy;
    }

    private static @Nullable FluidStack cloneOrNull(@Nullable FluidStack f) {
        return f == null ? null : f.copy();
    }

    private static @Nullable GasStack cloneOrNull(@Nullable GasStack g) {
        return g == null ? null : g.copy();
    }

    /** Optional: set an integer NBT on a stack copy (for mods that match by NBT). */
    public static ItemStack withIntTag(ItemStack base, String key, int value) {
        ItemStack c = safeCopy(base);
        NBTTagCompound tag = c.hasTagCompound() ? c.getTagCompound() : new NBTTagCompound();
        tag.setInteger(key, value);
        c.setTagCompound(tag);
        return c;
    }
}
