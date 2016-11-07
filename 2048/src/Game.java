/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run() {
		// NOTE : recall that the 'final' keyword notes inmutability
		// even for local variables.

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("2048");
		frame.setLocation(500, 200);
		// Main playing area
		final Board board = new Board();
		frame.add(board, BorderLayout.CENTER);

		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);

		final JButton reset = new JButton("Restart Game");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.reset();
			}
		});
		control_panel.add(reset);
		

		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
		board.reset();
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
