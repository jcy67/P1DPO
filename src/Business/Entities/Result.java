package Business.Entities;

/**
 * Represents the results of an Olympic competition.
 * Stores the identifiers of the gold, silver and bronze medal winners.
 */
public class Result {

    private String gold;
    private String silver;
    private String bronze;

    /**
     * Creates a result with the given medal winners.
     *
     * @param gold the identifier of the gold medal winner
     * @param silver the identifier of the silver medal winner
     * @param bronze the identifier of the bronze medal winner
     */
    public Result(String gold, String silver, String bronze) {
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }

    /**
     * Returns the identifier of the gold medal winner.
     *
     * @return the gold medal winner's identifier
     */
    public String getGold() {
        return gold;
    }

    /**
     * Returns the identifier of the silver medal winner.
     *
     * @return the silver medal winner's identifier
     */
    public String getSilver() {
        return silver;
    }

    /**
     * Returns the identifier of the bronze medal winner.
     *
     * @return the bronze medal winner's identifier
     */
    public String getBronze() {
        return bronze;
    }
}