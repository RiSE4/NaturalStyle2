package rise.naturalstyle.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import rise.naturalstyle.NaturalStyle;
import rise.naturalstyle.common.NaturalConfig;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 自動でBlockStateやModelのJsonを生成します。バニラにペアレントモデルがあるブロックに限り完全自動化が可能です。
 * 一部のソースコードを参考にさせていただきました。ありがとうございます！:: https://github.com/defeatedcrow/HeatAndClimateLib
 */
public class JsonHelper {

    private final String basePath;

    private boolean isDebug = NaturalConfig.DEBUG_MODE;
    private String modID = NaturalStyle.MOD_ID;

    private JsonHelper(String path)
    {
        this.basePath = path;
    }

    /** Modのリソースファイルのパスをコピペ*/
    public static final JsonHelper INSTANCE = new JsonHelper("D:\\IDEA\\1.12.2_forge2847\\NaturalStyle2\\src\\main\\resources");

    /**
     * デバッグ時に限りJsonを自動生成する。各コンテンツのコンストラクタなどで呼び出すと使える。
     * @param target BlockかItem
     * @param type Jsonの種類
     * @param name ファイルの名前 わかりやすいようにgetUnlocalizedName()の使用を推奨
     */
    public void registerJson(Object target, JsonType type, String name)
    {
        if (this.isDebug)
        {
            if (target instanceof Block)
            {
                Block block = (Block) target;
                this.generateJson(type, block, name.substring(5));
            }

            if (target instanceof Item)
            {
                Item item = (Item) target;
                this.generateJson(type, item, name.substring(5));
            }
        }
    }

    /**
     * 旧式のアイテムのJson自動生成メソッド 参照用に置いてあるが削除予定
     */
    @Deprecated
    private void generateSimpleItemJson(Item item)
    {
        String filePath;
        File json = null;
        boolean find = false;

        try
        {
            Path path = Paths.get(basePath);
            path.normalize();

            if(item != null)
            {
                filePath = path.toString() + "\\assets\\" + this.modID + "\\models\\block\\";

                json = new File(filePath + item.getUnlocalizedName().substring(5) + ".json");

                if(json.exists())
                {
                    find = true;
                    LogHelper.debugInfoLog("Item model file is found." + json.getName());
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(!find && item != null)
        {
            try
            {
                assert json != null;
                if (json.getParentFile() != null)
                {
                    json.getParentFile().mkdirs();
                }

                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(json.getPath())));

                String output = "{\n" +
                        "    \"parent\": \"item/generated\",\n" +
                        "    \"textures\": {\n" +
                        "        \"layer0\": \"" + this.modID + ":" + item.getUnlocalizedName().substring(5) + "\"\n" +
                        "    }\n" +
                        "}";
                pw.println(output);
                pw.close();

                LogHelper.debugInfoLog("Successful wrote of item model. Please restart client. " + json.getPath());

            }
            catch (IOException e)
            {
                LogHelper.warnLog("Failed to register item model. " + json.getName());
            }
        }
    }

    /**
     * BlockStateまたはModelのJsonを自動生成する。
     * テキストでゴリ押しで書いているのでもっといい方法があるかも。
     * @param type Jsonの種類
     * @param target BlockかItem
     * @param fileName ファイル名
     */
    private void generateJson(JsonType type, Object target, String fileName)
    {
        String filePath = null;
        File json = null;
        boolean find = false;

        try
        {
            Path path = Paths.get(basePath);
            path.normalize();

            if(target != null)
            {
                String rePath = path.toString() + "\\assets\\" + this.modID;

                if(type == JsonType.SIMPLE_BLOCK_STATE || type == JsonType.FACING_BLOCK_STATE)
                    filePath = rePath + "\\blockstates\\";

                if(type == JsonType.SIMPLE_BLOCK || type == JsonType.AXIS_BLOCK || type == JsonType.TOP_BOTTOM_BLOCK || type == JsonType.FACING_BLOCK)
                    filePath = rePath + "\\models\\block\\";

                if(type == JsonType.SIMPLE_ITEM || type == JsonType.TOOL_ITEM || type == JsonType.ITEM_BLOCK)
                    filePath = rePath + "\\models\\item\\";
                
                json = new File(filePath + fileName + ".json");

                if(json.exists())
                {
                    find = true;
                    LogHelper.debugInfoLog(type.getLogName() + " file is found. :: " + json.getName());
                }
            }
        }
        catch (Exception e)
        {
            LogHelper.debugTrace(e.toString());
        }

        if(!find && target != null)
        {
            try
            {
                assert json != null;
                if (json.getParentFile() != null)
                {
                    json.getParentFile().mkdirs();
                }

                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(json.getPath())));

                String output = "";

                if(type == JsonType.SIMPLE_BLOCK_STATE)
                    output = "{\n" +
                            "    \"variants\": {\n" +
                            "        \"normal\": { \"model\": \"" + this.modID + ":" + fileName + "\" }\n" +
                            "    }\n" +
                            "}";

                if(type == JsonType.FACING_BLOCK_STATE)
                    output = "{\n" +
                            "    \"variants\": {\n" +
                            "        \"facing=north\": { \"model\": \"" + this.modID + ":" + fileName + "\" },\n" +
                            "        \"facing=south\": { \"model\": \"" + this.modID + ":" + fileName + "\", \"y\": 180 },\n" +
                            "        \"facing=west\":  { \"model\": \"" + this.modID + ":" + fileName + "\", \"y\": 270 },\n" +
                            "        \"facing=east\":  { \"model\": \"" + this.modID + ":" + fileName + "\", \"y\": 90 }\n" +
                            "    }\n" +
                            "}\n";

                if(type == JsonType.ITEM_BLOCK)
                    output = "{\n" +
                            "    \"parent\": \"" + this.modID + ":" + "block/" + fileName + "\"\n" +
                            "}\n";

                if(type == JsonType.SIMPLE_ITEM)
                    output = this.getParentText("item/generated", "layer0", fileName);

                if(type == JsonType.TOOL_ITEM)
                    output = this.getParentText("item/handheld", "layer0", fileName);

                if(type == JsonType.SIMPLE_BLOCK)
                    output = this.getParentText("block/cube_all", "all", fileName);

                if(type == JsonType.AXIS_BLOCK)
                    output = "{\n" +
                            "    \"parent\": \"block/cube_column\",\n" +
                            "    \"textures\": {\n" +
                            "        \"end\":\"" + this.modID + ":" + fileName + "_top" + "\",\n" +
                            "        \"side\":\"" + this.modID + ":" + fileName + "_side" + "\"\n" +
                            "    }\n" +
                            "}\n";

                if(type == JsonType.TOP_BOTTOM_BLOCK)
                    output = "{\n" +
                            "    \"parent\": \"block/cube_bottom_top\",\n" +
                            "    \"textures\": {\n" +
                            "        \"top\":\"" + this.modID + ":" + fileName + "_top" + "\",\n" +
                            "        \"side\":\"" + this.modID + ":" + fileName + "_side" + "\",\n" +
                            "        \"bottom\":\"" + this.modID + ":" + fileName + "_bottom" + "\"\n" +
                            "    }\n" +
                            "}\n";

                if(type == JsonType.FACING_BLOCK)
                    output = "{\n" +
                            "    \"parent\": \"block/orientable\",\n" +
                            "    \"textures\": {\n" +
                            "        \"top\": \"" + this.modID + ":" + fileName + "_top" + "\",\n" +
                            "        \"front\": \"" + this.modID + ":" + fileName + "_front" + "\",\n" +
                            "        \"side\": \"" + this.modID + ":" + fileName + "_side" + "\"\n" +
                            "    }\n" +
                            "}\n";

                pw.println(output);
                pw.close();

                LogHelper.debugInfoLog("Successful wrote of " + type.getLogName() + ". Please restart client. :: " + json.getPath());
            }
            catch (IOException e)
            {
                LogHelper.warnLog("Failed to register " + type.getLogName() + ". :: " + json.getName());
            }
        }
    }

    private String getParentText(String parentName, String layerName, String fileName)
    {
        return "{\n" +
                "    \"parent\": \"" + parentName + "\",\n" +
                "    \"textures\": {\n" +
                "        \"" + layerName + "\": \"" + this.modID + ":" + fileName + "\"\n" +
                "    }\n" +
                "}";
    }

    /**
     * Jsonの種類 引数はログを表示する際にのみ使用し、enumごとの処理は generateJson(...)でやっている。
     */
    public enum JsonType
    {
        SIMPLE_BLOCK_STATE("Simple Block State"),
        FACING_BLOCK_STATE("Facing Block State"),
        ITEM_BLOCK("Item Block Model"),
        SIMPLE_ITEM("Simple Item Model"),
        TOOL_ITEM("Tool Item Model"),
        SIMPLE_BLOCK("Simple Block Model"),
        AXIS_BLOCK("Axis Block Model"),
        TOP_BOTTOM_BLOCK("Top Bottom Block Model"),
        FACING_BLOCK("Facing Block Model");

        private final String logName;

        JsonType(String logName)
        {
            this.logName = logName;
        }

        public String getLogName()
        {
            return this.logName;
        }
    }
}
