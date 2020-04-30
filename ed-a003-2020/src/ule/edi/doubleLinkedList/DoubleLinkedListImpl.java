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
	private class DoubleLinkedListIterator<T> implements Iterator<T> {

		private DoubleNode<T> nodo;

		public DoubleLinkedListIterator(DoubleNode<T> nodo) {
			this.nodo = nodo;
		}

		@Override
		public boolean hasNext() {
			return this.nodo != null;
		}

		@Override
		public T next() {
			if (this.nodo == null)
				throw new NoSuchElementException();
			T elemReturn = this.nodo.elem;
			this.nodo = this.nodo.next;
			return elemReturn;
		}

	}

	@SuppressWarnings("hiding")
	private class ReverseDoubleLinkedListIterator<T> implements Iterator<T> {

		private DoubleNode<T> nodo;

		public ReverseDoubleLinkedListIterator(DoubleNode<T> nodo) {
			this.nodo = nodo;
		}

		@Override
		public boolean hasNext() {
			return this.nodo != null;
		}

		@Override
		public T next() {
			if (this.nodo == null)
				throw new NoSuchElementException();
			T elemReturn = this.nodo.elem;
			this.nodo = this.nodo.prev;
			return elemReturn;
		}

	}

	@SuppressWarnings("hiding")
	private class EvenPositionsDoubleLinkedListIterator<T> implements Iterator<T> {

		private DoubleNode<T> nodo;

		public EvenPositionsDoubleLinkedListIterator(DoubleNode<T> nodo) {
			this.nodo = nodo;
			/* Se arranca en la posicion 2 que es la primera par */
			if (nodo != null)
				this.nodo = nodo.next;
		}

		@Override
		public boolean hasNext() {
			return this.nodo != null;
		}

		@Override
		public T next() {
			if (this.nodo == null)
				throw new NoSuchElementException();
			T elemReturn = this.nodo.elem;
			this.nodo = this.nodo.next;
			/* Si es posible se salta a la siguiente posicion par */
			if (this.nodo != null)
				this.nodo = this.nodo.next;
			return elemReturn;
		}

	}

	@SuppressWarnings("hiding")
	private class ProgressDoubleLinkedListIterator<T> implements Iterator<T> {

		private DoubleNode<T> nodo;
		private int iteratorProgressed;

		public ProgressDoubleLinkedListIterator(DoubleNode<T> nodo) {
			this.nodo = nodo;
			this.iteratorProgressed = 0;
		}

		@Override
		public boolean hasNext() {
			return this.nodo != null;
		}

		@Override
		public T next() {
			if (this.nodo == null)
				throw new NoSuchElementException();
			T elemReturn = this.nodo.elem;
			this.nodo = this.nodo.next;
			/* Si es posible se avanza las veces que toque en la progresion */
			for (int i = 0; i < this.iteratorProgressed; i++)
				if (this.nodo != null)
					this.nodo = this.nodo.next;
			this.iteratorProgressed++;

			return elemReturn;
		}

	}

	////// FIN ITERATOR

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
			/* Si la lista esta vacia se asigna el front al elemento */
			this.front = new DoubleNode<>(elem);
		} else {
			if (this.last == null) {
				this.last = this.front;
				this.front = new DoubleNode<>(elem);
				this.last.prev = this.front;
				this.front.next = this.last;
			} else {
				/*
				 * Si no se crea un nuevo nodo para almacenar el front y llevarlo a la segunda
				 * posicion
				 */
				DoubleNode<T> aux = this.front;
				this.front = new DoubleNode<>(elem);
				this.front.next = aux;
				aux.prev = this.front;
			}
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
			/* Si la lista esta vacia se asigna el front al elemento */
			this.front = new DoubleNode<>(elem);
		} else {
			if (this.last == null) {
				/* Si solo hay un elemento se asigna el last al elemento */
				this.last = new DoubleNode<>(elem);
				this.last.prev = this.front;
				this.front.next = this.last;
			} else {
				/*
				 * Si no se crea un nuevo nodo para almacenar el last y llevarlo a la penultima
				 * posicion
				 */
				DoubleNode<T> aux = this.last;
				this.last = new DoubleNode<>(elem);
				this.last.prev = aux;
				aux.next = this.last;
				aux.prev.next = aux;
			}
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
		if (this.last != null) {
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
		if (--position < 0)
			throw new IllegalArgumentException();

		/*
		 * Se comprueba si se quiere introducir el primero o el ultimo para llamar al
		 * metodo pertinente
		 */
		if (position == 0) {
			this.insertFirst(elem);
			return;
		}
		if (position >= this.size()) {
			this.insertLast(elem);
			return;
		}

		/* Se recorre con un bucle while hasta la posicion indicada como parametro */
		DoubleNode<T> aux = this.front;
		for (int i = 0; i < position; i++)
			aux = aux.next;
		DoubleNode<T> inserted = new DoubleNode<>(elem);

		/* Se enlaza el nuevo elemento en esa posicion */
		inserted.next = aux;
		aux.prev.next = inserted;
		aux.prev = inserted;
	}

	@Override
	public void insertBefore(T elem, T target) {
		/*
		 * Si alguno de los dos elementos pasados como parametro es nulo se lanza la
		 * excepcion marcada en la documentacion
		 */
		if (elem == null || target == null)
			throw new NullPointerException();

		/*
		 * Si la lista no contiene el elemento a buscar se lanza la excepcion marcada en
		 * la documentacion
		 */
		if (!this.contains(target))
			throw new NoSuchElementException();

		/*
		 * Si se llega aqui al menos hay un elemento por lo que se busca en un bucle el
		 * elemento
		 */
		if (this.front.elem.equals(target)) {
			this.insertFirst(elem);
			return;
		}

		DoubleNode<T> aux = this.front;
		DoubleNode<T> inserted = new DoubleNode<>(elem);
		while (aux.elem != target)
			aux = aux.next;
		/* Se enlaza el nuevo elemento en esa posicion */
		aux.prev.next = inserted;
		inserted.prev = aux.prev;
		inserted.next = aux;
	}

	@Override
	public T getElemPos(int position) {
		/*
		 * Se comprueba la validez de la posicion para lanzar la excepcion marcada en la
		 * documentacion
		 */
		if (--position < 0 || position >= this.size())
			throw new IllegalArgumentException();

		/* Se avanza en un bucle desde el inicio hasta la posicion requerida */
		DoubleNode<T> aux = this.front;
		for (int i = 0; i < position; i++)
			aux = aux.next;
		return aux.elem;
	}

	@Override
	public int getPosFirst(T elem) {
		/* Si el elemento es nulo se lanza la excepcion marcada en la documentacion */
		if (elem == null)
			throw new NullPointerException();
		/*
		 * Si la lista no contiene el elemento a buscar se lanza la excepcion marcada en
		 * la documentacion
		 */
		if (!this.contains(elem))
			throw new NoSuchElementException();

		/*
		 * Se avanza en un bucle de principio a fin buscando la primera aparicion del
		 * elemento
		 */
		int pos = 1;
		DoubleNode<T> aux = this.front;
		while (!aux.elem.equals(elem)) {
			aux = aux.next;
			pos++;
		}
		return pos;
	}

	@Override
	public int getPosLast(T elem) {
		/* Si el elemento es nulo se lanza la excepcion marcada en la documentacion */
		if (elem == null)
			throw new NullPointerException();
		/*
		 * Si la lista no contiene el elemento a buscar se lanza la excepcion marcada en
		 * la documentacion
		 */
		if (!this.contains(elem))
			throw new NoSuchElementException();

		/*
		 * Se avanza en un bucle del final hasta el inicio buscando la primera aparicion
		 * del elemento
		 */
		int pos = this.size();
		DoubleNode<T> aux = this.last;
		while (!aux.elem.equals(elem)) {
			aux = aux.prev;
			pos--;
		}
		return pos;
	}

	@Override
	public T removePos(int pos) {
		/*
		 * Si la posicion a eliminar esta fuera de los limites del array se lanza la
		 * excepcion marcada en la documentacion
		 */
		if (pos <= 0 || pos > this.size())
			throw new IllegalArgumentException();

		/*
		 * Si se quiere borrar el unico elemento de la lista se llama al metodo clear
		 */
		if (--pos == 0) {
			T returnElem = this.front.elem;
			if (this.front.next == null) {
				this.clear();
			} else {
				this.front = this.front.next;
				this.front.prev = null;
			}
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

		if (this.size() == 1) {
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
					this.front.prev = null;
				} else {
					aux.prev.next = aux.next;
					if (aux.next != null)
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
		/*
		 * Se recorre la lista desde front a last con un while por si solo hay un
		 * elemento
		 */
		DoubleNode<T> aux = this.front;
		while (aux != null) {
			/* Si se encuentra directamente se devuelve true */
			if (aux.elem.equals(elem))
				return true;
			aux = aux.next;
		}
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
		if (!this.isEmpty()) {
			if (this.last == null)
				return 1;
			DoubleNode<T> aux = this.front;
			while (aux != null) {
				size++;
				aux = aux.next;
			}
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
		/* Se crea una lista vacia y se recorre la actual insertando por el inicio */
		DoubleLinkedListImpl<T> returnedList = new DoubleLinkedListImpl<>();
		DoubleNode<T> aux = this.front;
		while (aux != null) {
			returnedList.insertFirst(aux.elem);
			aux = aux.next;
		}
		return returnedList;
	}

	@Override
	public int maxRepeated() {
		/* Se copia la lista y se recorre buscando las apariciones de cada elemento */
		DoubleList<T> anotherList = this.reverse().reverse();
		int max = 0;
		int actual = 0;
		DoubleNode<T> aux = this.front;
		while (aux != null) {
			if (anotherList.contains(aux.elem)) {
				actual = anotherList.removeAll(aux.elem);
				if (actual > max)
					max = actual;
			}
			aux = aux.next;
		}
		return max;
	}

	@Override
	public boolean isEquals(DoubleList<T> other) {
		/* Si los tamaños son diferentes ya ni se compara */
		if (this.size() != other.size())
			return false;

		/*
		 * Se recorre en un bucle las listas comparando elemento a elemento que todos
		 * son iguales
		 */
		for (int i = 1; i <= this.size(); i++)
			if (this.getElemPos(i) != other.getElemPos(i))
				return false;
		return true;
	}

	@Override
	public boolean containsAll(DoubleList<T> other) {
		/*
		 * Si la otra lista a comparar es nula se lanza la excepcion marcada en la
		 * documentacion
		 */
		if (other == null)
			throw new NullPointerException();

		/*
		 * Se recorre la lista comprobando si cada elemento pertenece tambien a la lista
		 * pasada como parametro
		 */
		for (int i = 1; i <= this.size(); i++)
			if (!other.contains(this.getElemPos(i)))
				return false;

		/*
		 * Si no ha saltado la condicion de parada del for es que todos los elementos de
		 * nuestra lista estan contenidos en ambas
		 */
		return true;
	}

	@Override
	public boolean isSubList(DoubleList<T> other) {
		/*
		 * Si la otra lista a comparar es nula se lanza la excepcion marcada en la
		 * documentacion
		 */
		if (other == null)
			throw new NullPointerException();

		/*
		 * Si la lista a comparar es mayor que nuestra lista ya no va a poder ser una
		 * sublista
		 */
		if (other.size() > this.size())
			return false;

		/* Una lista vacia siempre va a ser una sublista */
		if (other.isEmpty())
			return true;

		/*
		 * Se recorre la lista parametro comprobando si cada elemento pertenece a
		 * nuestra lista
		 */
		DoubleNode<T> aux = this.front;
		while (aux != null) {
			int i = 1;
			while (i <= other.size()) {
				if (this.getElemPos(i) != other.getElemPos(i))
					break;
				else if (i == other.size())
					return true;
				i++;
			}
			aux = aux.next;
		}

		/*
		 * Si no ha saltado la condicion de parada del for es que todos los elementos de
		 * la sublista estan en esta
		 */
		return false;
	}

	@Override
	public String toStringFromUntil(int from, int until) {
		/*
		 * Si los parametros de entrada no cumplen las condiciones se lanza la excepcion
		 * marcada en la documentacion
		 */
		if (from <= 0 || until < from || until > this.size())
			throw new IllegalArgumentException();

		/* Se avanza en un bucle for hasta la posicion de inicio marcada */
		DoubleNode<T> aux = this.front;
		for (int i = 0; i < from; i++)
			aux = aux.next;

		/*
		 * Desde esa posicion se rellena el stringbuilder hasta llegar al parametro fin
		 */
		StringBuilder str = new StringBuilder("(");
		for (int i = 0; i < until; i++) {
			str.append(aux.elem.toString()).append(" ");
			aux = aux.next;
		}
		str.append(")");
		return str.toString();
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
		return new DoubleLinkedListIterator<>(this.front);
	}

	@Override
	public Iterator<T> reverseIterator() {
		return new ReverseDoubleLinkedListIterator<>(this.last);
	}

	@Override
	public Iterator<T> evenPositionsIterator() {
		return new EvenPositionsDoubleLinkedListIterator<>(this.front);
	}

	@Override
	public Iterator<T> progressIterator() {
		return new ProgressDoubleLinkedListIterator<>(this.front);
	}

}
