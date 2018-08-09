package input;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {
	
	private static KeyListener listener;
	
	public static void setListener(KeyListener listener_) {
		listener = listener_;
	}
	
	public static void registerKey(int key, int action) {
		if (action == GLFW_PRESS) {
			listener.registerKeyPress(key);
		}else if (action == GLFW_RELEASE){
			listener.registerKeyRelease(key);
		}
	}

}
