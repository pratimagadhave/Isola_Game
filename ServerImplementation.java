/**
 * Server Implementation class which extends the
 * UnicastRemoteObject and implements the remote
 * server interface, is used to communicate with clients
 * with the moves played by one player.
 * 
 * @author Pratima Gadhave
 * @author Asawari Deshpande
 * 
 * @version 1.0
 * 
 * Revision: $Logs$
 *
 */

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

/**
 * Server Implementation to accept connection 
 * from the remote clients and update the clients
 * with a player's moves.
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerRemoteInterface{
	/**
	 * Port number on which the server is.
	 */
	public static int port = 9999;
	/**
	 * Localhost address.
	 */
	public static String address = "127.0.0.1";
	/**
	 * LinkedList to maintain the number of
	 * clients connect to the server. We have
	 * synchronized the list so that at a time
	 * only one client can update and register
	 * itself with the server.
	 */
	List clients = Collections.synchronizedList(new LinkedList());
	/**
	 * Static column and row which are passed
	 * to all the clients to update their views
	 * after a player has played the move.
	 */
	static int column, row;
	/**
	 * variable to check the change in state
	 * ie. if player has played an invalid move
	 * then he miss the chance at that point
	 * this bit is set to 0 to state the turn will
	 * change.
	 */
	static int counter = 1;
	/**
	 * variable to pass to all the clients to
	 * update their view explaining how many moves
	 * a player has played and is left with 
	 * how many moves more.
	 */
	public static int count = 0;
	/**
	 * variable to check if there is change
	 * in turn.
	 */
	static char newturn = ' ';
	/**
	 * Initial state of after reset.
	 */
	public static char turn = 'X';
	
	/**
	 * default constructor for the server
	 * it also invokes the super constructor.
	 * 
	 * @throws RemoteException
	 * 
	 */
	protected ServerImplementation() throws RemoteException {
		super();
	}
	
	/**
	 * Get the values of the players turn
	 * along with the move count.
	 * 
	 * @param turn, count
	 * @throws RemoteException
	 * 
	 */
	@Override
	public void getTurn(char turn, int count) throws RemoteException {
		newturn = turn;
		counter = count;
	}

	/**
	 * Get the x and y co-ordinates from the player
	 * along with the player.
	 * 
	 * @param _column, _row, _turn
	 * @throws RemoteException
	 * 
	 */
	@Override
	public void getXandY(int _column, int _row, char _turn) throws RemoteException {
		if (_turn == turn)
		{
			count++;
			column = _column;
			row = _row; System.out.println("count" + count + "c" + column + "r" + row);
			//send the updated moves to all the clients.
			updateClients();
		}
	}

	/**
	 * Register the clients connected to the server.
	 * 
	 * @param newClient
	 * @throws RemoteException
	 * 
	 */
	@Override
	public void registerClients(ClientRemoteInterface newClient)
			throws RemoteException {
		clients.add(newClient);		
	}
	
	/**
	 * Reset the game to start.
	 * 
	 * @throws RemoteException
	 * 
	 */
	@Override
	public void resetAll() throws RemoteException {
		//reset all the variables to initial state
		counter = 1;
		count = 0;
		newturn = ' ';
		turn = 'X';
		//iterate over all clients in the list.
		Iterator iter = clients.iterator();
		while (iter.hasNext())
		{
			ClientRemoteInterface client = (ClientRemoteInterface)iter.next();
			try
			{
				//for each client call the reset method.
				client.reset();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * After one player has played the move
	 * the server will update the view of all
	 * the clients in the List.
	 * 
	 */
	private void updateClients()
	{
		//iterate over all the clients in the list.
		Iterator iter = clients.iterator();
		while (iter.hasNext())
		{
			ClientRemoteInterface client = (ClientRemoteInterface)iter.next();
			try
			{
				//for each client update the move played.
				client.moveMade(column, row, count);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}System.out.println("count" + count + "counter" + counter + "newturn" + newturn + "turn" + turn);
		//check for the state after the move played.
		if (count == 2 || (counter == 0 && newturn != ' '))
		{
			count = 0;
			counter = 1;
			turn = newturn;
			newturn = ' ';System.out.println("count" + count + "counter" + counter + "newturn" + newturn + "turn" + turn);
		}
	}
	
	/**
	 * Main method to start the server
	 * it starts the server and makes it available
	 * for clients to connect from remote machine.
	 * 
	 * @param args[]
	 * 
	 */
	public static void main(String args[])
	{
		System.out.println("Server starting..");
		try
		{
			//create a reference of the server.
			ServerImplementation server = new ServerImplementation();
			//create a variable to state the name of the server.
			String serverName = "rmiServer";
			//locate the registry on the port and create a reference.
			Registry registry = LocateRegistry.createRegistry(port);
			/*register the object created in the rmi register
			with a lookup name. */
			registry.rebind(serverName, server);
			System.out.println("Server is now running and ready to register clients");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
