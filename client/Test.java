package client;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
	         String message = "#sethost 192.168.42.56";
		     //String message  = "#logoff";
	         Scanner st = new Scanner(message).useDelimiter(" ");
	         String cmd = st.next();
	         
	         switch(cmd) {
	         case "#quit":
	        	 System.out.println("quit method");
	        	 break;
	         case "#logoff":
	        	 System.out.println("logoff method");
	        	 break;
	         case "#sethost":
	        	 String host = st.next();
	        	 System.out.println("set host method with host: "+host);
	        	 break;
	        	 
	    
	        	 
	         default:
	        	 System.out.println("Default state.");
	         
	         
	         	         }
	         
	}

}
