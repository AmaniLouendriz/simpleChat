// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;
import java.util.Scanner;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  String login;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI as well as special commands.          
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
		  try
		    {
			  if (message.startsWith("#")) {
				  handleCommand(message);
			  }
			  else{
				  sendToServer(message);
				  }
		    }
		    catch(IOException e)
		    {
		      clientUI.display
		        ("Could not send message to server.  Terminating client.");
		      quit();
		    }  
	  
  }
  
  private void handleCommand(String message) throws IOException {
	
      Scanner st = new Scanner(message).useDelimiter(" ");// this is used when the user would like to change 
      // the port or the host. It provides us with the parameter value to which we would like to change.
      String cmd = st.next();
      
      switch(cmd) {
      case "#quit":
    	 this.closeConnection();
     	 this.quit();
     	 break;
     	 
      case "#logoff":
    	  if(this.isConnected()) {
  			clientUI.display("The client is going to be disconnected.");
  			this.closeConnection();
  		}
  		else {
  			clientUI.display("The client is already disconnected.");
  		}		
     	 break;
     	 
      case "#sethost":
     	 String host = st.next();// here we have the host number
     	 if(!this.isConnected()) {
     		 this.setHost(host);
     	 }
     	 else {
     		 clientUI.display("The client is connected. Can't set up host. Please disconnect first.");
     	 }
     	 break;
     	 
      case "#setport":
      	 int port = Integer.parseInt(st.next());
      	 if(!this.isConnected()) {
      		 this.setPort(port);
      	 }
      	 else {
      		 clientUI.display("The client is connected. Can't set up port. Please disconnect first.");
      	 }
      	 break;
      	 
      case "#login":
    	  if(!this.isConnected()) {
    		  this.openConnection();
    	  }
    	  else {
    		  clientUI.display("Client is already connected.");
    	  }
    	  break;
      case "#gethost":
    	  clientUI.display("the host used is :" + this.getHost());
    	  break;
      case "#getport" :
    	  clientUI.display("The port used is : " + this.getPort());
    	  break;
      
      default:
     	 clientUI.display("Command not known !");
     	 
      
      
      	         }
	
	
}


/**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  /**
	 * Implementation of a Hook method called after the connection has been closed. The default
	 * implementation does nothing. The method may be overriden by subclasses to
	 * perform special processing such as cleaning up and terminating, or
	 * attempting to reconnect.
	 */
  @Override
	protected void connectionClosed() {
	  
	  clientUI.display("Connection has been closed.");
	}

	/**
	 * Implementation of a Hook method called each time an exception is thrown by the client's
	 * thread that is waiting for messages from the server. The method may be
	 * overridden by subclasses.
	 * 
	 * @param exception
	 *            the exception raised.
	 */
  
  @Override
  
	protected void connectionException(Exception exception) {
	  
	  clientUI.display("The server has shut down. ");
	  this.quit();
	}
  /** Method I added to get the login ID of the user*/
  public String getLogin() {
	  return this.login;
  }
  
  /** Method I added to set the login ID of the user*/
  public void setLogin(String data) {
	  this.login = data;
  }

  /**
	 * Implementation of a Hook method called after a connection has been established. The default
	 * implementation does nothing. It may be overridden by subclasses to do
	 * anything they wish.
	 */
	protected void connectionEstablished() {
		String data = "#login" + login ;  // client.getLogin() 
		clientUI.display(data);
	}
}
//End of ChatClient class
