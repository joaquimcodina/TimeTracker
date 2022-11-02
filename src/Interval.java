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

  public Interval(Task father) {
    this.start = LocalDateTime.now();
    this.father = father;
  }

  public void update(Observable o, Object arg) {
    //this.end = ClockTimer.getInstance().getNow();
    //this.elapsedTime = Duration.ofSeconds(Duration.between(this.start,this.end).toSeconds());
  }

  public void update() {
    this.end = ClockTimer.getInstance().getNow();
    this.elapsedTime = Duration.ofSeconds(Duration.between(this.start, this.end).toSeconds());
  }
  //This method is called when an Interval is ended. It updates its information such as the elapsedTime and the endDate.

  public void stopClock() {
    ClockTimer.getInstance().deleteObserver(this);
  }

  public LocalDateTime getStart() {
    return this.start;
  }

  public LocalDateTime getEnd() {
    return this.end;
  }

  public Task getFather() {
    return father;
  }

  public Duration getElapsedTime() {
    return this.elapsedTime;
  }

  public void setEndDate(LocalDateTime end) {
    this.end = end;
  }

  @Override
  public String toString() {
    return "Interval         child of " + getFather().getName() + "      " + (getStart() == null ? "null" : getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) + "      "
        + (getEnd() == null ? "null" : getEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) + "      " + getElapsedTime().getSeconds();
  }
  //This method is used to print the information of an Interval.
}
