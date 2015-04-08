package blue3D;

import org.lwjgl.opengl.GL11;

public class Skybox {
	
	static CameraF tmpCamera=new CameraF();
	
	static Shader textureShader;
	
	private Texture skyboxPx;
	private Texture skyboxNx;
	private Texture skyboxNz;
	private Texture skyboxPz;
	private Texture skyboxNy;
	private Texture skyboxPy;
	
	
	/**
	 * Create a skybox from the pictures Px.bmp, Nx.bmp, Py.bmp, Ny.bmp, Pz.bmp, and Nz.bmp.
	 * @param path path to the folder containing the skybox images.
	 */
	public Skybox(String path) {
		skyboxPx=new Texture(path+"pos_x.bmp");
		skyboxPy=new Texture(path+"pos_y.bmp");
		skyboxPz=new Texture(path+"pos_z.bmp");
		
		skyboxNx=new Texture(path+"neg_x.bmp");
		skyboxNy=new Texture(path+"neg_y.bmp");
		skyboxNz=new Texture(path+"neg_z.bmp");
		
		if (textureShader==null){
			textureShader=new Shader(
					"#version 330 core\n" +
					"#extension GL_ARB_explicit_uniform_location : require\n" +
					"layout(location=8) uniform mat4 worldMatrix;\n" +
					"in vec4 gl_Vertex;\n" +
					//"in vec4 gl_Color;\n" +
					"in vec2 gl_MultiTexCoord0;\n" +
					"out vec4 outcol;\n" +
					"out vec2 texCoord;\n" +
					"void main() {\n" +
					"  gl_Position = worldMatrix * gl_Vertex;\n" +
					"  texCoord=gl_MultiTexCoord0;\n" +
					"}"
					, 
					/*"#version 330 core\n" +
					"in vec4 outcol;" +
					"out vec4 gl_FragColor;" +
					"void main() {" +
					"gl_FragColor = outcol;\n" +
					"}"*/
					
					"#version 330 core\n" +
					"#extension GL_ARB_explicit_uniform_location : require\n" +
					"in vec2 texCoord;\n" +
					"out vec4 gl_FragColor;\n" +
					"uniform sampler2D texSampler;\n" +
					"void main() {\n" +
					"  gl_FragColor = texture(texSampler, texCoord);\n" +
					"}"
					);
		}
	}
	
	
	/**
	 * render the 6-faced skybox texture, in the correct location.
	 */
	public void renderSkybox() {
		{	
			
			Camera camera=MatrixHandler.getCamera();
			
			if (tmpCamera.getWidth()!=camera.getWidth() || tmpCamera.getHeight()!=camera.getHeight()){
				tmpCamera.resize(camera.getWidth(), camera.getHeight());
			}
			
			if (camera instanceof CameraD)
				tmpCamera.orientation().set(((CameraD)camera).orientation());
			else if (camera instanceof CameraL)
				tmpCamera.orientation().set(((CameraL)camera).orientation());
			
			textureShader.use();
			
			tmpCamera.use();
			MatrixHandler.calculateCamera();
			MatrixHandler.load();
			
			
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			
			skyboxNz.use(8);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(5000, -5000, -5000);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(5000, 5000, -5000);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(-5000, 5000, -5000);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(-5000, -5000, -5000);
			
			
			GL11.glEnd();
			skyboxNy.use(8);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(-5000, -5000, -5000);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(-5000, -5000, 5000);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(5000, -5000, 5000);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(5000, -5000, -5000);
			
			GL11.glEnd();
			skyboxNx.use(8);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(-5000, 5000, -5000);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(-5000, 5000, 5000);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(-5000, -5000, 5000);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(-5000, -5000, -5000);
			
			GL11.glEnd();
			skyboxPx.use(8);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(5000, -5000, -5000);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(5000, -5000, 5000);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(5000, 5000, 5000);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(5000, 5000, -5000);
			
			GL11.glEnd();
			skyboxPy.use(8);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(5000, 5000, -5000);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(5000, 5000, 5000);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(-5000, 5000, 5000);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(-5000, 5000, -5000);
			
			GL11.glEnd();
			skyboxPz.use(8);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(5000, 5000, 5000);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(5000, -5000, 5000);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(-5000, -5000, 5000);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(-5000, 5000, 5000);
			
			GL11.glEnd();
			
			camera.use();
		}
	}
	
}
