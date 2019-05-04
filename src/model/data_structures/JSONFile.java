package model.data_structures;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class JSONFile<K extends Comparable <K>,V,A,D> {
	
//	 @SuppressWarnings("unchecked")
//	public void crearJSON(Graph<K,V,A,D> pGrafo) {
//		
//		Graph<K,V,A,D> grafo = pGrafo;
//		Iterator<K> i = grafo.getV().keys();
//		Iterator<K> it = null;
//		JSONArray grafoPrint = new JSONArray();
//		JSONObject adentro = null;
//		JSONObject afuera = null;
//		JSONArray adyacencia = null;
//		K identificador = null;
//		V latitud = null;
//		V longitud = null;
//		Vertex<K,V,A,D> vertice = null;
//		while(i.hasNext()) {
//			
//			identificador = i.next();
//			vertice = grafo.getV().get(identificador);
//			latitud = vertice.getLatitud();
//			longitud = vertice.getLongitud();
//			adentro = new JSONObject();
//			adentro.put("lat", latitud );
//			adentro.put("lon", longitud);
//			adyacencia = new JSONArray();
//			it = grafo.adj(identificador);
//			
//			while(it.hasNext()) {
//				adyacencia.add(it.next());
//			}
//			
//			adentro.put("adjs", adyacencia);
//			afuera = new JSONObject();
//			afuera.put(identificador, adentro);
//			grafoPrint.add(afuera);
//		}
//		
//		try (FileWriter file = new FileWriter("./docs/grafo.json")) {
//			 
//            file.write(grafoPrint.toJSONString());
//            file.flush();
// 
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	}
	    
}
