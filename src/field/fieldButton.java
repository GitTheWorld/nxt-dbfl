package field;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class fieldButton implements ActionListener {

  public void actionPerformed(ActionEvent e) {
  //  if (e.getActionCommand().equals("Button1")) {

	  if(fieldGui.pause){fieldGui.pause=false;fieldGui.stopButton.setText("Pausieren");}else{fieldGui.pause=true;fieldGui.stopButton.setText("Fortsetzen");}
	  
	//   }
  }
}