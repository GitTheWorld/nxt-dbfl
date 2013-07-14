package nxtPyhtonBridge;

import java.util.ArrayList;

public class Field {
	// Field configuration
	public static int size_x; // Größe der X Achse
	public static int size_y; // Größe der Y Achse
	public static double one_x; // Kosten für eine Bewegung auf der X Achse
	public static double one_y; // Kosten für eine Bewegung auf der Y Achse


	public static int[][] theField;

	public static ArrayList<int[]> listFields;
	public static SubField[][] theWayField;

	public static void init(int new_size_x, int new_size_y) {
		size_x = new_size_x;
		size_y = new_size_y;
		theField = new int[size_x][size_y];
	}
	
	// Sollte überschrieben werden damit manche Felder unbegehbar sind
	public static boolean isFree(int x, int y) {return true;}


	// Mithilfe eines fertig berechneten Feldes die Richtung finden in die
	// gegangen werden sollte um Ziel zu erreichen
	public static int getWayTo(int x, int y, SubField[][] theField) {
		int direction = 0;
		while (theField[x][y].distance != 0) {
			direction = theField[x][y].direction;
			int[] way = getWayWithDirection(additionDirection(direction, 2));
			x = x + way[0];
			y = y + way[1];
		}
		return direction;
	}

	public static int additionDirection(int direction, int plus) {
		direction = direction + plus;
		if (direction > 4) {
			direction = direction - 4;
		}
		return direction;
	}

	public static int[] getWayWithDirection(int direction) {
		int[] way = new int[2];
		switch (direction) {
		case 1:
			way[0] = 0;
			way[1] = -1;
			break;
		case 2:
			way[0] = -1;
			way[1] = 0;
			break;
		case 3:
			way[0] = 0;
			way[1] = 1;
			break;
		case 4:
			way[0] = 1;
			way[1] = 0;
			break;
		default:
			way[0] = 0;
			way[1] = 0;
		}
		return way;
	}

	// Sind die Koordinaten innerhalb des Feldes
	public static boolean inField(int x, int y) {
		return !(x < 0 || x > size_x - 1 || y < 0 || y > size_y - 1);
	}

	public static double getManhattanDistance(int x0, int y0, int x1, int y1) {
		return Math.abs(x1 - x0) * one_x + Math.abs(y1 - y0) * one_y;
	}

	public static void calcWays(int x, int y, int[] listID, int[] stopOnID) {

		listFields = new ArrayList<int[]>();
		ArrayList<int[]> newFields = new ArrayList<int[]>();
		theWayField = new SubField[size_x][size_y];
		theWayField[x][y] = new SubField(x, y, 0, 0);

		int[] element = new int[2];
		element[0] = x;
		element[1] = y;

		newFields.add(element);

		while (newFields.size() > 0) {
			checkField(theWayField[newFields.get(0)[0]][newFields.get(0)[1]],listID, stopOnID);
			newFields.addAll(createNew(newFields.get(0)[0],newFields.get(0)[1],theWayField));
			newFields.remove(0);
		}
	}


	public static void checkField(SubField field, int[] listID,
			int[] stopOnID) {

		for (int i = 0; i < listID.length; i++) {
			if (listID[i] == theField[field.x][field.y]) {
				int[] element = new int[2];
				element[0] = field.x;
				element[1] = field.y;
				listFields.add(element);
			}
		}
		
		for (int i = 0; i < stopOnID.length; i++) {
			if (stopOnID[i] == theField[field.x][field.y]) {
				field.endPoint = true;
			}
		}
	}

	public static ArrayList<int[]> createNew(int x, int y,SubField[][] theList) {

		if (theList[x][y].endPoint) {
			return new ArrayList<int[]>();
		}
		theList[x][y].inList = false;
		
		
		ArrayList<int[]> newFields = new ArrayList<int[]>();

		int new_x = 0;
		int new_y = 0;
		int new_direction = 0;
		double new_distance = 0;

		for (int i = 1; i <= 4; i++) {

			new_direction = i;
			int[] way = getWayWithDirection(i);

			new_x = way[0] + x;
			new_y = way[1] + y;

			switch (i) {
			case 1:
				new_distance = theList[x][y].distance + one_y;
				break;
			case 2:
				new_distance = theList[x][y].distance + one_x;
				break;
			case 3:
				new_distance = theList[x][y].distance + one_y;
				break;
			case 4:
				new_distance = theList[x][y].distance + one_x;
				break;
			}
			
			if ((inField(new_x, new_y)
					&& isFree(new_x, new_y))
					&& (theList[new_x][new_y] == null || theList[new_x][new_y].distance > new_distance)) {
				
				
				if (theList[new_x][new_y] == null) {
					theList[new_x][new_y] = new SubField(new_x, new_y,new_direction, new_distance);
					int[] element = new int[2];
					element[0] = new_x;
					element[1] = new_y;
					newFields.add(element);
				
				} else {
					theList[new_x][new_y].distance = new_distance;
					theList[new_x][new_y].direction = new_direction;
					if (!theList[new_x][new_y].inList) {
						int[] element = new int[2];
						element[0] = new_x;
						element[1] = new_y;
						newFields.add(element);
						theList[new_x][new_y].inList=true;
					}
				}

			}
		}
		return newFields;
	}
}
