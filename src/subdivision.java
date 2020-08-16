import java.util.ArrayList;
import java.util.Random;

/**
 * The Subdivision class represents a polling subdivision and its polling station in a Ward object.
 * @author Admin
 */
class Subdivision {
    private int subdivisionNumber;

    private int totalPopulation;
    private int infectedPopulation;
    private int finishedPopulation;
    private boolean stationInfected;
    private double rateOfInfection;
    private double baseRateInfection; 

    private int numberOfTouches;
    private int temperature;
    private int numberOfVents;

    /**
     * Constructor for class Subdivision.
     * @param _subdivisionNumber	the desired number of the new Subdivision
     * @param _totalPopulation		the voting population of the new Subdivision
     * @param _baseRateInfection	the disease's basic rate of infection after making contact
     * 								with an affected surface once 
     * @param _numberOfTouches		the number of times a voter makes contact with a voting device
     * @param _stationInfected		whether or not the Subdivision's polling station is infected
     * @param _temperature			the temperature in the polling station in degrees Celsius
     * @param _numberOfVents		the number of opened windows, vents, or other airways in the polling station
     */
    public Subdivision (int _subdivisionNumber,
                        int _totalPopulation,
                        double _baseRateInfection,
                        int _numberOfTouches,
                        boolean _stationInfected,
                        int _temperature,
                        int _numberOfVents) {
        
        this.subdivisionNumber = _subdivisionNumber;
        this.totalPopulation = _totalPopulation; 
        this.baseRateInfection = _baseRateInfection;  
        this.infectedPopulation = 0;
        this.finishedPopulation = 0;
        this.stationInfected = _stationInfected; 
        this.numberOfTouches = _numberOfTouches;
        this.temperature = _temperature;
        this.numberOfVents = _numberOfVents;

        this.calculateRate();
    }

    /**
     * Sets the state of infection of the subdivision's polling station.
     */
    public void toggleStation(boolean newState){
        this.stationInfected = newState;
    }

    /**
     * Recalculates the practical rate of infection and stores it in memory.
     * The practical rate of infection is calculated based on the basic rate of infection
     * and is slightly different between each polling station due to environmental factors.
     */
    public void calculateRate(){
        this.rateOfInfection = this.baseRateInfection;
        this.rateOfInfection /= this.numberOfTouches * 0.001;
        this.rateOfInfection *= this.numberOfVents * 0.005;
        if (this.temperature > 35){
            this.rateOfInfection += 0.0002;
        }
    }

    /**
     * Gets the practical rate of infection.
     * @return the practical rate of infection as calculated in Subdivision.calculateRate()
     */
    public double getCalculatedRate(){
        return this.rateOfInfection;
    }

    /**
     * Gets the total voting population of the subdivision.
     * @return an int containing the total number of voters
     */
    public int getTotalPopulation() {
        return this.totalPopulation;
    }

    /**
     * Gets the total infected voting population of the city.
     * @return the total number of infected voters
     */
    public int getInfectedPopulation() {
        return this.infectedPopulation;
    }

    /**
     * Determines whether the subdivision is infected.
     * @return <code>true</code> if the subdivision is infected;
     * 		   <code>false</code> otherwise.
     */
    public boolean isInfected() {
        return this.stationInfected;
    }

    /**
     * Gets the number of the subdivision.
     * @return the number of the subdivision
     */
    public int getSubdivisionNumber() {
        return this.subdivisionNumber;
    }

    /**
     * Steps forward in the simulation. The number of people that will vote in each step
     * is determined by the total number of periods over the entire simulation. It is assumed
     * that an equal number of people will vote between each step.
     * 
     * A percentage of the number of voters passing through the station in one period will be
     * infected if Subdivision.isInfected() is true.
     * @param periods	the number of periods the simulation will run for in total
     */
    public void iterate(int periods) {
        int numberOfVoters = this.totalPopulation / periods;

        this.finishedPopulation += numberOfVoters;
        
        this.infectedPopulation += this.isInfected() ?
                                   (int)(numberOfVoters * this.rateOfInfection) :
                                   0;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){ 
        return "Total Number of Voters: " + this.totalPopulation + "\nTotal Number of Voters Infected in Subdivision:" + this.infectedPopulation + "Rate of Infection" + this.rateOfInfection; 
    }
}