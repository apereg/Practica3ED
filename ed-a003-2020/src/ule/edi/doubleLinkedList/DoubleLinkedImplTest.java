package ule.edi.doubleLinkedList;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.*;

import ule.edi.exceptions.EmptyCollectionException;

public class DoubleLinkedImplTest {
	DoubleLinkedListImpl<String> lv;
	DoubleLinkedListImpl<String> listaConElems;

	@Before
	public void antesDe() {
		lv = new DoubleLinkedListImpl<>();
		listaConElems = new DoubleLinkedListImpl<>();
		listaConElems.insertFirst("D");
		listaConElems.insertFirst("B");
		listaConElems.insertFirst("A");
		listaConElems.insertFirst("C");
		listaConElems.insertFirst("B");
		listaConElems.insertFirst("A");
	}

	@Test
	public void isEmptyTest() {
		Assert.assertTrue(lv.isEmpty());
		Assert.assertEquals(lv.size(), 0);
		Assert.assertFalse(listaConElems.isEmpty());
		Assert.assertEquals(listaConElems.size(), 6);
	}

	@Test
	public void clearTest() {
		lv.clear();
		Assert.assertTrue(lv.isEmpty());
		Assert.assertEquals(lv.size(), 0);
		listaConElems.clear();
		Assert.assertTrue(listaConElems.isEmpty());
		Assert.assertEquals(listaConElems.size(), 0);
		Assert.assertEquals(listaConElems.toString(), listaConElems.toStringReverse());
	}

	@Test
	public void containsTest() {
		Assert.assertFalse(lv.contains("A"));
		Assert.assertTrue(listaConElems.contains("A"));
		Assert.assertTrue(listaConElems.contains("B"));
		Assert.assertTrue(listaConElems.contains("B"));
		Assert.assertTrue(listaConElems.contains("D"));
		Assert.assertFalse(listaConElems.contains("Z"));
	}

	@Test
	public void removeAllTest() throws EmptyCollectionException {
		Assert.assertEquals(2, listaConElems.removeAll("A"));
		Assert.assertEquals(listaConElems.toString(), "(B C B D )");

		listaConElems.removeAll("B");
		Assert.assertFalse(listaConElems.contains("A"));
		Assert.assertFalse(listaConElems.contains("B"));
		Assert.assertEquals(listaConElems.toString(), "(C D )");
		listaConElems.removeAll("C");

		Assert.assertTrue(listaConElems.contains("D"));
		Assert.assertFalse(listaConElems.contains("C"));
		listaConElems.removeAll("D");

		Assert.assertFalse(listaConElems.contains("D"));
		Assert.assertTrue(listaConElems.isEmpty());
		Assert.assertEquals(listaConElems.size(), 0);
		Assert.assertEquals(listaConElems.toString(), listaConElems.toStringReverse());

	}

	@Test
	public void isSubListTest() throws EmptyCollectionException {
		Assert.assertTrue(listaConElems.isSubList(lv));
		Assert.assertTrue(listaConElems.isSubList(new DoubleLinkedListImpl<String>("A", "B", "C")));
		Assert.assertEquals(listaConElems.toString(), "(A B C A B D )");
		Assert.assertEquals(new DoubleLinkedListImpl<String>("A", "C").toString(), "(A C )");
		Assert.assertFalse(listaConElems.isSubList(new DoubleLinkedListImpl<String>("A", "C")));
		Assert.assertEquals(listaConElems.maxRepeated(), 2);
		listaConElems.insertBefore("A", "D");
		Assert.assertEquals(listaConElems.toString(), "(A B C A B A D )");
		Assert.assertEquals(listaConElems.maxRepeated(), 3);
	}

	/* TESTS DEL ALUMNO */
	@Test
	public void testInsertFirst() {
		listaConElems.insertFirst("J");
		assertEquals(listaConElems.getElemPos(1), "J");
		assertEquals(listaConElems.getElemPos(2), "A");
		assertEquals(listaConElems.size(), 7);
	}

	@Test
	public void testInsertFirstEmptyList() {
		lv.insertFirst("T");
		assertEquals(lv.size(), 1);
		assertEquals(lv.getElemPos(1), "T");
	}

	@Test(expected = NullPointerException.class)
	public void testInsertFirstNullElement() {
		lv.insertFirst(null);
	}

	@Test
	public void testInsertLast() {
		listaConElems.insertLast("X");
		assertEquals(listaConElems.getElemPos(listaConElems.size()), "X");
		assertEquals(listaConElems.size(), 7);
	}

	@Test
	public void testInsertLastEmptyList() {
		lv.insertLast("T");
		assertEquals(lv.size(), 1);
		assertEquals(lv.getElemPos(1), "T");
	}

	@Test(expected = NullPointerException.class)
	public void testInsertLastNullElement() {
		lv.insertLast(null);
	}

	@Test
	public void testRemoveFirst() throws EmptyCollectionException {
		assertEquals(listaConElems.size(), 6);
		assertEquals(listaConElems.getElemPos(1), "A");
		listaConElems.removeFirst();
		assertEquals(listaConElems.getElemPos(1), "B");
		assertEquals(listaConElems.size(), 5);
	}

	@Test
	public void testRemoveFirstUniqueElem() throws EmptyCollectionException {
		assertTrue(lv.isEmpty());
		lv.insertFirst("A");
		assertFalse(lv.isEmpty());
		lv.removeFirst();
		assertTrue(lv.isEmpty());
	}

	@Test(expected = EmptyCollectionException.class)
	public void testRemoveFirstEmptyList() throws EmptyCollectionException {
		lv.removeFirst();
	}

	@Test
	public void testRemoveLast() throws EmptyCollectionException {
		assertEquals(listaConElems.size(), 6);
		assertEquals(listaConElems.getElemPos(listaConElems.size()), "D");
		listaConElems.removeLast();
		assertEquals(listaConElems.getElemPos(listaConElems.size()), "B");
		assertEquals(listaConElems.size(), 5);
	}

	@Test
	public void testRemoveLastUniqueElem() throws EmptyCollectionException {
		assertTrue(lv.isEmpty());
		lv.insertFirst("A");
		assertFalse(lv.isEmpty());
		lv.removeLast();
		assertTrue(lv.isEmpty());
	}

	@Test(expected = EmptyCollectionException.class)
	public void testRemoveLastEmptyList() throws EmptyCollectionException {
		lv.removeLast();
	}

	@Test
	public void testInsertPos() {
		assertEquals(listaConElems.size(), 6);
		assertEquals(listaConElems.getElemPos(3), "C");
		listaConElems.insertPos("Z", 3);
		assertEquals(listaConElems.size(), 7);
		assertEquals(listaConElems.getElemPos(3), "Z");
		assertEquals(listaConElems.getElemPos(4), "C");
	}

	@Test
	public void testInsertPosFirst() {
		assertEquals(listaConElems.size(), 6);
		assertEquals(listaConElems.getElemPos(1), "A");
		listaConElems.insertPos("Z", 1);
		assertEquals(listaConElems.size(), 7);
		assertEquals(listaConElems.getElemPos(1), "Z");
		assertEquals(listaConElems.getElemPos(2), "A");
	}

	@Test
	public void testInsertPosLast() {
		assertEquals(listaConElems.size(), 6);
		assertEquals(listaConElems.getElemPos(6), "D");
		listaConElems.insertPos("Z", 7);
		assertEquals(listaConElems.size(), 7);
		assertEquals(listaConElems.getElemPos(7), "Z");
		assertEquals(listaConElems.getElemPos(6), "D");
	}

	@Test
	public void testInsertPosBiggerParam() {
		assertEquals(listaConElems.size(), 6);
		assertEquals(listaConElems.getElemPos(6), "D");
		listaConElems.insertPos("Z", 10);
		assertEquals(listaConElems.size(), 7);
		assertEquals(listaConElems.getElemPos(7), "Z");
		assertEquals(listaConElems.getElemPos(6), "D");
	}

	@Test(expected = NullPointerException.class)
	public void testInsertPosNullElem() {
		listaConElems.insertPos(null, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertPosIllegalPosition() {
		listaConElems.insertPos("A", -1);
	}

	@Test
	public void testInsertBefore() {
		assertEquals(listaConElems.size(), 6);
		assertEquals(listaConElems.getElemPos(3), "C");
		listaConElems.insertBefore("Z", "C");
		assertEquals(listaConElems.size(), 7);
		assertEquals(listaConElems.getElemPos(3), "Z");
		assertEquals(listaConElems.getElemPos(4), "C");
	}

	@Test
	public void testInsertBeforeFirst() {
		assertEquals(listaConElems.size(), 6);
		assertEquals(listaConElems.getElemPos(1), "A");
		listaConElems.insertBefore("Z", "A");
		assertEquals(listaConElems.size(), 7);
		assertEquals(listaConElems.getElemPos(1), "Z");
		assertEquals(listaConElems.getElemPos(2), "A");
	}

	@Test(expected = NullPointerException.class)
	public void testInsertBeforeNullElem() {
		listaConElems.insertBefore(null, "A");
	}

	@Test(expected = NullPointerException.class)
	public void testInsertBeforeNullTarget() {
		listaConElems.insertBefore("X", null);
	}

	@Test(expected = NoSuchElementException.class)
	public void testInsertBeforeNonContainedTarget() {
		listaConElems.insertBefore("X", "H1Z1");
	}

	@Test
	public void testGetElemPos() {
		assertEquals(listaConElems.getElemPos(3), "C");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetElemPosNegative() {
		assertEquals(listaConElems.getElemPos(-1), "A");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetElemPosOutOfBounds() {
		assertEquals(listaConElems.getElemPos(99), "A");
	}

	@Test
	public void testGetPosFirst() {
		assertEquals(listaConElems.getPosFirst("B"), 2);
	}

	@Test(expected = NullPointerException.class)
	public void testGetPosFirstNullElem() {
		assertEquals(listaConElems.getPosFirst(null), 2);
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetPosFirstNonContainedTarget() {
		assertEquals(listaConElems.getPosFirst("Z"), 2);
	}
	
	@Test
	public void testGetPosLast() {
		assertEquals(listaConElems.getPosLast("B"), 5);
	}

	@Test(expected = NullPointerException.class)
	public void testGetPosLastNullElem() {
		assertEquals(listaConElems.getPosLast(null), 2);
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetPosLastNonContainedTarget() {
		assertEquals(listaConElems.getPosLast("Z"), 2);
	}
	
	@Test
	public void testRemovePos() {
		assertEquals(listaConElems.size(), 6);
		assertEquals(listaConElems.getElemPos(3), "C");
		assertEquals(listaConElems.removePos(3), "C");
		assertEquals(listaConElems.size(), 5);
		assertEquals(listaConElems.getElemPos(3), "A");
	}
	
	@Test
	public void testRemovePosFirstUnique() {
		assertTrue(lv.isEmpty());
		lv.insertFirst("L");
		assertFalse(lv.isEmpty());
		assertEquals(lv.removePos(1), "L");
		assertTrue(lv.isEmpty());
	}
	
	@Test
	public void testRemovePosFirst() {
		assertTrue(lv.isEmpty());
		lv.insertFirst("L");
		lv.insertLast("H");
		assertEquals(lv.size(), 2);
		assertEquals(lv.removePos(1), "L");
		assertEquals(lv.size(), 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemovePosNegatimePos() {
		listaConElems.removePos(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemovePosBiggerPos() {
		listaConElems.removePos(100);
	}
	
	@Test (expected = NullPointerException.class)
	public void testRemoveAllElemNull() {
		listaConElems.removeAll(null);
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testRemoveAllNonContainedElem() {
		listaConElems.removeAll("ZZ");
	}
	
	@Test (expected = NullPointerException.class)
	public void testContainsNullElem() {
		listaConElems.contains(null);
	}
	
	@Test
	public void testSize() {
		assertEquals(lv.size(), 0);
		assertEquals(listaConElems.size(), 6);
	}
	
	@Test
	public void testSizeUniqueElem() {
		lv.insertFirst("A");
		assertEquals(lv.size(), 1);
	}
	
	@Test
	public void testToStringReverse() {
		assertEquals("(D B A C B A )", listaConElems.toStringReverse());
		listaConElems.insertFirst("Z");
		assertEquals("(D B A C B A Z )", listaConElems.toStringReverse());
		listaConElems.insertLast("*");
		assertEquals("(* D B A C B A Z )", listaConElems.toStringReverse());
		assertEquals("()", lv.toStringReverse());
	}
	
	@Test
	public void testReverse() {
		assertEquals(listaConElems.size(), listaConElems.reverse().size());
		assertEquals(listaConElems.toStringReverse(), listaConElems.reverse().toString());
	}
	
	@Test
	public void testMaxRepeated() {
		assertEquals(lv.maxRepeated(), 0);
		assertEquals(listaConElems.maxRepeated(), 2);
		listaConElems.insertLast("B");
		assertEquals(listaConElems.maxRepeated(), 3);
	}
	
	@Test
	public void testIsEquals() throws EmptyCollectionException {
		assertFalse(listaConElems.isEquals(new DoubleLinkedListImpl<String>("A", "B", "C")));
		assertTrue(listaConElems.isEquals(listaConElems.reverse().reverse()));
		assertFalse(listaConElems.isEquals(new DoubleLinkedListImpl<String>("A", "B", "C", "A", "B", "Z")));
	}
	
	@Test
	public void testContainsAll() {
		assertFalse(listaConElems.containsAll(new DoubleLinkedListImpl<String>("A", "B", "C")));
		assertTrue(listaConElems.containsAll(new DoubleLinkedListImpl<String>("A", "B", "C", "D")));
	}
	
	@Test (expected = NullPointerException.class)
	public void testContainsAllNullList() {
		listaConElems.containsAll(null);
	}
	
	@Test
	public void testIsSubListFalse() {
		assertFalse(listaConElems.isSubList(new DoubleLinkedListImpl<String>("A", "B", "C", "A", "B", "D", "Z")));
	}
	
	@Test (expected = NullPointerException.class)
	public void testIsSubListNullList() {
		listaConElems.isSubList(null);
	}
	
	@Test
	public void testToStringFromUntil() {
		assertEquals(listaConElems.toStringFromUntil(1, 3), "(B C A )");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testToStringFromUntilNegativeFrom() {
		listaConElems.toStringFromUntil(-1, 4);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testToStringFromUntilInverseParams() {
		listaConElems.toStringFromUntil(4, 2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testToStringFromUntilBiggerUntil() {
		listaConElems.toStringFromUntil(1, 90);
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testIterator() {
		Iterator<String> itr = listaConElems.iterator();
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "A");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "B");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "C");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "A");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "B");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "D");
		assertFalse(itr.hasNext());
		itr.next();
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testReverseIterator() {
		Iterator<String> itr = listaConElems.reverseIterator();
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "D");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "B");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "A");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "C");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "B");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "A");
		assertFalse(itr.hasNext());
		itr.next();
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testEvenIterator() {
		Iterator<String> itr = listaConElems.evenPositionsIterator();
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "B");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "A");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "D");
		assertFalse(itr.hasNext());
		itr.next();
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testEvenIteratorEmpty() {
		Iterator<String> itr = lv.evenPositionsIterator();
		assertFalse(itr.hasNext());
		itr.next();
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testProgressIterator() {
		Iterator<String> itr = listaConElems.progressIterator();
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "A");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "B");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "A");
		assertFalse(itr.hasNext());
		itr.next();
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testProgressIteratorTwo() {
		listaConElems.insertLast("Z");
		Iterator<String> itr = listaConElems.progressIterator();
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "A");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "B");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "A");
		assertTrue(itr.hasNext());
		assertEquals(itr.next(), "Z");
		assertFalse(itr.hasNext());
		itr.next();
	}

	@Test
	public void testToString() {
		assertEquals("(A B C A B D )", listaConElems.toString());
		listaConElems.insertFirst("Z");
		assertEquals("(Z A B C A B D )", listaConElems.toString());
		listaConElems.insertLast("*");
		assertEquals("(Z A B C A B D * )", listaConElems.toString());
		assertEquals("()", lv.toString());
	}

}
