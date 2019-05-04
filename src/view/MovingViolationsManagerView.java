package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import controller.Controller;
import model.data_structures.ArregloDinamico;
import model.data_structures.IQueue;

public class MovingViolationsManagerView 
{
	/**
	 * Constante con el nÃºmero maximo de datos maximo que se deben imprimir en consola
	 */
	public static final int N = 20;

	public void printMenu() {
		System.out.println("---------ISIS 1206 - Estructuras de datos----------");
		System.out.println("---------------------Taller 8----------------------");
		System.out.println("0. Cargar grafo");
		System.out.println("1. Escribir JSON");
		System.out.println("2. Cargar JSON");
		System.out.println("3. Dibujar grafo desde HTML");
		System.out.println("4. Salir");
		System.out.println("Digite el nï¿½mero de opciï¿½n para ejecutar la tarea, luego presione enter: (Ej., 1):");

	}
}
