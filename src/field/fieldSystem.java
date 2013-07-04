package field;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.pythonbridge;


	public class fieldSystem extends pythonbridge {

		// Ai Konfiguration
		public double one_x = 1;
		public double one_y = 1.2;
		public double start_points = 0;
		public double distance_from_me = -100;
		public double distance_from_enemy = 50;
		public double distance_to_other_base = 10;
		public double distance_to_other_multi = 0.9;
		// AI Konfiguration END
		
	    int my_id;
		public int pos_x;
		public int pos_y;
		public int direction;
		public Image my_image;
		
		public int status_moves=0;
		public int status_foundBlock=0;
		
		
		private boolean needStart;
		private boolean ends;
	    
	    // Basis Funktionen //
	    
		public fieldSystem(String thename,String[] error,int mynewid,int x, int y, int dir) throws Exception{
		super(thename,error);
		pos_x=x;
		pos_y=y;
		direction=dir;
	    needStart=true;
	    ends=false;
	    my_id=mynewid;
	    
	    File f = new File(pythonbridge.path+"/custom/"+name+".png");
	    if(f.exists()) {my_image=ImageIO.read(f);}
	    
	    
	    try {
		    System.out.println (name + ": load custom AI configuration");
			BufferedReader in = new BufferedReader(new FileReader(pythonbridge.path+"/custom/"+name+".txt"));
			String zeile = null;
			int i=0;
			while ((zeile = in.readLine()) != null) {
			
				try{
				if(i==0){one_x=Double.parseDouble(zeile);}
				if(i==1){one_y=Double.parseDouble(zeile);}
				if(i==2){start_points=Double.parseDouble(zeile);}
				if(i==3){distance_from_me=Double.parseDouble(zeile);}
				if(i==4){distance_from_enemy=Double.parseDouble(zeile);}
				if(i==5){distance_to_other_base=Double.parseDouble(zeile);}
				if(i==6){distance_to_other_multi=Double.parseDouble(zeile);}
			    System.out.println (name + ": "+i+"-"+Double.parseDouble(zeile));
				i++;
				}
			    catch (NumberFormatException ex) 
			    { 
			    } 
			}
			in.close();
		} catch (IOException e) {
		    System.out.println (name + ": load backup configuration because error");

		    System.out.println (name + ": load custom AI configuration");
			BufferedReader in = new BufferedReader(new FileReader(pythonbridge.path+"/AI.txt"));
			String zeile = null;
			int i=0;
			while ((zeile = in.readLine()) != null) {
				
				try{
				if(i==0){one_x=Double.parseDouble(zeile);}
				if(i==1){one_y=Double.parseDouble(zeile);}
				if(i==2){start_points=Double.parseDouble(zeile);}
				if(i==3){distance_from_me=Double.parseDouble(zeile);}
				if(i==4){distance_from_enemy=Double.parseDouble(zeile);}
				if(i==5){distance_to_other_base=Double.parseDouble(zeile);}
				if(i==6){distance_to_other_multi=Double.parseDouble(zeile);}
				i++;
			    System.out.println (name + ": "+i+"-"+Double.parseDouble(zeile));
				}
			    catch (NumberFormatException ex) 
			    { 
			    } 
			}
			in.close();
		}
	    
	    
		}
		
		private void updatePosition()
		{
		if(direction==1){pos_y--;}
		if(direction==2){pos_x--;}
		if(direction==3){pos_y++;}
		if(direction==4){pos_x++;}
	    System.out.println (name + ": new position (up) "+pos_x+" "+pos_y);
		}

		private void downdatePosition()
		{
		if(direction==1){pos_y++;}
		if(direction==2){pos_x++;}
		if(direction==3){pos_y--;}
		if(direction==4){pos_x--;}
	    System.out.println (name + ": new position (down) "+pos_x+" "+pos_y);
		}
		
		// Startup Sequenz zur Kontrollübernahme über Bluetooth
		public void startup() throws Exception
		{
		    System.out.println (name + ": try to start software on NXT");
			Thread.sleep(300);
			stop();
			Thread.sleep(1000);
			start();
			Thread.sleep(300);
			downdatePosition();	
			System.out.println (name + ": the nxt is now ready to start");

		}
		
		private void receive_id() throws Exception
		{
	    status_moves++;
	    int id = readMailbox(fieldConfig.bluetooth_mailboxSystem,false,-1);
	    if(id<1 || id>2){return;}
	    if(id==2){
	    updatePosition();
	    fieldControl.setField(2, pos_x, pos_y, my_id);
	    System.out.println (name + ": receive field: "+id+" "+pos_x+" "+pos_y);
	    downdatePosition();	
	    direction=0;
	    status_foundBlock++;
	    fieldGui.update();
	    }
	    else{
		updatePosition();
		fieldControl.setField(1, pos_x, pos_y, my_id);
	    direction=0;
	    System.out.println (name + ": receive field: "+id+" "+pos_x+" "+pos_y);
	    System.out.println (name + ": new position: "+pos_x+" "+pos_y);
	    fieldGui.update();
	    }
		}
		
	    public void action() throws Exception
	    {
	    if(ends){return;}
	    
	    	if(needStart)
	    	{
		     updatePosition();
	    		if(fieldControl.isFreeFromAll(my_id,pos_x,pos_y))
	    		{
	    	 writeMailbox(fieldConfig.bluetooth_mailboxSystem,direction,-1);
	    	 needStart=false;
	    		}
		     downdatePosition();
	    	return;
	    	}	 
	    		
	    	if(direction==0)
	    	{
	    	if(fieldWin.is_win()){
	    	writeMailbox(fieldConfig.bluetooth_mailboxSystem,0,-1);
	    	ends=true;
	    	return;
	    	}
	    	int newdirection = fieldAI.getMove(my_id);
	    	if(newdirection<1 || newdirection>4){return;}
	    	direction=newdirection;
		    writeMailbox(fieldConfig.bluetooth_mailboxSystem,newdirection,-1);
		    fieldGui.update();
	    	}
	    	else
	    	{receive_id();}	    	
	    	
	    }
	    
	    public boolean is_free(int x, int y)
	    {
	    if(needStart){return true;}
	    if(pos_x==x && pos_y==y){return false;}
	    updatePosition();
	    if(pos_x==x && pos_y==y){downdatePosition();return false;}
	    downdatePosition();
	    return true;
	    }
		
	}

