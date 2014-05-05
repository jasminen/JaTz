package view;

import org.eclipse.swt.SWT;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/*
 * Board Composite - using the Tile Component.
 */

public class Board extends Composite {
	int[][] boardData; 
	Tile[][] tiles; // Represent of the draw tiles. 
	Color boardBackgroundColor = new Color(null, 179, 163, 148);
	Color tileBackGroundColor = new Color(null, 238, 228, 218);
	MouseDragCommand mouseCommand;

	public Board(Composite parent, int style, MouseDragCommand mouseCommand) {
		super(parent, style);
		this.mouseCommand = mouseCommand;
		
		// Defaults
		setBackground(boardBackgroundColor);

	}
	// Setting new board (width and height are not fixed)
	public void setBoard(int[][] boardData) {
		// If Tiles array is not null clean it.
		if (tiles != null && boardData != null && boardData[0] != null) {
			for (int i = 0; i < this.boardData.length; i++) {
				for (int j = 0; j < this.boardData[i].length; j++) {
					if (tiles[i][j] != null) {
						tiles[i][j].dispose();
					}
				}
			}
		}
		// creating new Tiles array
		this.boardData = boardData;
		this.tiles = new Tile[this.boardData.length][this.boardData[0].length];
		for (int i = 0; i < this.boardData.length; i++) {
			for (int j = 0; j < this.boardData[i].length; j++) {
				tiles[i][j] = new Tile(this, 0, mouseCommand);
				tiles[i][j].setTileNumber(this.boardData[i][j]);
				tiles[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			}
		}
		// setup grid style
		GridLayout gl = new GridLayout(this.boardData[0].length, true);
		gl.horizontalSpacing = 10;
		gl.verticalSpacing = 10;
		gl.marginWidth = 10;
		gl.marginHeight = 10;
		setLayout(gl);
	}

	// Updating existing board 
	public void updateBoard(int[][] boardData) {
		this.boardData = boardData;
		for (int i = 0; i < this.boardData.length; i++) {
			for (int j = 0; j < this.boardData[i].length; j++) {
				if (tiles[i][j].getTileNumber() != this.boardData[i][j]) {
					tiles[i][j].setTileNumber(this.boardData[i][j]);
				}
			}
		}
	}

	public void setGameColors(Color boardBackgroundColor, Color tilesBackgroundColor) {
		this.boardBackgroundColor = boardBackgroundColor;
		this.tileBackGroundColor = tilesBackgroundColor;
		redraw();
	}
}
