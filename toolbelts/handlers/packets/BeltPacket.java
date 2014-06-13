package toolbelts.handlers.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import toolbelts.HipItems;
import toolbelts.PlayerBeltData;
import toolbelts.sets.BeltBase;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

public class BeltPacket {


	public static void sendBeltToOthers(EntityPlayer player){

		PlayerBeltData pbd = PlayerBeltData.get(player);

		ByteBuf buf = Unpooled.buffer();
		ByteBufOutputStream out = new ByteBufOutputStream(buf);

		try {
			out.writeInt(ServerPacket.SMP_SYNC_BELT);

			out.writeUTF(player.getDisplayName());

			for(int i = 0; i < player.inventory.mainInventory.length; i++)
				ByteBufUtils.writeItemStack(buf, player.inventory.mainInventory[i]);

			out.writeInt(PlayerBeltData.get(player).BeltID);

			ByteBufUtils.writeItemStack(buf, player.getCurrentEquippedItem());

			ByteBufUtils.writeItemStack(buf, pbd.swordMemory[0]);
			ByteBufUtils.writeItemStack(buf, pbd.swordMemory[1]);

			ByteBufUtils.writeItemStack(buf, pbd.pickaxeMemory[0]);
			ByteBufUtils.writeItemStack(buf, pbd.pickaxeMemory[1]);

			ByteBufUtils.writeItemStack(buf, pbd.toolMemory[0]);
			ByteBufUtils.writeItemStack(buf, pbd.toolMemory[1]);

			TargetPoint point = new TargetPoint(player.dimension, player.posX,
					player.posY, player.posZ, 60);
			HipItems.Channel.sendToAllAround(new FMLProxyPacket(buf, HipItems.channelName), point);

			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void sendServerBeltId(EntityPlayer player, int id){

		ByteBuf buf = Unpooled.buffer();
		ByteBufOutputStream out = new ByteBufOutputStream(buf);

		try {
			out.writeInt(ServerPacket.SMP_SYNC_BELTID);
			out.writeInt(id);
			HipItems.Channel.sendToServer(new FMLProxyPacket(buf, HipItems.channelName));

			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void sendBeltToolsToClient(EntityPlayer player){
		ByteBuf buf = Unpooled.buffer();
		ByteBufOutputStream out = new ByteBufOutputStream(buf);

		PlayerBeltData pbd = PlayerBeltData.get(player);

		try {
			out.writeInt(3);

			ByteBufUtils.writeItemStack(buf, pbd.swordMemory[0]);
			ByteBufUtils.writeItemStack(buf, pbd.swordMemory[1]);

			ByteBufUtils.writeItemStack(buf, pbd.pickaxeMemory[0]);
			ByteBufUtils.writeItemStack(buf, pbd.pickaxeMemory[1]);

			ByteBufUtils.writeItemStack(buf, pbd.toolMemory[0]);
			ByteBufUtils.writeItemStack(buf, pbd.toolMemory[1]);

			out.close();

			HipItems.Channel.sendTo(new FMLProxyPacket(buf, HipItems.channelName), (EntityPlayerMP) player);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
