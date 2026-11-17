package Persistence;

import Business.Entities.Competition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

    public class CompetitionDAO {
        private static final String COMPETITIONS_PATH = "data/competitions.json";
        private final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

        private final Gson gson;

        // serializeNulls() incluye en el JSON los atributos cuyo valor es null, setDateFormat para que siga el format indicado y PrettyPrinting para que el JSON no se vea en una sola linea
        public CompetitionDAO() {this.gson = new GsonBuilder().setDateFormat(DATETIME_FORMAT).serializeNulls().setPrettyPrinting().create();}

        public ArrayList<Competition> readCompetitions() throws IOException{

            ArrayList<Competition> competitions = new ArrayList<>();

            // Si el archivo no existe, devuelve la lista vacía pero no crea el JSON
            if (Files.notExists(Paths.get(COMPETITIONS_PATH))) {
                return competitions;
            }

            String jsonContent = Files.readString(Paths.get(COMPETITIONS_PATH));
            Competition[] parsedItems = gson.fromJson(jsonContent, Competition[].class);

            // Si Gson devuelve null, lanza una excepcion
            if (parsedItems == null){
                throw new IOException();
            }

            competitions.addAll(Arrays.asList(parsedItems));

            return competitions;
        }

        // Lee el csv
        public ArrayList<String> readCSV(String filename) throws IOException{
            return new ArrayList<>(Files.readAllLines(Paths.get("data",filename)));
        }

        // Guardar en el json o crearlo
        public void saveCompetitions(ArrayList<Competition> competitions)throws IOException{
            Files.writeString(Path.of(COMPETITIONS_PATH), gson.toJson(competitions));
        }
    }
