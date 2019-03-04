package User;

/** Author: Orkun
 * Date: 03.03.2019
 * This is the UI element which prompts some decisions before the game starts
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import UI.Button;
import UI.Pad;

public class MenuPad extends Pad {
	
	private int USER_GO_FIRST_BUTTON_ID = 0, AI_GO_FIRST_BUTTON_ID = 0;
	
	private ArrayList<Button> buttons;
	private int padding = 20;
	private Button.OnClickListener buttonOnClickListener;
	private String current_text = "WHO GOES FIRST?";
	private Font font;
	
	/**Set the horizontal and vertical position on the screen
	 */
	public MenuPad(int x, int y) {
		super(x, y);
		initTools();
		initUI();
	}

	private void initTools() {
		buttons = new ArrayList<Button>();
		
		buttonOnClickListener = new Button.OnClickListener() {
			@Override
			public void onClick(int id) {
				if(id == USER_GO_FIRST_BUTTON_ID) {
					alertListener.onAlert("user");
				}else if(id == AI_GO_FIRST_BUTTON_ID) {
					alertListener.onAlert("ai");
				}
			}
		};
	}
	
	private void initUI() {
		USER_GO_FIRST_BUTTON_ID = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (padding), super.pos_y - (65 + padding), 610, 50).setText("YOU").setFont(new Font("Verdana", Font.BOLD, 32)).setID(USER_GO_FIRST_BUTTON_ID).setOnClickListener(buttonOnClickListener).addHint("First Guess is Yours"));
		AI_GO_FIRST_BUTTON_ID = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (padding), super.pos_y, 610, 50).setText("AI").setFont(new Font("Verdana", Font.BOLD, 32)).setID(AI_GO_FIRST_BUTTON_ID).setOnClickListener(buttonOnClickListener).addHint("First Guess is AI's"));
	}

	/**Set padding between each button
	 */
	public MenuPad setPadding(int padding) {
		this.padding = padding;
		return this;
	}
	
	/**Font of the displayed text on screen
	 * 
	 */
	public MenuPad setFont(Font font) {
		this.font = font;
		return this;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(!super.visible) {
			return;
		}
		
		g.setFont(this.font);
		g.setColor(Color.DARK_GRAY);
		g.drawString(current_text, super.pos_x, super.pos_y - 120);
		for (Button button : buttons) {
			button.render(g);
		}
	}
	
	@Override
	public void tick(int action, int mouse_x, int mouse_y) {
		if(!visible) {
			return;
		}
		for (Button button : buttons) {
			button.tick(action, mouse_x, mouse_y);
		}
	}

}
