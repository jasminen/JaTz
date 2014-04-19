package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class Tile extends Canvas {
	private Font numberFontStyle;
	private Color tileBackGroundColor;
	private Color boardBackGroundColor;
	private String tileNumber;

	public Tile(Composite parent, int style) {
		super(parent, style);
		Board p = (Board)parent;
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
	}


	void paintControl(PaintEvent e) {
		GC gc = e.gc;
		int x = 0, y = e.y;
		int width = e.width, height = e.height;
		int fontSzie = Math.min(width / 3, height / 3);

		numberFontStyle = new Font(null, "Arial", fontSzie, SWT.BOLD);
		gc.setFont(numberFontStyle);

		// Paint the tile, two background for the round rectangle
		gc.setBackground(boardBackGroundColor);
		gc.fillRectangle(x, y, width, height);
		gc.setBackground(tileBackGroundColor);
		gc.fillRoundRectangle(x, y, width, height, 3, 3);

		// Center the text
		x = (width - fontSzie) / 2 + 2;
		y = (height - fontSzie) / 2 - 5;
		gc.drawString(tileNumber, x, y);
		
		//Don't forget to dispose 
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

	public String getTileNumber() {
		return tileNumber;
	}

	public void setTileNumber(String tileNumber) {
		this.tileNumber = tileNumber;
		redraw();
	}
}
