//=========================================================================
//
// Author : Daniel Sage
//
//==========================================================================

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class StructuringElementCanvas extends Canvas {

	private int sizeX = 3;
	private int type = 0;
	
	public StructuringElementCanvas() {
		super();
		setSize(new Dimension(140, 140));
		setBackground(new Color(150, 150, 150));
	}
	
	public void repaint(int type, int size) {
		this.type = type;
		this.sizeX = size;
		repaint();
	}
	
	public boolean[][] getStructuringElement(int m) {
 
		boolean se[][] = new boolean[m][m];
		switch(type) {
			case 0:	se = (new CodeMorphological()).square(m); break;
			case 1:	se = (new CodeMorphological()).cross(m); break;
			case 2:	se = (new CodeMorphological()).disk(m); break;
			case 3:	se = (new CodeMorphological()).shift(m); break;
		}
		return se;
	}
	
	public void paint(Graphics g) {
		int m = sizeX;
		boolean se[][] = getStructuringElement(m);
		int s = Math.min(Math.round(140/m), Math.round(140/m));
		int ox = (140 - m*s)/2;
		int oy = (140 - m*s)/2;
		g.setColor(Color.white);
		for(int i=0; i<=m; i++)
			g.drawLine(ox, oy+i*s, ox+s*m, oy+i*s);
		for(int i=0; i<=m; i++)
			g.drawLine(ox+i*s, oy, ox+i*s, oy+s*m);

		for(int i=0; i<m; i++)
		for(int j=0; j<m; j++) {
			if (se[i][j])
				g.setColor(Color.orange);
			else
				g.setColor(Color.black);
			g.fillRect(ox+i*s+1, oy+j*s+1, s-2, s-2);
		}
	}
	
}
