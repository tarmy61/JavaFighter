/**
 * This class holds information on the referee overseeing the game
 * @author Thomas Armstrong
 * @version ver 1.0.0
 */

import java.lang.*;

public class Referee
{
    private boolean looking;

    /**
     * This is the default constructor for the class
     */
    public Referee()
    {
        looking = true;
    }

    /**
     * This is the non-default constructor for the class
     * @param    looking    A boolean set as the looking attribute
     */
    public Referee(boolean looking)
    {
        this.looking = looking;
    }

    /**
     * This method displays the necessary attributes of a given referee
     */
    public void display()
    {
        System.out.println("Was the referee looking? " + looking);
    }

    /**
     * This method retrieves the looking attribute of a given referee
     */
    public boolean getLooking()
    {
        return looking;
    }

    /**
     * This method creates an object of class Integer, and assigns the value of this object to be a random number between 0 and 99 inclusive
     */
    public int getRandom()
    {
        int rand = (int)(Math.random() * 100);
        return rand;
    }

    /**
     * This method randomly determines the looking attribute of a referee
     */
    public boolean RefCheck()
    {
        int check = getRandom();
        if (check < 50)
            looking = false;
        else
            looking = true;
        return looking;
    }

    /**
     * This method sets the looking attribute of a given referee to a given input
     * @param    looking    A boolean set as the looking attribute
     */
    public void setLooking(boolean looking)
    {
        this.looking = looking;
    }
}
