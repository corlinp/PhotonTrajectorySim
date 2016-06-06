package utils;

import java.awt.Color;
import java.awt.Graphics2D;

import utils.Pr;
import animate.AbstractAnimation;

public class ColorMapGenerator {
	public static final Color[] sunrise = new Color[]{new Color(70,10,40), new Color(150,15,135), new Color(250,5,35), new Color(255,180,135), new Color(255,245,50),new Color(255,255,255)};
	public static final Color[] earth = new Color[]{new Color(89,99,30),new Color(142,161,6), new Color(240,198,0),  new Color(255,144,0), new Color(219,88,0)};
	public static final Color[] change = new Color[]{new Color(1,28,38),new Color(88,140,140), new Color(244,203,137),  new Color(217,4,43), new Color(107,12,34)};
	public static final Color[] arctico = new Color[]{new Color(48,66,105),new Color(145,190,212), new Color(217,232,245),  new Color(255,255,255), new Color(242,97,1)};
	public static final Color[] sunset = new Color[]{new Color(0,0,0,0), new Color(46,9,39,180),new Color(217,0,0), new Color(255,45,0),  new Color(255,140,0), Color.WHITE};
	public static final Color[] rainbow = new Color[]{new Color(255,0,255),new Color(0,0,255), new Color(0,255,255), new Color(0,255,0), new Color(255,255,0), new Color(255,0,0)};
	public static final Color[] tweens = new Color[]{new Color(0,0,0), new Color(0,255,255), new Color(255,0,255), new Color(255,255,0), new Color(255,255,255)};
	public static final Color[] hot = new Color[]{new Color(0,0,0,0), new Color(255,0,0), new Color(255,255,0), new Color(255,255,255)};
	
	public static final Color[] sexy = new Color[]{new Color(20,10,0), new Color(50,10,60), new Color(175,0,120), new Color(255,0,0), new Color(255,140,0), new Color(255,255,0), new Color(255,255,255)};

	public static final Color[] cold = new Color[]{new Color(0,0,0), new Color(0,0,255), new Color(0,255,255), new Color(255,255,255)};

	public static void main(String[] args) {
		Color[] palette = generate(sunset,255);
		AbstractAnimation aa = new AbstractAnimation(palette.length,400){
			private static final long serialVersionUID = 1L;
			public void draw(Graphics2D g, long frame) {
				for(int i = 0; i < palette.length; i++){
					g.setColor(palette[i]);
					g.drawLine(i, 0, i, getHeight());
				}
			}
		};
		aa.runInFrame();
	}
	
	public static Color[] generate(Color[] in, int lenEach){
		Color[] out = new Color[lenEach * (in.length-1) + 1];
		//Pr.pr(out.length);
		//double lenEach = Math.floor((length-1.0)/(in.length-1));
		double leech = (double)lenEach;
		int outPos = 0;
		for(int i = 0; i < in.length-1; i++){
			double rf = (in[i+1].getRed() - in[i].getRed())/leech;
			double gf = (in[i+1].getGreen() - in[i].getGreen())/leech;
			double bf = (in[i+1].getBlue() - in[i].getBlue())/leech;
			double af = (in[i+1].getAlpha() - in[i].getAlpha())/leech;
			for(int c = 0; c < lenEach; c++){
				int r = (int)Math.round(in[i].getRed() + c*rf);
				int g = (int)Math.round(in[i].getGreen() + c*gf);
				int b = (int)Math.round(in[i].getBlue() + c*bf);
				int a = (int)Math.round(in[i].getAlpha() + c*af);
				out[outPos] = new Color(r,g,b,a);
				outPos++;
				//Pr.pr(r+","+g+","+b+","+outPos);
			}
		}
		out[out.length-1]=in[in.length-1];
		return out;
	}
	public static Color[] generate(int length, Color[] in){
		Color[] out = new Color[length];
		//Pr.pr(out.length);
		double lenEach = Math.floor((length-1.0)/(in.length-1));
		double leech = (double)lenEach;
		int outPos = 0;
		for(int i = 0; i < in.length-1; i++){
			double rf = (in[i+1].getRed() - in[i].getRed())/leech;
			double gf = (in[i+1].getGreen() - in[i].getGreen())/leech;
			double bf = (in[i+1].getBlue() - in[i].getBlue())/leech;
			double af = (in[i+1].getAlpha() - in[i].getAlpha())/leech;
			for(int c = 0; c < lenEach; c++){
				int r = (int)Math.round(in[i].getRed() + c*rf);
				int g = (int)Math.round(in[i].getGreen() + c*gf);
				int b = (int)Math.round(in[i].getBlue() + c*bf);
				int a = (int)Math.round(in[i].getAlpha() + c*af);
				out[outPos] = new Color(r,g,b,a);
				outPos++;
				//Pr.pr(r+","+g+","+b+","+outPos);
			}
		}
		out[out.length-1]=in[in.length-1];
		return out;
	}

}
