package rise.naturalstyle.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rise.naturalstyle.NaturalStyle;
import rise.naturalstyle.util.*;

public class BaseBlock extends Block implements IContent {

    private AxisAlignedBB boundingBox = AABBList.AABB_FULL;
    private AxisAlignedBB collisionBox = null;

    public BaseBlock(String name, Material material, SoundType sound, float hardness, float resistance)
    {
        super(material);
        this.setUnlocalizedName(name);
        this.setRegistryName(NaturalStyle.MOD_ID, name);
        this.setCreativeTab(NaturalStyle.TAB_NS);
        this.setSoundType(sound);
        this.setHardness(hardness);
        this.setResistance(resistance);
    }

    public BaseBlock register(ItemBlock itemBlock)
    {
        Helper.registerBlock(this, itemBlock);
        return this;
    }

    public BaseBlock register()
    {
        Helper.registerBlock(this);
        return this;
    }

    @Override
    public void registerModels()
    {
        NaturalStyle.proxy.registerItemModel(Item.getItemFromBlock(this), 0);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public BaseBlock setBoundingBox(AxisAlignedBB aabb)
    {
        this.boundingBox = aabb;
        return this;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return this.boundingBox;
    }

    public BaseBlock setCollisionBox(AxisAlignedBB aabb)
    {
        this.collisionBox = aabb;
        return this;
    }

    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return this.collisionBox != null ? this.collisionBox : super.getCollisionBoundingBox(state, world, pos);
    }

    public BaseBlock setHarvestLevel(ToolType type, int level)
    {
        String name = type.toString().toLowerCase();
        this.setHarvestLevel(name, level);
        LogHelper.debugInfoLog("ToolType toString :: " + name);
        return this;
    }
}