package render;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import entities.Camera;
import entities.Loader;
import exceptions.DisplayException;
import shaders.Shader;
import test.MainLoop;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

public class Display {

		// The window handle
		private static long window;
		
		private static int WIDTH = 1280;
		private static int HEIGHT = 720;
		
		private static int MAX_FPS = 60;
		private static int MAX_UPS = 60;
		
		private static int upsCount;
		private static int fpsCount;
		
		private static boolean press1, press2;
		
		
		public static void setUp(int width, int height, int fps, int ups) {
			WIDTH = width;
			HEIGHT = height;
			MAX_FPS = fps;
			MAX_UPS = ups;
		}
		
		
		public static void init() throws DisplayException {
			System.out.println("Build: LWJGL " + Version.getVersion());

			GLFWErrorCallback.createPrint(System.err).set();

			if (!glfwInit())
				throw new DisplayException("Unable to initialize GLFW");
			

			glfwDefaultWindowHints();
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
			glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
			

			window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
			if (window == NULL)
				throw new DisplayException("Failed to create the GLFW window");
			

			// Center the window
			try ( MemoryStack stack = stackPush() ) {
				IntBuffer pWidth = stack.mallocInt(1);
				IntBuffer pHeight = stack.mallocInt(1);
				glfwGetWindowSize(window, pWidth, pHeight);
				
				GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

				glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2,	(vidmode.height() - pHeight.get(0)) / 2);
			}

			glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
				if (key == GLFW_KEY_D && action == GLFW_PRESS) {
					press2 = true;
				}
				if (key == GLFW_KEY_D && action == GLFW_RELEASE) {
					press2 = false;
				}
				if (key == GLFW_KEY_A && action == GLFW_PRESS) {
					press1 = true;
				}
				if (key == GLFW_KEY_A && action == GLFW_RELEASE) {
					press1 = false;
				}
			});

			
			glfwMakeContextCurrent(window);
			glfwSwapInterval(1);	//v-sync
			GL.createCapabilities();			
		}
		
		
		
		public static void showWindow() {
			// Make the window visible
			glfwShowWindow(window);
		}

		public static void loop() {

			glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

			boolean end = false;
			upsCount = 0; fpsCount = 0;
			long currentTime, lastFrameTime, lastUpdateTime, lastDebuggerTime;
			currentTime = lastUpdateTime = lastFrameTime =  lastDebuggerTime = System.currentTimeMillis();
			
			
			while (!end) {
				currentTime = System.currentTimeMillis();
				
				if (currentTime - lastUpdateTime > 1000 / MAX_UPS) {
					lastUpdateTime = currentTime;
					upsCount++;
					checkInput();
					update();
					
					if (currentTime - lastFrameTime > 1000 / MAX_FPS) {		//indented bc we want frames to be sync-ed with updates
						lastFrameTime = currentTime;
						fpsCount++;
						render();
					}
				}
				
				if (currentTime - lastDebuggerTime > 1000) {
					lastDebuggerTime = currentTime;
					System.out.println(upsCount + " updates, " + fpsCount + " fps");
					upsCount = fpsCount = 0;
				}
				
				
				end = glfwWindowShouldClose(window);
			}

			Loader.dispose();
			Renderer.dispose();
			Shader.dispose();
			dispose();
			System.out.println("closed!");
		}

		private static void checkInput() {
			if (press1) {
				Camera.moveForward();
			}
			if (press2) {
				Camera.moveBackwards();
			}
			
		}


		public static void dispose() {
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);

			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}
		
		private static void update() {
			glfwPollEvents();
		}
		
		private static void render() {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			//######################################
			
			
			
			Renderer.render();
			
			

			//######################################
			glfwSwapBuffers(window); // swap the color buffers

		}
		
		

}
