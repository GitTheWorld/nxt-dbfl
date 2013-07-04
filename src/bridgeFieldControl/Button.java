package bridgeFieldControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Button implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		// if (e.getActionCommand().equals("Button1")) {

		if (Gui.pause) {
			Gui.pause = false;
			Gui.stopButton.setText("Pausieren");
		} else {
			Gui.pause = true;
			Gui.stopButton.setText("Fortsetzen");
		}

		// }
	}
}