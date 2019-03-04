package AI;

/** Author: Orkun
 * Date: 02.03.2019
 * This class creates a physical interface between AI and the user
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import UI.Pad;

public class AIBridgePad extends Pad {
	
	public String ai_message = "Hello I am AI", ai_guess = "", hint_message = "", hint = "";
	private Font font;

	/** Only AI talks through with this interface
	 */
	public AIBridgePad(int x, int y) {
		super(x, y);
	}
	
	/** Set displayed text font
	 */
	public AIBridgePad setFont(Font font) {
		this.font = font;
		return this;
	}
	
	/** Display a message according to game state
	 */
	public void display(String guess) {
		if(guess.equals("found")) {
			alertListener.onAlert("found");
			setMessage("Your num has to be '" + ai_guess + "'");
		}else {
			ai_guess = guess;
			
			setMessage("IS YOUR NUMBER '" + ai_guess + "' ?");
			if(hint.length() != 0) {
				setHintMessage("YOUR HINT IS " + hint);
			}
		}
	}
	
	/** Set hint directly that will be given to the user with some complementary text
	 * 
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}
	
	/** Set AI's other types of display messages directly
	 * 
	 */
	public void setMessage(String message) {
		ai_message = message;
	}
	
	/** Set AI's hint display messages directly
	 * 
	 */
	public void setHintMessage(String hint) {
		this.hint_message = hint;
	}
	
	/** Reset the pad and its circulating data
	 * 
	 */
	public void reset(boolean vis) {
		setVisibility(vis);
		ai_message = "Hello I am AI";
		ai_guess = "";
		hint_message = "";
		hint = "";
	}

	@Override
	public String getOutput() {
		return ai_message;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(!super.visible) {
			return;
		}
		g.setFont(this.font);
		g.setColor(Color.DARK_GRAY);
		g.drawString(ai_message, super.pos_x, super.pos_y+50);
		
		g.setFont(this.font);
		g.setColor(Color.BLACK);
		g.drawString(hint_message, super.pos_x, super.pos_y);
	}
	
	@Override
	public void tick(int action, int mouse_x, int mouse_y) {
		if(!visible) {
			return;
		}
	}

}
