package rise.naturalstyle.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import rise.naturalstyle.block.BaseBlock;
import rise.naturalstyle.block.BlockAuroraOre;

public class BlockRegistry {

    public static Block AURORA_ORE;
    public static Block AURORA_BLOCK;

    public static void load()
    {
        AURORA_BLOCK = new BlockAuroraOre("aurora_ore");
        AURORA_BLOCK = new BaseBlock("aurora_block", Material.ROCK, SoundType.STONE, 5F, 20F).register();

    }
}
