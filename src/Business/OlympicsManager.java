package Business;

import Business.Entities.Athlete;
import Business.Entities.Competition;
import Business.Entities.Delegation;
import Persistence.AthleteDAO;
import Persistence.CompetitionDAO;
import Persistence.DelegationDAO;

import java.util.ArrayList;

public class OlympicsManager {


    private final DelegationDAO delegationDAO;
    private final AthleteDAO athleteDAO;
    private final CompetitionDAO competitionDAO;

    public OlympicsManager(DelegationDAO d, AthleteDAO a, CompetitionDAO c) {
        this.delegationDAO = d;
        this.athleteDAO = a;
        this.competitionDAO = c;
    }

    public ArrayList<Delegation> getDelegationDAO() {
        return delegationDAO.readDelegations();
    }

    public ArrayList<Athlete> getAthleteDAO() {
        return athleteDAO.readAthletes();
    }

    public ArrayList<Competition> getCompetitionDAO() {
        return competitionDAO.readCompetitions();
    }

}
