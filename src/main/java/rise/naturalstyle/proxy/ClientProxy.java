package rise.naturalstyle.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Objects;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemModel(Item item, int meta)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }

    @Override
    public void registerItemModel(Item item, int meta, ModelResourceLocation location)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, location);
    }
}