import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;

import java.time.LocalDateTime;
import java.util.*;

/*
    This class implements the Observable Design Pattern. It basically has a Clock that will be
    updating its observers every "freq" seconds.

    @version 5.0
    @since 2022-11-06
 */

public class ClockTimer extends Observable {
  private Timer timer;
  private List<Interval> observers = new LinkedList<Interval>();
  private LocalDateTime now;
  private static final ClockTimer instance = new ClockTimer();
  private static final double freq = 2.75;
  private ClockTimer(){
    this.timer = new Timer();
    startClock();
  }

  /*
This method starts the clock of the ClockTimer's class.
*/
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
    this.timer.scheduleAtFixedRate(cycle_task, 0, (long) (2000.0));
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