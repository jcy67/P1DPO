package Business.Entities;

import java.util.ArrayList;
import java.util.Date;

public class Competition {

    private String id;
    private COMPETITION_FORMAT format;
    private String name;
    private Date dateTime;
    private Weight weights;
    private ArrayList<String> athleteIds;
    private Result results;

    public String getId() {
        return id;
    }

    public COMPETITION_FORMAT getFormat() {
        return format;
    }

    public String getName() {
        return name;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public Weight getWeights() {
        return weights;
    }

    public double getSpeed() {
        return weights.getSpeed();
    }

    public double getEndurance() {
        return weights.getEndurance();
    }

    public double getStrength() {
        return weights.getStrength();
    }

    public double getTechnique() {
        return weights.getTechnique();
    }

    public double getPrecision() {
        return weights.getPrecision();
    }

    public ArrayList<String> getAthleteIds() {
        return athleteIds;
    }

    public Result getResults() {
        return results;
    }

    public String getGold() {
        return results.getGold();
    }

    public String getSilver() {
        return results.getSilver();
    }

    public String getBronze() {
        return results.getBronze();
    }
}
