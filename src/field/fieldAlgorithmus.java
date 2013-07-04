package field;

import java.util.ArrayList;

public class fieldAlgorithmus {



public static ArrayList<int[]> theUnknown;
public static double[][][] theListSave;
public static int ID;

public static boolean isFree(int id,int x, int y)
{
if(!fieldControl.inField(x,y) || !fieldControl.isFreeFromAll(id, x,y) || fieldControl.theField[x][y]==2){return false;}
return true;
}

public static boolean isSureFree(int id,int x, int y)
{
if(!fieldControl.inField(x,y) || !fieldControl.isFreeFromAll(id, x,y) || !(fieldControl.theField[x][y]==1)){return false;}
return true;
}

public static void getPaths(int x, int y,int myID)
{
double[][][] theList = new double[fieldConfig.field_Sx][fieldConfig.field_Sx][3];

theUnknown = new ArrayList<int[]>();
ID=myID;

fieldOneField start = new fieldOneField(x,y,0,0,theList);
ArrayList<fieldOneField> newFields = new ArrayList<fieldOneField>();
newFields.add(start);

while(newFields.size()>0)
{
	newFields.addAll(newFields.get(0).createNew(theList));
	newFields.remove(0);
}

// last element in the list is the actual position
	int[] theInt = new int[2];
	theInt[0]=x;
	theInt[1]=y;
	fieldAlgorithmus.theUnknown.add(theInt);      	


theListSave=theList;
}

public static int getWay(int x, int y)
{
int direction = 0;

while(theListSave[x][y][1]!=0)
{
direction=(int) theListSave[x][y][1];
if(direction==1)
{
y=y-1;
}else if(direction==2)
{
x=x-1;
}else if(direction==3)
{
y=y+1;
}else if(direction==4)
{
x=x+1;
}
}

if(direction!=0){
direction=direction+2;
if(direction>4){direction=direction-4;}}

return direction;
}




}
