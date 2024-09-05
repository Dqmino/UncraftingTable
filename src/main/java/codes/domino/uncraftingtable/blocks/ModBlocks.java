package codes.domino.uncraftingtable.blocks;

import codes.domino.uncraftingtable.UncraftingTable;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static void initialize() {
    }

    public static final Block UNCRAFTING_TABLE = register(new UncraftingTableBlock(BlockBehaviour.Properties.of()
            .sound(SoundType.WOOD)
                    .ignitedByLava()),
            "uncrafting_table",
            true);

    private static Block register(Block block, String name, boolean shouldRegisterItem) {
        String id = UncraftingTable.MOD_ID + ":" + name;
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }
        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }
}