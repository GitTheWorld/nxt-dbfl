package field;

public class fieldAI {


	public static int getMove(int ID) {
		fieldAlgorithmus.getPaths(fieldMaster.bricks.get(ID).pos_x,
				fieldMaster.bricks.get(ID).pos_y,ID);

		int fieldID = getBest(ID);

		System.out.println(fieldMaster.bricks.get(ID).name
				+ ": new destination: "
				+ fieldAlgorithmus.theUnknown.get(fieldID)[0] + "|"
				+ fieldAlgorithmus.theUnknown.get(fieldID)[1]);
		return fieldAlgorithmus.getWay(
				fieldAlgorithmus.theUnknown.get(fieldID)[0],
				fieldAlgorithmus.theUnknown.get(fieldID)[1]);
	}

	public static int getBest(int ID) {

		int result = fieldAlgorithmus.theUnknown.size() - 1;
		double points = 0;

		for (int i = 0; i < fieldAlgorithmus.theUnknown.size() - 1; i++) {
			double newPoints = fieldMaster.bricks.get(ID).start_points;
			newPoints = newPoints + points_for_distance(i,ID);
			newPoints = newPoints + points_for_enemy(i,ID);
			newPoints = newPoints + punkte_blatt_sammmlung(i,ID);

			if (newPoints > points) {
				result = i;
				points = newPoints;
			}
		}

		return result;
	}

	public static double points_for_distance(int fieldID, int ID) {
		return fieldAlgorithmus.theListSave[fieldAlgorithmus.theUnknown
				.get(fieldID)[0]][fieldAlgorithmus.theUnknown.get(fieldID)[1]][0]
				* fieldMaster.bricks.get(ID).distance_from_me;
	}
	public static double points_for_enemy(int fieldID, int ID) {
		
		double points=0;
		
		for(int i=0;i<fieldMaster.bricks.size();i++)
		{
		if(i!=ID){
		points = points + (fieldMaster.bricks.get(ID).distance_from_enemy/(fieldMaster.bricks.size()-1))*get_manhatten_distance(fieldAlgorithmus.theUnknown.get(fieldID)[0],fieldAlgorithmus.theUnknown.get(fieldID)[1],fieldMaster.bricks.get(i).pos_x,fieldMaster.bricks.get(i).pos_y,ID);		
		}
		}
	
	return points;
	}
	
    private static double punkte_blatt_sammmlung(int fieldID, int ID)
    {
        double punkte = 0;
        for(int i=0;i<fieldAlgorithmus.theUnknown.size();i++)
        {
            if(fieldID!=i){
                punkte=punkte+fieldMaster.bricks.get(ID).distance_to_other_base*Math.pow(fieldMaster.bricks.get(ID).distance_to_other_multi,get_manhatten_distance(fieldAlgorithmus.theUnknown.get(fieldID)[0],fieldAlgorithmus.theUnknown.get(fieldID)[1],fieldAlgorithmus.theUnknown.get(i)[0],fieldAlgorithmus.theUnknown.get(i)[1],ID)); 
            } 
        }
        return punkte;
    }

	public static double get_real_distance(int fieldID) {
		return fieldAlgorithmus.theListSave[fieldAlgorithmus.theUnknown
				.get(fieldID)[0]][fieldAlgorithmus.theUnknown.get(fieldID)[1]][0];
	}

	public static double get_manhatten_distance(int x0, int y0, int x1, int y1, int ID) {
		return Math.abs(x1 - x0) * fieldMaster.bricks.get(ID).one_x + Math.abs(y1 - y0) * fieldMaster.bricks.get(ID).one_y;
	}

}
