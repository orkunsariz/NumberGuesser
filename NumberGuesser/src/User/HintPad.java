package User;

/** Author: Orkun
 * Date: 03.03.2019
 * This is the UI element where user will write down his hints and inform the AI
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import UI.Button;
import UI.Pad;

public class HintPad extends Pad {
	
	private int PRE_NUM_ID = 0, POST_NUM_ID = 0;
	private int PRE_NUM_PLUS = 0, POST_NUM_PLUS = 0;
	private int PRE_NUM_MINUS = 0, POST_NUM_MINUS = 0;
	private int ENTER_ID = 0;
	
	private ArrayList<Button> buttons;
	private int padding = 20;
	private Button.OnClickListener buttonOnClickListener;
	private Font font;
	
	int correctPlace = 0;
	int wrongPlace = 0;

	/**Set the horizontal and vertical position on the screen
	 */
	public HintPad(int x, int y) {
		super(x, y);
		initTools();
		initUI();
	}

	private void initTools() {
		buttons = new ArrayList<Button>();
		
		buttonOnClickListener = new Button.OnClickListener() {
			@Override
			public void onClick(int id) {
				if(id == PRE_NUM_PLUS && (correctPlace + wrongPlace < 4)) {
					buttons.get(PRE_NUM_ID-1).setText(++correctPlace+"");
				}else if(id == PRE_NUM_MINUS && correctPlace > 0) {
					buttons.get(PRE_NUM_ID-1).setText(--correctPlace+"");
				}else if(id == POST_NUM_PLUS && (correctPlace + wrongPlace < 4)) {
					buttons.get(POST_NUM_ID-1).setText(++wrongPlace+"");
				}else if(id == POST_NUM_MINUS && wrongPlace > 0) {
					buttons.get(POST_NUM_ID-1).setText(--wrongPlace+"");
				}else if(id == ENTER_ID) {
					alertListener.onAlert(getOutput());
					reset(false);
				}else if(id == PRE_NUM_ID) {
					correctPlace = 0;
					buttons.get(PRE_NUM_ID-1).setText(correctPlace+"");
				}else if(id == POST_NUM_ID) {
					wrongPlace = 0;
					buttons.get(POST_NUM_ID-1).setText(wrongPlace+"");
				}
			}
		};
	}
	
	private void initUI() {
		PRE_NUM_ID = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (padding), super.pos_y - (65 + padding), 50, 50).setText(correctPlace+"").setFont(new Font("Verdana", Font.BOLD, 32)).setID(PRE_NUM_ID).setOnClickListener(buttonOnClickListener));
		POST_NUM_ID = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (65 + padding) + 30, super.pos_y - (65 + padding), 50, 50).setText(wrongPlace+"").setFont(new Font("Verdana", Font.BOLD, 32)).setID(POST_NUM_ID).setOnClickListener(buttonOnClickListener));

		PRE_NUM_PLUS = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (padding), super.pos_y - (65 + padding) - 30, 50, 25).setText(String.valueOf("\u25b2")).setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16)).setID(PRE_NUM_PLUS).setOnClickListener(buttonOnClickListener));
		PRE_NUM_MINUS = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (padding), super.pos_y - (65 + padding) + 55, 50, 25).setText(String.valueOf("\u25bc")).setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16)).setID(PRE_NUM_MINUS).setOnClickListener(buttonOnClickListener));

		POST_NUM_PLUS = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (65 + padding) + 30, super.pos_y - (65 + padding) - 30, 50, 25).setText(String.valueOf("\u25b2")).setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16)).setID(POST_NUM_PLUS).setOnClickListener(buttonOnClickListener));
		POST_NUM_MINUS = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (65 + padding) + 30, super.pos_y - (65 + padding) + 55, 50, 25).setText(String.valueOf("\u25bc")).setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16)).setID(POST_NUM_MINUS).setOnClickListener(buttonOnClickListener));

		ENTER_ID = buttons.size()+1;
		buttons.add(new Button(super.pos_x + (130+padding) + 30, super.pos_y - (65 + padding), 120, 50).setText("HINT").setFont(new Font("Verdana", Font.BOLD, 32)).setID(ENTER_ID).setOnClickListener(buttonOnClickListener));

	}
	
	/**Set padding between each element
	 */
	public HintPad setPadding(int padding) {
		this.padding = padding;
		return this;
	}
	
	/** Set font of the displayed text on screen
	 */
	public HintPad setFont(Font font) {
		this.font = font;
		return this;
	}
	
	/**Clear all pad data and set visibility
	 */
	public void reset(boolean vis) {
		setVisibility(vis);
		correctPlace = 0;
		wrongPlace = 0;
		buttons.get(PRE_NUM_ID-1).setText(correctPlace+"");
		buttons.get(POST_NUM_ID-1).setText(wrongPlace+"");
	}
	
	@Override
	public String getOutput() {
		return correctPlace + "" + wrongPlace;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(!super.visible) {
			return;
		}
		g.setFont(this.font);
		g.setColor(Color.WHITE);
		g.drawString("+", super.pos_x-20, super.pos_y - 45);
		g.drawString("-", super.pos_x+80, super.pos_y - 45);
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
