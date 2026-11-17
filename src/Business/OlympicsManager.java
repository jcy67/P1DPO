package Business;

import Business.Entities.Athlete;
import Business.Entities.Competition;
import Business.Entities.Delegation;
import Persistence.AthleteDAO;
import Persistence.CompetitionDAO;
import Persistence.DelegationDAO;

import java.util.ArrayList;
import java.util.Comparator;

public class OlympicsManager {


    private final DelegationDAO delegationDAO;
    private final AthleteDAO athleteDAO;
    private final CompetitionDAO competitionDAO;

    public boolean verifyDelegations() {
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();
        return delegations != null;
    }

    public OlympicsManager(DelegationDAO d, AthleteDAO a, CompetitionDAO c) {
        this.delegationDAO = d;
        this.athleteDAO = a;
        this.competitionDAO = c;
    }

    public ArrayList<Delegation> sortDelegationsByName() {
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();

        delegations.sort(Comparator.comparing(Delegation::getName));

        return delegations;
    }

    public ArrayList<Delegation> sortDelegationsByMedals() {
        ArrayList<Delegation> delegations = delegationDAO.readDelegations();

        delegations.sort(Comparator.comparing(Delegation::getGoldMedals).reversed().thenComparing(Comparator.comparing(Delegation::getSilverMedals).reversed()).thenComparing(Comparator.comparing(Delegation::getBronzeMedals).reversed()).thenComparing(Delegation::getName));

        return delegations;
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
