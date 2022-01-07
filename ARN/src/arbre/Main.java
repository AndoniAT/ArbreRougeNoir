package arbre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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
		
		
		ABR<Integer> abrn = new ABR<>();
		System.out.println("\n\n\n");
		
		abrn.add(3);
		
		System.out.println(abrn.toString());
		
		abrn.add(4);
		System.out.println(abrn.toString());
		
		abrn.add(5);
		System.out.println(abrn.toString());
		
		abrn.add(2);
		System.out.println(abrn.toString());
		
		abrn.add(7);
		System.out.println(abrn.toString());
		
		abrn.add(6);
		System.out.println(abrn.toString());
		
		abrn.add(1);
		System.out.println(abrn.toString());
		
		ABR<Integer> abrn1 = new ABR<>();
		
		abrn1.add(5);
		abrn1.add(3);
		abrn1.add(7);
		abrn1.add(6);
		abrn1.add(8);
		abrn1.add(2);
		abrn1.add(4);
		abrn1.add(1);
		System.out.println(abrn1);
		
		int[] tab = new int[10];
		tab[0] = 1;
		//System.out.println(tab.length);
		List<Integer> li = new ArrayList<Integer>();
		li.add(4);
		li.add(5);
		System.out.println(li);
		System.out.println(li.get(0));
		System.out.println(li.size());
		

		ABR<Integer> ab = new ABR<>();
		
		ab.add(10);
		ab.add(20);
		ab.add(7);
		ab.add(8);
		ab.add(5);
		ab.add(30);
		ab.add(25);
		ab.add(40);
		
		System.out.println("\n\n\n================================= \n\n");
		System.out.println(ab.toString());
		
		Iterator<Integer> itt = ab.iterator();
		itt.next();
		itt.next();
		itt.next();
		itt.next();
		itt.next();
		itt.remove();
		
		System.out.println(ab.toString());
		System.out.println("\n\n\n================================= \n\n");
		ABR<Integer> abb = new ABR<>();
		
		abb.add(10);
		abb.add(20);
		abb.add(7);
		abb.add(8);
		abb.add(5);
		abb.add(30);
		abb.add(25);
		abb.add(40);
		abb.add(3);
		abb.add(6);
		abb.add(1);
		
		System.out.println(abb.toString());
		itt = abb.iterator();
		itt.next();
		itt.next();
		itt.next();
		itt.next();
		itt.remove();
		System.out.println(abb.toString());
		
		
		System.out.println("\n\n\n================================= \n\n");
		ABR<Integer> abbb = new ABR<>();
		
		abbb.add(20);
		abbb.add(15);
		abbb.add(30);
		abbb.add(10);
		abbb.add(17);
		abbb.add(29);
		abbb.add(40);
		abbb.add(45);
		abbb.add(47);
		
		System.out.println(abbb.toString());
		
		System.out.println("\n\n\n================================= \n\n");
		ABR<Integer> arn = new ABR<>();
		
		arn.add(10);
		arn.add(5);
		arn.add(20);
		arn.add(3);
		arn.add(8);
		arn.add(15);
		arn.add(25);
		arn.add(28);
		
		System.out.println(arn.toString());
		itt = arn.iterator();
		itt.next();
		itt.next();
		itt.next();
		itt.next();
		itt.remove();
		System.out.println(arn.toString());
	}

}
