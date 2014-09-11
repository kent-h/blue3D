package blue3D.sample;

import blue3D.bulletphysics.collision.broadphase.BroadphaseInterface;
import blue3D.bulletphysics.collision.broadphase.DbvtBroadphase;
import blue3D.bulletphysics.collision.dispatch.CollisionDispatcher;
import blue3D.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import blue3D.bulletphysics.collision.shapes.CollisionShape;
import blue3D.bulletphysics.collision.shapes.SphereShape;
import blue3D.bulletphysics.collision.shapes.StaticPlaneShape;
import blue3D.bulletphysics.dynamics.DiscreteDynamicsWorld;
import blue3D.bulletphysics.dynamics.RigidBody;
import blue3D.bulletphysics.dynamics.RigidBodyConstructionInfo;
import blue3D.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import blue3D.bulletphysics.linearmath.DefaultMotionState;
import blue3D.bulletphysics.linearmath.Transform;
import blue3D.type.Matrix4f;
import blue3D.type.QuaternionF;
import blue3D.type.Vector3f;

public class JBulletHelloWorld {
	
	public static void main(String[] args){
		helloWorld();
	}
	
	public static void helloWorld() {
		BroadphaseInterface broadphase = new DbvtBroadphase();
		DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
		CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);

		SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();

		DiscreteDynamicsWorld dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
		
		// set the gravity of our world
		dynamicsWorld.setGravity(new Vector3f(0, -9.80665f, 0));

		// setup our collision shapes
		CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0, 0.98480775301f, 0.17364817766f), 1);
		CollisionShape fallShape = new SphereShape(1);
		
		// setup the motion state
		DefaultMotionState groundMotionState = new DefaultMotionState(new Transform(new QuaternionF().calculateMatrix(new Matrix4f()).translate(new Vector3f(0, -1, 0)))); 

		RigidBodyConstructionInfo groundRigidBodyCI = new RigidBodyConstructionInfo(0, groundMotionState, groundShape, new Vector3f(0,0,0)); 
		RigidBody groundRigidBody = new RigidBody(groundRigidBodyCI); 

		dynamicsWorld.addRigidBody(groundRigidBody); // add our ground to the dynamic world.. 
		
		// setup the motion state for the ball
		DefaultMotionState fallMotionState = new DefaultMotionState(new Transform(new QuaternionF(0,0,1,0).calculateMatrix(new Matrix4f()).translate(new Vector3f(0, 50, 0))));

		//give it mass so it responds to gravity 
		int mass = 1;

		Vector3f fallInertia = new Vector3f(0,0,0); 
		fallShape.calculateLocalInertia(mass,fallInertia); 

		RigidBodyConstructionInfo fallRigidBodyCI = new RigidBodyConstructionInfo(mass,fallMotionState,fallShape,fallInertia); 
		RigidBody fallRigidBody = new RigidBody(fallRigidBodyCI); 

		//now we add it to our physics simulation 
		dynamicsWorld.addRigidBody(fallRigidBody); 
		
		long startTime=System.nanoTime();
		
		for (int i=0 ; i<30*60; i++) { 
//			Transform trans = new Transform();
//		  fallRigidBody.getMotionState().getWorldTransform(trans);
		  
//			System.out.println(trans.origin);  
//		  System.out.println(trans.origin);
//		  Display.sync(30);
		  dynamicsWorld.stepSimulation(1/30f, 20);
//		  dynamicsWorld.stepSimulation(1/60f, 10);
		}
		
		System.out.println((System.nanoTime()-startTime)/1000000000f+" seconds");
	}
	
}
