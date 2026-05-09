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

		@Override
		public boolean hasNext() {
			return !fCur.isEmpty();
		}

		@Override
		public E next() {
			if (fCur.isEmpty()) throw new NoSuchElementException();

			final E e = fCur.fElem;
			fCur = fCur.fTail;
			++fIndex;

			return e;
		}

		@Override
		public boolean hasPrevious() {
			return fHead != fCur;
		}

		/**
		 * Very slow :-)
		 *
		 */
		@Override
		public E previous() {
			if (!hasPrevious()) throw new NoSuchElementException();

			FList<E> p = fHead;

			while (p.fTail != fCur) p = p.fTail;

			fCur = p;
			--fIndex;

			return p.fElem;
		}

		@Override
		public int nextIndex() {
			return fIndex;
		}

		@Override
		public int previousIndex() {
			return fIndex - 1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Immutable.");
		}

		@Override
		public void set(E e) {
			throw new UnsupportedOperationException("Immutable.");
		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException("Immutable.");
		}
	}

	private static final FList<?> EMPTY = new FList<>(null, null);
	private final E fElem;
	private final FList<E> fTail;

	private FList(E elem, FList<E> tail) {
		fElem = elem;
		fTail = tail;
	}

	@SuppressWarnings("unchecked")
	public static <E> FList<E> flist(E elem, FList<? extends E> tail) {
		return new FList<>(elem, (FList<E>) tail);
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

	@SafeVarargs
	public static <E> FList<E> flist(E... elems) {
		FList<E> list = emptyList();

		for (int i = elems.length - 1; i >= 0; --i) {
			list = flist(elems[i], list);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public static <E> FList<E> flist(Collection<? extends E> c) {
		if (c instanceof FList<?> fl) return (FList<E>) fl;

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

	@SuppressWarnings("unchecked")
	public FList<E> prepend(Collection<? extends E> c) {
		FList<E> tmp;

		if (c.isEmpty()) return this;

		if (c instanceof FList<?> fl) {
			tmp = (FList<E>) fl;
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

	@Override
	public int size() {
		int n = 0;
		FList<E> cur = this;

		while (!cur.isEmpty()) {
			++n;
			cur = cur.fTail;
		}

		return n;
	}

	@Override
	public boolean isEmpty() {
		return this == EMPTY;
	}

	@Override
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

	@Override
	public Iterator<E> iterator() {
		return new ListIteratorImpl<>(this);
	}

	@Override
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

	@Override
	@SuppressWarnings("unchecked")
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

		if (a.length > n) a[n] = null;

		return a;
	}

	/**
	 * Unsupported.
	 *
	 */
	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Immutable.");
	}

	@Override
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
	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException("Immutable.");
	}

	@Override
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
	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException("Immutable.");
	}

	/**
	 * Unsupported.
	 *
	 */
	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException("Immutable.");
	}

	@Override
	public int indexOf(Object o) {
		int i = 0;

		for (FList<E> cur = this; !cur.isEmpty(); cur = cur.fTail) {
			if (Objects.equals(o, cur.fElem)) return i;

			++i;
		}

		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		int i = 0;
		int res = -1;

		for (FList<E> cur = this; !cur.isEmpty(); cur = cur.fTail) {
			if (Objects.equals(o, cur.fElem)) res = i;

			++i;
		}

		return res;
	}

	@Override
	public ListIterator<E> listIterator() {
		return new ListIteratorImpl<>(this);
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		ListIterator<E> it = listIterator();

		if (index < 0) throw new IndexOutOfBoundsException();

		while (it.nextIndex() < index) {
			if (!it.hasNext()) throw new IndexOutOfBoundsException();
			it.next();
		}

		return it;
	}

	// Returns a copy, not a live view. Safe for an immutable list since neither
	// the original nor the result can be mutated.
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex > toIndex) throw new IllegalArgumentException();

		// listIterator(fromIndex) validates fromIndex >= 0 and <= size
		ListIterator<E> it = listIterator(fromIndex);

		LinkedList<E> list = new LinkedList<>();
		while (it.nextIndex() < toIndex) {
			if (!it.hasNext()) throw new IndexOutOfBoundsException();
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

		if (!(obj instanceof List<?> rhs)) return false;

		Iterator<?> it = rhs.iterator();
		for (E e : this) {
			if (!it.hasNext()) return false;
			Object o = it.next();
			if (e == null) {
				if (o != null) return false;
				continue;
			}
			if (!e.equals(o)) return false;
		}

		return !it.hasNext();
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
