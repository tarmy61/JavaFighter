/**
 * This class is used to parse different object types as input by the user
 * @author Thomas Armstrong
 * @version ver 1.0.0
 */

import java.util.Scanner;

public class Input
{
    /**
     * This is the default constructor for the class
     */
    public Input()
    {
        String message = "Error - message not found";
        String output = "";
    }

    /**
     * This method creates an object of class Character for the next input given by user
     * @param    message    A string passed in to be displayed on console
     */
    public char acceptCharInput(String message)
    {
        System.out.println(message);
        Scanner console = new Scanner(System.in);
        char output = console.nextLine().charAt(0);
        return output;
    }

    /**
     * This method creates an object of class Double for the next input given by user
     * @param    message    A string passed in to be displayed on console
     */
    public double acceptDoubleInput(String message)
    {
        System.out.println(message);
        Scanner console = new Scanner(System.in);
        double output = Double.parseDouble(console.nextLine());
        return output;
    }

    /**
     * This method creates an object of class Integer for the next input given by user
     * @param    message    A string passed in to be displayed on console
     */
    public int acceptIntegerInput(String message)
    {
        System.out.println(message);
        Scanner console = new Scanner(System.in);
        int output = Integer.parseInt(console.nextLine());
        return output;
    }

    /**
     * This method creates an object of class String for the next input given by user
     * @param    message    A string passed in to be displayed on console
     */
    public String acceptStringInput(String message)
    {
        System.out.println(message);
        Scanner console = new Scanner(System.in);
        String output = console.nextLine();
        return output;
    }
}
