/**
 * myIsolaModel class is the client implementation
 * which extends JPanel and implements the remote
 * client interface, it updates the move as server 
 * invokes the remote methods and changes the board
 * state.
 * 
 * @author Pratima Gadhave
 * @author Asawari Deshpande
 * 
 * @version 1.0
 * 
 * Revision: $Logs$
 *
 */

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.*;
import javax.swing.*;

/**
 * Model class also the client implementation used to 
 * accept the remote command invocation from server and
 * update the view also to invoke commands on server.
 *  
 */
public class myIsolaModel extends JPanel implements ClientRemoteInterface{
	/**
	 * a character array to simulate the board.
	 */
	private char board[][] = new char[8][8];
	/**
	 * X indicates the player 1 or in Black.
	 */
	private final static char X = 'X';
	/**
	 * O indicates the player 2 or in Blue.
	 */
	private final static char O = 'O';
	/**
	 * D indicates the destroyed field.
	 */
	private final static char D = 'D';
	/**
	 * Empty indicates that the field is unused.
	 */
	private final static char Empty = ' ';
	/**
	 * SpaceSize for designing the board.
	 */
	private static final int SpaceSize = 40;
	/**
	 * CellaPadding used for drawing the pictures.
	 */
	private static final int CellPadding = 5;
	/**
	 * check if the game is over or not.
	 */
	static int gameOver;
	/**
	 * check whose turn it is.
	 */
	public static char turn;
	/**
	 * display the player's name.
	 */
	private static String turnInfo;
	
	/**
	 * Port address where to connect the server.
	 */
	private static int port = 9999;
	/**
	 * Localhost where the server is residing.
	 */
	private static String address = "127.0.0.1";
	
	/**
	 * reference of server remote interface.
	 */
	public ServerRemoteInterface rmiServer;
	/**
	 * name of the player connecting.
	 */
	public static String name = null;
	/**
	 * messages to be displayed.
	 */
	static String moveMessage = "Black will Start";
	
	/**
	 * Parameterized constructor which invokes super
	 * constructor, sets the name of the client, connects
	 * to the server and creates the view by resetting the
	 * game for the client.
	 * 
	 * @param name
	 * @throws RemoteException
	 * 
	 */
	public myIsolaModel (String name) throws RemoteException
	{
		//call the JPanel default constructor.
		super(); 
		//set the name of the client.
		this.name = name;
 
		try
		{
			System.out.println("Connecting to server..");
			//locate the rmi register on the given server address and port.
			Registry registry = LocateRegistry.getRegistry(address, port);
			//lookup for the register with the name given.
			rmiServer = (ServerRemoteInterface)registry.lookup("rmiServer");
			//export the object using unicastRemoteObject.
			UnicastRemoteObject.exportObject(this);
			//call the server method to register the client.
			rmiServer.registerClients(this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//call to the reset method to set all components.
		reset();	
	}
	
	/**
	 * When the client hits new game button call
	 * the server's resetAll method to reset
	 * the game for the clients.
	 *  
	 * @throws RemoteException
	 * 
	 */
	public void resetRequest() throws RemoteException
	{
		rmiServer.resetAll();
	}

	/**
	 * Remote method of client, where server will 
	 * send reset the board.
	 * 
	 * @throws RemoteException
	 * 
	 */
	@Override
	public void reset () throws RemoteException
	{
		//set gameOver back to 0.
		gameOver = 0;
		//set the first move for Black.
		turn = X;
		//set the message to initial state.
		moveMessage = "Black will Start";
		//if turn is of Black we set display info as Black.
		if(turn == X)
		{
			turnInfo = "Black";
		}
		//else we set display info of Blue.
		else if(turn == O)
		{
			turnInfo = "Blue";
		}
		//set all the array elements to Empty.
		for (int row = 1; row <= 7; row++)
		{
			for (int col = 1; col <= 7; col++)
			{
				board[col][row] = Empty;
			}
		}
		//Except for the gem position where the 2 opponents start.
		board[4][1] = X;
		board[4][7] = O;
		//we call the paintComponent method. 
		repaint();
	}
	
	/**
	 * Draw all the board depending upon the changed state.
	 * 
	 * @param g
	 * 
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		//call the super constructor.
		super.paintComponent(g);
		//create a font reference to user defined font.
		Font font = new Font("Verdana", Font.BOLD, 15);
		//set the font for the message.
		g.setFont(font);
		//using drawString draw the text to display the message.
		g.drawString(moveMessage, 100 + CellPadding, 30 - CellPadding * 2);
		//set the color of the lines to black.
		g.setColor(Color.BLACK);
		//draw vertical lines
		g.drawLine(SpaceSize, 40, SpaceSize, SpaceSize * 8);
		g.drawLine(SpaceSize * 2, 40, SpaceSize * 2, SpaceSize * 8);
		g.drawLine(SpaceSize * 3, 40, SpaceSize * 3, SpaceSize * 8);
		g.drawLine(SpaceSize * 4, 40, SpaceSize * 4, SpaceSize * 8);
		g.drawLine(SpaceSize * 5, 40, SpaceSize * 5, SpaceSize * 8);
		g.drawLine(SpaceSize * 6, 40, SpaceSize * 6, SpaceSize * 8);
		g.drawLine(SpaceSize * 7, 40, SpaceSize * 7, SpaceSize * 8);
		g.drawLine(SpaceSize * 8, 40, SpaceSize * 8, SpaceSize * 8);
		
		//draw horizontal lines
		g.drawLine(40, SpaceSize, SpaceSize * 8, SpaceSize);		
		g.drawLine(40, SpaceSize * 2, SpaceSize * 8, SpaceSize * 2);
		g.drawLine(40, SpaceSize * 3, SpaceSize * 8, SpaceSize * 3);
		g.drawLine(40, SpaceSize * 4, SpaceSize * 8, SpaceSize * 4);
		g.drawLine(40, SpaceSize * 5, SpaceSize * 8, SpaceSize * 5);
		g.drawLine(40, SpaceSize * 6, SpaceSize * 8, SpaceSize * 6);
		g.drawLine(40, SpaceSize * 7, SpaceSize * 8, SpaceSize * 7);
		g.drawLine(40, SpaceSize * 8, SpaceSize * 8, SpaceSize * 8);
		
		//if the player is Black we set color Black.
		if(turn == X)
		{
			g.setColor(Color.BLACK);
		}
		//if the player is Blue we set the color Blue.
		else if(turn == O)
		{
			g.setColor(Color.BLUE);
		}
		
		//display the turn of the player using the oval.
		g.fillOval( 10 + CellPadding, 
				10 + CellPadding, 
				30 - CellPadding * 2, 
				30 - CellPadding * 2);
		
		//draw the positions of the Black and Blue gem.
		for (int col = 0; col <= 7; col++)
		{
			for(int row = 0; row <= 7; row++)
			{
				//is Black at this position then we draw Black oval.
				if (board[col][row] == X)
				{
					//draw Black gem on the board.
					g.setColor(Color.BLACK);
					g.fillOval( SpaceSize * col + CellPadding, 
							SpaceSize * row + CellPadding, 
							SpaceSize - CellPadding * 2, 
							SpaceSize - CellPadding * 2);
				}
				//is Blue at this position then we draw Blue oval.	
				else if (board[col][row] == O)
				{
					//draw Blue gem on the board.
					g.setColor(Color.BLUE);
					g.fillOval( SpaceSize * col + CellPadding, 
								SpaceSize * row + CellPadding, 
								SpaceSize - CellPadding * 2, 
								SpaceSize - CellPadding * 2);
				}
				//is this position destroyed.
				else if (board[col][row] == D)
				{
					//draw red X on the board.
					g.setColor(Color.RED);
					g.drawLine( SpaceSize * col + CellPadding, 
								SpaceSize * row + CellPadding, 
								SpaceSize * col  + SpaceSize - CellPadding , 
								SpaceSize * row  + SpaceSize - CellPadding );
					
					g.drawLine( SpaceSize * col + SpaceSize - CellPadding , 
								SpaceSize * row + CellPadding, 
								SpaceSize * col + CellPadding, 
								SpaceSize * row  + SpaceSize - CellPadding );
				}
			}
		}
	}

	/**
	* See if opponent has lost the game
	* if the player has lost then we return 1 else
	* we return 0.
	* 
	* @param player 
	* @return lost  
	* 			
	*/
	private int checkForLose(char player)
	{
		//set lost as 0 indicating player has not lost.
		int lost = 0;
		//check if the player can make moves.
		for (int row = 1; row <= 7; row++)
		{
			for (int col = 1; col <= 7; col++)
			{
				if(board[col][row] == player)
				{
					//check for top left corner valid moves.
					if(col == 1 && row ==1)
					{
						if(board[col+1][row] != Empty &&
								board[col+1][row+1] != Empty &&
								board[col][row+1] != Empty)
						{
							lost = 1;
						}
					}
					//check for bottom left corner valid moves.
					else if(col == 1 && row == 7)
					{
						if(board[col+1][row] != Empty &&
								board[col+1][row-1] != Empty &&
								board[col][row-1] != Empty)
						{
							lost = 1;
						}
					}
					//check for top right corner valid moves.
					else if(col == 7 && row == 1)
					{
						if(board[col-1][row] != Empty &&
								board[col-1][row+1] != Empty &&
								board[col][row+1] != Empty)
						{
							lost = 1;
						}
					}
					//check for bottom right corner valid moves.
					else if(col == 7 && row == 7)
					{
						if(board[col-1][row] != Empty &&
								board[col-1][row-1] != Empty &&
								board[col][row-1] != Empty)
						{
							lost = 1;
						}
					}
					//check for left edge valid moves.
					else if (col == 1 && row != 1 && row != 7)
					{
						if(board[col][row-1] != Empty &&
								board[col][row+1] != Empty &&
								board[col+1][row] != Empty &&
								board[col+1][row-1] != Empty &&
								board[col+1][row+1] != Empty)
						{
							lost = 1;
						}
					}
					//check for right edge valid moves.
					else if (col == 7 && row != 1 && row != 7)
					{
						if(board[col][row-1] != Empty &&
								board[col][row+1] != Empty &&
								board[col-1][row] != Empty &&
								board[col-1][row-1] != Empty &&
								board[col-1][row+1] != Empty)
						{
							lost = 1;
						}
					}
					//check for top edge valid moves.
					else if(row == 1 && col != 1 && col != 7)
					{
						if(board[col-1][row] != Empty &&
								board[col+1][row] != Empty &&
								board[col-1][row+1] != Empty &&
								board[col][row+1] != Empty &&
								board[col+1][row+1] != Empty)
						{
							lost = 1;
						}
					}
					//check for bottom edge valid moves.
					else if(row == 7 && col != 1 && col != 7)
					{
						if(board[col-1][row] != Empty &&
								board[col+1][row] != Empty &&
								board[col-1][row-1] != Empty &&
								board[col][row-1] != Empty &&
								board[col+1][row-1] != Empty)
						{
							lost = 1;
						}
					}
					//else if the gem is not at the edge check all possible moves.
					else
					{
						if(board[col-1][row] != Empty && 
							board[col+1][row] != Empty && 
							board[col][row+1] != Empty && 
							board[col][row-1] != Empty && 
							board[col-1][row-1] != Empty &&
							board[col+1][row-1] != Empty &&
							board[col-1][row+1] != Empty &&
							board[col+1][row+1] != Empty)
						{
							lost = 1;
						}
					}
					break;
				}
			}
		}
			
		//return the result.
		return lost;
	}
	
	/**
	 * Check for the move made by the player
	 * this method is invoked by server on all the 
	 * clients remotely.
	 * 
	 * @param column, row, count
	 * @throws RemoteException
	 * 
	 */
	@Override
	public void moveMade(int column, int row, int count) throws RemoteException
	{
		/*check for the previous column and row of the gem
		 * before the move was made.
		 */
		int previousColumn = 0;
		int previousRow = 0;
		
		//if the gameOver is 1 then the game is already over.
		if (gameOver == 1)
		{
		 	//the game is over, and somebody won already.
			moveMessage = turnInfo + "already won!!!";
		}
		
		//if gameOver is 0 then the game is not yet done.
		else if (gameOver == 0)
		{
			/*calculate the previous value of column and row
			 * of the gem playing the move.
			 */
			for (int prow = 1; prow <= 7; prow++)
			{
				for (int pcol = 1; pcol <= 7; pcol++)
				{
					if(board[pcol][prow] == turn)
					{
						previousColumn = pcol;
						previousRow = prow;
						break;
					}
				}
			}
			//make sure click event was within the board.
			if (column <= 7 && column >= 1 && row <= 7 && row >= 1)
			{System.out.println("pc " + previousColumn + "pr " + previousRow);
				//if the count is 1 then gem performed 1st move.
				if (count == 1)
				{
					/*check if the field clicked was not a
					 * destroyed field or a gem.
					 */
					if (board[column][row] != D&& 
							board[column][row] != X && 
							board[column][row] != O)
					{
						/*check if the gem has performed a valid move.
						 * a valid move is just a step move to the 
						 * neighboring field.
						 */
						if(previousColumn == column-1)
						{
							//check for the valid row.
							if(previousRow == row-1)
							{
								board[column][row] = turn;
								board[previousColumn][previousRow] = Empty;
								moveMessage = "Valid Move";
							}
							else if (previousRow == row+1)
							{
								board[column][row] = turn;
								board[previousColumn][previousRow] = Empty;
								moveMessage = "Valid Move";
							}
							else if(previousRow == row)
							{
								board[column][row] = turn;
								board[previousColumn][previousRow] = Empty;
								moveMessage = "Valid Move";
							}
							//if not a valid move then the player miss the chance.
							else
							{
								moveMessage = "Invalid move; Miss Chance";
								count = 0;
								//change the turn for the next player.
								if (turn == X)
								{
									turn = O;
									/*call the method to inform server of the
									 * changed status. 
									 */
									setTurn(turn, count);
								} 
								else
								{
									turn = X;
									/* call the method to inform server of the 
									 * changed status.
									 */
									setTurn(turn, count);
								}
							}
						}
						//check for the valid column.
						else if (previousColumn == column)
						{
							//check for the valid row.
							if (previousRow == row+1)
							{
								board[column][row] = turn;
								board[previousColumn][previousRow] = Empty;
								moveMessage = "Valid Move";
							}
							else if(previousRow == row-1)
							{
								board[column][row] = turn;
								board[previousColumn][previousRow] = Empty;
								moveMessage = "Valid Move";
							}
							//if the move is not valid the player miss the chance.
							else
							{
								moveMessage = "Invalid move; Miss Chance";
								count = 0;
								//change the turn for the next player.
								if (turn == X)
								{
									turn = O;
									/* call the method to inform server of the 
									 * changed status.
									 */									
									setTurn(turn, count);
								} 
								else
								{
									turn = X;
									/* call the method to inform server of the 
									 * changed status.
									 */
									setTurn(turn, count);
								}
							}
						}
						//check for the valid column.
						else if(previousColumn == column+1)
						{
							//check for the valid row.
							if(previousRow == row)
							{
								board[column][row] = turn;
								board[previousColumn][previousRow] = Empty;
								moveMessage = "Valid Move";
							}
							else if (previousRow == row-1)
							{
								board[column][row] = turn;
								board[previousColumn][previousRow] = Empty;
								moveMessage = "Valid Move";
							}
							else if(previousRow == row+1)
							{
								board[column][row] = turn;
								board[previousColumn][previousRow] = Empty;
								moveMessage = "Valid Move";
							}
							//if the move is not valid the player miss the chance.
							else
							{
								moveMessage = "Invalid move; Miss Chance";
								count = 0;
								//change the turn for the next player.
								if (turn == X)
								{
									turn = O;
									/* call the method to inform server of the 
									 * changed status.
									 */
									setTurn(turn, count);
								} 
								else
								{
									turn = X;
									/* call the method to inform server of the 
									 * changed status.
									 */
									setTurn(turn, count);
								}
							}
						}
						//if the move is not valid the player miss the chance.
						else
						{
							moveMessage = "Invalid move; Miss Chance";
							count = 0;
							//change the turn for the next player.
							if (turn == X)
							{
								turn = O;
								/* call the method to inform server of the 
								 * changed status.
								 */
								setTurn(turn, count);
							} 
							else
							{
								turn = X;
								/* call the method to inform server of the 
								 * changed status.
								 */
								setTurn(turn, count);
							}
						}
					}
					//if the field chosen was not empty.
					else
					{
						/*check if it was gem placed in that field; if so you message
						 * invalid move.
						 */
						if (board[column][row] == X || board[column][row] == O)
						{
							moveMessage = "Invalid move; Miss Chance";
							count = 0;
							//the player miss the chance and you change the turn.
							if (turn == X)
							{
								turn = O;
								/* call the method to inform server of the 
								 * changed status.
								 */
								setTurn(turn, count);
							} 
							else
							{
								turn = X;
								/* call the method to inform server of the 
								 * changed status.
								 */
								setTurn(turn, count);
							}
						}
						//check if the field was already destroyed.
						else
						{
							//you drop an error message.
							moveMessage = "Invalid move; Miss Chance";
							count = 0;
							//the player miss the chance and you change the turn.
							if (turn == X)
							{
								turn = O;
								/* call the method to inform server of the 
								 * changed status.
								 */
								setTurn(turn, count);
							} 
							else
							{
								turn = X;
								/* call the method to inform server of the 
								 * changed status.
								 */
								setTurn(turn, count);
							}
						}
					}
				}
				/* if the count was 2 it means the gem has performed
				 * second move.
				 */
				else if (count == 2)
				{
					//check if the field is empty.
					if (board[column][row] != D  && 
							board[column][row] != X && 
							board[column][row] != O)
					{
						//reinitialize the count.
						ServerImplementation.count = 0;
						//change the field to destroyed.
						board[column][row] = D;
						//set the error message.
						moveMessage = "Valid Move";
						//change the turn for the next player.
						if (turn == X)
						{
							turn = O;
							/* call the method to inform server of the 
							 * changed status.
							 */
							setTurn(turn, count);
						} 
						else
						{
							turn = X;
							/* call the method to inform server of the 
							 * changed status.
							 */
							setTurn(turn, count);
						}
					}
					//if the field was not empty.
					else
					{
						//check if it was gem placed in the field.
						if (board[column][row] == X || board[column][row] == O)
						{
							//if yes you say invalid move.
							moveMessage = "Invalid move; Miss Chance";
							//set count as 0.
							count = 0;
							//the player miss the chance and you change the turn.
							if (turn == X)
							{
								turn = O;
								/* call the method to inform server of the 
								 * changed status.
								 */
								setTurn(turn, count);
							} 
							else
							{
								turn = X;
								/* call the method to inform server of the 
								 * changed status.
								 */
								setTurn(turn, count);
							}
						}
						//if the field was already destroyed.
						else
						{
							//you drop the message.
							moveMessage = "Invalid move; Miss Chance";
							//set the count to 0.
							count = 0;
							//the player miss the count and chnage the turn.
							if (turn == X)
							{
								turn = O;
								/* call the method to inform server of the 
								 * changed status.
								 */
								setTurn(turn, count);
							} 
							else
							{
								turn = X;
								/* call the method to inform server of the 
								 * changed status.
								 */
								setTurn(turn, count);
							}
						}
					}
				}
			}
			//check if the other player lost by this player's moves.
			gameOver = checkForLose(turn);
				
			//somebody won.
			if (gameOver == 1)
			{
				//if the turn was X
				if (turn == X)
				{
					//you switch turn to O.
					turn = O;
					/* call the method to inform server of the 
					 * changed status.
					 */
					setTurn(turn, count);
					//set the display info to Blue.
					turnInfo = "Blue";
				}
				//if the turn was O.
				else
				{
					//switch the turn to X.
					turn = X;
					/* call the method to inform server of the 
					 * changed status.
					 */
					setTurn(turn, count);
					//set the display info to Black.
					turnInfo = "Black";
				}
				//display the name of the winner.
				moveMessage = turnInfo + " wins!!!";
			}
			//since a move was made we need to refresh the display.
			repaint();
		}
	}
	
	/**
	 * Send the x and y co-ordinates of the mouse click
	 * event along with the player playing the turn.
	 * 
	 * @param x, y, turn
	 * @throws RemoteException
	 * 
	 */
	public void getStatus(int x, int y, char turn) throws RemoteException
	{
		//call the remote server method to send the co-ordinates.
		rmiServer.getXandY(x, y, turn);
	}

	/**
	 * If the move method makes certain changes in the 
	 * state of the turns, we pass the status to the server.
	 * 
	 * @param turn, count
	 * @throws RemoteException
	 * 
	 */
	public void setTurn(char turn, int count) throws RemoteException
	{
		//call the server remote method to send status.
		rmiServer.getTurn(turn, count);
	}
}

