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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JsonArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import model.data_structures.ArregloDinamico;
import model.data_structures.Bag;
import model.data_structures.Cola;
import model.data_structures.Comparaciones;
import model.data_structures.ComparacionesInfracciones;
import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.LinearProbing;
import model.data_structures.MaxColaPrioridad;
import model.data_structures.Vertex;
import model.vo.Sort;
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

	//	private boolean empezo;
	//
	//	private boolean highWay;
	//
	//	private boolean repetido;
	//
	//	private ArregloDinamico<Long> nodos ;

	private Graph<Long, String, Double> grafo;

	private Graph<Long, String, Double> grafo1;

	private Comparable[] muestraVertices;
	private Comparable[] muestra;

	public static final double R = 6372.8;

	private static final String ruta = "./data/Central-WashingtonDC-OpenStreetMap.xml";

	private ArregloDinamico<Vertex<Long, String, Double>> a1;
	// 745747=   67795*11+2
	private ArregloDinamico<Vertex<Long, String, Double>> a2;
	private ArregloDinamico<Vertex<Long, String, Double>> a3;
	private ArregloDinamico<Vertex<Long, String, Double>> a4;
	private ArregloDinamico<Vertex<Long, String, Double>> a5;
	private ArregloDinamico<Vertex<Long, String, Double>> a6;
	private ArregloDinamico<Vertex<Long, String, Double>> a7;
	private ArregloDinamico<Vertex<Long, String, Double>> a8;
	private ArregloDinamico<Vertex<Long, String, Double>> a9;
	private ArregloDinamico<Vertex<Long, String, Double>> a10;
	private ArregloDinamico<Vertex<Long, String, Double>> a11;


	public Controller() throws Exception {

		arreglo = new ArregloDinamico<VOMovingViolations>(100);
		view = new MovingViolationsManagerView();
		grafo = new Graph<Long, String, Double>();		
		grafo1 = new Graph<Long, String, Double>();		
		a1=new ArregloDinamico<>(67795);
		// 745747=   67795*11+2
		a2=new ArregloDinamico<>(67795);
		a3=new ArregloDinamico<>(67795);
		a4=new ArregloDinamico<>(67795);
		a5=new ArregloDinamico<>(67795);
		a6=new ArregloDinamico<>(67795);
		a7=new ArregloDinamico<>(67795);
		a8=new ArregloDinamico<>(67795);
		a9=new ArregloDinamico<>(67795);
		a10=new ArregloDinamico<>(67795);
		a11=new ArregloDinamico<>(67797);
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
					//					SAXParserFactory spf = SAXParserFactory.newInstance();
					//					spf.setNamespaceAware(true);
					//
					//					SAXParser saxParser = spf.newSAXParser();
					//					XMLReader xmlReader = saxParser.getXMLReader();
					//					xmlReader.setContentHandler(this);
					//					xmlReader.parse(ruta);
					//					cargarVerticesJson();
					//					cargarArcosJson();
					//					System.out.println("Empezo a juntar");
					//					juntarVerticesInfracciones();
					//					System.out.println("Termino");
					cargarVerticesJson();
					cargarArcosJson();
				}
				catch(Exception e) {
					e.getMessage();
				}
				break;
			case 1:
				escribirVerticesJson();
				escribirArcosJson();
				break;
			case 2:
				System.out.println("Inserte cu�l semestre desea cargar");
				int param = sc.nextInt();
				cargarInfracciones(param);
				break;
			case 3:
				juntarVerticesInfracciones();
				break;
			case 4:
				crearArreglos();
				break;
			case 5:	
				fin=true;
				sc.close();
				break;
			}
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

					while ((linea = lector.readNext()) != null) 
					{
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

				}
				System.out.println("Total de infracciones del semestre " + totSem);
				System.out.println("Infracciones de: ");
				System.out.println("Julio " + ju);
				System.out.println("Agosto " + ag);
				System.out.println("Septiembre " + s);
				System.out.println("Octubre" + o);
				System.out.println("Noviembre " + n);
				System.out.println("Diciembre " + d);
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
					lector.close();
				}
				System.out.println("Total de infracciones del semestre " + totSem);
				System.out.println("Infracciones de: ");
				System.out.println("Enero " + enero);
				System.out.println("Febrero " + f);
				System.out.println("Mazro " + m);
				System.out.println("Abril " + a);
				System.out.println("Mayo " + mayo);
				System.out.println("Junio " + j);
			}
		}
		catch (IOException e) {

			e.printStackTrace();
		}
	}

	private void crearArreglos()
	{
		//Ordenar vertices 
		System.out.println("Cantidad de vertices cargados "+grafo.V());
		Comparable [] copia = generarMuestraVertices(grafo.V());
		Sort.ordenarShellSort(copia, Comparaciones.COORD.comparador, true);
		System.out.println("Ordenado por latitud y luego por longitud");
		int q1=0;
		while(q1<grafo.V())
		{
			Vertex<Long, String, Double> v = (Vertex<Long, String, Double>) copia[q1];
			if(q1<67795)
				a1.agregar(v);			
			else if(q1<67795*2)
				a2.agregar(v);
			else if(q1<67795*3)
				a3.agregar(v);
			else if(q1<67795*4)
				a4.agregar(v);
			else if(q1<67795*5)
				a5.agregar(v);
			else if(q1<67795*6)
				a6.agregar(v);
			else if(q1<67795*7)
				a7.agregar(v);
			else if(q1<67795*8)
				a8.agregar(v);
			else if(q1<67795*9)
				a9.agregar(v);
			else if(q1<67795*10)
				a10.agregar(v);
			else
				a11.agregar(v);
			q1++;
		}
		System.out.println(a1.darTamano());
		System.out.println(a2.darTamano());
		System.out.println(a3.darTamano());
		System.out.println(a4.darTamano());
		System.out.println(a5.darTamano());
		System.out.println(a6.darTamano());
		System.out.println(a7.darTamano());
		System.out.println(a8.darTamano());
		System.out.println(a9.darTamano());
		System.out.println(a10.darTamano());
		System.out.println(a11.darTamano());

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
	private void juntarVerticesInfracciones() 
	{
		//		LinearProbing<Long, Vertex<Long, String, Double>> vertices = grafo.getV();
		//		Iterator<Long> it = vertices.keys();
		//		VOMovingViolations infraccion;
		//		for (int i = 0; i < arreglo.darTamano(); i++) 
		//		{
		//			infraccion = arreglo.darElem(i);
		//			String la= infraccion.darLat().replace(",", ".");
		//			String lo= infraccion.darLat().replace(",", ".");
		//			Double lat= Double.parseDouble(la);
		//			Double lon= Double.parseDouble(lo);
		//			MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
		//			while(it.hasNext())
		//			{
		//				Long num= it.next();
		//				Vertex<Long, String, Double> vertice = vertices.get(num);
		//				Double lat2= Double.parseDouble(vertice.getLatitud());
		//				Double lon2= Double.parseDouble(vertice.getLongitud());
		//				cola.agregar(haversine(lat, lon, lat2, lon2), vertice.getId());
		//			}
		//			Long corto= cola.delMax();
		//			vertices.get(corto).aumentarCantidadInfracciones();
		//		}
		//	}
		Comparable [] copia= generarMuestra(arreglo.darTamano());
		System.out.println(copia);
		Sort.ordenarMergeSort(copia, ComparacionesInfracciones.COORD.comparador, true);
		//Ordenadas
		System.out.println(a1.darElem(0).darInfoVertice());
		System.out.println(a2.darElem(0).darInfoVertice());
		System.out.println(a3.darElem(0).darInfoVertice());
		System.out.println(a4.darElem(0).darInfoVertice());
		System.out.println(a5.darElem(0).darInfoVertice());
		System.out.println(a6.darElem(0).darInfoVertice());
		System.out.println(a7.darElem(0).darInfoVertice());
		System.out.println(a8.darElem(0).darInfoVertice());
		System.out.println(a9.darElem(0).darInfoVertice());
		System.out.println(a10.darElem(0).darInfoVertice());
		System.out.println(a11.darElem(0).darInfoVertice());
		int c1=0;
		int c2=0;
		int c3=0;
		int c4=0;
		int c5=0;
		int c6=0;
		int c7=0;
		int c8=0;
		int c9=0;
		int c10=0;
		int c11=0;
		LinearProbing<Long, Vertex<Long, String, Double>> vertices = grafo.getV();
		for (int i = 0; i < copia.length; i++) {
			VOMovingViolations v = (VOMovingViolations) copia[i];

			if(Double.parseDouble(v.darLat())<38.7998200 && Double.parseDouble(v.darLon())<-76.8456310)
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a1.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a1.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a1.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();		
				c1++;
			}
			else if(Double.parseDouble(v.darLat())<38.8244800 && Double.parseDouble(v.darLon())<-77.0125709)
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a2.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a2.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a2.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();	
				c2++;
			}
			else if(Double.parseDouble(v.darLat())<38.8475036 && Double.parseDouble(v.darLon())<-77.0673638)
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a3.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a3.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a3.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();	
				c3++;
			}
			else if(Double.parseDouble(v.darLat())<38.8669498 && Double.parseDouble(v.darLon())<-77.2538007)
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a4.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a4.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a4.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();	
				c4++;	
			}
			else if(Double.parseDouble(v.darLat())<38.8856110 && Double.parseDouble(v.darLon())<-76.8457860)
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a5.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a5.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a5.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();	
				c5++;
			}
			else if(Double.parseDouble(v.darLat())<38.9038135 && Double.parseDouble(v.darLon())<-77.1407222)
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a6.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a6.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a6.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();	
				c6++;
			}
			else if(Double.parseDouble(v.darLat())<38.9247310 && Double.parseDouble(v.darLon())<-77.1922380)
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a7.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a7.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a7.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();	
				c7++;
			}
			else if(Double.parseDouble(v.darLat())<38.9428580 && Double.parseDouble(v.darLon())<-77.1844630)
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a8.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a8.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a8.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();	
				c8++;
			}
			else if(Double.parseDouble(v.darLat())<38.9672169 && Double.parseDouble(v.darLon())<-76.9183474)
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a9.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a9.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a9.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();	
				c9++;
			}
			else if(Double.parseDouble(v.darLat())<38.9923831 && Double.parseDouble(v.darLon())<-77.0160329)
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a10.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a10.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a10.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();	
				c10++;
			}
			else
			{
				MaxColaPrioridad<Double, Long> cola = new MaxColaPrioridad<>();
				for(int j=0;j<a11.darTamano();j++)				
				{
					Double lat2= Double.parseDouble(a11.darElem(j).getLatitud());
					Double lon2= Double.parseDouble(a11.darElem(j).getLongitud());
					cola.agregar(haversine(Double.parseDouble(v.darLat()), Double.parseDouble(v.darLon()), lat2, lon2), a1.darElem(j).getId());
				}
				Long corto= cola.delMax();
				vertices.get(corto).aumentarCantidadInfracciones();	
				c11++;
			}
		}
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4);
		System.out.println(c5);
		System.out.println(c6);
		System.out.println(c7);
		System.out.println(c8);
		System.out.println(c9);
		System.out.println(c10);
		System.out.println(c11);

	}
	//	public void startDocument() throws SAXException {
	//		empezo = false;
	//		highWay = false;
	//		repetido = false;
	//	}

	//	@Override
	//	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
	//
	//		if(qName.equals("node")){
	//			//			String info =  atts.getValue(1) + "|" + atts.getValue(2);
	//			//			System.out.println(info.toString());
	//			//			System.out.println(atts.getLength());
	//			grafo.addVertex(Long.parseLong(atts.getValue(0)),  atts.getValue(1) + "|" + atts.getValue(2),0);
	//			//			for(int n = 0; n < 3; n++) {
	//			//				System.out.println(atts.getQName(n)+ ": " + atts.getValue(n));						
	//			//			}
	//		}
	//
	//		else if(qName.equals("way")){
	//			if(empezo == false){
	//
	//				empezo = true;
	//				nodos = null;
	//				nodos =  new ArregloDinamico<>(10);
	//				highWay = false;
	//			}
	//
	//			//			System.out.println("Empez� nodo way de tamano " + atts.getLength());
	//
	//		}
	//		else if(qName.equals("nd") && empezo){
	//			//			System.out.println("Empez� nodo de nd");
	//			for(int n = 0; n < atts.getLength(); n++) {
	//				//				System.out.println(atts.getQName(n)+ ": " + atts.getValue(n));
	//				nodos.agregar(Long.parseLong(atts.getValue(n)));
	//			}
	//		}
	//		else if(qName.equals("tag") && empezo ){
	//			if(atts.getValue(0).equals("highway")) {
	//				highWay = true;
	//			}
	//		}
	//	}
	//
	//	@Override
	//	public void endElement(String uri, String localName, String qName) throws SAXException {
	//		if(qName.equals("way")){	//Pregunta si termino de leer etiqueta way	
	//
	//			if(empezo){		//Pregunta si habia empezado a leer una etiqueta way, si es as� empezo es false ahora
	//
	//				empezo = false;		
	//				if(highWay == false) {		//Pregunta si uno de los tags del way ten�a highway como valor. Si es falso elimina la lista de nodos nd
	//					nodos = null;
	//					nodos = new ArregloDinamico<>(10);
	//				}
	//				else {		//Si hab�a way, procesa los nodos del way.
	//					for(int i = 0; i<nodos.darTamano() - 1 ;i++) {
	//						double hav =0.0;
	//						try {
	//							hav = haversine(Double.parseDouble(grafo.getV().get(nodos.darElem(i)).getLatitud()), Double.parseDouble(grafo.getV().get(nodos.darElem(i)).getLongitud()), Double.parseDouble(grafo.getV().get(nodos.darElem(i+1)).getLatitud()), Double.parseDouble(grafo.getV().get(nodos.darElem(i+1)).getLongitud()));
	//							Long idNodoOrigen = nodos.darElem(i);
	//							Long idNodoDestino = nodos.darElem(i+1);
	//
	//							Iterator<Long> id = grafo.getV().get(idNodoOrigen).getAdjsId().iterator();
	//							Long actual = null;
	//							while(!repetido && id.hasNext()) {
	//								actual = id.next();
	//								if(actual.equals(idNodoDestino)) {
	//									repetido = true;
	//								}
	//							}
	//
	//							if(repetido == false) {
	//								grafo.addEdge(idNodoOrigen, idNodoDestino, hav);
	//							}
	//							else if(repetido == true) {
	//								repetido = false;
	//							}
	//						} catch (Exception e) {
	//							// TODO Auto-generated catch block
	//							e.printStackTrace();
	//						}
	//					}
	//				}
	//			}
	//		}
	//	}
	public  double haversine(double lat1, double lon1, double lat2, double lon2) {

		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}
	//	public void endDocument() throws SAXException {
	//		System.out.println("\n Termino");
	//		LinearProbing<Long, Vertex<Long, String, Double>> lin = grafo.getV();
	//		Iterator<Long> it = lin.keys();
	//		Vertex<Long, String, Double> v = null;
	//		while(it.hasNext()) {
	//			Long idVertex = it.next();
	//			v = lin.get(idVertex);
	//			if(v.getAdjsId().size() == 0) {
	//				v.setId(0L);
	//				v = null;
	//				grafo.reducirV();
	//
	//			}
	//		}
	//		System.out.println("Numero de vertices: " + grafo.V());
	//		System.out.println("Numero de arcos: " + grafo.E());
	//	}
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
				//afuera
				afuera = new JSONObject();
				afuera.put("vertice", adentro);

				verticesPrint.add(afuera);
				c++;
			}

		}
		System.out.println("Vertices escritos"+c);
		try (FileWriter file = new FileWriter("./data/vertices1.json")) {

			file.write(verticesPrint.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void escribirVerticesJson2() {
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
				adentro.put("numIn", infracciones);
				//afuera
				afuera = new JSONObject();
				afuera.put("vertice", adentro);

				verticesPrint.add(afuera);
				c++;
			}

		}
		System.out.println("vertices cargados"+c);
		try (FileWriter file = new FileWriter("./data/verticesInfracciones.json")) {

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
				grafo.addVertex(id, lat+"|"+lon, 0);
				//				System.out.println(id);
				//				System.out.println(lat);
				//				System.out.println(lon);
			}
			System.out.println(grafo.V());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	private void cargarVerticesJson2() {
		try {
			JSONParser parser = new JSONParser();
			JSONArray a = (JSONArray) parser.parse(new FileReader("./data/verticesInfracciones.json"));
			for (Object o : a)
			{
				JSONObject actual = (JSONObject) o;
				JSONObject e = (JSONObject) actual.get("vertice");
				Long id=  (Long) e.get("id");
				String lat = (String) e.get("lat");
				String lon=(String) e.get("lon");
				String infra= (String)e.get("numIn");
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
		int c=0;
		int c1=0;
		while(it.hasNext()) 
		{
			Long a = it.next();
			Vertex<Long, String, Double> v= lin.get(a);
			ArregloDinamico<Edge<Long, String, Double>> b = v.getEdges();
			for (int i = 0; i < b.darTamano(); i++) 
			{
				if(!edges.contains(b.darElem(i)))
				{
					edges.add(b.darElem(i));
					c++;
				}
			}
		}
		JSONObject adentro=null;
		JSONObject afuera =null;
		for (int i = 0; i < edges.size(); i++) 
		{
			Edge<Long, String, Double> e=edges.get(i);
			Vertex<Long, String,  Double> in=e.getStartVertex();
			Vertex<Long, String,  Double> fi=e.getEndVertex();
			adentro=new JSONObject();
			adentro.put("inicio",in.getId());
			adentro.put("fin", fi.getId());
			adentro.put("peso", haversine(Double.parseDouble(in.getLatitud()), Double.parseDouble(in.getLongitud()), Double.parseDouble(fi.getLatitud()), Double.parseDouble(fi.getLongitud())));
			afuera = new JSONObject();
			afuera.put("arco", adentro);
			arcosPrint.add(afuera);
			c1++;
		}
		try (FileWriter file = new FileWriter("./data/arcos1.json")) {

			file.write(arcosPrint.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Arcos escritos "+c1);
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

				grafo.addEdge(inicio, fin, peso);

			}	

			System.out.println("Arcos cargados con JSON " + grafo.E());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
