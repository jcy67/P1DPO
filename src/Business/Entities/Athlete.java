package Business.Entities;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents an athlete participating in the Olympic Games.
 * Stores personal information, delegation details and physical attributes.
 */
public class Athlete {

    public static final int MIN_METER = 0;
    public static final int MAX_METER = 100;

    private String id;
    private PROFILE_TYPE profile;
    private String name;
    private Date birthDate;
    private String delegationCode;
    private int speed;
    private int endurance;
    private int strength;
    private int technique;
    private int precision;

    // Transient prevents Gson from serializing and deserializing this field.
    private transient Delegation delegation;


    public Athlete(String id, PROFILE_TYPE profile, String name, Date birthDate, String delegationCode) {
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.birthDate = birthDate;
        this.delegationCode = delegationCode;
        this.speed = randomAttribute();
        this.endurance = randomAttribute();
        this.strength = randomAttribute();
        this.technique = randomAttribute();
        this.precision = randomAttribute();
    }

    private int randomAttribute() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }




    /**
     * Returns the athlete identifier.
     *
     * @return the athlete identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the athlete profile.
     *
     * @return the athlete profile
     */
    public PROFILE_TYPE getProfile() {
        return profile;
    }

    /**
     * Returns the athlete name.
     *
     * @return the athlete name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the athlete birth date.
     *
     * @return the athlete birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Returns the code of the athlete's delegation.
     *
     * @return the delegation code
     */
    public String getDelegationCode() {
        return delegationCode;
    }

    /**
     * Returns the athlete speed attribute.
     *
     * @return the speed value
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns the athlete endurance attribute.
     *
     * @return the endurance value
     */
    public int getEndurance() {
        return endurance;
    }

    /**
     * Returns the athlete strength attribute.
     *
     * @return the strength value
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Returns the athlete technique attribute.
     *
     * @return the technique value
     */
    public int getTechnique() {
        return technique;
    }

    /**
     * Returns the athlete precision attribute.
     *
     * @return the precision value
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * Returns the delegation associated with the athlete.
     *
     * @return the athlete delegation
     */
    public Delegation getDelegation() {
        return delegation;
    }

    /**
     * Sets the delegation associated with the athlete.
     *
     * @param delegation the delegation to assign
     */
    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    /**
     * Adds points (or subtracts them if negative) to the speed attribute,
     * keeping its value within the allowed limits.
     *
     * @param points the points to add to the speed attribute
     */
    public void addSpeedPoints(int points) {
        this.speed = clamp(this.speed + points);
    }

    /**
     * Adds points (or subtracts them if negative) to the endurance attribute,
     * keeping its value within the allowed limits.
     *
     * @param points the points to add to the endurance attribute
     */
    public void addEndurancePoints(int points) {
        this.endurance = clamp(this.endurance + points);
    }

    /**
     * Adds points (or subtracts them if negative) to the strength attribute,
     * keeping its value within the allowed limits.
     *
     * @param points the points to add to the strength attribute
     */
    public void addStrengthPoints(int points) {
        this.strength = clamp(this.strength + points);
    }

    /**
     * Adds points (or subtracts them if negative) to the technique attribute,
     * keeping its value within the allowed limits.
     *
     * @param points the points to add to the technique attribute
     */
    public void addTechniquePoints(int points) {
        this.technique = clamp(this.technique + points);
    }

    /**
     * Adds points (or subtracts them if negative) to the precision attribute,
     * keeping its value within the allowed limits.
     *
     * @param points the points to add to the precision attribute
     */
    public void addPrecisionPoints(int points) {
        this.precision = clamp(this.precision + points);
    }

    /**
     * Restricts a value to the range between MIN_METER and MAX_METER.
     *
     * @param value the value to restrict
     * @return the value, or the nearest limit if it is out of range
     */
    private int clamp(int value) {
        return Math.max(MIN_METER, Math.min(MAX_METER, value));
    }
}