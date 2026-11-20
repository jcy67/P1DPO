package Persistence;

import Business.Entities.Delegation;
import com.google.gson.Gson;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;


public class DelegationDAO {
    private static final String DELEGATIONS_PATH = "data/delegations.json";

    private final Gson gson;
    public DelegationDAO() {this.gson = new Gson();}

    public ArrayList<Delegation> readDelegations() throws IOException{
        ArrayList<Delegation> delegations = new ArrayList<>();


        String jsonContent = Files.readString(Paths.get(DELEGATIONS_PATH));
        Delegation[] parsedItems = gson.fromJson(jsonContent, Delegation[].class);

        // Si Gson devuelve null, lanza una excepcion
        if (parsedItems == null){
            throw new IOException();
        }

        delegations.addAll(Arrays.asList(parsedItems));

        return delegations;
    }

    public void saveDelegations(ArrayList<Delegation> delegations)
            throws IOException {

        Files.writeString(Path.of(DELEGATIONS_PATH), new Gson().toJson(delegations));
    }
}
