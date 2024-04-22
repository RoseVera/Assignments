import java.util.ArrayList;
import java.util.List;

/**
 * This is SmartcolorLamp classes extends from SmartLamp class
 * It has colorlamp attributes namely kelvin and brightness
 * It has methods that get and set the kelvin and brightness values.
 * Kelvin and brightness variables initialised with default values: 4000K and 100%
 */
public class SmartColorLamp extends SmartLamp{
    public List<SmartColorLamp> smartColorLamps = new ArrayList<>();
    public List<String> sColorNames = new ArrayList<>();
    private String kelvin = "4000";
    private int brightness = 100 ;


FileIO ioObj = new FileIO();
    public SmartColorLamp(){
    }
    public SmartColorLamp(String name ){
        super(name);
        this.name = name;
    }
    public SmartColorLamp(String name ,String switchStatus){
        super(name,switchStatus);
        this.name = name;
        this.switchStatus =switchStatus;

    }
    public SmartColorLamp(String name ,String switchStatus, String kelvin, int brightness){
        super(name,switchStatus,kelvin,brightness);
        this.name = name;
        this.kelvin= kelvin;
        this.switchStatus =switchStatus;
        this.brightness =brightness;

    }

    public String getKelvin() {
        return kelvin;
    }

    public void setKelvin(String kelvin) {
        this.kelvin = kelvin;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
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
        int ind = colorLampObj.sColorNames.indexOf(name);
        String message = "Smart Color Lamp "+name+" is "+getSwitchStatus().toLowerCase()+" and its color value is "+colorLampObj.smartColorLamps.get(ind).getKelvin();
        try {
            if(colorLampObj.smartColorLamps.get(ind).getKelvin().contains("x")){
                String message2= " with "+colorLampObj.smartColorLamps.get(ind).getBrightness()+"% brightness, and its time to switch its status is "+getSwitchTime().toString().replace("T","_")+".";
                ioObj.writeToFile(out,message+message2,true,true);
            }
            else{String message2= "K with "+colorLampObj.smartColorLamps.get(ind).getBrightness()+"% brightness, and its time to switch its status is "+getSwitchTime().toString().replace("T","_")+".";
                ioObj.writeToFile(out,message+message2,true,true);}

        }
        catch (NullPointerException e){
            if(colorLampObj.smartColorLamps.get(ind).getKelvin().contains("x")){
                String message2= " with "+colorLampObj.smartColorLamps.get(ind).getBrightness()+"% brightness, and its time to switch its status is "+getSwitchTime()+".";
                ioObj.writeToFile(out,message+message2,true,true);
            }
            else{String message2= "K with "+colorLampObj.smartColorLamps.get(ind).getBrightness()+"% brightness, and its time to switch its status is "+getSwitchTime()+".";
                ioObj.writeToFile(out,message+message2,true,true);}

        }
    }
}
