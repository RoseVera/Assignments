import java.time.LocalDateTime;

/**
 * This class controls current time and inital time.
 */
public class TimeController {
    private LocalDateTime initalTime;
    private LocalDateTime currentTime;

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

   public LocalDateTime getInitalTime() {return initalTime;}

    public void setInitalTime(LocalDateTime initalTime) {
        this.initalTime = initalTime;
    }
}
