package UI;

/** Author: Orkun
 * Date: 02.03.2019
 * This is the main window where mouse and user events are handeled and displayed
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.omg.IOP.ENCODING_CDR_ENCAPS;
import UI.Canvas.RenderCallback;

public class Window extends JFrame implements MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	// Window title // TODO : display window title if it is undecorated
	private String title = "";
	// Default window width and height
	private int WIDTH = 800;
	private int HEIGHT = 600;
	// Drawable list to render and update
	private ArrayList<Drawable> drawables;
	
	private Canvas.RenderCallback renderCallback;
	
	private Button.OnClickListener buttonOnClickListener;
	private ArrayList<Button> buttons;
	
	private int EXIT_ID = 0, SINK_ID = 0;
	
	private int fps = 0;
	private String fps_displayed = "";

	/**Initialize window with title, width and height
	 * 
	 * @param title
	 * Window title that will be displayed on screen
	 * @param WIDTH
	 * Width of the window
	 * @param HEIGHT
	 * Height of the window
	 */
	public Window(String title, int WIDTH, int HEIGHT) {
		this.title = title; // set title of window
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		
		initTools();
		initUI();
	}
	
	public Window(int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		
		initTools();
		initUI();
	}
	
	public Window() {
		initTools();
		initUI();
	}
	
	// set window attributes
	private void initUI() {
		super.setContentPane(canvas); // add custom canvas to window
		//super.setResizable(false); // make window not resizeable
		super.setTitle(this.title); // TODO : change title and exit operation
		super.setUndecorated(true);
		pack();
		super.setLocationRelativeTo(null); // center to screen
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.addMouseListener(this); // add mouse event listener
		super.addMouseMotionListener(this);
		super.setVisible(true);
		
		EXIT_ID = buttons.size()+1;
		buttons.add(new Button(this.WIDTH - 25 - 4, 4, 25, 25).setText("X").setFont(new Font("Verdana", Font.BOLD, 16)).setID(EXIT_ID).setColor(Color.DARK_GRAY, Color.GRAY).setOnClickListener(buttonOnClickListener));
		SINK_ID = buttons.size()+1;
		buttons.add(new Button(this.WIDTH - 50 - 5, 4, 25, 25).setText("-").setFont(new Font("Verdana", Font.BOLD, 16)).setID(SINK_ID).setColor(Color.DARK_GRAY, Color.GRAY).setOnClickListener(buttonOnClickListener));
	}
	
	// initialize lists and other tools
	private void initTools() {
		buttons = new ArrayList<Button>();
		buttonOnClickListener = new Button.OnClickListener() {
			
			@Override
			public void onClick(int id) {
				if(id == EXIT_ID) {
					System.out.println("EXIT");
					dispatchEvent(new WindowEvent(getFrames()[0], WindowEvent.WINDOW_CLOSING));
				} else if(id == SINK_ID) {
					System.out.println("SINK");
					setExtendedState(getExtendedState() | JFrame.ICONIFIED);
				}
			}
		};
		
		drawables = new ArrayList<Drawable>();
		renderCallback = new Canvas.RenderCallback() {
			
			@Override
			public void render(Graphics g) {
				fps++;
				renderGraphics(g);
			}
		};
		
		canvas = new Canvas(this.WIDTH, this.HEIGHT);
		canvas.setRenderCallback(renderCallback);
		
		// This is a fps measurement thread, every second it resets to 0
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					fps_displayed = fps + "";
					fps = 0;
					repaint();
					//System.out.println("FPS RESET");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	/**Add a drawable to the window draw queue
	 */
	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}
	
	private void renderGraphics(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.WIDTH-1, this.HEIGHT-1);
		g.setColor(Color.DARK_GRAY);
		((Graphics2D) g).setStroke(new BasicStroke(5));
		g.drawRect(0, 0, this.WIDTH-1, this.HEIGHT-1);
		
		g.drawString("render: "  + fps_displayed, 10, this.HEIGHT - 10);
		
		g.setColor(new Color(0, 0, 50));
		g.drawString("CS-TECH | Applicant Assesment Test 1 | ORKUN SARIZ", 470, this.HEIGHT - 10);
		
		for (Button button : buttons) {
			button.render(g);
		}
		
        for (Drawable drawable : drawables) {
			drawable.render(g);
		}
	}
	
	private void update(int action, int mouse_x, int mouse_y) {
		// Update screen elements according to action and mouse positions
		for (Drawable drawable : drawables) {
			drawable.tick(action, mouse_x, mouse_y);
		}
		
		for (Button button : buttons) {
			button.tick(action, mouse_x, mouse_y);
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This will be called when a mouse button is down
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		// System.out.println(event.getX() + " " + event.getY());
		// Update every drawable that is in the list
		update(Drawable.ACTION_PRESS, event.getX(), event.getY());
		// repaint screen with mouse click since we dont need to repaint every so often
		super.repaint();
	}


	/**
	 * This will be called when a mouse button is up
	 */
	@Override
	public void mouseReleased(MouseEvent event) {
		update(Drawable.ACTION_RELEASE, event.getX(), event.getY());
		// repaint screen with mouse click since we dont need to repaint every so often
		super.repaint();
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This will be called when mouse pointer moved on screen
	 */
	@Override
	public void mouseMoved(MouseEvent event) {
		// Render and update hover colors when moved and collided
		update(Drawable.ACTION_MOVE, event.getX(), event.getY());
		super.repaint();
	}

}
