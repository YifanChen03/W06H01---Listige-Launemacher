package pgdp.datastructures.lists;

import java.util.Arrays;

public class RecIntList {
	private RecIntListElement head;

	public RecIntList() {
		head = null;
	}

	public void append(int value) {
		if (head == null) {
			head = new RecIntListElement(value);
		} else {
			head.append(value);
		}
	}

	public int get(int idx) {
		if (head == null) {
			System.out.println("Invalid index: list is empty!");
			return Integer.MAX_VALUE;
		}
		return head.get(idx);
	}

	public int size() {
	    /**
		 * can be rewritten as
		 * if(head==null)
		 * return 0;
		 * else
		 * return head.size();
		*/
		return head == null ? 0 : head.size();
	}

	public boolean insert(int value, int idx) {
		if (head == null) {
			if (idx == 0) {
				append(value);
				return true;
			} else {
				System.out.println("You may only insert at index 0 to a empty list!");
				return false;
			}
		}
		return head.insert(value, idx);
	}

	@Override
	public String toString() {
		if (head != null) {
			return "List: [" + head.toString() + "]";
		} else {
			return "Empty list";
		}
	}

	public String toConnectionString() {
		if (head != null) {
			return "List: [" + head.toConnectionString() + "]";
		} else {
			return "Empty list";
		}
	}

	public long[] countThresh(int threshold) {
		// TODO
		long[] output;
		long smaller = 0;
		long equal = 0;
		long greater = 0;
		RecIntListElement c = this.head;

		//berechnet Summe der Werte der Liste kleiner threshold
		smaller = smaller_threshold(threshold, c);

		//sucht, ob threshold in der Liste vorhanden ist
		equal = find_equal(threshold, c);

		//berechnet Summe der Werte der Liste größer threshold
		greater = greater_threshold(threshold, c);

		//setze kleiner, equal und größer in array ein
		output = new long[]{smaller, equal, greater};
		return output;
	}

	private long smaller_threshold(int threshold, RecIntListElement rile) {
		//Stoppbedingung falls n == null
		if (rile == null) {
			return 0;
		}

		//falls kleiner threshold
		if (rile.getValue() < threshold) {
			return rile.getValue() + smaller_threshold(threshold, rile.getNext());
		} else {
			return smaller_threshold(threshold, rile.getNext());
		}
	}

	private long find_equal(int threshold, RecIntListElement rile) {
		if (rile == null) {
			return 0;
		}

		//falls gleich threshold
		if (rile.getValue() == threshold) {
			return rile.getValue() + find_equal(threshold, rile.getNext());
		} else {
			return find_equal(threshold, rile.getNext());
		}
	}

	private long greater_threshold(int threshold, RecIntListElement rile) {
		//Stoppbedingung falls n == null
		if (rile == null) {
			return 0;
		}

		//falls größer threshold
		if (rile.getValue() > threshold) {
			return rile.getValue() + greater_threshold(threshold, rile.getNext());
		} else {
			return greater_threshold(threshold, rile.getNext());
		}
	}

	public void kinguinSort(boolean increasing) {
		// TODO
		//falls Liste leer
		if (size() == 0 || this == null) {
			return;
		}
		RecIntListElement c = this.head;
		kS_helper(increasing, c);
	}

	private void kS_helper(boolean increasing, RecIntListElement rile) {
		//Stoppbedingung falls Ende erreicht und somit rile == null
		if (rile == null) {
			return;
		}

		if (increasing) {
			if (rile.getNext() != null && rile.getValue() > rile.getNext().getValue()) {
				if (rile.getNext().getNext() == null) {
					rile.setNext(null);
				} else {
					RecIntListElement tel = rile.getNext().getNext();
					rile.setNext(tel);
					tel.setPrev(rile);
				}
				kS_helper(increasing, rile);
			}
		} else {
			if (rile.getNext() != null && rile.getValue() < rile.getNext().getValue()) {
				//falls vorletztes Element
				if (rile.getNext().getNext() == null) {
					rile.setNext(null);
				} else {
					RecIntListElement tel = rile.getNext().getNext();
					rile.setNext(tel);
					tel.setPrev(rile);
				}
				kS_helper(increasing, rile);
			}
		}
		kS_helper(increasing, rile.getNext());
	}

	public void reverse() {
		// TODO
		//catche leere listen und listen null
		if (size() == 0 || this == null) {
			return;
		}
		r_helper(head);
	}

	public void r_helper(RecIntListElement c) {
		RecIntListElement tel;
		//Stoppbedingung wenn c == null da letztes Element
		if (c == null) {
			return;
		}

		//speicher zu ersetzendes Listenelement
		tel = c.getNext();
		c.setNext(c.getPrev());
		c.setPrev(tel);

		//verschiebe head
		head = c;

		r_helper(tel);
	}

	public static void zip(RecIntList l1, RecIntList l2) {
		// TODO
		//falls zweite Liste leer
		if (l1.size() == 0 && l2.size() != 0) {
			l1.head = l2.head;
		} else if (l2.size() != 0) {
			z_helper(l1.head, null, l2.head, null);
		}
	}

	public static void z_helper(RecIntListElement le1, RecIntListElement p_le1,
								RecIntListElement le2, RecIntListElement p_le2) {
		//Stoppbedingung wenn le1 und le2 null erreicht haben
		if (le1 == null && le2 == null) {
			return;
		}
		if (le1 == null) {
			p_le2.setNext(le2);
			le2.setPrev(p_le2);
			return;
		}
		if (le2 == null) {
			le1.setPrev(p_le2);
			return;
		}

		RecIntListElement tle1 = le1.getNext();
		RecIntListElement tle2 = le2.getNext();
		le1.setPrev(p_le2);
		le1.setNext(le2);
		le2.setPrev(le1);
		le2.setNext(tle1);
		p_le1 = le1;
		p_le2 = le2;
		le1 = tle1;
		le2 = tle2;

		z_helper(le1, p_le1, le2, p_le2);
	}

	public static void main(String[] args) {
		// countThresh example
		/*int threshold = -3;
		RecIntList countThreshExample = new RecIntList();
		for (int i = -7; i <= 6; i++) {
			countThreshExample.append(i);
		}
		System.out.println("Threshold: " + threshold);
		System.out.println(countThreshExample);
		System.out.println(Arrays.toString(countThreshExample.countThresh(threshold)));*/

		// kinguinSort example (1)
		/*RecIntList kinguinSortExample = new RecIntList();
		int[] kinguinSortvalues = new int[] {3,2,4,7,1,6,5,9,8};
		//int[] kinguinSortvalues = new int[] {};
		for (int i : kinguinSortvalues) {
			kinguinSortExample.append(i);
		}
		kinguinSortExample.kinguinSort(false); // false for example (2)
		System.out.println(kinguinSortExample);*/

		// reverse example
		/*RecIntList reverseExample = new RecIntList();
		for (int i = 1; i < 6; i++) {
			reverseExample.append(i);
		}
		reverseExample.reverse();
		System.out.println(reverseExample);*/

		// zip example
		RecIntList l1 = new RecIntList();
		RecIntList l2 = new RecIntList();
		for (int i = 1; i <= 5; i += 2) {
			l1.append(i);
			l2.append(i + 1);
		}
		l1.append(7);
		l1.append(8);
		System.out.println("l1: " + l1);
		System.out.println("l2: " + l2);
		RecIntList.zip(l1, l2);
		System.out.println(l1);
	}
}
