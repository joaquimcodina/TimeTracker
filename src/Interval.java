import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
  private LocalDateTime start;
  private LocalDateTime end;
  private Task father;
  private Duration elapsedTime = Duration.ZERO;
  public Interval(LocalDateTime now, Task father) {
    this.start = now;
    this.father = father;
  }
  public void update(Observable o, Object arg) {
    this.end = ClockTimer.getInstance().getNow();
    this.elapsedTime = Duration.ofSeconds(Duration.between(start,end).toSeconds());
    this.father.updateElapsedTime(getEnd());
    //this.father.updateElapsedTime(getElapsedTime());
  }

  public void stopClock() { ClockTimer.getInstance().deleteObserver(this); }
  public LocalDateTime getStart(){ return this.start; }
  public LocalDateTime getEnd(){ return this.end; }
  public Task getFather() {
    return father;
  }
  public Duration getElapsedTime(){ return this.elapsedTime; }

  @Override
  public String toString() {
    return "Interval         child of " + getFather().getName() + "      " +getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "       " + getEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "       " + getElapsedTime().getSeconds();
  }
}