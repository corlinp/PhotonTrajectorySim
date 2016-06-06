import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.math3.util.FastMath;

import utils.ColorMapGenerator;
import utils.NumUtil;
import utils.Pr;

public class Photon {
	static Color[] palette = ColorMapGenerator.generate(Vars.PHOTON_TRAIL_LENGTH, 
			ColorMapGenerator.sunset);
	public Color color = null;
	public LinkedList<PhotonVector> pos = new LinkedList<>();
	public int lifetime = 0;
	public boolean GRcorrection = true;

	public double getX(){
		return pos.getFirst().pos[0];
	}
	public double getY(){
		return pos.getFirst().pos[1];
	}
	public double getXV(){
		return pos.getFirst().vel[0];
	}
	public double getYV(){
		return pos.getFirst().vel[1];
	}
	public void setPos(double x, double y, double xv, double yv){
		PhotonVector pv = new PhotonVector(x,y,xv,yv);
		pos.addFirst(pv);
		if(lastN == 0){
			lastN = calcN();
		}
		if(pos.size() > Vars.PHOTON_TRAIL_LENGTH){
			pos.removeLast();
		}
	}
	public static double sqr(double d){
		return d*d;
	}
	public static double mag(double a,double b){
		return Math.sqrt(sqr(a) + sqr(b));
	}
	public static double mag(double[] a){
		return Math.sqrt(sqr(a[0]) + sqr(a[1]));
	}
	public static double cross(double[] a,double[] b){
		return (a[0]*b[1])-(b[0]*a[1]);
	}

	public double calcN(){
		double gp = calcGP();
		//calculate the index of refraction
		double n = 1.0/(1 + (2*gp)/sqr(Vars.C));
		//Pr.pr(n + " and " + gp);
		return n;
	}
	
	public double calcGP(){
		double gp = 0;
		PhotonVector pv = pos.getFirst();
		for(BlackHole bh : BlackHoleSim.blackHoles){
			//calculate r in meters
			double r = mag((pv.pos[0] - bh.x),(pv.pos[1] - bh.y));
			//calculate gravitational potential in m^2 / s^2
			gp += -(Vars.GRAVITATIONAL_CONSTANT * bh.mass) / r;
			//calculating angular velocity
			double[] normalVec = new double[]{pv.pos[0]-bh.x, pv.pos[1]-bh.y};
			double cross = cross(pv.vel,normalVec);
			double thetasin = ((cross)/(mag(pv.vel)*mag(normalVec)));

			double alpha = mag(pv.vel)*(thetasin)/r;
			double h = sqr(r)*alpha;
			//finding the gravitational potential the schwarzchild way
			double v = sqr(h)/(2*sqr(r));
			v*= - ((2*Vars.GRAVITATIONAL_CONSTANT*bh.mass)/(sqr(Vars.C)*r));
			if(GRcorrection)
				gp+=v;
			
			//Pr.pr(v + " and " + gp);
		}
		return gp;
	}

	double lastN=0;
	private ArrayList<Line2D.Double> l2d = new ArrayList<>();
	/*
	 * Calculates the new speed and position
	 */
	private void calc(){
		l2d = new ArrayList<>();
		PhotonVector pv = pos.getFirst();
		double[] oldpos = pv.pos;
		pv.pos = NumUtil.add(pv.pos, NumUtil.multiply(pv.vel, Vars.TIME_STEP*1.0));
		double n = calcN();
		double thetadifftotal = 0;
		for(BlackHole bh : BlackHoleSim.blackHoles){
			//System.out.println("Old n is " + lastN + " and new n is " + n);
			//trying something where we project velocity first
			
			//calculate angle between star normal vector and momentum vector
			//first calculate cross product:
			double[] normalVec = new double[]{pv.pos[0]-bh.x, pv.pos[1]-bh.y};
			if(BlackHoleSim.geometry){
				l2d.add(new Line2D.Double(bh.x*Vars.PIXLES_PER_METER, bh.y*Vars.PIXLES_PER_METER, normalVec[0], normalVec[1]));
				l2d.add(new Line2D.Double(pv.pos[0]*Vars.PIXLES_PER_METER, pv.pos[1]*Vars.PIXLES_PER_METER, pv.vel[0], pv.vel[1]));
			}
			double cross = cross(pv.vel,normalVec);
			double sintheta = (cross)/(mag(pv.vel)*mag(normalVec));
			double theta1 = FastMath.asin(sintheta);
			
			if(theta1 > Math.PI/2)
				theta1 -= Math.PI/2;
			else if(theta1 < -Math.PI/2)
				theta1 += Math.PI/2;
			double theta2 = FastMath.asin((lastN/n)*sintheta);
			if(n < lastN){
				double crit = FastMath.asin(n/lastN);
				if(theta1 > crit || theta1 < -crit){
					theta2 = Math.PI-theta1;
				}
			}
			//calculate Critical angle
			double thetadiff = -theta1+theta2;
			if(n < lastN){
				thetadiff*=-1;
			}
			thetadifftotal += thetadiff;
			
			//Pr.pr("Theta diff "+thetadiff+" make heading goes from " + Arrays.toString(pv.vel) + " to " + Arrays.toString(vnew));
		}
		double sintheta = FastMath.sin(thetadifftotal);
		double costheta = FastMath.cos(thetadifftotal);
		double[] vnew = new double[]{pv.vel[0]*costheta - pv.vel[1]*sintheta, 
				pv.vel[0]*sintheta + pv.vel[1]*costheta};

		double newSpeed = Vars.C/n;

		vnew = NumUtil.multiply(NumUtil.unitVector(vnew),newSpeed);

		//now normalize to new speed

		//now calculate new theta

		//snell's law

		double dx = vnew[0] * Vars.TIME_STEP;
		double dy = vnew[1] * Vars.TIME_STEP;
		
		//delete this?
		
		
		setPos(oldpos[0]+dx,oldpos[1]+dy,vnew[0],vnew[1]);
		//setPos(pv.pos[0]+pv.vel[0]*Vars.TIME_STEP,pv.pos[1]+pv.vel[1]*Vars.TIME_STEP,pv.vel[0],pv.vel[1]);

		lastN = n;
		lifetime++;
	}

	public void draw(Graphics2D g2d){
		//calculate new position from velocities
		//draw each path as a series of 2D lines with different colors for age
		calc();
		if(BlackHoleSim.geometry && l2d != null){
			for(Line2D.Double l : l2d){
				g2d.setColor(Color.green);
				g2d.draw(l);
			}
		}
		//pv's x is given in miles from left and top
		Iterator<PhotonVector> it = pos.iterator();
		int pos = 0;
		double lx=0, ly=0;
		g2d.setStroke(new BasicStroke(1.2f));
		while(it.hasNext()){
			PhotonVector pv = it.next();
			double x = pv.pos[0]*Vars.PIXLES_PER_METER;
			double y = pv.pos[1]*Vars.PIXLES_PER_METER;
			g2d.setColor(palette[palette.length-pos-1]);
			if(color != null)
				g2d.setColor(color);
			if(pos == 0){
				g2d.fill(new Ellipse2D.Double(x-2.0, y-2.0, 4.0, 4.0));
			}
			else{
				g2d.draw(new Line2D.Double(x, y, lx, ly));
			}
			lx=x; ly=y;
			pos++;
		}
		//Pr.pr(x);
	}

	public static class PhotonVector{
		//velocity in meters per second
		public double[] pos, vel;

		public PhotonVector(double x, double y, double xv, double yv) {
			pos = new double[]{x,y};
			vel = new double[]{xv,yv};
		}
	}
}
















