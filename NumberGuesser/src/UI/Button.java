package UI;

/** Author: Orkun
 * Date: 02.03.2019
 * This class is a basic button with click, hover and hint properties
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Button extends Drawable{
	
	protected int id = 0;
	
	private String text = "";
	private int text_x = 0, text_y = 0;
	
	private Font font;
	
	private Color defaultColor = Color.GRAY;
	private Color hoverColor = Color.DARK_GRAY;
	
	private boolean hovered = false;
	private Hint hint = null;

	private OnClickListener onClickListener;

	/**
	 * @param x
	 * x position relative to screen in pixels
	 * @param y
	 * y position relative to screen in pixels
	 * @param width
	 * width of the button in pixels
	 * @param height
	 * height of the button in pixels in pixels
	 */
	public Button(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	/**Set the text that will be displayed on top of the button
	 * 
	 * @param text
	 * Displayed text on screen
	 * @return
	 * Itself
	 */
	public Button setText(String text) {
		this.text = text;
		return this;
	}

	/**Set the font of the displayed text
	 * 
	 * @param font
	 * Font of the displayed text
	 * @return
	 */
	public Button setFont(Font font) {
		this.font = font;
		return this;
	}
	
	/**Set the default and hover colors of the button
	 * 
	 * @param color
	 * Default color when not hovered with mouse
	 * @param hoverColor
	 * Default color when hovered over the button
	 * @return
	 */
	public Button setColor(Color color, Color hoverColor) {
		this.defaultColor = color;
		this.hoverColor = hoverColor;
		return this;
	}
	
	/**Set the visibility of the button and child elements
	 * @param state
	 * If true button will be displayed
	 * If false button will not be displayed
	 */
	public Button setVisibility(boolean state) {
		this.visible = state;
		this.hovered = false;
		return this;
	}
	
	/**ID is required for order in the button hierarchy
	 * @param state
	 * ID of the button
	 */
	public Button setID(int id) {
		this.id = id;
		return this;
	}
	
	/**Listener for click events
	 * @param state
	 * This will be called when user clicks on the button
	 */
	public Button setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
		return this;
	}
	
	/**Get the ID of the button
	 * 
	 * @return
	 * Id of the button
	 */
	public int getId() {
		return id;
	}
	
	/**Add button info when hovered over with a mouse
	 * 
	 * @param hint
	 * Display text of the hint
	 */
	public Button addHint(String hint) {
		this.hint = new Hint(hint);
		return this;
	}
	
	private void centerText(Graphics g) {
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
		
		this.text_x = super.pos_x + (super.width - (int) bounds.getWidth()) / 2;
		this.text_y = super.pos_y + (int) bounds.getHeight();
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		if(!super.visible) {
			return;
		}
		if(hovered) {
			g.setColor(hoverColor);
		}else {
			g.setColor(defaultColor);
		}
        g.fillRect(super.pos_x, super.pos_y + super.vert_anim_padding, super.width, super.height);
        
        g.setFont(this.font);
		g.setColor(Color.WHITE);
		
		
		centerText(g);
		g.drawString(text, text_x, text_y + super.vert_anim_padding);
		
		if(this.hint != null) {
			hint.render(g);
		}
	}
	
	@Override
	public void tick(int action, int mouse_x, int mouse_y) {
		if(!visible) {
			return;
		}
		super.collide = false;
		if((mouse_x > super.pos_x && mouse_x < super.pos_x + super.width) && (mouse_y > super.pos_y && mouse_y < super.pos_y + super.height)) {
			super.collide = true;
			super.vert_anim_padding = -2;
			
			if(this.hint != null) {
				hint.setVisibility(Drawable.VISIBLE);
				hint.tick(action, mouse_x, mouse_y);
			}
		}else {
			super.vert_anim_padding = 0;
			if(this.hint != null) {
				hint.setVisibility(Drawable.INVISIBLE);
			}
		}
		
		if(super.collide) {
			switch (action) {
			case Drawable.ACTION_MOVE:
				hovered = true;
				break;

			case Drawable.ACTION_PRESS:
				hovered = false;
				break;
			case Drawable.ACTION_RELEASE:
				hovered = true;
				if(onClickListener != null) {
					onClickListener.onClick(this.id);
				}
				break;
			}
		}else {
			hovered = false;
		}
	}
	
	public interface OnClickListener{
		public void onClick(int id);
	}
	
	public class Hint extends Drawable{
		
		private String hint_text;
		private Font font;
		
		public Hint(String text) {
			this.hint_text = text;
			this.setFont(new Font("Verdana", Font.BOLD, 12));
			this.setVisibility(Drawable.INVISIBLE);
		}
		
		/**Set visibility of the hint
		 * 
		 */
		public Hint setVisibility(boolean state) {
			this.visible = state;
			return this;
		}
		
		/**Set font of the hint text
		 */
		public Hint setFont(Font font) {
			this.font = font;
			return this;
		}
		
		@Override
		public void render(Graphics g) {
			super.render(g);
			if(!super.visible) {
				return;
			}
			
			g.setColor(new Color(125, 192, 104, 150));
	        g.fillRect(super.pos_x, super.pos_y-15, (int) (hint_text.length() * 7f), 20);
	        
	        g.setFont(this.font);
			g.setColor(new Color(255, 255, 255, 150));
			g.drawString(hint_text, super.pos_x+7, super.pos_y);
		}
		
		@Override
		public void tick(int action, int mouse_x, int mouse_y) {
			if(super.visible) {
				super.pos_x = mouse_x;
				super.pos_y = mouse_y;
			}
		}
		
	}
}
