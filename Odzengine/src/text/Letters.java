package text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.Loader;
import exceptions.LoaderException;

public class Letters {
	
	private static Map<Character, Letter> map;
	private static Letter notFound;
	public static float spaceSize;

	public static int FONT_IMAGE_WIDTH = 1024;
	public static int FONT_IMAGE_HEIGHT = 1024;
	
	public static int fontTexture;
	
	public static void init() {
		//	Load all letters
		map = new HashMap<Character, Letter>();
		try {
			fontTexture = Loader.loadTexture("res/font.png");
		} catch (LoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadLetters();
	}
	
	private static void loadLetters() {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("res/font.fnt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		sc.nextLine();sc.nextLine();sc.nextLine();sc.nextLine();
		
		boolean end = false;
		while (!end) {
			String line = sc.nextLine();
			if (line.contains("kernings")) {
				end = true;
			}else {
				parseLetterFromLine(line);
			}
		}
		sc.close();
	}
	
	
	private static void parseLetterFromLine(String line) {
		Pattern pattern = Pattern.compile("char id=(\\d+)\\s+x=(\\d+)\\s+y=(\\d+)\\s+width=(\\d+)\\s+height=(\\d+)\\s+xoffset=(-)?(\\d+)\\s+yoffset=(\\d+)\\s+xadvance=(\\d+).*");
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			char c = (char) Integer.parseInt(matcher.group(1));
			int x = Integer.parseInt(matcher.group(2));
			int y = Integer.parseInt(matcher.group(3));
			int w = Integer.parseInt(matcher.group(4));
			int h = Integer.parseInt(matcher.group(5));
			int xoff = Integer.parseInt(matcher.group(7));
			boolean isxoffNegative = matcher.group(6) != null;
			if (isxoffNegative) {	//	If its a minus (-)
				xoff *= -1;
			}
			int yoff = Integer.parseInt(matcher.group(8));
			int advance = Integer.parseInt(matcher.group(9));
			

			if (c == ' ') {	//	Space
				spaceSize = (float) advance / FONT_IMAGE_WIDTH;
				return;
			}
			
			Letter l = new Letter(xoff, yoff, advance, x, y, w, h);
			
			if (c == (char) 0) {
				notFound = l;
			}else {
				map.put(c, l);
			}
//			System.out.println("id=" + (int) c + ",x=" + x + ",y=" + y + ",w=" + w + ",h=" + h + ",xo=" + xoff + ",yo=" + yoff + ",adv=" + advance);
		}else {
			System.out.println("wut");
			//	TODO
		}
	}
	
	
	public static Letter getLetter(char c) {
		Letter l = map.get(c).getCopy();
		if (l == null) {
			return notFound;
		}else {
			return l;
		}
	}
	
	

}
