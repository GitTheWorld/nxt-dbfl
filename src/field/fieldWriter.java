package field;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

import control.pythonbridge;

public class fieldWriter implements Runnable{

	  private BufferedImage file;
	  private String path;
	  private static boolean written=false;

	  public fieldWriter(BufferedImage file,String path){
	    this.file = file;
	    this.path = path;
	  }
	  
	  public static void write() throws FileNotFoundException
	  {
			if(fieldGui.pro.size()>0){
				 if(!written){
				String out3 =  new Integer(fieldGui.count).toString();
				if(fieldGui.count < 10){out3="0"+out3;}	  
				if(fieldGui.count < 100){out3="0"+out3;}
				if(fieldGui.count < 1000){out3="0"+out3;}

				PrintStream out = new PrintStream(new FileOutputStream(pythonbridge.path+"/plays/"+fieldGui.folder+"/"+out3+".txt"));
				out.print(fieldGui.getText());
				out.close();
				 }
				 written=true;
				}
	  }

	  public void run(){
		  try {
			ImageIO.write(file, "PNG", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
		  
	  }
	}