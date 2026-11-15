package Persistence;

import Business.Entities.Competition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

    public class CompetitionDAO {
        private static final String COMPETITIONS_PATH = "data/competitions.json";
        private final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

        private final Gson gson;
        public CompetitionDAO() {this.gson = new GsonBuilder().setDateFormat(DATETIME_FORMAT).create();}

        public ArrayList<Competition> readCompetitions() {
            ArrayList<Competition> competitions = new ArrayList<>();
            try {
                String jsonContent = Files.readString(Paths.get(COMPETITIONS_PATH));
                Competition[] parsedItems = gson.fromJson(jsonContent, Competition[].class);
                competitions.addAll(Arrays.asList(parsedItems));
            } catch (IOException e) {
                return null;
            }
            return competitions;
        }
    }
