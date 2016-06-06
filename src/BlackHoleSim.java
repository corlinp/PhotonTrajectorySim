import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.math3.util.FastMath;

import animate.AbstractAnimation;
import utils.ColorMapGenerator;
import utils.Pr;

/*
 * This is the main class for the black hole simulation
 * 
 * Simulates N number of photons in discrete time steps
 * 
 * 
 * 
 * Features Ideas:
 *    Scale to show Real-life distances
 *    Click a button to send volley of photons and show them moving at real light speed
 *        Should be around 1 million miles across (around 5 seconds for light)
 *           Maybe say 0.01 au instead
 *           Maybe say 500 miles per pixel
 */
public class BlackHoleSim extends AbstractAnimation{
	/*
	 * More Specifics:
	 *     Center of window will be coordinate 0,0
	 *     Window will be 500 miles per pixel in size
	 */
	public static LinkedList<Photon> photons = new LinkedList<>();
	public static LinkedList<BlackHole> blackHoles = new LinkedList<>();
	public static boolean geometry = false;
	public BlackHoleSim(int w, int h) {
		super(w, h);
		BlackHole bh = new BlackHole(10000); //roughly 20000 solar masses
		windowWidth = w * Vars.METERS_PER_PIXEL;
		windowHeight = h * Vars.METERS_PER_PIXEL;
		lastHeight = h;
		bh.x = windowWidth/2;
		bh.y = windowHeight/2;
		blackHoles.add(bh);
		this.addButton("pause",' ', new Runnable(){
			public void run() {
				BlackHoleSim.this.togglePlay();
			}
		});
		this.addButton("reset",'r', new Runnable(){
			public void run() {
				BlackHoleSim.this.reset();
				if(photons.size()==0)
					blackHoles = new LinkedList<>();
				else
					photons = new LinkedList<>();
			}
		});
		this.addButton("geometry",'g', new Runnable(){
			public void run() {
				geometry=!geometry;
			}
		});
		this.addButton("Refractive Index",'n', new Runnable(){
			public void run() {
				maxN = 1E-100;
				showN=!showN;
			}
		});
		this.addButton("volley",'v', new Runnable(){
			public void run() {
				double inc = (windowHeight/Vars.VOLLEY_SIZE);
				for(int i = 1; i < Vars.VOLLEY_SIZE; i++){
					Photon p = new Photon();
					p.setPos(10, i*inc, Vars.C, 0);
					photons.add(p);
				}
			}
		});
		this.addButton("single",'s', new Runnable(){
			public void run() {
				Photon p = new Photon();
				p.setPos(10, Math.random() * windowHeight, Vars.C, 0);
				photons.add(p);
			}
		});
		this.addButton("GRcompare",'c', new Runnable(){
			public void run() {
				double inc = (windowHeight/50);
				for(int i = 1; i < Vars.VOLLEY_SIZE; i++){
					Photon p = new Photon();
					p.setPos(10, i*inc, Vars.C, 0);
					p.color=Color.green;
					photons.add(p);
				}
				for(int i = 1; i < Vars.VOLLEY_SIZE; i++){
					Photon p = new Photon();
					p.setPos(10, i*inc, Vars.C, 0);
					p.GRcorrection = false;
					p.color=Color.red;
					photons.add(p);
				}
			}
		});
		this.addButton("Black Hole Cloud",'1', new Runnable(){
			public void run() {
				for(double i = 0; i < windowWidth; i+=150*Vars.METERS_PER_PIXEL){
					for(double j = 0; j < windowHeight; j+=150*Vars.METERS_PER_PIXEL){
						BlackHole bh = new BlackHole(0.1);
						bh.x=i+Math.random()*Vars.METERS_PER_PIXEL*200;
						bh.y=j+Math.random()*Vars.METERS_PER_PIXEL*200;
						blackHoles.add(bh);
					}
				}
			}
		});
		this.addButton("LIGO Sim",'2', new Runnable(){
			public void run() {
				if(!ligosim){
					blackHoles = new LinkedList<>();
					photons = new LinkedList<>();
					BlackHole bh = new BlackHole(3000);
					blackHoles.add(bh);
					bh = new BlackHole(3000);
					blackHoles.add(bh);
					ligosim=true;
					frame = 0;
				}
				else{
					ligosim = false;
				}
			}
		});
		//		this.addButton("collisionSim",']', new Runnable(){
		//			public void run() {
		//				photons = new LinkedList<>();
		//				blackHoles = new LinkedList<>();
		//				BlackHole main = new BlackHole(2000);
		//			}
		//		});
		this.addMouseWheelListener(new MouseWheelListener(){
			public void mouseWheelMoved(MouseWheelEvent e) {
				for(BlackHole bh : blackHoles){
					if(bh.shape.contains(e.getPoint())){
						bh.setMass(bh.massinsolarmasses - bh.massinsolarmasses*e.getWheelRotation()*0.1);
						return;
					}
				}
			}
		});
	}
	double windowWidth, windowHeight;
	int lastHeight = 0;
	boolean ligosim = false;

	/**
	 * Sends a new volley
	 */
	public void spaceAction(){
		double inc = windowHeight/Vars.VOLLEY_SIZE;
		for(int i = 1; i < Vars.VOLLEY_SIZE; i++){
			Photon p = new Photon();
			p.setPos(10, i*inc, Vars.C, 0);
			photons.add(p);
		}
	}

	public void mouseAction(MouseEvent e){
		if(e.getClickCount() == 2){
			for(BlackHole bh : blackHoles){
				if(bh.shape.contains(e.getPoint())){
					double inc = 360.0/Vars.VOLLEY_SIZE;
					for(double i = 0; i < 360; i+=inc){
						double radian = i* (Math.PI*2/360);
						double radius = bh.radius*5 + bh.radius*0.5*(Math.random()-0.5);
						Photon p = new Photon();
						p.setPos(bh.x+radius*Math.cos(radian),
								bh.y+radius*Math.sin(radian), 
								-Math.sin(radian-0.75)*Vars.C, 
								Math.cos(radian-0.75)*Vars.C);
						photons.add(p);
					}
					return;
				}
			}
			double inc = 360.0/Vars.VOLLEY_SIZE;
			for(double i = 0; i < 360; i+=inc){
				double rad = i* (Math.PI*2/360);
				Photon p = new Photon();
				p.setPos(e.getX()*Vars.METERS_PER_PIXEL, 
						e.getY()*Vars.METERS_PER_PIXEL, 
						Math.cos(rad)*Vars.C, Math.sin(rad)*Vars.C);
				photons.add(p);
			}
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			for(BlackHole bh : blackHoles){
				if(bh.shape.contains(e.getPoint())){
					blackHoles.remove(bh);
					return;
				}
			}
			BlackHole bh = new BlackHole(10000); //roughly 20000 solar masses
			bh.x = e.getX()*Vars.METERS_PER_PIXEL;
			bh.y = e.getY()*Vars.METERS_PER_PIXEL;
			blackHoles.add(bh);
		}

	}


	public static void main(String[] args) {
		BlackHoleSim bs = new BlackHoleSim(1400,1000);
		bs.runInFrame();
		bs.setBackground(Color.black);

	}
	public static double sqr(double d){
		return d*d;
	}
	static Color[] palette = ColorMapGenerator.generate(200, ColorMapGenerator.hot);
	static double maxN = 0.000001;
	boolean showN = false;
	int nskip = 6;
	boolean neverDie = false;
	public void draw(Graphics2D g, long frame) {
		if(getHeight() != lastHeight){
			windowWidth = getWidth() * Vars.METERS_PER_PIXEL;
			windowHeight = getHeight() * Vars.METERS_PER_PIXEL;
		}
		if(showN)
			for(int i = 0; i < getWidth(); i+=nskip){
				for(int j = 0; j < getHeight(); j+=nskip){
					double gp = 0;
					for(BlackHole bh : BlackHoleSim.blackHoles){
						//calculate r in meters
						double r = Photon.mag((i*Vars.METERS_PER_PIXEL - bh.x),
								(j*Vars.METERS_PER_PIXEL - bh.y));
						//calculate gravitational potential in m^2 / s^2
						gp += -(Vars.GRAVITATIONAL_CONSTANT * bh.mass) / r;
					}
					double n = 1.0/(1 + (2*gp)/(Vars.C*Vars.C)) - 1;
					boolean neg = false;
					if(n < 0)
						neg=true;
					n=Math.log((n));
					//Pr.pr(n);
					int idx = (int)Math.round(n/maxN*palette.length);
					if(idx >= palette.length){
						idx = palette.length-1;
					}
					if(idx <= 0){
						idx = 0;
					}
					//Pr.pr(idx);
					Color c = palette[idx];
					if(neg)
						c=Color.white;
					g.setColor(c);
					g.fillRect(i-(nskip+1)/2, j-(nskip+1)/2, nskip, nskip);
					if(n > maxN)
						maxN=n;
				}
			}
		if(ligosim){
			BlackHole bh1 = blackHoles.get(0);
			BlackHole bh2 = blackHoles.get(1);
			double r = Vars.METERS_PER_PIXEL * 200 - frame * Vars.METERS_PER_PIXEL * 0.15;
			if(r < 4)
				ligosim = false;
			double theta = frame * frame * 0.00002 + frame*0.005;
			double bhx = r*FastMath.cos(theta);
			double bhy =  r*FastMath.sin(theta);
			bh1.x=bhx + windowWidth/2; bh2.x=-bhx + windowWidth/2;
			bh1.y=bhy + windowHeight/2; bh2.y=-bhy + windowHeight/2;
		}
		Iterator<BlackHole> bhit = blackHoles.iterator();
		while(bhit.hasNext()){
			BlackHole bh =bhit.next();
			if(this.isMousePressed && 
					bh.shape != null && 
					bh.shape.contains(new Point2D.Double(mouseX, mouseY))){
				bh.x = this.mouseX*Vars.METERS_PER_PIXEL;
				bh.y  = this.mouseY*Vars.METERS_PER_PIXEL;
			}
			bh.draw(g);
		}

		Iterator<Photon> it = photons.iterator();
		while(it.hasNext()){
			Photon p =it.next();
			if(!neverDie && 
					(p.lifetime > Vars.PHOTON_MAX_LIFETIME || 
							(Math.abs(p.getX())>windowWidth*1.5 || 
									Math.abs(p.getY())>windowHeight*1.5) || 
							p.lastN > 5000000)){

				it.remove();
			}
			else{
				p.draw(g);
			}
		}
		g.setColor(Color.white);
		g.drawString(""+photons.size(), 0, getHeight());
	}

}



















