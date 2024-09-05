package codes.domino.uncraftingtable;

import codes.domino.uncraftingtable.blocks.ModBlocks;
import net.fabricmc.api.ModInitializer;

public class UncraftingTable implements ModInitializer {
    public static final String MOD_ID = "uncraftingtable";

    @Override
    public void onInitialize() {
        ModBlocks.initialize();
    }
}