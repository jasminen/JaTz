package model.gameMaze.solver;



public interface Searcher {

	public double search(Node start, Node goal) ;
	public int getNumOfEvaluatedNodes();

}
