//No collaborators

import java.io.*;
import java.util.*;




public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE
		 * 1. We need to introduce the colouring scheme
		 * 2. boo
		 * Using an integer array to colour nodes, the first spot corresponds to the source,
		 * last spot to the sink. The colour scheme will be 0 (initialized to this colour) is white, 1 is gray and 2 is black
		 * we set up the predecessors list and set all predecessors to -1 indicating they have no predecessor
		 * 
		 */
		int [] visited = new int[graph.getNbNodes()];		
		int [] predecessor = new int[graph.getNbNodes()];
		for (int i = 0; i<predecessor.length; i++) {
			predecessor[i]=-1;
		}		
		for (int i = 0; i< graph.getNbNodes(); i++) {
			if (predecessor[i] > -1 && i==graph.getDestination() && i!=-1) {
				Stack.add(i);
				break;
			}
			if (visited[i]!=2 && i!=graph.getDestination()) {
				//setting colour to visited, which is 1
				visited[i]=1;
				for (int j = 0; j<graph.getNbNodes()+1; j++) {
					if (j==graph.getNbNodes()) {
						visited[i]=2;
						i = predecessor[i]-1;
						if (!Stack.isEmpty()) {Stack.remove(Stack.size()-1);}
					}else if (graph.getEdge(i, j)!= null && graph.getEdge(i, j).weight != 0 ) {
						if (visited[j]==0) {
							predecessor[j] = i;
							Stack.add(i);
							i=--j;
							break;
						} 
					} 
				}
				if (i<-1) {
					break;
				}
			}
		}
		
		return Stack;
	}
	
	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260855765"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		WGraph residual = new WGraph(graph);
		WGraph theGraph = new WGraph(graph);
		for (Edge e: theGraph.getEdges()) {
			theGraph.setEdge(e.nodes[0], e.nodes[1], 0);
			//setting up backward edges in residual graph
			Edge b = new Edge(e.nodes[1], e.nodes[0], 0);
			residual.addEdge(b);
		}
		ArrayList<Integer> augmentPath = pathDFS(residual.getSource(), residual.getDestination(), residual);
		
		while(!augmentPath.isEmpty()) {
			augmentingPath(residual, theGraph, augmentPath);
			augmentPath = pathDFS(residual.getSource(), residual.getDestination(), residual);
		}
		
		graph = theGraph;
		for (int i =0; i<graph.getNbNodes(); i++) {
			Edge e = graph.getEdge(i, graph.getDestination());
			if (e != null) {
				maxFlow += e.weight;
			}
		}
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	public static void augmentingPath(WGraph graph, WGraph mGraph, ArrayList<Integer> path) {
		int bottle = graph.getEdge(path.get(0), path.get(1)).weight;
		for (int i = 2; i<path.size(); i++) {
			int a = graph.getEdge(path.get(i-1), path.get(i)).weight;
			if (a<bottle) {
				bottle = a;
			}
		}
		for(int i =0; i< path.size()-1; i++) {
			Edge e = mGraph.getEdge(path.get(i), path.get(i+1));
			Edge eBack = mGraph.getEdge(path.get(i+1), path.get(i));
			Edge b = graph.getEdge(path.get(i), path.get(i+1));
			Edge c = graph.getEdge(path.get(i+1), path.get(i));
			if ( e != null) {
				mGraph.setEdge(path.get(i), path.get(i+1), e.weight + bottle);
				//updating residual graph at the same time
				graph.setEdge(path.get(i), path.get(i+1), b.weight - bottle);
				graph.setEdge(path.get(i+1), path.get(i), c.weight + bottle);
			} else {
				mGraph.setEdge(path.get(i+1), path.get(i), eBack.weight - bottle);
				//updating residual graph
				graph.setEdge(path.get(i), path.get(i+1), b.weight - bottle);
				graph.setEdge(path.get(i+1), path.get(i), c.weight + bottle);
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
