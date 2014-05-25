package model.gameMaze.solver.astar;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import model.gameMaze.solver.AbsSearcher;
import model.gameMaze.solver.Distance;
import model.gameMaze.solver.Domain;
import model.gameMaze.solver.Maze;
import model.gameMaze.solver.MazeAction;
import model.gameMaze.solver.MazeAirDistance;
import model.gameMaze.solver.MazeDistanceG;
import model.gameMaze.solver.MazeDomain;
import model.gameMaze.solver.Node;
import model.gameMaze.solver.NodeMazeActionPair;

/**
 * Search algorithm that is using Heuristics in order to solve the issue in the most efficient way. 
 * @author Tzelon Machluf and Jasmine Nouriel
 *
 */

public class Astar extends AbsSearcher implements Serializable {


	private static final long serialVersionUID = 1L;
	private Domain domain;
	private Distance g,h;
	
	/**
	 * Default C'tor
	 */
	public Astar() {
		this.domain= new MazeDomain(new Maze());
		this.g=new MazeDistanceG();
		this.h=new MazeAirDistance();
	}
	
	/**
	 * C'tor
	 * @param domain
	 * @param g
	 * @param h
	 */
	public Astar(Domain domain, Distance g, Distance h) {
	
		this.domain=domain;
		this.g=g;
		this.h=h;
	}
	
	
	@Override 
	public double search (Node sstart, Node sgoal)
	{
		Node father, current;
		Node start=getNode(sstart);
		Node goal=getNode(sgoal);		
		double tempGScore = 0;
		ArrayList<MazeAction> legalActions;
		HashMap<Node, NodeMazeActionPair> cameFrom = new HashMap<Node, NodeMazeActionPair>() ;
		
		
		addToOpenList(start);
		start.setG(g.getDistance(start, start));
		start.setF(g.getDistance(start, start) + h.getDistance(start, goal));
		
		while (!OpenListIsEmpty())
		{
			father=pollFromOpenList();
			if (father.equals(goal)) {
				return reconstructPath(cameFrom, father);
			}
			addToClosedList(father);
			legalActions=domain.getMazeActions(father);
			for (MazeAction a : legalActions)
			{
				current=getNode(a.doAction(father));
				tempGScore=father.getG() + g.getDistance(father, current);
				
				if(closedListContains(current) && tempGScore >= current.getG())
					continue;
				if ( !openListContains(current) || tempGScore < current.getG() )
				{
					cameFrom.put(current, new NodeMazeActionPair(father,a));
					current.setG(tempGScore);
					current.setF(current.getG() + h.getDistance(current, goal));
					if (!openListContains(current))
						addToOpenList(current);
				}
			}
				
		}
		
		return 0;
		
	}
	
	/**
	 * Reconstruct the path from goal to start
	 * @param cameFrom
	 * @param current
	 * @return the score
	 */
	private double reconstructPath (HashMap<Node, NodeMazeActionPair> cameFrom, Node current)
	{
		double score=0;
		if (cameFrom==null || cameFrom.isEmpty())
			return 0;
		while (cameFrom.containsKey(current) && cameFrom.get(current).getNode()!=null) {
			    score += g.getDistance(current, cameFrom.get(current).getNode());
				current=cameFrom.get(current).getNode();
		}
		return score;
	}

	/**
	 * domain getter 
	 * @return domain
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * domain setter
	 * @param domain
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	/**
	 * g getter
	 * @return g
	 */
	public Distance getG() {
		return g;
	}

	/**
	 * g setter
	 * @param g
	 */
	public void setG(Distance g) {
		this.g = g;
	}

	/**
	 * h getter
	 * @return h
	 */
	public Distance getH() {
		return h;
	}

	/**
	 * h setter
	 * @param h
	 */
	public void setH(Distance h) {
		this.h = h;
	}
	
	@Override
	 public String toString(){
		return "Class Astar: Domain= "+domain+", DistanceG= "+g+", DistanceH= "+h;
	 }
	
	
}

