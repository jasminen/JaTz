package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import common.Keys;

/**
 * The tile class extends canvas  
 * 
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public class Tile extends Canvas {
	private Font numberFontStyle;
	private Color tileBackGroundColor;
	private Color boardBackGroundColor;
	private int tileNumber;
	private Point to, tileSize;
	

	/**
	 * Constructor Creates new tile
	 * @param parent
	 * @param style
	 * @param The command what happens when a mouse drag event emitted.
	 */
	public Tile(Composite parent, int style, final MouseDragCommand mouseCommand) {
		super(parent, style);
		Board p = (Board) parent;
		boardBackGroundColor = new Color(null, p.boardBackgroundColor.getRGB());
		tileBackGroundColor = new Color(null, p.tileBackGroundColor.getRGB());
		numberFontStyle = new Font(null, "Arial", 24, SWT.BOLD);
		

		
		//--------------------------------
		// Listeners 
		//--------------------------------
		
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
				to = new Point(e.x, e.y);
				tileSize = Tile.this.getSize();
				mouseCommand.setCommand(to, tileSize);
			}
			
			@Override
			public void mouseDown(MouseEvent e) {}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {}
		});
	}
	
	/**
	 * Paint new tile
	 * @param e
	 */
	void paintControl(PaintEvent e) {
		GC gc = e.gc;
		int width = e.width, height = e.height;
		int fontSzie = (int)Math.min(width / 4, height / 4);
		FontMetrics fm = e.gc.getFontMetrics();
		int fwidth = fm.getAverageCharWidth();
		int mx = getSize().x / 2 - ((("" + tileNumber).length() * fwidth));
		int my = getSize().y / 2 - (fm.getHeight() / 2)- fm.getDescent();

		
		
		numberFontStyle = new Font(null, "Arial", fontSzie, SWT.BOLD);
		gc.setFont(numberFontStyle);

		// Paint the tile, two background for the round rectangle
		gc.setBackground(boardBackGroundColor);
		gc.fillRectangle(0, 0, width, height);
		
		
		setColor(gc);
		gc.fillRoundRectangle(0, 0, width, height, 3, 3);
		
		switch (tileNumber) {
		case Keys.MOUSE:
			Image mouseImage = new Image(Display.getDefault(),"images/mouse.jpg");
			gc.drawImage(new Image(Display.getDefault(), mouseImage.getImageData().scaledTo(width, height)), 0, 0);
			break;
		case Keys.CHEESE:
			Image cheeseImage = new Image(Display.getDefault(),"images/cheese.jpg");
			gc.drawImage(new Image(Display.getDefault(), cheeseImage.getImageData().scaledTo(width, height)), 0, 0);
			break;
		case Keys.MOUSE_AND_CHEESE:
			Image mouseCheeseImage = new Image(Display.getDefault(),"images/mouseNcheese2.jpg");
			gc.drawImage(new Image(Display.getDefault(), mouseCheeseImage.getImageData().scaledTo(width, height)), 0, 0);
			break;
		case Keys.WALL:
			Image wallImage = new Image(Display.getDefault(),"images/wall2.jpg");
			gc.drawImage(new Image(Display.getDefault(), wallImage.getImageData().scaledTo(width, height)), 0, 0);
			break;
		case Keys.EMPTY:
			break;
		default:
			gc.drawString("" + tileNumber, mx, my);
			break;
		}
		
		// Don't forget to dispose
		gc.dispose();
		numberFontStyle.dispose();
	}

	/**
	 * Getter for the tile background  
	 * @return Color 
	 */
	public Color getTileBackGroundColor() {
		return tileBackGroundColor;
	}

	/**
	 * Setter for the tile background 
	 * @param tile backGround
	 */
	public void setTileBackGroundColor(Color backGround) {
		this.tileBackGroundColor = backGround;
		redraw();
	}

	/**
	 * Getter for the tile number
	 * @return tile number
	 */
	public int getTileNumber() {
		return tileNumber;
	}

	/**
	 * Setter for the tile number 
	 * @param tileNumber
	 */
	public void setTileNumber(int tileNumber) {
		this.tileNumber = tileNumber;
		redraw();
	}

	/**
	 * Set color of the tile according to the number
	 * @param gc
	 */
	private void setColor(GC gc) {
		switch (tileNumber) {
		case 2:
			gc.setBackground(new Color(null, 238,223,204));
			break;
		case 4:
			gc.setBackground(new Color(null, 243,200,160));
			break;
		case 8:
			gc.setBackground(new Color(null,250,180,100));
			break;
		case 16:
			gc.setBackground(new Color(null,255,140,70));
			break;
		case 32:
			gc.setBackground(new Color(null,255,90,89));
			break;
		case 64:
			gc.setBackground(new Color(null,255,0,0));
			break;
		case 128:
			gc.setBackground(new Color(null,238,235,155));
			break;
		case 256:
			gc.setBackground(new Color(null,238,220,130));
			break;
		case 512:
			gc.setBackground(new Color(null,243,210,100));
			break;
		case 1024:
			gc.setBackground(new Color(null,255,250,100));
			break;
		case 2048:
			gc.setBackground(new Color(null,255,255,50));
			break;
		case 4096:
			gc.setBackground(new Color(null,139,105,20));
			break;
		default:
			gc.setBackground(tileBackGroundColor);
			break;
		}
	}
}
