package rise.naturalstyle.init;

import net.minecraft.block.Block;
import rise.naturalstyle.block.BlockAuroraOre;

public class BlockRegistry {

    public static Block AURORA_ORE;
    public static Block AURORA_BLOCK;

    public static void load()
    {
        AURORA_ORE = new BlockAuroraOre("aurora_ore");
//        AURORA_BLOCK = new BaseBlock("aurora_block", Material.ROCK, SoundType.STONE, 5F, 20F).register();

    }
}
