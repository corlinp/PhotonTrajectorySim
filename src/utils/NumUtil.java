package utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NumUtil {

	public static void main(String []args){
		//		double[] nums = new double[]{1.0,2.0,3.0,7.0,6.0,4.0,3.0,3.0};
		//		double idx = peakIndex(nums);
		//		System.out.println();
		//		for(int i = 0; i < nums.length; i++){
		//			System.out.print(nums[i] + "	");
		//		}
		//		System.out.println();
		//		for(int i = 0; i <= idx*8-1; i++){
		//			System.out.print(" ");
		//		}
		//		System.out.print("^");
		//		System.out.println("\n\n index at " + idx);
		//
		//		System.out.println(analysis(nums));
//		double[][] test = new double[][]{{6,7}, {4,5}};
//		System.out.println(toString(collapse(test)));
		System.out.println(round(123.15234543,2));
	}
	
	
	public static double dot(double[] one, double[] two){
		double out = 0;
		for(int i = 0; i < one.length && i < two.length; i++){
			out += one[i] * two[i];
		}
		return out;
	}
	
	public static double magnitude(double[] one){
		double out = 0;
		for(int i = 0; i < one.length; i++){
			out += one[i] * one[i];
		}
		return Math.sqrt(out);
	}
	
	public static double[] unitVector(double[] one){
		return divide(one,magnitude(one));
	}
	
	public static double[] perpendicular(double[] one){
		if(one.length != 2)
			return null;
		return new double[]{-one[1],one[0]};
	}
	
	public static double[] project(double[] a, double[] b){
		double a1 = dot(a,divide(b,magnitude(b)));
		return multiply(b,a1);
	}
	
	/*
	 * Round to places number of decimal places
	 */
	public static double round(double in, int places){
		double ovr = Math.pow(10, places);
		return Math.round(in*ovr) / ovr;
	}

	public static double[] collapse(double[][] data){
		ArrayList<Double> list = new ArrayList<Double>();

		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].length; j++){
				list.add(data[i][j]);
			}
		}

		return toArray(list);
	}

	public static double[] concat(double[] a, double[] b) {
		int aLen = a.length;
		int bLen = b.length;
		double[] c= new double[aLen+bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}

	/**
	 * Finds the 'peak' of a set of data using a taylor-expansion type weight.
	 * Returns the modified 'index' of the peak. This may be confusing if there are multiple peaks.
	 * */
	public static double peakIndex(double[] toSort){
		if(toSort.length == 1)
			return 0;
		ArrayList<Double> values = new ArrayList<>();
		ArrayList<Integer> indexes = new ArrayList<>();

		for(int i = 0; i < toSort.length; i++){
			int j = 0;
			for(; j < values.size(); j++){
				if(toSort[i] >= values.get(j)){
					break;
				}
			}
			values.add(j, toSort[i]);
			indexes.add(j, i);
			//System.err.println(toSort[i] + ":  " + values.toString());
		}
		//System.out.println(indexes.toString() + values.toString());
		if(values.get(0)==0){
			return 0;
			//return peakIndex(positiveNormalize(toSort));
		}
		double sum = 0;
		double over = 0;
		for(int i = 0; i < values.size(); i++){
			double ov = Math.pow((values.get(i) / values.get(0)),2);
			//System.out.println((double)values.get(i) * ov);
			sum += (double)values.get(i) * ov;
			over += ov;
		}

		double cutoff = sum/over;
		//System.out.println("CUTOFF: " + cutoff + " from sum " + sum);
		sum = 0;
		over = 0;

		for(int i = 0; i < values.size(); i++){
			if(values.get(i) >= cutoff){
				double ov = Math.pow((values.get(i) / values.get(0)),4);
				sum += (double)indexes.get(i) * ov;
				over += ov;
			}
		}
		return sum/over;
	}

	/**
	 * Adds two arrays together
	 */
	public static double[] add(double[] one, double[] two){
		double[] out = new double[(int)Math.round(Math.min(one.length, two.length))];
		for(int i = 0; i < one.length && i < two.length; i++){
			out[i] = one[i] + two[i];
		}
		return out;
	}
	public static double[] subtract(double[] one, double[] two){
		double[] out = new double[(int)Math.round(Math.min(one.length, two.length))];
		for(int i = 0; i < one.length && i < two.length; i++){
			out[i] = one[i] - two[i];
		}
		return out;
	}
	public static double[] divide(double[] one, double[] two){
		double[] out = new double[(int)Math.round(Math.min(one.length, two.length))];
		for(int i = 0; i < one.length && i < two.length; i++){
			out[i] = one[i] / two[i];
		}
		return out;
	}
	public static double[] multiply(double[] one, double[] two){
		double[] out = new double[(int)Math.round(Math.min(one.length, two.length))];
		for(int i = 0; i < one.length && i < two.length; i++){
			out[i] = one[i] * two[i];
		}
		return out;
	}
	public static double[] add(double[] one, double two){
		double[] out = new double[one.length];
		for(int i = 0; i < one.length; i++){
			out[i] = one[i] + two;
		}
		return out;
	}
	public static double[] subtract(double[] one, double two){
		double[] out = new double[one.length];
		for(int i = 0; i < one.length; i++){
			out[i] = one[i] - two;
		}
		return out;
	}
	public static double[] multiply(double[] one, double two){
		double[] out = new double[one.length];
		for(int i = 0; i < one.length; i++){
			out[i] = one[i] * two;
		}
		return out;
	}
	public static double[] divide(double[] one, double two){
		double[] out = new double[one.length];
		for(int i = 0; i < one.length; i++){
			out[i] = one[i] / two;
		}
		return out;
	}
	public static double[] abs(double[] one){
		double[] out = new double[one.length];
		for(int i = 0; i < one.length; i++){
			out[i] = Math.abs(one[i]);
		}
		return out;
	}
	
	public static double[] pow(double[] one,double num){
		double[] out = new double[one.length];
		for(int i = 0; i < one.length; i++){
			out[i] = Math.pow(one[i],num);
		}
		return out;
	}
	public static double[] ln(double[] one){
		double[] out = new double[one.length];
		for(int i = 0; i < one.length; i++){
			out[i] = Math.log(one[i]);
		}
		return out;
	}
	
	
	public static double errorAbsolute(double[] one, double[] two){
		double out = 0;
		for(int i = 0; i < one.length && i < two.length; i++){
			out += Math.abs(one[i] - two[i]);
		}
		return out;
	}
	

	public static double[] SMA(double[] one, int rad){
		double[] out = new double[one.length];
		for(int i = 0; i < one.length; i++){
			double tot = 0;
			int num = 0;
			for(int r = -rad; r <= rad; r++){
				if(i+r >= 0 && i+r < one.length){
					tot += one[i+r];
					num++;
				}
			}
			out[i] = tot/num;
		}
		return out;
	}
	
	public static double[] subset(double[] one, int start, int end){
		double[] out = new double[one.length];
		for(int i = Math.max(start,0); i < Math.min(one.length,end); i++){
			out[i] = one[i];
		}
		return out;
	}

	
	public static double correlationPositive(double[] one, double[] two){
		double out = 0;
		double[] oo = divide(subtract(one,mean(one)),stdev(one));
		double[] tt = divide(subtract(two,mean(two)),stdev(two));
		out = cumsum(abs(subtract(oo,tt)));
		return out;
	}
	public static double covarSubset(double[] one, double[] two, int subsets){
		int len = (int)Math.round(Math.min(one.length, two.length))/subsets;
		double tot = 0;
		for(int i = 0; i < subsets; i++){
			double[] oo = subset(one,i*len,i*len + len);
			double[] tt = subset(two,i*len,i*len + len);
			tot += Math.abs(covar(oo,tt));
		}
		return tot/subsets;
	}

	/**
	 * Finds the cutoff of the 'peak' of a set of data using SUM(el*(el/max)^2).
	 * Returns the modified 'index' of the peak.
	 * */
	public static double peakCutoff(double[] toSort){
		ArrayList<Double> values = new ArrayList<>();
		ArrayList<Integer> indexes = new ArrayList<>();

		for(int i = 0; i < toSort.length; i++){
			int j = 0;
			for(; j < values.size(); j++){
				if(toSort[i] >= values.get(j)){
					break;
				}
			}
			values.add(j, toSort[i]);
			indexes.add(j, i);
		}
		//System.out.println(indexes.toString() + values.toString());

		double sum = 0;
		double over = 0;
		if(values.get(0)==0){
			return 0;
		}
		for(int i = 0; i < values.size(); i++){
			double ov = Math.pow((values.get(i) / values.get(0)),2);
			sum += (double)values.get(i) * ov;
			over += ov;
		}

		return sum/over;
	}

	private static double[] positiveNormalize(double[] in){
		double chk = min(in);
		if(chk<=0){
			double[] d = new double[in.length];
			for(int i = 0; i < in.length; i++){
				d[i] = in[i]+chk+1;
			}
			return d;
		}
		else
			return in;
	}
	public static double[] toArray(ArrayList<Double> in){
		double[] d = new double[in.size()];
		for(int i = 0; i < in.size(); i++){
			d[i] = in.get(i);
		}
		return d;
	}

	public static ArrayList<Double> toArray(double[] in){
		ArrayList<Double> d = new ArrayList<Double>();
		for(int i = 0; i < in.length; i++){
			d.add(in[i]);
		}
		return d;
	}

	private static double sqr(double x) {
		return x * x;
	}

	public static double mean(double[] v) {
		double tot = 0.0;
		for (int i = 0; i < v.length; i++)
			tot += v[i];
		return tot / v.length;
	}


	public static double mean(int[] v) {
		double tot = 0.0;
		for (int i = 0; i < v.length; i++)
			tot += v[i];
		return tot / v.length;
	}

	/**
	 * the standard deviation
	 */
	public static double stdev(double[] v) {
		return Math.sqrt(variance(v));
	}

	/**
	 * The standard deviation of the sample divided by the square root of the
	 * sample size.
	 */
	public static double stderr(double[] v) {
		return stdev(v) / Math.sqrt(v.length);
	}

	public static double variance(double[] v) {
		double mu = mean(v);
		double sumsq = 0.0;
		for (int i = 0; i < v.length; i++)
			sumsq += sqr(mu - v[i]);
		return sumsq / (v.length);
	}

	public static double covar(double[] v1, double[] v2) {
		double m1 = mean(v1);
		double m2 = mean(v2);
		double sumsq = 0.0;
		for (int i = 0; i < v1.length; i++)
			sumsq += (m1 - v1[i]) * (m2 - v2[i]);
		return sumsq / (v1.length);
	}

	public static double correlation(double[] v1, double[] v2) {
		return covar(v1, v2) / (stdev(v1) * stdev(v2));
	}

	public static double correlation2(double[] v1, double[] v2) {
		return sqr(covar(v1, v2)) / (covar(v1, v1) * covar(v2, v2));
	}


	public static double max(double[] v) {
		double m = v[0];
		for (int i = 1; i < v.length; i++)
			m = Math.max(m, v[i]);
		return m;
	}


	public static double min(double[] v) {
		double m = v[0];
		for (int i = 1; i < v.length; i++)
			m = Math.min(m, v[i]);
		return m;
	}

	public static double median(double[] v){
		double[] sorted = sort(v);
		if (sorted.length % 2 == 0){

			//this is for if your array has an even ammount of numbers
			double middleNumOne = sorted[(int)(sorted.length / 2 - 0.5)];
			double middleNumTwo = sorted[(int)(sorted.length / 2 + 0.5)];
			double median = (middleNumOne + middleNumTwo) / 2.0;
			return median;
		}else{
			return sorted[sorted.length/2];
		}
	}

	public static double[] sort(double[] v){
		double[] copy = copy(v);
		Arrays.sort(copy);
		return copy;
	}

	public static double[] copy(double[] v){
		double[] out = new double[v.length];
		for(int i = 0; i < v.length; i++){
			out[i]=v[i];
		}
		return out;
	}

	public static double cumsum(double[] v){
		double sum = 0;
		for(double d:v){
			sum+=d;
		}
		return sum;
	}

	public static double[] negate(double[] v){
		double[] out = new double[v.length];
		for(int i = 0; i < v.length; i++){
			out[i]=-v[i];
		}
		return out;
	}

	public static double cumproduct(double[] v){
		double prod = 1;
		for(double d:v){
			prod*=d;
		}
		return prod;
	}

	public static double geometricMean(double[] v){
		return Math.pow(cumproduct(v), 1.0/v.length);
	}

	public static double[][] combinations(double[] v){
		ArrayList<double[]> combinationList = new ArrayList<double[]>();
		// Start i at 1, so that we do not include the empty set in the results
		for ( long i = 1; i < Math.pow(2, v.length); i++ ) {
			ArrayList<Double> doubleList = new ArrayList<Double>();
			for ( int j = 0; j < v.length; j++ ) {
				if ( (i & (long) Math.pow(2, j)) > 0 ) {
					// Include j in set
					doubleList.add(v[j]);
				}
			}
			combinationList.add(toArray(doubleList));
		}
		return combinationList.toArray(new double[0][0]);
	}

	public static String toString(double[] v){
		return Arrays.toString(v);
	}
	public static String toString(double[][] v){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < v.length; i++){
			sb.append(Arrays.toString(v[i])+System.lineSeparator());
		}
		return sb.toString();
	}

	public static double[] init(int len, double val){
		double[] d = new double[len];
		for(int i = 0; i < len; i++){
			d[i]=val;
		}
		return d;
	}

	public static String analysis(double[] v) {
		return "Average = " + mean(v) + "  sd = " + stdev(v) + "  min = "
				+ min(v) + "  max = " + max(v);
	}
	
	////////some random stats garbage
    // return phi(x) = standard Gaussian pdf
    private static double gaussian(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

    // return phi(x, mu, signma) = Gaussian pdf with mean mu and stddev sigma
    public static double gaussian(double x, double mu, double sigma) {
        return gaussian((x - mu) / sigma) / sigma;
    }
}








