package me.andreiwasfound.custompets;

import java.util.EnumSet;

import net.minecraft.server.v1_15_R1.EntityCreature;
import net.minecraft.server.v1_15_R1.EntityInsentient;
import net.minecraft.server.v1_15_R1.EntityLiving;
import net.minecraft.server.v1_15_R1.PathfinderGoal;
import net.minecraft.server.v1_15_R1.RandomPositionGenerator;
import net.minecraft.server.v1_15_R1.Vec3D;

public class PathfinderGoalPet extends PathfinderGoal {

	private final EntityInsentient a; // our pet
	private EntityLiving b; // owner <-- player
	
	private final double f; // pet's speed
	private final float g; // distance between owner and pet
	
	private double c; // x
	private double d; // y
	private double e; // z
	
	public PathfinderGoalPet(EntityInsentient a, double speed, float distance) {
		this.a = a;
		this.f = speed;
		this.g = distance;
		this.a(EnumSet.of(Type.MOVE));
	}
	
	@Override
	public boolean a() {
		// will start the pathfinding goal if it is true
		// runs every tick
		this.b = this.a.getGoalTarget();
		if (this.b == null)
			return false;
		else if (this.a.getDisplayName() == null)
			return false;
		else if (!(this.a.getDisplayName().toString().contains(this.b.getName())))
			return false;
		else if (this.b.h(this.a) > (double) (this.g * this.g)) {
			// teleports pet to owner
			a.setPosition(this.b.locX(), this.b.locY(), this.b.locZ());
			return false;
		}
		else {
			// follow the owner
			
			Vec3D vec = RandomPositionGenerator.a((EntityCreature)this.a, 16, 7, this.b.getPositionVector());
			// in air
			if (vec == null)
				return false;
			
			this.c = this.b.locX(); // x
			this.d = this.b.locY(); // y
			this.e = this.b.locZ(); // z
			return true; // <-- runs c()
		}
	}
	public void c() {
		// runner                   x       y      z     speed
		this.a.getNavigation().a(this.c, this.d, this.e, this.f);
	}
	
	public boolean b() {
		// runs after c()
		// run every tick as long as it is true (repeats c)
		return !this.a.getNavigation().m() && this.b.h(this.a) < (double) (this.g * this.g);
	}
	
	public void d() {
		// runs when b() returns false.
		this.b = null;
	}
}
