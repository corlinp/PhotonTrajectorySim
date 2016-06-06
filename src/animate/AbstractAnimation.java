package animate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public abstract class AbstractAnimation extends JPanel{

	public static void main(String[] args) {
		AbstractAnimation aa = new AbstractAnimation(1000,1000){
			public void draw(Graphics2D g, long frame) {
				g.setColor(Color.black);
				int wid = (int)(Math.sin(frame/50.0) * 100.0) + 100;
				//System.out.println(wasd[0]);
				g.fillOval(500, 500, wid, 80);
			}
		};
		aa.addButton("yolo", 'y',new Runnable(){
			public void run() {
				System.out.println("swag");
			}
		});
		aa.runInFrame();
	}

	int w, h;
	public AbstractAnimation(int w, int h){
		this.w=w;this.h=h;
		this.setBackground(Color.white);
		this.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e) {
				mouseY = e.getY();
				mouseX = e.getX();
			}
			public void mouseMoved(MouseEvent arg0) {
			}
		});
		//this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"space");
		AbstractAction spaceAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				spaceAction();
			}
		};
		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"space");
		this.getActionMap().put("space", spaceAction);

		for(int i = 0; i < wasd.length; i++){
			wasd[i] = false;
			int key = i;
			this.getInputMap().put(KeyStroke.getKeyStroke(wasdKeys[key], 0, false),i+"press");
			this.getActionMap().put(i+"press", new AbstractAction(){
				public void actionPerformed(ActionEvent e) {
					wasd[key] = true;
				}
			});
			this.getInputMap().put(KeyStroke.getKeyStroke(wasdKeys[key], 0, true),i+"release");
			this.getActionMap().put(i+"release", new AbstractAction(){
				public void actionPerformed(ActionEvent e) {
					wasd[key] = false;
				}
			});
		}


		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent e) {
				if(!checkButtons(e)){
					mouseAction(e);
				}
				isMousePressed = true;
			}
			public void mouseReleased(MouseEvent arg0) {
				isMousePressed = false;
			}
		});
	}
	public boolean isMousePressed = false;

	public void spaceAction(){
		System.err.println("You have to override spaceAction to get this to do anything");
	}
	public boolean[] wasd = new boolean[8];
	public int[] wasdKeys = new int[]{KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT};

	public void mouseAction(MouseEvent e){
		System.err.println("You have to override mouseAction to get this to do anything");
	}


	private long refresh = 17; //msec per frame
	public long setFPS(int fps){
		refresh = Math.round(1000.0/fps);
		return refresh;
	}
	public static long getRefresh(int fps){
		return Math.round(1000.0/fps);
	}
	private long curTime = 0;
	public int mouseY, mouseX;
	Thread thread = null;

	public void run(){
		thread = new Thread(new Runnable(){
			public void run() {
				while(true){
					curTime = System.currentTimeMillis();
					if(!pause){
						repaint();
						//paintComponent(getGraphics());
						//paintImmediately(0,0,getWidth(),getHeight());
					}
					long wait = refresh - (System.currentTimeMillis() - curTime);
					if(wait > 0){
						try {
							Thread.sleep(wait);
						} catch (InterruptedException e) {
							//this is normal, stops the thread when window closes
							//e.printStackTrace();
						}
					}
				}
			}
		});
		thread.start();
	}

	public void runInFrame(){
		JFrame jf = new JFrame();
		jf.setSize(w, h);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(this);
		jf.setVisible(true);
		jf.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent) {
				thread.interrupt();
			}
		});
		run();
	}
	
	private static final Font buttonFont = new Font("DinPro", 12, Font.PLAIN);
//	private static final Color[] buttonColors = new Color[]{
//			new Color(243,97,68), new Color(243,163,68),
//			new Color(238,233,103),
//			new Color(121,243,68),new Color(68,203,243),
//			new Color(208,103,238)};
	private static final Color[] buttonColors = new Color[]{
			new Color(225,225,225), new Color(185,185,185)};
	ArrayList<OnScreenButton> buttons = null;
	public void addButton(String text, char trigger, Runnable r){
		if(buttons == null)
			buttons = new ArrayList<>();
		text += " ["+trigger+"]";
		double x = 0;
		if(buttons.size() > 0){
			x=buttons.get(buttons.size()-1).square.getX()+buttons.get(buttons.size()-1).square.getWidth();
		}
		buttons.add(new OnScreenButton(x,r,text));
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar(trigger), 0, false),text);
		this.getActionMap().put(text, new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				r.run();
			}
		});
	}
	private class OnScreenButton{
		Rectangle2D.Double square = null;
		String text = "";
		Runnable run = null;
		public OnScreenButton(double x, Runnable r, String t){
			text = t;
			int tlen = t.length()*6+10;
			square = new Rectangle2D.Double(x, 0, tlen, 18);
			run=r;
		}
	}
	private boolean checkButtons(MouseEvent e){
		for(OnScreenButton osb : buttons){
			if(osb.square.contains(e.getPoint())){
				osb.run.run();
				return true;
			}
		}
		return false;
	}

	protected boolean pause = false;
	public void pause(){
		pause = true;
	}
	public void play(){
		pause = false;
	}
	public void togglePlay(){
		pause = !pause;
	}
	public void reset(){
		frame = 0;
	}

	boolean aa = true;
	public void disableAA(){
		aa=false;
	}

	protected long frame = 0;
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		if(aa)
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		draw(g2,frame);
		if(buttons != null){
			for(int i = 0; i < buttons.size(); i++){
				g2.setColor(buttonColors[i%buttonColors.length]);
				OnScreenButton osb = buttons.get(i);
				g2.fill(osb.square);
				g2.setColor(Color.gray);
				g2.draw(osb.square);
				g2.setColor(Color.black);
				g2.drawString(osb.text, (int)osb.square.x+5, 14);
			}
		}
		frame++;
	}

	public abstract void draw(Graphics2D g, long frame);
}










