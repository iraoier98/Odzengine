package test;

import entities.Camera;
import entities.Entity;
import entities.Loader;
import exceptions.DisplayException;
import exceptions.LoaderException;
import input.KeyListener;
import input.Keyboard;
import math.Vector2f;
import render.Display;
import render.Renderer;
import shaders.Shader;
import text.Letter;
import text.Letters;
import text.Text;

public class MainLoop implements KeyListener{

	
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
		try {
			quad = new Entity(Loader.loadQuad());
			quad.a();
		} catch (LoaderException e) {
			e.printStackTrace();
		}
		Renderer.init();
		Renderer.render(quad);
		Letters.init();
		Text t = new Text(new Vector2f(0, 0), "_liuyf y");
//		Text t = new Text(new Vector2f(0, 0), "abcdefghijklmnopqrstuvwxyz");
		Renderer.render(t);
		
		Camera.setUp();
		Keyboard.setListener(new MainLoop());
		

		
		
		
		
		
		
		
		
		
		//########################################
		Display.showWindow();
	}
	
	private static void loop() {
		Display.loop();
	}


	
	


	public static void main(String[] args) {
		
		init();
		loop();

	}

	@Override
	public void registerKeyPress(int key) {
		System.out.println("Pressed " + key);
	}

	@Override
	public void registerKeyRelease(int key) {
		System.out.println("Released " + key);
		
	}

	
}
