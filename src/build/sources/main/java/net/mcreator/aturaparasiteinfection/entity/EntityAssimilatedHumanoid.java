
package net.mcreator.aturaparasiteinfection.entity;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelBase;

import net.mcreator.aturaparasiteinfection.procedure.ProcedureParasiteKill;
import net.mcreator.aturaparasiteinfection.procedure.ProcedureParasiteDeath;
import net.mcreator.aturaparasiteinfection.ElementsAturaParasiteInfection;

import java.util.Iterator;
import java.util.ArrayList;

@ElementsAturaParasiteInfection.ModElement.Tag
public class EntityAssimilatedHumanoid extends ElementsAturaParasiteInfection.ModElement {
	public static final int ENTITYID = 1;
	public static final int ENTITYID_RANGED = 2;
	public EntityAssimilatedHumanoid(ElementsAturaParasiteInfection instance) {
		super(instance, 1);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> EntityEntryBuilder.create().entity(EntityCustom.class)
				.id(new ResourceLocation("aturaparasiteinfection", "assimilatedhumanoid"), ENTITYID).name("assimilatedhumanoid").tracker(64, 3, true)
				.egg(-1, -1).build());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		Biome[] spawnBiomes = allbiomes(Biome.REGISTRY);
		EntityRegistry.addSpawn(EntityCustom.class, 20, 3, 30, EnumCreatureType.MONSTER, spawnBiomes);
	}

	private Biome[] allbiomes(net.minecraft.util.registry.RegistryNamespaced<ResourceLocation, Biome> in) {
		Iterator<Biome> itr = in.iterator();
		ArrayList<Biome> ls = new ArrayList<Biome>();
		while (itr.hasNext())
			ls.add(itr.next());
		return ls.toArray(new Biome[ls.size()]);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityCustom.class, renderManager -> {
			return new RenderLiving(renderManager, new Modelassimilatedhumanoid(), 0.5f) {
				protected ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("aturaparasiteinfection:textures/assimilatedhumanoid_texture.png");
				}
			};
		});
	}
	public static class EntityCustom extends EntityMob {
		public EntityCustom(World world) {
			super(world);
			setSize(0.6f, 1.8f);
			experienceValue = 5;
			this.isImmuneToFire = false;
			setNoAI(!true);
		}

		@Override
		protected void initEntityAI() {
			super.initEntityAI();
			this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
			this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.2, true));
			this.tasks.addTask(3, new EntityAIWander(this, 1));
			this.tasks.addTask(4, new EntityAILookIdle(this));
			this.tasks.addTask(5, new EntityAISwimming(this));
			this.tasks.addTask(6, new EntityAILeapAtTarget(this, (float) 0.8));
			this.targetTasks.addTask(7, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false, false));
			this.targetTasks.addTask(8, new EntityAINearestAttackableTarget(this, EntityPlayerMP.class, false, false));
			this.targetTasks.addTask(9, new EntityAINearestAttackableTarget(this, EntityAnimal.class, false, false));
		}

		@Override
		public EnumCreatureAttribute getCreatureAttribute() {
			return EnumCreatureAttribute.UNDEFINED;
		}

		@Override
		protected Item getDropItem() {
			return null;
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.death"));
		}

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		@Override
		public void onDeath(DamageSource source) {
			super.onDeath(source);
			int x = (int) this.posX;
			int y = (int) this.posY;
			int z = (int) this.posZ;
			Entity entity = this;
			{
				java.util.HashMap<String, Object> $_dependencies = new java.util.HashMap<>();
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				ProcedureParasiteDeath.executeProcedure($_dependencies);
			}
		}

		@Override
		public void onKillEntity(EntityLivingBase entity) {
			super.onKillEntity(entity);
			int x = (int) this.posX;
			int y = (int) this.posY;
			int z = (int) this.posZ;
			{
				java.util.HashMap<String, Object> $_dependencies = new java.util.HashMap<>();
				$_dependencies.put("entity", entity);
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				ProcedureParasiteKill.executeProcedure($_dependencies);
			}
		}

		@Override
		protected void applyEntityAttributes() {
			super.applyEntityAttributes();
			if (this.getEntityAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
			if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6D);
		}
	}

	public static class Modelassimilatedhumanoid extends ModelBase {
		private final ModelRenderer Head;
		private final ModelRenderer Head_r1;
		private final ModelRenderer Body;
		private final ModelRenderer bone;
		private final ModelRenderer Body_r1;
		private final ModelRenderer Body_r2;
		private final ModelRenderer Body_r3;
		private final ModelRenderer Body_r4;
		private final ModelRenderer Body_r5;
		private final ModelRenderer Body_r6;
		private final ModelRenderer bone2;
		private final ModelRenderer cube_r1;
		private final ModelRenderer cube_r2;
		private final ModelRenderer flowers;
		private final ModelRenderer cube_r3;
		private final ModelRenderer cube_r4;
		private final ModelRenderer cube_r5;
		private final ModelRenderer cube_r6;
		private final ModelRenderer cube_r7;
		private final ModelRenderer cube_r8;
		private final ModelRenderer cube_r9;
		private final ModelRenderer cube_r10;
		private final ModelRenderer cube_r11;
		private final ModelRenderer cube_r12;
		private final ModelRenderer RightArm;
		private final ModelRenderer RightArm_r1;
		private final ModelRenderer LeftArm;
		private final ModelRenderer LeftArm_r1;
		private final ModelRenderer RightLeg;
		private final ModelRenderer LeftLeg;
		public Modelassimilatedhumanoid() {
			textureWidth = 128;
			textureHeight = 128;
			Head = new ModelRenderer(this);
			Head.setRotationPoint(0.0F, 0.0F, 0.0F);
			Head_r1 = new ModelRenderer(this);
			Head_r1.setRotationPoint(0.0F, -8.0F, 0.0F);
			Head.addChild(Head_r1);
			setRotationAngle(Head_r1, 0.3054F, 0.0F, 0.0F);
			Head_r1.cubeList.add(new ModelBox(Head_r1, 0, 0, -4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F, false));
			Body = new ModelRenderer(this);
			Body.setRotationPoint(0.0F, 0.0F, 0.0F);
			bone = new ModelRenderer(this);
			bone.setRotationPoint(0.0F, 12.0F, 0.0F);
			Body.addChild(bone);
			bone.cubeList.add(new ModelBox(bone, 0, 16, -4.0F, -4.0F, -2.0F, 8, 4, 4, 0.0F, false));
			Body_r1 = new ModelRenderer(this);
			Body_r1.setRotationPoint(2.0F, -10.0F, 1.0F);
			bone.addChild(Body_r1);
			setRotationAngle(Body_r1, 0.2396F, -0.6519F, -0.0425F);
			Body_r1.cubeList.add(new ModelBox(Body_r1, 0, 40, -2.0F, -2.0F, -4.0F, 4, 4, 4, 0.0F, false));
			Body_r2 = new ModelRenderer(this);
			Body_r2.setRotationPoint(2.0F, -8.0F, 1.0F);
			bone.addChild(Body_r2);
			setRotationAngle(Body_r2, 0.1309F, -0.3927F, 0.0F);
			Body_r2.cubeList.add(new ModelBox(Body_r2, 16, 46, -2.0F, 0.0F, -4.0F, 4, 2, 4, 0.0F, false));
			Body_r3 = new ModelRenderer(this);
			Body_r3.setRotationPoint(2.0F, -8.0F, 2.0F);
			bone.addChild(Body_r3);
			setRotationAngle(Body_r3, 0.0F, -0.2182F, 0.0F);
			Body_r3.cubeList.add(new ModelBox(Body_r3, 32, 46, -2.0F, 2.0F, -4.0F, 4, 2, 4, 0.0F, false));
			Body_r4 = new ModelRenderer(this);
			Body_r4.setRotationPoint(-2.0F, -10.0F, 1.0F);
			bone.addChild(Body_r4);
			setRotationAngle(Body_r4, 0.2396F, 0.6519F, 0.0425F);
			Body_r4.cubeList.add(new ModelBox(Body_r4, 32, 32, -2.0F, -2.0F, -4.0F, 4, 4, 4, 0.0F, false));
			Body_r5 = new ModelRenderer(this);
			Body_r5.setRotationPoint(-2.0F, -8.0F, 1.0F);
			bone.addChild(Body_r5);
			setRotationAngle(Body_r5, 0.1309F, 0.3927F, 0.0F);
			Body_r5.cubeList.add(new ModelBox(Body_r5, 32, 40, -2.0F, 0.0F, -4.0F, 4, 2, 4, 0.0F, false));
			Body_r6 = new ModelRenderer(this);
			Body_r6.setRotationPoint(-2.0F, -8.0F, 2.0F);
			bone.addChild(Body_r6);
			setRotationAngle(Body_r6, 0.0F, 0.2182F, 0.0F);
			Body_r6.cubeList.add(new ModelBox(Body_r6, 16, 40, -2.0F, 2.0F, -4.0F, 4, 2, 4, 0.0F, false));
			bone2 = new ModelRenderer(this);
			bone2.setRotationPoint(0.0F, 8.0F, 2.0F);
			Body.addChild(bone2);
			bone2.cubeList.add(new ModelBox(bone2, 40, 52, -1.0F, -9.0F, 0.75F, 2, 5, 2, 0.0F, false));
			cube_r1 = new ModelRenderer(this);
			cube_r1.setRotationPoint(0.0F, -9.0F, 2.0F);
			bone2.addChild(cube_r1);
			setRotationAngle(cube_r1, 0.3927F, 0.0F, 0.0F);
			cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 58, -1.0F, -5.0F, -1.25F, 2, 5, 2, 0.0F, false));
			cube_r2 = new ModelRenderer(this);
			cube_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
			bone2.addChild(cube_r2);
			setRotationAngle(cube_r2, -0.3927F, 0.0F, 0.0F);
			cube_r2.cubeList.add(new ModelBox(cube_r2, 24, 16, -1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F, false));
			flowers = new ModelRenderer(this);
			flowers.setRotationPoint(0.0F, 0.0F, 0.0F);
			Body.addChild(flowers);
			cube_r3 = new ModelRenderer(this);
			cube_r3.setRotationPoint(-2.0F, -7.5F, -5.5F);
			flowers.addChild(cube_r3);
			setRotationAngle(cube_r3, -2.9447F, 0.5357F, 1.102F);
			cube_r3.cubeList.add(new ModelBox(cube_r3, 48, 0, 0.0F, -2.5F, -2.5F, 0, 5, 5, 0.0F, false));
			cube_r4 = new ModelRenderer(this);
			cube_r4.setRotationPoint(-2.0F, -7.5F, -5.5F);
			flowers.addChild(cube_r4);
			setRotationAngle(cube_r4, 2.5973F, 0.169F, -0.5703F);
			cube_r4.cubeList.add(new ModelBox(cube_r4, 48, 0, 0.0F, -2.5F, -2.5F, 0, 5, 5, 0.0F, false));
			cube_r5 = new ModelRenderer(this);
			cube_r5.setRotationPoint(2.0F, -6.5F, -4.5F);
			flowers.addChild(cube_r5);
			setRotationAngle(cube_r5, -2.7489F, -0.48F, -0.3927F);
			cube_r5.cubeList.add(new ModelBox(cube_r5, 48, 0, 0.0F, -2.5F, -2.5F, 0, 5, 5, 0.0F, false));
			cube_r6 = new ModelRenderer(this);
			cube_r6.setRotationPoint(2.0F, -6.5F, -4.5F);
			flowers.addChild(cube_r6);
			setRotationAngle(cube_r6, 2.6285F, -0.3463F, 1.3671F);
			cube_r6.cubeList.add(new ModelBox(cube_r6, 48, 0, 0.0F, -2.5F, -2.5F, 0, 5, 5, 0.0F, false));
			cube_r7 = new ModelRenderer(this);
			cube_r7.setRotationPoint(-3.0F, 3.5F, -3.5F);
			flowers.addChild(cube_r7);
			setRotationAngle(cube_r7, -2.618F, 0.7418F, 0.0F);
			cube_r7.cubeList.add(new ModelBox(cube_r7, 48, 0, 0.0F, -2.5F, -2.5F, 0, 5, 5, 0.0F, false));
			cube_r8 = new ModelRenderer(this);
			cube_r8.setRotationPoint(-3.0F, 3.5F, -3.5F);
			flowers.addChild(cube_r8);
			setRotationAngle(cube_r8, 2.328F, 0.3775F, -1.9427F);
			cube_r8.cubeList.add(new ModelBox(cube_r8, 48, 0, 0.0F, -2.5F, -2.5F, 0, 5, 5, 0.0F, false));
			cube_r9 = new ModelRenderer(this);
			cube_r9.setRotationPoint(2.0F, 5.5F, -3.5F);
			flowers.addChild(cube_r9);
			setRotationAngle(cube_r9, -2.618F, -0.5236F, 0.0F);
			cube_r9.cubeList.add(new ModelBox(cube_r9, 48, 0, 0.0F, -2.5F, -2.5F, 0, 5, 5, 0.0F, false));
			cube_r10 = new ModelRenderer(this);
			cube_r10.setRotationPoint(2.0F, 5.5F, -3.5F);
			flowers.addChild(cube_r10);
			setRotationAngle(cube_r10, -2.5536F, 0.4478F, -1.2898F);
			cube_r10.cubeList.add(new ModelBox(cube_r10, 48, 0, 0.0F, -2.5F, -2.5F, 0, 5, 5, 0.0F, false));
			cube_r11 = new ModelRenderer(this);
			cube_r11.setRotationPoint(-2.0F, 8.5F, -2.5F);
			flowers.addChild(cube_r11);
			setRotationAngle(cube_r11, -2.8464F, 0.298F, 0.5427F);
			cube_r11.cubeList.add(new ModelBox(cube_r11, 48, 0, 0.0F, -2.5F, -2.5F, 0, 5, 5, 0.0F, false));
			cube_r12 = new ModelRenderer(this);
			cube_r12.setRotationPoint(-2.0F, 8.5F, -2.5F);
			flowers.addChild(cube_r12);
			setRotationAngle(cube_r12, 2.831F, 0.2818F, -1.1171F);
			cube_r12.cubeList.add(new ModelBox(cube_r12, 48, 0, 0.0F, -2.5F, -2.5F, 0, 5, 5, 0.0F, false));
			RightArm = new ModelRenderer(this);
			RightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
			RightArm_r1 = new ModelRenderer(this);
			RightArm_r1.setRotationPoint(0.0F, -2.0F, 0.0F);
			RightArm.addChild(RightArm_r1);
			setRotationAngle(RightArm_r1, 0.0152F, 0.0859F, 0.1752F);
			RightArm_r1.cubeList.add(new ModelBox(RightArm_r1, 0, 24, -4.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
			LeftArm = new ModelRenderer(this);
			LeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
			LeftArm_r1 = new ModelRenderer(this);
			LeftArm_r1.setRotationPoint(0.0F, -2.0F, 0.0F);
			LeftArm.addChild(LeftArm_r1);
			setRotationAngle(LeftArm_r1, 0.0152F, -0.0859F, -0.1752F);
			LeftArm_r1.cubeList.add(new ModelBox(LeftArm_r1, 16, 24, 0.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
			RightLeg = new ModelRenderer(this);
			RightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
			RightLeg.cubeList.add(new ModelBox(RightLeg, 32, 0, -2.1F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
			LeftLeg = new ModelRenderer(this);
			LeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
			LeftLeg.cubeList.add(new ModelBox(LeftLeg, 32, 16, -1.9F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			Head.render(f5);
			Body.render(f5);
			RightArm.render(f5);
			LeftArm.render(f5);
			RightLeg.render(f5);
			LeftLeg.render(f5);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
			super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
			this.LeftLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.RightLeg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		}
	}
}
