import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Array;
import java.util.ArrayList;

public class ReadCsv {

    public int[] readLastColumn(int rows,int whichCol,String path){
        String data[];
        String currentLine;
        ArrayList<Integer> intData = new ArrayList<Integer>();
        ArrayList<String> allLines = new ArrayList<String>();

        try{
            FileReader fr =new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            while ((currentLine=br.readLine()) != null){
                allLines.add(currentLine);
                /*data = currentLine.split(",");
                intData.add(Integer.valueOf(data[4]));*/

            }

            for (int i = 1; i < rows+1; i++) {
                String row = allLines.get(i);
                data=row.split(",");
                intData.add(Integer.valueOf(data[whichCol]));
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
        int[] arr = new int[intData.size()];
        for (int i = 0; i < intData.size(); i++) {
            arr[i]=intData.get(i);
        }

        return  arr;

    }
}
