package toolbelts;

import java.lang.reflect.Field;

import net.minecraftforge.common.MinecraftForge;
import toolbelts.handlers.HandleLogin;
import toolbelts.handlers.TickHandler;
import toolbelts.handlers.packets.ServerPacket;
import toolbelts.proxy.ClientHipProxy;
import toolbelts.proxy.CommonHipProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "ToolBelt", name = "ToolBelts", version = HipItems.version)
public class HipItems {

	public static final String name = "ToolBelts";
	public static final String version = "1.7.2 v1";

	public static final int BELTID_MAX = 6; //used in tickhandler to hop back to default value
	public static final int BELTID_MIN = 0; //default value

	public static FMLEventChannel Channel;
	public static final String channelName = "ToolBelts";

	public static boolean hasAP;

	private static final String CLASS_LOC = "thehippomaster.AnimatedPlayer.client.ModelPlayer";

	@SidedProxy(serverSide = "toolbelts.proxy.CommonHipProxy", clientSide = "toolbelts.proxy.ClientHipProxy")
	public static CommonHipProxy proxy;

	@EventHandler
	public void load (FMLInitializationEvent e){

		Channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelName);
		Channel.register(new ServerPacket());
		proxy.registerClientChannel();

		MinecraftForge.EVENT_BUS.register(new PlayerBeltLoginTracker());

		FMLCommonHandler.instance().bus().register(new HandleLogin());

		ClientHipProxy.renderHandler();

		FMLCommonHandler.instance().bus().register(new TickHandler());

		
		try {

			Class c = Class.forName(CLASS_LOC);
			hasAP = true;
			FMLLog.getLogger().info("ToolBelts found AnimatedPlayer.");

		} catch (Exception e2) {
			e2.printStackTrace();
			FMLLog.getLogger().info("ToolBelts did not find AnimatedPlayer. Normal rendering will be used");
		}
	}
}
