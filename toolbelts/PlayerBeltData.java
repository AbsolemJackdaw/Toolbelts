package toolbelts;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import toolbelts.proxy.CommonHipProxy;
import cpw.mods.fml.common.FMLLog;

public class PlayerBeltData implements IExtendedEntityProperties {


	public final static String EXT_PROP_NAME = "BeltPropertiesID";

	private static final String tagName = "BeltID";

	public int BeltID = 0;

	public ItemStack[] swordMemory = new ItemStack[2];
	public boolean[] swordMem = new boolean[2];

	public ItemStack[] pickaxeMemory = new ItemStack[2];
	public boolean[] pickaxeMem = new boolean[2];

	public ItemStack[] toolMemory = new ItemStack[2];
	public boolean[] toolMem = new boolean[2];


	private static String getSaveKey(EntityPlayer player) {
		return player.getDisplayName() + ":" + EXT_PROP_NAME;
	}

	public static void loadProxyData(EntityPlayer player) {
		PlayerBeltData playerData = PlayerBeltData.get(player);
		NBTTagCompound savedData = CommonHipProxy.getEntityData(getSaveKey(player));

		if(savedData != null)
			playerData.loadNBTData(savedData);
	}

	public static void saveProxyData(EntityPlayer player) {
		PlayerBeltData playerData = PlayerBeltData.get(player);
		NBTTagCompound savedData = new NBTTagCompound();

		playerData.saveNBTData(savedData);
		CommonHipProxy.storeEntityData(getSaveKey(player), savedData);
	}

	public static final void register(EntityPlayer player){
		player.registerExtendedProperties(EXT_PROP_NAME, new PlayerBeltData());
		FMLLog.getLogger().info("Player properties registered for ToolBelt" );
	}

	public static final PlayerBeltData get(EntityPlayer p){
		return (PlayerBeltData) p.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setInteger(tagName, BeltID);

//		NBTTagList nbttaglist = new NBTTagList();
//		for (int i = 0; i < 2; ++i)
//		{
//			if (this.swordMemory[i] != null)
//			{
//				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
//				nbttagcompound1.setByte("Memory", (byte)i);
//				this.swordMemory[i].writeToNBT(nbttagcompound1);
//				nbttaglist.appendTag(nbttagcompound1);
//			}
//		}
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		BeltID = compound.getInteger(tagName);

//		NBTTagList nbttaglist = compound.getTagList("Items", 10);
//		
//		for (int i = 0; i < nbttaglist.tagCount(); ++i)
//		{
//			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
//			int j = nbttagcompound1.getByte("Memory") & 255;
//
//			if (j >= 0 && j < swordMemory.length)
//			{
//				swordMemory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
//			}
//		}
	}

	@Override
	public void init(Entity entity, World world) {

	}
}
