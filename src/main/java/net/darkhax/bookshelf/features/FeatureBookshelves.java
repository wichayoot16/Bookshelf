package net.darkhax.bookshelf.features;

import net.darkhax.bookshelf.block.BlockWoodenShelf;
import net.darkhax.bookshelf.item.ItemBlockBasic;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FeatureBookshelves extends Feature {
    
    private boolean enabled = true;
    private boolean allowCrafting = true;
    
    private final Block blockShelf = new BlockWoodenShelf();
    
    @Override
    public void onPreInit () {
        
        if (this.enabled) {
            
            GameRegistry.register(this.blockShelf);
            GameRegistry.register(new ItemBlockBasic(this.blockShelf, BlockWoodenShelf.types, true));
            
            if (this.allowCrafting)
                for (int meta = 1; meta < 6; meta++)
                    GameRegistry.addShapedRecipe(new ItemStack(this.blockShelf, 1, meta - 1), new Object[] { "xxx", "yyy", "xxx", Character.valueOf('x'), new ItemStack(Blocks.PLANKS, 1, meta), Character.valueOf('y'), Items.BOOK });
        }
    }
    
    @Override
    public void setupConfig (Configuration config) {
        
        this.enabled = config.getBoolean("Enabled", "Bookshelves", true, "While enabled, new bookshelf varients for the other wood types will be added to the game");
        this.allowCrafting = config.getBoolean("Crafting", "Bookshelves", true, "Should the new varients be craftable?");
    }
    
    @Override
    public void setupRendering () {
        
        final Item item = Item.getItemFromBlock(this.blockShelf);
        
        for (int meta = 0; meta < 5; meta++)
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation("bookshelf:bookshelf_" + BlockWoodenShelf.types[meta], "inventory"));
    }
}