/**
 * This class holds information on the events taking place in the game
 * @author Thomas Armstrong
 * @version ver 1.0.0
 */

import java.util.*;
import java.lang.*;

public class Fighter
{
    private ArrayList<Player> players;
    public final String INPUT_FILE_NAME = "specialmoves.txt";
    public final String OUTPUT_FILE_NAME = "outcome.txt";

    /**
     * This is the default constructor for the class
     */
    public Fighter()
    {
        players = new ArrayList<Player>();
    }

    /**
     * This is the non-default constructor for the class
     * @param    players    An array list (of class Player) set as the players attribute
     */
    public Fighter(ArrayList<Player> players)
    {
        this.players = players;
    }

    /**
     * This method writes the output file with the winner of the fight after a given input of turns
     * @param    counter    A number which has recorded the number of turns in fight
     */
    public void announceWinner(int counter)
    {
        String winner = "";
        if (getUserHealth() <= 0 && getComputerHealth() <= 0)
            winner = "absolutely nobody! You knocked each other out! It's a draw";
        else if (getUserHealth() <= 0)
            winner = players.get(1).getName();
        else
            winner = players.get(0).getName();

        winner = "The winner is " + winner + ". The fight took " + (counter-1) + " turns!";
        System.out.println(winner + " Saving the result...");

        FileIO outcome = new FileIO(OUTPUT_FILE_NAME);
        outcome.writeFile(winner);
    }

    /**
     * This method determines the outcome of the computer action for that turn
     * @param    nextAction    A number which has previously been input by the user
     * @param    computerAction    A number which has previously been randomly selected by the computer
     * @param    look    A boolean which has previously been randomly selected by the referee
     * @param    userBribe    A boolean which has previously been determined by the user
     * @param    computerBribe    A boolean which has previously been determined by the computer
     */
    public void computerMove(int nextAction, int computerAction, boolean look, boolean userBribe, boolean computerBribe)
    {
        if (getComputerMoveEffect(computerAction).equals("Damage") && !getUserMoveName(nextAction).equals("Block"))
        {
            if (getComputerMoveName(computerAction).equals("Cheat"))
            {
                if (computerBribe == false && look == false)
                {
                    setUserHealth(getUserHealth() - getComputerMoveValue(computerAction));
                }
                else if (computerBribe == false && look == true)
                {
                    setComputerHealth(getComputerHealth() - 20);
                }
                else if (computerBribe == true)
                {
                    setUserHealth(getUserHealth() - getComputerMoveValue(computerAction));
                }
            }
            else if (!getComputerMoveName(computerAction).equals("Cheat"))
            {
                setUserHealth(getUserHealth() - getComputerMoveValue(computerAction));
            }
        }
        else if (getComputerMoveEffect(computerAction).equals("Damage") && getUserMoveName(nextAction).equals("Block"))
        {
            // effect calculated elsewhere, after the user's actions are observed.
        }
        else if (getComputerMoveName(computerAction).equals("Block"))
        {
            if (getUserMoveEffect(nextAction).equals("Damage") && !getUserMoveName(nextAction).equals("Cheat"))
            {
                setComputerHealth(getComputerHealth() - (getComputerMoveValue(computerAction)*getUserMoveValue(nextAction)));
            }
            else if (getUserMoveName(nextAction).equals("Cheat"))
            {
                if (look == false)
                {
                    setComputerHealth(getComputerHealth() - (getComputerMoveValue(computerAction)*getUserMoveValue(nextAction)));
                }
                else if (look == true && userBribe == true)
                {
                    setComputerHealth(getComputerHealth() - (getComputerMoveValue(computerAction)*getUserMoveValue(nextAction)));
                }
                else if (look == true && userBribe == false)
                {
                    setUserHealth(getUserHealth() - 20);
                }
            }
        }
        else if (getComputerMoveEffect(computerAction).equals("Health"))
        {
            if (getComputerHealth() + getComputerMoveValue(computerAction) <= 100)
            {
                setComputerHealth(getComputerHealth() + getComputerMoveValue(computerAction));
            }
            else if (getComputerHealth() + getComputerMoveValue(computerAction) > 100)
            {
                setComputerHealth(100);
            }
        }
        else
        {
            throw new IllegalArgumentException("Invalid Move Used by Computer");
        }

        System.out.println(players.get(1).getName() + " used " + getComputerMoveName(computerAction) + ".");
    }

    /**
     * This method displays the necessary attributes of the array list of players
     */
    public void display()
    {
        for (int i = 0; i < players.size(); i++)
        {
            System.out.println(players.get(i).display());
        }
    }

    /**
     * This method displays the necessary attributes of the array list of user moves
     */
    public void displayUserMoves()
    {
        int i = 1;
        for (Moves temp : getUserMoves())
        {
            System.out.println("(" + i + ") " + temp.display());
            i++;
        }
    }

    /**
     * This method creates two objects of the Player class, one for the user and one for the computer, and sets the name attribute of the user to an input string
     */
    public void gameSetup()
    {
        Player user = new Player();
        players.add(user);

        Player computer = new Player("Deep Thought");
        players.add(computer);

        boolean flag = true;
        do
        {
            try
            {
                Input input = new Input();
                String newName = input.acceptStringInput("Please enter your name:");
                Validation nameCheck = new Validation();
                if (nameCheck.isBlank(newName) == false && nameCheck.lengthWithinRange(newName,3,12))
                {
                    user.setName(newName);
                    flag = false;
                }
            }
            catch (Exception e)
            {
                System.out.println("Input does not meet requirements. Please enter a name between 3 and 12 characters.");
            }
        } while (flag);

        for (Player temp : players)
            System.out.println(temp.display());
    }

    /**
     * This method begins the fight and allows it to continue while both players are conscious
     */
    public void gameStart()
    {
        readFile();

        int counter = 1;

        Referee referee = new Referee();
        boolean userBribe = false;
        boolean computerBribe = false;

        while (getUserHealth() > 0 && getComputerHealth() > 0)
        {
            displayUserMoves();

            try
            {
                boolean look = referee.RefCheck();

                int nextAction = generateUserAction(userBribe, counter);
                int computerAction = generateComputerAction(computerBribe, counter);

                // Toss to determine who goes first each turn.
                if (getRandom() < 50)
                {
                    userMove(nextAction, computerAction, look, userBribe, computerBribe);
                    computerMove(nextAction, computerAction, look, userBribe, computerBribe);
                }
                else
                {
                    computerMove(nextAction, computerAction, look, userBribe, computerBribe);
                    userMove(nextAction, computerAction, look, userBribe, computerBribe);
                }

                referee.display();

                System.out.println(players.get(0).getName() + ": " + getUserHealth() + " | " + players.get(1).getName() + ": " + getComputerHealth());

                removeUnusedUserMoves(nextAction);
                removeUnusedComputerMoves(computerAction);

                counter++; // and so the next turn begins...
            }
            catch (Exception e)
            {
                System.out.println("Input does not meet requirements. Choose a number from the list.");
            }
        }
        announceWinner(counter);
    }

    /**
     * This method randomly generates the computer's next action as according to given probabilities
     * @param    computerBribe    A boolean which has previously been determined by the computer
     * @param    counter    A number which has recorded the number of turns in fight
     */
    public int generateComputerAction(boolean computerBribe, int counter)
    {
        int computerAction = -1;
        boolean flag2 = false; // this flag prevents the computer from choosing an unavailable move

        do
        {
            int rand = getRandom();
            int rand2 = getRandom();
            if (getComputerMoves().size() == 8)
            {
                if (rand <= 25)
                {
                    computerAction = 1; //"Punch"
                    flag2 = true;
                }
                else if (rand > 25 && rand <= 50)
                {
                    computerAction = 2; //"Kick"
                    flag2 = true;
                }
                else if (rand > 50 && rand <= 70)
                {
                    computerAction = 3; //"Block"
                    flag2 = true;
                }
                else if (rand > 70 && rand <= 90)
                {
                    computerAction = 4; //"Cheat"
                    flag2 = true;
                }
                else if (rand > 90)
                {
                    if (rand2 <= 25)
                    {
                        computerAction = 5; //"Special Move - Double Punch"
                        flag2 = true;
                    }
                    else if (rand2 > 25 && rand2 <= 50)
                    {
                        computerAction = 6; //"Special Move - Double Kick"
                        flag2 = true;
                    }
                    else if (rand2 > 50 && rand2 <= 75)
                    {
                        computerAction = 7; //"Special Move - Double Heal"
                        flag2 = true;
                    }
                    else if (rand2 > 75 && counter == 1)
                    {
                        computerAction = 8; //"Special Move - Bribed Referee"
                        flag2 = true;
                        computerBribe = true;
                    }
                }
            }
            else if (getComputerMoves().size() < 8)
            {
                if (rand <= 25)
                {
                    computerAction = 1; //"Punch"
                    flag2 = true;
                }
                else if (rand > 25 && rand <= 50)
                {
                    computerAction = 2; //"Kick"
                    flag2 = true;
                }
                else if (rand > 50 && rand <= 70)
                {
                    computerAction = 3; //"Block"
                    flag2 = true;
                }
                else if (rand > 70 && rand <= 90)
                {
                    computerAction = 4; //"Cheat"
                    flag2 = true;
                }
                else if (rand > 90 && !players.get(1).getSpecificMove(4).equals("Bribed Referee"))
                {
                    computerAction = 5; //"Special Move"
                    flag2 = true;
                }
            }
        } while (!flag2);
        return computerAction;
    }

    /**
     * This method generates the user's next action based on input from the user
     * @param    userBribe    A boolean which has previously been determined by the user
     * @param    counter    A number which has recorded the number of turns in fight
     */
    public int generateUserAction(boolean userBribe, int counter)
    {
        boolean flag1 = false; // this flag prevents the user from choosing an unavailable move
        int nextAction = -1;
        do
        {
            Input input = new Input();
            nextAction = input.acceptIntegerInput("Choose your next move:");

            if (!getUserMoveName(nextAction).equals("Bribed Referee"))
            {
                nextAction = nextAction;
                flag1 = true;
            }
            else if (getUserMoveName(nextAction).equals("Bribed Referee") && counter == 1)
            {
                nextAction = nextAction;
                flag1 = true;
                userBribe = true;
            }
            else if (getUserMoveName(nextAction).equals("Bribed Referee") && counter != 1)
            {
                System.out.println("You can't bribe the referee after the first turn!");
            }
            else
            {
                throw new IllegalArgumentException("Invalid Move Used");
            }
        } while (!flag1);
        return nextAction;
    }

    /**
     * This method retrieves the health attribute of the computer player
     */
    public double getComputerHealth()
    {
        return players.get(1).getHealth();
    }

    /**
     * This method retrieves the effect attribute of a specific computer player move
     * @param    computerAction    A number passed in to be looked up
     */
    public String getComputerMoveEffect(int computerAction)
    {
        return getComputerMoves().get(computerAction-1).getEffect();
    }

    /**
     * This method retrieves the name attribute of a specific computer player move
     * @param    computerAction    A number passed in to be looked up
     */
    public String getComputerMoveName(int computerAction)
    {
        return getComputerMoves().get(computerAction-1).getMoveName();
    }

    /**
     * This method retrieves the moves attribute of a specific computer player
     */
    public ArrayList<Moves> getComputerMoves()
    {
        return players.get(1).getMoves();
    }

    /**
     * This method retrieves the value attribute of a specific computer player move
     * @param    computerAction    A number passed in to be looked up
     */
    public double getComputerMoveValue(int computerAction)
    {
        return getComputerMoves().get(computerAction-1).getValue();
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
     * This method retrieves the health attribute of the user player
     */
    public double getUserHealth()
    {
        return players.get(0).getHealth();
    }

    /**
     * This method retrieves the effect attribute of a specific user player move
     * @param    nextAction    A number passed in to be looked up
     */
    public String getUserMoveEffect(int nextAction)
    {
        return getUserMoves().get(nextAction-1).getEffect();
    }

    /**
     * This method retrieves the name attribute of a specific user player move
     * @param    nextAction    A number passed in to be looked up
     */
    public String getUserMoveName(int nextAction)
    {
        return getUserMoves().get(nextAction-1).getMoveName();
    }

    /**
     * This method retrieves the moves attribute of a specific user player
     */
    public ArrayList<Moves> getUserMoves()
    {
        return players.get(0).getMoves();
    }

    /**
     * This method retrieves the value attribute of a specific user player move
     * @param    nextAction    A number passed in to be looked up
     */
    public double getUserMoveValue(int nextAction)
    {
        return getUserMoves().get(nextAction-1).getValue();
    }

    /**
     * This is the main method which begins the program execution
     * @param    args    An array of class String passed in as command line parameters
     */
    public static void main(String[] args)
    {
        Fighter roundOne = new Fighter();
        roundOne.gameSetup();
        roundOne.gameStart();
    }

    /**
     * This method attempts to add special moves to the players' move pools, taking information from a supplied file
     */
    public void readFile()
    {
        FileIO io = new FileIO(INPUT_FILE_NAME);
        String[] input = io.readFile().split("\\n");

        for (int i = 0; i < input.length; i++)
        {
            String[] lineContents = input[i].split(",");
            String moveName = lineContents[0];
            String effect = lineContents[1];
            double value = -1;
            try
            {
                value = Double.parseDouble(lineContents[2]);
            }
            catch (Exception e)
            {
                System.out.println("Error in importing special move #" + (i+1) + ". Skipping.");
                continue;
            }
            getUserMoves().add(new Moves(moveName, effect, value));
            getComputerMoves().add(new Moves(moveName, effect, value));
        }
    }

    /**
     * This method removes the special moves not selected by the computer for this round
     * @param    computerAction    A number passed in to be looked up
     */
    public void removeUnusedComputerMoves(int computerAction)
    {
        if (computerAction == 5 && getComputerMoves().size() > 5)
        {
            getComputerMoves().remove(5);
            getComputerMoves().remove(5);
            getComputerMoves().remove(5);
        }
        if (computerAction == 6 && getComputerMoves().size() > 5)
        {
            getComputerMoves().remove(4);
            getComputerMoves().remove(5);
            getComputerMoves().remove(5);
        }
        if (computerAction == 7 && getComputerMoves().size() > 5)
        {
            getComputerMoves().remove(4);
            getComputerMoves().remove(4);
            getComputerMoves().remove(5);
        }
        if (computerAction == 8 && getComputerMoves().size() > 5)
        {
            getComputerMoves().remove(4);
            getComputerMoves().remove(4);
            getComputerMoves().remove(4);
        }
    }

    /**
     * This method removes the special moves not selected by the user for this round
     * @param    nextAction    A number passed in to be looked up
     */
    public void removeUnusedUserMoves(int nextAction)
    {
        if (nextAction == 5 && getUserMoves().size() > 5)
        {
            getUserMoves().remove(5);
            getUserMoves().remove(5);
            getUserMoves().remove(5);
        }
        if (nextAction == 6 && getUserMoves().size() > 5)
        {
            getUserMoves().remove(4);
            getUserMoves().remove(5);
            getUserMoves().remove(5);
        }
        if (nextAction == 7 && getUserMoves().size() > 5)
        {
            getUserMoves().remove(4);
            getUserMoves().remove(4);
            getUserMoves().remove(5);
        }
        if (nextAction == 8 && getUserMoves().size() > 5)
        {
            getUserMoves().remove(4);
            getUserMoves().remove(4);
            getUserMoves().remove(4);
            getUserMoves().remove(4);
        }
    }

    /**
     * This method sets the health attribute of a given computer player to a given input
     * @param    health    A double set as the health
     */
    public void setComputerHealth(double health)
    {
        players.get(1).setHealth(health);
    }

    /**
     * This method sets the health attribute of a given user player to a given input
     * @param    health    A double set as the health
     */
    public void setUserHealth(double health)
    {
        players.get(0).setHealth(health);
    }

    /**
     * This method determines the outcome of the user action for that turn
     * @param    nextAction    A number which has previously been input by the user
     * @param    computerAction    A number which has previously been randomly selected by the computer
     * @param    look    A boolean which has previously been randomly selected by the referee
     * @param    userBribe    A boolean which has previously been determined by the user
     * @param    computerBribe    A boolean which has previously been determined by the computer
     */
    public void userMove(int nextAction, int computerAction, boolean look, boolean userBribe, boolean computerBribe)
    {
        if (getUserMoveEffect(nextAction).equals("Damage") && !getComputerMoveName(computerAction).equals("Block"))
        {
            if (getUserMoveName(nextAction).equals("Cheat"))
            {
                if (userBribe == false && look == false)
                {
                    setComputerHealth(getComputerHealth() - getUserMoveValue(nextAction));
                }
                else if (userBribe == false && look == true)
                {
                    setUserHealth(getUserHealth() - 20);
                }
                else if (userBribe == true)
                {
                    setComputerHealth(getComputerHealth() - getUserMoveValue(nextAction));
                }
            }
            else if (!getUserMoveName(nextAction).equals("Cheat"))
            {
                setComputerHealth(getComputerHealth() - getUserMoveValue(nextAction));
            }
        }
        else if (getUserMoveEffect(nextAction).equals("Damage") && getComputerMoveName(computerAction).equals("Block"))
        {
            // effect calculated elsewhere, after the computer's actions are observed.
        }
        else if (getUserMoveName(nextAction).equals("Block"))
        {
            if (getComputerMoveEffect(computerAction).equals("Damage") && !getComputerMoveName(computerAction).equals("Cheat"))
            {
                setUserHealth(getUserHealth() - (getUserMoveValue(nextAction)*getComputerMoveValue(computerAction)));
            }
            else if (getComputerMoveName(computerAction).equals("Cheat"))
            {
                if (look == false)
                {
                    setUserHealth(getUserHealth() - (getUserMoveValue(nextAction)*getComputerMoveValue(computerAction)));
                }
                else if (look == true && computerBribe == true)
                {
                    setUserHealth(getUserHealth() - (getUserMoveValue(nextAction)*getComputerMoveValue(computerAction)));
                }
                else if (look == true && computerBribe == false)
                {
                    setComputerHealth(getComputerHealth() - 20);
                }
            }
        }
        else if (getUserMoveEffect(nextAction).equals("Health"))
        {
            if (getUserHealth() + getUserMoveValue(nextAction) <= 100)
            {
                setUserHealth(getUserHealth() + getUserMoveValue(nextAction));
            }
            else if (getUserHealth() + getUserMoveValue(nextAction) > 100)
            {
                setUserHealth(100);
            }
        }
        else
        {
            throw new IllegalArgumentException("Invalid Move Used by Player");
        }
        System.out.println(players.get(0).getName() + " used " + getUserMoveName(nextAction) + ".");
    }
}
