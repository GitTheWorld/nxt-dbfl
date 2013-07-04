package field;

public class fieldStop {
	
public static void stopMore()
{
try {
System.out.println("main: start to stop");

fieldWriter.write();
	
while(fieldGui.pro.size()!=0){
fieldGui.pro.get(0).join();
fieldGui.pro.remove(0);
}

while(fieldMaster.bricks.size()!=0){
System.out.println(fieldMaster.bricks.get(0).name+": disconnect");
fieldMaster.bricks.get(0).shutdown();
fieldMaster.bricks.remove(0);
}
System.exit(0);
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	System.exit(0);
}}
}
