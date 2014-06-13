package toolbelts.render;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import toolbelts.PlayerBeltData;
import toolbelts.handlers.packets.BeltPacket;
import toolbelts.sets.BeltAssassinSet;
import toolbelts.sets.BeltDefaultSet;
import toolbelts.sets.BeltHotBar;
import toolbelts.sets.BeltIChunSet;
import toolbelts.sets.BeltRamboSet;
import toolbelts.sets.BeltSimpleBag;
import toolbelts.sets.BeltSurvivorSet;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderHipItemsOnPlayer {

	@SubscribeEvent
	public void PlayerRender(RenderPlayerEvent.SetArmorModel evt ){

		switch (PlayerBeltData.get(evt.entityPlayer).BeltID) {
		case 0:
			new BeltDefaultSet(evt);
			break;
		case 1:
			new BeltRamboSet(evt);
			break;
		case 2:
			new BeltAssassinSet(evt);
			break;
		case 3:
			new BeltSimpleBag(evt);
			break;
		case 4:
			new BeltHotBar(evt);
			break;
		case 5:
			new BeltSurvivorSet(evt);
			break;
		case 6:
			new BeltIChunSet(evt);
			break;
		default:
			new BeltDefaultSet(evt);
			break;
		}
	}
}
