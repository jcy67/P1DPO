package Business.Entities;

public class Delegation {
    private String code;
    private String name;
    private int goldMedals;
    private int silverMedals;
    private int bronzeMedals;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getGoldMedals() {
        return goldMedals;
    }

    public int getSilverMedals() {
        return silverMedals;
    }

    public int getBronzeMedals() {
        return bronzeMedals;
    }

    public void setGoldMedals(int goldMedals) {
        this.goldMedals = goldMedals;
    }

    public void setSilverMedals(int silverMedals) {
        this.silverMedals = silverMedals;
    }

    public void setBronzeMedals(int bronzeMedals) {
        this.bronzeMedals = bronzeMedals;
    }

    // Método toString para imprimir fácilmente
    @Override
    public String toString() {
        return name;
    }

    public void resetMedals() {
        setGoldMedals(0);
        setSilverMedals(0);
        setBronzeMedals(0);
    }
}
