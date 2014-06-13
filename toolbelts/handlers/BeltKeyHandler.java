package toolbelts.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import toolbelts.HipItems;
import toolbelts.PlayerBeltData;
import toolbelts.handlers.packets.BeltPacket;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class BeltKeyHandler {

	protected static KeyBinding keyCyclePlus = new KeyBinding(
			"Next Belt + Ctrl", Keyboard.KEY_ADD, "ToolBelt");
	
	protected static KeyBinding keyCycleMin = new KeyBinding(
			"Previous Belt + Ctrl", Keyboard.KEY_SUBTRACT, "ToolBelt");
	
	public BeltKeyHandler() {
		ClientRegistry.registerKeyBinding(keyCyclePlus);
		ClientRegistry.registerKeyBinding(keyCycleMin);
	}

	int cycle = 0;
	
	private static boolean isCtrlKeyDown()
    {
        return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
    }
	
	@SubscribeEvent
	public void keys(KeyInputEvent evt) {
		EntityPlayer p = Minecraft.getMinecraft().thePlayer;

		if(keyCyclePlus.isPressed() && isCtrlKeyDown()){
			if(cycle >= HipItems.BELTID_MAX){
				cycle = HipItems.BELTID_MIN;
			}else{
				cycle++;
			}
			BeltPacket.sendServerBeltId(p, cycle);
		}
		
		if(keyCycleMin.isPressed() && isCtrlKeyDown()){
			if(cycle <= HipItems.BELTID_MIN){
				cycle = HipItems.BELTID_MAX;
			}else{
				cycle--;
			}
			BeltPacket.sendServerBeltId(p, cycle);
		}
		

	}
}