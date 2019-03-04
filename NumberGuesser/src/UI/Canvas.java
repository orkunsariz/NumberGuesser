package UI;

/** Author: Orkun
 * Date: 02.03.2019
 * This class is a JPanel for drawing basic shapes and text on the screen
 */

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private RenderCallback renderCallback;
	
	public Canvas(int WIDTH, int HEIGHT) {
		super.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	/**This callback is set if you want to manually call paintComponent and reach graphics object
	 * 
	 * @param renderCallback
	 * Callback of the render function
	 */
	public void setRenderCallback(RenderCallback renderCallback) {
		this.renderCallback = renderCallback;
	}
	
	/**Render the basic shapes and text on the screen once
	 * 
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderCallback.render(g);
    }

	public interface RenderCallback {
		public void render(Graphics g);
	}
	
}
