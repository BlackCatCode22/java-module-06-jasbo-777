public class Animals {
    import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

    public class Animal {

        // Static counters for generating unique IDs
        private static int numOfHyenas = 0;
        private static int numOfLions = 0;
        private static int numOfTigers = 0;
        private static int numOfBears = 0;

        private String name;
        private String species;
        private String sex;
        private int age;
        private String color;
        private double weight;
        private String habitat;
        private LocalDate birthDate;
        private String uniqueID;

        // Constructor for the Animal class
        public Animal(String species, String sex, int age, String color, double weight, String habitat, String season) {
            this.species = species;
            this.sex = sex;
            this.age = age;
            this.color = color;
            this.weight = weight;
            this.habitat = habitat;
            this.birthDate = genBirthDay(age, season);
            this.uniqueID = genUniqueID(species);
        }

        // Method to generate an animal's birth date
        private LocalDate genBirthDay(int age, String season) {
            LocalDate currentDate = LocalDate.now();
            LocalDate birthDate = currentDate.minusYears(age);

            if ("spring".equalsIgnoreCase(season)) {
                birthDate = birthDate.withMonth(3).withDayOfMonth(21); // Start of Spring
            } else if ("summer".equalsIgnoreCase(season)) {
                birthDate = birthDate.withMonth(6).withDayOfMonth(21); // Start of Summer
            } else if ("fall".equalsIgnoreCase(season)) {
                birthDate = birthDate.withMonth(9).withDayOfMonth(23); // Start of Fall
            } else if ("winter".equalsIgnoreCase(season)) {
                birthDate = birthDate.withMonth(12).withDayOfMonth(21); // Start of Winter
            } else {
                birthDate = birthDate.withMonth(1).withDayOfMonth(1); // Default to start of the year
            }
            return birthDate;
        }

        // Method to generate unique IDs for each animal
        private String genUniqueID(String species) {
            String idPrefix = species.substring(0, 2).toUpperCase(); // First 2 letters of species
            if ("hyena".equalsIgnoreCase(species)) {
                numOfHyenas++;
                return idPrefix + String.format("%02d", numOfHyenas);
            } else if ("lion".equalsIgnoreCase(species)) {
                numOfLions++;
                return idPrefix + String.format("%02d", numOfLions);
            } else if ("tiger".equalsIgnoreCase(species)) {
                numOfTigers++;
                return idPrefix + String.format("%02d", numOfTigers);
            } else if ("bear".equalsIgnoreCase(species)) {
                numOfBears++;
                return idPrefix + String.format("%02d", numOfBears);
            }
            return idPrefix + "01"; // Default if unknown species
        }

        // Getter and Setter methods
        public String getAnimalDetails() {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            return String.format("%s; %s; birth date: %s; %s color; %s; %.2f pounds; from %s; arrived %s",
                    uniqueID, name, birthDate.format(formatter), color, sex, weight, habitat, LocalDate.now().format(formatter));
        }

        public String getHabitat() {
            return habitat;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

