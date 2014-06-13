package toolbelts.sets;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;

import org.lwjgl.opengl.GL11;

public class BeltDefaultSet extends BeltBase{

	public BeltDefaultSet(RenderPlayerEvent.SetArmorModel evt){
		super(evt);
	}

	@Override
	public void renderDefault(SetArmorModel evt ){

		float scale = 0.3f;

		renderHeldSword(heldItem, evt.entityPlayer);
		renderHeldPickaxe(heldItem, evt.entityPlayer);

		for (int i = 0; i < evt.entityPlayer.inventory.getSizeInventory(); i ++){
			ItemStack is = evt.entityPlayer.inventory.getStackInSlot(i);
			if(is != null){

				if(is.getItem().equals(Items.potionitem)){
					float f = 0;
					GL11.glPushMatrix();
					GL11.glTranslatef(0f, 0f, 0f);
					addItemToBelt(is, evt.entityPlayer,0, scale);
					GL11.glPopMatrix();
				}

				if(is.getItem().equals(Items.glass_bottle)){
					float f = 0;
					GL11.glPushMatrix();
					GL11.glTranslatef(0f, 0f, 0.3f);
					addItemToBelt(is, evt.entityPlayer,1, scale);
					GL11.glPopMatrix();
				}

				if(is.getItem().equals(Item.getItemFromBlock(Blocks.torch))){
					GL11.glPushMatrix();
					GL11.glTranslatef(-0.15f, -0.07f, 0f);
					addItemToBelt(is, evt.entityPlayer,0 , scale + 0.05f);
					GL11.glPopMatrix();
				}

				if(is.getItem().equals(Items.arrow)){
					GL11.glPushMatrix();
					GL11.glTranslatef(-0.3f, -0.05f, 0.3f);
					addItemToBelt(is, evt.entityPlayer,1 , 0.4f);
					GL11.glPopMatrix();
				}
			}
		}

		ItemStack st = new ItemStack(Blocks.wool, 1 , 12);
		renderBelt(st, evt.entityPlayer);

		ItemStack chest = new ItemStack(Blocks.chest);
		GL11.glPushMatrix();
		GL11.glRotatef(-90, 0, 1, 0);
		GL11.glTranslatef(-0.3f, 0.02f, 0.3f);
		addItemToBelt(chest, evt.entityPlayer, 2, scale);
		GL11.glPopMatrix();

	}
}
