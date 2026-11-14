package Business;

import Persistence.AthleteDAO;
import Persistence.CompetitionDAO;
import Persistence.DelegationDAO;

public class OlympicsManager {


    private final DelegationDAO delegationDAO;
    private final AthleteDAO athleteDAO;
    private final CompetitionDAO competitionDAO;

    public OlympicsManager(DelegationDAO de, AthleteDAO at, CompetitionDAO co) {
        this.delegationDAO = de;
        this.athleteDAO = at;
        this.competitionDAO = co;
    }

}
