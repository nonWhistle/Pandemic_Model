/**
 * Question 3a
 *
 * The class SIRModel models how an infection spreads amongst 150 people over 60 days with
 * a total of 4 people already infected. The class can accommodate for different transmission
 * and recovery rates of different types of models, a graph can be generated to see the results.
 *
 * @author Thomas Milton
 * @version 29/12/2021
 */
public class SIRModel
{

    // Question 3b.

    // The number of people for the model.
    private static final int POPULATION = 150;
    // The number of initial infections.
    private static final int INITIAL_INFECTIONS = 4;
    // The number of days the model is based over.
    private static final int DAYS = 60;
    // Stores the rate of transmission value.
    private final double TRANS_RATE;
    // Stores the rate of recovery value.
    private final double REC_RATE;
    // Stores the daily number of susceptible people.
    private final double[] susceptible;
    // Stores the daily number of infected people.
    private final double[] infected;
    // Stores the daily number of recovered people.
    private final double[] recovered;


    /**
     * Question 3c
     *
     * Creates a new SIRModel with 150 people over 60 days and initialises the transmission
     * and recovery rate to the users specified values.
     *
     * @param transRate transRate which specifies the rate of transmission.
     * @param recRate recRate which specifies the rate of recovery once infected.
     */
    public SIRModel(double transRate, double recRate)
    {
        TRANS_RATE = transRate;
        REC_RATE = recRate;
        susceptible = new double[DAYS];
        infected = new double[DAYS];
        recovered = new double[DAYS];
        infected[0] = INITIAL_INFECTIONS;
        susceptible[0] = (POPULATION - INITIAL_INFECTIONS);

        createData();
        printGraph();
    }



    /**
     * Question 3d
     *
     * Returns a value for the number of infected people based on the values in the parameters and
     * the density-dependent model of infection.
     *
     * @param currentInfected currentInfected which is the number of people currently infected.
     * @param currentSusceptible currentSusceptible which is the number of people that are neither infected nor recovered.
     * @return aDouble returns the amount of people who are newly infected.
     */
    public double newlyInfected(double currentInfected, double currentSusceptible)
    {
        return (TRANS_RATE * currentInfected * currentSusceptible) / POPULATION;
    }


    /**
     * Question 3e
     *
     * Returns a value for the number of recovered people based on the value in the parameters.
     *
     * @param currentInfected currentInfected which is the number of people currently infected.
     * @return aDouble newlyRecovered the number of people that are newly recovered.
     */
    public double newlyRecovered(double currentInfected)
    {
        return REC_RATE * currentInfected;
    }


    /**
     * Question 3f
     *
     * Returns a value for the current amount of people infected based on the values in the
     * parameters.
     *
     * @param currentInfected currentInfected which is the number of people currently infected.
     * @param currentSusceptible currentSusceptible which is the number of people that are neither infected nor recovered.
     * @return aDouble changeInInfected which is the new number of infected people.
     */
    public double changeInInfected(double currentInfected, double currentSusceptible)
    {
        return newlyInfected(currentInfected, currentSusceptible) - newlyRecovered(currentInfected);
    }


    /**
     * Question 3g
     *
     * Populates the index given by the parameter value of the infected, recovered and
     * susceptible arrays. The index values are calculated from the field values.
     *
     * @param prevDay the previous day, 0 being the start of the test and 59 being the last.
     */
    public void nextDay(int prevDay)
    {
        int today = prevDay + 1;

        infected[today] = infected[prevDay] + changeInInfected(infected[prevDay], susceptible[prevDay]);

        recovered[today] = recovered[prevDay] + newlyRecovered(infected[prevDay]);

        susceptible[today] = POPULATION - (infected[today] + recovered[today]);
    }


    /**
     * Question 3h
     *
     * Continuously calls the nextDay method until all the arrays are filled with values.
     */
    public void createData()
    {
        boolean data = true;
        int today = 1;

        while(data){
            nextDay(today -1);
            today++;

            if(today == DAYS){
                data = false;
            }
        }
    }


    /**
     * Question 3i
     *
     * Generates a bar graph in the BlueJ terminal window based on the results of the SIR model.
     */
    public void printGraph()
    {
        System.out.println("Graph of the number of infected people on each day with");
        System.out.println("Transmission rate "+TRANS_RATE);
        System.out.println("Recovery rate "+REC_RATE);
        System.out.println("Starting S "+(susceptible[0])+
                " I "+infected[0]+" R "+(int) Math.round(recovered[0]));
        System.out.println();

        for(int i = 0; i < infected.length; i++){
            System.out.print(i+" ");
            int numInfected = (int) Math.round(infected[i]);
            for(int j = 0; j < numInfected; j++){
                System.out.print("*");
            }
            System.out.println(" ("+numInfected+")");
        }

        System.out.println();
        System.out.println("Ending S "+(int) Math.round(susceptible[59])+
                " I "+(int) Math.round(infected[59])+
                " R "+(int) Math.round(recovered[59]));
    }
}

