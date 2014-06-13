package toolbelts.handlers;

import toolbelts.PlayerBeltData;
import toolbelts.handlers.packets.BeltPacket;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class HandleLogin  {
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent e) {
		PlayerBeltData.get(e.player);
		BeltPacket.sendBeltToOthers(e.player);
	}
}
