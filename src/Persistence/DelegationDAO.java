package Persistence;

import Business.Entities.Delegation;
import com.google.gson.Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Manages the persistence of delegations in a JSON file.
 * Provides methods to read and save delegation data.
 */
public class DelegationDAO {

    private static final String DELEGATIONS_PATH = "data/delegations.json";
    private final Gson gson;


    /**
     * Creates a DelegationDAO and initializes the Gson object.
     */
    public DelegationDAO() {this.gson = new GsonBuilder().setPrettyPrinting().create();}


    /**
     * Reads all delegations from the JSON file.
     *
     * @return an ArrayList containing the delegations
     * @throws IOException if the file cannot be read or contains no valid list
     */
    public ArrayList<Delegation> readDelegations() throws IOException{
        ArrayList<Delegation> delegations = new ArrayList<>();


        String jsonContent = Files.readString(Paths.get(DELEGATIONS_PATH));
        Delegation[] parsedItems = gson.fromJson(jsonContent, Delegation[].class);

        // If Gson returns null, throws an exception
        if (parsedItems == null){
            throw new IOException();
        }

        delegations.addAll(Arrays.asList(parsedItems));

        return delegations;
    }


    /**
     * Saves the given delegations to the JSON file.
     *
     * @param delegations the list of delegations to save
     * @throws IOException if an error occurs while writing the file
     */
    public void saveDelegations(ArrayList<Delegation> delegations)
            throws IOException {

        Files.writeString(Path.of(DELEGATIONS_PATH), gson.toJson(delegations));
    }
}
