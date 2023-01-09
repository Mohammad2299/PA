


public class VehicleHiringManager {

	LocatorMap<String> locatorMap = new TreeLocatorMap<>();

	public VehicleHiringManager() {
	}

	// Returns the locator map.
	public LocatorMap<String> getLocatorMap() {
		return this.locatorMap;
	}

	// Sets the locator map.
	public void setLocatorMap(LocatorMap<String> locatorMap) {
		this.locatorMap = locatorMap;
	}

	// Inserts the vehicle id at location loc if it does not exist and returns true.
	// If id already exists, the method returns false.
	public boolean addVehicle(String id, Location loc) {
		Pair<Boolean, Integer> add = this.locatorMap.add(id, loc);
		return add.first;
	}

	// Moves the vehicle id to location loc if id exists and returns true. If id not
	// exist, the method returns false.
	public boolean moveVehicle(String id, Location loc) {
		Pair<Boolean, Integer> move = this.locatorMap.move(id, loc);
		return move.first;
	}

	// Removes the vehicle id if it exists and returns true. If id does not exist,
	// the method returns false.
	public boolean removeVehicle(String id) {
		Pair<Boolean, Integer> remove = this.locatorMap.remove(id);
		return remove.first;
	}

	// Returns the location of vehicle id if it exists, null otherwise.
	public Location getVehicleLoc(String id) {

		return this.locatorMap.getLoc(id).first;
	}

	// Returns all vehicles located within a square of side 2*r centered at loc
	// (inclusive of the boundaries).
	public List<String> getVehiclesInRange(Location loc, int r) {
		return 	this.locatorMap.getInRange(
				new Location(loc.x-r, loc.y-r), new Location(loc.x+r, loc.y+r)).first;
	}
}
