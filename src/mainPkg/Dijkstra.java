package mainPkg;

/**
 * @author Piyush Bhatt
 * Data Structures and Algorithm 2
 * PGM-2
 * This class contains Dijkstra Algorithm which is used to calculate the shortest path for a given graph
 */
public class Dijkstra {

    public int minimumDistance(int distance[], Boolean spt[], int vertices)
    {
        
        int min = Integer.MAX_VALUE, minimumIndex=-1;
 
        for (int ver = 0; ver < vertices; ver++)
            if (spt[ver] == false && distance[ver] <= min)
            {
                min = distance[ver];
                minimumIndex = ver;
            }
 
        return minimumIndex;
    }

public int[] calculateSPD(int firstarray[][], int src)
{
	int dist[] = new int[src]; 
	Boolean sptSet[] = new Boolean[src];

	for (int i = 0; i < src; i++)
	{
		dist[i] = Integer.MAX_VALUE;
		sptSet[i] = false;
	}

	dist[src-1] = 0;

	for (int count = 0; count < src-1; count++)
	{
		
		int u = minimumDistance(dist, sptSet, src);

		sptSet[u] = true;

		
		for (int v = 0; v < src; v++)

			if (!sptSet[v] && firstarray[u][v]!=0 &&
					dist[u] != Integer.MAX_VALUE &&
					dist[u]+firstarray[u][v] < dist[v])
				dist[v] = dist[u] + firstarray[u][v];
	}
	return dist;
}
}
