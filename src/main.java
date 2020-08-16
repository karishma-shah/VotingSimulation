import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        int simulationLength = 600; // number of minutes in ten-hour polling period

        ArrayList<City> noVotingMachineCities = new ArrayList<City>();
        ArrayList<City> someVotingMachineCities = new ArrayList<City>();
        ArrayList<City> allVotingMachineCities = new ArrayList<City>();

        noVotingMachineCities.add(new City("Staphyloccus aureus, none, L", 0.67));
        noVotingMachineCities.add(new City("Staphyloccus aureus, none, H", 0.67));
        noVotingMachineCities.add(new City("Staphyloccus epidermidis, none, L", 0.33));
        noVotingMachineCities.add(new City("Staphyloccus epidermidis, none, H", 0.33));
        noVotingMachineCities.add(new City("Enterobacter cloacae, none, L", 0.25));
        noVotingMachineCities.add(new City("Enterobacter cloacae, none, H", 0.25));

        someVotingMachineCities.add(new City("Staphyloccus aureus, some, L", 0.67));
        someVotingMachineCities.add(new City("Staphyloccus aureus, some, H", 0.67));
        someVotingMachineCities.add(new City("Staphyloccus epidermidis, some, L", 0.33));
        someVotingMachineCities.add(new City("Staphyloccus epidermidis, some, H", 0.33));
        someVotingMachineCities.add(new City("Enterobacter cloacae, some, L", 0.25));
        someVotingMachineCities.add(new City("Enterobacter cloacae, some, H", 0.25));

        allVotingMachineCities.add(new City("Staphyloccus aureus, all, L", 0.67));
        allVotingMachineCities.add(new City("Staphyloccus aureus, all, H", 0.67));
        allVotingMachineCities.add(new City("Staphyloccus epidermidis, all, L", 0.33));
        allVotingMachineCities.add(new City("Staphyloccus epidermidis, all, H", 0.33));
        allVotingMachineCities.add(new City("Enterobacter cloacae, all, L", 0.25));
        allVotingMachineCities.add(new City("Enterobacter cloacae, all, H", 0.25));

        for (City city : noVotingMachineCities) {
            try (Scanner populationData = new Scanner(new File("sample.txt"))) {
            
                int wardNumber = 0, subdivisionNumber = 0, population = 0;
                int previousWard = 0;

                int vents = (city.getName().charAt(city.getName().length() - 1) == 'L') ? 2 : 10;
                
                while (populationData.hasNext()) {
                    wardNumber = populationData.nextInt();
                    subdivisionNumber = populationData.nextInt();
                    population = populationData.nextInt();
                    
                    // round to nearest simulationLength so that the voting population can be evenly divided
                    population += (simulationLength - population) % simulationLength;
    
                    if (wardNumber != previousWard)
                        city.addWard(wardNumber);
                    else
                        previousWard = wardNumber;

                    city.getSpecificWard(wardNumber).addSubdivision(subdivisionNumber, population, 1, false, 25, vents);
                }

                city.getSpecificWard(13).getSpecificSubdivision(63).toggleStation(true);
    
                for (int minutesElapsed = 0; minutesElapsed <= simulationLength; minutesElapsed ++) {
                    city.iterate(simulationLength);
                }
                System.out.println(city);
            }
            catch (IOException | InputMismatchException e) {
                System.out.println("scanning machine broke");
            }
        }

        for (City city : someVotingMachineCities) {
            try (Scanner populationData = new Scanner(new File("sample.txt"))) {
            
                int wardNumber = 0, subdivisionNumber = 0, population = 0;
                int previousWard = 0;

                int vents = (city.getName().charAt(city.getName().length() - 1) == 'L') ? 2 : 10;
                
                while (populationData.hasNext()) {
                    wardNumber = populationData.nextInt();
                    subdivisionNumber = populationData.nextInt();
                    population = populationData.nextInt();
                    
                    // round to nearest simulationLength so that the voting population can be evenly divided
                    population += (simulationLength - population) % simulationLength;
    
                    if (wardNumber != previousWard)
                        city.addWard(wardNumber);
                    else
                        previousWard = wardNumber;

                    if (population > 1000) {
                        city.getSpecificWard(wardNumber).addSubdivision(subdivisionNumber, population, 5, false, 25, vents);
                    }
                    city.getSpecificWard(wardNumber).addSubdivision(subdivisionNumber, population, 1, false, 25, vents);
                }
    
                city.getSpecificWard(13).getSpecificSubdivision(63).toggleStation(true);

                for (int minutesElapsed = 0; minutesElapsed <= simulationLength; minutesElapsed ++) {
                    city.iterate(simulationLength);
                }
                System.out.println(city);
            }
            catch (IOException | InputMismatchException e) {
                System.out.println("scanning machine broke");
            }
        }

        for (City city : allVotingMachineCities) {
            try (Scanner populationData = new Scanner(new File("sample.txt"))) {
            
                int wardNumber = 0, subdivisionNumber = 0, population = 0;
                int previousWard = 0;

                int vents = (city.getName().charAt(city.getName().length() - 1) == 'L') ? 2 : 10;
                
                while (populationData.hasNext()) {
                    wardNumber = populationData.nextInt();
                    subdivisionNumber = populationData.nextInt();
                    population = populationData.nextInt();
                    
                    // round to nearest simulationLength so that the voting population can be evenly divided
                    population += (simulationLength - population) % simulationLength;
    
                    if (wardNumber != previousWard)
                        city.addWard(wardNumber);
                    else
                        previousWard = wardNumber;

                    city.getSpecificWard(wardNumber).addSubdivision(subdivisionNumber, population, 5, false, 25, vents);
                }

                city.getSpecificWard(13).getSpecificSubdivision(63).toggleStation(true);

                for (int minutesElapsed = 0; minutesElapsed <= simulationLength; minutesElapsed ++) {
                    city.iterate(simulationLength);
                }
                System.out.println(city);
            }
            catch (IOException | InputMismatchException e) {
                System.out.println("scanning machine broke");
            }
        }
    }
}