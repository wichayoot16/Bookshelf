package net.darkhax.bookshelf.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockShelves extends Block {
    
    public static String[] types = new String[] { "spruce", "birch", "jungle", "acacia", "dark_oak" };
    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType> create("variant", EnumType.class);
    
    public BlockShelves() {
        
        super(Material.wood);
        this.setHardness(1.5F);
        this.setStepSound(soundTypeWood);
        this.setUnlocalizedName("bookshelf.bookshelf");
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.SPRUCE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped (IBlockState state) {
        
        return ((EnumType) state.getValue(VARIANT)).getMetadata();
    }
    
    @Override
    public IBlockState getStateFromMeta (int meta) {
        
        return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
    }
    
    @Override
    public int getMetaFromState (IBlockState state) {
        
        return ((EnumType) state.getValue(VARIANT)).getMetadata();
    }
    
    @Override
    protected BlockState createBlockState () {
        
        return new BlockState(this, new IProperty[] { VARIANT });
    }
    
    @Override
    public int quantityDropped (Random random) {
        
        return 3;
    }
    
    @Override
    public Item getItemDropped (IBlockState state, Random rand, int fortune) {
        
        return Items.book;
    }
    
    @Override
    public float getEnchantPowerBonus (World world, BlockPos pos) {
        
        return 1;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (Item item, CreativeTabs tab, List<ItemStack> list) {
        
        for (int meta = 0; meta < 5; meta++)
            list.add(new ItemStack(item, 1, meta));
    }
    
    public static enum EnumType implements IStringSerializable {
        
        SPRUCE(0, "spruce"),
        BIRCH(1, "birch"),
        JUNGLE(2, "jungle"),
        ACACIA(3, "acacia"),
        DARK_OAK(4, "dark_oak");
        
        private static final EnumType[] META_LOOKUP = new EnumType[values().length];
        private final int meta;
        private final String name;
        
        private EnumType(int meta, String name) {
            
            this.meta = meta;
            this.name = name;
        }
        
        public int getMetadata () {
            
            return this.meta;
        }
        
        public String toString () {
            
            return this.name;
        }
        
        public static EnumType byMetadata (int meta) {
            
            if (meta < 0 || meta >= META_LOOKUP.length)
                meta = 0;
                
            return META_LOOKUP[meta];
        }
        
        public String getName () {
            
            return this.name;
        }
        
        static {
            
            for (EnumType type : values())
                META_LOOKUP[type.getMetadata()] = type;
        }
    }
}
