import java.awt.Color;

import javax.swing.JPanel;

public class Tile implements Comparable <Tile>{
	Color two = new Color(237, 226, 206);
	Color four = new Color(217, 207, 189);
	Color eight = new Color(237, 171, 116);
	Color sixteen = new Color(240, 147, 72);
	Color thtytwo = new Color(237, 117, 92);
	Color sxtyfour = new Color(224, 64, 31);
	Color oneteight = new Color(240,218,132);
	Color tfsix = new Color(240, 208,86);
	Color fotwo = new Color(240, 201, 64);
	Color thoutwofour = new Color(242, 206, 44);
	Color tzfoureight = new Color(255, 208, 0);
	
	private int val;
	private Color c; //color for background of the tile
	private Color wordC = Color.WHITE; //color for the number text
	private boolean canMerge;
	
	public Tile (int val) {
		this.val = val;
		canMerge = true;
		if (val == 2) {
			c = two;
			wordC = new Color(115, 109, 99);
		}
		if (val == 4) {
			c = four;
			wordC = new Color(92, 87, 77);
		}
		if (val == 8) {
			c = eight;
			wordC = Color.WHITE;
		}
		if (val == 16) {
			c = sixteen;
		}
		if (val == 32) {
			c = thtytwo;
		}
		if (val == 64) {
			c = sxtyfour;
		}
		if (val == 128) {
			c = oneteight;
		}
		if (val == 256) {
			c = tfsix;
		}
		if (val == 512) {
			c = fotwo;
		}
		if (val == 1024) {
			c = thoutwofour;
		}
		if (val == 2048) {
			c = tzfoureight;
		}
	}
	
	public int getVal() {
		return val;
	}
	
	public Color getC() {
		return c;
	}
	
	//check how many digits the number has
	public int getValDigit() {
		if (getVal()/10 == 0) {return 1;}
		else if (getVal()/100 == 0) {return 2;}
		else if (getVal()/1000 == 0) {return 3;}
		else {return 4;}
	}
	
	public int setFontSize() {
		if (getVal() < 100) {
			return 80;
		}
		if (getVal() > 1000) {
			return 58;
		}
		else {return 68;}
	}
	
	public boolean getCanMerge() {
		return canMerge;
	}
	
	public boolean canMove(Tile o) {
		if (o == null) {return true;}
		if (o.getVal() == getVal()) {return true;}
		return false;
	}
	
	public void mergeFalse() {
		canMerge = false;
	}
	public void mergeTrue() {
		canMerge = true;
	}
	
	public Color getWordC() {
		return wordC;
	}
	
	@Override
	public int compareTo(Tile o) {
		if (val == o.val || c == o.c) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
	public boolean mergable(Tile o) {
		if(o != null) {
			if (compareTo(o) == 0) {
				if (getCanMerge() && getCanMerge()) {
					return true;
				}
				return false;
			}
		}
		return false;
	}
}
