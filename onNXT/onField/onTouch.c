// Sobald der Berührungssensor ausgelöst wird soll er das Feld markieren

void onTouch()
{
if(sensor_touch()==1)
{
rotate_change(2);
bluetooth_isBlocked=true;

motor_forward(global_power,physic_move(touch_rev));
// Unwichtig wie rum, interessanter wenn es zufällig erscheint
if(followLine_lastChange){
motor_rotate(global_rotatePower, physic_rotate(180),-100);
}else{
motor_rotate(global_rotatePower, physic_rotate(180),100);
}
motor_forward(global_power,physic_move(touch_fwd));
}
}
