package toolbelts;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerBeltLoginTracker {


	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayer && PlayerBeltData.get((EntityPlayer) event.entity) == null)
			PlayerBeltData.register((EntityPlayer) event.entity);
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if (!event.entity.worldObj.isRemote && (event.entity instanceof EntityPlayer))
			PlayerBeltData.loadProxyData((EntityPlayer) event.entity);
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if (!event.entity.worldObj.isRemote && (event.entity instanceof EntityPlayer))
			PlayerBeltData.saveProxyData((EntityPlayer) event.entity);
	}
}
