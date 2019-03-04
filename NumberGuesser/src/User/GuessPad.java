package User;

/** Author: Orkun
 * Date: 03.03.2019
 * This is the UI element where user will write down his guesses and inform the AI
 */

import java.awt.BasicStroke;

/* Author: Orkun
 * Date: 02.03.2019
 * 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import UI.Button;
import UI.Drawable;
import UI.Pad;

public class GuessPad extends Pad {
	
	private int DELETE_ID = 0, ENTER_ID = 0;
	
	private ArrayList<Button> buttons;
	private int button_count = 0;
	private int padding = 15;
	private Button.OnClickListener buttonOnClickListener;
	private String current_text = "";
	private Font font, hint_font;

	/**Set the horizontal and vertical position on the screen and give the maximum button count inside of the numpad
	 */
	public GuessPad(int x, int y, int button_count) {
		super(x, y);
		this.button_count = button_count;
		initTools();
		initUI();
	}
	
	public GuessPad(int x, int y) {
		super(x, y);
		initTools();
		initUI();
	}

	private void initTools() {
		buttons = new ArrayList<Button>();
		
		buttonOnClickListener = new Button.OnClickListener() {
			@Override
			public void onClick(int id) {
				if(id == ENTER_ID && current_text.length() == 4) {
					String output = getOutput();
					deleteAllChar();
					reset(false);
					alertListener.onAlert(output);
					return;
				}else if(id == DELETE_ID && current_text.length() != 0) {
					deleteLastChar();
					buttons.get(ENTER_ID-1).setVisibility(Drawable.INVISIBLE);
					if(current_text.length() == 0) {
						buttons.get(DELETE_ID-1).setVisibility(Drawable.INVISIBLE);
					}
					return;
				}else if(id > 0 && id <= button_count) {
					if(current_text.length() == 4) {
						return;
					}
					
					current_text += id+"";
					
					buttons.get(DELETE_ID-1).setVisibility(Drawable.VISIBLE);
					buttons.get(id-1).setVisibility(Drawable.INVISIBLE);
					if(current_text.length() == 4) {
						buttons.get(ENTER_ID-1).setVisibility(Drawable.VISIBLE);
					}
				}
			}
		};
	}
	
	private void initUI() {
		for (int i = 0; i < button_count; i++) {
			buttons.add(new Button(super.pos_x + (50+padding) * i, super.pos_y, 50, 50).setText((i+1)+"").setFont(new Font("Verdana", Font.BOLD, 32)).setID(i+1).setOnClickListener(buttonOnClickListener));
		}
		DELETE_ID = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (150+padding), super.pos_y - (70 + padding), 50, 50).setText(String.valueOf("\u2b10")).setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25)).setID(DELETE_ID).setOnClickListener(buttonOnClickListener).setVisibility(Drawable.INVISIBLE));
		ENTER_ID = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (220+padding), super.pos_y - (70 + padding), 100, 50).setText("TRY").setFont(new Font("Verdana", Font.BOLD, 32)).setID(ENTER_ID).setOnClickListener(buttonOnClickListener).setVisibility(Drawable.INVISIBLE));
	}
	
	// Delete the last character of the written text by the user
	private int deleteLastChar() {
		int deleted_id = Integer.parseInt(current_text.substring(current_text.length()-1, current_text.length()));
		current_text = current_text.substring(0, current_text.length()-1);
		buttons.get(deleted_id-1).setVisibility(Drawable.VISIBLE);
		return deleted_id;
	}
	
	/**Clear all pad data and set visibility
	 */
	public void reset(boolean vis) {
		setVisibility(vis);
		for (Button button : buttons) {
			button.setVisibility(Drawable.VISIBLE);
		}
		current_text = "";
		buttons.get(DELETE_ID-1).setVisibility(Drawable.INVISIBLE);
		buttons.get(ENTER_ID-1).setVisibility(Drawable.INVISIBLE);
	}
	
	// Delete all of the text that has been written
	private void deleteAllChar() {
		for (int i = 0; i < 4; i++) {
			buttons.get(deleteLastChar()).setVisibility(Drawable.VISIBLE);
		}
	}
	
	/**Padding between each button
	 */
	public GuessPad setPadding(int padding) {
		this.padding = padding;
		return this;
	}
	
	/**Font of the displayed text on screen
	 * 
	 */
	public GuessPad setFont(Font font) {
		this.font = font;
		this.hint_font = new Font("Verdana", Font.BOLD, 18);
		return this;
	}
	
	@Override
	public String getOutput() {
		return current_text;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(!super.visible) {
			return;
		}
		
		g.setColor(Color.WHITE);
        g.fillRect(super.pos_x, super.pos_y-85, 149, 50);
        
        ((Graphics2D) g).setStroke(new BasicStroke(1));
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < 4; i++) {
        	if(i == current_text.length()) {
        		g.fillRect(super.pos_x + 37 * i, super.pos_y-85, 150/4, 50);
        	}else {
        		g.drawRect(super.pos_x + 37 * i, super.pos_y-85, 150/4, 50);
        	}
		}
		
		g.setFont(this.font);
		g.setColor(Color.DARK_GRAY);
		g.drawString(current_text, super.pos_x, super.pos_y - 40);
		
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
