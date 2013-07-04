package control;

import javax.swing.JOptionPane;

public class guiElements{
	
public static String displayInput(String question, String title) {
	
while(true){

String s = JOptionPane.showInputDialog(null,question,title, JOptionPane.PLAIN_MESSAGE);

if(s == null){System.exit(0);}

if (s.length() > 0) {
    return s;
}

}}

public static void displayOutput(String message, String title) {
JOptionPane.showMessageDialog(null, message,title, JOptionPane.PLAIN_MESSAGE);
}

}
