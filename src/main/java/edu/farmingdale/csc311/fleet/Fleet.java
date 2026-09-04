package edu.farmingdale.csc311.fleet;

/**
 * A named group of vehicles stored in a plain array.
 * No ArrayList, no HashMap. Arrays and loops only.
 *
 * Note: used git copilot to:
 * - help write the skeleton of the contains method, the add method, the findbyVin method,
 * the removebyVin method, the toArray method, the sortedByYear method, the countWithFuelType method,
 * the averageEngineSize method, and the longestRange method.
 *
 *
 * @author Carlos Gonzalez
 */
public class Fleet {

    public static final int MAX_VEHICLES = 25;

    /* ------------------------------------------------------------------
     * TODO-08     commit: TODO-08: implement Fleet storage
     *
     * 1. Add three private fields:
     *        name        String, final
     *        vehicles    Vehicle[], final, sized MAX_VEHICLES
     *        count       int, how many slots are actually used
     *
     * 2. The constructor checks name (not null, not blank) and trims it.
     *
     * 3. Methods:
     *
     *    contains(Vehicle v)
     *        loop over the used slots and return true if one equals v.
     *        Use the equals you wrote in TODO-05, not ==.
     *
     *    add(Vehicle v)
     *        null argument           throw IllegalArgumentException
     *        already in the fleet    return false, store nothing
     *        array full              return false
     *        otherwise               store at index count, count++, return true
     *
     *    removeByVin(String vin)
     *        find the slot whose VIN matches, ignoring case. Shift every
     *        later element one place left, null out the old last slot,
     *        count--, return true. Return false when nothing matched or
     *        the vin was null or blank.
     *
     *    findByVin(String vin)
     *        return the matching Vehicle, ignoring case, or null.
     *
     *    size()
     *        return count.
     *
     *    toArray()
     *        return a NEW array of length count holding the vehicles in
     *        insertion order. Returning the internal array lets a caller
     *        overwrite your slots, so copy it.
     * ------------------------------------------------------------------ */

    private final String name;
    private final Vehicle[] vehicles;
    private int count;

    public Fleet(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        this.name = name.trim();
        this.vehicles = new Vehicle[MAX_VEHICLES];
        this.count = 0;
    }

    public String getName() {
        return name;
    }

    public boolean contains(Vehicle vehicle) {
        for(int i = 0; i < count; i++) {
            if (vehicles[i].equals(vehicle)) {
                return true;
            }
        }
        return false;
    }

    public boolean add(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (contains(vehicle)) {
            return false;
        }
        if (count >= MAX_VEHICLES) {
            return false;
        }
        vehicles[count] = vehicle;
        count++;
        return true;
    }

    public boolean removeByVin(String vin) {
        if (vin == null || vin.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < count; i++) {
            if (vehicles[i].getVin().equalsIgnoreCase(vin.trim())) {
                // Shift elements to the left
                for (int j = i; j < count - 1; j++) {
                    vehicles[j] = vehicles[j + 1];
                }
                vehicles[count - 1] = null; // Null out the last slot
                count--;
                return true;
            }
        }
        return false;
    }

    public Vehicle findByVin(String vin) {
        if (vin == null || vin.trim().isEmpty()) {
            return null;
        }
        for (int i = 0; i < count; i++) {
            if (vehicles[i].getVin().equalsIgnoreCase(vin.trim())) {
                return vehicles[i];
            }
        }
        return null;
    }

    public int size() {
        return count;
    }

    public Vehicle[] toArray() {
        Vehicle[] result = new Vehicle[count];
        System.arraycopy(vehicles, 0, result, 0, count);
        return result;
    }

    /* ------------------------------------------------------------------
     * TODO-09     commit: TODO-09: implement Fleet reports
     *
     * None of these may reorder or change the internal array. Start from
     * toArray() when you need a different order.
     *
     *    sortedByYear()
     *        a new array ordered by year, oldest first. When two years
     *        match, order by make A to Z ignoring case
     *        (String.compareToIgnoreCase). Write the sort yourself:
     *        selection sort or insertion sort, your choice. No Arrays.sort,
     *        no Comparator.
     *
     *    countWithFuelType(FuelType fuel)
     *        how many vehicles use that fuel.
     *
     *    averageEngineSize()
     *        average engine size over the vehicles whose fuel type has an
     *        engine. Electrics are left out, otherwise their 0.0 drags the
     *        number down and it means nothing. Return 0.0 when the count is
     *        zero, and watch the division.
     *
     *    longestRange()
     *        the vehicle with the largest rangeInMiles(), or null when the
     *        fleet is empty. On a tie keep the one added first. Note that
     *        this compares cars against trucks without a single if about
     *        the type: rangeInMiles() already knows which formula to run.
     * ------------------------------------------------------------------ */

    public Vehicle[] sortedByYear() {
        Vehicle[] sortedArray = toArray();
        for (int i = 0; i < sortedArray.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < sortedArray.length; j++) {
                if (sortedArray[j].getYear() < sortedArray[minIndex].getYear() ||
                        (sortedArray[j].getYear() == sortedArray[minIndex].getYear() &&
                                sortedArray[j].getMake().compareToIgnoreCase(sortedArray[minIndex].getMake()) < 0)) {
                    minIndex = j;
                }
            }
            // Swap the found minimum element with the first element
            Vehicle temp = sortedArray[minIndex];
            sortedArray[minIndex] = sortedArray[i];
            sortedArray[i] = temp;
        }
        return sortedArray;
    }

    public int countWithFuelType(FuelType fuel) {
        int total = 0;
        for (int i = 0; i < count; i++) {
            if (vehicles[i].getFuelType() == fuel) {
                total++;
            }
        }
        return total;
    }

    public double averageEngineSize() {
        double total = 0.0;
        int engineCount = 0;
        for (int i = 0; i < count; i++) {
            if (vehicles[i].getFuelType().hasEngine()) {
                total += vehicles[i].getEngineSize();
                engineCount++;
            }
        }
        return engineCount > 0 ? total / engineCount : 0.0;
    }

    public Vehicle longestRange() {
        if (count == 0) {
            return null;
        }
        Vehicle longestRangeVehicle = vehicles[0];
        for (int i = 1; i < count; i++) {
            if (vehicles[i].rangeInMiles() > longestRangeVehicle.rangeInMiles()) {
                longestRangeVehicle = vehicles[i];
            }
        }
        return longestRangeVehicle;
    }
}
