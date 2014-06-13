package toolbelts.handlers.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import toolbelts.HipItems;
import toolbelts.PlayerBeltData;
import toolbelts.Utils;
import toolbelts.render.RenderHipItemsOnPlayer;
import toolbelts.sets.BeltBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;

public class ServerPacket {

	public static final int SMP_SYNC_BELT = 1;
	public static final int SMP_SYNC_BELTID = 2;

	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event) {

		if(!event.packet.channel().equals(HipItems.channelName))
			return;
		
		EntityPlayerMP p = ((NetHandlerPlayServer) event.handler).playerEntity;
		ByteBufInputStream dis = new ByteBufInputStream(event.packet.payload());
		ByteBuf buf = event.packet.payload();

		World world = p.worldObj;

		try {

			int guiId = dis.readInt();

			switch (guiId) {

			case SMP_SYNC_BELTID:
				int id = dis.readInt();
				PlayerBeltData.get(p).BeltID = id;
				p.addChatMessage(new ChatComponentText("Set belt to " + Utils.beltIDToName(id)));
				break;

			}

			dis.close();
		} catch (Exception e) {
		}
	}

}
