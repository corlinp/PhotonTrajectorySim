import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import utils.ColorMapGenerator;

public class BlackHole {
	static Color[] palette = ColorMapGenerator.generate(20, 
			ColorMapGenerator.sunset);

	public double x,y; //in meters
	public double mass, massinsolarmasses; //in kg
	public double radius = 0; //in meters

	public BlackHole(double m){
		setMass(m);
	}
	
	public void setMass(double m){
		massinsolarmasses=m;
		mass = m*1.988435E30;
		radius = (mass * Vars.GRAVITATIONAL_CONSTANT)/(Vars.C * Vars.C);
	}

	Ellipse2D.Double shape = null;
	public void draw(Graphics2D g2d){
		double x = this.x*Vars.PIXLES_PER_METER;
		double y = this.y*Vars.PIXLES_PER_METER;
		double pxRad = radius * Vars.PIXLES_PER_METER;
		if(pxRad < 1)
			pxRad = 1;
		//System.out.println("pxrad is " + pxRad);
		
		g2d.setColor(Color.darkGray);
		shape = new Ellipse2D.Double(x-pxRad*2, y-pxRad*2, pxRad*4, pxRad*4);
		g2d.fill(shape);
//		for(int i = 0; i < palette.length; i++){
//			g2d.setColor(palette[palette.length-i-1]);
//			double growth = pxRad * 0.05 * i;
//			g2d.fill(new Ellipse2D.Double(x-pxRad*2+growth, y-pxRad*2+growth, 
//					pxRad*4-growth*2, pxRad*4-growth*2));
//		}
		
		
		//g2d.setColor(Color.green);
		//g2d.draw(new Ellipse2D.Double(x-pxRad*3, y-pxRad*3, pxRad*6, pxRad*6));
	}
}
