import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class reads the commands line by line and carry out them.
 * It has three methods.(public void init(), public void checkSwitchTime() and public void readCommands()
 * It implements instances of the classes: TimeController FileIO Errors Devices SmartLamp SmartColorLamp SmartPlug
 * and SmartCamera.
 */
public class ReadingCommands {
    public ReadingCommands(){
    }
    TimeController timeController = new TimeController();
    FileIO ioObj = new FileIO();
    Errors errors = new Errors();
    Devices devices = new Devices();
    SmartLamp smartLampObj = new SmartLamp();
    SmartColorLamp colorLampObj = new SmartColorLamp();
    SmartPlug smartPlugObj = new SmartPlug();
    SmartCamera smartCameraObj = new SmartCamera();

    /**
     * This method reads lines from txt file and get first command.(SetInitialTime)
     * @param input  txt file to be read
     * @param output  txt file to be written
     */
    public void init(String input,String output){
    String[] linesArr = ioObj.readFile(input,true,true);
    List<String> lines = Arrays.asList(linesArr);
    String[] strSplit = linesArr[0].split("\t");
    List<String> firstLine = Arrays.asList(strSplit);
        if ((!firstLine.get(0).equals("SetInitialTime")) || (firstLine.size()==1)){
        ioObj.writeToFile(output,"COMMAND: "+linesArr[0],true,true);
        ioObj.writeToFile(output,"ERROR: First command must be set initial time! Program is going to terminate!",true,false);
        }
        else if (errors.setInitalTimeError(output,firstLine,linesArr[0])){
            LocalDateTime dateTime = errors.createLocalDateTime(firstLine.get(1));
            timeController.setInitalTime(dateTime);
            timeController.setCurrentTime(dateTime);
            ioObj.writeToFile(output,"COMMAND: "+linesArr[0],true,true);
            ioObj.writeToFile(output,"SUCCESS: Time has been set to "+dateTime.toString().replace("T","_")+"!",true,true);

            List<String> newLines = lines.subList(1,lines.size()); //remove first line
        for (String line : newLines) {
            String[] strSplit2 = line.split("\t");
            List<String> argsList = Arrays.asList(strSplit2);
            readCommands( output, argsList, line);
        }
        if(!newLines.get(newLines.size() - 1).equals("ZReport")){
            List<String> argsList = new ArrayList<>();
            argsList.add("ZReport");argsList.add("ZReport");
            readCommands( output, argsList, "ZReport");
            System.out.println();
        }

        }
    }

    /**
     * This method checks the devices whose switch time comes when a new time set and minutes skip
     * It also change the index of an object after switch operation and becomes null value.
     * @param devicesList  list of devices objects
     * @param deviceNames  list of names of devices objects
     */
    public void checkSwitchTime(List<Devices> devicesList, List<String> deviceNames){
        for (int i= 0; i<devicesList.size();i++) {
            if (devicesList.get(i).getSwitchTime()!=null){
            if(timeController.getCurrentTime().isAfter(devicesList.get(i).getSwitchTime())|| timeController.getCurrentTime().isEqual(devicesList.get(i).getSwitchTime())){
                //Off
                devicesList.get(i).setSwitchTime(null);
                if(devicesList.get(i).getSwitchStatus().equals("On")){
                    devicesList.get(i).setSwitchStatus("Off");
                if(devicesList.get(i) instanceof SmartPlug){
                    String name = deviceNames.get(i);
                    int indexPlug = smartPlugObj.plugNames.indexOf(name);
                    smartPlugObj.smartPlugs.get(indexPlug).setTotalEnergy(timeController,smartPlugObj.smartPlugs.get(indexPlug).getAmpere());
                } else if (devicesList.get(i) instanceof SmartCamera) {
                    String name = deviceNames.get(i);
                    int indexCamera = smartCameraObj.cameraNames.indexOf(name);
                    smartCameraObj.smartCameras.get(indexCamera).setTotalStorage(timeController); }
                }
                else {// On
                    devicesList.get(i).setSwitchStatus("On");
                    devicesList.get(i).setOpenTime(timeController.getCurrentTime());}

                devicesList.add(0,devicesList.get(i));
                devicesList.remove(i+1);
                deviceNames.add(0,deviceNames.get(i));
                deviceNames.remove(i+1);
            }
        }
    }
}

    /**
     * This method reads commands and do what it requires.
     *
     * @param output  txt file to be written
     * @param argsList  list of every word that split by tab for each line
     * @param commandLine  whole line as a string
     */
    public void readCommands(String output ,List<String> argsList,String commandLine){
        String command = argsList.get(0);
        switch (command){
            case "Add":
                if(errors.addCommandError(output,argsList,commandLine,devices)) {
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    String deviceName = argsList.get(2);
                    String deviceType = argsList.get(1);
                    switch (deviceType) {
                        case "SmartLamp":

                            devices.getDeviceNames().add(deviceName);
                            smartLampObj.lampNames.add(deviceName);
                            if (argsList.size() == 3) {
                                devices.getDevicesList().add(new SmartLamp(deviceName));
                                smartLampObj.smartLamps.add(new SmartLamp(deviceName));

                            } else if (argsList.size() == 4) {
                                devices.getDevicesList().add(new SmartLamp(deviceName, argsList.get(3)));
                                smartLampObj.smartLamps.add(new SmartLamp(deviceName, argsList.get(3)));

                            } else if (argsList.size() == 6) {
                                String kelvin = argsList.get(4);
                                int brightness = Integer.parseInt(argsList.get(5));
                                devices.getDevicesList().add(new SmartLamp(deviceName, argsList.get(3), kelvin, brightness));
                                smartLampObj.smartLamps.add(new SmartLamp(deviceName, argsList.get(3), kelvin, brightness));
                            }
                            //smartLampObj.smartLamps.get(0).giveReport(out);
                            break;

                        case "SmartColorLamp":
                            devices.getDeviceNames().add(deviceName);
                            colorLampObj.sColorNames.add(deviceName);
                            if (argsList.size() == 3) {
                                devices.getDevicesList().add(new SmartColorLamp(deviceName));
                                colorLampObj.smartColorLamps.add(new SmartColorLamp(deviceName));

                            } else if (argsList.size() == 4) {
                                devices.getDevicesList().add(new SmartColorLamp(deviceName, argsList.get(3)));
                                colorLampObj.smartColorLamps.add(new SmartColorLamp(deviceName, argsList.get(3)));

                            } else if (argsList.size() == 6) {
                                String kelvin = argsList.get(4);
                                int brightness = Integer.parseInt(argsList.get(5));
                                SmartColorLamp colorLamp = new SmartColorLamp(deviceName, argsList.get(3), kelvin, brightness);
                                devices.getDevicesList().add(colorLamp);
                                colorLampObj.smartColorLamps.add(colorLamp);
                            }
                            //colorLampObj.smartColorLamps.get(0).giveReport(out);
                            break;

                        case "SmartCamera":
                            //SmartCamera camera =new SmartCamera(timeController);
                            devices.getDeviceNames().add(deviceName);
                            smartCameraObj.cameraNames.add(deviceName);

                            float megabyte = Float.parseFloat(argsList.get(3));
                            if (argsList.size() == 4) {
                                devices.getDevicesList().add(new SmartCamera(deviceName, megabyte));
                                smartCameraObj.smartCameras.add(new SmartCamera(deviceName, megabyte));
                            } else if (argsList.size() == 5) {
                                String switchStatue = argsList.get(4);
                                SmartCamera smartCamera = new SmartCamera(deviceName, megabyte, switchStatue);
                                if (switchStatue.equals("On")) {
                                    smartCamera.setOpenTime(timeController.getCurrentTime());
                                }
                                devices.getDevicesList().add(smartCamera);
                                smartCameraObj.smartCameras.add(smartCamera);
                            }
                            //smartCameraObj.smartCameras.get(0).giveReport(out);
                            break;

                        case "SmartPlug":
                            devices.getDeviceNames().add(deviceName);
                            smartPlugObj.plugNames.add(deviceName);
                            if (argsList.size() == 3) {
                                devices.getDevicesList().add(new SmartPlug(deviceName));
                                smartPlugObj.smartPlugs.add(new SmartPlug(deviceName));
                            } else if (argsList.size() == 4) {
                                String switchStatue = argsList.get(3);
                                devices.getDevicesList().add(new SmartPlug(deviceName, switchStatue));
                                smartPlugObj.smartPlugs.add(new SmartPlug(deviceName, switchStatue));

                            } else if (argsList.size() == 5) {
                                String switchStatue = argsList.get(3);
                                String ampere = argsList.get(4);
                                SmartPlug plug = new SmartPlug(deviceName, switchStatue, Float.parseFloat(ampere));
                                if (switchStatue.equals("On")) {
                                    plug.setOpenTime(timeController.getCurrentTime());
                                }
                                devices.getDevicesList().add(plug);
                                smartPlugObj.smartPlugs.add(plug);
                            }
                            //smartPlugObj.smartPlugs.get(0).giveReport(out);
                            break;

                    }
                }
                break;
            case "SetBrightness": //Command-1
                if(errors.brightnessError(output,argsList,commandLine,devices,smartLampObj.lampNames,colorLampObj.sColorNames)) {
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    String name1 = argsList.get(1);
                    if (smartLampObj.lampNames.contains(name1)) {
                        int index1 = smartLampObj.lampNames.indexOf(name1);
                        smartLampObj.smartLamps.get(index1).setBrightness(Integer.parseInt(argsList.get(2)));
                        //System.out.println("brhgt: "+smartLampObj.smartLamps.get(index1).getBrightness());
                    } else if (colorLampObj.sColorNames.contains(name1)) {
                        int index1 = colorLampObj.sColorNames.indexOf(name1);
                        colorLampObj.smartColorLamps.get(index1).setBrightness(Integer.parseInt(argsList.get(2)));
                        //System.out.println("brhgt: "+colorLampObj.smartColorLamps.get(index1).getBrightness());
                    }
                }
                break;

            case "SetKelvin": //Command-2
                if(errors.kelvinError(output,argsList,commandLine,devices,smartLampObj.lampNames,colorLampObj.sColorNames)){
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    String name2 = argsList.get(1);
                    if (smartLampObj.lampNames.contains(name2)) {
                        int index2 = smartLampObj.lampNames.indexOf(name2);
                        smartLampObj.smartLamps.get(index2).setKelvin(argsList.get(2));
                        //System.out.println("kelv: "+smartLampObj.smartLamps.get(index2).getKelvin());
                    } else if (colorLampObj.sColorNames.contains(name2)) {
                        int index2 = colorLampObj.sColorNames.indexOf(name2);
                        colorLampObj.smartColorLamps.get(index2).setKelvin(argsList.get(2));
                        //System.out.println("kelv: "+colorLampObj.smartColorLamps.get(index2).getKelvin());
                    }
                }
                break;

            case "SetWhite": //Command-3
                if(errors.whiteError(output,argsList,commandLine,devices,smartLampObj.lampNames,colorLampObj.sColorNames)) {
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    String name3 = argsList.get(1);
                    if (smartLampObj.lampNames.contains(name3)) {
                        int index3 = smartLampObj.lampNames.indexOf(name3);
                        smartLampObj.smartLamps.get(index3).setKelvin(argsList.get(2));
                        smartLampObj.smartLamps.get(index3).setBrightness(Integer.parseInt(argsList.get(3)));


                    } else if (colorLampObj.sColorNames.contains(name3)) {
                        int index3 = colorLampObj.sColorNames.indexOf(name3);
                        colorLampObj.smartColorLamps.get(index3).setKelvin(argsList.get(2));
                        colorLampObj.smartColorLamps.get(index3).setBrightness(Integer.parseInt(argsList.get(3)));
                    }


                }
                break ;
            case "SetColorCode": //Command-4
                if(errors.colorCodeError(output,argsList,commandLine,devices,colorLampObj.sColorNames)) {
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    String name4 = argsList.get(1);
                    int index4 = colorLampObj.sColorNames.indexOf(name4);
                    colorLampObj.smartColorLamps.get(index4).setKelvin(argsList.get(2));

                    // System.out.println("kelv: "+colorLampObj.smartColorLamps.get(index4).getKelvin());
                }
                break;

            case "SetColor": //Command-5
                if(errors.colorError(output,argsList,commandLine,devices,colorLampObj.sColorNames)) {
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    String name5 = argsList.get(1);
                    int index5 = colorLampObj.sColorNames.indexOf(name5);
                    colorLampObj.smartColorLamps.get(index5).setKelvin(argsList.get(2));
                    colorLampObj.smartColorLamps.get(index5).setBrightness(Integer.parseInt(argsList.get(3)));

                }
                break;

            case "Switch": //Command-6

                if(errors.switchError(output,argsList,commandLine,devices)){
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);

                    String name66 = argsList.get(1);
                    int index66 = devices.getDeviceNames().indexOf(name66);

                    if (smartPlugObj.plugNames.contains(name66)) {// it is a plug
                        int indexPlug = smartPlugObj.plugNames.indexOf(name66);
                        if (argsList.get(2).equals("On")){
                            if(smartPlugObj.smartPlugs.get(indexPlug).getAmpere()==0.00){
                                devices.getDevicesList().get(index66).setSwitchStatus("On");
                                smartPlugObj.smartPlugs.get(indexPlug).setSwitchStatus("On");

                            }
                            else {
                                smartPlugObj.smartPlugs.get(indexPlug).setOpenTime(timeController.getCurrentTime());

                                devices.getDevicesList().get(index66).setSwitchStatus("On");
                                smartPlugObj.smartPlugs.get(indexPlug).setSwitchStatus("On");
                            }
                        }
                        else if (argsList.get(2).equals("Off")) {
                            smartPlugObj.smartPlugs.get(indexPlug).setTotalEnergy(timeController,smartPlugObj.smartPlugs.get(indexPlug).getAmpere());// ampere var mi yok mu bakmadim yoksa zaten 0 hesapliyor

                            devices.getDevicesList().get(index66).setSwitchStatus("Off");
                            smartPlugObj.smartPlugs.get(indexPlug).setSwitchStatus("Off");
                        }
                    } else if (smartCameraObj.cameraNames.contains(name66)) {// it is a camera
                        int indexCamera = smartCameraObj.cameraNames.indexOf(name66);
                        if (argsList.get(2).equals("On")) { // if switch status is on and plug in set opentime
                            smartCameraObj.smartCameras.get(indexCamera).setOpenTime(timeController.getCurrentTime());

                            smartCameraObj.smartCameras.get(indexCamera).setSwitchStatus("On");
                            devices.getDevicesList().get(index66).setSwitchStatus("On");

                        } else if (argsList.get(2).equals("Off")) { // onceki statuyu kontrol etmeyi unutma
                            smartCameraObj.smartCameras.get(indexCamera).setTotalStorage(timeController);

                            smartCameraObj.smartCameras.get(indexCamera).setSwitchStatus("Off");
                            devices.getDevicesList().get(index66).setSwitchStatus("Off");

                        }
                    } else {//it is smartlamp or smartcolor lamp
                        if (devices.getDevicesList().get(index66) instanceof SmartLamp){
                            int sml = smartLampObj.lampNames.indexOf(name66);
                            smartLampObj.smartLamps.get(sml).setSwitchStatus(argsList.get(2));
                            devices.getDevicesList().get(index66).setSwitchStatus(argsList.get(2));
                        }
                        if (devices.getDevicesList().get(index66) instanceof SmartColorLamp){
                            int scl = colorLampObj.sColorNames.indexOf(name66);
                            colorLampObj.smartColorLamps.get(scl).setSwitchStatus(argsList.get(2));
                            devices.getDevicesList().get(index66).setSwitchStatus(argsList.get(2));
                        }
                    }

                }
                break ;

            case "PlugIn": //Command-7
                if(errors.plugInErrors(output,argsList,commandLine,devices,smartPlugObj.plugNames,smartPlugObj.smartPlugs)) {
                    //cihazin plug olup olmadigini kontrol et!
                    // prize baska bir cihaz takili mi diye kontrol et
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    String name7 = argsList.get(1);
                    int index7 = smartPlugObj.plugNames.indexOf(name7);
                    smartPlugObj.smartPlugs.get(index7).setAmpere(Float.parseFloat(argsList.get(2)));
                    smartPlugObj.smartPlugs.get(index7).setOpenTime(timeController.getCurrentTime());
                }
                break ;

            case "PlugOut":  //Command-8
                if(errors.plugOutErrors(output,argsList,commandLine,devices,smartPlugObj.plugNames,smartPlugObj.smartPlugs)) {
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    //cihazin plug olup olmadigini kontrol et!
                    // prize bos mu kontrol et
                    String name8 = argsList.get(1);
                    int index8 = smartPlugObj.plugNames.indexOf(name8);
                    if(smartPlugObj.smartPlugs.get(index8).getSwitchStatus().equals("On")){
                        smartPlugObj.smartPlugs.get(index8).setTotalEnergy(timeController,smartPlugObj.smartPlugs.get(index8).getAmpere());

                    } //switch off gibi dusunup calculate

                    smartPlugObj.smartPlugs.get(index8).setAmpere(0); // prizden cikar
                }
                break ;

            case "ChangeName": //Command-9
                if(errors.changeNameError(output,argsList,commandLine,devices)){
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    String name9 = argsList.get(1);
                int index9 = devices.getDeviceNames().indexOf(name9);
                if(devices.getDevicesList().get(index9) instanceof SmartPlug){
                    int indP = smartPlugObj.plugNames.indexOf(name9);
                    smartPlugObj.smartPlugs.get(indP).setName(argsList.get(2));
                    devices.getDevicesList().get(index9).setName(argsList.get(2)); //set new name
                    smartPlugObj.plugNames.set(indP,argsList.get(2));
                    devices.getDeviceNames().set(index9,argsList.get(2));
                }
                else if(devices.getDevicesList().get(index9) instanceof SmartCamera){
                    int indP = smartCameraObj.cameraNames.indexOf(name9);
                    smartCameraObj.smartCameras.get(indP).setName(argsList.get(2));
                    devices.getDevicesList().get(index9).setName(argsList.get(2)); //set new name
                    smartCameraObj.cameraNames.set(indP,argsList.get(2));
                    devices.getDeviceNames().set(index9,argsList.get(2));
                }
                else if(devices.getDevicesList().get(index9) instanceof SmartLamp){
                    int indP = smartLampObj.lampNames.indexOf(name9);
                    smartLampObj.smartLamps.get(indP).setName(argsList.get(2));
                    devices.getDevicesList().get(index9).setName(argsList.get(2)); //set new name
                    smartLampObj.lampNames.set(indP,argsList.get(2));
                    devices.getDeviceNames().set(index9,argsList.get(2));
                }
                else if(devices.getDevicesList().get(index9) instanceof SmartColorLamp){
                    int indP = colorLampObj.sColorNames.indexOf(name9);
                    colorLampObj.smartColorLamps.get(indP).setName(argsList.get(2));
                    devices.getDevicesList().get(index9).setName(argsList.get(2)); //set new name
                    colorLampObj.sColorNames.set(indP,argsList.get(2));
                    devices.getDeviceNames().set(index9,argsList.get(2));

                }
            }
                break ;

            case "SetTime":
                if(errors.setTimeError(output,argsList,commandLine,timeController)) {
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    LocalDateTime dateTime = errors.createLocalDateTime(argsList.get(1));
                    timeController.setCurrentTime(dateTime);
                    checkSwitchTime(devices.devicesList,devices.deviceNames);
                }
                break ;

            case "SkipMinutes":
                if(errors.skipMinErrors(output,argsList,commandLine)) {
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    // yeni time i set ettikte sonra burda switch time i gelen cihazlari switch et !!!
                    int minutes = Integer.parseInt(argsList.get(1));
                    LocalDateTime newTime = timeController.getCurrentTime().plusMinutes(minutes);
                    timeController.setCurrentTime(newTime);
                    checkSwitchTime(devices.getDevicesList(),devices.getDeviceNames());
                }
                break ;

            case "Nop":
                List<Devices> switchTimes = new ArrayList<>();
                for (Devices device:devices.getDevicesList()) {
                    if(device.getSwitchTime()!=null){switchTimes.add(device);}
                }
                if(switchTimes.size()==0){
                    errors.nopCommand(commandLine,output);
                }
                else {
                    ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                    switchTimes.sort(Comparator.comparing(Devices::getSwitchTime));
                    timeController.setCurrentTime(switchTimes.get(0).getSwitchTime());
                    checkSwitchTime(devices.getDevicesList(),devices.getDeviceNames());
                }
                break;

            case "SetSwitchTime": //Command-10
                if(errors.switchTimeError(output,argsList,commandLine,devices,timeController)) {
                    ioObj.writeToFile(output, "COMMAND: " + commandLine, true, true);
                    String name10 = argsList.get(1);
                    int index10 = devices.getDeviceNames().indexOf(name10);
                    LocalDateTime dateTime = errors.createLocalDateTime(argsList.get(2));
                    devices.getDevicesList().get(index10).setSwitchTime(dateTime);
                    checkSwitchTime(devices.getDevicesList(),devices.getDeviceNames());
                }
                break ;


            case "Remove":
                if (errors.removeError(output,argsList,commandLine,devices)) {
                    ioObj.writeToFile(output, "COMMAND: " + commandLine, true, true);
                    ioObj.writeToFile(output, "SUCCESS: Information about removed smart device is as follows:", true, true);
                    String name = argsList.get(1);
                    int index = devices.getDeviceNames().indexOf(name);
                    if (devices.getDevicesList().get(index) instanceof SmartPlug) {
                        int indPlug = smartPlugObj.plugNames.indexOf(name);
                        smartPlugObj.smartPlugs.get(indPlug).setSwitchStatus("Off");
                        smartPlugObj.smartPlugs.get(indPlug).setTotalEnergy(timeController,smartPlugObj.smartPlugs.get(indPlug).getAmpere());
                        devices.getDevicesList().get(index).giveReport(output,colorLampObj,smartLampObj,smartCameraObj,smartPlugObj, name);
                        devices.getDevicesList().remove(index);
                        devices.getDeviceNames().remove(index);
                        smartPlugObj.smartPlugs.remove(indPlug);
                        smartPlugObj.plugNames.remove(name);
                    } else if (devices.getDevicesList().get(index) instanceof SmartCamera) {
                        int indCam = smartCameraObj.cameraNames.indexOf(name);
                        smartCameraObj.smartCameras.get(indCam).setSwitchStatus("Off");
                        smartCameraObj.smartCameras.get(indCam).setTotalStorage(timeController);
                        devices.getDevicesList().get(index).giveReport(output,colorLampObj,smartLampObj,smartCameraObj,smartPlugObj,name);
                        devices.getDevicesList().remove(index);
                        devices.getDeviceNames().remove(index);
                        smartCameraObj.smartCameras.remove(indCam);
                        smartCameraObj.cameraNames.remove(name);
                    } else if (smartLampObj.lampNames.contains(name)) {
                        int indLamp = smartLampObj.lampNames.indexOf(name);
                        smartLampObj.smartLamps.get(indLamp).setSwitchStatus("Off");
                        devices.getDevicesList().get(index).giveReport(output,colorLampObj,smartLampObj,smartCameraObj,smartPlugObj,name);
                        devices.getDevicesList().remove(index);
                        devices.getDeviceNames().remove(index);
                        smartLampObj.smartLamps.remove(indLamp);
                        smartLampObj.lampNames.remove(name);
                    } else if (devices.getDevicesList().get(index) instanceof SmartColorLamp) {
                        int indClamp = colorLampObj.sColorNames.indexOf(name);
                        colorLampObj.smartColorLamps.get(indClamp).setSwitchStatus("Off");
                        devices.getDevicesList().get(index).giveReport(output,colorLampObj,smartLampObj,smartCameraObj,smartPlugObj,name);
                        devices.getDevicesList().remove(index);
                        devices.getDeviceNames().remove(index);
                        colorLampObj.smartColorLamps.remove(indClamp);
                        colorLampObj.sColorNames.remove(name);
                    }
                }
                break ;

            case "ZReport":
                if (argsList.size()==2){ioObj.writeToFile(output,"ZReport:",true,true);}
                else {ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);}
                ioObj.writeToFile(output,"Time is:\t"+timeController.getCurrentTime().toString().replace("T","_"),true,true);
                //devices.getSetSwitchTimeObjects().sort(Comparator.comparing(Devices::getSwitchTime));
                List<Devices> hasSwitchTime = new ArrayList<>();
                List<Devices> hasNull = new ArrayList<>();
                for (Devices device:devices.getDevicesList()) {
                    if(device.getSwitchTime()!=null){hasSwitchTime.add(device);}
                    else{hasNull.add(device);}
                }
                hasSwitchTime.sort(Comparator.comparing(Devices::getSwitchTime));
                // print z report
                for (Devices device: hasSwitchTime){
                    device.giveReport(output,colorLampObj,smartLampObj,smartCameraObj,smartPlugObj,device.getName());}
                for (Devices device: hasNull){
                    device.giveReport(output,colorLampObj,smartLampObj,smartCameraObj,smartPlugObj,device.getName());}
                break ;
            default:
                ioObj.writeToFile(output,"COMMAND: "+commandLine,true,true);
                ioObj.writeToFile(output, "ERROR: Erroneous command!", true, true);
        }
    }
}