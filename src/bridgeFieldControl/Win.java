package bridgeFieldControl;

import java.io.FileNotFoundException;

public class Win {
	public static boolean win = false;

	public static int ifWin = 0;

	public static boolean is_win() throws FileNotFoundException {
		if (ifWin == 0) {
			return false;
		}
		boolean newWin = false;

		if (win) {
			newWin = true;
		} else if (Control.unknown == 0 && ifWin == -1) {
			newWin = true;
		} else if (Control.objects == ifWin) {
			newWin = true;
		}

		if (newWin) {
			if (!win) {
				Gui.stopButton.setEnabled(false);
				Gui.pause = false;
				Writer.write();
				win = true;
			}
			return true;
		}

		return false;
	}

}
