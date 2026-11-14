package Business.Entities;

public class Attribute {

    public static final double MIN_METER = 0.0;
    public static final double MAX_METER = 1.0;

    private static final int METER_COUNT = 5;
//    private static final int MOOD_THRIVING_MIN = 75;
//    private static final int MOOD_FINE_MIN = 50;
//    private static final int MOOD_GRUMPY_MIN = 25;

    private double speed;
    private double endurance;
    private double strength;
    private double technique;
    private double precision;

    /**
     * Creates a status with the given meters. the meters are ajusted with clamp()
     *
     * @param speed   food meter
     * @param health endurance meter
     * @param fun    strength meter
     * @param energy technique meter
     */
    public Attribute(double speed, double health, double fun, double energy, double precision) {
        this.speed = clamp(speed);
        this.endurance = clamp(health);
        this.strength = clamp(fun);
        this.technique = clamp(energy);
        this.precision = clamp(precision);
    }

    /**
     * Copy constructor.
     *
     * @param other the status to copy
     */
    public Attribute(Attribute other) {
        this.speed = other.speed;
        this.endurance = other.endurance;
        this.strength = other.strength;
        this.technique = other.technique;
        this.precision = other.precision;
    }

    /**
     * Returns the food meter.
     *
     * @return the food value
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Returns the endurance meter.
     *
     * @return the endurance value
     */
    public double getEndurance() {
        return endurance;
    }

    /**
     * Returns the strength meter.
     *
     * @return the strength value
     */
    public double getStrength() {
        return strength;
    }

    /**
     * Returns the technique meter.
     *
     * @return the technique value
     */
    public double getTechnique() {
        return technique;
    }

    public double getPrecision() {
        return precision;
    }

//    /**
//     * Computes the mood from the (integer) average of the four meters.
//     *
//     *
//     * @return the mood corresponding to this status
//     */
//    public POU_MOOD getMood() {
//        int average = (food + endurance + strength + technique) / METER_COUNT;
//        if (average >= MOOD_THRIVING_MIN) {
//            return POU_MOOD.THRIVING;
//        }
//        if (average >= MOOD_FINE_MIN) {
//            return POU_MOOD.FINE;
//        }
//        if (average >= MOOD_GRUMPY_MIN) {
//            return POU_MOOD.GRUMPY;
//        }
//        return POU_MOOD.MISERABLE;
//    }

    /**
     * Adds points (or subtracts them if negative) to the food meter, keeping
     * it within limits.
     *
     * @param points points to add
     */
    public void addSpeedPoints(double points) {
        this.speed = clamp(this.speed + points);
    }

    /**
     * Adds points (or subtracts them if negative) to the endurance meter, keeping
     * it within limits.
     *
     * @param points points to add
     */
    public void addEndurancePoints(int points) {
        this.endurance = clamp(this.endurance + points);
    }

    /**
     * Adds points (or subtracts them if negative) to the strength meter, keeping
     * it within limits.
     *
     * @param points points to add
     */
    public void addStrengthPoints(double points) {
        this.strength = clamp(this.strength + points);
    }

    /**
     * Adds points (or subtracts them if negative) to the technique meter, keeping
     * it within limits.
     *
     * @param points points to add
     */
    public void addTechniquePoints(double points) {
        this.technique = clamp(this.technique + points);
    }

    public void addPrecisionPoints(double points) { this.precision = clamp(this.precision + points); }

    /**
     * Restricts a value to the range.
     *
     * @param value the value to restrict
     * @return the value, or the nearest limit if it was out of range
     */
    private double clamp(double value) {
        return Math.max(MIN_METER, Math.min(MAX_METER, value));
    }

}
