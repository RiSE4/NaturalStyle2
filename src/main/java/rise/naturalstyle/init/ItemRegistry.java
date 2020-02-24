package rise.naturalstyle.init;

import net.minecraft.item.Item;
import rise.naturalstyle.item.BaseItem;

public class ItemRegistry {

    public static Item AURORA_GEM;

    public static void load()
    {
        AURORA_GEM = new BaseItem("aurora_gem").register();
    }
}
