// Hier wird die Bluetooth funktionalität hergestellt
void bluetooth_sendInt(int what,int connection,int mailbox,bool wait_answer=true);

int bluetooth_receiveInt(int connection,int mailbox,bool sendAnswer=true, bool wait=true, bool delete=true)
{
int out;
do{
out = StrToNum(BTReceiveMessage(connection, mailbox, delete));
if(out<0){out=0;}
}while(out==0 && wait); // Wartet auf eine positive Zahl
if(out!=0 && sendAnswer){bluetooth_sendInt(0,connection,bluetooth_mailboxGotIt,false);}
return out-1; // -1 bedeutet somit das es keine Nachricht gab
}

// Int Empfangen und falls nötig auf Antwort warten
void bluetooth_sendInt(int what,int connection,int mailbox,bool wait_answer=true)
{
BTSendMessage(connection,mailbox,NumToStr(what+1));
if(wait_answer){bluetooth_receiveInt(connection,bluetooth_mailboxGotIt,false);}
}

void bluetooth_sendId()
{
if(bluetooth_isBlocked){
bluetooth_sendInt(2,bluetooth_connection,bluetooth_mailboxSystem,false);
bluetooth_isBlocked=false;
}else{
bluetooth_sendInt(1,bluetooth_connection,bluetooth_mailboxSystem,false);
}}

void bluetooth_init()
{
display_bluetoothConfiguration();
rotate_change(bluetooth_receiveInt(bluetooth_connection,bluetooth_mailboxSystem,false),true);
}

void bluetooth_run()
{
bluetooth_sendId();
int out = bluetooth_receiveInt(bluetooth_connection,bluetooth_mailboxSystem,false);
if(out==1){move_yMinus();return;}
if(out==2){move_xMinus();return;}
if(out==3){move_yPlus();return;}
if(out==4){move_xPlus();return;}
display_stop();
}
