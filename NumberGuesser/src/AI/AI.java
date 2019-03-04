package AI;

/** Author: Orkun
 * Date: 01.03.2019
 * This class calculates the possible answers of the user's guess // memory heavy approach
 */

import java.util.ArrayList;
import java.util.Random;

public class AI {
	
	private ArrayList<String> possibilities, guessHistory; // holds the rest of the possibilities and user's previous guesses
	private int max_digits, max_numbers; // to make things expendable receive max digits and max number per digit

	/** Artificial Intelligence to guess n unique digit number
	 */
	public AI(int max_digits, int max_numbers) {
		this.max_digits = max_digits;
		this.max_numbers = max_numbers;
		
		init();
	}
	
	private void init() {
		// initialize
		possibilities = new ArrayList<String>();
		guessHistory = new ArrayList<String>();
		
		setPossibilities();
	}
	
	private void setPossibilities() {
		for (int i = 0; i < this.max_numbers; i++) {
			String num = "";
			num += (i+1)+"";
			for (int j = 0; j < this.max_numbers; j++) {
				String second = num;
				if(!second.contains((j+1)+"")){
					second += (j+1)+"";
				}
				for (int m = 0; m < this.max_numbers; m++) {
					String third = second;
					if(!third.contains((m+1)+"")){
						third += (m+1)+"";
					}
					for (int k = 0; k < this.max_numbers; k++) {
						String fourth = third;
						if(!fourth.contains((k+1)+"")){
							fourth += (k+1)+"";
							if(fourth.length() == 4) {
								possibilities.add(fourth);
							}
						}
					}
				}
			}
		}
		
		//System.out.println(possibilities.toString());
		System.out.println("Total of possibilities: " + possibilities.size());
	}
	
	/** Guess a random number within rest of the possibilities
	 */
	public String guess() {
		if(possibilities.size() == 0){
			return "found";
		}
		String random_guess = possibilities.get(new Random().nextInt(possibilities.size()));
		guessHistory.add(random_guess);
		System.out.println("AI's next guess: " + random_guess);
		return random_guess;
	}
	
	/** Receive a hint about the previous guess and filter out the possibilities
	 */
	public void giveHint(String hint) {
		filter(guessHistory.get(guessHistory.size()-1), hint);
		//guess();
	}
	
	private void filter(String guess, String hint) {
		possibilities.remove(guess);
		int prev_posses = possibilities.size();
		System.out.println("Before filter: " + possibilities.toString());
		
		int filter_pre_zero = 0;
		int filter_post_zero = 0;
		for (int i = 0; i < possibilities.size(); i++) {
			boolean searchRest = true;
			String current = possibilities.get(i);
				int h1 = Integer.parseInt(hint.charAt(0) + "");
				int h2 = Integer.parseInt(hint.charAt(1) + "");
				/*filter non similar*/
					int similarities = 0;
					for (int k = 0; k < this.max_digits; k++) {
						if(current.contains(guess.charAt(k)+"")) {
							similarities++;
							// get how many digits are similary
						}
					}
					if(similarities < h1+h2) {
						// if similarities below the hint point discard the possibility
						possibilities.remove(i);
						i--;
						searchRest = false;
					}
					
				/*filter pre zero where else of that digit cant be the correct place*/
				if(h1 == 0 && searchRest) {
					for (int k = 0; k < guess.length(); k++) {
						if((current.charAt(k)+"").equals(guess.charAt(k)+"")) {
							// since this digit cannot be in this place discard the possibility
							possibilities.remove(i);
							filter_pre_zero++;
							i--;
							searchRest = false;
							//break
							k = guess.length();
						}
					}
				}
				
				/*filter post zero where that digit cant be the correct place*/
				if(h2 == 0 && searchRest) {
					for (int k = 0; k < guess.length(); k++) {
						for (int j = 0; j < current.length(); j++) {
							if(k != j && (current.charAt(j)+"").equals(guess.charAt(k)+"")) {
								// since this digit cannot be in this place discard the possibility
								possibilities.remove(i);
								filter_post_zero++;
								i--;
								searchRest = false;
								//break
								k = guess.length();
								j = current.length();
							}
						}
					}
				}
		}
		
		System.out.println("After filter: " + possibilities.toString());
		System.out.println("Total of possibilities: " + possibilities.size() + " [eliminated " + (prev_posses - possibilities.size()) + "] [pre_zero: " + filter_pre_zero + "] [post_zero: " + filter_post_zero + "]");
	}
	
	/** Clear data and create new possibilities previously for the next turn
	 * 
	 */
	public void reset() {
		possibilities.clear();
		guessHistory.clear();
		setPossibilities();
	}
	
}
