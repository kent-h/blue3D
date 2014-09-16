==================
Blue3D Game Engine
==================

-------
Purpose
-------
To reduce the time required to program 3D applications, without inhibiting advanced functionality.

------------
Requirements
------------
LWJGL (Light Weight Java Game Library)

--------------------
Current Organization
--------------------
blue3D.type:
	Mathematic entities (matrices, vectors, quaternions), and base 3D objects.
	
blue3D.sample:
	Sample applications, each application should test and demonstrate how to use a single engine feature.

blue3D.bulletphysics:
	Physics engine, built on and from the JBullet library.  Work in Progress.

Eventually, the Engine should be organized into the following individual packages:
- Core
- OpenGL Graphics
- Physics Engine
- Other...

-----------------
Design Priorities
-----------------
(approximately in order)
- Ease of use.  A java programmer with little mathematical background should be able to pick up the engine
- Advanced use must continue to be possible. (Custom shaders, Direct world matrix manipulation, using OpenCL in the application, etc.)
		- This is done by having a default configuration for everything, which can be overridden by advanced users.
- Speed.  This is a game engine, everything must run at a high speed.
