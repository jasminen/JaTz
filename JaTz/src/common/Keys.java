package common;

/*
 * Keys - Int to String mapping  
 */
public interface Keys {

	
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int LEFT = 4;

	
	public static final int DIAGONAL_LEFT_UP = 5;
	public static final int DIAGONAL_RIGHT_UP = 6;
	public static final int DIAGONAL_LEFT_DOWN = 7;
	public static final int DIAGONAL_RIGHT_DOWN = 8;	
	
	
	public static final int UNDO = -1;
	public static final int RESTART = 0;
	public static final int SAVE = 50;
	public static final int LOAD = 60;
	public static final int CONNECT = 80;
	public static final int DISCONNECT = 90;
	public static final int GET_HINT = 800;
	public static final int FULL_SOLVER = 900;

	
	public static final int NEW_GAME = 999;
	public static final int DIFFERENT_GAME = 888;
	public static final int IN_PROGRESS = 0;
	public static final int GAMEOVER = 666;
	public static final int WIN = 2048;
	
	
	public static final int MOUSE = 11;
	public static final int CHEESE = 22;
	public static final int MOUSE_AND_CHEESE = 33;
	public static final int WALL = 555;
	
	public static final int EMPTY = 0;
	
	
	
}
