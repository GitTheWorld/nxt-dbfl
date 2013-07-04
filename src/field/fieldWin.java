package field;

import java.io.FileNotFoundException;

public class fieldWin {
	public static boolean win=false;
	
	public static int ifWin=0;
	
	public static boolean is_win() throws FileNotFoundException
	{
	if(ifWin==0){return false;}
	boolean newWin = false;
		
	if(win){newWin=true;}else
	if(fieldControl.unknown==0 && ifWin==-1){newWin=true;}else
	if(fieldControl.objects==ifWin){newWin=true;}
		
    if(newWin){
	if(!win){	
	fieldGui.stopButton.setEnabled(false);
	fieldGui.pause=false;
	fieldWriter.write();
	win=true;
	}
	return true;
    }
	
	return false;
	}
	
	
}
