
public class Vars {
	public static int PHOTON_TRAIL_LENGTH = 100;
	public static int VOLLEY_SIZE = 250;
	public static double TIME_STEP = 0.017; //Time step per frame for photons in sec
	public static int PHOTON_MAX_LIFETIME = 1800;
	public static double METERS_PER_PIXEL = 804672; //roughly 500 miles per pixel
	public static double PIXLES_PER_METER = 1.0 / METERS_PER_PIXEL;
	
	public static double C = 2.998E8; //speed of light in meters per sec
	public static double VISIBLE_LIGHT_FREQUENCY = 5E12; //in Hz
	public static double GRAVITATIONAL_CONSTANT = 6.674E-11; //in newton meters^2 per kg^2 OR meters cubed per second squared kilogram
	public static double PLANCKS_CONSTANT = 6.62607E-34; //in Joule Seconds or kg m^2 / s
}
