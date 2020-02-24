package rise.naturalstyle.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import rise.naturalstyle.NaturalStyle;
import rise.naturalstyle.util.Helper;
import rise.naturalstyle.util.IContent;
import rise.naturalstyle.util.JsonHelper;

public class BaseItem extends Item implements IContent {

    private EnumRarity rare;

    public BaseItem(String name)
    {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(NaturalStyle.TAB_NS);
    }

    @Override
    public void registerModels()
    {
        NaturalStyle.proxy.registerItemModel(this, 0);
        JsonHelper.INSTANCE.registerItemJson(this);
    }

    public BaseItem register()
    {
        Helper.registerItem(this);
        return this;
    }

    public EnumRarity setItemRarity(EnumRarity rare)
    {
        return rare;
    }

    public IRarity getForgeRarity(ItemStack itemStack)
    {
        return rare != null ? rare : super.getForgeRarity(itemStack);
    }
}
