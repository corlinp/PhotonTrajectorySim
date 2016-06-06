package utils;

import java.util.Arrays;

public class Pr {
	public static void main(String[] args) {
		double[] yolo = new double[]{243.453453,432,123.657756,543,1324.234342,435,656,12,243,544.283};
		double[][] d = new double[20][yolo.length];
		for(int i = 0; i < 20; i++){
			d[i] = yolo;
		}
		prs(d);
	}

	public static void pr(String s){
		System.out.println(s);
	}
	public static void pr(char s){
		System.out.println(s);
	}
	public static void pr(int s){
		System.out.println(s);
	}
	public static void pr(long s){
		System.out.println(s);
	}
	public static void pr(double s){
		System.out.println(s);
	}
	//print a rounded double
	public static void prr(double s){
		System.out.println(round(s));
	}	
	public static void pr(float s){
		System.out.println(s);
	}
	public static void pr(byte s){
		System.out.println(s);
	}
	public static void pr(short s){
		System.out.println(s);
	}
	public static void pr(boolean s){
		System.out.println(s);
	}
	public static void pr(char[] s){
		System.out.println(Arrays.toString(s));
	}
	public static void pr(int[] s){
		System.out.println(Arrays.toString(s));
	}
	public static void pr(long[] s){
		System.out.println(Arrays.toString(s));
	}
	public static void pr(double[] s){
		System.out.println(Arrays.toString(s));
	}
	public static void prr(double[] s){
		StringBuilder sb = new StringBuilder("[");
		for(Double d : s){
			sb.append(round(d)+", ");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		System.out.println(sb.toString());
	}
	public static void pr(float[] s){
		System.out.println(Arrays.toString(s));
	}
	public static void pr(byte[] s){
		System.out.println(Arrays.toString(s));
	}
	public static void pr(boolean[] s){
		System.out.println(Arrays.toString(s));
	}
	public static void pr(short[] s){
		System.out.println(Arrays.toString(s));
	}
	public static void pr(Object[] s){
		System.out.println(Arrays.toString(s));
	}
	public static void pr(Object s){
		System.out.println(s);
	}
	private static String round(double in){
		double ovr = Math.pow(10, 3);
		String s = String.valueOf(Math.round(in*ovr) / ovr);
		if(s.endsWith(".0")){
			s = s.substring(0, s.length()-2);
		}
		return s;
	}
	/**
	 * Print - short: This will cut out some middle entries in the array
	 * and replace them with ...
	 */
	public static void prs(double[] s){
		System.out.println(shorten(s));
	}
	private static String shorten(double[] s){
		if(s.length > 6){
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(round(s[0]) + ", ");
			sb.append(round(s[1]) + ", ");
			sb.append(round(s[2]) + ", ... , ");
			sb.append(round(s[s.length-2]) + ", ");
			sb.append(round(s[s.length-1]) + "]");
			return sb.toString();
		}
		else{
			return (Arrays.toString(s));
		}
	}
	public static void prs(double[][] s){
		if(s.length > 6){
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(shorten(s[0]) + "\n");
			sb.append(shorten(s[1]) + "\n");
			sb.append(shorten(s[2]) + "\n");
			sb.append(" ... \n");
			sb.append(shorten(s[s.length-2]) + "\n");
			sb.append(shorten(s[s.length-1]) + "]");
			System.out.println(sb);
		}
		else{
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(double[] d : s){
				sb.append(Arrays.toString(d));
			}
			sb.append("]");
		}
	}
	public static void prs(int[] s){
		System.out.println(shorten(s));
	}
	private static String shorten(int[] s){
		if(s.length > 6){
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(s[0] + ", ");
			sb.append(s[1] + ", ");
			sb.append(s[2] + ", ... , ");
			sb.append(s[s.length-2] + ", ");
			sb.append(s[s.length-1] + "]");
			return sb.toString();
		}
		else{
			return (Arrays.toString(s));
		}
	}
	public static void prs(int[][] s){
		if(s.length > 6){
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(shorten(s[0]) + "\n");
			sb.append(shorten(s[1]) + "\n");
			sb.append(shorten(s[2]) + "\n");
			sb.append(" ... \n");
			sb.append(shorten(s[s.length-2]) + "\n");
			sb.append(shorten(s[s.length-1]) + "]");
			System.out.println(sb);
		}
		else{
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(int[] d : s){
				sb.append(Arrays.toString(d));
			}
			sb.append("]");
		}
	}
}

















