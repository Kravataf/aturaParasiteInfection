package net.mcreator.aturaparasiteinfection.procedure;

import net.minecraft.world.World;

import net.mcreator.aturaparasiteinfection.ElementsAturaParasiteInfection;

@ElementsAturaParasiteInfection.ModElement.Tag
public class ProcedureParasiteDeath extends ElementsAturaParasiteInfection.ModElement {
	public ProcedureParasiteDeath(ElementsAturaParasiteInfection instance) {
		super(instance, 6);
	}

	public static void executeProcedure(java.util.HashMap<String, Object> dependencies) {
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure ParasiteDeath!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure ParasiteDeath!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure ParasiteDeath!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure ParasiteDeath!");
			return;
		}
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		if (!world.isRemote) {
			world.createExplosion(null, (int) x, (int) y, (int) z, (float) 1, true);
		}
	}
}
