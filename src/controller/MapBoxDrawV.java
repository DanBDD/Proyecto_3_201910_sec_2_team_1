package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import model.data_structures.ArregloDinamico;
import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.LinearProbing;
import model.data_structures.Vertex;

public class MapBoxDrawV {
	private String rutaArchivo;
	private Graph<Long,String,Double> grafo;
	private Iterable<Long> it;
	
	public MapBoxDrawV(String pRuta, Iterable<Long> pIt, Graph<Long, String, Double> pGrafo) {
		rutaArchivo = pRuta;
		it = pIt;
		grafo = pGrafo;
	}
	
	public String darRutaArchivo() {
		return rutaArchivo;
	}
	public Iterable<Long> darIterable(){
		return it;
	}
	public Graph<Long, String, Double> darGrafo(){
		return grafo;
	}
	
	public void draw() {
		FileWriter file;
		try {
			if(it == null || grafo == null) {
				throw new NullPointerException();
			}
			file = new FileWriter(rutaArchivo);
			BufferedWriter bw = new BufferedWriter(file);
			bw.write("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"<meta charset=utf-8 />\r\n" + 
					"<title>Grafo generado</title>\r\n" + 
					"<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />\r\n" + 
					"<script src='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.js'></script>\r\n" + 
					"<link href='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.css' rel='stylesheet' /> \r\n" + 
					"<style>\r\n" + 
					" body { margin:0; padding:0; }\r\n" + 
					"#map { position:absolute; top:0; bottom:0; width:100%; }\r\n" + 
					"</style>\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"<div id='map'>\r\n" + 
					"</div>\r\n" + 
					"<script>\r\n" + 
					"L.mapbox.accessToken = 'pk.eyJ1IjoiZ2FsMTE3IiwiYSI6ImNqdjYwdTc4YzAwYmg0ZG5seWdtdWh2cmYifQ.UZEaapqtmO8s8esHG99z4A';\r\n" + 
					"var map = L.mapbox.map('map', 'mapbox.streets').setView([38.9072,77.0369], 17);\r\n" + 
					"var calozanoElMejor = [[38.8847, -77.0542 ],[38.9135,-77.0542]];\r\n" + 
					"map.fitBounds(calozanoElMejor);\r\n" );
			int cont = 0;
			
			Iterator<Long> iterador = it.iterator();
			LinearProbing<Long, Vertex<Long, String, Double>> lin = grafo.getV();
			bw.write("var polyline_options = {color: '#ff2fc6'}; \n");
			ArregloDinamico<Vertex<Long,String,Double>> arre = new ArregloDinamico<>(1000);
			while(iterador.hasNext()) {
				Long actual = iterador.next();
				Vertex<Long, String, Double> vertice = lin.get(actual);
				arre.agregar(vertice);
				
				
			}
			for(int i=0; i<arre.darTamano()-1;i++) {
				Vertex<Long,String,Double> actual = arre.darElem(i);
				Vertex<Long,String,Double> sig = arre.darElem(i+1);
				bw.write("var line_points" + cont + " = [ [ "+ actual.getLatitud() + ", " + actual.getLongitud()  + "],"+"[" + sig.getLatitud() + ", " + sig.getLongitud() + "] ];\n");
				bw.write("L.polyline("+"line_points" + cont + ", polyline_options).addTo(map);\r\n");
				cont++;
			}
			
			bw.write("</script>\r\n" + 
					"</body>\r\n" + 
					"</html>");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
