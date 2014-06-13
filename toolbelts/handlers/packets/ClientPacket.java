package toolbelts.handlers.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import toolbelts.HipItems;
import toolbelts.PlayerBeltData;
import toolbelts.sets.BeltBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;

public class ClientPacket extends ServerPacket{


	@SubscribeEvent
	public void onClientPacket(ClientCustomPacketEvent event) {

		if(!event.packet.channel().equals(HipItems.channelName))
			return;

		EntityPlayer p = Minecraft.getMinecraft().thePlayer;
		ByteBufInputStream dis = new ByteBufInputStream(event.packet.payload());
		ByteBuf buf = event.packet.payload();

		World world = p.worldObj;

		try {
			int guiId = dis.readInt();

			switch (guiId) {

			case SMP_SYNC_BELT:
				String otherPlayerName = dis.readUTF();
				EntityPlayer other = world.getPlayerEntityByName(otherPlayerName);

				PlayerBeltData pbd = PlayerBeltData.get(other);

				for(int i = 0; i < other.inventory.mainInventory.length; i++)
					other.inventory.setInventorySlotContents(i, ByteBufUtils.readItemStack(buf));

				int i = dis.readInt();
				PlayerBeltData.get(other).BeltID = i;

				if(!other.getDisplayName().equals(p.getDisplayName())){
					other.setCurrentItemOrArmor(0, ByteBufUtils.readItemStack(buf));

					pbd.swordMemory[0] = ByteBufUtils.readItemStack(buf);
					pbd.swordMemory[1] = ByteBufUtils.readItemStack(buf);

					pbd.pickaxeMemory[0] = ByteBufUtils.readItemStack(buf);
					pbd.pickaxeMemory[1] = ByteBufUtils.readItemStack(buf);

					pbd.toolMemory[0] = ByteBufUtils.readItemStack(buf);
					pbd.toolMemory[1] = ByteBufUtils.readItemStack(buf);
				}

				break;

			case 3:
				PlayerBeltData pbd2 = PlayerBeltData.get(p);

				pbd2.swordMemory[0] =ByteBufUtils.readItemStack(buf);				
				pbd2.swordMemory[1] =ByteBufUtils.readItemStack(buf);				

				pbd2.pickaxeMemory[0] =ByteBufUtils.readItemStack(buf);				
				pbd2.pickaxeMemory[1] =ByteBufUtils.readItemStack(buf);				

				pbd2.toolMemory[0] =ByteBufUtils.readItemStack(buf);				
				pbd2.toolMemory[1] =ByteBufUtils.readItemStack(buf);		

				break;
			}

			dis.close();

		} catch (Exception e) {

		}
	}
}
