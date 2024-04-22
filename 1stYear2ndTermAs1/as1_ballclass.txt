import java.util.ArrayList;
import java.util.List;
public class BallMoves {
    public List<Integer> indicesAccordingToDirecton(String direction,int r, int c, int whiteBallInd){
        List<Integer> data = new ArrayList<>();
        switch (direction){
            case "U":
                //If the white ball is not in the border of the table, it will go to this index
                data.add(whiteBallInd-c);
                //If the white ball is in the border and cannot go further, it will come out the opposite site of its direction
                data.add(whiteBallInd+((r-1)*c));
                break;
            case "D":
                data.add(whiteBallInd+c);
                data.add(whiteBallInd-((r-1)*c));
                break;
            case "R":
                data.add(whiteBallInd+1);
                data.add(whiteBallInd-(c-1));
                break;
            case "L":
                data.add(whiteBallInd-1);
                data.add(whiteBallInd-(c-1));
                break;
        }
        //first index of data gives us the targeted index in normal condition
        //second index is when the ball is in the border and cannot go further
        return data;
    }
    public Integer ballMoves(List<String> gameTable,String direction,String reverse,int r ,int c){
        int score =0;
        int whiteBallIndex = gameTable.indexOf("*");// take the current index of white ball.
        String targetLetter;
        int targetInd;
        List<Integer> data =  indicesAccordingToDirecton(direction,r ,c,whiteBallIndex);
        int targeteNoException = data.get(0);
        int targetWhenExceptionOccurs = data.get(1);

        // try-catch block checks that if the ball can go further from the border of the table or not.
        // It takes the proper data according to occurrence of an exception.
        try { targetLetter = gameTable.get(targeteNoException); targetInd = targeteNoException ;}
        catch (IndexOutOfBoundsException e){ targetLetter = gameTable.get(targetWhenExceptionOccurs); targetInd = targetWhenExceptionOccurs ;}

        switch (targetLetter) { //this switch block make the moves according to targeted index and targeted letter.
            case "R":
            case "Y":
            case "B":
                gameTable.set(targetInd, "*");
                gameTable.set(whiteBallIndex, "X");
                switch (targetLetter) {
                    case "R": // for red +10
                        score += 10;
                        break;
                    case "Y": // for red +5
                        score += 5;
                        break;
                    case "B": // for red -5
                        score -= 5;
                        break;
                }
                return score;
            case "H":
                gameTable.set(whiteBallIndex, " ");
                return -1; // this -1 represents that game is over. It is like a message that given to the program.
            case "W":
                // W is a special case.
                // So if ball hits the wall, it goes 1 unit to the opposite direction of the targeted direction.
                return ballMoves(gameTable,reverse,direction,r,c); // this method take the reverse direction as its targeted direction.
            default:
                // this case for the other letters that do not affect the score or the ball's direction.
                gameTable.set(targetInd,"*");
                gameTable.set(whiteBallIndex,targetLetter);
        }
        return score;
    }
}