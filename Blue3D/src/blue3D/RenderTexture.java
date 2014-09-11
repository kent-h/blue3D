package blue3D;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class RenderTexture {
	
	private int FBOId;
	
	Texture texture;
	
	
	public RenderTexture(){
		FBOId=GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, FBOId);
	}
	
	
	/**
	 * note that this will reset the current draw target back to the default device (the screen)
	 * @param texture the texture which should be drawn to
	 */
	public void setRenderTarget(Texture texture){
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, FBOId);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}
	
	
	/**
	 * without a depth target, a RenderableTexture will be unable to use depth-testing
	 */
	public void setDepthTarget(DepthTexture texture){
		
	}
	
	
	public void use(){
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, FBOId);
	}
	
	
	public static void use(RenderTexture rt){
		if (rt==null){
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		}else{
			rt.use();
		}
	}
	
}
