package pl.caerglad.multiblockhelper.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import pl.caerglad.multiblockhelper.misc.Helpers;

public class SelectorWandItem extends Item {
    private BlockPos pos1, pos2;


    public SelectorWandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        if (pLevel.isClientSide()) //No client
            return InteractionResultHolder.success(item);

        if (pos1 == null || pos2 == null) return super.use(pLevel, pPlayer, pUsedHand);

        getSelection(pLevel);

        return InteractionResultHolder.success(item);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();

            if (player.isShiftKeyDown()) {
                player.sendSystemMessage(Component.literal("Saving Pos 2"));
                pos2 = positionClicked;
            } else {
                player.sendSystemMessage(Component.literal("Saving Pos 1"));
                pos1 = positionClicked;
            }
        }

        return InteractionResult.SUCCESS;
    }

    private void getSelection(Level pLevel) {
        var blocks = pLevel.getBlockStates(new AABB(pos1, pos2)).map(b -> b.getBlock().getDescriptionId()).toList();
        var symbolsDictionary = Helpers.createSymbolDictionary(blocks);
        var symbols = Helpers.convertStringsToSymbols(blocks, symbolsDictionary);

        int xSize = Helpers.calculateSelectionSize(pos1.getX(), pos2.getX());
        int ySize = Helpers.calculateSelectionSize(pos1.getY(), pos2.getY());
        int zSize = Helpers.calculateSelectionSize(pos1.getZ(), pos2.getZ());

        String[][][] blocks3DArray = Helpers.convertListTo3DArray(symbols, xSize, ySize, zSize);
        StringBuilder stringBuilder = new StringBuilder();

        // Generate aisles
        for (int i = 0; i < xSize; i++) {
            stringBuilder.append(".aisle(");
            for (int j = 0; j < ySize; j++) {
                stringBuilder.append("\'");
                for (int k = 0; k < zSize; k++) {
                    stringBuilder.append(blocks3DArray[i][j][k]);
                }
                stringBuilder.append("\'");
                if (j < ySize - 1) stringBuilder.append(", ");
            }
            stringBuilder.append(")\n");
        }

        // Generate dictionary
        symbolsDictionary.forEach((k, v) -> stringBuilder
                .append(".where(\'")
                .append(v)
                .append("\', Predicates.blocks(\'")
                .append(k)
                .append("\')\n"));

        Minecraft.getInstance().keyboardHandler.setClipboard(stringBuilder.toString());
    }
}
