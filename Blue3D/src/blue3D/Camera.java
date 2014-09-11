package blue3D;

import blue3D.type.Matrix4f;

public interface Camera {
	public Camera resize(int width, int height);
	public Camera calculateMatrix();
	public Matrix4f getMatrix();
	public Camera use();
	public int getWidth();
	public int getHeight();
}
