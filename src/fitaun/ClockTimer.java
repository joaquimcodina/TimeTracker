package fitaun;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class implements the Observable Design Pattern. It basically has a Clock that will be
// updating its observers every "freq" seconds.
public class ClockTimer extends Observable {
  private Timer timer;
  private List<Interval> observers = new LinkedList<>();
  private LocalDateTime now;
  private static Logger logger = LoggerFactory.getLogger("time.tracker.fita1");
  private static final ClockTimer instance = new ClockTimer();

  private ClockTimer() {
    this.timer = new Timer();
    startClock();
  }

  public void startClock() {
    // Get current date time init
    TimerTask cycleTask = new TimerTask() {
      @Override
      public void run() {
        now = LocalDateTime.now();
        setChanged();
        notifyObservers(now);

      }
    };
    this.timer.scheduleAtFixedRate(cycleTask, 0, (long) (2000.0));
  }

  public void stopClock() {
    this.timer.cancel();
  }

  public static ClockTimer getInstance() {
    return instance;
  }

  public LocalDateTime getNow() {
    return this.now;
  }

  @Override
  public synchronized void addObserver(Observer o) {
    assert o != null;
    logger.trace("ClockTimer has a new Observer added");
    super.addObserver(o);
  }

  public void addInterval(Interval inter) {
    assert inter != null;
    this.observers.add(inter);
  }

  public List<Interval> getObservers() {
    return this.observers;
  }

  @Override
  public void notifyObservers() {
    super.notifyAll();
  }

}