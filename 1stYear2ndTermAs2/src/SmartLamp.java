import java.util.ArrayList;
import java.util.List;

/**
 * This class has attributes for smart Lamp
 * It has methods that get and set the kelvin and brightness values.
 * Also has constructor for every situation of parameter numbers.
 * It holds SmartLamp objects and their names in two ArrayList
 */
public class SmartLamp extends Devices{
    FileIO ioObj =new FileIO();
    public List<SmartLamp> smartLamps = new ArrayList<>();
    public List<String> lampNames = new ArrayList<>();
    private String kelvin = "4000";
    private int brightness = 100 ;

    public SmartLamp(){}
    public SmartLamp(String name ){
        super();
        this.name = name;
    }
    public SmartLamp(String name ,String switchStatus){
        super();
        this.name = name;
        this.switchStatus =switchStatus;
    }
    public SmartLamp(String name ,String switchStatus, String kelvin, int brightness){
        this.name = name;
        this.switchStatus =switchStatus;
        this.kelvin= kelvin;
        this.brightness = brightness;

    }
    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getKelvin() {
        return kelvin;
    }


    public void setKelvin(String kelvin) {
        this.kelvin = kelvin;
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
    public void giveReport(String out,SmartColorLamp colorLampObj, SmartLamp smartLampObj, SmartCamera smartCameraObj, SmartPlug smartPlugObj,String name) {
        int ind = smartLampObj.lampNames.indexOf(name);

        String message = "Smart Lamp "+getName()+" is "+getSwitchStatus().toLowerCase()+" and its kelvin value is "+smartLampObj.smartLamps.get(ind).getKelvin();
        try {
            String message2= "K with "+smartLampObj.smartLamps.get(ind).getBrightness()+"% brightness, and its time to switch its status is "+getSwitchTime().toString().replace("T","_")+".";
            ioObj.writeToFile(out,message+message2,true,true);
        }
        catch (NullPointerException e){
            String message2= "K with "+smartLampObj.smartLamps.get(ind).getBrightness()+"% brightness, and its time to switch its status is "+getSwitchTime()+".";
            ioObj.writeToFile(out,message+message2,true,true);
        }

    }

}
