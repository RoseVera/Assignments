import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has attributes for SmartCamera and uses its super attributes as well.
 * It has a method that calculates total storage.
 * It holds SmartCamera objects and their names in two Arraylists.
 */
public class SmartCamera extends Devices{
    FileIO ioObj = new FileIO();
    public List<SmartCamera> smartCameras = new ArrayList<>();
    public List<String> cameraNames = new ArrayList<>();

    private float megabytesPerMinute; //non negative
    private float totalStorage=0;


TimeController timeController;
    public SmartCamera() {
    }

    public SmartCamera(TimeController timeController) {
        this.timeController=timeController;
    }
    public SmartCamera(String name , float megabytesPerMinute){
        super();
        this.name = name;
        this.megabytesPerMinute = megabytesPerMinute;

    }
    public SmartCamera(String name , float megabytesPerMinute,String switchStatus){
        super();
        this.name = name;
        this.switchStatus =switchStatus;
        this.megabytesPerMinute = megabytesPerMinute;

    }
    public double getTotalStorage() {
        return totalStorage;
    }

    /**
     * Calculates used storage in a time period and add to total storage.
     * @param timeController  instance of TimeController object.
     */
    public void setTotalStorage(TimeController timeController) {
        long seconds = ChronoUnit.SECONDS.between(openTime, timeController.getCurrentTime());
        float minutes = (float) (seconds/60.0);
        this.totalStorage = totalStorage +megabytesPerMinute*(minutes);
    }
    /**
     * It overrides the super method and give proper report message.
     * @param out  txt file to be written
     * @param colorLampObj  instance of SmartColorLamp class
     * @param smartLampObj  instance of SmartLamp class
     * @param smartCameraObj  instance of SmartCamera class
     * @param smartPlugObj  instance of SmartPlug class
     * @param name  name of the device whose report is wanted.
     */
    @Override
    public void giveReport(String out,SmartColorLamp colorLampObj, SmartLamp smartLampObj, SmartCamera smartCameraObj, SmartPlug smartPlugObj,String name){
        int ind = smartCameraObj.cameraNames.indexOf(name);
        String totalStorage = String.format("%.2f",smartCameraObj.smartCameras.get(ind).getTotalStorage());
        String message = "Smart Camera "+getName()+" is "+getSwitchStatus().toLowerCase()+" and used "+totalStorage.replace(".",",");
        try {
            String message2= " MB of storage so far (excluding current status), and its time to switch its status is "+getSwitchTime().toString().replace("T","_")+".";
            ioObj.writeToFile(out,message+message2,true,true);

        }catch (NullPointerException e){
            String message2= " MB of storage so far (excluding current status), and its time to switch its status is "+getSwitchTime()+".";
            ioObj.writeToFile(out,message+message2,true,true);
        }
    }
}
