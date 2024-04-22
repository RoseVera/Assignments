import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * This class has attributes for smart plug
 * It has method that calculates total energy consumption
 * It holds SmartPlug objects and their names in two ArrayList
 */
public class SmartPlug extends Devices {
    FileIO ioObj = new FileIO();
    public List<SmartPlug> smartPlugs = new ArrayList<>();
    public List<String> plugNames = new ArrayList<>();

    private float ampere = 0;
    private float totalEnergy= 0;

    public SmartPlug(){
    }
    public SmartPlug(String name){
        this.name = name;}

    public SmartPlug(String name, String switchStatus){
        this.name= name;
        this.switchStatus= switchStatus;
    }
    public SmartPlug(String name, String switchStatus, float ampere){
        this.name= name;
        this.switchStatus= switchStatus;
        this.ampere= ampere;
    }
    public double getTotalEnergy() {
        return totalEnergy;
    }

    /**
     * This method calculates energy consumption in a time period and adds it to the total consumption.
     * @param timeController  instance of TimeController object
     * @param amper  Float ampere value.
     */
    public void setTotalEnergy(TimeController timeController , Float amper) {
        try {
            long seconds = ChronoUnit.SECONDS.between(openTime, timeController.getCurrentTime());
            float total =amper*220*(seconds)/3600;
            this.totalEnergy = totalEnergy + total;
        }catch (NullPointerException e){ // if no open time is set.
            this.totalEnergy = totalEnergy +0;
        }
    }
    public float getAmpere() {
        return ampere;
    }
    public void setAmpere(float ampere) {
        this.ampere = ampere;
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
        int ind = smartPlugObj.plugNames.indexOf(name);
        String totalConsumption  = String.format("%.2f",smartPlugObj.smartPlugs.get(ind).getTotalEnergy());
        String message = "Smart Plug "+getName()+" is "+getSwitchStatus().toLowerCase()+" and consumed "+totalConsumption.replace(".",",");
        try {
            String message2= "W so far (excluding current device), and its time to switch its status is "+getSwitchTime().toString().replace("T","_")+".";
            ioObj.writeToFile(out,message+message2,true,true);
        }
        catch (NullPointerException e){
            String message2= "W so far (excluding current device), and its time to switch its status is "+getSwitchTime()+".";
            ioObj.writeToFile(out,message+message2,true,true);
        }
    }
}
