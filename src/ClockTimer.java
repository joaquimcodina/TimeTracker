import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;

import java.time.LocalDateTime;
import java.util.*;

public class ClockTimer extends Observable {
  private Timer timer;

  private List<Interval>observers = new LinkedList<Interval>();
  private LocalDateTime now;
  private static final ClockTimer instance = new ClockTimer();
  private static final double freq = 2.5;
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
    this.timer.scheduleAtFixedRate(cycle_task, (long) 0.0, (long) (1000.0 * freq));
  }
  public void stopClock(){
    this.timer.cancel();
  }
  public static ClockTimer getInstance() { return instance; }
  public static double getFreq() { return freq; }
  public LocalDateTime getNow() { return now; }

  @Override
  public synchronized void addObserver(Observer o) {
    super.addObserver(o);
  }

  public void addInterval(Interval inter){
    observers.add(inter);
  }
  public List<Interval> getObservers(){
    return observers;
  }
  @Override
  public void notifyObservers() {
    super.notifyObservers();
  }



}