package common;

import java.io.Serializable;

public class Message implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private State state;
	private String msg;
	private int result;
	private String game;
	
	public Message() {}
	
	public Message(State state, String msg, int result, String game) {
		this.state = state;
		this.msg = msg;
		this.result = result;
		this.game = game;
	}


	public State getState() {
		return state;
	}


	public void setState(State state) {
		this.state = state;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public int getResult() {
		return result;
	}


	public void setResult(int result) {
		this.result = result;
	}


	public String getGame() {
		return game;
	}


	public void setGame(String game) {
		this.game = game;
	}
	
	
	
}