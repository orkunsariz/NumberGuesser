package AI;
import java.util.Random;

/** Author: Orkun
 * Date: 03.03.2019
 * This class holds a unique n digit random number and gives hints about it when received a guess
 */

public class NumberHolder {

	private int max_digit = 0;
	private String number = "";
	private Random random;
	
	/** Construct the class accoring the the digit count
	 */
	public NumberHolder(int max_digit) {
		this.max_digit = max_digit;
		random = new Random();
	}
	

	/** Generate a random unique digit number
	 * 
	 * @return
	 * Returns generated number
	 */
	public String generateRandomNumber() {
		number = "";
		int randomDigit = random.nextInt(9)+1;
		while(number.length() < max_digit) {
			randomDigit = random.nextInt(9)+1;
			if(!number.contains(randomDigit + "")) {
				number += randomDigit;
			}
		}
		System.out.println("AI holding: " + number);
		return number;
	}
	
	/** Recives a guess and returns a hint about it
	 * @param guess
	 * Guess of the user
	 * @return
	 * Hint about the guess [+correct-wrong]
	 */
	public String takeGuess(String guess) {
		System.out.println("User guess is: " + guess);
		int correct = 0;
		int wrong = 0;
		for (int i = 0; i < number.length(); i++) {
			if(number.contains((guess.charAt(i)+""))) {
				if((number.charAt(i)+"").equals((guess.charAt(i)+""))) {
					correct++;
				}else {
					wrong++;
				}
			}
		}
		
		String hint = "+" + correct + "-" + wrong;
		System.out.println("Hint given to the user: " + hint);
		return hint;
	}
	
	/** Reset the data
	 * 
	 */
	public void reset() {
		number = "";
	}
	
}
