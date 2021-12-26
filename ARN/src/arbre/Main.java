package arbre;

import java.util.Comparator;
import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		System.out.println("Creation d'arbre");
		ABR<Integer> abr = new ABR<>();
		abr.add(50);
		abr.add(30);
		abr.add(33);
		abr.add(15);
		abr.add(17);
		abr.add(23);
		abr.add(19);
		abr.add(12);
		abr.add(13);
		abr.add(5);
		abr.add(8);
		abr.add(9);
		abr.add(10);
		abr.add(6);
		abr.add(7);
		abr.add(52);
		abr.add(54);
		abr.add(53);
		abr.add(70);
		abr.add(68);
		abr.add(63);
		abr.add(72);
		
		
		ABR<Integer> abr2 = new ABR<>(abr);
		
		//System.out.println(abr.toString());
		
		Iterator<Integer> it = abr.iterator();
		Iterator<Integer> it2 = abr2.iterator();
		
		it2.remove();
		
		System.out.println(abr.toString());
		System.out.println(abr2.toString());
		
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) {
				return  ((Comparable<Integer>) e1).compareTo(e2);
			}
		};
		
		ABR<Integer> abr3 = new ABR<>(c);
		
		abr3.add(3);
		abr3.add(4);
		abr3.add(5);
		abr3.add(1);
		
		System.out.println(abr3.toString());
		
		
		


	}

}
