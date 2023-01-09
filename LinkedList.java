

public class LinkedList<T> implements List<T> {

	private Node<T> head;
	private Node<T> current;
	public LinkedList() {
		this.head = null;
		this.current = null;
	}

	private class Node<T> {
		public T data;
		public Node next;
	}

	@Override
	public boolean empty() {
		// TODO Auto-generated method stub
		return this.head == null;
	}

	@Override
	public boolean full() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void findFirst() {
		// TODO Auto-generated method stub
		this.current = this.head;
	}

	@Override
	public void findNext() {
		// TODO Auto-generated method stub
		if(this.current != null)
			this.current = this.current.next;
	}

	@Override
	public boolean last() {
		// TODO Auto-generated method stub
		return this.current == null || this.current.next == null;
	}

	@Override
	public T retrieve() {
		// TODO Auto-generated method stub
		return this.current != null ? this.current.data : null;
	}

	@Override
	public void update(T e) {
		this.current.data = e;
		// TODO Auto-generated method stub
	}

	@Override
	public void insert(T e) {
		// TODO Auto-generated method stub
		Node<T> node = new Node<T>();
		node.data = e;
		if(this.head == null) {
			this.head = node;
			return;
		}
		Node<T> temp = this.head;
		if (temp.next != null) {
			do {
				temp = temp.next;
			} while (temp.next != null);
		}
		temp.next = node;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		if(this.head == this.current) {
			this.head = this.head.next;
			this.current = this.head;
		}else {
			Node<T> temp = this.head;
			while (temp.next != this.current) {
				temp = temp.next;
			}
			temp.next = this.current.next;
			this.current = this.current.next;
		}
	}

        public String toString() {
            String res = "[";
            Node temp = this.head;
            while(temp != null) {
                res += temp.data + ",";
                temp = temp.next;
            }
            return res + "]";
        }
}
