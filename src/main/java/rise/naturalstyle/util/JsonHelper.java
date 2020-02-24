package rise.naturalstyle.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import rise.naturalstyle.NaturalStyle;
import rise.naturalstyle.common.NaturalConfig;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

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
//            this.generateJsonSimple(block);
            this.generateSimpleBlockState(block);
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
        String filePath = null;
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

    private enum JsonType
    {
        BLOCK_STATE,
        SIMPLE_ITEM,
        SIMPLE_BLOCK;
    }
}
