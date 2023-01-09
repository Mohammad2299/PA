

public class BST<K extends Comparable<K>, T> implements Map<K, T> {
	private List<K> keys;
	private Node root;
	private Node current;
	private List<K> sortedKeys;
	public BST() {
		this.root = null;
		this.current = null;
		this.keys = new LinkedList<K>();
	}

                    void print() {
        printHelper(this.root);
    }

    void printHelper(Node node) {
        if(node == null) return;
        if(node.right != null) printHelper(node.right);
        System.out.println(node.key + "" +node.data);
        if(node.left != null) printHelper(node.left);
//        System.out.println(node.key + "" +node.data);

    }
        
        
	private class Node {
		public T data;
		public K key;
		public Node left;
		public Node right;
	}

	@Override
	public boolean empty() {
		// TODO Auto-generated method stub
		return this.root == null;
	}

	@Override
	public boolean full() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T retrieve() {
		// TODO Auto-generated method stub
		return this.current.data;
	}

	@Override
	public void update(T e) {
		// TODO Auto-generated method stub
		if(this.current != null)
			this.current.data = e;
	}

	@Override
	public Pair<Boolean, Integer> find(K key) {
		// TODO Auto-generated method stub
		Node temp = this.root;
		Integer counter =0;
		do {
			if (temp != null) {
				counter++;
				if (temp.key.compareTo(key) == 0) {
					this.current = temp;
					return new Pair<Boolean, Integer>(true, counter);
				}
				if (temp.key.compareTo(key) < 0) {
					temp = temp.left;
				} else {
					temp = temp.right;
				}
			} else {
				return new Pair<Boolean, Integer>(false, counter);
			}

		} while (true);
	}

	@Override
	public Pair<Boolean, Integer> insert(K key, T data) {
		// TODO Auto-generated method stub
		Node node = new Node();
		node.data = data;
		node.key = key;

		if(this.root == null) {
			this.root = node;
			this.current = this.root;
			return new Pair<Boolean, Integer>(true, 0);
		}else {
			Integer counter = 0;
			Node temp = this.root;
			do {
				counter++;
				if (temp.key.compareTo(key) == 0) {
					return new Pair<>(false, counter);
				}
				if (temp.key.compareTo(key) < 0) {
					if (temp.left == null) {
						temp.left = node;
						this.current = node;
						this.keys.insert(key);
						return new Pair<>(true, counter);
					}
					temp = temp.left;
				} else {
					if (temp.right == null) {
						temp.right = node;
						this.current = node;
						this.keys.insert(key);
						return new Pair<>(true, counter);
					}
					temp = temp.right;
				}
			} while (true);
		}
	}

	@Override
	public Pair<Boolean, Integer> remove(K key) {
		// TODO Auto-generated method stub
		Pair<Boolean, Integer> result = this.find(key);
		if(result.first) {
			this.keys.findFirst();
			while (!this.keys.last()){
				if(this.keys.retrieve().equals(key)){
					this.keys.remove();
				}else{
					this.keys.findNext();
				}
			}
			if(this.current == this.root && this.isLeaf(this.current)){
				this.root = null;
				this.current = null;
				return result;
			}
			if(this.isLeaf(this.current)) {
				Pair<Node, Integer> parent = this.getParent(this.current);
				if(parent.second == 1){
					this.current = null;
					parent.first.right = null;
					return result;
				} else {
					this.current = null;
					parent.first.left = null;
					return result;
				}
			}else {
				Pair<Node, Pair<Node, Integer>> BL = getBL(this.current);
				Pair<Node, Pair<Node, Integer>> SR = getSR(this.current);
				if(BL != null) {
					this.current.key = BL.first.key;
					this.current.data = BL.first.data;
					if(BL.second.second == 1){
						BL.second.first.right = null;
						this.current = null;
						return result;
					}else {
						BL.second.first.left = null;
						this.current = null;
						return result;
					}
				}else {
					this.current.key = SR.first.key;
					this.current.data = SR.first.data;
					if(SR.second.second == 1){
						SR.second.first.right = null;
						this.current = null;
						return result;
					}else {
						SR.second.first.left = null;
						this.current = null;
						return result;
					}
				}
			}


		}
		return result;
	}

	@Override
	public List<K> getAll() {
		// TODO Auto-generated method stub
                this.sortedKeys = new LinkedList<>();
                if(root != null)
		DFS(this.root);
		return this.sortedKeys;
	}

	private void DFS(Node node){
			
		if(node.right != null)
			DFS(node.right);
                this.sortedKeys.insert(node.key);
		if(node.left != null)
			DFS(node.left);
	}

	private Boolean isLeaf(Node node){
		return node.left == null && node.right == null;
	}

	private Pair<Node, Pair<Node, Integer>> getBL(Node node) {
		if(node.left == null)
			return null;
		if(node.left.right == null){
			return new Pair<>(node.left, new Pair<Node, Integer>(node, -1));
		}
		Node temp = node.left;
		Node prev = null;
		while (true) {
			if (temp.right != null) {
				prev = temp;
				temp = temp.right;
			} else {
				break;
			}
		}
		return new Pair<>(temp, new Pair<Node, Integer>(prev, 1));
	}
	private Pair<Node, Pair<Node, Integer>> getSR(Node node) {
		if(node.right == null)
			return null;
		if(node.right.left == null){
			return new Pair<>(node.right, new Pair<Node, Integer>(node, 1));
		}
		Node temp = node.right;
		Node prev = null;
		while (true) {
			if (temp.left != null) {
				prev = temp;
				temp = temp.left;
			} else {
				break;
			}
		}
		return new Pair<>(temp, new Pair<Node, Integer>(prev, -1));
	}

	private Pair<Node, Integer> getParent(Node node) {
		Node temp = this.root;
		do {
			if (temp == null) {
				return null;
			}
			if (temp.left == node) {
				return new Pair<>(temp, -1);
			}
			if (temp.right == node) {
				return new Pair<>(temp, 1);
			}
			if (temp.key.compareTo(node.key) < 0) {
				temp = temp.left;
			} else {
				temp = temp.right;
			}
		} while (true);
	}
}
