package utils;




import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class SortedList<E> extends ArrayList<E>{

	public static void main(String[] args) {
		Comparator<Integer> cc = new Comparator<Integer>(){
			public int compare(Integer o1, Integer o2) {
				if(o1 > o2)
					return 1;
				else if(o1 == o2)
					return 0;
				else return -1;
			}
		};
		SortedList<Integer> sl = new SortedList<Integer>(cc);
		sl.add(7); sl.add(4);sl.add(2);sl.add(9);sl.add(8);sl.add(6);sl.add(4);
		System.out.println(sl);
	}

	Comparator<E> comp;

	public SortedList(Comparator<E> c){
		super();
		comp = c;
	}

	/**
	 * I'm making this O(n) for now cause I'm lazy
	 */
	public boolean add(E e){
		if(size() == 0){
			super.add(e);
			return true;
		}
		int low = 0;
		int high = size() - 1;

		while (low < high) {
			int mid = (low + high) / 2;
			//assert(mid < high);
			if(comp.compare(e, get(mid)) < 0){
				low = mid + 1;
			}
			else{
				high = mid;
			}
		}
		//this next part it dumb but works for now
		if(high == size()-1){
			//System.out.println("comparing " + e + " to " + get(size()-1));
			if(comp.compare(e, get(size()-1)) < 0){
				high++;
			}
		}
		//System.out.println("adding " + e + " to index " + high);
		add(high,e);
		//System.out.println(this);
		return true;
		//		for(int i = 0; i < this.size(); i++){
		//			if(comp.compare(e, get(i)) > 0){
		//				this.add(i,e);
		//				return true;
		//			}
		//		}
		//		this.add(this.size(), e);
		//		return true;
	}

	public int indexOf(Object obj){
		@SuppressWarnings("unchecked")
		E e = (E)obj;
		//do a binary search
		if(comp.compare(e, get(0))==1){
			return -1;
			//returns -1 if too low
		}
		if(comp.compare(e, get(size()-1))==-1){
			return -1;
			//returns -1 if too high
		}
		int low = 0;
		int high = size() - 1;

		while (low < high) {
			int mid = (low + high) / 2;
			//assert(mid < high);
			if(comp.compare(e, get(mid)) < 0){
				low = mid + 1;
			}
			else{
				high = mid;
			}
		}
		//this next part it dumb but works for now
		if(high == size()-1){
			//System.out.println("comparing " + e + " to " + get(size()-1));
			if(comp.compare(e, get(size()-1)) < 0){
				high++;
			}
		}
		return high;
	}
}





































