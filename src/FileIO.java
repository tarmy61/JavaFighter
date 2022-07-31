/**
 * This class is used interact with external files
 * @author Thomas Armstrong
 * @version ver 1.0.0
 */

import java.util.*;
import java.io.*;

public class FileIO
{
    private String fileName;

    /**
     * This is the default constructor for the class
     */
    public FileIO()
    {
        fileName = "";
    }

    /**
     * This is the  non-default constructor for the class
     * @param    fileName    A string set as the file name
     */
    public FileIO(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * This method attempts to create an object of type String, containing the contents of the file with the given file name
     */
    public String readFile()
    {
        String contents = "";
        if(fileName.trim().length() > 0)
        {
            try
            {
                FileReader inputFile = new FileReader(fileName);
                Scanner scanner = new Scanner(inputFile);
                while(scanner.hasNextLine())
                {
                    contents += scanner.nextLine() + "\n";
                }
                inputFile.close();
            }
            catch(FileNotFoundException exception)
            {
                System.out.println(fileName + " not found");
            }
            catch(IOException exception)
            {
                System.out.println("I/O error");
            }
            catch(Exception e)
            {
                System.out.println("Error in reading from file! Exiting...");
            }
        }
        else
        {
            System.out.println("File name invalid");
        }
        return contents;
    }

    /**
     * This method attempts to create a file and print to this
     * @param    outcome    A string passed in to be printed
     */
    public void writeFile(String outcome)
    {
        try
        {
            PrintWriter outputFile = new PrintWriter(fileName);
            outputFile.println(outcome);
            outputFile.close();
        }
        catch(IOException exception)
        {
            System.out.println("An error was encountered while trying to write the data to the " + fileName + " file.");
        }
    }
}
