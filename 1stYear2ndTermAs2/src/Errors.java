import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * This class has methods that prevent possible errors and exceptions
 * Each of them is for one command and related exceptions with them
 * Also this class has method to create a LocalDateTime object
 */
public class Errors extends Exception {
    FileIO ioObj = new FileIO();

    /**
     * These methods for writing accurate messages for every error.
     * They are called by multiple methods below that checks errors.
     * @param commandLine  whole line as a string
     * @param out  txt file to be written
     */
    public void errorneousCommand(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: Erroneous command!", true, true);
    }

    public void noSkipCommand(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: There is nothing to skip!", true, true);
    }

    public void itIsONCommand(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: This device is already switched on!", true, true);
    }

    public void itIsOffCommand(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: This device is already switched off!", true, true);
    }

    public void timeFormatCommand(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: Time format is not correct!", true, true);
    }

    public void nopCommand(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: There is nothing to switch!", true, true);
    }

    public void occupiedName(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: There is already a smart device with same name!", true, true);
    }

    public void noDevice(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: There is not such a device!", true, true);
    }

    public void notSmartLamp(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: This device is not a smart lamp!", true, true);
    }

    public void notColorLamp(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: This device is not a smart color lamp!", true, true);
    }

    public void notPlug(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: This device is not a smart plug!", true, true);
    }

    public void kelvinRange(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: Kelvin value must be in range of 2000K-6500K!", true, true);
    }

    public void colorRange(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: Color code value must be in range of 0x0-0xFFFFFF!", true, true);
    }

    public void mgbRange(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: Megabyte value must be a positive number!", true, true);
    }

    public void amperRange(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: Ampere value must be a positive number!", true, true);
    }

    public void brightnessRange(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: Brightness must be in range of 0%-100%!", true, true);
    }

    public void reversedTime(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: Time cannot be reversed!", true, true);
    }
    public void initTimeFormat(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: Format of the initial date is wrong! Program is going to terminate!", true, true);
    }

    public void pluggedIn(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: There is already an item plugged in to that plug!", true, true);
    }

    public void pluggedOut(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: This plug has no item to plug out from that plug!", true, true);
    }

    public void bothSame(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: Both of the names are the same, nothing changed!", true, true);
    }

    public void thereIsName(String commandLine, String out) {
        ioObj.writeToFile(out, "COMMAND: " + commandLine, true, true);
        ioObj.writeToFile(out, "ERROR: There is already a smart device with same name!", true, true);
    }

    /**
     * These are the methods checks errors and exceptions for each command.
     *
     * @param out  txt file to be written
     * @param argsList  list of every word that split by tab for each line
     * @param commandLine  whole line as a string
     * @param devices  object of class Devices
     * @return boolean value if it returns true that means there is no error, otherwise command is not carried out.
     */
    public boolean addCommandError(String out, List<String> argsList, String commandLine, Devices devices) {
        String deviceType = argsList.get(1);
        switch (deviceType) {
            case "SmartLamp":
                int size = argsList.size();
                switch (size) {
                    case 3:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        break;
                    case 4:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        if (!(argsList.get(3).equals("On") || argsList.get(3).equals("Off"))) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        break;
                    case 6:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        if (!(argsList.get(3).equals("On") || argsList.get(3).equals("Off"))) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        try {
                            if (!((2000 <= Integer.parseInt(argsList.get(4))) && (Integer.parseInt(argsList.get(4)) <= 6500))) {
                                kelvinRange(commandLine, out);
                                return false;
                            }
                            if (!((0 <= Integer.parseInt(argsList.get(5))) && (Integer.parseInt(argsList.get(5)) <= 100))) {
                                brightnessRange(commandLine, out);
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        break;
                    default:
                        errorneousCommand(commandLine, out);
                        return false;
                }
                break;
            case "SmartColorLamp":
                int size2 = argsList.size();
                switch (size2) {
                    case 3:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        break;
                    case 4:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        if (!(argsList.get(3).equals("On") || argsList.get(3).equals("Off"))) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        break;
                    case 6:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        if (!(argsList.get(3).equals("On") || argsList.get(3).equals("Off"))) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        // check kelvin and color value
                        try {
                            if (argsList.get(4).contains("x")) {
                                String hex = argsList.get(4).replace("0x", "");
                                int value = Integer.parseInt(hex, 16);
                                if (!(0 <= value && value <= 16777215)) {
                                    colorRange(commandLine, out);
                                    return false;
                                }
                            } else {
                                if (!((2000 <= Integer.parseInt(argsList.get(4))) && (Integer.parseInt(argsList.get(4)) <= 6500))) {
                                    kelvinRange(commandLine, out);
                                    return false;
                                }
                            }
                        } catch (Exception e) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        // check brightness
                        try {
                            if (!((0 <= Integer.parseInt(argsList.get(5))) && (Integer.parseInt(argsList.get(5)) <= 100))) {
                                brightnessRange(commandLine, out);
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        break;
                    default:
                        errorneousCommand(commandLine, out);
                        return false;
                }
                break;

            case "SmartCamera":
                int size3 = argsList.size();
                switch (size3) {
                    case 4:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        try {
                            if (Integer.parseInt(argsList.get(3)) <= 0) {
                                mgbRange(commandLine, out);
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        break;
                    case 5:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        try {
                            if (Float.parseFloat(argsList.get(3)) <= 0) {
                                mgbRange(commandLine, out);
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        if (!(argsList.get(4).equals("On") || argsList.get(4).equals("Off"))) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        break;
                    default:
                        errorneousCommand(commandLine, out);
                        return false;
                }
                break;

            case "SmartPlug":
                int size4 = argsList.size();
                switch (size4) {
                    case 3:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        break;

                    case 4:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        if (!(argsList.get(3).equals("On") || argsList.get(3).equals("Off"))) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        break;

                    case 5:
                        if (devices.deviceNames.contains(argsList.get(2))) {
                            occupiedName(commandLine, out);
                            return false;
                        }
                        if (!(argsList.get(3).equals("On") || argsList.get(3).equals("Off"))) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        try {
                            if (Float.parseFloat(argsList.get(4)) <= 0) {
                                amperRange(commandLine, out);
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            errorneousCommand(commandLine, out);
                            return false;
                        }
                        break;

                    default:
                        errorneousCommand(commandLine, out);
                        return false;

                }
        }
        return true;
    }

    public boolean brightnessError(String out, List<String> argsList, String commandLine, Devices devices, List<String> lampNames, List<String> sColorNames) {
        if (argsList.size() != 3) {
            errorneousCommand(commandLine, out);
            return false;
        }
        if (!devices.deviceNames.contains(argsList.get(1))) {
            noDevice(commandLine, out);
            return false;
        }
        if (!(lampNames.contains(argsList.get(1)) || sColorNames.contains(argsList.get(1)))) {
            notSmartLamp(commandLine, out);
            return false;
        }
        try {
            if (!((0 <= Integer.parseInt(argsList.get(2))) && (Integer.parseInt(argsList.get(2)) <= 100))) {
                brightnessRange(commandLine, out);
                return false;
            }
        } catch (NumberFormatException e) {
            errorneousCommand(commandLine, out);
            return false;
        }
        return true;
    }

    public boolean kelvinError(String out, List<String> argsList, String commandLine, Devices devices, List<String> lampNames, List<String> sColorNames) {
        if (argsList.size() != 3) {
            errorneousCommand(commandLine, out);
            return false;
        }
        if (!devices.deviceNames.contains(argsList.get(1))) {
            noDevice(commandLine, out);
            return false;
        }
        if (!(lampNames.contains(argsList.get(1)) || sColorNames.contains(argsList.get(1)))) {
            notSmartLamp(commandLine, out);
            return false;
        }
        try {
            if (!((2000 <= Integer.parseInt(argsList.get(2))) && (Integer.parseInt(argsList.get(2)) <= 6500))) {
                kelvinRange(commandLine, out);
                return false;
            }
        } catch (NumberFormatException e) {
            errorneousCommand(commandLine, out);
            return false;
        }
        return true;
    }

    public boolean colorCodeError(String out, List<String> argsList, String commandLine, Devices devices, List<String> sColorNames) {
        if (argsList.size() != 3) {
            errorneousCommand(commandLine, out);
            return false;
        }
        if (!devices.deviceNames.contains(argsList.get(1))) {
            noDevice(commandLine, out);
            return false;
        }
        if (!sColorNames.contains(argsList.get(1))) {
            notColorLamp(commandLine, out);
            return false;
        }
        try {
            String hex = argsList.get(2).replace("0x", "");
            int value = Integer.parseInt(hex, 16);
            if (!(0 <= value && value <= 16777215)) {
                colorRange(commandLine, out);
                return false;
            }
        } catch (NumberFormatException e) {
            errorneousCommand(commandLine, out);
            return false;
        }
        return true;
    }

    public boolean whiteError(String out, List<String> argsList, String commandLine, Devices devices, List<String> lampNames, List<String> sColorNames) {
        if (argsList.size() != 4) {
            errorneousCommand(commandLine, out);
            return false;
        }
        if (!devices.deviceNames.contains(argsList.get(1))) {
            noDevice(commandLine, out);
            return false;
        }
        if (!(lampNames.contains(argsList.get(1)) || sColorNames.contains(argsList.get(1)))) {
            notSmartLamp(commandLine, out);
            return false;
        }
        try {
            if (!((2000 <= Integer.parseInt(argsList.get(2))) && (Integer.parseInt(argsList.get(2)) <= 6500))) {
                kelvinRange(commandLine, out);
                return false;
            }
        } catch (NumberFormatException e) {
            errorneousCommand(commandLine, out);
            return false;
        }
        try {
            if (!((0 <= Integer.parseInt(argsList.get(3))) && (Integer.parseInt(argsList.get(3)) <= 100))) {
                brightnessRange(commandLine, out);
                return false;
            }
        } catch (NumberFormatException e) {
            errorneousCommand(commandLine, out);
            return false;
        }
        return true;

    }

    public boolean colorError(String out, List<String> argsList, String commandLine, Devices devices, List<String> sColorNames) {
        if (argsList.size() != 4) {
            errorneousCommand(commandLine, out);
            return false;
        }
        if (!devices.deviceNames.contains(argsList.get(1))) {
            noDevice(commandLine, out);
            return false;
        }
        if (!sColorNames.contains(argsList.get(1))) {
            notColorLamp(commandLine, out);
            return false;
        }
        try {
            String hex = argsList.get(2).replace("0x", "");
            int value = Integer.parseInt(hex, 16);
            if (!(0 <= value && value <= 16777215)) {
                colorRange(commandLine, out);
                return false;
            }
        } catch (NumberFormatException e) {
            errorneousCommand(commandLine, out);
            return false;
        }
        try {
            if (!((0 <= Integer.parseInt(argsList.get(3))) && (Integer.parseInt(argsList.get(3)) <= 100))) {
                brightnessRange(commandLine, out);
                return false;
            }
        } catch (NumberFormatException e) {
            errorneousCommand(commandLine, out);
            return false;
        }
        return true;
    }

    public boolean setTimeError(String out, List<String> argsList, String commandLine, TimeController timeController) {
        if (argsList.size() != 2) {
            errorneousCommand(commandLine, out);
            return false;
        }
        try{
            LocalDateTime dateTime = createLocalDateTime(argsList.get(1));
            if (dateTime.isBefore(timeController.getCurrentTime())) {
                reversedTime(commandLine, out);
                return false;
            }
        }
        catch (Exception e){
            timeFormatCommand(commandLine,out);
            return false;
        }
            /*String localDate = argsList.get(1).replace("_", " ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(localDate, formatter);*/
        return true;
    }

    public boolean skipMinErrors(String out, List<String> argsList, String commandLine) {
        if (argsList.size() != 2) {
            errorneousCommand(commandLine, out);
            return false;
        }
        try {
            int minutes = Integer.parseInt(argsList.get(1));
            if ((minutes < 0)) {
                reversedTime(commandLine, out);
                return false;
            }
            if ((minutes == 0)) {
                noSkipCommand(commandLine, out);
                return false;
            }
        } catch (Exception e) {
            errorneousCommand(commandLine, out);
            return false;
        }
        return true;
    }

    public boolean plugInErrors(String out, List<String> argsList, String commandLine, Devices devices, List<String> plugNames, List<SmartPlug> smartPlugs) {
        if (argsList.size() != 3) {
            errorneousCommand(commandLine, out);
            return false;
        }
        if (!devices.deviceNames.contains(argsList.get(1))) {
            noDevice(commandLine, out);
            return false;
        }
        if (!plugNames.contains(argsList.get(1))) {
            notPlug(commandLine, out);
            return false;
        }
        int index = plugNames.indexOf(argsList.get(1));
        if (smartPlugs.get(index).getAmpere() != 0) {
            pluggedIn(commandLine, out);
            return false;
        }
        try {
            if (Float.parseFloat(argsList.get(2)) <= 0) {
                amperRange(commandLine, out);
                return false;
            }
        } catch (NumberFormatException e) {
            errorneousCommand(commandLine, out);
            return false;
        }
        return true;
    }

    public boolean plugOutErrors(String out, List<String> argsList, String commandLine, Devices devices, List<String> plugNames, List<SmartPlug> smartPlugs) {
        if (argsList.size() != 2) {
            errorneousCommand(commandLine, out);
            return false;
        }
        if (!devices.deviceNames.contains(argsList.get(1))) {
            noDevice(commandLine, out);
            return false;
        }
        if (!plugNames.contains(argsList.get(1))) {
            notPlug(commandLine, out);
            return false;
        }
        int index = plugNames.indexOf(argsList.get(1));
        if (smartPlugs.get(index).getAmpere() == 0.00) {
            pluggedOut(commandLine, out);
            return false;
        }
        return true;
    }

    public boolean changeNameError(String out, List<String> argsList, String commandLine, Devices devices) {
        if (argsList.size() != 3) {
            errorneousCommand(commandLine, out);
            return false;
        }
        if (argsList.get(1).equals(argsList.get(2))) {
            bothSame(commandLine, out);
            return false;
        }
        if (!devices.deviceNames.contains(argsList.get(1))) {
            noDevice(commandLine, out);
            return false;
        }
        if (devices.deviceNames.contains(argsList.get(2))) {
            thereIsName(commandLine, out);
            return false;
        }
        return true;
    }

    public boolean switchTimeError(String out, List<String> argsList, String commandLine, Devices devices, TimeController timeController) {
        if (argsList.size() != 3) {
            errorneousCommand(commandLine, out);
            return false;
        }
        if (!devices.deviceNames.contains(argsList.get(1))) {
            noDevice(commandLine, out);
            return false;
        }
        try {
            LocalDateTime dateTime = createLocalDateTime(argsList.get(2));
            if (dateTime.isBefore(timeController.getCurrentTime())) {
                reversedTime(commandLine, out);
                return false;
            }
        } catch (Exception e) {
            timeFormatCommand(commandLine, out);
            return false;
        }
        return true;
    }

    public boolean switchError(String out, List<String> argsList, String commandLine, Devices devices) {

        if (argsList.size() != 3) {errorneousCommand(commandLine, out);return false;}
        if (!devices.deviceNames.contains(argsList.get(1))) {noDevice(commandLine, out);return false;}
        if (!(argsList.get(2).equals("On") || argsList.get(2).equals("Off"))) {
            errorneousCommand(commandLine, out);return false;}
        int ind = devices.deviceNames.indexOf(argsList.get(1));
        if (argsList.get(2).equals(devices.getDevicesList().get(ind).getSwitchStatus())) {
            if (argsList.get(2).equals("On")) {
                itIsONCommand(commandLine, out);
                return false;
            }
            if (argsList.get(2).equals("Off")) {
                itIsOffCommand(commandLine, out);
                return false;
            }
        }
        return true;
    }

    public boolean removeError(String out, List<String> argsList, String commandLine, Devices devices) {
        if (argsList.size()!=2) {
            errorneousCommand(commandLine, out);return false;}
        if (!devices.deviceNames.contains(argsList.get(1))) {noDevice(commandLine, out);return false;}
        return true;
    }
    public boolean setInitalTimeError(String out, List<String> argsList, String commandLine) {
        if (argsList.size() != 2) {
            errorneousCommand(commandLine, out);
            return false;
        }
        try {
            String[] splitted = argsList.get(1).split("_");
            String [] yearMonthDay = splitted[0].split("-");
            int[] yearMonthDay2 = Arrays.stream(yearMonthDay).mapToInt(Integer::parseInt).toArray();
            String [] hourMinSec = splitted[1].split(":");
            int[] hourMinSec2 = Arrays.stream(hourMinSec).mapToInt(Integer::parseInt).toArray();
            LocalDateTime dateTime = LocalDateTime.of(yearMonthDay2[0],yearMonthDay2[1],yearMonthDay2[2],hourMinSec2[0],hourMinSec2[1],hourMinSec2[2]);
        } catch (Exception e) {
            initTimeFormat(commandLine, out);
            return false;
        }
        return true;
    }

    /**
     * This method takes a String that will be converted to LocalDateTime and convert it if there is no exceptions
     * @param timeString  String that given as LocalDateTime
     * @return  LocaldateTime dateTime
     */
    public LocalDateTime createLocalDateTime(String timeString){
        String[] splitted = timeString.split("_");
        String [] yearMonthDay = splitted[0].split("-");
        int[] intDate = Arrays.stream(yearMonthDay).mapToInt(Integer::parseInt).toArray();
        String [] hourMinSec = splitted[1].split(":");
        int[] intTime = Arrays.stream(hourMinSec).mapToInt(Integer::parseInt).toArray();
        return LocalDateTime.of(intDate[0],intDate[1],intDate[2],intTime[0],intTime[1],intTime[2]);
    }
}
