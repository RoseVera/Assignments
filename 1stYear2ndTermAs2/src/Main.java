/**
 * This class is for initialising the program.
 * It takes input and output files from terminal.
 */

public class Main {
    /**
     * It implements readingCommands class.
     * Calls init() method from readingCommands class by giving input and output as arguments
     * @param args comes from command prompt
     */
    public static void main(String[] args) {
        String input = args[0];
        String output = args[1];
        ReadingCommands readingCommands = new ReadingCommands();
        readingCommands.init(input,output);
        }
}