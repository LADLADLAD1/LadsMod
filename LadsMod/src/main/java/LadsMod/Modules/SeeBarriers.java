package LadsMod.Modules;

import LadsMod.Util.BlockChanger;
import LadsMod.Util.ConfigManager;
import LadsMod.Util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.util.ArrayList;
import java.util.List;

public class SeeBarriers {

    public static List<BlockPos> blocks = new ArrayList<BlockPos>();

    public static void grabBlocks(Boolean doWool) {
        blocks.clear();
        BlockPos playerPosition = Minecraft.getMinecraft().thePlayer.getPosition();
        int bd = 25; // BoxRadius
        for (int x = -bd; x < bd; x++) {
            for (int y = -bd; y < bd; y++) {
                for (int z = -bd; z < bd; z++) {
                    BlockPos pos = new BlockPos(x + playerPosition.getX(), y + playerPosition.getY(), z + playerPosition.getZ());
                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
                    if (block == Blocks.barrier) {
                        if (doWool) {
                            IBlockState blockState = Blocks.wool.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED);
                            BlockChanger.setBlock(pos, blockState);
                        } else {
                            blocks.add(pos); //adds position only for barrier blocks
                        }
                    }
                }
            }
        }
    }

    public static void renderBlocks(RenderWorldLastEvent event) {
        for (BlockPos pos : blocks) { //draws box on position of blocks
            RenderUtil.renderBlockMask(event.partialTicks, pos, ConfigManager.COLOR);
        }
    }
}
