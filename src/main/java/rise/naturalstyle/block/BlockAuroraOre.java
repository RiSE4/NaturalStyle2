package rise.naturalstyle.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rise.naturalstyle.init.ItemRegistry;
import rise.naturalstyle.util.JsonHelper;
import rise.naturalstyle.util.ToolType;

import java.util.Random;

public class BlockAuroraOre extends BaseBlock {

    public BlockAuroraOre(String name)
    {
        super(name, Material.ROCK, SoundType.STONE, 3.5F, 10F);
        this.setHarvestLevel(ToolType.PICKAXE, 3);
        this.register();
        JsonHelper.INSTANCE.registerJson(this, JsonHelper.JsonType.SIMPLE_BLOCK_STATE, this.getUnlocalizedName());
        JsonHelper.INSTANCE.registerJson(this, JsonHelper.JsonType.SIMPLE_BLOCK, this.getUnlocalizedName());
        JsonHelper.INSTANCE.registerJson(this, JsonHelper.JsonType.ITEM_BLOCK, this.getUnlocalizedName());
    }

    public Item getItemDropped(IBlockState state, Random random, int fortune)
    {
        return ItemRegistry.AURORA_GEM;
    }

    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), random, fortune))
        {
            int i = random.nextInt(fortune + 2) - 1;

            if (i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : new Random();

        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
        {
            int i = MathHelper.getInt(rand, 0, 2);

            return i;
        }
        return 0;
    }
    public ItemStack getItem(World world, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this);
    }
}
