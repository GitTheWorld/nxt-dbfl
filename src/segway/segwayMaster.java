package segway;


import control.guiElements;
import control.pythonbridge;

public class segwayMaster {
	public static String pathtoserver;
	public static segwayControl brick;
	
	public static void main(String[] args) {
		if(args.length==0){return;}
		pythonbridge.path=args[0];

		String[] error = new String[1];
		error[0]="fatal";
		

		try {
	    brick = new segwayControl(guiElements.displayInput("Wie ist der Name des Segways?", "Segway Name"),error);
	    new segwayGui();
	    
		} catch (Exception e) {
	    System.out.println ("main: something is wrong!!!!"); 
	    e.printStackTrace();
	    guiElements.displayOutput("Etwas stimmt nicht, Programm muss beendet werden!", "Error");
	    System.exit(1);
		}

	}
}
