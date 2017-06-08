package mainPkg;

import java.io.FileNotFoundException;

/**
 * @author Piyush Bhatt
 * Data Structures and Algorithm 2
 * PGM-2
 * This class is used to create Graphs (Adjacency Matrices) using random weights(edges).
 */
public class CreateGraph {
	public int[][] createGraph(int numberOfVertices) throws FileNotFoundException{
	int graphMatrix[][]= new int[numberOfVertices][numberOfVertices];
		for (int i = 0; i < graphMatrix.length; i++) {
		    for (int j = 0; j < graphMatrix[i].length; j++) {
		        if(i==j){
		        	graphMatrix[i][j]=0;
		        } else {
		        	graphMatrix[i][j] = (int)(Math.random()*100);
		        }
		    }
		}
		return graphMatrix;
}
	
}
