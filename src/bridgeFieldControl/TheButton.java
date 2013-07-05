package bridgeFieldControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TheButton implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		if (Status.pause) {
			Status.pause = false;
			Gui.stopButton.setText("Pausieren");
		} else {
			Status.pause = true;
			Gui.stopButton.setText("Fortsetzen");
		}
	}

}
