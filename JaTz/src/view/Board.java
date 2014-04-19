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
	Tile tile;
	int[][] boardData;

	public Board(Composite parent, int style, int[][] boardData) {
		super(parent, style);
		this.boardData = boardData;
		setBackground(new Color(null, 166, 151, 88));
		GridLayout gl = new GridLayout(4, true);
		gl.horizontalSpacing = 10;
		gl.verticalSpacing = 10;
		gl.marginWidth = 10;
		gl.marginHeight = 10;
		setLayout(gl);

		for (int i = 0; i < this.boardData.length; i++) {
			for (int j = 0; j < this.boardData[i].length; j++) {
				tile = new Tile(this, 0);
				tile.setTileNumber("" + this.boardData[i][j]);
				tile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
						1, 1));
			}
		}

		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				// ///////
				Board.this.tile.dispose();
			}
		});

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				if (Board.this.boardData != null) {

				}
			}
		});

	}

}
