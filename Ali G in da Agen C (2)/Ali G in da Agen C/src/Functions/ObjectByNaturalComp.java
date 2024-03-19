package Functions;

import java.util.Comparator;

public class ObjectByNaturalComp<T extends Comparable> implements Comparator<T>{

	@Override
	public int compare(Comparable o1, Comparable o2) {

		return o1.compareTo(o2);
		
	}
}
