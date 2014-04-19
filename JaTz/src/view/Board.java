package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class Board extends Composite {
	int[][] boardData;
	Tile[][] tiles;
	boolean boardChanged = false;
	Color boardBackgroundColor = new Color(null, 166, 151, 88);
	Color tileBackGroundColor = new Color(null, 222, 211, 149);

	public Board(Composite parent, int style) {
		super(parent, style);

		// Defaults
		setBackground(boardBackgroundColor);

		// Graphics resources must be disposed.
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				Board.this.boardBackgroundColor.dispose();
				for (Tile[] t : Board.this.tiles) {
					for (Tile tile : t) {
						tile.dispose();
					}
				}
			}
		});

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				if (Board.this.boardData != null) {
					Board.this.paintControl(e);
				}
			}
		});

	}

	void paintControl(PaintEvent e) {
		if (this.boardChanged) {
			setBackground(boardBackgroundColor);
			for (int i = 0; i < this.boardData.length; i++) {
				for (int j = 0; j < this.boardData[i].length; j++) {
					if (tiles[i][j] != null) {
						tiles[i][j].dispose();
					}
					tiles[i][j] = new Tile(this, 0);
					tiles[i][j].setTileNumber("" + this.boardData[i][j]);
					tiles[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
				}
			}
			this.layout(false);
			this.boardChanged = false;
		}
	}

	public void setBoard(int[][] boardData) {
		this.boardChanged = true;
		this.boardData = boardData;
		this.tiles = new Tile[boardData.length][boardData[0].length];
		GridLayout gl = new GridLayout(this.boardData[0].length, true);
		gl.horizontalSpacing = 10;
		gl.verticalSpacing = 10;
		gl.marginWidth = 10;
		gl.marginHeight = 10;
		setLayout(gl);
		redraw();
	}

	public void setGameColors(Color boardBackgroundColor, Color tilesBackgroundColor) {
		this.boardChanged = true;
		this.boardBackgroundColor = boardBackgroundColor;
		this.tileBackGroundColor = tilesBackgroundColor;
		redraw();
	}
}
