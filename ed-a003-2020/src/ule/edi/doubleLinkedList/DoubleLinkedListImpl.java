package ule.edi.doubleLinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ule.edi.exceptions.EmptyCollectionException;

public class DoubleLinkedListImpl<T> implements DoubleList<T> {

	// referencia al primer nodo de la lista
	private DoubleNode<T> front;

	// referencia al Último nodo de la lista
	private DoubleNode<T> last;

	private class DoubleNode<T> {

		DoubleNode(T element) {
			this.elem = element;
			this.next = null;
			this.prev = null;
		}

		T elem;

		DoubleNode<T> next;
		DoubleNode<T> prev;
	}

///// ITERADOR normal //////////

	@SuppressWarnings("hiding")
	private class DobleLinkedListIterator<T> implements Iterator<T> {
		// añadir atributos

		public DobleLinkedListIterator(DoubleNode<T> nodo) {

		}

		@Override
		public boolean hasNext() {

			// todo
			return false;
		}

		@Override
		public T next() {
			// todo

			return null;

		}

	}

	////// FIN ITERATOR

	/// TODO : AÑADIR OTRAS CLASES PARA LOS OTROS 3 ITERADORES

	/////

	@SafeVarargs
	public DoubleLinkedListImpl(T... v) {
		for (T elem : v) {
			this.insertLast(elem);
		}
	}

	@Override
	public boolean isEmpty() {
		return this.front == null;
	}

	@Override
	public void clear() {
		this.front = null;
		this.last = null;
	}

	@Override
	public void insertFirst(T elem) {
		/*
		 * Si el elemento a eliminar es nulo se lanza la excepcion marcada en la
		 * documentacion
		 */
		if (elem == null)
			throw new NullPointerException();

		if (this.isEmpty()) {
			/* Si la lista esta vacia se asigna el front y el last al elemento */
			this.front.elem = elem;
			this.last.elem = elem;
			this.front.next = this.last;
			this.last.prev = this.front;
		} else {
			/*
			 * Si no se crea un nuevo nodo para almacenar el front y llevarlo a la segunda
			 * posicion
			 */
			DoubleNode<T> aux = this.front;
			this.front.elem = elem;
			this.front.next = aux;
			aux.prev = this.front;
		}
	}

	@Override
	public void insertLast(T elem) {
		/*
		 * Si el elemento a eliminar es nulo se lanza la excepcion marcada en la
		 * documentacion
		 */
		if (elem == null)
			throw new NullPointerException();

		if (this.isEmpty()) {
			/* Si la lista esta vacia se asigna el front y el last al elemento */
			this.front.elem = elem;
			this.last.elem = elem;
			this.front.next = this.last;
			this.last.prev = this.front;
		} else {
			/*
			 * Si no se crea un nuevo nodo para almacenar el last y llevarlo a la penultima
			 * posicion
			 */
			DoubleNode<T> aux = this.last;
			this.last.elem = elem;
			this.last.prev = aux;
			aux.next = this.last;
		}
	}

	@Override
	public T removeFirst() throws EmptyCollectionException {
		/* Si la lista esta vacia se lanza la excepcion marcada en la documentacion */
		if (this.isEmpty())
			throw new EmptyCollectionException("DoubleLinkedList");

		T elemReturn = this.front.elem;
		if (this.front.next != null) {
			/* Si hay mas de un elemento se mueve el segundo a la primera posicion */
			this.front = this.front.next;
			this.front.prev = null;
		} else {
			/* Si no se llama al metodo que limpia la lista */
			this.clear();
		}
		return elemReturn;
	}

	@Override
	public T removeLast() throws EmptyCollectionException {
		/* Si la lista esta vacia se lanza la excepcion marcada en la documentacion */
		if (this.isEmpty())
			throw new EmptyCollectionException("DoubleLinkedList");

		T elemReturn = this.front.elem;
		if (this.last.prev != null) {
			/* Si hay mas de un elemento se mueve el segundo a la primera posicion */
			this.last = this.last.prev;
			this.last.next = null;
		} else {
			/* Si no se llama al metodo que limpia la lista */
			this.clear();
		}
		return elemReturn;
	}

	@Override
	public void insertPos(T elem, int position) {
		/*
		 * Si el elemento a eliminar es nulo se lanza la excepcion marcada en la
		 * documentacion
		 */
		if (elem == null)
			throw new NullPointerException();
		/*
		 * Si el indice no esta entre las dimensiones del array se lanza la excepcion
		 * marcada en la documentacion
		 */
		if (--position < 0 || position > this.size())
			throw new IllegalArgumentException();

		/*
		 * Se comprueba si se quiere introducir el primero para llamar al metodo
		 * pertinente
		 */
		if (position == 0) {
			this.insertFirst(elem);
			return;
		}

		/* Se recorre con un bucle while hasta la posicion indicada como parametro */
		DoubleNode<T> aux = this.front;
		while (position != 0) {
			aux = aux.next;
			position--;
		}
		DoubleNode<T> inserted = new DoubleNode<>(elem);
		/* Si es el ultimo no se enlaza el siguiente (Evitar NullPointerException) */
		if (aux.next != null)
			aux.next.prev = inserted;

		/* Se enlaza el nuevo elemento en esa posicion */
		inserted.next = aux.next;
		aux.next = inserted;
		inserted.prev = aux;
	}

	@Override
	public void insertBefore(T elem, T target) {
		/*
		 * Si alguno de los dos elementos pasados como parametro es nulo se lanza la
		 * excepcion marcada en la documentacion
		 */
		if (elem == null || target == null)
			throw new NullPointerException();
	
		/* Si la lista no contiene el elemento a buscar se lanza la excepcion marcada en la documentacion */
		if(!this.contains(target))
			throw new NoSuchElementException();
		
		/* Si se llega aqui al menos hay un elemento por lo que se busca en un bucle el elemento */
		DoubleNode<T> aux = this.front;
		while (aux.elem != target) 
			aux = aux.next;
		
		DoubleNode<T> inserted = new DoubleNode<>(elem);
		/* Si es el primero no se enlaza el anterior (Evitar NullPointerException) */
		if (aux.prev != null)
			aux.prev.next = inserted;

		/* Se enlaza el nuevo elemento en esa posicion */
		inserted.prev = aux.prev;
		aux.prev = inserted;
		inserted.next = aux;
	}

	@Override
	public T getElemPos(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPosFirst(T elem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPosLast(T elem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T removePos(int pos) {
		/*
		 * Si la posicion a eliminar esta fuera de los limites del array se lanza la
		 * excepcion marcada en la documentacion
		 */
		if (pos < 0 || pos > this.size())
			throw new IllegalArgumentException();

		/*
		 * Si se quiere borrar el unico elemento de la lista se llama al metodo clear
		 */
		if (--pos == 0 && this.front.next == null) {
			T returnElem = this.front.elem;
			this.clear();
			return returnElem;
		}

		/*
		 * Se avanza en un bucle for el numero de veces desde el inicio hasta llegar al
		 * elemento deseado
		 */
		DoubleNode<T> aux = this.front;
		for (int i = 0; i < pos; i++)
			aux = aux.next;

		/*
		 * Una vez llegado se guarda ese elemento, se sobrescribe en la cola y se
		 * devuelve
		 */
		T returnElem = aux.elem;
		aux.prev.next = aux.next;
		aux.next.prev = aux.prev;
		return returnElem;
	}

	@Override
	public int removeAll(T elem) {
		/*
		 * Si el elemento a eliminar es nulo se lanza la excepcion marcada en la
		 * documentacion
		 */
		if (elem == null)
			throw new NullPointerException();
		/*
		 * Si el elemento no esta en la lista se lanza la excepcion marcada en la
		 * documentacion
		 */
		if (!this.contains(elem))
			throw new NoSuchElementException();

		if (this.size() == 1 && this.front.elem.equals(elem)) {
			/* Si es el unico elemento de la lista se vacia y se devuelve 1 */
			this.clear();
			return 1;
		}

		/*
		 * Si se ha llegado a este punto el elemento esta al menos una vez y la lista
		 * tiene al menos un elemento por lo que se recorre de principio a fin buscando
		 * esos elementos y eliminandolos incrementando el contador
		 */
		int apariciones = 0;
		DoubleNode<T> aux = this.front;
		while (aux != null) {
			if (aux.elem.equals(elem)) {
				/*
				 * Si es el primer elemento se mueve el front, si no se elimina el elemento
				 * saltandolo
				 */
				if (aux.prev == null) {
					this.front = this.front.next;
				} else {
					aux.prev.next = aux.next;
					aux.next.prev = aux.prev;
				}
				apariciones++;
			}
			aux = aux.next;
		}
		return apariciones;
	}

	@Override
	public boolean contains(T elem) {
		/*
		 * Si el elemento a comparar es nulo se lanza la excepcion marcada en la
		 * documentacion
		 */
		if (elem == null)
			throw new NullPointerException();
		/* Si la lista esta vacia no va a contener el elemento */
		if (this.isEmpty())
			return false;

		/*
		 * Se recorre la lista desde front a last con un do-while por si solo hay un
		 * elemento
		 */
		DoubleNode<T> aux = this.front;
		do {
			/* Si se encuentra directamente se devuelve true */
			if (aux.elem.equals(elem))
				return true;
			aux = aux.next;
		} while (aux != this.last);
		/*
		 * Si se ha recorrido todo el bucle y no se ha encontrado ninguna coincidencia
		 * se devuelve false
		 */
		return false;
	}

	@Override
	public int size() {
		/*
		 * Se recorre de principio a fin la lista incrementado la variable a devolver en
		 * cada paso
		 */
		int size = 0;
		DoubleNode<T> aux = this.front;
		while (aux != null) {
			size++;
			aux = aux.next;
		}
		return size;
	}

	@Override
	public String toStringReverse() {
		StringBuilder str = new StringBuilder("(");
		/*
		 * Se recorre del ultimo al primero añadiendo el toString del elemento
		 * almacenado y un espacio
		 */
		DoubleNode<T> aux = this.last;
		while (aux != null) {
			str.append(aux.elem.toString()).append(" ");
			aux = aux.prev;
		}
		str.append(")");
		return str.toString();
	}

	@Override
	public DoubleList<T> reverse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int maxRepeated() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEquals(DoubleList<T> other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(DoubleList<T> other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSubList(DoubleList<T> other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toStringFromUntil(int from, int until) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("(");
		/*
		 * Se recorre del primero al ultimo añadiendo el toString del elemento
		 * almacenado y un espacio
		 */
		DoubleNode<T> aux = this.front;
		while (aux != null) {
			str.append(aux.elem.toString()).append(" ");
			aux = aux.next;
		}
		str.append(")");
		return str.toString();
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<T> reverseIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<T> evenPositionsIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<T> progressIterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
