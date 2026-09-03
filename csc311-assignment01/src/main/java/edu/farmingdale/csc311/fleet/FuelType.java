package edu.farmingdale.csc311.fleet;

/**
 * The fuels a fleet vehicle can run on.
 *
 * @author Carlos Gonzalez
 */
public enum FuelType {

    /* ------------------------------------------------------------------
     * TODO-01     commit: TODO-01: add FuelType data and lookup
     *
     * Each constant carries three values:
     *
     *      constant   label        unit        milesPerUnit
     *      GASOLINE   "Gasoline"   "gallons"   28.0
     *      DIESEL     "Diesel"     "gallons"   34.0
     *      ELECTRIC   "Electric"   "kWh"        3.2
     *      HYBRID     "Hybrid"     "gallons"   48.0
     *
     * Pass those values to each constant, add three private final fields,
     * write the constructor, then finish the five methods below.
     * ------------------------------------------------------------------ */

    GASOLINE("Gasoline", "gallons", 28.0, true),
    DIESEL("Diesel", "gallons", 34.0, true),
    ELECTRIC("Electric", "kWh", 3.2, false),
    HYBRID("Hybrid", "gallons", 48.0, true);

    private final String label;
    private final String unit;
    private final double milesPerUnit;
    private final boolean hasEngine;

    FuelType(String label, String unit, double milesPerUnit, boolean hasEngine) {
        this.label = label;
        this.unit = unit;
        this.milesPerUnit = milesPerUnit;
        this.hasEngine = hasEngine;
    }

    public String getLabel() {
        return label;
    }

    public String getUnit() {
        return unit;
    }

    public double getMilesPerUnit() {
        return milesPerUnit;
    }

    /** False for ELECTRIC, true for the rest. */
    public boolean hasEngine() {
        return hasEngine;
    }

    /**
     * Finds a constant by its label, ignoring case and outer spaces.
     * Throws IllegalArgumentException if the text matches nothing.
     */
    public static FuelType fromLabel(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Label must not be null");
        }
        String t = text.trim();
        for (FuelType ft : values()) {
            if (ft.label.equalsIgnoreCase(t)) {
                return ft;
            }
        }
        throw new IllegalArgumentException("No FuelType for label: " + text);
    }
}
