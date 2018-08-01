package test;

import entities.Entity;
import entities.Loader;
import exceptions.DisplayException;
import render.Display;
import render.Renderer;

public class MainLoop {

	
	private static Entity quad;
	
	
	private static void init() {
		Display.setUp(1280, 720, 60, 60);
		try {
			Display.init();
		} catch (DisplayException e) {
			e.printStackTrace();
		}
		
		//#####################################
		
		
		

		Loader.init();
		quad = Loader.loadQuad();
		Renderer.init();
		Renderer.render(quad);
		
		
		
		
		
		
		
		
		//########################################
		Display.showWindow();
	}
	
	private static void loop() {
		Display.loop();
	}
	
	private static void exit() {
		Display.dispose();
		Loader.dispose();
		Renderer.dispose();
	}



	
	


	public static void main(String[] args) {
		
		init();
		loop();
		exit();

	}

	
}
