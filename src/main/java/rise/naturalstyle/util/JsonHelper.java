package rise.naturalstyle.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import rise.naturalstyle.NaturalStyle;
import rise.naturalstyle.common.NaturalConfig;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 自動でBlockStateやModelのJsonを生成します。ある程度のシンプルな物に限り完全自動化が可能です。
 * 一部のソースコードを参考にさせていただきました。:: https://github.com/defeatedcrow/HeatAndClimateLib
 * @author RiSE4
 */
public class JsonHelper {

    private final String basePath;
    private boolean isDebug = NaturalConfig.DEBUG_MODE;
    private String modID = NaturalStyle.MOD_ID;

    public JsonHelper(String path)
    {
        this.basePath = path;
    }

    public static final JsonHelper INSTANCE = new JsonHelper("D:\\IDEA\\1.12.2_forge2847\\NaturalStyle2\\src\\main\\resources");

    public void registerJson(Object target, JsonType type)
    {
        if(target != null)
            return;

        if(this.isDebug)
        {
            /* ここに処理を書く */
        }
    }

    public void registerBlockJson(Block block)
    {
        if(block == null)
            return;

        if(this.isDebug)
        {
            this.generateJson(JsonType.BLOCK_STATE, block, block.getUnlocalizedName().substring(5));
            this.generateJson(JsonType.SIMPLE_BLOCK, block, block.getUnlocalizedName().substring(5));
            this.generateJson(JsonType.ITEM_BLOCK, block, block.getUnlocalizedName().substring(5));
        }
    }

    public void registerItemJson(Item item)
    {
        if(item == null)
            return;

        if(this.isDebug)
        {
//            this.generateJsonSimple(item);
        }
    }

    private void generateSimpleBlockState(Block block)
    {
        String filePath;
        File json = null;
        boolean find = false;

        try
        {
            Path path = Paths.get(basePath);
            path.normalize();

            if(block != null)
            {
                filePath = path.toString() + "\\assets\\" + this.modID + "\\blockstates\\";

                json = new File(filePath + block.getUnlocalizedName().substring(5) + ".json");

                if(json.exists())
                {
                    find = true;
                    LogHelper.debugInfoLog("Block state file is found. " + json.getName());
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(!find && block != null)
        {
            try
            {
                assert json != null;
                if(json.getParentFile() != null)
                {
                    json.getParentFile().mkdirs();
                }

                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(json.getPath())));

                String output = "{\n" +
                        "    \"variants\": {\n" +
                        "        \"normal\": { \"model\": \"" + this.modID + ":" + block.getUnlocalizedName().substring(5) + "\" }\n" +
                        "    }\n" +
                        "}";
                pw.println(output);
                pw.close();

                LogHelper.debugInfoLog("Successful wrote of block state. Please restart client. " + json.getPath());
            }
            catch(IOException e)
            {
                LogHelper.warnLog("Failed to register block state. " + json.getName());
            }
        }
    }

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

                if(type == JsonType.BLOCK_STATE)
                    filePath = rePath + "\\blockstates\\";

                if(type == JsonType.SIMPLE_BLOCK || type == JsonType.AXIS_BLOCK || type == JsonType.TOP_BOTTOM_BLOCK)
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

                if(type == JsonType.BLOCK_STATE)
                    output = "{\n" +
                            "    \"variants\": {\n" +
                            "        \"normal\": { \"model\": \"" + this.modID + ":" + fileName + "\" }\n" +
                            "    }\n" +
                            "}";

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

    private enum JsonType
    {
        BLOCK_STATE("Block State"),
        ITEM_BLOCK("Item Block Model"),
        SIMPLE_ITEM("Simple Item Model"),
        TOOL_ITEM("Tool Item Model"),
        SIMPLE_BLOCK("Simple Block Model"),
        AXIS_BLOCK("Axis Block Model"),
        TOP_BOTTOM_BLOCK("Top Bottom Block Model");

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
