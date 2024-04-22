import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the superclass for all devices
 * It has common attributes for each device: LocalDateTime switchTime= null; String switchStatus="Off"; String name;
 * and LocalDateTime openTime;
 * It holds every device and its name in two seperated Arraylists.
 */
public class Devices {
    protected LocalDateTime switchTime= null;
    protected String switchStatus="Off";
    protected String name;
    protected LocalDateTime openTime;
    protected List<Devices> devicesList= new ArrayList<>();
    protected  List<String> deviceNames = new ArrayList<>();

    public Devices() {}
    public LocalDateTime getSwitchTime() {return switchTime;}
    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }
    public String getSwitchStatus() {
        return switchStatus;
    }
    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }
    public List<Devices>  getDevicesList() {return devicesList;}
    public List<String> getDeviceNames() {
        return deviceNames;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    /**
     * This method is empty and for overriding in its subclasses
     * @param out  txt file to be written
     * @param colorLampObj  instance of SmartColorLamp class
     * @param smartLampObj  instance of SmartLamp class
     * @param smartCameraObj  instance of SmartCamera class
     * @param smartPlugObj  instance of SmartPlug class
     * @param name  name of the device whose report is wanted.
     */
    public void giveReport(String out,SmartColorLamp colorLampObj, SmartLamp smartLampObj, SmartCamera smartCameraObj, SmartPlug smartPlugObj,String name) {

    }
}