package Business.Entities;

public class Result {

    private String gold;
    private String silver;
    private String bronze;

    public Result(String gold, String silver, String bronze) {
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }

    public String getGold() {
        return gold;
    }

    public String getSilver() {
        return silver;
    }

    public String getBronze() {
        return bronze;
    }
}
