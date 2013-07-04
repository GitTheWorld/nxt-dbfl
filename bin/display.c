// Hier wird alles was aus dem Display Informationen ausgibt geregelt.

void display_bluetoothConfiguration()
{
ClearScreen();
TextOut(0,LCD_LINE3,"Warte auf");
TextOut(0,LCD_LINE4,"meine");
TextOut(0,LCD_LINE5,"Ausrichtung!!");
}

void display_running()
{
  ClearScreen();
  string name = BrickDataName(); 
  TextOut(0, LCD_LINE1, "Name: ");
  TextOut(0, LCD_LINE2, name);
  TextOut(0, LCD_LINE4, "Ich Arbeite!!!");
}

void display_start()
{
  int freq = 440;
  int c;
  ClearScreen();
  TextOut(0, LCD_LINE1, "Startvorgang!");
  TextOut(0, LCD_LINE2, "mit Bluetooth");
  TextOut(20, LCD_LINE5, "Starte in");

  for (c=5; c>0;c--) {
    NumOut(47, LCD_LINE6, c);
    PlayTone(freq,100);
    Wait(1000);
  }
  NumOut(47, LCD_LINE6, 0);
  PlayTone(freq,1000);
  Wait(1000);
  display_running();
}

void display_stop()
{
  int freq = 800;
  int c;
  ClearScreen();
  TextOut(0, LCD_LINE2, "Siegbedingung");
  TextOut(0, LCD_LINE3, "erreicht!!!");
  TextOut(20, LCD_LINE5, "Warte auf beendung.");

  while(true){
    PlayTone(freq,100);
    Wait(1000);
  }

}
