package controller;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.data_structures.ArregloDinamico;
import model.data_structures.Bag;
import model.data_structures.Cola;
import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.JSONFile;
import model.data_structures.LinearProbing;
import model.data_structures.Vertex;

import java.util.*;

import view.MovingViolationsManagerView;

public class Controller extends DefaultHandler{

	private MovingViolationsManagerView view;

	private boolean empezo;

	private boolean highWay;

	private boolean repetido;

	private ArregloDinamico<Long> nodos ;

	private Graph<Long, String, Double> grafo;

	private Graph<Long, String, Double> grafo1;

	public static final double R = 6372.8;

	private static final String ruta = "./data/Central-WashingtonDC-OpenStreetMap.xml";


	public Controller() throws Exception {


		view = new MovingViolationsManagerView();
		grafo = new Graph<Long, String, Double>();		
		grafo1 = new Graph<Long, String, Double>();		
	}

	public void run() {
		Scanner sc = new Scanner(System.in);

		boolean fin=false;
		while(!fin)
		{
			view.printMenu();
			int option = sc.nextInt();

			switch(option)
			{
			case 0:
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					spf.setNamespaceAware(true);

					SAXParser saxParser = spf.newSAXParser();
					XMLReader xmlReader = saxParser.getXMLReader();
					xmlReader.setContentHandler(this);
					xmlReader.parse(ruta);
				}
				catch(Exception e) {
					e.getMessage();
				}
				break;
			case 1:
				escribirVerticesJson();
				escribirArcosJson();
				System.out.println("Termino escritura, revisar carpeta docs.");
				break;
			case 2:
				cargarVerticesJson();
				cargarArcosJson();

				break;
			case 4:	
				fin=true;
				sc.close();
				break;
			}
		}
	}

	public void startDocument() throws SAXException {
		empezo = false;
		highWay = false;
		repetido = false;
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

		if(qName.equals("node")){
			//			String info =  atts.getValue(1) + "|" + atts.getValue(2);
			//			System.out.println(info.toString());
			//			System.out.println(atts.getLength());
			grafo.addVertex(Long.parseLong(atts.getValue(0)),  atts.getValue(1) + "|" + atts.getValue(2));
			//			for(int n = 0; n < 3; n++) {
			//				System.out.println(atts.getQName(n)+ ": " + atts.getValue(n));						
			//			}
		}

		else if(qName.equals("way")){
			if(empezo == false){

				empezo = true;
				nodos = null;
				nodos =  new ArregloDinamico<>(10);
				highWay = false;
			}

			//			System.out.println("Empez� nodo way de tamano " + atts.getLength());

		}
		else if(qName.equals("nd") && empezo){
			//			System.out.println("Empez� nodo de nd");
			for(int n = 0; n < atts.getLength(); n++) {
				//				System.out.println(atts.getQName(n)+ ": " + atts.getValue(n));
				nodos.agregar(Long.parseLong(atts.getValue(n)));
			}
		}
		else if(qName.equals("tag") && empezo ){
			if(atts.getValue(0).equals("highway")) {
				highWay = true;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("way")){	//Pregunta si termino de leer etiqueta way	

			if(empezo){		//Pregunta si habia empezado a leer una etiqueta way, si es as� empezo es false ahora

				empezo = false;		
				if(highWay == false) {		//Pregunta si uno de los tags del way ten�a highway como valor. Si es falso elimina la lista de nodos nd
					nodos = null;
					nodos = new ArregloDinamico<>(10);
				}
				else {		//Si hab�a way, procesa los nodos del way.
					for(int i = 0; i<nodos.darTamano() - 1 ;i++) {
						double hav =0.0;
						try {
							hav = haversine(Double.parseDouble(grafo.getV().get(nodos.darElem(i)).getLatitud()), Double.parseDouble(grafo.getV().get(nodos.darElem(i)).getLongitud()), Double.parseDouble(grafo.getV().get(nodos.darElem(i+1)).getLatitud()), Double.parseDouble(grafo.getV().get(nodos.darElem(i+1)).getLongitud()));
							Long idNodoOrigen = nodos.darElem(i);
							Long idNodoDestino = nodos.darElem(i+1);

							Iterator<Long> id = grafo.getV().get(idNodoOrigen).getAdjsIds().iterator();
							Long actual = null;
							while(!repetido && id.hasNext()) {
								actual = id.next();
								if(actual.equals(idNodoDestino)) {
									repetido = true;
								}
							}

							if(repetido == false) {
								grafo.addEdge(idNodoOrigen, idNodoDestino, hav);
							}
							else if(repetido == true) {
								repetido = false;
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	public  double haversine(double lat1, double lon1, double lat2, double lon2) {

		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}
	public void endDocument() throws SAXException {
		System.out.println("\n Termino");
		LinearProbing<Long, Vertex<Long, String, Double>> lin = grafo.getV();
		Iterator<Long> it = lin.keys();
		Vertex<Long, String, Double> v = null;
		while(it.hasNext()) {
			Long idVertex = it.next();
			v = lin.get(idVertex);
			if(v.getAdjsIds().size() == 0) {
				v.setId(0L);
				v = null;
				grafo.reducirV();

			}
		}
		System.out.println("Numero de vertices: " + grafo.V());
		System.out.println("Numero de arcos: " + grafo.E());
	}
	public void escribirVerticesJson() {
		LinearProbing<Long, Vertex<Long, String, Double>> lin = grafo.getV();
		Iterator<Long> it = lin.keys();
		JSONArray verticesPrint = new JSONArray();
		JSONObject adentro = null;
		JSONObject afuera = null;
		Long identificador = null;
		String latitud = null;
		String longitud = null;
		int c=0;
		Vertex<Long,String,Double> vertice = null;

		while(it.hasNext()){
			//adentro
			identificador = it.next();
			if(lin.get(identificador).getId()!=0L) {
				vertice = grafo.getV().get(identificador);
				latitud = vertice.getLatitud();
				longitud = vertice.getLongitud();
				adentro = new JSONObject();
				adentro.put("lat", latitud );
				adentro.put("lon", longitud);
				adentro.put("id",identificador);
				//afuera
				afuera = new JSONObject();
				afuera.put("vertice", adentro);

				verticesPrint.add(afuera);
				c++;
			}

		}
		System.out.println("vertices cargados"+c);
		try (FileWriter file = new FileWriter("./docs/vertices1.json")) {

			file.write(verticesPrint.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cargarVerticesJson() {
		try {
			JSONParser parser = new JSONParser();
			JSONArray a = (JSONArray) parser.parse(new FileReader("./docs/vertices1.json"));
			for (Object o : a)
			{
				JSONObject actual = (JSONObject) o;
				JSONObject e = (JSONObject) actual.get("vertice");
				Long id=  (Long) e.get("id");
				String lat = (String) e.get("lat");
				String lon=(String) e.get("lon");

				grafo1.addVertex(id, lat+"|"+lon);
				//				System.out.println(id);
				//				System.out.println(lat);
				//				System.out.println(lon);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void escribirArcosJson() {
		LinearProbing<Long, Vertex<Long, String,  Double>> lin = grafo.getV();
		Iterator<Long> it = lin.keys();//ids vertices
		JSONArray arcosPrint = new JSONArray();
		ArrayList<Edge<Long, String, Double>> edges= new ArrayList<>();
		int c = 0;
		int c1=0;
		while(it.hasNext()) 
		{
			Long idVertice= it.next();
			Vertex<Long, String, Double> verticeOrigen = lin.get(idVertice);
			Iterator<Long> i = verticeOrigen.getEdges().keys();
			while(i.hasNext())
			{
				edges.add(new Edge<Long, String, Double>(lin.get(idVertice), lin.get(i.next()), 0.0));
				c++;
			}

		}
		for (int j = 0; j < edges.size()-1; j++) 
		{
			Edge<Long, String, Double> actual= edges.get(j);

			if(actual!=null) 
			{

				for (int k = j+1; k < edges.size(); k++) 
				{

					Edge<Long, String, Double> actual2=edges.get(k);

					if(actual2!=null) 
					{

						if(actual.getStartVertex().equals(actual2.getEndVertex()) && actual.getEndVertex().equals(actual2.getStartVertex()))
						{
							edges.remove(k);

							c--;

						}
					}
				}

			}

		}
		JSONObject adentro=new JSONObject();
		for (int i = 0; i < edges.size(); i++) 
		{
			Edge<Long, String, Double> e=edges.get(i);
			Vertex<Long, String,  Double> in=e.getStartVertex();
			Vertex<Long, String,  Double> fi=e.getEndVertex();
			adentro.put("inicio",in.getId());
			adentro.put("fin", fi.getId());
			adentro.put("peso", haversine(Double.parseDouble(in.getLatitud()), Double.parseDouble(in.getLongitud()), Double.parseDouble(fi.getLatitud()), Double.parseDouble(fi.getLongitud())));
			JSONObject afuera = new JSONObject();
			afuera.put("arco", adentro);
			arcosPrint.add(afuera);
		}


		try (FileWriter file = new FileWriter("./docs/arcos1.json")) {

			file.write(arcosPrint.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void cargarArcosJson() {
		try {
			JSONParser parser = new JSONParser();
			JSONArray a = (JSONArray) parser.parse(new FileReader("./docs/arcos1.json"));
			ArregloDinamico<Long> b = new ArregloDinamico<>(12);
			for (Object o : a)
			{
				JSONObject actual = (JSONObject) o;
				JSONObject e = (JSONObject) actual.get("arco");

				Double peso=  (Double) e.get("peso");
				Long inicio = (Long) e.get("inicio");
				Long fin = (Long) e.get("fin");

				grafo1.addEdge(inicio, fin, peso);

			}	

			System.out.println("Arcos cargados con JSON " + grafo1.E());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
