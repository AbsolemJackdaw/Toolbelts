package toolbelts.proxy;

import net.minecraftforge.common.MinecraftForge;
import toolbelts.HipItems;
import toolbelts.handlers.BeltKeyHandler;
import toolbelts.handlers.packets.ClientPacket;
import toolbelts.render.RenderHipItemsOnPlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientHipProxy extends CommonHipProxy {


	@SideOnly(Side.CLIENT)
	public static void renderHandler() {
		MinecraftForge.EVENT_BUS.register(new RenderHipItemsOnPlayer());
		FMLCommonHandler.instance().bus().register(new BeltKeyHandler());
	}

	public void registerClientChannel(){
		HipItems.Channel.register(new ClientPacket());
	}
}
