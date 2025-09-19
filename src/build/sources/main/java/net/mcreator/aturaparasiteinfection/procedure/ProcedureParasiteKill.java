package net.mcreator.aturaparasiteinfection.procedure;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;

import net.mcreator.aturaparasiteinfection.entity.EntityAssimilatedHumanoid;
import net.mcreator.aturaparasiteinfection.ElementsAturaParasiteInfection;
import net.mcreator.aturaparasiteinfection.AturaParasiteInfectionVariables;

@ElementsAturaParasiteInfection.ModElement.Tag
public class ProcedureParasiteKill extends ElementsAturaParasiteInfection.ModElement {
	public ProcedureParasiteKill(ElementsAturaParasiteInfection instance) {
		super(instance, 2);
	}

	public static void executeProcedure(java.util.HashMap<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure ParasiteKill!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure ParasiteKill!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure ParasiteKill!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure ParasiteKill!");
			return;
		}
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		AturaParasiteInfectionVariables.MapVariables
				.get(world).points = (double) ((AturaParasiteInfectionVariables.MapVariables.get(world).points) + 1);
		AturaParasiteInfectionVariables.MapVariables.get(world).syncData(world);
		if (!world.isRemote) {
			Entity entityToSpawn = new EntityAssimilatedHumanoid.EntityCustom(world);
			if (entityToSpawn != null) {
				entityToSpawn.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360F, 0.0F);
				world.spawnEntity(entityToSpawn);
			}
		}
	}
}
