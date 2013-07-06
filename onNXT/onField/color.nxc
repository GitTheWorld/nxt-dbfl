// Kontrolliere was der Sensor aktuell ausgibt.
void color_update()
{
int light = sensor_light();

if(color_middle==-1){color_middle=light;}

if(light<color_middle-color_around)
{
color_now=0;
}
else if(light>color_middle+color_around)
{
color_now=2;
}
else
{
//color_middle=color_middle-((color_middle-light)/2);
color_now=1;
}
}

bool color_backround(bool update=true)
{
if(update || color_middle==-1){color_update();}
if(color_now==0){return true;}else{return false;}
}
bool color_line(bool update=true)
{
if(update || color_middle==-1){color_update();}
if(color_now==1){return true;}else{return false;}
}
bool color_field(bool update=true)
{
if(update || color_middle==-1){color_update();}
if(color_now==2){return true;}else{return false;}
}
