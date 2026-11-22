package Business.Entities;

/**
 * Represents the attribute weights of an Olympic competition.
 * Stores the importance of speed, endurance, strength, technique
 * and precision, with values between 0.0 and 1.0.
 */
public class Weight {

    public static final double MIN_METER = 0.0;
    public static final double MAX_METER = 1.0;

    private double speed;
    private double endurance;
    private double strength;
    private double technique;
    private double precision;

    /**
     * Creates an empty Weight object.
     */
    public Weight() {}

    /**
     * Creates a Weight object with the given attribute weights.
     * Each value is restricted to the allowed range using clamp().
     *
     * @param speed the speed weight
     * @param health the endurance weight
     * @param fun the strength weight
     * @param energy the technique weight
     * @param precision the precision weight
     */
    public Weight(double speed, double health, double fun,
                  double energy, double precision) {

        this.speed = clamp(speed);
        this.endurance = clamp(health);
        this.strength = clamp(fun);
        this.technique = clamp(energy);
        this.precision = clamp(precision);
    }

    /**
     * Creates a copy of another Weight object.
     *
     * @param other the Weight object to copy
     */
    public Weight(Weight other) {
        this.speed = other.speed;
        this.endurance = other.endurance;
        this.strength = other.strength;
        this.technique = other.technique;
        this.precision = other.precision;
    }

    /**
     * Returns the speed weight.
     *
     * @return the speed weight
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Returns the endurance weight.
     *
     * @return the endurance weight
     */
    public double getEndurance() {
        return endurance;
    }

    /**
     * Returns the strength weight.
     *
     * @return the strength weight
     */
    public double getStrength() {
        return strength;
    }

    /**
     * Returns the technique weight.
     *
     * @return the technique weight
     */
    public double getTechnique() {
        return technique;
    }

    /**
     * Returns the precision weight.
     *
     * @return the precision weight
     */
    public double getPrecision() {
        return precision;
    }

    /**
     * Adds points to the speed weight, or subtracts them if negative.
     * Keeps the value within the allowed range.
     *
     * @param points the points to add to the speed weight
     */
    public void addSpeedPoints(double points) {
        this.speed = clamp(this.speed + points);
    }

    /**
     * Adds points to the endurance weight, or subtracts them if negative.
     * Keeps the value within the allowed range.
     *
     * @param points the points to add to the endurance weight
     */
    public void addEndurancePoints(int points) {
        this.endurance = clamp(this.endurance + points);
    }

    /**
     * Adds points to the strength weight, or subtracts them if negative.
     * Keeps the value within the allowed range.
     *
     * @param points the points to add to the strength weight
     */
    public void addStrengthPoints(double points) {
        this.strength = clamp(this.strength + points);
    }

    /**
     * Adds points to the technique weight, or subtracts them if negative.
     * Keeps the value within the allowed range.
     *
     * @param points the points to add to the technique weight
     */
    public void addTechniquePoints(double points) {
        this.technique = clamp(this.technique + points);
    }

    /**
     * Adds points to the precision weight, or subtracts them if negative.
     * Keeps the value within the allowed range.
     *
     * @param points the points to add to the precision weight
     */
    public void addPrecisionPoints(double points) {
        this.precision = clamp(this.precision + points);
    }

    /**
     * Restricts a value to the range between MIN_METER and MAX_METER.
     *
     * @param value the value to restrict
     * @return the value, or the nearest limit if it is out of range
     */
    private double clamp(double value) {
        return Math.max(MIN_METER, Math.min(MAX_METER, value));
    }
}