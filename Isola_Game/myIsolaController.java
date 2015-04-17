/**
 * myIsolaController acts as the driver for the client
 * which has listener and listens to the mouse click 
 * on the board performed by the client and send the 
 * x and y co_ordinates to the server via the model
 * along with the player who played the game.
 * 
 * @author Pratima Gadhave
 * @author Asawari Deshpande
 * 
 * @version 1.0
 * 
 * Revision: $Logs$
 * 
 * Note: Player should be started with command line
 * argument indicating the player number.
 *
 */

import java.awt.event.*;
import java.rmi.RemoteException;

/**
 * myIsolaController acts as the driver for the client
 * which has listener and listens to the mouse click 
 * on the board performed by the client and send the 
 * x and y co_ordinates to the server via the model
 * along with the player who played the game.
 *
 */
public class myIsolaController{
	/**
	 * SpaceSize used to identify the column and row
	 * where the user has clicked.
	 */
	private static final int SpaceSize = 40;
	/**
	 * are used to send the column and row.
	 */
	public static int move1x, move1y;
	/**
	 * String to hold the name of the client.
	 */
	public static String name = null;
	/**
	 * create a reference of the myIsolaModel.
	 */
	myIsolaModel game;
	/**
	 * character variable to set the board variable for client.
	 */
	public char _turn = ' ';

	/**
	 * parameterized constructor for the controller
	 * to listen to mouse click events.
	 * 
	 * @throws RemoteException
	 */
	public myIsolaController() throws RemoteException
	{	
		/*instantiate myIsolaModel calling its parameterized
		 * constructor passing name as parameter. 
		 */
		game = new myIsolaModel(name);
		//if the player is 1 his board is X
		if (name.equals("1"))
			_turn = 'X';
		//if the player is 1 his board is X
		else if (name.equals("2"))
			_turn = 'O';
		//an event listener on mouse click.
		game.addMouseListener(
				new MouseAdapter()
				{	
					/**
					 * mousePressed event to get the x and y
					 * co_ordinates.
					 * 
					 */
					@Override
					public void mousePressed(MouseEvent e)
					{
						/*calculate the column and row using the 
						 * x and y co-ordinates and SpaceSize. 
						 */
						move1x = e.getX() / SpaceSize;
						move1y = e.getY() / SpaceSize;
						try 
						{
							/*send the status to the model which will
							 * in turn send the values to server invoking
							 * its remote method.
							 */
							game.getStatus(move1x, move1y, _turn);
						} 
						catch (Exception e1) 
						{
							e1.printStackTrace();
						}
					}
				}
				);
	}

	/**
	 * Main method to start the client; where
	 * we set the client view of the board. 
	 * 
	 * @param args
	 * @throws RemoteException
	 * 
	 */
	public static void main(String args[]) throws RemoteException
	{
		//take the player name from the command line
		name = args[0];
		//call the view for the client.
		new myIsolaView();
	}
}

