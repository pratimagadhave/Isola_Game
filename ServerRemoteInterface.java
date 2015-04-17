/**
 * Remote interface of the server; which will be used
 * by client to send the moves when a player plays.
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

/**
 * Remote server interface for client
 * to communicate with the server.
 */
public interface ServerRemoteInterface extends Remote{
	/**
	 * Get the x and y co-ordinates from the player
	 * along with the player.
	 * 
	 * @param _column, _row, _turn
	 * @throws RemoteException
	 * 
	 */
	public void getXandY(int _column, int row, char _turn) throws RemoteException;
	/**
	 * Get the values of the players turn
	 * along with the move count.
	 * 
	 * @param turn, count
	 * @throws RemoteException
	 * 
	 */
	public void getTurn(char turn, int count) throws RemoteException;
	/**
	 * Register the clients connected to the server.
	 * 
	 * @param newClient
	 * @throws RemoteException
	 * 
	 */
	public void registerClients (ClientRemoteInterface newClient) throws RemoteException;
	/**
	 * Reset the game to start.
	 * 
	 * @throws RemoteException
	 * 
	 */
	public void resetAll() throws RemoteException;
}
