package arbre;

import java.util.Comparator;
import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Creation d'arbre");
		ABR<Integer> abr = new ABR<>();
		
		abr.add(20);
		abr.add(25);
		abr.add(22);
		
		
		
		System.out.println(abr.toString());
	}

}
