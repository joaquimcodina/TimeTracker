import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class ClockTimer extends Observable {
  private Timer timer;
  private LocalDateTime now;
  private static final ClockTimer instance = new ClockTimer();
  private static final int freq = 2;

  private ClockTimer(){
    this.timer = new Timer();
    startClock();
  }
  public void startClock(){
    // Get current date time init
    TimerTask cycle_task = new TimerTask() {
      @Override
      public void run() {
        now = LocalDateTime.now();
        setChanged();
        notifyObservers(now);
      }
    };
    this.timer.scheduleAtFixedRate(cycle_task, 0, 1000 * freq);
  }
  public void stopClock(){
    this.timer.cancel();
  }
  public static ClockTimer getInstance() { return instance; }
  public static int getFreq() { return freq; }
  public LocalDateTime getNow() { return now; }
}