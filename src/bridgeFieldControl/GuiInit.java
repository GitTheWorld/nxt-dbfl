package bridgeFieldControl;

import java.util.ArrayList;

import nxtPyhtonBridge.GuiUtils;

public class GuiInit {
	public static void askMe(String sfield, String splayer) {
		String field;
		String field2[];
		while (true) {
			if (sfield != null) {
				field = sfield;
				sfield = null;
			} else {
				field = GuiUtils.displayInput(
						"Syntax: 'size_x;size_y;players;ifWin'",
						"Feld,Spieler,Sieg");
			}
			field2 = field.split(";");

			if (field2.length == 4
					&& field2[0].matches("[0-9]+")
					&& field2[1].matches("[0-9]+")
					&& field2[2].matches("[0-9]+")
					&& field2[3].substring(0, 1).matches("[-0-9]+")
					&& (field2[3].length() < 2 || field2[3].substring(1)
							.matches("[0-9]+"))) {
				break;
			}
		}
		Config.field_Sx = Integer.parseInt(field2[0]);
		Config.field_Sy = Integer.parseInt(field2[1]);
		Config.other = Integer.parseInt(field2[2]);
		Win.ifWin = Integer.parseInt(field2[3]);

		Control.initField();
		Master.bricks = new ArrayList<OneBrick>();

		if (Config.other != 0) {
			String members;
			String members2[];
			while (true) {
				if (splayer != null) {
					members = splayer;
					splayer = null;
				} else {
					members = GuiUtils.displayInput(
							"Syntax: 'name;pos_x;pos_y;direction;...'",
							"Spieler Eingabe");
				}
				members2 = members.split(";");

				boolean right = true;

				if (members2.length == Config.other * 4) {

					for (int i = 0; i < Config.other; i++) {
						if (!(members2[i * 4] != ""
								&& members2[i * 4 + 1].matches("[0-9]+")
								&& members2[i * 4 + 2].matches("[0-9]+") && members2[i * 4 + 3]
								.matches("[0-9]+"))) {
							right = false;
							break;
						}
					}
				} else {
					right = false;
				}

				if (right) {
					String[] error = new String[2];
					error[0] = "fatal";
					error[1] = "writeproblem";

					for (int i = 0; i < Config.other; i++) {
						OneBrick brick;
						try {
							brick = new OneBrick(members2[i * 4], error, i,
									Integer.parseInt(members2[i * 4 + 1]),
									Integer.parseInt(members2[i * 4 + 2]),
									Integer.parseInt(members2[i * 4 + 3]));
							Master.bricks.add(brick);

						} catch (Exception e) {
							System.out
									.println("main: something is wrong by connecting!!!!");
							e.printStackTrace();
							GuiUtils.displayOutput("Konnte nicht mit: "
									+ members2[i * 4] + " verbinden!!!",
									"Error");
							System.exit(1);
						}
					}
					System.out
							.println("main: Init abgeschlossen, Spieler sind verbunden.");
					break;
				}
			}
		}

	}
}
