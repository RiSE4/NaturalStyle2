package rise.naturalstyle.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import rise.naturalstyle.util.LogHelper;

public class NaturalConfig {

    public static boolean DEBUG_MODE = false;

    public void load(Configuration config)
    {
        try
        {
            config.load();
            Property debug = config.get("general", "DebugMode", DEBUG_MODE, "Start in debug mode. Enable tools for author.");
            DEBUG_MODE = debug.getBoolean();
        }
        catch (Exception e)
        {
            LogHelper.debugTrace(e.toString());
        }
        finally
        {
            config.save();
        }
    }
}
