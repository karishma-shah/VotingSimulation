import java.util.ArrayList;
import java.util.Random;
/**
 * The Ward class represents an electoral ward in a City object.
 * @author Admin
 */
class Ward {
    private int wardNumber;
    private double diseaseRate;

    private ArrayList<Subdivision> subdivisions = new ArrayList<Subdivision>();

    /**
     * Constructor for class Ward.
     * @param _wardNumber	the desired number of the new Ward
     * @param _diseaseRate	the disease's basic rate of infection after making contact
     * 						with an affected surface once
     */
    public Ward(int _wardNumber, double _diseaseRate){
        this.wardNumber = _wardNumber;
        this.diseaseRate = _diseaseRate;
    }

    /**
     * Steps forward in the simulation by iterating all constituent wards.
     * @param periods	the number of periods the simulation will run for in total
     */
    public void iterate(int periods){
        Random rand = new Random();
        for (Subdivision currentSubdivision : this.subdivisions) {
            currentSubdivision.iterate(periods);
            // If a random percentage is greater than the percentage of infected voters in this subdivision,
            if (currentSubdivision.getInfectedPopulation() > rand.nextDouble() * currentSubdivision.getTotalPopulation()) {
                // then try to infect another subdivision in the same ward
                this.getRandomSubdivision().toggleStation(true);
            }
        }
    }

    /**
     * Gets the total voting population of the ward.
     * @return an int containing the total number of voters
     */
    public int getTotalPopulation() {
        int total = 0;
        for (Subdivision currentSubdivision : this.subdivisions)
            total += currentSubdivision.getTotalPopulation();
        return total;
    }

    /**
     * Gets the total infected voting population of the city.
     * @return the total number of infected voters
     */
    public int getInfectedPopulation() {
        int total = 0;
        for (Subdivision currentSubdivision : this.subdivisions)
            total += currentSubdivision.getInfectedPopulation();
        return total;
    }

    /**
     * Determines whether any of the constituent subdivisions are infected.
     * @return <code>true</code> if the ward is infected;
     * 		   <code>false</code> otherwise.
     */
    public boolean isInfected() {
        for (Subdivision currentSubdivision : this.subdivisions)
        	if (currentSubdivision.isInfected())
        		return true;
        return false;
    }

    /**
     * Gets the number of the ward.
     * @return the number of the ward
     */
    public int getWardNumber() {
        return this.wardNumber;
    }

    /**
     * Adds a Subdivision with the specified conditions.
     * @param number		the desired number of the new Subdivision
     * @param population	the voting population of the new Subdivision
     * @param touches		the number of times a voter makes contact with a voting device
     * @param infected		whether or not the Subdivision's polling station is infected
     * @param temperature	the temperature in the polling station in degrees Celsius
     * @param vents			the number of opened windows, vents, or other airways in the polling station
     */
    public void addSubdivision(int number, int population, int touches, boolean infected, int temperature, int vents) {
        this.subdivisions.add(new Subdivision(number, population, this.diseaseRate, 1, false, 1, 1));
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
        return "\nTotal Subdivision Population " + this.getTotalPopulation() + "\nTotal Infected Population: " + this.getInfectedPopulation() + "\n ";  
    }

    /**
     * Gets Subdivision with the specified number. Returns null if no included Subdivisions have
     * the specified number.
     * @param number
     * @return the Subdivision in this.subdivisions with the specified number
     */
    public Subdivision getSpecificSubdivision(int number) {
        for (Subdivision currentSubdivision : this.subdivisions)
            if (currentSubdivision.getSubdivisionNumber() == number)
                return currentSubdivision;
        return this.subdivisions.get(0);
    }

    /**
     * Gets random included Subdivision.
     * @return a randomly selected Subdivision from the ArrayList of Subdivisions
     */
    public Subdivision getRandomSubdivision() {
        Random rand = new Random();
        return this.getSpecificSubdivision(rand.nextInt(this.subdivisions.size() - 1) + 1);
    }
}