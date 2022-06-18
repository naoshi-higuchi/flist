package jp.nhiguchi.libs.flist;

import java.util.*;

/**
 * Functional List.
 *
 * Immutable, Cons-cell based.
 *
 * @author Naoshi Higuchi
 */
public final class FList<E> implements List<E> {
	private static class ListIteratorImpl<E> implements ListIterator<E> {
		private final FList<E> fHead;
		private FList<E> fCur;
		private int fIndex;

		private ListIteratorImpl(FList<E> head) {
			fHead = head;
			fCur = fHead;
			fIndex = 0;
		}

		public boolean hasNext() {
			return !fCur.isEmpty();
		}

		public E next() {
			if (fCur.isEmpty()) throw new NoSuchElementException();

			final E e = fCur.fElem;
			fCur = fCur.fTail;
			++fIndex;

			return e;
		}

		public boolean hasPrevious() {
			return fHead != fCur;
		}

		/**
		 * Very slow :-)
		 *
		 */
		public E previous() {
			if (!hasPrevious()) throw new NoSuchElementException();

			FList<E> p = fHead;

			while (p.fTail != fCur) p = p.fTail;

			fCur = p;
			--fIndex;

			return p.fElem;
		}

		public int nextIndex() {
			return fIndex;
		}

		public int previousIndex() {
			return fIndex - 1;
		}

		public void remove() {
			throw new UnsupportedOperationException("Immutable.");
		}

		public void set(E e) {
			throw new UnsupportedOperationException("Immutable.");
		}

		public void add(E e) {
			throw new UnsupportedOperationException("Immutable.");
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static final FList EMPTY = new FList(null, null);
	private final E fElem;
	private final FList<E> fTail;

	private FList(E elem, FList<E> tail) {
		fElem = elem;
		fTail = tail;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <E> FList<E> flist(E elem, FList<? extends E> tail) {
		return (FList<E>) new FList(elem, tail);
	}

	@SuppressWarnings("unchecked")
	public static <E> FList<E> emptyList() {
		return (FList<E>) EMPTY;
	}

	public static <E> FList<E> flist() {
		return emptyList();
	}

	public static <E> FList<E> flist(E elem) {
		return flist(elem, emptyList());
	}

	public static <E> FList<E> flist(E... elems) {
		FList<E> list = emptyList();

		for (int i = elems.length - 1; i >= 0; --i) {
			list = flist(elems[i], list);
		}

		return list;
	}

	public static <E> FList<E> flist(Collection<? extends E> c) {
		if (c instanceof FList) return (FList) c;

		if (c.isEmpty()) return flist();

		E[] elems = (E[]) c.toArray();
		return flist(elems);
	}

	public static <E> FList<E> cons(E elem, FList<? extends E> tail) {
		return flist(elem, tail);
	}

	public E head() {
		if (isEmpty()) throw new NoSuchElementException();

		return fElem;
	}

	public FList<E> tail() {
		if (isEmpty()) throw new NoSuchElementException();

		return fTail;
	}

	public FList<E> append(E elem) {
		if (isEmpty()) return flist(elem);

		return cons(head(), tail().append(elem));
	}

	public FList<E> append(Collection<? extends E> c) {
		FList<E> res = this;

		for (E elem : c) {
			res = res.append(elem);
		}

		return res;
	}

	public FList<E> prepend(E elem) {
		return cons(elem, this);
	}

	public FList<E> prepend(Collection<? extends E> c) {
		FList<E> tmp;

		if (c.isEmpty()) return this;

		if (c instanceof FList) {
			tmp = (FList) c;
		} else {
			tmp = flist(c);
		}

		return cons(tmp.head(), prepend(tmp.tail()));
	}

	public FList<E> reverse() {
		FList<E> cur = this;
		FList<E> newList = emptyList();

		while (!cur.isEmpty()) {
			newList = cons(cur.fElem, newList);
			cur = cur.fTail;
		}

		return newList;
	}

	public int size() {
		int n = 0;
		FList<E> cur = this;

		while (!cur.isEmpty()) {
			++n;
			cur = cur.fTail;
		}

		return n;
	}

	public boolean isEmpty() {
		return this == EMPTY;
	}

	public boolean contains(Object o) {
		FList<E> cur = this;

		while (!cur.isEmpty()) {
			E e = cur.fElem;

			if (Objects.equals(o, e))
				return true;

			cur = cur.fTail;
		}

		return false;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public Iterator<E> iterator() {
		return new ListIteratorImpl(this);
	}

	public Object[] toArray() {
		int n = size();

		Object[] res = new Object[n];

		FList<E> cur = this;
		for (int i = 0; i < n; ++i) {
			res[i] = cur.fElem;
			cur = cur.fTail;
		}

		return res;
	}

	public <T> T[] toArray(T[] a) {
		int n = size();

		if (a.length < n) {
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), n);
		}

		FList<E> cur = this;

		for (int i = 0; i < n; ++i) {
			try {
				a[i] = (T) cur.fElem;
			} catch (ClassCastException e) {
				throw new ArrayStoreException();
			}
			cur = cur.fTail;
		}

		return a;
	}

	/**
	 * Unsupported.
	 *
	 */
	public boolean add(E e) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Immutable.");
	}

	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) return false;
		}

		return true;
	}

	/**
	 * Unsupported.
	 *
	 */
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	public void clear() {
		throw new UnsupportedOperationException("Immutable.");
	}

	public E get(int index) {
		FList<E> cur = this;

		if (index < 0 || cur.isEmpty()) throw new IndexOutOfBoundsException();

		for (int i = 0; i < index; ++i) {
			cur = cur.fTail;

			if (cur.isEmpty()) throw new IndexOutOfBoundsException();
		}

		return cur.fElem;
	}

	/**
	 * Unsupported.
	 *
	 */
	public E set(int index, E element) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	public void add(int index, E element) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	public E remove(int index) {
		throw new UnsupportedOperationException("Immutable.");
	}

	public int indexOf(Object o) {
		int i = 0;

		for (FList<E> cur = this; !cur.isEmpty(); cur = cur.fTail) {
			if (Objects.equals(o, cur.fElem)) return i;

			++i;
		}

		return -1;
	}

	public int lastIndexOf(Object o) {
		int i = 0;
		int res = -1;

		for (FList<E> cur = this; !cur.isEmpty(); cur = cur.fTail) {
			if (Objects.equals(o, cur.fElem)) res = i;

			++i;
		}

		return res;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public ListIterator<E> listIterator() {
		return new ListIteratorImpl(this);
	}

	public ListIterator<E> listIterator(int index) {
		ListIterator<E> it = listIterator();

		if (index < 0) throw new IndexOutOfBoundsException();

		while (it.nextIndex() < index) {
			if (!it.hasNext()) throw new IndexOutOfBoundsException();
			it.next();
		}

		return it;
	}

	public List<E> subList(int fromIndex, int toIndex) {
		ListIterator<E> it = listIterator();

		while (it.nextIndex() < fromIndex) it.next();

		LinkedList<E> list = new LinkedList<>();
		while (it.nextIndex() < toIndex) {
			list.add(it.next());
		}

		return flist(list);
	}

	public String toStringWithoutBrackets(String sep) {
		StringBuilder sb = new StringBuilder();

		FList<E> cur = this;
		while (!cur.isEmpty()) {
			if (sb.length() != 0) sb.append(sep);

			sb.append(cur.fElem.toString());
			cur = cur.fTail;
		}

		return sb.toString();
	}

	public String toStringWithoutBrackets() {
		return toStringWithoutBrackets(", ");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(toStringWithoutBrackets()).append("]");

		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if ((obj == EMPTY) ^ (this == EMPTY)) return false;

		if (!(obj instanceof List)) return false;

		List rhs = (List) obj;

		Iterator it = rhs.iterator();
		for (E e : this) {
			if (!it.hasNext()) return false;
			Object o = it.next();
			if (e == null) {
				if (o != null) return false;
				continue;
			}
			if (!e.equals(o)) return false;
		}

		if (it.hasNext()) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int hashCode = 1;
		for (E e : this) {
			hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
		}

		return hashCode;
	}
}
