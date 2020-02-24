package rise.naturalstyle.util;

import net.minecraft.util.math.AxisAlignedBB;

public class AABBList {

    public static final AxisAlignedBB AABB_FULL = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    public static final AxisAlignedBB AABB_HALF_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    public static final AxisAlignedBB AABB_NONE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    public static final AxisAlignedBB AABB_CUBE_SMALL = new AxisAlignedBB(0.2D, 0.6D, 0.2D, 0.8D, 0.0D, 0.8D);
}
