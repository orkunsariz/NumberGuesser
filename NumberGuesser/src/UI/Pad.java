package UI;

/** Author: Orkun
 * Date: 03.03.2019
 * The main parent of every user interface panel that can be displayed on the screen
 */

public class Pad extends Drawable {
	
	protected Pad.AlertListener alertListener;
	
	/**Decide the position of the panel
	 * 
	 * @param x
	 * @param y
	 */
	public Pad(int x, int y) {
		super(x, y);
	}

	/**This is the output returned by the pad when complete an operation
	 * 
	 * @return
	 * A string variable returned by the pad
	 */
	public String getOutput() {
		return "";
	}
	
	/**Set the callback when pad yields an output out of the class itself
	 * 
	 * @param padListener
	 * Listener object of the pad
	 * @return
	 * Drawable
	 */
	public Drawable setPadListener(Pad.AlertListener padListener) {
		this.alertListener = padListener;
		return this;
	}
	
	/**The callback when pad yields an output inside of the class itself
	 */
	public interface AlertListener {
		public void onAlert(String output);
	}
	
}
