package text;

import java.util.ArrayList;
import java.util.List;

import math.Vector2f;

public class Text {
	
	private List<Letter> list;
	
	private Vector2f position;
	
	public Text(Vector2f position, String text) {
		this.position = position;
		this.list = new ArrayList<Letter>();
		float nextX = position.x;
		float lineY = position.y;
		
		Letter l;
		for (int i = 0; i < text.length(); i++) {
			char current = text.charAt(i);
			
			if (current == ' ') {
				nextX += Letters.spaceSize;
				System.out.println("space");
			}else {
				l = Letters.getLetter(current);
				l.setPosition(new Vector2f(nextX + l.getXoff(), lineY - l.getYoff()));
				nextX += l.getAdv();
				list.add(l);
				System.out.println(l.getPosition().x * 1024 + " " + l.getPosition().y * 1024 + " " + nextX * 1024);
			}
		}
	}
	
	public List<Letter> getLetters(){
		return list;
	}

	public void print() {
		list.get(0).print();
		list.get(1).print();
	}

}
