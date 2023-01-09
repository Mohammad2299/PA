


public class TreeLocatorMap<K extends Comparable<K>> implements LocatorMap<K> {

	private Map<K, Location> map = new BST<>();
	private Locator<K> locator = new TreeLocator<K>();
	@Override
	public Map<K, Location> getMap() {
		// TODO Auto-generated method stub
		return this.map;
	}

	@Override
	public Locator<K> getLocator() {
		// TODO Auto-generated method stub
		return this.locator;
	}

	@Override
	public Pair<Boolean, Integer> add(K k, Location loc) {
		if(k == null || loc == null) return new Pair<>(false, 0);
		Pair<Boolean, Integer> insert = map.insert(k, loc);
		if(insert.first) {
			int add = locator.add(k, loc);
			return new Pair<>(insert.first, insert.second + add);
		}
		return new Pair<>(insert.first, insert.second);
	}

	@Override
	public Pair<Boolean, Integer> move(K k, Location loc) {
		// TODO Auto-generated method stub
                if(k == null || loc == null) return new Pair<>(false, 0);
		Pair<Boolean, Integer> pair = this.map.find(k);
		if(pair.first) {
			Pair<Boolean, Integer> pair1 = this.locator.remove(k, this.map.retrieve());
			this.map.update(loc);
			int count = this.locator.add(k, loc);
			return new Pair<>(pair.first, pair.second + pair1.second + count);
		}
		return new Pair<>(pair.first, pair.second);
	}

	@Override
	public Pair<Location, Integer> getLoc(K k) {
		// TODO Auto-generated method stub
                if(k == null)return new Pair<>(null, 0);
		Pair<Boolean, Integer> pair = this.map.find(k);
		if(pair.first)
			return new Pair<>(this.map.retrieve(),pair.second);
		else{
			return new Pair<>(null,pair.second);
		}
	}

	@Override
	public Pair<Boolean, Integer> remove(K k) {
		// TODO Auto-generated method stub
                if(k == null) return new Pair<>(false, 0);
		Pair<Boolean, Integer> pair = this.map.find(k);
		Pair<Boolean, Integer> pair1 = new Pair<>(false, 0);
		if(pair.first) {
			pair1 = this.locator.remove(k, this.map.retrieve());
                        this.map.remove(k);
		}

		return new Pair<>(pair.first, pair.second + pair1.second);
	}

	@Override
	public List<K> getAll() {
		// TODO Auto-generated method stub
		return this.map.getAll();
	}

	@Override
	public Pair<List<K>, Integer> getInRange(Location lowerLeft, Location upperRight) {
            if(lowerLeft == null || upperRight == null) return new Pair<>(null, 0);
		// TODO Auto-generated method stub
		List<K> list = new LinkedList<>();
		Pair<List<Pair<Location, List<K>>>, Integer> listIntegerPair = this.locator.inRange(lowerLeft, upperRight);
		listIntegerPair.first.findFirst();
		if (!listIntegerPair.first.last()) {
			do {
				Pair<Location, List<K>> pair = listIntegerPair.first.retrieve();
				pair.second.findFirst();
				while (!pair.second.last()) {
					list.insert(pair.second.retrieve());
					pair.second.findNext();
				}
				list.insert(pair.second.retrieve());
				pair.second.findNext();
				listIntegerPair.first.findNext();
			} while (!listIntegerPair.first.last());
		}
		if(!listIntegerPair.first.empty()){
			Pair<Location, List<K>> pair = listIntegerPair.first.retrieve();
			pair.second.findFirst();
			while (!pair.second.last()) {
				list.insert(pair.second.retrieve());
				pair.second.findNext();
			}
			list.insert(pair.second.retrieve());
			pair.second.findNext();
			listIntegerPair.first.findNext();
		}
		return new Pair<>(list, listIntegerPair.second);
	}

}
