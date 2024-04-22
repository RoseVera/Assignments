import java.util.*;
public class Main {
    public static void main(String[] args) {
        FileIO ioObj = new FileIO(); // Creating a ioObj to use methods of FileIO class.

        String[] lines = ioObj.readFile("board.txt", false, false);
        String[] linesOfMoves = ioObj.readFile("move.txt", false, false);

        ioObj.writeToFile("output.txt", "Game board:", true, true);
        for (String line : lines) {
            ioObj.writeToFile("output.txt", line, true, true);} //write game board on output.txt

        List<String> gridList = new ArrayList<>(Arrays.asList(lines));
        assert linesOfMoves != null;
        List<String> moveArray = new ArrayList<>(Arrays.asList(linesOfMoves));
        String[] splitMoves = moveArray.get(0).split(" ");
        List<String> moveList = new ArrayList<>(Arrays.asList(splitMoves));

        int r = gridList.size();  // Number of rows
        String[] strSplit = gridList.get(0).split(" ");
        int c = strSplit.length;  // Number of columns

        List<String> unitedGrid = new ArrayList<>();  //Add all the letters in board in one single array list.
        for(String row : gridList){List<String> strRow = new ArrayList<>(Arrays.asList(row.split(" ")));
            unitedGrid.addAll(strRow);}

        BallMoves ballObj = new BallMoves(); // Creating a ballObj to use methods of BallMoves class.
        int totalScore=0;
        ioObj.writeToFile("output.txt", "\nYour movement is:", true, true);
        boolean isGameOver = false;  // It tells us when the white ball fall into hole.
        label:
        for (String direction:moveList){
            // First write every move that implemented.
            ioObj.writeToFile("output.txt", direction+" ", true, false);
            switch (direction) { // There are 4 cases for direction of movement.
                case "U":
                    int returnValue = ballObj.ballMoves(unitedGrid, "U", "D", r, c);
                    if (returnValue == -1) { // if return value is -1 that means ball fell into the hole.
                        isGameOver=true;
                        break label;
                    } else {
                        totalScore += returnValue;
                    }
                    break;
                case "D":
                    int returnValue2 = ballObj.ballMoves(unitedGrid, "D", "U", r, c);
                    if (returnValue2 == -1) {
                        isGameOver=true;
                        break label;
                    } else {
                        totalScore += returnValue2;
                    }
                    break;
                case "R":
                    int returnValue3 = ballObj.ballMoves(unitedGrid, "R", "L", r, c);
                    if (returnValue3 == -1) {
                        isGameOver=true;
                        break label;
                    } else {
                        totalScore += returnValue3;
                    }
                    break;
                case "L":
                    int returnValue4 = ballObj.ballMoves(unitedGrid, "L", "R", r, c);
                    if (returnValue4 == -1) {
                        isGameOver=true;
                        break label;
                    } else {
                        totalScore += returnValue4;
                    }
                    break;
            }
        }
        // Last part is writing final display of the game board and the total score.
        ioObj.writeToFile("output.txt", "\n\nYour output is:", true, true);
        int i=0;int j =c;
        for(int k=0; k < r; k++){
            String listString = String.join(" ", unitedGrid.subList(i,j));
            ioObj.writeToFile("output.txt", listString, true, true);
            i+=c;j+=c;
        }
        if (isGameOver){ioObj.writeToFile("output.txt", "\nGame Over!", true, true);}
        ioObj.writeToFile("output.txt", "Score: "+totalScore, true, false);
    }
}