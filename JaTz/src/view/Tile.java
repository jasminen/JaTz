package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class Tile extends Canvas {
	private Font numberFontStyle;
	private Color tileBackGroundColor;
	private Color boardBackGroundColor;
	private int tileNumber;

	public Tile(Composite parent, int style) {
		super(parent, style);
		Board p = (Board) parent;
		boardBackGroundColor = new Color(null, p.boardBackgroundColor.getRGB());
		tileBackGroundColor = new Color(null, p.tileBackGroundColor.getRGB());
		numberFontStyle = new Font(null, "Arial", 24, SWT.BOLD);

		// Graphics resources must be disposed.
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				Tile.this.numberFontStyle.dispose();
				Tile.this.tileBackGroundColor.dispose();
				Tile.this.boardBackGroundColor.dispose();
				Tile.this.numberFontStyle.dispose();
			}
		});
		
		
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				Tile.this.paintControl(e);
			}
		});

		
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println("mouse up: x: "+e.x +" y: "+e.y+ " size: x: "+Tile.this.getSize().x+" y: "+Tile.this.getSize().y);
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.println("mouse down: x: "+e.x +" y: "+e.y);
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		


	}

	
	
	
	void paintControl(PaintEvent e) {
		GC gc = e.gc;
		int width = e.width, height = e.height;
		int fontSzie = 16; // (int)Math.min(width / 3, height / 3);
		FontMetrics fm = e.gc.getFontMetrics();
		int fwidth = fm.getAverageCharWidth();
		int mx = getSize().x / 2 - ("" + tileNumber).length() * fwidth / 2;
		int my = getSize().y / 2 - fm.getHeight() / 2 - fm.getDescent();

		numberFontStyle = new Font(null, "Arial", fontSzie, SWT.BOLD);
		gc.setFont(numberFontStyle);

		// Paint the tile, two background for the round rectangle
		gc.setBackground(boardBackGroundColor);
		gc.fillRectangle(0, 0, width, height);
		gc.setBackground(tileBackGroundColor);
		gc.fillRoundRectangle(0, 0, width, height, 3, 3);

		if (tileNumber > 0) {
			gc.drawString("" + tileNumber, mx, my);
		}

		// Don't forget to dispose
		gc.dispose();
		numberFontStyle.dispose();
	}

	public Color getTileBackGroundColor() {
		return tileBackGroundColor;
	}

	public void setTileBackGroundColor(Color backGround) {
		this.tileBackGroundColor = backGround;
		redraw();
	}

	public int getTileNumber() {
		return tileNumber;
	}

	public void setTileNumber(int tileNumber) {
		this.tileNumber = tileNumber;
		redraw();
	}
}
