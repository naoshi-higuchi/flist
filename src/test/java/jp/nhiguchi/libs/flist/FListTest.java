package jp.nhiguchi.libs.flist;

import java.util.*;

import org.junit.jupiter.api.Test;

import static jp.nhiguchi.libs.flist.FList.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Naoshi Higuchi
 */
public class FListTest {
	/**
	 * Test of flist method, of class FList.
	 */
	@Test
	public void testFlist_0args() {
		System.out.println("newFList");
		FList<?> result = FList.flist();
		assertTrue(result.isEmpty());
	}

	/**
	 * Test of flist method, of class FList.
	 */
	@Test
	public void testFlist_Collection() {
		System.out.println("newFList");
		Collection<String> c = new LinkedList<>();
		c.add("1st");
		c.add("2nd");
		c.add("3rd");

		FList<String> expResult = flist("1st", "2nd", "3rd");
		FList<String> result = FList.flist(c);
		assertEquals(expResult, result);
	}

	@Test
	public void testFlist_Collection_alreadyFList() {
		FList<String> original = flist("1st", "2nd", "3rd");
		assertSame(original, FList.flist(original));
	}

	@Test
	public void testFlist_Collection_empty() {
		assertTrue(FList.flist(Collections.emptyList()).isEmpty());
	}

	/**
	 * Test of cons method, of class FList.
	 */
	@Test
	public void testCons() {
		System.out.println("cons");
		FList<String> result = cons("1st", cons("2nd", flist("3rd")));
		assertEquals(3, result.size());
		assertEquals("1st", result.get(0));
		assertEquals("2nd", result.get(1));
		assertEquals("3rd", result.get(2));
	}

	/**
	 * Test of head method, of class FList.
	 */
	@Test
	public void testHead() {
		System.out.println("head");

		FList<String> instance;
		String expResult;
		String result;

		instance = flist("1st", "2nd", "3rd");
		expResult = "1st";
		result = instance.head();
		assertEquals(expResult, result);

		instance = flist();
		try {
			result = instance.head();
			fail();
		} catch (NoSuchElementException expected) {
			// succeed.
		}
	}

	/**
	 * Test of tail method, of class FList.
	 */
	@Test
	public void testTail() {
		System.out.println("tail");
		FList<String> instance;
		FList<String> expResult;
		FList<String> result;

		instance = flist("1st", "2nd", "3rd");
		expResult = flist("2nd", "3rd");
		result = instance.tail();
		assertEquals(expResult, result);

		instance = flist("1st");
		expResult = flist();
		result = instance.tail();
		assertEquals(expResult, result);

		instance = flist();
		try {
			result = instance.tail();
			fail();
		} catch (NoSuchElementException expected) {
			// succeed.
		}
	}

	/**
	 * Test of append method, of class FList.
	 */
	@Test
	public void testAppend() {
		System.out.println("append");
		FList<String> instance;
		FList<String> expResult;
		FList<String> result;

		instance = flist("1st", "2nd", "3rd");
		expResult = flist("1st", "2nd", "3rd", "4th");
		result = instance.append("4th");
		assertEquals(expResult, result);
	}

	/**
	 * Test of append method, of class FList.
	 */
	@Test
	public void testAppend_Collection() {
		System.out.println("append");
		FList<String> instance;
		FList<String> expResult;
		FList<String> result;

		instance = flist("1st", "2nd", "3rd");
		expResult = flist("1st", "2nd", "3rd", "4th", "5th", "6th");
		result = instance.append(flist("4th", "5th", "6th"));
		assertEquals(expResult, result);
	}

	@Test
	public void testAppend_onEmptyList() {
		assertEquals(flist("1st"), flist().append("1st"));
		assertEquals(flist("1st", "2nd"), flist().append(Arrays.asList("1st", "2nd")));
	}

	/**
	 * Test of prepend method, of class FList.
	 */
	@Test
	public void testPrepend() {
		System.out.println("prepend");
		FList<String> instance;
		FList<String> expResult;
		FList<String> result;

		instance = flist("1st", "2nd", "3rd");
		expResult = flist("0th", "1st", "2nd", "3rd");
		result = instance.prepend("0th");
		assertEquals(expResult, result);
	}

	@Test
	public void testPrepend_onEmptyList() {
		assertEquals(flist("1st"), flist().prepend("1st"));
	}

	/**
	 * Test of prepend method, of class FList.
	 */
	@Test
	public void testPrepend_Collection() {
		System.out.println("prepend");
		FList<String> instance;
		FList<String> expResult;
		FList<String> result;

		instance = flist("4th", "5th", "6th");
		expResult = flist("1st", "2nd", "3rd", "4th", "5th", "6th");
		result = instance.prepend(flist("1st", "2nd", "3rd"));
		assertEquals(expResult, result);
	}

	@Test
	public void testPrepend_Collection_nonFList() {
		assertEquals(
			flist("1st", "2nd", "3rd", "4th", "5th"),
			flist("4th", "5th").prepend(Arrays.asList("1st", "2nd", "3rd")));
	}

	@Test
	public void testPrepend_Collection_empty() {
		FList<String> instance = flist("1st", "2nd");
		assertSame(instance, instance.prepend(Collections.emptyList()));
	}

	/**
	 * Test of reverse method, of class FList.
	 */
	@Test
	public void testReverse() {
		System.out.println("reverse");
		FList<String> instance;
		FList<String> expResult;
		FList<String> result;

		instance = flist("1st", "2nd", "3rd");
		expResult = flist("3rd", "2nd", "1st");
		result = instance.reverse();
		assertEquals(expResult, result);

		instance = flist();
		expResult = flist();
		result = instance.reverse();
		assertEquals(expResult, result);
	}

	/**
	 * Test of size method, of class FList.
	 */
	@Test
	public void testSize() {
		System.out.println("size");
		FList<String> instance;
		int expResult;
		int result;

		instance = flist("1st");
		expResult = 1;
		result = instance.size();
		assertEquals(expResult, result);

		instance = flist();
		expResult = 0;
		result = instance.size();
		assertEquals(expResult, result);

		instance = flist((String) null);
		expResult = 1;
		result = instance.size();
		assertEquals(expResult, result);

		instance = flist("1st", "2nd", "3rd");
		expResult = 3;
		result = instance.size();
		assertEquals(expResult, result);

		instance = flist("1st", null, "3rd");
		expResult = 3;
		result = instance.size();
		assertEquals(expResult, result);
	}

	/**
	 * Test of isEmpty method, of class FList.
	 */
	@Test
	public void testIsEmpty() {
		System.out.println("isEmpty");
		FList<String> instance;
		boolean expResult;
		boolean result;

		instance = flist("1st", "2nd", "3rd");
		expResult = false;
		result = instance.isEmpty();
		assertEquals(expResult, result);

		instance = flist();
		expResult = true;
		result = instance.isEmpty();
		assertEquals(expResult, result);

		instance = flist((String) null);
		expResult = false;
		result = instance.isEmpty();
		assertEquals(expResult, result);

		instance = flist((String) null, "2nd");
		expResult = false;
		result = instance.isEmpty();
		assertEquals(expResult, result);
	}

	/**
	 * Test of contains method, of class FList.
	 */
	@Test
	public void testContains() {
		System.out.println("contains");
		String o;
		FList<String> instance = flist("1st", "2nd", "3rd");
		boolean expResult;
		boolean result;

		o = "1st";
		expResult = true;
		result = instance.contains(o);
		assertEquals(expResult, result);

		o = "2nd";
		expResult = true;
		result = instance.contains(o);
		assertEquals(expResult, result);

		o = "3rd";
		expResult = true;
		result = instance.contains(o);
		assertEquals(expResult, result);

		o = "4th";
		expResult = false;
		result = instance.contains(o);
		assertEquals(expResult, result);
	}

	@Test
	public void testContains_null() {
		assertTrue(flist("1st", null, "3rd").contains(null));
		assertFalse(flist("1st", "2nd").contains(null));
		assertFalse(flist().contains(null));
	}

	/**
	 * Test of iterator method, of class FList.
	 */
	@Test
	public void testIterator() {
		System.out.println("iterator");
		FList<String> instance = flist("1st", "2nd", "3rd");
		Iterator<String> result = instance.iterator();

		assertTrue(result.hasNext());
		assertEquals("1st", result.next());
		assertTrue(result.hasNext());
		assertEquals("2nd", result.next());
		assertTrue(result.hasNext());
		assertEquals("3rd", result.next());
		assertFalse(result.hasNext());
	}

	@Test
	public void testIterator_next_exhausted() {
		assertThrows(NoSuchElementException.class, flist().iterator()::next);
	}

	/**
	 * Test of toArray method, of class FList.
	 */
	@Test
	public void testToArray_0args() {
		System.out.println("toArray");
		FList<String> instance = flist("1st", "2nd", "3rd");
		Object[] expResult = {"1st", "2nd", "3rd"};
		Object[] result = instance.toArray();
		assertArrayEquals(expResult, result);
	}

	/**
	 * Test of toArray method, of class FList.
	 */
	@Test
	public void testToArray_GenericType() {
		System.out.println("toArray");
		String[] a = new String[3];
		FList<String> instance = flist("1st", "2nd", "3rd");
		String[] expResult = {"1st", "2nd", "3rd"};
		String[] result = instance.toArray(a);
		assertArrayEquals(expResult, result);
	}

	@Test
	public void testToArray_GenericType_LargerArray() {
		String[] a = new String[5];
		a[3] = "sentinel";
		a[4] = "sentinel";
		FList<String> instance = flist("1st", "2nd", "3rd");
		String[] result = instance.toArray(a);
		assertSame(a, result);
		assertEquals("1st", result[0]);
		assertEquals("2nd", result[1]);
		assertEquals("3rd", result[2]);
		assertNull(result[3]);
		assertEquals("sentinel", result[4]);
	}

	@Test
	public void testToArray_GenericType_SmallerArray() {
		String[] a = new String[1];
		FList<String> instance = flist("1st", "2nd", "3rd");
		String[] result = instance.toArray(a);
		assertNotSame(a, result);
		assertArrayEquals(new String[]{"1st", "2nd", "3rd"}, result);
	}

	/**
	 * Test of add method, of class FList.
	 */
	@Test
	public void testAdd_GenericType() {
		System.out.println("add");
		String e = "something";
		FList<String> instance = flist();

		try {
			instance.add(e);
			fail();
		} catch (UnsupportedOperationException expected) {
			// succeed.
		}
	}

	/**
	 * Test of remove method, of class FList.
	 */
	@Test
	public void testRemove_Object() {
		System.out.println("remove");
		String e = "2nd";
		FList<String> instance = flist("1st", "2nd", "3rd");

		try {
			instance.remove(e);
			fail();
		} catch (UnsupportedOperationException expected) {
			// succeed.
		}
	}

	/**
	 * Test of containsAll method, of class FList.
	 */
	@Test
	public void testContainsAll() {
		System.out.println("containsAll");
		Collection<String> c;
		FList<String> instance = flist("1st", "2nd", "3rd");
		boolean expResult;
		boolean result;

		c = Arrays.asList("1st", "2nd", "3rd");
		expResult = true;
		result = instance.containsAll(c);
		assertEquals(expResult, result);

		c = Arrays.asList("2nd", "3rd");
		expResult = true;
		result = instance.containsAll(c);
		assertEquals(expResult, result);

		c = Arrays.asList("1st", "2nd");
		expResult = true;
		result = instance.containsAll(c);
		assertEquals(expResult, result);

		c = Arrays.asList("1st", "2nd", "3rd", "4th");
		expResult = false;
		result = instance.containsAll(c);
		assertEquals(expResult, result);
	}

	@Test
	public void testContainsAll_emptyCollection() {
		assertTrue(flist("1st", "2nd").containsAll(Collections.emptyList()));
		assertTrue(flist().containsAll(Collections.emptyList()));
	}

	/**
	 * Test of addAll method, of class FList.
	 */
	@Test
	public void testAddAll_Collection() {
		System.out.println("addAll");
		Collection<String> c = Arrays.asList("4th", "5th", "6th");
		FList<String> instance = flist("1st", "2nd", "3rd");

		try {
			instance.addAll(c);
			fail();
		} catch (UnsupportedOperationException expected) {
			// succeed.
		}
	}

	/**
	 * Test of addAll method, of class FList.
	 */
	@Test
	public void testAddAll_int_Collection() {
		System.out.println("addAll");
		int index = 1;
		Collection<String> c = Arrays.asList("1.5", "1.6");
		FList<String> instance = flist("1st", "2nd", "3rd");

		try {
			instance.addAll(index, c);
			fail();
		} catch (UnsupportedOperationException expected) {
			// succeed.
		}
	}

	/**
	 * Test of removeAll method, of class FList.
	 */
	@Test
	public void testRemoveAll() {
		System.out.println("removeAll");
		Collection<String> c = Arrays.asList("1st", "2nd");
		FList<String> instance = flist("1st", "2nd", "3rd");

		try {
			instance.removeAll(c);
			fail();
		} catch (UnsupportedOperationException expected) {
			// succeed.
		}
	}

	/**
	 * Test of retainAll method, of class FList.
	 */
	@Test
	public void testRetainAll() {
		System.out.println("retainAll");
		Collection<String> c = Arrays.asList("1st", "2nd");
		FList<String> instance = flist("1st", "2nd", "3rd");

		try {
			instance.retainAll(c);
			fail();
		} catch (UnsupportedOperationException expected) {
			// succeed.
		}
	}

	/**
	 * Test of clear method, of class FList.
	 */
	@Test
	public void testClear() {
		System.out.println("clear");
		FList<String> instance = flist("1st", "2nd", "3rd");

		try {
			instance.clear();
			fail();
		} catch (UnsupportedOperationException expected) {
			// succeed.
		}
	}

	/**
	 * Test of get method, of class FList.
	 */
	@Test
	public void testGet() {
		System.out.println("get");
		int index;
		FList<String> instance = flist("1st", "2nd", "3rd");
		String expResult;
		String result;

		index = 0;
		expResult = "1st";
		result = instance.get(index);
		assertEquals(expResult, result);

		index = 1;
		expResult = "2nd";
		result = instance.get(index);
		assertEquals(expResult, result);

		index = 2;
		expResult = "3rd";
		result = instance.get(index);
		assertEquals(expResult, result);

		index = -1;
		try {
			result = instance.get(index);
			fail();
		} catch (IndexOutOfBoundsException expected) {
			// succeed.
		}

		index = 3;
		try {
			result = instance.get(index);
			fail();
		} catch (IndexOutOfBoundsException expected) {
			// succeed.
		}
	}

	/**
	 * Test of set method, of class FList.
	 */
	@Test
	public void testSet() {
		System.out.println("set");
		String e = "something";
		FList<String> instance = flist();

		try {
			instance.set(0, e);
			fail();
		} catch (UnsupportedOperationException expected) {
			// succeed.
		}
	}

	/**
	 * Test of add method, of class FList.
	 */
	@Test
	public void testAdd_int_GenericType() {
		System.out.println("add");
		int index = 0;
		String e = "something";
		FList<String> instance = flist();

		try {
			instance.add(index, e);
			fail();
		} catch (UnsupportedOperationException expected) {
			// succeed.
		}
	}

	/**
	 * Test of remove method, of class FList.
	 */
	@Test
	public void testRemove_int() {
		System.out.println("remove");
		int index = 0;
		FList<String> instance = flist("1st", "2nd", "3rd");

		try {
			instance.remove(index);
			fail();
		} catch (UnsupportedOperationException expected) {
			// succeed.
		}
	}

	/**
	 * Test of indexOf method, of class FList.
	 */
	@Test
	public void testIndexOf() {
		System.out.println("indexOf");
		String o;
		FList<String> instance = flist("1st", "2nd", "3rd");
		int expResult;
		int result;

		o = "1st";
		expResult = 0;
		result = instance.indexOf(o);
		assertEquals(expResult, result);

		o = "2nd";
		expResult = 1;
		result = instance.indexOf(o);
		assertEquals(expResult, result);

		o = "3rd";
		expResult = 2;
		result = instance.indexOf(o);
		assertEquals(expResult, result);

		o = "4th";
		expResult = -1;
		result = instance.indexOf(o);
		assertEquals(expResult, result);
	}

	@Test
	public void testIndexOf_null() {
		assertEquals(1, flist("1st", null, "3rd").indexOf(null));
		assertEquals(-1, flist("1st", "2nd").indexOf(null));
	}

	/**
	 * Test of lastIndexOf method, of class FList.
	 */
	@Test
	public void testLastIndexOf() {
		System.out.println("lastIndexOf");
		String o;
		FList<String> instance;
		int expResult;
		int result;

		o = "2nd";
		instance = flist("1st", "2nd", "3rd");
		expResult = 1;
		result = instance.lastIndexOf(o);
		assertEquals(expResult, result);

		o = "2nd";
		instance = flist("1st", "2nd", "3rd", "2nd");
		expResult = 3;
		result = instance.lastIndexOf(o);
		assertEquals(expResult, result);

		o = "4th";
		instance = flist("1st", "2nd", "3rd");
		expResult = -1;
		result = instance.lastIndexOf(o);
		assertEquals(expResult, result);
	}

	@Test
	public void testLastIndexOf_null() {
		assertEquals(2, flist("1st", null, null).lastIndexOf(null));
		assertEquals(-1, flist("1st", "2nd").lastIndexOf(null));
	}

	/**
	 * Test of listIterator method, of class FList.
	 */
	@Test
	public void testListIterator_0args() {
		System.out.println("listIterator");
		FList<String> instance = flist("1st", "2nd", "3rd");
		List<String> lst = new LinkedList<>();
		lst.add("1st");
		lst.add("2nd");
		lst.add("3rd");
		ListIterator<String> expResult = lst.listIterator();
		ListIterator<String> result = instance.listIterator();

		for (int i = 0; i < 100; ++i) {
			if (Math.random() < 0.5) {
				assertEquals(expResult.hasNext(), result.hasNext());
				if (expResult.hasNext()) {
					assertEquals(expResult.next(), result.next());
					assertEquals(expResult.nextIndex(), result.nextIndex());
				}
			} else {
				assertEquals(expResult.hasPrevious(), result.hasPrevious());
				if (expResult.hasPrevious()) {
					assertEquals(expResult.previous(), result.previous());
					assertEquals(expResult.previousIndex(), result.previousIndex());
				}
			}
		}
	}

	@Test
	public void testListIterator_mutationThrows() {
		ListIterator<String> it = flist("1st").listIterator();
		assertThrows(UnsupportedOperationException.class, it::remove);
		assertThrows(UnsupportedOperationException.class, () -> it.set("x"));
		assertThrows(UnsupportedOperationException.class, () -> it.add("x"));
	}

	@Test
	public void testListIterator_next_exhausted() {
		ListIterator<String> it = flist("1st").listIterator();
		it.next();
		assertThrows(NoSuchElementException.class, it::next);
	}

	@Test
	public void testListIterator_previous_atStart() {
		assertThrows(NoSuchElementException.class, flist("1st").listIterator()::previous);
	}

	/**
	 * Test of listIterator method, of class FList.
	 */
	@Test
	public void testListIterator_int() {
		System.out.println("listIterator");
		int index = 1;
		FList<String> instance = flist("1st", "2nd", "3rd");
		List<String> lst = new LinkedList<>();
		lst.add("1st");
		lst.add("2nd");
		lst.add("3rd");
		ListIterator<String> expResult = lst.listIterator(index);
		ListIterator<String> result = instance.listIterator(index);

		for (int i = 0; i < 100; ++i) {
			if (Math.random() < 0.5) {
				assertEquals(expResult.hasNext(), result.hasNext());
				if (expResult.hasNext()) {
					assertEquals(expResult.next(), result.next());
					assertEquals(expResult.nextIndex(), result.nextIndex());
				}
			} else {
				assertEquals(expResult.hasPrevious(), result.hasPrevious());
				if (expResult.hasPrevious()) {
					assertEquals(expResult.previous(), result.previous());
					assertEquals(expResult.previousIndex(), result.previousIndex());
				}
			}
		}
	}

	@Test
	public void testListIterator_int_invalidIndex() {
		FList<String> instance = flist("1st", "2nd");
		assertThrows(IndexOutOfBoundsException.class, () -> instance.listIterator(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> instance.listIterator(3));
	}

	/**
	 * Test of subList method, of class FList.
	 */
	@Test
	public void testSubList() {
		System.out.println("subList");
		int fromIndex = 1;
		int toIndex = 3;
		FList<String> instance = flist("1st", "2nd", "3rd", "4th");
		List<String> expResult = flist("2nd", "3rd");
		List<String> result = instance.subList(fromIndex, toIndex);
		assertEquals(expResult, result);
	}

	@Test
	public void testSubList_edgeCases() {
		FList<String> instance = flist("1st", "2nd", "3rd");
		assertEquals(flist(), instance.subList(1, 1));
		assertEquals(flist("1st", "2nd"), instance.subList(0, 2));
		assertEquals(flist("2nd", "3rd"), instance.subList(1, 3));
	}

	@Test
	public void testSubList_invalidRange() {
		FList<String> instance = flist("1st", "2nd");
		assertThrows(IndexOutOfBoundsException.class, () -> instance.subList(-1, 1));
		assertThrows(IndexOutOfBoundsException.class, () -> instance.subList(0, 3));
		assertThrows(IllegalArgumentException.class, () -> instance.subList(2, 1));
	}

	/**
	 * Test of equals method, of class FList.
	 */
	@Test
	public void testEquals() {
		System.out.println("equals");
		FList<String> instance;
		boolean expResult;
		boolean result;

		instance = flist("1st");
		expResult = true;
		result = instance.equals(flist("1st"));
		assertEquals(expResult, result);

		instance = flist("1st", "2nd");
		expResult = true;
		result = instance.equals(flist("1st", "2nd"));
		assertEquals(expResult, result);

		instance = flist("1st", "2nd");
		expResult = false;
		result = instance.equals(flist("1st", "second"));
		assertEquals(expResult, result);

		instance = flist("1st", "2nd");
		expResult = false;
		result = instance.equals(flist("1st"));
		assertEquals(expResult, result);

		instance = flist("1st");
		expResult = false;
		result = instance.equals(flist("1st", "2nd"));
		assertEquals(expResult, result);

		instance = flist();
		expResult = true;
		result = instance.equals(flist());
		assertEquals(expResult, result);

		instance = flist((String) null);
		expResult = true;
		result = instance.equals(flist((String) null));
		assertEquals(expResult, result);

		instance = flist((String) null);
		expResult = false;
		result = instance.equals(flist());
		assertEquals(expResult, result);

		instance = flist();
		expResult = false;
		result = instance.equals(flist((String) null));
		assertEquals(expResult, result);
	}

	@Test
	public void testEquals_null() {
		assertFalse(flist("1st").equals(null));
		assertFalse(flist().equals(null));
	}

	@Test
	public void testEquals_self() {
		FList<String> instance = flist("1st", "2nd");
		assertTrue(instance.equals(instance));
	}

	@Test
	public void testEquals_nonList() {
		assertFalse(flist("1st").equals("not a list"));
		assertFalse(flist().equals(42));
	}

	@Test
	public void testEquals_crossType() {
		assertTrue(flist("1st", "2nd", "3rd").equals(Arrays.asList("1st", "2nd", "3rd")));
		assertFalse(flist("1st", "2nd").equals(Arrays.asList("1st", "2nd", "3rd")));
	}

	@Test
	public void testHashCode() {
		// Deterministic for same content
		assertEquals(flist("1st", "2nd").hashCode(), flist("1st", "2nd").hashCode());
		assertEquals(flist().hashCode(), flist().hashCode());
		// Null elements
		assertEquals(flist((String) null).hashCode(), flist((String) null).hashCode());
		// List contract: equal lists must have equal hashCodes across implementations
		assertEquals(Arrays.asList("1st", "2nd").hashCode(), flist("1st", "2nd").hashCode());
		assertEquals(Arrays.asList().hashCode(), flist().hashCode());
	}

	@Test
	public void testToStringWithoutBrackets_sep() {
		assertEquals("1st-2nd-3rd", flist("1st", "2nd", "3rd").toStringWithoutBrackets("-"));
		assertEquals("only", flist("only").toStringWithoutBrackets("-"));
		assertEquals("", flist().toStringWithoutBrackets("-"));
	}

	/**
	 * Test of toString method, of class FList.
	 */
	@Test
	public void testToString() {
		System.out.println("toString");
		FList<String> instance;
		String expResult;
		String result;

		instance = flist("foo");
		expResult = "[foo]";
		result = instance.toString();
		assertEquals(expResult, result);

		instance = flist();
		expResult = "[]";
		result = instance.toString();
		assertTrue(instance.isEmpty());
		assertEquals(expResult, result);

		instance = flist("foo", "bar");
		expResult = "[foo, bar]";
		result = instance.toString();
		assertEquals(expResult, result);
	}
}
