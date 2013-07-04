// Rotation des Roboters im Raum ohne Beachtung der Ausrichtung und der Feldgröße // Koordinaten

void rotate_change(int number,bool new=false)
{
if(new){
global_direction=number;
}else{
global_direction=global_direction+number;
}
while(global_direction>4){global_direction=global_direction-4;}
while(global_direction<1){global_direction=global_direction+4;}
}

void rotate_right()
{
rotate_change(1);
motor_rotate(global_rotatePower, physic_rotate(90),100);
}

void rotate_left()
{
rotate_change(-1);
motor_rotate(global_rotatePower, physic_rotate(90),-100);
}

void rotate_opposite(bool site=true)
{
rotate_change(2);
if(site){
motor_rotate(global_rotatePower, physic_rotate(180),-100);
}else{
motor_rotate(global_rotatePower, physic_rotate(180),100);
}
}
