package q.ArrayBoundedQueue;

import DSR.QueueInterface;
import DSR.QueueOverflowException;
import DSR.QueueUnderflowException;

public class ArrayBoundedQueue<T> implements QueueInterface<T> {
	
	protected final int DEFCAP = 100;
	protected T[] elements;
	protected int origCap;
	protected int numElements = 0;
	protected int front = 0;
	protected int rear;
	
	public ArrayBoundedQueue() {
		elements = (T[]) new Object[DEFCAP];
		rear = DEFCAP - 1;
		origCap = DEFCAP;
	}
	
	public ArrayBoundedQueue(int maxSize) {
		elements = (T[]) new Object[maxSize];
		rear = maxSize - 1;
		this.origCap = origCap;
	}
	
	private void enlarge() {
		T[] larger = (T[]) new Object[elements.length + origCap];
		
		int currSmaller = front;
		for (int currLarger = 0; currLarger < numElements; currLarger++) {
			larger[currLarger] = elements[currSmaller];
			currSmaller = (currSmaller + 1) % elements.length;
		}
		
		elements = larger;
		front = 0;
		rear = numElements - 1;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (numElements == elements.length) {
			enlarge();
		}
		
		rear = (rear + 1) % elements.length;
		elements[rear] = element;
		numElements = numElements + 1;
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException("Dequeue attempted on an empty queue.");
		} else {
			T toReturn = elements[front];
			elements[front] = null;
			front = (front + 1) % elements.length;
			numElements = numElements - 1;
			return toReturn;
		}
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return (numElements == 0);
	}

	@Override
	public int size() {
		
		return numElements;
	}

	
	
}
