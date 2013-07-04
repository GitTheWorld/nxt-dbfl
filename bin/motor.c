// Motor Steuerung

void motor_forward(int power, int degree)
{
RotateMotor(motor_port, motor_site*power, degree);
}

void motor_rotate(int power,int degree, int turnpct, bool sync=true, bool stopIt=true)
{
RotateMotorEx(motor_port, motor_site*power, degree,turnpct, sync, stopIt);
}

void motor_forwardWithoutEnd(int power)
{
OnFwdSync(OUT_AC,motor_site*power,0);
}

void motor_stop()
{
Off(OUT_AC);
}
