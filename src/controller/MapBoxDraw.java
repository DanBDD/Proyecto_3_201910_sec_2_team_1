package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import model.data_structures.ArregloDinamico;
import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.LinearProbing;
import model.data_structures.SeparateChaining;
import model.data_structures.Vertex;

public class MapBoxDraw {

	private String rutaArchivo;
	private Graph<Long,String,Double> grafo;
	private ArregloDinamico<Vertex<Long, String, Double>> arr;
	private Iterable<Edge<Long, String, Double>> it;
	
	public MapBoxDraw(String pRuta, Iterable<Edge<Long, String, Double>> pIterable, Graph<Long, String, Double> pGraph) {
		rutaArchivo = pRuta;
		grafo = pGraph;
		it = pIterable;
	}

	public MapBoxDraw(String pRuta, Graph<Long, String, Double> pGrafo) {
		rutaArchivo = pRuta;
		grafo = pGrafo;
	}

	public MapBoxDraw(String pRuta, ArregloDinamico<Vertex<Long, String, Double>> pArr) {
		rutaArchivo = pRuta;
		arr = pArr;
	}


	public Graph<Long, String, Double> darGrafo() {
		return grafo;
	}

	public ArregloDinamico<Vertex<Long,String,Double>> darArreglo(){
		return arr;
	}

	public String darRuta() {
		return rutaArchivo;
	}

	public void drawIterable() {
		FileWriter file;
		try {
			if(it == null) {
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
			
			Iterator<Edge<Long, String, Double>> iterador = it.iterator();
			LinearProbing<Long, Vertex<Long, String, Double>> lin = grafo.getV();
			bw.write("var polyline_options = {color: '#ff2fc6'}; \n");
			while(iterador.hasNext()) {
				Edge<Long, String, Double> actual = iterador.next();
				Long iniId = actual.getStartVertexId();
				Long finId = actual.getEndVertexId();
				Vertex<Long, String, Double> inicial = lin.get(iniId);
				Vertex<Long, String, Double> fin = lin.get(finId);
				bw.write("var line_points" + cont + " = [ [ "+ inicial.getLatitud() + ", " + inicial.getLongitud()  + "],"+"[" + fin.getLatitud() + ", " + fin.getLongitud() + "] ];\n");
	
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
//	public void drawGraph() {
//		FileWriter file;
//		try {
//
//			file = new FileWriter(rutaArchivo);
//			BufferedWriter bw = new BufferedWriter(file);
//			bw.write("<!DOCTYPE html>\r\n" + 
//					"<html>\r\n" + 
//					"<head>\r\n" + 
//					"<meta charset=utf-8 />\r\n" + 
//					"<title>Grafo generado</title>\r\n" + 
//					"<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />\r\n" + 
//					"<script src='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.js'></script>\r\n" + 
//					"<link href='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.css' rel='stylesheet' /> \r\n" + 
//					"<style>\r\n" + 
//					" body { margin:0; padding:0; }\r\n" + 
//					"#map { position:absolute; top:0; bottom:0; width:100%; }\r\n" + 
//					"</style>\r\n" + 
//					"</head>\r\n" + 
//					"<body>\r\n" + 
//					"<div id='map'>\r\n" + 
//					"</div>\r\n" + 
//					"<script>\r\n" + 
//					"L.mapbox.accessToken = 'pk.eyJ1IjoiZ2FsMTE3IiwiYSI6ImNqdjYwdTc4YzAwYmg0ZG5seWdtdWh2cmYifQ.UZEaapqtmO8s8esHG99z4A';\r\n" + 
//					"var map = L.mapbox.map('map', 'mapbox.streets').setView([38.9072,77.0369], 17);\r\n" + 
//					"var calozanoElMejor = [[38.8847, -77.0542 ],[38.9135,-77.0542]];\r\n" + 
//					"map.fitBounds(calozanoElMejor);\r\n" );
//			int cont = 0;
//			
//			LinearProbing<Long, Vertex<Long, String, Double>> lin = grafo.getV();
//			SeparateChaining<Long, ArregloDinamico<Edge<Long,String,Double>>> sep = grafo.getEdges();
//			Iterable<Long> iti = sep.keys();
//			Iterator<Long> iterador = iti.iterator();
//			
//			bw.write("var polyline_options = {color: '#ff2fc6'}; \n");
//			while(iterador.hasNext()) {
//				Long id
//				Edge<Long, String, Double> actual = iterador.next();
//				bw.write("var line_points" + cont + " = [");
//				Long iniId = actual.getStartVertexId();
//				Long finId = actual.getEndVertexId();
//				Vertex<Long, String, Double> inicial = lin.get(iniId);
//				Vertex<Long, String, Double> fin = lin.get(finId);
//				bw.write("[" + inicial.getLatitud() + ", " + inicial.getLongitud()  + "],");
//				bw.write("[" + fin.getLatitud() + ", " + fin.getLongitud() + "] ];\n");
//				bw.write("L.polyline("+"line_points" + cont + ", polyline_options).addTo(map);\r\n");
//				cont++;
//			}
//			
//			bw.write("</script>\r\n" + 
//					"</body>\r\n" + 
//					"</html>");
//			bw.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public void drawVertices() {
		FileWriter file;
		
		try {
			if(arr == null) {
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
	
			bw.write("var polyline_options = {color: '#ff2fc6'}; \n");
			for(int i=0; i<arr.darTamano();i++) {
				Vertex<Long, String, Double> actual = arr.darElem(i);
				bw.write("L.marker( [" + actual.getLatitud() + ", " + actual.getLongitud() + "], { title: \"Nodo\"} ).addTo(map); \r\n ");
			
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
