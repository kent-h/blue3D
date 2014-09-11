package blue3D;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;


public class Shader {
	boolean isValid=true;
	int shaderId;
	
	int vShader, fShader, gShader;
	
	/**
	 * default shader, works with the loaded matrix and colour only.
	 */
	public Shader(){
			String vertex="#version 330 core\n" +
			"#extension GL_ARB_explicit_uniform_location : require\n" +
			"layout(location=8) uniform mat4 worldMatrix;" +
			"in vec4 gl_Vertex;" +
			"in vec4 gl_Color;" +
			"out vec4 outcol;" +
			"void main() {" +
			"  gl_Position = worldMatrix * gl_Vertex;" +
			"  outcol=gl_Color;" +
			"}";
			String fragment="#version 330 core\n" +
			"in vec4 outcol;" +
			"out vec4 gl_FragColor;" +
			"void main() {" +
			"gl_FragColor = outcol;\n" +
			"}";
			shaderId = GL20.glCreateProgram();
			vShader=createShader(vertex, GL20.GL_VERTEX_SHADER);
			GL20.glAttachShader(shaderId, vShader);
			fShader=createShader(fragment, GL20.GL_FRAGMENT_SHADER);
			GL20.glAttachShader(shaderId, fShader);
			GL20.glLinkProgram(shaderId);
	}
	
	
	public Shader(String vertex, String fragment){
		shaderId = GL20.glCreateProgram();
		vShader=createShader(vertex, GL20.GL_VERTEX_SHADER);
		GL20.glAttachShader(shaderId, vShader);
		fShader=createShader(fragment, GL20.GL_FRAGMENT_SHADER);
		GL20.glAttachShader(shaderId, fShader);
		GL20.glLinkProgram(shaderId);
	}
	
	
	public Shader(String vertex, String fragment, String geometry){
		shaderId = GL20.glCreateProgram();
		vShader=createShader(vertex, GL20.GL_VERTEX_SHADER);
		GL20.glAttachShader(shaderId, vShader);
		fShader=createShader(fragment, GL20.GL_FRAGMENT_SHADER);
		GL20.glAttachShader(shaderId, fShader);
		gShader=createShader(geometry, GL32.GL_GEOMETRY_SHADER);
		GL20.glAttachShader(shaderId, gShader);
		GL20.glLinkProgram(shaderId);
	}
	
	
	/**
	 * use this shader for all further drawing
	 * @return 
	 */
	public Shader use(){
		GL20.glUseProgram(shaderId);
		return this;
	}
	
	
	/** 
	 * internal shader setup
	 * @param s shader code
	 * @param type type of shader to create
	 * @return the new shader's id
	 */
	private static int createShader(String s, int type){
		int shaderId=GL20.glCreateShader(type);
		GL20.glShaderSource(shaderId, s);
		GL20.glCompileShader(shaderId);
		String log=GL20.glGetShaderInfoLog(shaderId,200);
		if (!log.equals("")){
			Dev.print(s+"\n\n  "+log);
		}
		return shaderId;
	}
	
	
	/**
	 * cleanup, this can safely be called multiple times
	 */
	public void finalize(){
		if (isValid){
			isValid=false;
			GL20.glDetachShader(shaderId, fShader);//detach shaders
			GL20.glDetachShader(shaderId, vShader);
			GL20.glDetachShader(shaderId, gShader);
			
			GL20.glDeleteShader(fShader);//delete shaders
			GL20.glDeleteShader(vShader);
			GL20.glDeleteShader(gShader);
			
			GL20.glDeleteProgram(shaderId);//delete program
			shaderId=0;
		}
	}
	
	
}

























