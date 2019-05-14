package model.vo;

public class VOMovingViolations implements Comparable<VOMovingViolations>{

	private String id;
	
	private String lat;
	
	private String lon;
	
	public VOMovingViolations(String pid, String plat, String plon) {
		id = pid;
		lat = plat.replace(",",".");
		lon = plon.replace(",",".");
	}
	
	public String darID() {
		return id;
	}
	public String darLat() {
		return lat;
	}
	public String darLon() {
		return lon;
	}
	public String toString() {
		
		return "Datos infraccion: ID: " + id + " Latitud: " + lat + " Longitud " + lon;
	}

	@Override
	public int compareTo(VOMovingViolations o) {
		// TODO Auto-generated method stub
		return 0;
	}
 }
