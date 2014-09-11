package blue3D;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;


public class DepthTexture {
	
	
	private int textureId, width, height;
	private boolean writeOnly;
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param writeOnly write-only depth textures are used where depth testing is required, but reading the result in a shader is not
	 * @param depthBitCount either 16, 24, or 32 bits can be used. Other values will have undefined results.
	 */
	DepthTexture(int width, int height, boolean writeOnly, int depthBitCount){
		
		int depth=GL14.GL_DEPTH_COMPONENT24;
		
		switch(depthBitCount){
		case(16): depth=GL14.GL_DEPTH_COMPONENT16;
		break;
		case(24): depth=GL14.GL_DEPTH_COMPONENT24;
		break;
		case(32): depth=GL14.GL_DEPTH_COMPONENT32;
		break;
		}
		
		this.width=width;
		this.height=height;
		
		if (writeOnly){//if write-only, use a renderBuffer, since it is more optimized
			
			textureId=GL30.glGenRenderbuffers();
			GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, textureId);
			GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, depth, width, height);//possibly should be GL11.GL_DEPTH_COMPONENT instead of 'depth'
			GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, textureId);
			GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
			
		}else{//if not read-only, use a depth texture. (currently 16 bits)
			
			textureId = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
			
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			
			//do not blur the pixels
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST); 
			
			// The null at the end must be cast to tell javac which overload to use,
			// even though the last parameter (for the different overloads) isn't even used
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, depth, width, height, 0,GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (java.nio.ByteBuffer) null);
			
			// Unbind texture
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		}
	
	}
	
	
	public int getId(){
		return textureId;
	}
	
	
	public int getWidth(){
		return width;
	}
	
	
	public int getHeight(){
		return height;
	}
	
	
	public boolean writeOnly(){
		return writeOnly;
	}
	
	
}
