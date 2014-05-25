package view;

import org.eclipse.swt.SWT;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Board class extends Composite.
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public class Board extends Composite {
	/**
	 * Matrix of the game board
	 */
	int[][] boardData; 
	/**
	 * Matrix representation of the draw tiles.  
	 */
	Tile[][] tiles; 
	/**
	 * Board default color 
	 */
	Color boardBackgroundColor = new Color(null, 179, 163, 148);
	/**
	 * Tiles default color 
	 */
	Color tileBackGroundColor = new Color(null, 238, 228, 218);
	/**
	 * Mouse drag command representation
	 */
	MouseDragCommand mouseCommand;

	/**
	 * Constructor creates the board Composite 
	 * @param parent
	 * @param style
	 * @param mouseCommand
	 */
	public Board(Composite parent, int style, MouseDragCommand mouseCommand) {
		super(parent, style);
		this.mouseCommand = mouseCommand;
		
		// Defaults
		setBackground(boardBackgroundColor);

	}
	// 
	/**
	 * Setting new board and creating new tiles array.
	 * @param boardData board matrix can be in any size. 
	 */
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

	/**
	 * Updating existing board without redraw 
	 * @param boardData Board matrix.
	 */
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
	/**
	 * Setting new colors for the game
	 * @param boardBackgroundColor Board background color
	 * @param tilesBackgroundColor Tiles background color
	 */
	public void setGameColors(Color boardBackgroundColor, Color tilesBackgroundColor) {
		this.boardBackgroundColor = boardBackgroundColor;
		this.tileBackGroundColor = tilesBackgroundColor;
		redraw();
	}
}
