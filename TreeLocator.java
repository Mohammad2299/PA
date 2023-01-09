

import java.util.ArrayList;

public class TreeLocator<T> implements Locator<T> {
    private Node root;

    public TreeLocator() {
        this.root = null;
    }

    void print() {
        printHelper(root);
    }

    void printHelper(Node node) {
        if(node == null) return;
        System.out.println(node.location + "->" + node.keys);
        if(node.SW != null) {
            printHelper(node.SW);
            
        }
        
        if(node.NW != null) {
            printHelper(node.NW);
        }
//                    System.out.println(node.location + "->" + node.keys);

        if(node.NE != null) {
            printHelper(node.NE);
        }
//                    System.out.println(node.location + "->" + node.keys);

        if(node.SE != null) {
            printHelper(node.SE);
        }
//                    System.out.println(node.location + "->" + node.keys);

    }
    
    private class Node {
        public Location location;
        public List<T> keys;
        public Node SW;
        public Node NW;
        public Node NE;
        public Node SE;
    }

    @Override
    public int add(T e, Location loc) {
        // TODO Auto-generated method stub
        int counter = 0;
        if (this.root == null) {
            Node node = new Node();
            node.keys = new LinkedList<>();
            node.keys.insert(e);
            node.location = new Location(loc.x, loc.y);
            this.root = node;
            return counter;
        } else {
            Node temp = this.root;
            do {
                counter++;
                if (andOperator(loc.x == temp.location.x, loc.y == temp.location.y)) {
                    temp.keys.insert(e);
                    break;
                }
                if (andOperator(loc.x < temp.location.x, loc.y <= temp.location.y)) {
                    if (temp.SW == null) {
                        Node node = new Node();
                        node.keys = new LinkedList<>();
                        node.keys.insert(e);
                        node.location = new Location(loc.x, loc.y);
                        temp.SW = node;
                        return counter;
                    }
                    temp = temp.SW;
                }
                if (andOperator(loc.x <= temp.location.x, loc.y > temp.location.y)) {
                    if (temp.NW == null) {
                        Node node = new Node();
                        node.keys = new LinkedList<>();
                        node.keys.insert(e);
                        node.location = new Location(loc.x, loc.y);
                        temp.NW = node;
                        return counter;
                    }
                    temp = temp.NW;
                }
                if (andOperator(loc.x > temp.location.x, loc.y >= temp.location.y)) {
                    if (temp.NE == null) {
                        Node node = new Node();
                        node.keys = new LinkedList<>();
                        node.keys.insert(e);
                        node.location = new Location(loc.x, loc.y);
                        temp.NE = node;
                        return counter;
                    }
                    temp = temp.NE;
                }
                if (andOperator(loc.x >= temp.location.x, loc.y < temp.location.y)) {
                    if (temp.SE == null) {
                        Node node = new Node();
                        node.keys = new LinkedList<>();
                        node.keys.insert(e);
                        node.location = new Location(loc.x, loc.y);
                        temp.SE = node;
                        return counter;
                    }
                    temp = temp.SE;
                }
            } while (true);
            return counter;
        }
    }

    @Override
    public Pair<List<T>, Integer> get(Location loc) {
        Node temp = this.root;
        Integer counter = 0;
        if (temp != null) {
            do {
                counter++;
                if (andOperator(temp.location.x == loc.x, temp.location.y == loc.y)) {
                    return new Pair<List<T>, Integer>(temp.keys, counter);
                }
                if (andOperator(loc.x < temp.location.x, loc.y <= temp.location.y)) {
                    temp = temp.SW;
                    continue;
                }
                if (andOperator(loc.x <= temp.location.x, loc.y > temp.location.y)) {
                    temp = temp.NW;
                    continue;
                }
                if (andOperator(loc.x > temp.location.x, loc.y >= temp.location.y)) {
                    temp = temp.NE;
                    continue;
                }
                if (andOperator(loc.x >= temp.location.x, loc.y < temp.location.y)) {
                    temp = temp.SE;
                    continue;
                }
            } while (temp != null);
        }
        return new Pair<List<T>, Integer>(new LinkedList<T>(), counter);
    }

    @Override
    public Pair<Boolean, Integer> remove(T e, Location loc) {
        // TODO Auto-generated method stub
        Pair<List<T>, Integer> list = this.get(loc);
        list.first.findFirst();
        Boolean removed = false;
        if (list.first != null && list.first.retrieve() != null) {
            do {
                if (list.first.retrieve().equals(e)) {
                    list.first.remove();
                    removed = true;
                } else {
                    list.first.findNext();
                }
            } while (list.first != null && list.first.retrieve() != null);
        }
        return new Pair<>(removed, list.second);
    }

     @Override
    public List<Pair<Location, List<T>>> getAll()
    {
        Node p = this.root;
        List<Pair<Location , List<T>>> list = new LinkedList<>();
        recursive(p, list);
        return list;
    }

    private void recursive(Node p,  List<Pair<Location, List<T>>>  keys) {
        if (p != null)
        {
            keys.insert(new Pair(p.location , p.keys));
            final boolean name = p.SW != null;
            if(name) {
                recursive(p.SW, keys);
            } else {
                
            }
            final boolean name1 = p.NW != null;
            if(name1) {
                recursive(p.NW, keys);
            } else {
            }
            final boolean name2 = p.NE != null;
            if(name2) {
                recursive(p.NE, keys);
            } else {
            }
            if(p.SE != null) {
                recursive(p.SE, keys);
            } else {
            }
        }
        else {
            return;
        }
    }

    @Override
    public Pair<List<Pair<Location, List<T>>>, Integer> inRange(Location lowerLeft, Location upperRight)
    {
        Node p = this.root;
        List<Pair<Location, List<T>>> list = new LinkedList<>();
        int count = getInRange(0 , list , p , lowerLeft , upperRight );
        return new Pair<>(list , count);
    }

    private int getInRange( int count ,List<Pair<Location, List<T>>> list , Node node, Location lowerLeft, Location upperRight)
    {
        final boolean name = node != null;
        if (name) {
            count++;
            final Location location = node.location;
            final boolean valid = isvalid(lowerLeft, upperRight, location);
            if (valid) {
                list.insert(new Pair(location, node.keys));
            } else {
            }
            final boolean validCh1 = isvalidCh1(lowerLeft, location);
            if (validCh1) {
                count += getInRange(0, list, node.SW, lowerLeft, upperRight);
            } else {
            }
            final boolean validCh2 = isvalidCh2(lowerLeft, upperRight, location);
            if (validCh2) {
                count += getInRange(0, list, node.NW, lowerLeft, upperRight);
            } else {
            }
            final boolean name1 = !isvalidCh3(upperRight, location);
            if (name1) {
            } else {
                count += getInRange(0, list, node.NE, lowerLeft, upperRight);
            }
            final boolean validCh4 = isvalidCh4(lowerLeft, upperRight, location);
            if (validCh4) {
                count += getInRange(0, list, node.SE, lowerLeft, upperRight);
            } else {
            }
            return count;
        } else {
            return count;
        }
    }

    private boolean isvalid(Location lowerLeft, Location upperRight , Location current)
    {
        final boolean name = lowerLeft.x <= current.x;
        final boolean name1 = upperRight.x >= current.x;
        boolean bool = (name && lowerLeft.y <= current.y) && (name1 && upperRight.y >= current.y);
        return bool;
    }
    private boolean isvalidCh1(Location lowerLeft , Location current)
    {
        final boolean name = lowerLeft.x < current.x;
        return name && lowerLeft.y<=current.y;
    }
    private boolean isvalidCh2(Location lowerLeft, Location upperRight , Location current)
    {
        final boolean name = lowerLeft.x <= current.x;
        boolean b = name && upperRight.y > current.y;
        return b;
    }
    private boolean isvalidCh3(Location upperRight , Location current)
    {
        final boolean name = upperRight.x>current.x;
        return name && upperRight.y>=current.y;
    }
    private boolean isvalidCh4(Location lowerLeft, Location upperRight , Location current)
    {
        final boolean name = upperRight.x>=current.x;
        return name && lowerLeft.y<current.y;
    }
    private boolean insert4(Node temp, boolean inserted, List<Node> queue, ArrayList<Node> visited) {
        if (notNull( temp.SE)) {
//                    inserted = true;
inserted = insertToQueue(queue,  temp.SE, visited);
        }
        return inserted;
    }

    private boolean insert3(Node temp, boolean inserted, List<Node> queue, ArrayList<Node> visited) {
        if (notNull( temp.NE)) {
//                    inserted = true;
inserted = insertToQueue(queue,  temp.NE, visited);
        }
        return inserted;
    }

    private boolean insert2(Node temp, boolean inserted, List<Node> queue, ArrayList<Node> visited) {
        if (notNull( temp.NW)) {
            inserted = insertToQueue(queue,  temp.NW, visited);
        }
        return inserted;
    }

    private boolean insert1(Node temp, boolean inserted, List<Node> queue, ArrayList<Node> visited) {
        if (notNull(temp.SW)) {
            
            inserted = insertToQueue((List<Node>) queue, temp.SW, visited);
        }
        return inserted;
    }

    private boolean andOperator(boolean cond1, boolean cond2) {
        return cond1 &&
                cond2;
    }

    private boolean notNull(Node sw) {
        return sw != null;
    }

    private boolean insertToQueue(List<Node> queue, Node node , ArrayList<Node> visited) {
        if(!visited.contains(node)) {
            queue.insert(node);
            visited.add(node);
            return true;
        }
        return false;
    }
}
