package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class Tile extends Canvas {
	private Font numberFontStyle;
	private Color tileBackGroundColor;
	private Color boardBackGroundColor;
	private String tileNumber;

	public Tile(Composite parent, int style) {
		super(parent, style);
		boardBackGroundColor = new Color(null, 166, 151, 88);
		tileBackGroundColor = new Color(null, 222, 211, 149);
		numberFontStyle = new Font(null, "Arial", 24, SWT.BOLD);
		tileNumber = "2";
		// Graphics resources that must be disposed.
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
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

	void controlResized(ControlEvent e) {
		Shell shell[] = e.widget.getDisplay().getShells();

		Control[] t = shell[0].getChildren();
		Rectangle boardSzie = t[1].getBounds();
		System.out.println(t[1].getBounds());
		computeSize(boardSzie.width / 4, boardSzie.height / 4, false);
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
	}

	public Color getBackGround() {
		return tileBackGroundColor;
	}

	public void setBackGround(Color backGround) {
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

	public Color getRectangleBackGroundColor() {
		return tileBackGroundColor;
	}

	public void setRectangleBackGroundColor(Color rectangleBackGroundColor) {
		this.tileBackGroundColor = rectangleBackGroundColor;
	}

	public Color getBoardBackGroundColor() {
		return boardBackGroundColor;
	}

	public void setBoardBackGroundColor(Color boardBackGroundColor) {
		this.boardBackGroundColor = boardBackGroundColor;
	}
}
