// Einer Linie Folgen und auf eventuelle Abweichungen reagieren.
// Im Notfall nach Linie suchen...

void follow_line()
{
bool allRight=true;

if(color_backround()){
motor_stop();
allRight=false;

if(followLine_lastChange){

for(int i=0;i<followLine_maxDegree;i++){
motor_rotate(global_rotatePower,physic_rotate(followLine_gap), 100);
if(!color_backround()){followLine_lastChange=true;allRight=true;break;}
}

if(!allRight){
motor_rotate(global_rotatePower,physic_rotate(followLine_gap*followLine_maxDegree), -100);
for(int i=0;i<followLine_maxDegree;i++)
{
motor_rotate(global_rotatePower,physic_rotate(followLine_gap), -100);
if(!color_backround()){followLine_lastChange=false;allRight=true;break;}
}
if(!allRight){motor_rotate(global_rotatePower,physic_rotate(followLine_gap*followLine_maxDegree), 100);}
}

}else{

for(int i=0;i<followLine_maxDegree;i++){
motor_rotate(global_rotatePower,physic_rotate(followLine_gap), -100);
if(!color_backround()){followLine_lastChange=false;allRight=true;break;}
}

if(!allRight){
motor_rotate(global_rotatePower,physic_rotate(followLine_gap*followLine_maxDegree), 100);
for(int i=0;i<followLine_maxDegree;i++)
{
motor_rotate(global_rotatePower,physic_rotate(followLine_gap), 100);
if(!color_backround()){followLine_lastChange=true;allRight=true;break;}
}}
if(!allRight){motor_rotate(global_rotatePower,physic_rotate(followLine_gap*followLine_maxDegree), -100);}
}

}

if(allRight){
motor_forwardWithoutEnd(global_power);
}else{motor_forward(global_power,physic_move(1));}

}
