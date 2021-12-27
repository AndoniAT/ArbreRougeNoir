package arbre;

import java.util.Comparator;
import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Creation d'arbre");
		ABR<Integer> abr = new ABR<>();
		
		abr.add(20);
		abr.add(25);
		abr.add(30);
		abr.add(32);
		abr.add(35);
		abr.add(22);
		
		Iterator<Integer> it = abr.iterator();
		it.next();
		it.next();
		it.remove();
		
		
		System.out.println(abr.toString());
		
		ABR<Integer> abr2 = new ABR<>(abr);
		
		
		System.out.println(abr.toString());
		System.out.println(abr2.toString());

	}

}
