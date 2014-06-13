package toolbelts.sets;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

public class BeltRamboSet extends BeltBase{

	public BeltRamboSet(RenderPlayerEvent.SetArmorModel evt){
		super(evt);
	}

	public void renderDefault(RenderPlayerEvent.SetArmorModel evt ){

		float scale = 0.3f;
		
		renderHeldSword(heldItem, evt.entityPlayer);

		for (int i = 0; i < evt.entityPlayer.inventory.getSizeInventory(); i ++){
			ItemStack is = evt.entityPlayer.inventory.getStackInSlot(i);
			if(is != null){
				if(is.getItem().equals(Items.potionitem)){
					
					float f = 0;
					GL11.glPushMatrix();
					GL11.glTranslatef(-0.35f, -0.15f, 0f);
					addItemToBelt(is, evt.entityPlayer,6, scale);
					GL11.glPopMatrix();
				}
				if(is.getItem().equals(Items.bow)){
					
					float f = 0;
					GL11.glPushMatrix();
					GL11.glTranslatef(0.2f, -0.2f, 0.0f);
					GL11.glRotatef(180, 0, 1, 0);
					addItemToBelt(is, evt.entityPlayer,8, 0.7f);
					GL11.glPopMatrix();
				}
				if(is.getItem().equals(Blocks.torch)){
					
					GL11.glPushMatrix();
					GL11.glTranslatef(-0.07f, -0.5f, 0f);
					addItemToBelt(is, evt.entityPlayer, 7 , scale + 0.1f);
					GL11.glPopMatrix();
				}
				if(is.getItem().equals(Items.arrow)){
					
					GL11.glPushMatrix();
					GL11.glTranslatef(0.2f, -0.3f, -0.3f);
					GL11.glRotatef(180, 0, 1, 0);
					addItemToBelt(is, evt.entityPlayer, 9 , 0.4f);
					GL11.glPopMatrix();
				}
			}
		}

		ItemStack st = new ItemStack(Blocks.stained_hardened_clay, 1 , 12);
		renderBelt(st, evt.entityPlayer);
		renderShoulderBelt(st, evt.entityPlayer);

		ItemStack chest = new ItemStack(Blocks.chest);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5f, 0.1f, 0.15f);
		addItemToBelt(chest, evt.entityPlayer, 14, scale);
		GL11.glPopMatrix();
	}
}
