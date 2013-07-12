package bridgeFieldControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TheButton implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		
	      String action = e.getActionCommand();
	      
	      
	      if (action.equals("Pausieren")) {
				Status.pause = true;
				Gui.stopButton.setText("Fortsetzen");
	      }
	      if (action.equals("Fortsetzen")) {
				Status.pause = false;
				Gui.stopButton.setText("Pausieren");
	      }
	      if (action.equals("Reset"))
	      {
	    Master.needRestart=true;
	      }
	      
	}

}
