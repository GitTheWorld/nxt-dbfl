package bridgeFieldControl;

import java.util.ArrayList;

public class OneField {
	private int x;
	private int y;

	public OneField(int x, int y, int direction, double distance,
			double[][][] theList) {
		this.x = x;
		this.y = y;
		theList[this.x][this.y][0] = distance;
		theList[this.x][this.y][1] = direction;
	}

	public ArrayList<OneField> createNew(double[][][] theList) {
		System.out.println("fieldOneField: createNew " + x + "|" + y);

		theList[x][y][2] = 2;
		ArrayList<OneField> newFields = new ArrayList<OneField>();

		if (Control.theField[x][y] == 0) {
			int[] theInt = new int[2];
			theInt[0] = x;
			theInt[1] = y;
			Algorithmus.theUnknown.add(theInt);
			return newFields;
		}

		if (Algorithmus.isFree(-1, x + 1, y)
				&& (theList[x + 1][y][2] == 0 || theList[x + 1][y][0] > theList[x][y][0]
						+ Master.bricks.get(Algorithmus.ID).one_x)) {
			if (theList[x + 1][y][2] == 2 || theList[x + 1][y][2] == 0) {
				newFields.add(new OneField(this.x + 1, this.y, 2,
						theList[x][y][0]
								+ Master.bricks.get(Algorithmus.ID).one_x,
						theList));
				theList[x + 1][y][2] = 1;
			} else {
				theList[x + 1][y][0] = theList[x][y][0]
						+ Master.bricks.get(Algorithmus.ID).one_x;
				theList[x + 1][y][2] = 2;
			}
		}

		if (Algorithmus.isFree(-1, x - 1, y)
				&& (theList[x - 1][y][2] == 0 || theList[x - 1][y][0] > theList[x][y][0]
						+ Master.bricks.get(Algorithmus.ID).one_x)) {
			if (theList[x - 1][y][2] == 2 || theList[x - 1][y][2] == 0) {
				newFields.add(new OneField(this.x - 1, this.y, 4,
						theList[x][y][0]
								+ Master.bricks.get(Algorithmus.ID).one_x,
						theList));
				theList[x - 1][y][2] = 1;
			} else {
				theList[x - 1][y][0] = theList[x][y][0]
						+ Master.bricks.get(Algorithmus.ID).one_x;
				theList[x - 1][y][1] = 4;
			}
		}

		if (Algorithmus.isFree(-1, x, y + 1)
				&& (theList[x][y + 1][2] == 0 || theList[x][y + 1][0] > theList[x][y][0]
						+ Master.bricks.get(Algorithmus.ID).one_y)) {
			if (theList[x][y + 1][2] == 2 || theList[x][y + 1][2] == 0) {
				newFields.add(new OneField(this.x, this.y + 1, 1,
						theList[x][y][0]
								+ Master.bricks.get(Algorithmus.ID).one_y,
						theList));
				theList[x][y + 1][2] = 1;
			} else {
				theList[x][y + 1][0] = theList[x][y][0]
						+ Master.bricks.get(Algorithmus.ID).one_y;
				theList[x][y + 1][1] = 1;
			}
		}

		if (Algorithmus.isFree(-1, x, y - 1)
				&& (theList[x][y - 1][2] == 0 || theList[x][y - 1][0] > theList[x][y][0]
						+ Master.bricks.get(Algorithmus.ID).one_y)) {
			if (theList[x][y - 1][2] == 2 || theList[x][y - 1][2] == 0) {
				newFields.add(new OneField(this.x, this.y - 1, 3,
						theList[x][y][0]
								+ Master.bricks.get(Algorithmus.ID).one_y,
						theList));
				theList[x][y - 1][2] = 1;
			} else {
				theList[x][y - 1][0] = theList[x][y][0]
						+ Master.bricks.get(Algorithmus.ID).one_y;
				theList[x][y - 1][1] = 3;
			}
		}

		return newFields;
	}
}
