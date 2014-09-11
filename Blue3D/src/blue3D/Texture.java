package blue3D;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;


public class Texture {
	
	private int textureId, width, height;
	
	
	/**
	 * @param buffer a FLIPPED buffer, containing an image
	 */
	public Texture(int w, int h, ByteBuffer buffer) {
		width=w;
		height=h;
		glSetup(width, height, buffer);
	}
	
	
	/**
	 * can only import 24-bit bitmaps for now.
	 * @param filePath location of the texture to load
	 */
	public Texture(String filePath){
		
		File file=new File(filePath);
		
		try {
			FileInputStream in=new FileInputStream(file);
			byte[] header=new byte[54];
			
			//check for valid header
			if(in.read(header)!=-1){
				if(header[0]==66 && header[1]==77){
					//read attributes
					int dataPos	= header[10]|header[11]<<8|header[12]<<16|header[13]<<24,
					imageSize  	= header[34]|header[35]<<8|header[36]<<16|header[37]<<24;
					width      	= header[18]|header[19]<<8|header[20]<<16|header[21]<<24;
					height     	= header[22]|header[23]<<8|header[24]<<16|header[25]<<24;
					//confirm valid attributes
					if (imageSize==0)    imageSize=width*height*3; // 3 : one byte for each Red, Green and Blue component
					if (dataPos==0)      dataPos=54;
					
					//allocate temporary ram space
					byte[] tmpBuff=new byte[imageSize];
					//allocate gpu space
					ByteBuffer buffer = ByteBuffer.allocateDirect(imageSize);
					//fast forward to start of the data
					in.skip(54-dataPos);
					
					//read actual image data
					in.read(tmpBuff);
					
					for(int i=0;i<imageSize;i+=3){
						buffer.put(tmpBuff[i+2]);
						buffer.put(tmpBuff[i+1]);
						buffer.put(tmpBuff[i]);
					}
					
					
					//send to gpu
					buffer.flip();
					//activate opengl ties
					glSetup(width, height, buffer);
				}
				
			}else{
				Dev.print("invalid texture file");
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			Dev.print(e);
		} catch (IOException e){
			Dev.print(e);
		}
		
	}
	
	
	/**
	 * creates a texture, which can be rendered to
	 */
	public Texture(int width, int height){
		this.width=width;
		this.height=height;
		glSetup(width, height, null);
	}
	
	
	public int getWidth(){
		return width;
	}
	
	
	public int getHeight(){
		return height;
	}
	
	
	private void glSetup(int width, int height, ByteBuffer buffer){
	// Attach a texture
			textureId = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
			//set access options
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			//create the texture from the given texture
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,GL11.GL_RGB, width, height, 0,GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
			// Unbind texture
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			
	}
	
	
	public void use(int location){
		//GL13.glActiveTexture(GL13.GL_TEXTURE0+location);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
	}
	
	
	public int getId() {
		return textureId;
	}
	
	
	public void finalize(){
		GL11.glDeleteTextures(textureId);
	}
	
	
	/////////////////////////////////////////////static methods/////////////////////////////////////////////
	
	
	static Texture colorRenderTexture;
	static DepthTexture depthRenderTexture;
	static boolean FBOIsBound=false;
	
	//whether or not this texture has been prepared for rendering
	private static boolean readyForRendering=false;
	
	//used to render to this texture
	private static int FBOId;
	
	
	private static void prepareRenderTargeting(){
		if (!readyForRendering){
			readyForRendering=true;
			
			int FBOId = EXTFramebufferObject.glGenFramebuffersEXT();
			EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, FBOId);
			
			//no draw or read buffer is used
			GL11.glDrawBuffer(GL11.GL_NONE);
			GL11.glReadBuffer(GL11.GL_NONE);
			
			EXTFramebufferObject.glFramebufferTexture2DEXT(
					EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
					EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT,
					GL11.GL_TEXTURE_2D, depthRenderTexture.getId(), 0);
			
			// Check
			if(EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT) != EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT)
				throw new Error("Could not create FBO!");
			
			//unbind
			EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
			
		}
	}
	
	
	/**
	 * set the color Texture to use when drawing
	 * @param texture
	 */
	public static void setRenderTexture(Texture texture){
		prepareRenderTargeting();
		colorRenderTexture=texture;
		//bind FBO
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, FBOId);
		//set render target
		EXTFramebufferObject.glFramebufferTexture2DEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
				EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT,
				GL11.GL_TEXTURE_2D, 
				texture==null?0:texture.getId(), 0);
		if (!FBOIsBound){
			//unbind
			EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		}
	}
	
	
	/**
	 * set the depth texture to use when drawing
	 * @param texture depth texture to use
	 */
	public static void setRenderDepthTexture(DepthTexture texture){
		depthRenderTexture=texture;
		
		if (!texture.writeOnly()){
		  //bind FBO
			EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, FBOId);
			//set render target
			EXTFramebufferObject.glFramebufferTexture2DEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
				EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT,
				texture.writeOnly()?GL30.GL_RENDERBUFFER:GL11.GL_TEXTURE_2D,//if write-only, then a render buffer is used internally, otherwise a texture is used
				texture==null?0:texture.getId(), 0);
		}
		if (!FBOIsBound){
			//unbind
			EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		}
	}
	
	
	/**
	 * use the chosen render color and depth textures for drawing
	 * @param x top x to start drawing
	 * @param y top y to start drawing
	 * @param width the width to draw to
	 * @param height the height to draw to
	 */
	public static void useAsTarget(int x, int y, int width, int height){
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, FBOId);
		if (depthRenderTexture.writeOnly()){
			GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthRenderTexture.getId());
		}
		FBOIsBound=true;
		GL11.glViewport(x,y,width,height);
	}
	
	
	/**
	 * return rendering to the primary surface (the screen)
	 * the target size may need to be reset with GL11.glViewport(0, 0, width, height);
	 */
	public static void resetTarget(){
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		if (depthRenderTexture.writeOnly()){
			GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthRenderTexture.getId());
		}
		FBOIsBound=false;
		
	}
	
}









































