package bridgeFieldControl;

public class AI {

	public static int getMove(int ID) {
		Algorithmus.getPaths(Master.bricks.get(ID).pos_x,
				Master.bricks.get(ID).pos_y, ID);

		int fieldID = getBest(ID);

		System.out.println(Master.bricks.get(ID).name + ": new destination: "
				+ Algorithmus.theUnknown.get(fieldID)[0] + "|"
				+ Algorithmus.theUnknown.get(fieldID)[1]);
		return Algorithmus.getWay(Algorithmus.theUnknown.get(fieldID)[0],
				Algorithmus.theUnknown.get(fieldID)[1]);
	}

	public static int getBest(int ID) {

		int result = Algorithmus.theUnknown.size() - 1;
		double points = 0;

		for (int i = 0; i < Algorithmus.theUnknown.size() - 1; i++) {
			double newPoints = Master.bricks.get(ID).start_points;
			newPoints = newPoints + points_for_distance(i, ID);
			newPoints = newPoints + points_for_enemy(i, ID);
			newPoints = newPoints + punkte_blatt_sammmlung(i, ID);

			if (newPoints > points) {
				result = i;
				points = newPoints;
			}
		}

		return result;
	}

	public static double points_for_distance(int fieldID, int ID) {
		return Algorithmus.theListSave[Algorithmus.theUnknown.get(fieldID)[0]][Algorithmus.theUnknown
				.get(fieldID)[1]][0] * Master.bricks.get(ID).distance_from_me;
	}

	public static double points_for_enemy(int fieldID, int ID) {

		double points = 0;

		for (int i = 0; i < Master.bricks.size(); i++) {
			if (i != ID) {
				points = points
						+ (Master.bricks.get(ID).distance_from_enemy / (Master.bricks
								.size() - 1))
						* get_manhatten_distance(
								Algorithmus.theUnknown.get(fieldID)[0],
								Algorithmus.theUnknown.get(fieldID)[1],
								Master.bricks.get(i).pos_x,
								Master.bricks.get(i).pos_y, ID);
			}
		}

		return points;
	}

	private static double punkte_blatt_sammmlung(int fieldID, int ID) {
		double punkte = 0;
		for (int i = 0; i < Algorithmus.theUnknown.size(); i++) {
			if (fieldID != i) {
				punkte = punkte
						+ Master.bricks.get(ID).distance_to_other_base
						* Math.pow(
								Master.bricks.get(ID).distance_to_other_multi,
								get_manhatten_distance(
										Algorithmus.theUnknown.get(fieldID)[0],
										Algorithmus.theUnknown.get(fieldID)[1],
										Algorithmus.theUnknown.get(i)[0],
										Algorithmus.theUnknown.get(i)[1], ID));
			}
		}
		return punkte;
	}

	public static double get_real_distance(int fieldID) {
		return Algorithmus.theListSave[Algorithmus.theUnknown.get(fieldID)[0]][Algorithmus.theUnknown
				.get(fieldID)[1]][0];
	}

	public static double get_manhatten_distance(int x0, int y0, int x1, int y1,
			int ID) {
		return Math.abs(x1 - x0) * Master.bricks.get(ID).one_x
				+ Math.abs(y1 - y0) * Master.bricks.get(ID).one_y;
	}

}
