package Business.Entities;

/**
 * Represents a delegation participating in the Olympic Games.
 * Stores its code, name and number of medals won.
 */
public class Delegation {

    private String code;
    private String name;
    private int goldMedals;
    private int silverMedals;
    private int bronzeMedals;

    /**
     * Returns the delegation code.
     *
     * @return the delegation code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the delegation name.
     *
     * @return the delegation name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of gold medals won.
     *
     * @return the number of gold medals
     */
    public int getGoldMedals() {
        return goldMedals;
    }

    /**
     * Returns the number of silver medals won.
     *
     * @return the number of silver medals
     */
    public int getSilverMedals() {
        return silverMedals;
    }

    /**
     * Returns the number of bronze medals won.
     *
     * @return the number of bronze medals
     */
    public int getBronzeMedals() {
        return bronzeMedals;
    }

    /**
     * Sets the number of gold medals.
     *
     * @param goldMedals the number of gold medals to set
     */
    public void setGoldMedals(int goldMedals) {
        this.goldMedals = goldMedals;
    }

    /**
     * Sets the number of silver medals.
     *
     * @param silverMedals the number of silver medals to set
     */
    public void setSilverMedals(int silverMedals) {
        this.silverMedals = silverMedals;
    }

    /**
     * Sets the number of bronze medals.
     *
     * @param bronzeMedals the number of bronze medals to set
     */
    public void setBronzeMedals(int bronzeMedals) {
        this.bronzeMedals = bronzeMedals;
    }


    /**
     * Resets all medal counts to zero.
     */
    public void resetMedals() {
        setGoldMedals(0);
        setSilverMedals(0);
        setBronzeMedals(0);
    }
}