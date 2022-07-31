/**
 * This class holds information on the different players in the game
 * @author Thomas Armstrong
 * @version ver 1.0.0
 */

import java.util.*;

public class Player
{
    private String name;
    private double health;
    private ArrayList<Moves> moves;

    /**
     * This is the default constructor for the class
     */
    public Player()
    {
        name = "unknown";
        health = 100;
        moves = new ArrayList<Moves>();
        moves.add(new Moves("Punch", "Damage", 10));
        moves.add(new Moves("Kick", "Damage", 20));
        moves.add(new Moves("Block", "Defence", 0.5));
        moves.add(new Moves("Cheat", "Damage", 40));
    }

    /**
     * This is the non-default constructor for the class
     * @param    name    A string set as the name attribute
     */
    public Player(String name)
    {
        this.name = name;
        health = 100;
        moves = new ArrayList<Moves>();
        moves.add(new Moves("Punch", "Damage", 10));
        moves.add(new Moves("Kick", "Damage", 20));
        moves.add(new Moves("Block", "Defence", 0.5));
        moves.add(new Moves("Cheat", "Damage", 40));
    }

    /**
     * This method displays the necessary attributes of a given player
     */
    public String display()
    {
        String message = "Player: " + name + " (Health: " + health + ")";
        return message;
    }

    /**
     * This method retrieves the health attribute of a given player
     */
    public double getHealth()
    {
        return health;
    }

    /**
     * This method retrieves the moves attribute of a given player
     */
    public ArrayList<Moves> getMoves()
    {
        return moves;
    }

    /**
     * This method retrieves the name attribute of a given player
     */
    public String getName()
    {
        return name;
    }

    /**
     * This method retrieves a specific move attribute of a given player
     */
    public Moves getSpecificMove(int index)
    {
        return moves.get(index);
    }

    /**
     * This method sets the health attribute of a given player to a given input
     * @param    health    A double set as the health
     */
    public void setHealth(double health)
    {
        this.health = health;
    }

    /**
     * This method sets the moves attribute of a given player to a given input
     * @param    moves    An array list (of class Moves) set as the moves
     */
    public void setMoves(ArrayList<Moves> moves)
    {
        this.moves = moves;
    }

    /**
     * This method sets the name attribute of a given player to a given input
     * @param    name    A string set as the name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * This method sets the a specific move attribute of a given player to a given input
     * @param    move    An object (of class Moves) set as the specific move
     */
    public void setSpecificMove(int index, Moves move)
    {
        moves.set(index, move);
    }
}
