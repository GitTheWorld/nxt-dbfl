package bridgeFieldControl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import nxtPyhtonBridge.Tools;

public class GuiInit {
	public static void askMe(boolean reInit) throws Exception {
		String sfield = null;
		String splayer = null;

		String path = BrickGame.path + "/init.txt";
		System.out.println("main: try to get: " + path);
		BufferedReader in;
		String zeile;
		try {
			in = new BufferedReader(new FileReader(path));
			int i = 0;
			while ((zeile = in.readLine()) != null) {
				try {
					if (i == 1) {
						splayer = zeile;
						System.out.println("main: splayer: " + splayer);
						i++;
					}
					if (i == 0) {
						sfield = zeile;
						System.out.println("main: sfield: " + sfield);
						i++;
					}
				} catch (NumberFormatException ex) {
				}
			}
			in.close();
		} catch (IOException e) {
			System.out.println("main: fail");
		}
		System.out.println("main: success");

		String field;
		String field2[];
		int[] result;
		while (true) {
			if (sfield != null) {
				field = sfield;
				sfield = null;
			} else {
				field = Tools.displayInput("Syntax: 'size_x;size_y;ifWin'",
						"Feld größe und Siegbedingung");
			}
			field2 = field.split(";");
			result = Tools.convertToInt(field2);
			if (result.length == 3) {
				break;
			}

		}

		FieldGame.init_sub(result[0], result[1]);
		Status.ifWin = result[2];
		BrickGame.error = new String[2];
		BrickGame.error[0] = "fatal";
		BrickGame.error[1] = "writeproblem";

		String members;
		String members2[];
		String[] names;
		int[][] coords;
		while (true) {
			if (splayer != null) {
				members = splayer;
				splayer = null;
			} else {
				members = Tools.displayInput(
						"Syntax: 'name;pos_x;pos_y;direction;...'",
						"Spieler Eingabe");
			}

			members2 = members.split(";");

			if (members2.length != 0 && members2.length % 4 == 0) {

				names = new String[members2.length / 4];
				for (int i = 0; i < members2.length / 4; i++) {
					names[i] = members2[i * 4];
					members2[i * 4] = "0";
				}
				result = Tools.convertToInt(members2);
				if (result.length != 0) {
					break;
				}
			}
		}

		coords = new int[members2.length / 4][3];

		for (int i = 0; i < members2.length / 4; i++) {
			coords[i][0] = result[i * 4 + 1];
			coords[i][1] = result[i * 4 + 2];
			coords[i][2] = result[i * 4 + 3];
		}

		if (reInit) {
			for (int i = 0; i < BrickGame.bricks.size(); i++) {
				BrickGame.bricks.get(i).pos_x = coords[i][0];
				BrickGame.bricks.get(i).pos_y = coords[i][1];
				BrickGame.bricks.get(i).direction = coords[i][2];
				BrickGame.bricks.get(i).startup();
			}
		} else {
			BrickGame.init(names, coords, false);
		}

		System.out.println("main: init finish");
	}
}
