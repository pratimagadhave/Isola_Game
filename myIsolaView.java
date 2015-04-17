/**
 * myIsolaView class extends JFrame and implements
 * serializable interface; is use to set the clients
 * first view when the game starts displaying
 * the board new game button and game rules.
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
import javax.swing.*;
import java.awt.event.*;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * myIsolaView class extends JFrame and implements
 * serializable interface; is use to set the clients
 * first view when the game starts displaying
 * the board new game button and game rules.
 *
 */
public class myIsolaView extends JFrame implements Serializable{
	/**
	 * create the object of myIsolaController.
	 */
	myIsolaController control;
	/**
	 * create the object of JButton as new button.
	 */
	JButton resetButton;
	/**
	 * create the object of JLabel as turn label.
	 */
	JLabel turnLabel;
	/**
	 * create the object of JLabel as rule label.
	 */
	JLabel ruleLabel;

	/**
	 * Override the default constructor of the myIsolaView
	 * to call the super constructor and create the game
	 * board for the client.
	 * 
	 * @throws RemoteException
	 */
	public myIsolaView() throws RemoteException
	{
		//call the constructor of JFrame.
		super("myIsolaView");
		//use the border layout for the display.
		getContentPane().setLayout(new BorderLayout());
		//set the default action for the frame.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set the size of the frame.
		setSize(750, 450);

		//Initialize the myIsolaController instance variable.
		control = new myIsolaController();
		
		//set the title for the frame window.
		setTitle("Isola Player " + control.name);

		//initialize the turn label.
		turnLabel = new JLabel("Turn: ");

		//initialize the rule label. display all the rules.
		ruleLabel = new JLabel("<html>The Game starts with Black.<br><br>" +
				"A move consists of two subsequent actions:<br><br>" +
				"1) Moving one's gem to a neighboring<br>" +
				"	(horizontally, vertically, or diagonally) field<br> " +
				"	that contains a square but not the opponent's gem<br>" +
				"	and also should be undestroyed field<br>" +
				"	(which is not marked with X);<br>" +
				"   only one square movement allowed.<br><br>" +
				"2) Destroying any square with no gem on it.<br>" +
				"	Try blocking the opponent.<br><br>" +
				"Missing on rule is losing your move.<br><br>" +		
				"If a player cannot move any more, he/she loses the game.</html>");	

		//initialize the JButton instance variable as new game.
		resetButton = new JButton("New Game");
		resetButton.setPreferredSize(new Dimension(50, 50));

		//add a listener for the reset button.
		resetButton.addMouseListener( 
				new MouseAdapter() {
					public void mousePressed(MouseEvent e)
					{
						//reset the game.
						try
						{
							//send the reset request.
							control.game.resetRequest();
							//repaint the components.
							repaint();
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
						}
					}
				}
				);

		//add the components to the JFrame.
		getContentPane().add(control.game, BorderLayout.CENTER);
		getContentPane().add(ruleLabel, BorderLayout.EAST);
		getContentPane().add(turnLabel, BorderLayout.NORTH);
		getContentPane().add(resetButton, BorderLayout.SOUTH);
		setVisible(true);
	}
}

