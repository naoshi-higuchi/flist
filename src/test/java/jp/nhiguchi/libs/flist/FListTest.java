package jp.nhiguchi.libs.flist;

import java.util.*;
import org.junit.*;

import static jp.nhiguchi.libs.flist.FList.*;
import static org.junit.Assert.*;

/**
 *
 * @author Naoshi Higuchi
 */
public class FListTest {
	public FListTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of flist method, of class FList.
	 */
	@Test
	public void testFlist_0args() {
		System.out.println("newFList");
		FList result = FList.flist();
		assertTrue(result.isEmpty());
	}

	/**
	 * Test of flist method, of class FList.
	 */
	@Test
	public void testFlist_Collection() {
		System.out.println("newFList");
		Collection<String> c = new LinkedList<String>();
		c.add("1st");
		c.add("2nd");
		c.add("3rd");

		FList<String> expResult = flist("1st", "2nd", "3rd");
		FList<String> result = FList.flist(c);
		assertEquals(expResult, result);
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
		FList instance;
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

		instance = flist((Object) null);
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
		FList instance;
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

		instance = flist((Object) null);
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
		Object o;
		FList instance = flist("1st", "2nd", "3rd");
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

	/**
	 * Test of iterator method, of class FList.
	 */
	@Test
	public void testIterator() {
		System.out.println("iterator");
		FList instance = flist("1st", "2nd", "3rd");
		Iterator result = instance.iterator();

		assertTrue(result.hasNext());
		assertEquals("1st", result.next());
		assertTrue(result.hasNext());
		assertEquals("2nd", result.next());
		assertTrue(result.hasNext());
		assertEquals("3rd", result.next());
		assertFalse(result.hasNext());
	}

	/**
	 * Test of toArray method, of class FList.
	 */
	@Test
	public void testToArray_0args() {
		System.out.println("toArray");
		FList instance = flist("1st", "2nd", "3rd");
		Object[] expResult = {"1st", "2nd", "3rd"};
		Object[] result = instance.toArray();
		assertEquals(expResult, result);
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
		assertEquals(expResult, result);
	}

	/**
	 * Test of add method, of class FList.
	 */
	@Test
	public void testAdd_GenericType() {
		System.out.println("add");
		Object e = "something";
		FList instance = flist();

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
		Object e = "2nd";
		FList instance = flist("1st", "2nd", "3rd");

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
		Collection<?> c;
		FList instance = flist("1st", "2nd", "3rd");
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

	/**
	 * Test of addAll method, of class FList.
	 */
	@Test
	public void testAddAll_Collection() {
		System.out.println("addAll");
		Collection c = Arrays.asList("4th", "5th", "6th");
		FList instance = flist("1st", "2nd", "3rd");

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
		Collection c = Arrays.asList("1.5", "1.6");
		FList instance = flist("1st", "2nd", "3rd");

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
		Collection c = Arrays.asList("1st", "2nd");
		FList instance = flist("1st", "2nd", "3rd");

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
		Collection c = Arrays.asList("1st", "2nd");
		FList instance = flist("1st", "2nd", "3rd");

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
		FList instance = flist("1st", "2nd", "3rd");

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
		FList instance = flist("1st", "2nd", "3rd");
		Object expResult;
		Object result;

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
		Object e = "something";
		FList instance = flist();

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
		Object e = "something";
		FList instance = flist();

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
		FList instance = flist("1st", "2nd", "3rd");

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
		Object o;
		FList instance = flist("1st", "2nd", "3rd");
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

	/**
	 * Test of lastIndexOf method, of class FList.
	 */
	@Test
	public void testLastIndexOf() {
		System.out.println("lastIndexOf");
		Object o;
		FList instance;
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

	/**
	 * Test of listIterator method, of class FList.
	 */
	@Test
	public void testListIterator_0args() {
		System.out.println("listIterator");
		FList instance = flist("1st", "2nd", "3rd");
		List lst = new LinkedList();
		lst.add("1st");
		lst.add("2nd");
		lst.add("3rd");
		ListIterator expResult = lst.listIterator();
		ListIterator result = instance.listIterator();

		for (int i = 0; i < 100; ++i) {
			if (Math.random() % 2 == 0) {
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

	/**
	 * Test of listIterator method, of class FList.
	 */
	@Test
	public void testListIterator_int() {
		System.out.println("listIterator");
		int index = 1;
		System.out.println("listIterator");
		FList instance = flist("1st", "2nd", "3rd");
		List lst = new LinkedList();
		lst.add("1st");
		lst.add("2nd");
		lst.add("3rd");
		ListIterator expResult = lst.listIterator(index);
		ListIterator result = instance.listIterator(index);

		for (int i = 0; i < 100; ++i) {
			if (Math.random() % 2 == 0) {
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

	/**
	 * Test of subList method, of class FList.
	 */
	@Test
	public void testSubList() {
		System.out.println("subList");
		int fromIndex = 1;
		int toIndex = 3;
		FList instance = flist("1st", "2nd", "3rd", "4th");
		List expResult = flist("2nd", "3rd");
		List result = instance.subList(fromIndex, toIndex);
		assertEquals(expResult, result);
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
