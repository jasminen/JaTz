package controller;

import model.Game2048.Game2048Model;

public class Run {

	public static void main(String[] args) {
		Game2048Model bla= new Game2048Model();
		
		String str=new String();
		
		
		for (int i = 0; i < 4; i++) {
			  str=str.concat("\n");
			  for (int j = 0; j < 4; j++) {
			      str=str.concat(bla.getState().getBoard()[i][j]+"  ");
			    }
			}
		  str=str.concat("\n");
		  System.out.println(str);
		
		  str=new String()  ;
		  bla.doAction(1);
		
		for (int i = 0; i < 4; i++) {
			  str=str.concat("\n");
			  for (int j = 0; j < 4; j++) {
			      str=str.concat(bla.getState().getBoard()[i][j]+"  ");
			    }
			}
		  str=str.concat("\n");
		  System.out.println(str);
		  
		  
		  
		  str=new String()  ;
		  bla.doAction(3);
		  bla.doAction(2);
		for (int i = 0; i < 4; i++) {
			  str=str.concat("\n");
			  for (int j = 0; j < 4; j++) {
			      str=str.concat(bla.getState().getBoard()[i][j]+"  ");
			    }
			}
		  str=str.concat("\n");
		  System.out.println(str);
		  
	}

}
