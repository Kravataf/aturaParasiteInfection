package net.mcreator.aturaparasiteinfection.procedure;

import net.minecraftforge.fml.common.FMLCommonHandler;

import net.minecraft.world.World;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.server.MinecraftServer;

import net.mcreator.aturaparasiteinfection.ElementsAturaParasiteInfection;
import net.mcreator.aturaparasiteinfection.AturaParasiteInfectionVariables;

@ElementsAturaParasiteInfection.ModElement.Tag
public class ProcedureGetPointsCommand extends ElementsAturaParasiteInfection.ModElement {
	public ProcedureGetPointsCommand(ElementsAturaParasiteInfection instance) {
		super(instance, 5);
	}

	public static void executeProcedure(java.util.HashMap<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure GetPointsCommand!");
			return;
		}
		World world = (World) dependencies.get("world");
		{
			MinecraftServer mcserv = FMLCommonHandler.instance().getMinecraftServerInstance();
			if (mcserv != null)
				mcserv.getPlayerList().sendMessage(
						new TextComponentString((("Atura Points: ") + "" + ((AturaParasiteInfectionVariables.MapVariables.get(world).points)))));
		}
	}
}
