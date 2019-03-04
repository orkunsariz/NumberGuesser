package UI;

/** Author: Orkun
 * Date: 03.03.2019
 * The main parent of every element can be displayed on the screen
 */

import java.awt.Graphics;

public abstract class Drawable {
	
	public static final int ACTION_MOVE = 0;
	public static final int ACTION_PRESS = 1;
	public static final int ACTION_RELEASE = 2;
	
	public static boolean VISIBLE = true;
	public static boolean INVISIBLE = false;
	
	protected int pos_x = 0, pos_y = 0;
	protected int width = 0, height = 0;
	
	protected boolean visible = true;
	protected boolean collide = true;
	
	protected int vert_anim_padding = 0;
	
	/**Set position and size of the drawable
	 * 
	 * @param x
	 * x position relative to screen in pixels
	 * @param y
	 * y position relative to screen in pixels
	 * @param width
	 * width of the button in pixels
	 * @param height
	 * height of the button in pixels in pixels
	 */
	public Drawable(int pos_x, int pos_y, int width, int height) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
	}
	
	/**Set position of the drawable
	 * 
	 * @param x
	 * x position relative to screen in pixels
	 * @param y
	 * y position relative to screen in pixels
	 */
	public Drawable(int pos_x, int pos_y) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
	}
	
	public Drawable() {
		
	}
	
	/**Set the visibility of the drawable
	 * 
	 * @param state
	 * If set to false it will not be displayed on the screen
	 * @return
	 */
	public Drawable setVisibility(boolean state) {
		this.visible = state;
		return this;
	}
	
	/**Render the drawable on the screen
	 * 
	 * @param g
	 * Graphics component of the Canvas
	 */
	public void render(Graphics g) {
		
	}
	
	/**Apply some collision detection over the button
	 * 
	 * @param action
	 * Action include mouse press, release, hover and so on
	 * @param mouse_x
	 * Horizontal position of the mouse over the Window
	 * @param mouse_y
	 * Vertical position of the mouse over the Window
	 */
	public void tick(int action, int mouse_x, int mouse_y) {
		
	}

}
