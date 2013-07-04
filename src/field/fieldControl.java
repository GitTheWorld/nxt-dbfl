package field;

public class fieldControl {
	
    public static int[][] theField;
    public static int unknown;
    public static int objects;


    public static void initField()
    {
    theField = new int[fieldConfig.field_Sx][fieldConfig.field_Sy];
    unknown = fieldConfig.field_Sx*fieldConfig.field_Sy;
    objects=0;
    }
	
	public static void setField(int id, int x, int y, int my_id) throws Exception
	{
	if(id < 0 || id > 2 || !inField(x, y)){return;}
	
	if(id>0 && theField[x][y]==0){unknown--;}
	if(id==0 && theField[x][y]!=0){unknown++;}
	if(id!=2 && theField[x][y]==2){objects--;}
	if(id==2 && theField[x][y]!=2){objects++;}
    System.out.println ("main: there are "+unknown+ " unknown fields"); 
	theField[x][y]=id;
	}
	
	public static boolean isFreeFromAll(int id, int x, int y)
	{
		for(int i=0;i<fieldMaster.bricks.size();i++)
		{
	    if(i!=id){
		if(!fieldMaster.bricks.get(i).is_free(x,y)){return false;}
		}}			
		return true;
	}
	
	public static boolean inField(int x, int y)
	{
    return !(x<0 || x>fieldConfig.field_Sx-1 || y<0 || y>fieldConfig.field_Sy-1);
	}
}
