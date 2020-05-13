package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private SimpleGraph<Country, DefaultEdge> grafo;
	private BordersDAO dao;
	Map<Country, Country> vicini;
	private int anno;
	private Set<Country> soluzione;
	
	public Model() {
		dao = new BordersDAO();
	}

	public void creaGrafo(int anno) {
		this.anno = anno;
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		Graphs.addAllVertices(grafo, dao.loadAllCountries());
		for(Border b : dao.getCountryPairs(anno)) {
			grafo.addEdge(b.getS1(), b.getS2());
		}
	}
	
	public String output() {
		String s = "";
		for(Country c : grafo.vertexSet()) {
			s += statiConfinanti(c);
		}
		return s;
	}
	
	public String statiConfinanti(Country c) {
		Set<Country> confinanti = Graphs.neighborSetOf(grafo, c);
		ConnectivityInspector ci = new ConnectivityInspector(grafo);
		Set<Country> connessi = ci.connectedSetOf(c);
		return String.format("%s:\t #stati confinanti: %d\t #componenti connesse: %d\n", c.getNome(), confinanti.size(), connessi.size());
	}

	public List<Country> getStati() {
		return dao.loadAllCountries();
	}
	
	public Set<Country> viciniBreadth(Country c){
		
		vicini = new HashMap<>();
		
		BreadthFirstIterator<Country, DefaultEdge> b = new BreadthFirstIterator<>(grafo, c);
		vicini.put(c, null);
		b.addTraversalListener(new TraversalListener<Country, DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
			
				Country p1 = grafo.getEdgeSource(e.getEdge());
				Country p2 = grafo.getEdgeTarget(e.getEdge());
				
				if(!vicini.containsKey(p1) && vicini.containsKey(p2))
					vicini.put(p1, p2);
				else if(!vicini.containsKey(p2) && vicini.containsKey(p1))
					vicini.put(p2, p1);
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
			
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				
			}
			
		});	
		
		while(b.hasNext()) {
			b.next();
		}
		
		vicini.remove(c);
		
		return vicini.keySet();
	}
	
	public Set<Country> viciniDepth(Country c){

		vicini = new HashMap<>();
		
		DepthFirstIterator<Country, DefaultEdge> di = new DepthFirstIterator<>(grafo, c);
		vicini.put(c, null);
		
		di.addTraversalListener(new TraversalListener<Country, DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				Country p1 = grafo.getEdgeSource(e.getEdge());
				Country p2 = grafo.getEdgeTarget(e.getEdge());
				
				if(!vicini.containsKey(p1) && vicini.containsKey(p2))
					vicini.put(p1, p2);
				else if(!vicini.containsKey(p2) && vicini.containsKey(p1))
					vicini.put(p2, p1);
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		while(di.hasNext()) {
			di.next();
		}
		
		vicini.remove(c);
		
		return vicini.keySet();
	}
	
	public Set<Country> viciniIterazione(Country c){
		Set<Country> parziale = new HashSet<>();
		parziale.add(c);
		Set<Country> daVisitare = new HashSet<Country>(parziale);
		cercaVicino(parziale, 0, c, daVisitare);
		parziale.remove(c);
		return parziale;
	}

	private void cercaVicino(Set<Country> parziale, int livello, Country adesso, Set<Country> daVisitare) {
		
		daVisitare = dao.getConfinanti(adesso, anno);
		
		for(Country x : daVisitare) {
			if(!parziale.contains(x)) {
				parziale.add(x);
				cercaVicino(parziale, livello+1, x, daVisitare);
			}
		}
		
	}
	
	
}
