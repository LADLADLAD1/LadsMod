package LadsMod.Util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

@SuppressWarnings("unused")
public class BlockChanger {
    /**
     * Utility class for all things rendering in my mod.
     * @author SesuMoe
     */
    private static Minecraft mc = Minecraft.getMinecraft();
    public static List<Pair<BlockPos, IBlockState>> blocks = new ArrayList();

    public BlockChanger() {
    }

    private static int indexOfBlock(BlockPos pos) {
        for(int i = 0; i < blocks.size(); ++i) {
            if (((BlockPos)((Pair)blocks.get(i)).first).equals(pos)) {
                return i;
            }
        }

        return -1;
    }
    public static void setBlockToAir(BlockPos pos) {
        if (indexOfBlock(pos) == -1) {
            blocks.add(new Pair(pos, mc.theWorld.getBlockState(pos)));
        }
        mc.theWorld.setBlockToAir(pos);
    }

    public static void revertBlock(BlockPos pos) {
        int i = indexOfBlock(pos);
        if (i >= 0) {
            Pair<BlockPos, IBlockState> block = (Pair)blocks.get(i);
            blocks.remove(i);
            if (block != null) {
                setBlock((BlockPos)block.first, (IBlockState)block.second);
            }
        }

    }

    public static void revertAllBlocks() {
        Iterator var0 = blocks.iterator();

        while(var0.hasNext()) {
            Pair<BlockPos, IBlockState> p = (Pair)var0.next();
            setBlock((BlockPos)p.first, (IBlockState)p.second);
        }

        blocks.clear();
    }

    public static void setBlock(BlockPos pos, IBlockState state) {
        if (indexOfBlock(pos) == -1) {
            blocks.add(new Pair(pos, mc.theWorld.getBlockState(pos)));
        }
        try {
            mc.theWorld.setBlockState(pos, state, 3);
        } catch (Exception var3) {
            mc.theWorld.setBlockState(pos, state);
        }

    }
    public static class Pair<U, V> {
        public U first;
        public V second;

        public Pair(U first, V second) {
            this.first = first;
            this.second = second;
        }
    }
}
