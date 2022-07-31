/**
 * This class holds information on the different moves to be used in the game
 * @author Thomas Armstrong
 * @version ver 1.0.0
 */

public class Moves
{
    private String moveName;
    private String effect;
    private double value;

    /**
     * This is the default constructor for the class
     */
    public Moves()
    {
        moveName = "unknown";
        effect = "unknown";
        value = -1;
    }

    /**
     * This is the  non-default constructor for the class
     * @param    moveName    A string set as the move name
     * @param    effect    A string set as the move effect
     * @param    value    A double set as the move value
     */
    public Moves(String moveName, String effect, double value)
    {
        this.moveName = moveName;
        this.effect = effect;
        this.value = value;
    }

    /**
     * This method displays the attributes of a given move
     */
    public String display()
    {
        return "Move Name: " + moveName + " (Effect: " + effect + ", Value: " + value + ")";
    }

    /**
     * This method retrieves the move effect attribute of a given move
     */
    public String getEffect()
    {
        return effect;
    }

    /**
     * This method retrieves the move name attribute of a given move
     */
    public String getMoveName()
    {
        return moveName;
    }

    /**
     * This method retrieves the move value attribute of a given move
     */
    public double getValue()
    {
        return value;
    }

    /**
     * This method sets the move effect attribute of a given move to a given input
     * @param    effect    A string set as the move effect
     */
    public void setEffect(String effect)
    {
        this.effect = effect;
    }

    /**
     * This method sets the move name attribute of a given move to a given input
     * @param    moveName    A string set as the move name
     */
    public void setMoveName(String moveName)
    {
        this.moveName = moveName;
    }

    /**
     * This method sets the move value attribute of a given move to a given input
     * @param    value    A double set as the move value
     */
    public void setValue(double value)
    {
        this.value = value;
    }
}
