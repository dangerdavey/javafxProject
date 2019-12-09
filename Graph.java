//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Graph
// Files:           NONE
// Course:          CS 400 Fall 2019
//
// Author:          John Yuu
// Email:           yuu@wisc.edu
// Lecturer's Name: Debra Deppeler
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (name of your pair programming partner)
// Partner Email:   (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Filename:   Graph.java
 * Project:    P4 Package Manager
 * Authors:    John Yuu
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT 
{
	int size;
	int order;
	private ArrayList<Vertex> graph;
	
	/**
	 * Vertex class for new vertices
	 *
	 */
	private class Vertex
	{
		String name;
		ArrayList<Vertex> dependencies;
		private Vertex(String name)
		{
			this.name = name;
			dependencies = new ArrayList<Vertex>();
		}
		/**
		 * Finds dependency if vertex is dependent on this vertex
		 * 
		 * @param vertex Vertex to find dependency
		 * @return Index in dependencies array
		 */
		private int findDependency(String vertex)
		{
			for(int i = 0; i < dependencies.size(); i++)
			{
				if(dependencies.get(i).name.equals(vertex))
				{
					return i;
				}
			}
			return -1;
		}
	}
	
	/*
	 * Default no-argument constructor
	 */ 
	public Graph() 
	{
		this.size = 0;
		this.order = 0;
		graph = new ArrayList<Vertex>();
	}

	/**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void addVertex(String vertex) 
	{
		if(vertex == null)
		{
			return;
		}
		//Returns if vertex is already in graph
		for(int i = 0; i < graph.size(); i++)
		{
			if(graph.get(i).name.equals(vertex))
			{
				return;
			}
		}
		graph.add(new Vertex(vertex));
		this.order++;
	}

	/**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void removeVertex(String vertex) 
	{
		boolean removed = false;
		if(vertex == null)
		{
			return;
		}
		//Returns if graph is empty, as there would be nothing to remove
		if(graph.isEmpty())
		{
			return;
		}
		//Removes vertex if it is found
		for(int i = 0; i < graph.size(); i++)
		{
			if(graph.get(i).name.equals(vertex))
			{
				graph.remove(i);
				this.order--;
				removed = true;
				break;
			}
		}
		//Removes dependencies
		if(removed)
		{
			for(int i = 0; i < graph.size(); i++)
			{
				for(int j = 0; j < graph.get(i).dependencies.size(); j++)
				{
					if(graph.get(i).dependencies.get(j).name.equals(vertex))
					{
						graph.get(i).dependencies.remove(j);
					}
				}
			}
		}
	}
	
	/**
	 * Vertex finding helper method
	 * 
	 * @param vertex Vertex to find
	 * @return Vertex index in graph
	 */
	private int findVertex(String vertex)
	{
		for(int i = 0; i < graph.size(); i++)
		{
			if(graph.get(i).name.equals(vertex))
			{
				return i;
			}
		}
		return -1;
	}

	/**
     * Add the edge from vertex1 to vertex2
     * to this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * add vertex, and add edge, no exception is thrown.
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
	 */
	public void addEdge(String vertex1, String vertex2) 
	{
		if(vertex1 == null || vertex2 == null)
		{
			return;
		}
		//Adds vertex1 if it doesn't exist in graph
		if(findVertex(vertex1) == -1)
		{
			addVertex(vertex1);
		}
		//Adds vertex2 if it doesn't exist in graph
		if(findVertex(vertex2) == -1)
		{
			addVertex(vertex2);
		}
		if((graph.get(findVertex(vertex1)).findDependency(vertex2) == -1) 
				&& (graph.get(findVertex(vertex2)).findDependency(vertex1) == -1))
		{
			graph.get(findVertex(vertex1)).dependencies.add(graph.get(findVertex(vertex2)));
			graph.get(findVertex(vertex2)).dependencies.add(graph.get(findVertex(vertex1)));
			this.size++;
		}
	}
	
	/**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     */
	public void removeEdge(String vertex1, String vertex2) 
	{
		//Returns if either vertex1 or vertex2 are null
		if(vertex1 == null || vertex2 == null)
		{
			return;
		}
		if(findVertex(vertex1) == -1)
		{
			return;
		}
		if(findVertex(vertex2) == -1)
		{
			return;
		}
		int dependencyIndex1 = graph.get(findVertex(vertex1)).findDependency(vertex2);
		if(dependencyIndex1 != -1)
		{
			graph.get(findVertex(vertex1)).dependencies.remove(dependencyIndex1);
		}
		int dependencyIndex2 = graph.get(findVertex(vertex2)).findDependency(vertex1);
		if(dependencyIndex2 != -1)
		{
			graph.get(findVertex(vertex2)).dependencies.remove(dependencyIndex2);
		}
		this.size--;
	}	

	/**
     * Returns a Set that contains all the vertices
     * 
	 */
	public Set<String> getAllVertices() 
	{
		Set<String> vertices = new HashSet<String>();
		for(int i = 0; i < graph.size(); i++)
		{
			vertices.add(graph.get(i).name);
		}
		return vertices;
	}

	/**
     * Get all the neighbor (adjacent) vertices of a vertex
     *
	 */
	public List<String> getAdjacentVerticesOf(String vertex) 
	{
		List<String> adjVertices = new ArrayList<String>();
		if(vertex.equals(null))
		{
			return adjVertices;
		}
		Vertex temp = graph.get(findVertex(vertex));
		if(temp == null)
		{
			return adjVertices;
		}
		for(int i = 0; i < temp.dependencies.size(); i++)
		{
			adjVertices.add(temp.dependencies.get(i).name);
		}
		return adjVertices;
	}
	
	/**
     * Returns the number of edges in this graph.
     */
    public int size() 
    {
        return this.size;
    }

	/**
     * Returns the number of vertices in this graph.
     */
	public int order() 
	{
        return this.order;
    }
}
