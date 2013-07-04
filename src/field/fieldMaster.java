package field;
import java.util.ArrayList;
import control.guiElements;
import control.pythonbridge;

public class fieldMaster {

	public static String pathtoserver;
    public static ArrayList<fieldSystem> bricks;
    
	public static void main(String[] args) {
		if(args.length==0){return;}
		
	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	          System.out.println("main: ends");
	        }
	      }));
		
		
		pythonbridge.path=args[0];
		
		if(args.length==2){
		fieldGuiInit.askMe(args[1],null);
		}else if(args.length==3){
		fieldGuiInit.askMe(args[1],args[2]);
		}else
		{
		fieldGuiInit.askMe(null,null);
		}
		
		try {
			for(int i=0;i<fieldConfig.other;i++)
			{
				bricks.get(i).startup();
			}
			
			fieldGui.init();

		while(true){
			for(int i=0;i<bricks.size();i++)
			{
			if(fieldGui.finish){fieldStop.stopMore();}
			if(fieldGui.pause){while(fieldGui.pause){Thread.sleep(200);}}
			bricks.get(i).action();
			}
		}

		} catch (Exception e) {
	    System.out.println ("main: something is wrong!!!!"); 
	    e.printStackTrace();
	    guiElements.displayOutput("Etwas stimmt nicht, Programm muss beendet werden!", "Error");
	    System.exit(1);
	    }
		}
	
	

	}
