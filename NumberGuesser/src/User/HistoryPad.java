package User;

/** Author: Orkun
 * Date: 03.03.2019
 * This is the UI element which includes the user's guesses and hints in order
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import UI.Button;
import UI.Pad;

public class HistoryPad extends Pad {

	private int RESET_BUTTON = 0;
	
	private ArrayList<Button> buttons;
	private ArrayList<HistoryElement> historyElements;
	private Button.OnClickListener buttonOnClickListener;
	private String text = "Your Guesses";
	private Font font;
	
	/**Set the horizontal and vertical position on the screen
	 */
	public HistoryPad(int x, int y) {
		super(x, y);
		initTools();
		initUI();
	}

	private void initTools() {
		buttons = new ArrayList<Button>();
		historyElements = new ArrayList<HistoryElement>();
		
		buttonOnClickListener = new Button.OnClickListener() {
			@Override
			public void onClick(int id) {
				if(id == RESET_BUTTON) {
					alertListener.onAlert("reset");
				}
			}
		};
		
	}
	
	private void initUI() {
		RESET_BUTTON = buttons.size()+1;
		buttons.add(new Button(super.pos_x+54, super.pos_y-46, 60, 25).setText("restart").setText("restart").setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12)).setID(RESET_BUTTON).setColor(Color.DARK_GRAY, Color.GRAY).setOnClickListener(buttonOnClickListener));
	
	}
	
	/**Clear all pad data and set visibility
	 */
	public void reset(boolean vis) {
		setVisibility(vis);
		historyElements.clear();
	}
	
	/**Add guess info and its hint in the history to be displayed right after
	 */
	public void addToHistory(String data) {
		historyElements.add(new HistoryElement(super.pos_x+1, super.pos_y + 8 + historyElements.size() * 16).setText(data).setFont(new Font("Verdana", Font.BOLD, 12)).setHeaderText((historyElements.size()+1+"")));
	}

	/**Font of the displayed text on screen
	 */
	public HistoryPad setFont(Font font) {
		this.font = font;
		return this;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(!super.visible) {
			return;
		}
		
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.setColor(new Color(160, 160, 160));
		g.drawRect(super.pos_x, super.pos_y+7, 150, 220);
		
		g.fillRect(super.pos_x-1, super.pos_y-20, 150+2, 25);
		
		g.setFont(this.font);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(text, super.pos_x+5, super.pos_y);
		for (Button button : buttons) {
			button.render(g);
		}
		
		for (HistoryElement element : historyElements) {
			element.render(g);
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
		for (HistoryElement element : historyElements) {
			element.tick(action, mouse_x, mouse_y);
		}
	}

}
