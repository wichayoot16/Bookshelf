package net.darkhax.bookshelf.creativetab;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.bookshelf.handler.SupporterHandler;
import net.darkhax.bookshelf.handler.SupporterHandler.SupporterData;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.lib.util.PlayerUtils;
import net.darkhax.bookshelf.lib.util.SkullUtils;
import net.darkhax.bookshelf.lib.util.SkullUtils.MHFAccount;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabSkulls extends CreativeTabs {
    
    private static ItemStack displayStack = null;
    
    /**
     * A cache of all the player skull ItemStacks so they don't need to be recreated.
     */
    private static List<ItemStack> cache = new ArrayList<ItemStack>();
    
    public CreativeTabSkulls() {
        
        super("bookshelfheads");
        
        this.setBackgroundImageName("item_search.png");
        displayStack = SkullUtils.createSkull("Darkhax");
        
        for (final SupporterData data : SupporterHandler.getSupporters())
            if (data.wantsHead()) {
                
                final ItemStack stack = SkullUtils.createSkull(PlayerUtils.getPlayerNameFromUUID(data.getPlayerID()));
                ItemStackUtils.setLore(stack, new String[] { data.getLocalizedType() });
                cache.add(stack);
            }
            
        for (final MHFAccount mhf : MHFAccount.values())
            cache.add(SkullUtils.createSkull(mhf));
    }
    
    @Override
    public Item getTabIconItem () {
        
        return Items.SKULL;
    }
    
    @Override
    public ItemStack getIconItemStack () {
        
        return displayStack;
    }
    
    @Override
    public boolean hasSearchBar () {
        
        return true;
    }
    
    @Override
    public void displayAllRelevantItems (List<ItemStack> itemList) {
        
        System.out.println(cache.size());
        itemList.addAll(cache);
    }
}