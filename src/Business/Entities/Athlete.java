package Business.Entities;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Athlete {


    public static final int MIN_METER = 0;
    public static final int MAX_METER = 100;

    private  String id;
    private  PROFILE_TYPE profile;
    private  String name;
    private Date birthDate;
    private  String delegationCode;
    private int speed;
    private int endurance;
    private int strength;
    private int technique;
    private int precision;

    // Transient es para que el Gson lo ignore al crear el objeto
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



    public String getId() {
        return id;
    }

    public PROFILE_TYPE getProfile() {
        return profile;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getDelegationCode() {
        return delegationCode;
    }

    public int getSpeed() {
        return speed;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getStrength() {
        return strength;
    }

    public int getTechnique() {
        return technique;
    }

    public int getPrecision() {
        return precision;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    /**
     * Adds points (or subtracts them if negative) to the food meter, keeping
     * it within limits.
     *
     * @param points points to add
     */
    public void addSpeedPoints(int points) {
        this.speed = clamp(this.speed + points);
    }

    /**
     * Adds points (or subtracts them if negative) to the health meter, keeping
     * it within limits.
     *
     * @param points points to add
     */
    public void addEndurancePoints(int points) {
        this.endurance = clamp(this.endurance + points);
    }

    /**
     * Adds points (or subtracts them if negative) to the fun meter, keeping
     * it within limits.
     *
     * @param points points to add
     */
    public void addStrengthPoints(int points) {
        this.strength = clamp(this.strength + points);
    }

    /**
     * Adds points (or subtracts them if negative) to the energy meter, keeping
     * it within limits.
     *
     * @param points points to add
     */
    public void addTechniquePoints(int points) {
        this.technique = clamp(this.technique + points);
    }

    public void addPrecisionPoints(int points) {
        this.precision = clamp(this.precision + points);
    }

    /**
     * Restricts a value to the range.
     *
     * @param value the value to restrict
     * @return the value, or the nearest limit if it was out of range
     */

    private int clamp(int value) {
        return Math.max(MIN_METER, Math.min(MAX_METER, value));
    }
}
