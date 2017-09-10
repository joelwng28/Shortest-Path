/*
 * Name: Zi Zhou Wang
 * EID: zw3948
 */

import java.util.Vector;
import java.util.HashMap;

/* Your solution goes in this file.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */

public class Program2 extends VertexNetwork {
    /* DO NOT FORGET to add a graph representation and 
       any other fields and/or methods that you think 
       will be useful. 
       DO NOT FORGET to modify the constructors when you 
       add new fields to the Program2 class. */
    
    Program2() {
        super();
    }
    
    Program2(String locationFile) {
        super(locationFile);
    }
    
    Program2(String locationFile, double transmissionRange) {
        super(locationFile, transmissionRange);
    }
    
    Program2(double transmissionRange, String locationFile) {
        super(transmissionRange, locationFile);
    }

    public Vector<Vertex> gpsrPath(int sourceIndex, int sinkIndex) {
        /* This method returns a path from a source at location sourceIndex 
           and a sink at location sinkIndex using the GPSR algorithm. An empty 
           path is returned if the GPSR algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
           implements the GPSR algorithm. */
    	HashMap<Vertex, HashMap<Vertex, Edge>> map = new HashMap<Vertex, HashMap<Vertex, Edge>>();
    	for (Vertex u : location) {
            map.put(u, new HashMap<Vertex, Edge>());
        }
    	for (Edge e : edges) {
            Vertex u = location.get(e.getU());
            Vertex v = location.get(e.getV());
            if (u.distance(v) <= transmissionRange) {
                map.get(u).put(v, e);
                map.get(v).put(u, e);
            }
        }
    	Vector<Vertex> route = new Vector<Vertex>();
        Vertex sink = location.get(sinkIndex);
        Vertex source = location.get(sourceIndex);
        return gpsrPath(source, sink, route, map);
    }
    public Vector<Vertex> gpsrPath(Vertex source, Vertex sink, Vector<Vertex> route, HashMap<Vertex, HashMap<Vertex, Edge>> map){
    	route.add(source);
    	if(source.equals(sink)){
        	return route;
    	}
    	Vertex next = null;
        double dist = source.distance(sink);
        for (Vertex v : map.get(source).keySet()) {
            double test = sink.distance(v);
            if (test < dist) {
                dist = test;
                next = v;
            }
        }
        if (next == null) {
            return new Vector<Vertex>(0);
        } 
        return gpsrPath(next, sink, route, map);
    }
    
    public Vector<Vertex> dijkstraPathLatency(int sourceIndex, int sinkIndex) {
        /* This method returns a path (shortest in terms of latency) from a source at
           location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
           An empty path is returned if Dijkstra's algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
           implements Dijkstra's algorithm. */
    	HashMap<Vertex, HashMap<Vertex, Edge>> edgeup = new HashMap<Vertex, HashMap<Vertex, Edge>>();
        for (Vertex u : location) {
            edgeup.put(u, new HashMap<Vertex, Edge>());
        }

        for (Edge e : edges) {
            Vertex u = location.get(e.getU());
            Vertex v = location.get(e.getV());
            if (u.distance(v) <= transmissionRange) {
                edgeup.get(u).put(v, e);
                edgeup.get(v).put(u, e);
            }
        }
    	Vertex source = location.get(sourceIndex);
    	Vertex sink = location.get(sinkIndex);
    	HashMap<Vertex, Double> cost = new HashMap<Vertex, Double>();
        HashMap<Vertex, Vertex> last = new HashMap<Vertex, Vertex>();
        for (Vertex v : location) {
            cost.put(v, Double.MAX_VALUE);
            last.put(v, null);
        }
        cost.put(source, 0.0);
        Vector<Vertex> Q = new Vector<Vertex>(location);
        while(!Q.isEmpty()){
        	Vertex u = source;
            double min = Double.MAX_VALUE;
            for (Vertex v : Q) {
                if (cost.get(v) < min) {
                    u   = v;
                    min = cost.get(v);
                }
            }
            if (min == Double.MAX_VALUE) break;
            Q.remove(u);
            if (u.equals(sink)) break;
            for (Vertex v : Q) {
                Edge e = edgeup.get(u).get(v);
                if (e != null) {
                    double alt = cost.get(u) + e.getW();
                    if (alt < cost.get(v)) {
                        cost.put(v, alt);
                        last.put(v, u);
                    }
                }
            }
        }
        Vector<Vertex> path = new Vector<Vertex>();
        Vertex u = sink;
        while (!u.equals(source)) {
            path.add(u);
            u = last.get(u);
            if (u == null) {
                return new Vector<Vertex>(0);
            }
        }
        return path;
    }
    
    public Vector<Vertex> dijkstraPathHops(int sourceIndex, int sinkIndex) {
        /* This method returns a path (shortest in terms of hops) from a source at
           location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
           An empty path is returned if Dijkstra's algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
           implements Dijkstra's algorithm. */
    	HashMap<Vertex, HashMap<Vertex, Edge>> edgeup = new HashMap<Vertex, HashMap<Vertex, Edge>>();
        for (Vertex u : location) {
            edgeup.put(u, new HashMap<Vertex, Edge>());
        }

        for (Edge e : edges) {
            Vertex u = location.get(e.getU());
            Vertex v = location.get(e.getV());
            if (u.distance(v) <= transmissionRange) {
                edgeup.get(u).put(v, e);
                edgeup.get(v).put(u, e);
            }
        }
    	Vertex source = location.get(sourceIndex);
    	Vertex sink = location.get(sinkIndex);
    	HashMap<Vertex, Double> cost = new HashMap<Vertex, Double>();
        HashMap<Vertex, Vertex> last = new HashMap<Vertex, Vertex>();
        for (Vertex v : location) {
            cost.put(v, Double.MAX_VALUE);
            last.put(v, null);
        }
        cost.put(source, 0.0);
        Vector<Vertex> Q = new Vector<Vertex>(location);
        while(!Q.isEmpty()){
        	Vertex u = source;
            double min = Double.MAX_VALUE;
            for (Vertex v : Q) {
                if (cost.get(v) < min) {
                    u   = v;
                    min = cost.get(v);
                }
            }
            if (min == Double.MAX_VALUE) break;
            Q.remove(u);
            if (u.equals(sink)) break;
            for (Vertex v : Q) {
                Edge e = edgeup.get(u).get(v);
                if (e != null) {
                    double alt = cost.get(u) + 1;
                    if (alt < cost.get(v)) {
                        cost.put(v, alt);
                        last.put(v, u);
                    }
                }
            }
        }
        Vector<Vertex> path = new Vector<Vertex>();
        Vertex u = sink;
        while (!u.equals(source)) {
            path.add(u);
            u = last.get(u);
            if (u == null) {
                return new Vector<Vertex>(0);
            }
        }
        return path;
    }
    
}

