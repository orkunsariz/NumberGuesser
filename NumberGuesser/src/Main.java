import java.awt.Font;

import AI.AI;
import AI.AIBridgePad;
import AI.NumberHolder;
import UI.Drawable;
import UI.Pad;
import UI.Window;
import User.GuessPad;
import User.HintPad;
import User.HistoryPad;
import User.MenuPad;

/** Author: Orkun
 * Date: 02.03.2019
 * This class is the entry point of the project
 */

public class Main {
	
	/* ui elements */
	private static Window window;
	private static AIBridgePad aiBridgePad;
	private static GuessPad guessPad;
	private static HintPad hintPad;
	private static MenuPad menuPad;
	private static HistoryPad historyPad;
	/* ai elements*/
	private static NumberHolder nh;
	private static AI ai;
	
	public static void main(String[] args) {
		
		ai = new AI(4, 9); // init ai to guess the user numbers
		nh = new NumberHolder(4); // init ai's random number object
		
		window = new Window("Number Guesser / Orkun Sarýz", 800, 300); // Window to display ui elements
		
		// User gives his guesses via this interface
		guessPad = (GuessPad) new GuessPad(20, 230, 9)
		.setFont(new Font("Verdana", Font.BOLD, 52))
		.setPadListener(new Pad.AlertListener() {
			@Override
			public void onAlert(String output) {
				System.out.println(output);
				
				String userGuessResult = nh.takeGuess(output); // take user's guess and ask the ai to give some hint about it
				
				historyPad.addToHistory(output + " [" + userGuessResult + "]");
				if(userGuessResult.equals("+4-0")) {
					// game is over number is found
					aiBridgePad.setMessage("YOU WON !!!");
					aiBridgePad.setHintMessage("");
					guessPad.setVisibility(Drawable.INVISIBLE);
					return;
				}else {
					// continue with hints
					aiBridgePad.setHint(userGuessResult);
				}
				aiBridgePad.display(ai.guess()); // take ai's guess about user's number and display it on screen
				hintPad.setVisibility(Drawable.VISIBLE);
				guessPad.setVisibility(Drawable.INVISIBLE);
			}
		})
		.setVisibility(Drawable.INVISIBLE);
		
		window.addDrawable(guessPad); // add pad to window
		
		// User gives hints about ai's guesses via this interface
		hintPad = (HintPad) new HintPad(50, 280)
		.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 52))
		.setPadListener(new Pad.AlertListener() {
			@Override
			public void onAlert(String output) {
				System.out.println("User hint is " + output);
				if(output.equals("40")) {
					// game is over number is found
					aiBridgePad.setMessage("AI WON !!!");
					aiBridgePad.setHintMessage("");
					hintPad.setVisibility(Drawable.INVISIBLE);
				}else {
					// continue with hints
					ai.giveHint(output);
					aiBridgePad.setMessage("YOUR TURN, GUESS MY NUMBER");
					aiBridgePad.setHintMessage("");
					hintPad.setVisibility(Drawable.INVISIBLE);
					guessPad.setVisibility(Drawable.VISIBLE);
				}
			}
		})
		.setVisibility(Drawable.INVISIBLE);
		
		window.addDrawable(hintPad); // add pad to window
		
		// AI comments about the game hints and guesses promted via this interface
		aiBridgePad = (AIBridgePad) new AIBridgePad(20, 40)
		.setFont(new Font("Verdana", Font.BOLD, 32))
		.setPadListener(new Pad.AlertListener() {
			@Override
			public void onAlert(String output) {
				System.out.println(output);
				//hintPad.setVisibility(Drawable.INVISIBLE);
			}
		})
		.setVisibility(Drawable.INVISIBLE);
		
		window.addDrawable(aiBridgePad); // add pad to window
		
		// User chooses who goes first in the beginning of the game
		menuPad = (MenuPad) new MenuPad(70, 210)
		.setFont(new Font("Verdana", Font.BOLD, 64))
		.setPadListener(new Pad.AlertListener() {
			@Override
			public void onAlert(String output) {
				if(output.equals("user")) {
					// if user chooses to go first
					aiBridgePad.setMessage("YOUR TURN, GUESS MY NUMBER");
					guessPad.setVisibility(Drawable.VISIBLE);
					aiBridgePad.setVisibility(Drawable.VISIBLE);
					menuPad.setVisibility(Drawable.INVISIBLE);
					historyPad.setVisibility(Drawable.VISIBLE);
				}else if(output.equals("ai")) {
					// if user chooses to go after ai
					aiBridgePad.display(ai.guess());
					hintPad.setVisibility(Drawable.VISIBLE);
					aiBridgePad.setVisibility(Drawable.VISIBLE);
					menuPad.setVisibility(Drawable.INVISIBLE);
					historyPad.setVisibility(Drawable.VISIBLE);
				}
				nh.generateRandomNumber();
			}
		});
				
		window.addDrawable(menuPad); // add pad to window
		
		// To help the user, a list of guesses are given
		historyPad = (HistoryPad) new HistoryPad(630, 50)
		.setFont(new Font("Verdana", Font.BOLD, 18))
		.setPadListener(new Pad.AlertListener() {
			@Override
			public void onAlert(String output) {
				System.out.println(output);
				//Reset all data when user presses 'reset' button
				if(output.equals("reset")) {
					historyPad.reset(false);
					aiBridgePad.reset(false);
					guessPad.reset(false);
					hintPad.reset(false);
					ai.reset();
					nh.reset();
					menuPad.setVisibility(Drawable.VISIBLE);
				}
			}
		})
		.setVisibility(Drawable.INVISIBLE);
						
		window.addDrawable(historyPad); // add pad to window
	}

}
