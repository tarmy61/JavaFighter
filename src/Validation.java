/**
 * This class is used to validate input arguments from the user, to ensure outlined conditions are met
 * @author Thomas Armstrong
 * @version ver 1.0.0
 */

public class Validation
{

    /**
     * This is the default constructor for the class
     */
    public Validation()
    {

    }

    /**
     * This method validates if a String object has a length greater than zero
     * @param    input    A string passed in to be validated
     */
    public boolean isBlank(String input)
    {
        boolean statement = false;
        if (input.trim().length() == 0)
        {
            statement = true;
            throw new IllegalArgumentException("Input cannot be empty or all spaces.");
        }
        return statement;
    }

    /**
     * This method validates if a String object has a length inside a given range
     * @param    input    A string passed in to be validated
     * @param    lowerBound    The lower bound for the allowed range
     * @param    upperBound    The upper bound for the allowed range
     */
    public boolean lengthWithinRange(String input, int lowerBound, int upperBound)
    {
        boolean statement = false;
        if (input.length() >= lowerBound && input.length() <= upperBound)
        {
            statement = true;
        }
        else
        {
            throw new IllegalArgumentException("Input does not meet requirements.");
        }
        return statement;
    }
}
