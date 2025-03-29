import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ZooManager {

    // List to store all animals
    private static List<Animals.Animal> animals = new ArrayList<>();

    // Map to store animal names by species
    private static Map<String, List<String>> animalNames = new HashMap<>();

    public static void main(String[] args) {
        try {
            // Load animal names from the file
            loadAnimalNames("animalNames.txt");

            // Load animals from arrivingAnimals.txt and process the data
            loadArrivingAnimals("arrivingAnimals.txt");

            // Generate and save the zoo population report
            generateReport("zooPopulation.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load animal names from the animalNames.txt file
    public static void loadAnimalNames(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        String currentSpecies = "";
        List<String> namesList = null;

        for (String line : lines) {
            line = line.trim();
            if (line.endsWith("Names:")) {
                currentSpecies = line.replace(" Names:", "").trim();
                namesList = new ArrayList<>();
                animalNames.put(currentSpecies.toLowerCase(), namesList);
            } else if (!line.isEmpty()) {
                String[] names = line.split(", ");
                Collections.addAll(namesList, names);
            }
        }
    }

    // Method to load the arriving animals from the arrivingAnimals.txt file
    public static void loadArrivingAnimals(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines) {
            String[] parts = line.split(", ");
            int age = Integer.parseInt(parts[0].split(" ")[0]);
            String sex = parts[1].split(" ")[0];
            String species = parts[2].split(" ")[1];
            String season = parts[3].split(" ")[3];
            String color = parts[4].split(" ")[0];
            double weight = Double.parseDouble(parts[5].split(" ")[0]);
            String habitat = parts[6].split(" ")[2];

            // Create animal and assign it a name and unique ID
            Animals.Animal animal = new Animal(species, sex, age, color, weight, habitat, season);
            List<String> speciesNames = animalNames.get(species.toLowerCase());
            if (speciesNames != null && !speciesNames.isEmpty()) {
                animal.setName(speciesNames.get(animals.size() % speciesNames.size()));
            }

            animals.add(animal);
        }
    }

    // Method to generate the final report in the zooPopulation.txt file
    public static void generateReport(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        // Group animals by habitat
        Map<String, List<Animal>> habitats = new HashMap<>();
        for (Animal animal : animals) {
            habitats.computeIfAbsent(animal.getHabitat(), k -> new ArrayList<>()).add(animal);
        }

        // Write the grouped animals into the report
        for (String habitat : habitats.keySet()) {
            writer.write(habitat + ":\n");
            for (Animal animal : habitats.get(habitat)) {
                writer.write(animal.getAnimalDetails() + "\n");
            }
            writer.write("\n");
        }
        writer.close();
    }
}
