package control;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class pythonbridge {

		// Zeige den Debug aus dieser Klasse
	    public static boolean debug=false;

	
	    public static String path;
		public String name;
		public Process process;
		public BufferedReader reader;
		public BufferedWriter writer;
		public String[] error;
		
	    // Basis Funktionen //
		public pythonbridge(String thename,String[] allError) throws Exception{
	    error=allError;
		name=thename;
		String pathToExe = "control.py";
		if(debug){System.out.println (name + "(d): start with "+pathToExe);}
		process = new ProcessBuilder("python", path+"/control.py").start();
		reader = new BufferedReader (new InputStreamReader(process.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		
		if(!readStream().equals("ready")){throw new Exception();};
		writeStream(name);
		if(!readStream().equals("connecting")){throw new Exception();};
		if(!readStream().equals("success")){throw new Exception();};
		if(debug){System.out.println (name + "(d): ready to control :)");}
		}
		
		public void shutdown() throws Exception
		{
		stop();
		process.destroy();
		}
		
		
		// Lese aus dem Stream
		protected String readStream() throws Exception
		{
		    while(!reader.ready())
		    {Thread.sleep(50);}
			String out = reader.readLine();
	        
	        for(int i=0;i<error.length;i++)
	        {
	        if(out.equals(error[i]))
	        {
	        throw new Exception();
	        }
	        }
	        return out;
		}
		
		// Schreibe aus dem Stream
		protected void writeStream(String what) throws IOException
		{
			writer.append(what);
			writer.newLine();
			writer.flush();
		}
				
		// Start unsere Software
		protected void start() throws Exception
		{
	    writeStream(String.valueOf(21));
	    if(debug){System.out.println (name + "(d): start_nxc");}
        if(!readStream().equals("success")){throw new Exception();};		
        }
		
		// Stoppe unsere Software
		protected void stop() throws Exception
		{
	    writeStream(String.valueOf(20));
	    if(debug){System.out.println (name + "(d): stop_nxc");}   
        if(!readStream().equals("success")){throw new Exception();};		
        }
		
		// Schreibe etwas in die Mailbox und warte falls nötig auf Antwort
		protected void writeMailbox(int which, int what, int answer) throws Exception
		{
		if(which<0 || which>9){return;}
	    writeStream(String.valueOf(which));
	    writeStream(String.valueOf(what+1));
        if(debug){System.out.println (name + "(d): send "+what+" on "+which);}
        if(!readStream().equals("success"))
        {throw new Exception();}
        if(answer>=0 && answer<=9){readMailbox(answer,true,-1);}
		}
		
		// Lese etwas aus der Mailbox und warte auf eine Antwort eventuell
		protected int readMailbox(int which,boolean wait, int answer) throws Exception
		{
	    if(which<0 || which>9){return -1;}
		String out;
		if(wait){if(debug){System.out.println (name + "(d): wait for message on "+which); }}
		do{
	    writeStream(String.valueOf(which+10));
	    out = readStream();
		}while(wait && out.equals("nomessage"));
		
		if(out.equals("nomessage")){out="0";}
		int output = Integer.parseInt(out.replaceAll("\\D+",""))-1;
		if(debug){System.out.println (name + "(d): read "+output+" on "+which);}
        if(answer>=0 && answer<=9 && output!=-1){writeMailbox(answer,0,-1);}
        return output;
		}

	}


