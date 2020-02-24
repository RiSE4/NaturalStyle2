package rise.naturalstyle.util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rise.naturalstyle.NaturalStyle;

import java.util.Arrays;
import java.util.Objects;

public class Helper {

    /**
     * ブロックを登録する
     * @param block ブロック
     */
    public static void registerBlock(Block block)
    {
        registerBlock(block, new ItemBlock(block));
    }

    /**
     * ブロックを登録する メタを持つ場合はこちらを使う
     * @param block ブロック
     * @param itemBlock アイテムブロック
     */
    public static void registerBlock(Block block, ItemBlock itemBlock)
    {
        if(block == null)
            return;

        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName())));

        if(block instanceof IContent )
        {
            ((IContent) block).registerModels();
        }
    }

    /**
     * アイテムを追加する
     * @param item アイテム
     */
    public static void registerItem(Item item)
    {
        if(item == null)
            return;

        ForgeRegistries.ITEMS.register(item);

        if(item instanceof IContent)
        {
            ((IContent) item).registerModels();
        }
    }

    public static void registerItemModel(Block block, String category, String name)
    {
        NaturalStyle.proxy.registerItemModel(Item.getItemFromBlock(block), 0, new ModelResourceLocation("naturalstyle:" + category + "/" + name, "inventory"));
    }

    /**
     * 指定したenumの数だけアイテムのモデルを追加する処理を繰り返す。
     * @param block ブロック
     * @param category モデルが格納されたフォルダパス
     * @param target 使いたいenum
     * @param maxMeta 最大メタ値(<E>.values().lengthで取得するのが基本)
     * @param <E> IMetadataとIStringSerializableを継承したenumクラス
     */
    public static <E extends IMeta & IStringSerializable> void forItemModels(Block block, String category, Class<E> target, int maxMeta)
    {
        for(int i = 0; i < maxMeta; ++i)
        {
            String name = valueOf(target, i).getName();
            NaturalStyle.proxy.registerItemModel(Item.getItemFromBlock(block), i, new ModelResourceLocation("naturalstyle:" + category + "/" + block.getUnlocalizedName().substring(5) + "_" + name, "inventory"));
        }
    }

    /**
     * 指定したenumから値を逆引きする
     * @param target 使いたいenum
     * @param meta 値を取得するためのメタ
     * @param <E> IMetadataを継承したenumクラス
     * @return 各種パラメータを追加したenum
     */
    private static <E extends IMeta> E valueOf(Class<E> target, int meta) {

        return Arrays.stream(target.getEnumConstants())
                .filter(data -> data.getMetadata() == meta)
                .findFirst()
                .orElse(null);
    }

    /**
     * メタの数だけタブに登録する処理を繰り返す
     * @param target ブロックまたはアイテム
     * @param list リスト
     * @param meta メタ
     */
    public static void forCreativeTab(Object target, NonNullList<ItemStack> list, int meta)
    {
        if(target instanceof Block)
        {
            Block block = (Block) target;
            Item.getItemFromBlock(block);

            for(int i = 0; i < meta; ++i)
            {
                list.add(new ItemStack(block, 1, i));
            }
        }

        if(target instanceof Item)
        {
            Item item = (Item) target;

            for(int i = 0; i < meta; ++i)
            {
                list.add(new ItemStack(item, 1, i));
            }
        }
    }

    /**
     *
     * @param tileClass タイルエンティティ
     * @param name 名前
     */
    public static void registerTileEntity(Class<? extends TileEntity> tileClass, String name)
    {
        GameRegistry.registerTileEntity(tileClass, new ResourceLocation(NaturalStyle.MOD_ID, name));
    }
}
