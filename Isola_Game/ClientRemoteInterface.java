/**
 * Remote interface of the client; which will be used
 * by server to update the client status whenever a player 
 * plays the move.
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
 * Remote Client interface for server
 * to communicate with the client.
 */
public interface ClientRemoteInterface extends Remote {
	/**
	 * Remote method of client, where server will 
	 * send the row and column and to update on the board.
	 * 
	 * @param column, row, count
	 * @throws RemoteException
	 * 
	 */
	public void moveMade(int column, int row, int count) throws RemoteException;
	/**
	 * Remote method of client, where server will 
	 * send reset the board.
	 * 
	 * @throws RemoteException
	 * 
	 */
	public void reset() throws RemoteException;
}

