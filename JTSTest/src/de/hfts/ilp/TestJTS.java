/**
 * Beispiel zur Kollisionsberechnung mit einem Hindernis.
 * 
 */
package de.hfts.ilp;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import com.vividsolutions.jts.io.WKTReader;

public class TestJTS 
{
	/**
	 * Hauptprogramm. 
	 * Dieses berechnet mögliche Wege von einem Startpunkt s zu den Ecken eines Hindernisses g1.
	 * Auf der Konsole wir ausgegeben, welche Ecken auf direktem Weg erreicht werden können
	 * und welche Ecken nicht erreicht werden können.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception
    {
		/**
		 * GeometryFactory ist eine JTS Klasse, welche geometrische Objekte mit dem
		 * angegebenen Genauigkeitsmodell produziert.
		 */
		GeometryFactory  geoFab    = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING));
		
		/**
		 * WTKReader ist eine JTS Klasse, mit der geometrische Objekte gelesen werden können.
		 */
		WKTReader 		 wtkReader = new WKTReader(geoFab);
		
		/*
		 * Als erstes wird die Geometrie des Hindernisses von einen String eingelesen 
		 * und als Text wieder ausgegeben.
		 */
		LinearRing obstacleShell = (LinearRing) wtkReader.read("LINEARRING (5 0, 5 10, 15 10, 15 0, 5 0)");
		Polygon    obstacle1 = new Polygon(obstacleShell, null, geoFab);
		System.out.println(obstacle1.toString() + "\n");
		
		/*
		 * Dann wird der Startpunkt eingelesen.
		 */
		Point s = (Point) wtkReader.read("POINT(4 5)");
		
		/*
		 * Es wird geprüft, ob der Startpunkt innerhalb des Hindernisses liegt.
		 * Wenn ja, wird das Programm beendet.
		 */
		if (obstacle1.covers(s))
		{
			System.out.println("Startpunkt liegt innerhalb des Hindernis. ");
			return;
		}
		
		/*
		 * Jetzt werden die Wege von dem Startpunkt zu den einzelnen 
		 * Ecken des Hindernises berechnet.
		 */
		Coordinate [] 			coordinates = obstacle1.getCoordinates(); // Koordinaten des Hinderniss
		LineString    			path;									  // Weg von s  zu einer der Ecken des Hinderniss
		CoordinateArraySequence seq;									  // Hilfsvariable zur Konstruktion des Weges
		
		/*
		 * Über alle Eckkoordinaten der Ecken des Hinderniss 
		 */
		for (int i = 0; i < coordinates.length - 1; ++i)
		{
			/*
			 * Der Pfad wird durch eine Instanz der Klasse LineString dargestellt.
			 * Er besteht aus zwei Punkten: 1. Startpunkt, 2. Eckpunkt des Hundernis.
			 */
			seq 	= new CoordinateArraySequence(new Coordinate[]{s.getCoordinate(), coordinates[i]});
			path  	= new LineString(seq, geoFab);
			
			/*
			 * Zur Kontrolle geben wir das Wegstück (Pfad) aus.
			 */
			System.out.print("Path " + i + ": " + path.toString() + ": ");
			
			/*
			 * Jetzt wird geprüft, ob der Pfad das Hinderniss schneidet oder nicht.
			 */
			if (obstacle1.touches(path))
			{
				// Der Pfad berührt das Hindernis und schneidet dieses nicht 
				System.out.println("touches ");
			}
			else
			{
				// Andernfalls führt der Pfad durch das Hindernis.
				System.out.println("intersects ");
			}
			
			/*
			 * Bei Bedarf kann hier noch die Beziehungsmatrix der beiden Objekte (Hindernis und Weg)
			 * angezeigt werden
			 */
			printDE9IM(obstacle1.relate(path));
		}
		
		/**
		 * Die Ausgabe ist wie folgt:
		 * LINEARRING (5 0, 5 10, 15 10, 15 0, 5 0)
		 *
		 *	Path 0: LINESTRING (0 5, 5 0): touches 
		 *	Path 1: LINESTRING (0 5, 5 10): touches 
		 *	Path 2: LINESTRING (0 5, 15 10): intersects 
		 *	Path 3: LINESTRING (0 5, 15 0): intersects
		 */
    }

	
	/**
	 * Hilfsmethode zur Anzeige der DE-9IM Matrix
	 * @param ism
	 */
	public static void printDE9IM(IntersectionMatrix ism)
	{
		System.out.print(  ism.get(0, 0)+ "; ");
		System.out.print(  ism.get(0, 1)+ "; ");
		System.out.println(ism.get(0, 2)+ "; ");
		
		System.out.print(  ism.get(1, 0)+ "; ");
		System.out.print(  ism.get(1, 1)+ "; ");
		System.out.println(ism.get(1, 2)+ "; ");
		
		System.out.print(  ism.get(2, 0)+ "; ");
		System.out.print(  ism.get(2, 1)+ "; ");
		System.out.println(ism.get(2, 2)+ "; ");
	}
}
