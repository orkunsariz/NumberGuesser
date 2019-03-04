package User;

/** Author: Orkun
 * Date: 03.03.2019
 * This is the UI element which includes the user's guess and its hint 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import UI.Button;
import UI.Pad;

public class HistoryElement extends Pad {

    private int BACK_BUTTON_ID = 0;
	
	private ArrayList<Button> buttons;
	private Button.OnClickListener buttonOnClickListener;
	private String text = "";
	private Font font;
	
	/**Set the horizontal and vertical position on the screen
	 */
	public HistoryElement(int x, int y) {
		super(x, y);
		initTools();
		initUI();
	}

	private void initTools() {
		buttons = new ArrayList<Button>();
		
		buttonOnClickListener = new Button.OnClickListener() {
			@Override
			public void onClick(int id) {
				
			}
		};
	}
	
	private void initUI() {
		BACK_BUTTON_ID = buttons.size()+1;
		buttons.add(new Button(super.pos_x, super.pos_y, 25, 15).setText("").setFont(new Font("Verdana", Font.BOLD, 9)).setID(BACK_BUTTON_ID).setColor(new Color(130, 130, 130), new Color(134, 134, 134)).setOnClickListener(buttonOnClickListener));
	}

	/**Font of the displayed text on screen
	 * 
	 */
	public HistoryElement setFont(Font font) {
		this.font = font;
		return this;
	}
	
	/**Set the number of the history element
	 */
	public HistoryElement setHeaderText(String text) {
		buttons.get(BACK_BUTTON_ID-1).setText(text);
		return this;
	}
	
	/**Set the detailed info of the previosly given hint
	 */
	public HistoryElement setText(String text) {
		this.text = text;
		return this;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(!super.visible) {
			return;
		}
		
		g.setColor(new Color(160, 160, 160));
		g.fillRect(super.pos_x, super.pos_y, 148, 15);
		
		g.setColor(Color.WHITE);
		g.setFont(this.font);
		g.drawString(text, super.pos_x+28, super.pos_y+11);
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
