package controller;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
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
import model.data_structures.MaxColaPrioridad;
import model.data_structures.Vertex;
import model.vo.VOMovingViolations;

import java.util.*;

import view.MovingViolationsManagerView;

public class Controller extends DefaultHandler{


	public static final String rutaEnero = "./data/January_wgs84_corregido.csv";

	/**
	 * Ruta de archivo CSV Febrero.
	 */
	public static final String rutaFebrero = "./data/February_wgs84.csv";

	/**
	 * Ruta de archivo CSV Marzo.
	 */
	public static final String rutaMarzo = "./data/March_wgs84.csv";

	/**
	 * Ruta de archivo CSV Abril.
	 */
	public static final String rutaAbril = "./data/Abril_wgs84.csv";

	/**
	 * Ruta de archivo CSV Mayo.
	 */
	public static final String rutaMayo = "./data/May_wgs84.csv";
	/**
	 * Ruta de archivo CSV Junio.
	 */
	public static final String rutaJunio = "./data/June_wgs84.csv";
	/**
	 * Ruta de archivo CSV Julio.
	 */
	public static final String rutaJulio = "./data/July_wgs84.csv";
	/**
	 * Ruta de archivo CSV Agosto.
	 */
	public static final String rutaAgosto = "./data/August_wgs84.csv";
	/**
	 * Ruta de archivo CSV Septiembre.
	 */
	public static final String rutaSeptiembre = "./data/September_wgs84.csv";
	/**
	 * Ruta de archivo CSV Octubre.
	 */
	public static final String rutaOctubre = "./data/October_wgs84.csv";
	/**
	 * Ruta de archivo CSV Noviembre.
	 */
	public static final String rutaNoviembre = "./data/November_wgs84.csv";
	/**
	 * Ruta de archivo CSV Diciembre.
	 */
	public static final String rutaDiciembre = "./data/December_wgs84.csv";

	private String[] sem1;

	private String[] sem2;

	private ArregloDinamico<VOMovingViolations> arreglo;

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

		arreglo = new ArregloDinamico<VOMovingViolations>(100);
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
			case 3:
				System.out.println("Inserte cu�l semestre desea cargar");
				int param = sc.nextInt();
				cargarInfracciones(param);
				break;
			case 4:
				juntarVerticesInfracciones();
			case 5:	
				fin=true;
				sc.close();
				break;
			}
		}
	}

	private void juntarVerticesInfracciones() 
	{
		LinearProbing<Long, Vertex<Long, String, Double>> vertices = grafo.getV();
		Iterator<Long> it = vertices.keys();

		for (int i = 0; i < arreglo.darTamano(); i++) 
		{
			VOMovingViolations infraccion = arreglo.darElem(i);
			Double lat= Double.parseDouble(infraccion.darLat());
			Double lon= Double.parseDouble(infraccion.darLon());
			MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
			while(it.hasNext())
			{
				Long num= it.next();
				Vertex<Long, String, Double> vertice = vertices.get(num);
				Double lat2= Double.parseDouble(vertice.getLatitud());
				Double lon2= Double.parseDouble(vertice.getLongitud());
				cola.agregar(haversine(lat, lon, lat2, lon2), vertice.getId());
			}
			Long corto= cola.delMax();
			vertices.get(corto).aumentarCantidadIngfracciones();
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
			grafo.addVertex(Long.parseLong(atts.getValue(0)),  atts.getValue(1) + "|" + atts.getValue(2),0);
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
		int infracciones=0;
		Vertex<Long,String,Double> vertice = null;

		while(it.hasNext()){
			//adentro
			identificador = it.next();
			if(lin.get(identificador).getId()!=0L) {
				vertice = grafo.getV().get(identificador);
				latitud = vertice.getLatitud();
				longitud = vertice.getLongitud();
				infracciones = vertice.getCantidadInfracciones();
				adentro = new JSONObject();
				adentro.put("lat", latitud );
				adentro.put("lon", longitud);
				adentro.put("id",identificador);
				adentro.put("infracciones", infracciones);
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
			JSONArray a = (JSONArray) parser.parse(new FileReader("./data/vertices1.json"));
			for (Object o : a)
			{
				JSONObject actual = (JSONObject) o;
				JSONObject e = (JSONObject) actual.get("vertice");
				Long id=  (Long) e.get("id");
				String lat = (String) e.get("lat");
				String lon=(String) e.get("lon");
				String infra= (String)e.get("infracciones");
				grafo1.addVertex(id, lat+"|"+lon, Integer.parseInt(infra));
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
			JSONArray a = (JSONArray) parser.parse(new FileReader("./data/arcos1.json"));
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
	private void cargarInfracciones(int numeroSemestre) {
		sem1 = new String[6];
		sem2 = new String[6];

		int enero= 0;
		int f=0;
		int a =0;
		int m =0;
		int mayo =0;
		int j =0;
		int ju =0;
		int ag = 0;
		int s =0;
		int o =0;
		int n = 0;
		int d = 0;
		int totSem = 0;
		int contMes=0;
		for(int i = 0; i<6;i++){
			if(i == 0){
				sem1[i] = rutaEnero;
				sem2[i] = rutaJulio;
			}
			else if(i == 1){
				sem1[i] = rutaFebrero;
				sem2[i] = rutaAgosto;
			}
			else if(i == 2){
				sem1[i] = rutaMarzo;
				sem2[i] = rutaSeptiembre;
			}
			else if(i == 3){
				sem1[i] = rutaAbril;
				sem2[i] = rutaOctubre;
			}
			else if(i == 4){
				sem1[i] = rutaMayo;
				sem2[i] = rutaNoviembre;
			}
			else if(i == 5){
				sem1[i] = rutaJunio;
				sem2[i] = rutaDiciembre;
			}
		}
		try{
			if(numeroSemestre ==2){

				for(int i = 0;i<sem2.length;i++){
					contMes = 0;
					String mes = sem2[i];
					int latitud = -1;
					int longitud = -1;

					if(i==10 || i==11 || i==12) {
						latitud = 19;
						longitud = 20; 
					}
					else {
						latitud = 18;
						longitud = 19;
					}
					CSVReader lector = new CSVReader(new FileReader(mes), ';');
					String[] linea = lector.readNext();
					while ((linea = lector.readNext()) != null) {

						String obID = linea[0];
						String lat = linea[latitud];
						String lon = linea[longitud];
						VOMovingViolations vo = new VOMovingViolations(obID,lat, lon);
						arreglo.agregar(vo);
						totSem++;
						contMes++;
						if(i == 0){
							ju=contMes;

						}
						else if(i == 1){
							ag=contMes;
						}
						else if(i == 2){
							s=contMes;

						}
						else if(i == 3){
							o=contMes;

						}
						else if(i == 4){
							n=contMes;

						}
						else if(i == 5){
							d=contMes;

						}
					}

					lector.close();
					System.out.println("Total de infracciones del semestre " + totSem);
					System.out.println("Infracciones de: ");
					System.out.println("Julio " + ju);
					System.out.println("Agosto " + ag);
					System.out.println("Septiembre " + s);
					System.out.println("Octubre" + o);
					System.out.println("Noviembre " + n);
					System.out.println("Diciembre " + d);
				}
			}

			else{
				for(int i = 0;i<sem1.length;i++){
					contMes = 0;
					String mes = sem1[i];
					int latitud = -1;
					int longitud = -1;

					if(i==0) {
						latitud = 17;
						longitud = 18; 
					}
					else {
						latitud = 18;
						longitud = 19;
					}
					CSVReader lector = new CSVReader(new FileReader(mes), ';');

					String[] linea = lector.readNext();
					while ((linea = lector.readNext()) != null) {

						String obID = linea[1];
						String lat = linea[latitud];
						String lon = linea[longitud];
						VOMovingViolations vo = new VOMovingViolations(obID,lat, lon);
						arreglo.agregar(vo);	
						totSem++;
						contMes++;
						if(i == 0){
							enero=contMes;
						}
						else if(i == 1){
							f=contMes;
						}
						else if(i == 2){
							m=contMes;

						}
						else if(i == 3){
							a=contMes;

						}
						else if(i == 4){
							mayo=contMes;

						}
						else if(i == 5){
							j=contMes;

						}
					}
					System.out.println("Total de infracciones del semestre " + totSem);
					System.out.println("Infracciones de: ");
					System.out.println("Enero " + enero);
					System.out.println("Febrero " + f);
					System.out.println("Mazro " + m);
					System.out.println("Abril " + a);
					System.out.println("Mayo " + mayo);
					System.out.println("Junio " + j);
					lector.close();
				}
			}
		}
		catch (IOException e) {

			e.printStackTrace();
		}
	}
}
