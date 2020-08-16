import java.util.ArrayList;
import java.util.Random;
/**
 * The City class represents a simulated city affected by a specific disease.
 * @author Admin
 */
class City {
    private String name;
    private double diseaseRate;

    private ArrayList<Ward> wards = new ArrayList<Ward>();

    /**
     * Constructor for class City.
     * @param _name			a String containing a brief description of the City, used
     * 						exclusively in City.toString()
     * @param _diseaseRate	the disease's basic rate of infection after making contact
     * 						with an affected surface once			
     */
    public City(String _name, double _diseaseRate){ 
        this.name = _name;
        this.diseaseRate = _diseaseRate;
    }

    /**
     * Steps forward in the simulation by iterating all constituent wards.
     * @param periods	the number of periods the simulation will run for in total
     */
    public void iterate(int periods){
        Random rand = new Random();
        for (Ward currentWard : this.wards) {
            currentWard.iterate(periods);
            // If a random percentage is greater than the percentage of infected voters in this ward,
            if (currentWard.getInfectedPopulation() > rand.nextDouble() * currentWard.getTotalPopulation()) {
                // then try to infect a random subdivision in another ward
                this.getSpecificWard(rand.nextInt(this.wards.size() - 1) + 1).getRandomSubdivision().toggleStation(true);
            }
        }
    }

    /**
     * Gets Ward with the specified number. Returns null if no included Wards have
     * the specified number.
     * @param number
     * @return the Ward in this.wards with the specified number
     */
    public Ward getSpecificWard(int number) {
        for (Ward currentWard : this.wards)
            if (currentWard.getWardNumber() == number)
                return currentWard;
        return this.wards.get(0);
    }

    /**
     * Gets the total voting population of the city.
     * @return an int containing the total number of voters
     */
    public int getTotalPopulation() {
        int total = 0;
        for (Ward currentWard : this.wards)
            total += currentWard.getTotalPopulation();
        return total;
    }

    /**
     * Gets the total infected voting population of the city.
     * @return an int containing the total number of infected voters
     */
    public int getInfectedPopulation() {
        int total = 0;
        for (Ward currentWard : this.wards)
            total += currentWard.getInfectedPopulation();
        return total;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String message = "\nCity: " + name  + "\nWards Infected: ";
        
        String infectedMessage = "";
        for (Ward currentWard : this.wards)
        	
        	// If a Ward is infected, add its number to the output string
            if (currentWard.isInfected())
                infectedMessage += "" + currentWard.getWardNumber() + ", ";
        
        if (infectedMessage.length() == 0)
            infectedMessage = "None  ";
        
        // Remove last two characters from output string
        message += infectedMessage.substring(0, infectedMessage.length() - 2);

        message += "\nTotal Population in City: " + this.getTotalPopulation();
        message += "\nTotal infected Population in City: " + this.getInfectedPopulation(); 
        return message; 
    }

    /**
     * Adds a Ward with the specified number.
     * @param number the desired number of the new Ward
     */
    public void addWard(int number) {
        this.wards.add(new Ward(number, this.diseaseRate));
    }

    /**
     * Gets the name of the City.
     * @return a String containing a brief description of the scenario
     */
    public String getName() {
        return this.name;
    }
}