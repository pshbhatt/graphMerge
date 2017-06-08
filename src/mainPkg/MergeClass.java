package mainPkg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Piyush Bhatt
 * Data Structures and Algorithm 2
 * PGM-2
 * This is the main class which calculates the minimum distance of all the graphs (Primary and Merge Graphs).
 * This class also merges the Primary Graph and the secondary Graph. 
 */
public class MergeClass {
public static void main(String[] args) throws NumberFormatException, IOException {
	PrintStream printToConsole = System.out;
	File file = new File("report.txt");
    FileOutputStream fos = new FileOutputStream(file);
	PrintStream printToFile = new PrintStream(fos);
	CreateGraph cg = new CreateGraph();
	int graphCt;
	int totalDistance = 0;
	int minimumDistance = 0;
	int edgeCount=0;
	Scanner scan = new Scanner(System.in);
	System.out.println("Number of graphs: ");
	graphCt = scan.nextInt();
	if(graphCt<2||graphCt>10){
		System.out.println("Please enter the number of graphs between 2 and 10");
		graphCt = scan.nextInt();
	}
	scan.close();
	System.setOut(printToFile);
	Random rn = new Random();
	int numberOfVertices = (int)(Math.random()*10)+10;
	System.out.println("Number of vertices: " + numberOfVertices);
	System.out.println();
	int primaryMatrix[][]= cg.createGraph(numberOfVertices);
	System.out.println("Primary Matrix: ");
	for(int row=0;row<primaryMatrix.length;row++){
		
		for(int column=0;column<primaryMatrix[row].length;column++){
			if(primaryMatrix[row][column]!=0){
				edgeCount++;
			}
			System.out.print(primaryMatrix[row][column]+"\t");
		}
		
		System.out.println();
	}
	System.out.println();
	long primartStartTime = System.nanoTime();
	HashMap<String,Integer> pathMap = new HashMap<String, Integer>();
	pathMap.put("Shortest Path of Primary Graph", shortestDistance(primaryMatrix, numberOfVertices));
	long primaryTimeConsumed = System.nanoTime()-primartStartTime;
	double primaryComp = (numberOfVertices*(Math.log(numberOfVertices)/Math.log(2))+edgeCount*(Math.log(numberOfVertices)/Math.log(2)));
	System.out.println("Asymptotic Complexity for Primary Graph: " + primaryComp);
	System.out.println();
	System.out.println("Time consumed for Primary Graph: " + primaryTimeConsumed);
	System.out.println();
	ArrayList<Integer> matrices = new ArrayList<Integer>();
	for(int graphs=0+1;graphs<graphCt+1;graphs++){
		
		matrices.clear();
		int commonVertices = rn.nextInt((5 - 1) + 1) + 2;
		int secondaryMatrix[][] = cg.createGraph(numberOfVertices);
		for(int rows=0;rows<numberOfVertices;rows++){
			for(int cols=0;cols<numberOfVertices;cols++){
				if((primaryMatrix[rows][cols]==secondaryMatrix[rows][cols])&& (primaryMatrix[rows][cols]!=0)){
					secondaryMatrix[rows][cols] = secondaryMatrix[rows][cols]-1;
					
				}
			}
		}
		System.out.println("Secondary Graph_" + graphs + ": ");
		for(int row=0;row<secondaryMatrix.length;row++){
			for(int column=0;column<secondaryMatrix[row].length;column++){
				System.out.print(secondaryMatrix[row][column]+"\t");
			}
			
			System.out.println();
		}
		System.out.println();
		for(int ver=0;ver<commonVertices;ver++){
			int row = (int)(Math.random()*numberOfVertices);
			int firstVertice = (int)(Math.random()*numberOfVertices);
			int secondVertice = (int)(Math.random()*numberOfVertices);
			primaryMatrix[row][firstVertice] = secondaryMatrix[row][firstVertice];
			primaryMatrix[row][secondVertice] = secondaryMatrix[row][secondVertice];
			secondaryMatrix[row][firstVertice] = 999;
			secondaryMatrix[row][secondVertice] = 999;
			
			
		}
		long startTime = System.nanoTime();
		for(int rows=0;rows<numberOfVertices;rows++){
			for(int cols=0;cols<numberOfVertices;cols++){
				matrices.add(primaryMatrix[rows][cols]);
					matrices.add(secondaryMatrix[rows][cols]);
			}
		}
		while(matrices.contains(999)){
			matrices.remove(matrices.indexOf(999));
		}
		Integer convMatrix[] = matrices.toArray(new Integer[matrices.size()]);
		/*for(int d=0;d<convMatrix.length;d++){
			System.out.println(convMatrix[d]);
		}*/
		
		int sizeOfMerge = (int) Math.sqrt(matrices.size());
		int mergedGraph[][] = new int[sizeOfMerge][sizeOfMerge];
		for(int r=0;r<sizeOfMerge;r++){
			for(int c=0;c<sizeOfMerge;c++){
				mergedGraph[r][c] = convMatrix[(r*sizeOfMerge)+c];
			}
		}
		int mergedEdgeCount=0;
		
		System.out.println("Merged Graph_"+graphs + ": ");
		for(int row=0;row<mergedGraph.length;row++){
			for(int column=0;column<mergedGraph[row].length;column++){
				if(mergedGraph[row][column]!=0){
					mergedEdgeCount++;
				}
				System.out.print(mergedGraph[row][column]+"\t");
			}
			
			System.out.println();
		}
		System.out.println();
		System.out.println("Edge Count: " + mergedEdgeCount);
		System.out.println();
		double mergedComp = (numberOfVertices*(Math.log(numberOfVertices)/Math.log(2))+mergedEdgeCount*(Math.log(numberOfVertices)/Math.log(2)));
		System.out.println("Asymptotic Complexity of Merged Graph_"+graphs+": "+mergedComp);
		System.out.println();
		pathMap.put("Shortest path Distance of Primary Graph and Secondary Graph_" + graphs + ": ", shortestDistance(mergedGraph, numberOfVertices));
		long timeConsumed = System.nanoTime() - startTime;
		System.out.println("Time Consumed for Merged Graph_"+graphs+":"+timeConsumed);
		System.out.println();
	}
	int count = 0;
	for (String key : pathMap.keySet()) {
	    System.out.println(key + " " + pathMap.get(key));
	    if(!key.equals("Shortest Path of Primary Graph")){
	    totalDistance = totalDistance + pathMap.get(key);
	    count++;
	    }
	}
	minimumDistance = Collections.min(pathMap.values());
	System.out.println();
	System.out.println("Minimum distance among all the graphs: " + minimumDistance);
	System.out.println("Average distance for all merged Graphs: "+totalDistance/count);
	System.out.println();
	if(pathMap.get("Shortest Path of Primary Graph")>totalDistance/count){
		System.out.println("Primary Graph shortest distance > average shortest distance of all merged graphs");
	} else if(pathMap.get("Shortest Path of Primary Graph") < totalDistance/count){
		System.out.println("Primary Graph shortest distance < average shortest distance of all merged graphs");
	} 
	System.out.println();
	if(pathMap.get("Shortest Path of Primary Graph")<=minimumDistance){
		System.out.println("Primary Graph Shortest distance <= the minimum distance");
	} else if(pathMap.get("Shortest Path of Primary Graph")>minimumDistance){
		System.out.println("Primary Graph Shortest distance > the minimum distance");
	} 
	System.setOut(printToConsole);
	System.out.println("Since the amount of data generated in the console is too much \n for better viewing and analysis all the data has been written to 'report.txt'");
}

public static int shortestDistance(int matrix[][], int numberOfVertices){
	Dijkstra dk = new Dijkstra();
	int shortestDist[] = dk.calculateSPD(matrix, numberOfVertices);
	int totalDistance=0;
	for(int d=0;d<numberOfVertices;d++){
		totalDistance = totalDistance + shortestDist[d];
		
	}
	return totalDistance;
}

}

