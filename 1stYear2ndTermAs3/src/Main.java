public class Main {
    public static void main(String[] args) {
       String input = args[0]; String output = args[1];
       ReadingCommands readingCommands = new ReadingCommands();
       readingCommands.init(input,output);
    }
}