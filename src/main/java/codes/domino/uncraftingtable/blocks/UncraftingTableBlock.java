package codes.domino.uncraftingtable.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class UncraftingTableBlock extends Block {

    public UncraftingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hand == InteractionHand.OFF_HAND) {
            return ItemInteractionResult.FAIL;
        }

        for (RecipeHolder<?> recipeHolder : level.getRecipeManager().getRecipes()) {
            Recipe<?> recipe = recipeHolder.value();
            ItemStack recipeResult = getRecipeResult(recipe, level);
            boolean isSameItemAsPlayerItem = recipeResult.getItem().equals(stack.getItem());
            if (!isSameItemAsPlayerItem) {
                continue;
            }
            if (recipe.getType() != RecipeType.CRAFTING) {
                return ItemInteractionResult.FAIL;
            }
            if (circularRecipeExists(level, recipe, stack)) continue;

            for (Ingredient ingredient : recipe.getIngredients()) {
                for (ItemStack item : ingredient.getItems()) {
                    item = item.copy();
                    if (!player.getInventory().add(item)) {
                        player.drop(item, false);
                    }
                }
            }
            stack.shrink(getRecipeResult(recipe, level).getCount());
            break;
        }
        return ItemInteractionResult.SUCCESS;
    }
    private boolean circularRecipeExists(Level level, Recipe<?> recipe, ItemStack item) {
        return getRecipeResult(recipe, level).getCount() > item.getCount();
    }

    private static ItemStack getRecipeResult(Recipe<?> recipe, Level level) {
        return recipe.getResultItem(level.registryAccess());
    }
}
