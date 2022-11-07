// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;
import java.util.Scanner;

import common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @author Amani Louendriz
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  //final public static int DEFAULT_PORT = 5555;
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
   ChatIF serverUI; 
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
 * @throws IOException 
   */
  public EchoServer(int port,ChatIF serverUI) throws IOException 
  {
    super(port);
    this.serverUI = serverUI;
    listen();// listen to what's going to happen
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    serverUI.display("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    serverUI.display("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    serverUI.display("Server has stopped listening for connections.");
  }
  
  /**
   * Implementation of a Hook method called each time a new client connection is
   * accepted. The default implementation does nothing.
   * @param client the connection connected to the client.
   */
  protected void clientConnected(ConnectionToClient client) {
	  serverUI.display(client + "has been connected successfully.");
  }

  /**
   * Implementation of a Hook method called each time a client disconnects.
   * The default implementation does nothing. The method
   * may be overridden by subclasses but should remains synchronized.
   *
   * @param client the connection with the client.
   */
  synchronized protected void clientDisconnected(
    ConnectionToClient client) {
	  serverUI.display(client + "has been disconnected."); 
  }
  
  /** Adding the handleMessagefromServerUI to here.
   * It handles all the data coming from the UI as well as special commands.
   * @param message from the server UI. */
  
  public void handleMessageFromServerUI(String message) throws IOException
  //TODO adding commands here
  // TODO Here the catch IO Exception was removed. Could restorate it maybe?
  {
		 if (message.startsWith("#")) {
			  handleCommand(message);
		 }
		 else{
	      serverUI.display(message);
		  sendToAllClients(message);
		 }  
  }
		 
		 
  private void handleCommand(String message) throws IOException {
				
		      Scanner st = new Scanner(message).useDelimiter(" ");// this is used when the user would like to change 
		      // the port or the host. It provides us with the parameter value to which we would like to change.
		      String cmd = st.next();
		      
		      switch(cmd) {
		      case "#quit":
		    	  serverUI.display("The server is going to shut down.");
		    	  this.stopListening();
		    	 this.close();
		    	 System.exit(0);
		     	 break;
		     	 
		      case "#stop":
		    	  serverUI.display("server is going to stop listening from clients");
		    	  this.stopListening();	
		     	 break;
		     	 
		      case "#close":
		    	  serverUI.display("The server is going to stop listening and disconnect all the clients.");
		     		 this.stopListening();
		     		 this.close();
		     		 break;	 
		     		 
		      case "#setport":
		      	 int port = Integer.parseInt(st.next());
		      	 if(!this.isListening()) {
		      		 this.setPort(port);
		      	 }
		      	 else {
		      		 serverUI.display("The server is connected. Can't set up port. Please disconnect the server first.");
		      	 }
		      	 break;
		      	 
		      case "#start":
		    	  if(!this.isListening()){
		    		  this.listen();
		    	  }
		    	  else {
		    		  serverUI.display("Server is already listening.");
		    	  }
		    	  break;
		      case "#getport" :
		    	  serverUI.display("The port used is : " + this.getPort());
		    	  break;
		      
		      default:
		     	 serverUI.display("Command not known !");
		      	         }
			
			
		}
  
  /**
   * This method terminates the server.
   */
  public void quit()
  {
    try
    {
      close();
    }
    catch(IOException e) {}
    System.exit(0);
  }

  
  

}
//End of EchoServer class
