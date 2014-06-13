package toolbelts.handlers;


import java.util.List;

import com.google.common.eventbus.Subscribe;

import toolbelts.PlayerBeltData;
import toolbelts.handlers.packets.BeltPacket;
import toolbelts.sets.BeltBase;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandler {

	private int countDown = 40;
	@SubscribeEvent
	public void ticks(TickEvent.PlayerTickEvent e){

		countDown--;

		if(countDown <=0){
			List<EntityPlayer> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;

			for(EntityPlayer player : players)
				if(player != null){
					if(!player.isUsingItem()){
						if(!player.worldObj.isRemote){
							BeltPacket.sendBeltToOthers(player);
						}

						trackingItems(player);
					}
				}
			countDown = 40;
		}
	}




	private void trackingItems(EntityPlayer player){

		ItemStack heldItem = player.getCurrentEquippedItem();

		PlayerBeltData pbd = PlayerBeltData.get(player);

		/*======SWORD=====*/
		if(!ItemStack.areItemStacksEqual(pbd.swordMemory[1], heldItem)){
			if(!ItemStack.areItemStacksEqual(pbd.swordMemory[1], pbd.swordMemory[0])){
				pbd.swordMemory[0] = pbd.swordMemory[1];
				BeltPacket.sendBeltToolsToClient(player);
			}
		}

		if(!pbd.swordMem[0] && heldItem != null){
			if(heldItem.getItem() instanceof ItemSword){
				pbd.swordMemory[0] = heldItem.copy();
				pbd.swordMem[0] = true;
				pbd.swordMem[1] = true;
			}
		}

		if(pbd.swordMem[1] && !ItemStack.areItemStacksEqual(heldItem, pbd.swordMemory[0])){
			if(heldItem != null)
				if(heldItem.getItem() instanceof ItemSword){
					pbd.swordMemory[1] = heldItem.copy();
				}
		}


		if(pbd.swordMemory[0] != null && !player.inventory.hasItem(pbd.swordMemory[0].getItem())){
			pbd.swordMemory[0] = null;
			pbd.swordMemory[1] = null;
		}

		/*=====PICKAXE=====*/
		if(!ItemStack.areItemStacksEqual(pbd.pickaxeMemory[1], heldItem)){
			if(!ItemStack.areItemStacksEqual(pbd.pickaxeMemory[1], pbd.pickaxeMemory[0])){
				pbd.pickaxeMemory[0] = pbd.pickaxeMemory[1];
				BeltPacket.sendBeltToolsToClient(player);
			}
		}

		if(!pbd.pickaxeMem[0] && heldItem != null){
			if(heldItem.getItem() instanceof ItemPickaxe){
				pbd.pickaxeMemory[0] = heldItem.copy();
				pbd.pickaxeMem[0] = true;
				pbd.pickaxeMem[1] = true;
			}
		}

		if(pbd.pickaxeMem[1] && !ItemStack.areItemStacksEqual(heldItem, pbd.pickaxeMemory[0])){
			if(heldItem != null)
				if(heldItem.getItem() instanceof ItemPickaxe){
					pbd.pickaxeMemory[1] = heldItem.copy();
				}
		}

		if(pbd.pickaxeMemory[0] != null && !player.inventory.hasItem(pbd.pickaxeMemory[0].getItem())){
			pbd.pickaxeMemory[0] = null;
			pbd.pickaxeMemory[1] = null;
		}

		/*=====TOOLS=====*/
		if(!ItemStack.areItemStacksEqual(pbd.toolMemory[1], heldItem)){
			if(!ItemStack.areItemStacksEqual(pbd.toolMemory[1], pbd.toolMemory[0])){
				pbd.toolMemory[0] = pbd.toolMemory[1];
				BeltPacket.sendBeltToolsToClient(player);
			}
		}

		if(!pbd.toolMem[0] && heldItem != null){
			if(isTool(heldItem)){
				pbd.toolMemory[0] = heldItem.copy();
				pbd.toolMem[0] = true;
				pbd.toolMem[1] = true;
			}
		}

		if(pbd.toolMem[1] && !ItemStack.areItemStacksEqual(heldItem, pbd.toolMemory[0])){
			if(heldItem != null)
				if(isTool(heldItem)){
					pbd.toolMemory[1] = heldItem.copy();
				}
		}

		if(pbd.toolMemory[0] != null && !player.inventory.hasItem(pbd.toolMemory[0].getItem())){
			pbd.toolMemory[0] = null;
			pbd.toolMemory[1] = null;
		}

	}

	private boolean isTool(ItemStack is){

		if(!(is.getItem() instanceof ItemPickaxe) &&
				!(is.getItem() instanceof ItemSword) &&
				!is.getItem().equals(Items.stick)){

			if(is.getItem().isFull3D() || is.getItem() instanceof ItemTool || 
					is.getItemUseAction().equals(EnumAction.bow)||
					is.getItem().equals(Items.clock))

				return true;
		}
		return false;
	}
}
