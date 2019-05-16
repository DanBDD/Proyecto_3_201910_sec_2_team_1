package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.opencsv.CSVReader;
import com.sun.xml.internal.ws.api.pipe.NextAction;

import model.data_structures.ArregloDinamico;
import model.data_structures.BFS;
import model.data_structures.Bag;
import model.data_structures.DFS;
import model.data_structures.Graph;
import model.data_structures.LinearProbing;
import model.data_structures.MaxHeapCP;
import model.data_structures.SeparateChaining;
import model.data_structures.Vertex;
import model.vo.VOMovingViolations;
import view.MovingViolationsManagerView;

public class Controller {

	// Componente vista (consola)
	private MovingViolationsManagerView view;

	//TODO Definir los atributos de estructuras de datos del modelo del mundo del proyecto

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

	private ArregloDinamico<Long> arregloIdsGrafo;

	//	private boolean empezo;
	//
	//	private boolean highWay;
	//
	//	private boolean repetido;
	//
	//	private ArregloDinamico<Long> nodos ;

	private Graph<Long, String, Double> grafo;
	private Graph<Long, String, Double> grafoR2y9;


	private Comparable[] muestraVertices;
	private Comparable[] muestra;

	private MaxHeapCP<Vertex<Long, String, Double>> heap;

	public static final double R = 6372.8;
	/**
	 * Metodo constructor
	 */
	public Controller()
	{
		view = new MovingViolationsManagerView();
		arreglo = new ArregloDinamico<VOMovingViolations>(100);
		grafo = new Graph<Long, String, Double>();	
		arregloIdsGrafo=new ArregloDinamico<>(3000);
		heap= new MaxHeapCP<>();
		grafoR2y9= new Graph<Long, String, Double>();
	}

	/**
	 * Metodo encargado de ejecutar los  requerimientos segun la opcion indicada por el usuario
	 */
	public void run(){

		long startTime;
		long endTime;
		long duration;

		Scanner sc = new Scanner(System.in);
		boolean fin = false;


		while(!fin){
			view.printMenu();

			int option = sc.nextInt();
			int idVertice1 = 0;
			int idVertice2 = 0;


			switch(option){

			case 0:
				String RutaArchivoVertices = "";
				String RutaArchivoArcos="";
				view.printMessage("Escoger el grafo a cargar: (1) Downtown  o (2)Ciudad Completa.");
				int ruta = sc.nextInt();
				if(ruta == 1)
				{
					RutaArchivoVertices = ""; //TODO Dar la ruta del archivo de Downtown
					RutaArchivoArcos="";
				}

				else
				{
					RutaArchivoVertices = "./data/finalGraph.json"; //TODO Dar la ruta del archivo de la ciudad completa
					RutaArchivoArcos="./data/arcosGrande.json";

				}

				startTime = System.currentTimeMillis();
				loadJSONVertices(RutaArchivoVertices);
				loadJSONArcos(RutaArchivoArcos);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				// TODO Informar el total de vértices y el total de arcos que definen el grafo cargado
				break;

			case 1:

				view.printMessage("Ingrese El id del primer vertice (Ej. 901839): ");
				idVertice1 = sc.nextInt();
				view.printMessage("Ingrese El id del segundo vertice (Ej. 901839): ");
				idVertice2 = sc.nextInt();


				startTime = System.currentTimeMillis();
				caminoCostoMinimoA1(idVertice1, idVertice2);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/* 
				TODO Consola: Mostrar el camino a seguir con sus vértices (Id, Ubicación Geográfica),
				el costo mínimo (menor cantidad de infracciones), y la distancia estimada (en Km).

				TODO Google Maps: Mostrar el camino resultante en Google Maps 
				(incluyendo la ubicación de inicio y la ubicación de destino).
				 */
				break;

			case 2:
				view.printMessage("2A. Consultar los N v�rtices con mayor n�mero de infracciones. Ingrese el valor de N: ");
				int n = sc.nextInt();


				startTime = System.currentTimeMillis();
				mayorNumeroVerticesA2(n);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/* 
				TODO Consola: Mostrar la informacion de los n vertices 
				(su identificador, su ubicación (latitud, longitud), y el total de infracciones) 
				Mostra el número de componentes conectadas (subgrafos) y los  identificadores de sus vertices 

				TODO Google Maps: Marcar la localización de los vértices resultantes en un mapa en
				Google Maps usando un color 1. Destacar la componente conectada más grande (con
				más vértices) usando un color 2. 
				 */
				break;

			case 3:			

				view.printMessage("Ingrese El id del primer vertice (Ej. 901839): ");
				idVertice1 = sc.nextInt();
				view.printMessage("Ingrese El id del segundo vertice (Ej. 901839): ");
				idVertice2 = sc.nextInt();

				startTime = System.currentTimeMillis();
				caminoLongitudMinimoaB1(idVertice1, idVertice2);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");

				/*
				   TODO Consola: Mostrar  el camino a seguir, informando
					el total de vértices, sus vértices (Id, Ubicación Geográfica) y la distancia estimada (en Km).

				   TODO Google Maps: Mostre el camino resultante en Google Maps (incluyendo la
					ubicación de inicio y la ubicación de destino).
				 */
				break;

			case 4:		
				double lonMin;
				double lonMax;
				view.printMessage("Ingrese la longitud minima (Ej. -87,806): ");
				lonMin = sc.nextDouble();
				view.printMessage("Ingrese la longitud m�xima (Ej. -87,806): ");
				lonMax = sc.nextDouble();

				view.printMessage("Ingrese la latitud minima (Ej. 44,806): ");
				double latMin = sc.nextDouble();
				view.printMessage("Ingrese la latitud m�xima (Ej. 44,806): ");
				double latMax = sc.nextDouble();

				view.printMessage("Ingrese el n�mero de columnas");
				int columnas = sc.nextInt();
				view.printMessage("Ingrese el n�mero de filas");
				int filas = sc.nextInt();


				startTime = System.currentTimeMillis();
				definirCuadriculaB2(lonMin,lonMax,latMin,latMax,columnas,filas);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/*
				   TODO Consola: Mostrar el número de vértices en el grafo
					resultado de la aproximación. Mostar el identificador y la ubicación geográfica de cada
					uno de estos vértices. 

				   TODO Google Maps: Marcar las ubicaciones de los vértices resultantes de la
					aproximación de la cuadrícula en Google Maps.
				 */
				break;

			case 5:

				startTime = System.currentTimeMillis();
				arbolMSTKruskalC1();
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/*
				   TODO Consola: Mostrar los vértices (identificadores), los arcos incluidos (Id vértice inicial e Id vértice
					final), y el costo total (distancia en Km) del árbol.

				   TODO Google Maps: Mostrar el árbol generado resultante en Google Maps: sus vértices y sus arcos.
				 */

				break;

			case 6:

				startTime = System.currentTimeMillis();
				arbolMSTPrimC2();
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/*
				   TODO Consola: Mostrar los vértices (identificadores), los arcos incluidos (Id vértice inicial e Id vértice
				 	final), y el costo total (distancia en Km) del árbol.

				   TODO Google Maps: Mostrar el árbol generado resultante en Google Maps: sus vértices y sus arcos.
				 */
				break;

			case 7:

				startTime = System.currentTimeMillis();
				caminoCostoMinimoDijkstraC3();
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/*
				   TODO Consola: Mostrar de cada camino resultante: su secuencia de vértices (identificadores) y su costo (distancia en Km).

				   TODO Google Maps: Mostrar los caminos de costo mínimo en Google Maps: sus vértices
					y sus arcos. Destaque el camino más largo (en distancia) usando un color diferente
				 */
				break;

			case 8:
				view.printMessage("Ingrese El id del primer vertice (Ej. 901839): ");
				idVertice1 = sc.nextInt();
				view.printMessage("Ingrese El id del segundo vertice (Ej. 901839): ");
				idVertice2 = sc.nextInt();

				startTime = System.currentTimeMillis();
				caminoMasCortoC4(idVertice1, idVertice2);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/*
				   TODO Consola: Mostrar del camino resultante: su secuencia de vértices (identificadores), 
				   el total de infracciones y la distancia calculada (en Km).

				   TODO Google Maps: Mostrar  el camino resultante en Google Maps: sus vértices y sus arcos.	  */
				break;

			case 9: 	
				fin = true;
				sc.close();
				break;
			}
		}
	}
	private ArregloDinamico<Long> arregloDinamicoGrafo() 
	{
		Iterator<Long> it = grafo.getV().keys();
		ArregloDinamico<Long> vertices = new ArregloDinamico<>(3000);
		while(it.hasNext())
		{
			Long i = it.next();
			vertices.agregar(i);
		}
		return vertices;
	}

	private void cargarInfracciones(int numeroSemestre) {
		//		sem1 = new String[6];
		//		sem2 = new String[6];
		//
		//		int enero= 0;
		//		int f=0;
		//		int a =0;
		//		int m =0;
		//		int mayo =0;
		//		int j =0;
		//		int ju =0;
		//		int ag = 0;
		//		int s =0;
		//		int o =0;
		//		int n = 0;
		//		int d = 0;
		//		int totSem = 0;
		//		int contMes=0;
		//		for(int i = 0; i<6;i++){
		//			if(i == 0){
		//				sem1[i] = rutaEnero;
		//				sem2[i] = rutaJulio;
		//			}
		//			else if(i == 1){
		//				sem1[i] = rutaFebrero;
		//				sem2[i] = rutaAgosto;
		//			}
		//			else if(i == 2){
		//				sem1[i] = rutaMarzo;
		//				sem2[i] = rutaSeptiembre;
		//			}
		//			else if(i == 3){
		//				sem1[i] = rutaAbril;
		//				sem2[i] = rutaOctubre;
		//			}
		//			else if(i == 4){
		//				sem1[i] = rutaMayo;
		//				sem2[i] = rutaNoviembre;
		//			}
		//			else if(i == 5){
		//				sem1[i] = rutaJunio;
		//				sem2[i] = rutaDiciembre;
		//			}
		//		}
		//		try{
		//			if(numeroSemestre ==2){
		//				for(int i = 0;i<sem2.length;i++){
		//					contMes = 0;
		//					String mes = sem2[i];
		//					int latitud = -1;
		//					int longitud = -1;
		//
		//					if(i==10 || i==11 || i==12) {
		//						latitud = 19;
		//						longitud = 20; 
		//					}
		//					else {
		//						latitud = 18;
		//						longitud = 19;
		//					}
		//					CSVReader lector = new CSVReader(new FileReader(mes), ';');
		//					String[] linea = lector.readNext();
		//
		//					while ((linea = lector.readNext()) != null) 
		//					{
		//						String obID = linea[0];
		//						String lat = linea[latitud];
		//						String lon = linea[longitud];
		//						VOMovingViolations vo = new VOMovingViolations(obID,lat, lon);
		//						arreglo.agregar(vo);
		//						totSem++;
		//						contMes++;
		//						if(i == 0){
		//							ju=contMes;
		//
		//						}
		//						else if(i == 1){
		//							ag=contMes;
		//						}
		//						else if(i == 2){
		//							s=contMes;
		//
		//						}
		//						else if(i == 3){
		//							o=contMes;
		//
		//						}
		//						else if(i == 4){
		//							n=contMes;
		//
		//						}
		//						else if(i == 5){
		//							d=contMes;
		//
		//						}
		//					}
		//
		//					lector.close();
		//
		//				}
		//				System.out.println("Total de infracciones del semestre " + totSem);
		//				System.out.println("Infracciones de: ");
		//				System.out.println("Julio " + ju);
		//				System.out.println("Agosto " + ag);
		//				System.out.println("Septiembre " + s);
		//				System.out.println("Octubre" + o);
		//				System.out.println("Noviembre " + n);
		//				System.out.println("Diciembre " + d);
		//			}
		//
		//			else{
		//				for(int i = 0;i<sem1.length;i++){
		//					contMes = 0;
		//					String mes = sem1[i];
		//					int latitud = -1;
		//					int longitud = -1;
		//
		//					if(i==0) {
		//						latitud = 17;
		//						longitud = 18; 
		//					}
		//					else {
		//						latitud = 18;
		//						longitud = 19;
		//					}
		//					CSVReader lector = new CSVReader(new FileReader(mes), ';');
		//
		//					String[] linea = lector.readNext();
		//					while ((linea = lector.readNext()) != null) {
		//
		//						String obID = linea[1];
		//						String lat = linea[latitud];
		//						String lon = linea[longitud];
		//						VOMovingViolations vo = new VOMovingViolations(obID,lat, lon);
		//						arreglo.agregar(vo);	
		//						totSem++;
		//						contMes++;
		//						if(i == 0){
		//							enero=contMes;
		//						}
		//						else if(i == 1){
		//							f=contMes;
		//						}
		//						else if(i == 2){
		//							m=contMes;
		//
		//						}
		//						else if(i == 3){
		//							a=contMes;
		//
		//						}
		//						else if(i == 4){
		//							mayo=contMes;
		//
		//						}
		//						else if(i == 5){
		//							j=contMes;
		//
		//						}
		//					}
		//					lector.close();
		//				}
		//				System.out.println("Total de infracciones del semestre " + totSem);
		//				System.out.println("Infracciones de: ");
		//				System.out.println("Enero " + enero);
		//				System.out.println("Febrero " + f);
		//				System.out.println("Mazro " + m);
		//				System.out.println("Abril " + a);
		//				System.out.println("Mayo " + mayo);
		//				System.out.println("Junio " + j);
		//			}
		//		}
		//		catch (IOException e) {
		//
		//			e.printStackTrace();
		//		}
	}

	// TODO El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia


	/**
	 * Cargar el Grafo No Dirigido de la malla vial: Downtown o Ciudad Completa
	 * @param rutaArchivo 
	 */

	public void loadJSONVertices(String rutaArchivo) 
	{
		try {

			JSONParser parser = new JSONParser();
			JSONArray a = (JSONArray) parser.parse(new FileReader(rutaArchivo));
			for (Object o : a)
			{
				JSONObject actual = (JSONObject) o;
				String id=  (String) actual.get("id");
				Object lat = actual.get("lat");
				String lon=(String) actual.get("lon").toString();

				JSONArray ja= (JSONArray) actual.get("infractions");
				Iterator <String> it = ja.iterator();
				ArregloDinamico<String> ar = new ArregloDinamico<>(6);
				while (it.hasNext())
				{
					String i = it.next();
					ar.agregar(i);
				}
				JSONArray ja2= (JSONArray) actual.get("adj");
				Iterator <String> it2 = ja2.iterator();
				Bag<Long> ar2 = new Bag<>();
				while (it2.hasNext())
				{
					String i = it2.next();
					ar2.add(Long.parseLong(i));
				}
				grafo.addVertex(Long.parseLong(id), lat+"|"+lon,ar,ar2);
				grafoR2y9.addVertex(Long.parseLong(id), lat+"|"+lon,ar,ar2);
				heap.agregar(new Vertex<Long, String, Double>(Long.parseLong(id), lat+"|"+lon, ar,ar2));
				arregloIdsGrafo.agregar(Long.parseLong(id));
			}
			System.out.println("Vertices cargados "+grafo.V());
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	private void loadJSONArcos(String rutaArchivo)
	{
		try{
			JSONParser parser = new JSONParser();
			JSONArray a = (JSONArray) parser.parse(new FileReader(rutaArchivo));
			for (Object o : a)
			{
				JSONObject actual = (JSONObject) o;

				JSONObject e = (JSONObject) actual.get("arco");

				Double peso=  (Double) e.get("peso");
				Long inicio = (Long) e.get("inicio");
				Long fin = (Long) e.get("fin");

				grafo.addEdge(inicio, fin, peso);
				grafoR2y9.addEdge(inicio, fin, (double)grafoR2y9.getV().get(inicio).getCantidadInfracciones());
			}	

			System.out.println("Arcos cargados con JSON " + grafo.E());
		}
		catch(Exception e )
		{e.printStackTrace();}

	}

	public Comparable<VOMovingViolations> [ ] generarMuestraVertices( int n )
	{
		muestraVertices = new Comparable[ n ];
		// TODO Llenar la muestra aleatoria con los datos guardados en la estructura de datos
		Iterator<Long> it = grafo.getV().keys();
		int pos=0;
		while(it.hasNext())
		{
			Long a = it.next();
			Vertex<Long, String, Double> v = grafo.getV().get(a);
			muestraVertices[pos] = v;
			pos++;
		}
		return muestraVertices;
	}
	public Comparable<VOMovingViolations> [ ] generarMuestra( int n )
	{
		muestra = new Comparable[ n ];
		// TODO Llenar la muestra aleatoria con los datos guardados en la estructura de datos
		ArregloDinamico<VOMovingViolations> e = arreglo;

		int pos=0;
		while(pos<n)
		{
			muestra[pos] = e.darElem(pos);
			pos++;
		}
		return muestra;
	}


	// TODO El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 1A: Encontrar el camino de costo m�nimo para un viaje entre dos ubicaciones geogr�ficas.
	 * @param idVertice2 
	 * @param idVertice1 
	 */
	public void caminoCostoMinimoA1(int idVertice1, int idVertice2)
	{
		
	}

	// TODO El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 2A: Determinar los n v�rtices con mayor n�mero de infracciones. Adicionalmente identificar las
	 * componentes conectadas (subgrafos) que se definan �nicamente entre estos n v�rtices
	 * @param  int n: numero de vertices con mayor numero de infracciones  
	 */
	public void mayorNumeroVerticesA2(int n) {
		// TODO Auto-generated method stub
		ArregloDinamico<Vertex<Long, String, Double>> mayores= new ArregloDinamico<>(20);
		for(int i=0;i<n;i++)
		{
			Vertex<Long, String, Double> v= heap.delMax();
			mayores.agregar(v);
			System.out.println(v.darInfoVertice());
		}
		Graph<Long,String,Double> grafo1= new Graph<>();
		for (int i = 0; i < mayores.darTamano(); i++)
		{
			Vertex<Long, String, Double> v = mayores.darElem(i);
			grafo1.addVertex(v.getId(), v.getLatitud()+"|"+v.getLongitud(), v.getInfracciones(),v.getIds());
		}
		LinearProbing<Long, Vertex<Long,String,Double>> lin= grafo1.getV();
		LinearProbing<Long, Vertex<Long,String,Double>> linGrande= grafo.getV();
		Iterator <Long> it = linGrande.keys();
		while(it.hasNext())
		{
			Long i= it.next();
			if(lin.get(i)!=null)
			{
				Vertex<Long, String, Double> vertice=linGrande.get(i);
				Bag<Long> adjs= vertice.getIds();
				for(Long a: adjs)
				{
					if(lin.get(a)!=null)
					{
						Vertex<Long, String, Double> verticeFin=lin.get(a);
						double peso= haversine(Double.parseDouble(vertice.getLatitud()), Double.parseDouble(vertice.getLongitud()), Double.parseDouble(verticeFin.getLatitud()), Double.parseDouble(verticeFin.getLongitud()));
						grafo1.addEdge(vertice.getId(), verticeFin.getId(), peso);
					}
				}
			}
		}
		LinearProbing<Long, Boolean> marked = new LinearProbing<Long, Boolean>(grafo1.V());
		Iterator<Long> itt = lin.keys();
		while(itt.hasNext())
		{
			Long i = itt.next();
			marked.put(i, false);
		}
		Iterator<Long> ite= marked.keys();
		int componentes = 0;
		MaxHeapCP<Bag<Long>> h= new MaxHeapCP<Bag<Long>>();
		while(ite.hasNext())
		{
			Long a = ite.next();
			if(marked.get(a)==false)
			{
				componentes++;
				DFS<Long,String,Double> d= new DFS<Long, String, Double>(grafo1, a, marked);
				marked= d.darMarcados();
				h.agregar(d.darBagDeVertices());
			}
		}
		Bag<Long> max = h.delMax();
		//en el bag estan los vertices del componente conexo mayor
		System.out.println("Numeros de componentes: "+componentes);
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

	// TODO El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 1B: Encontrar el camino m�s corto para un viaje entre dos ubicaciones geogr�ficas 
	 * @param idVertice2 
	 * @param idVertice1 
	 */
	public void caminoLongitudMinimoaB1(long idVertice1, long idVertice2) {
		BFS b = new BFS<>(grafo, idVertice1);
		Iterable r = b.pathTo(idVertice2);
		for (Object object : r) {
			System.out.println(object);
		}
	}

	// TODO El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 2B:  Definir una cuadricula regular de N columnas por M filas. que incluya las longitudes y latitudes dadas
	 * @param  lonMin: Longitud minima presente dentro de la cuadricula
	 * @param  lonMax: Longitud maxima presente dentro de la cuadricula
	 * @param  latMin: Latitud minima presente dentro de la cuadricula
	 * @param  latMax: Latitud maxima presente dentro de la cuadricula
	 * @param  columnas: Numero de columnas de la cuadricula
	 * @param  filas: Numero de filas de la cuadricula
	 */
	public void definirCuadriculaB2(double lonMin, double lonMax, double latMin, double latMax, int columnas,
			int filas) {
		// TODO Auto-generated method stub
	}

	// TODO El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 1C:  Calcular un �rbol de expansi�n m�nima (MST) con criterio distancia, utilizando el algoritmo de Kruskal.
	 */
	public void arbolMSTKruskalC1() {
		// TODO Auto-generated method stub

	}

	// TODO El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 2C: Calcular un �rbol de expansi�n m�nima (MST) con criterio distancia, utilizando el algoritmo de Prim. (REQ 2C)
	 */
	public void arbolMSTPrimC2() {
		// TODO Auto-generated method stub

	}

	// TODO El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 3C: Calcular los caminos de costo m�nimo con criterio distancia que conecten los v�rtices resultado
	 * de la aproximaci�n de las ubicaciones de la cuadricula N x M encontrados en el punto 5.
	 */
	public void caminoCostoMinimoDijkstraC3() {
		// TODO Auto-generated method stub

	}

	// TODO El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 4C:Encontrar el camino m�s corto para un viaje entre dos ubicaciones geogr�ficas escogidas aleatoriamente al interior del grafo.
	 * @param idVertice2 
	 * @param idVertice1 
	 */
	public void caminoMasCortoC4(int idVertice1, int idVertice2) {
		// TODO Auto-generated method stub

	}

}
