import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

/*
    This class Implements the Observer Design Pattern and, it is connected to the ClockTimer.

    It basically has a start and end LocalDateTime attributes, which will be expanded above
    in the hierarchy every update, and an elapsedTime attribute of type Duration which
    will be expanded too.

    @version 3.0
    @since 2022-11-06
 */
public class Interval implements Observer {
  private final LocalDateTime start;
  private LocalDateTime end;
  private final Task father;
  private Duration elapsedTime = Duration.ZERO;
  private boolean active = true;

  /*
This Constructor initializes the initialDate of the Interval to the actual System Date.

@param Father : A Task. It will be used to expand the updates.

@return Interval
*/
  public Interval(Task father) {
    this.start = LocalDateTime.now();
    this.father = father;
  }

  public Interval(LocalDateTime start, LocalDateTime end, Task father, Duration elapsedTime) {
    this.start = start;
    this.end = end;
    this.father = father;
    this.elapsedTime = elapsedTime;
    father.addInterval(this);
  }

  /*
This method updates the elapsedTime every time it receives an update of Observable.
*/
  public void update(Observable o, Object arg) {
    this.elapsedTime = Duration.ofSeconds(
        Duration.between(this.start, ClockTimer.getInstance().getNow()).toSeconds());
  }

  public boolean getActive() {
    return active;
  }

  public void setNotActive() {
    this.active = false;
  }

  /*
This method expands the inner changes into the father Task, and then,
if exists, recursively into the upper nodes.
*/
  public void update() {
    this.father.updateDates();
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

  public LocalDateTime getActualDate() {
    return LocalDateTime.now();
  }

  public Duration getActualElapsedTime() {
    return Duration.ofSeconds(Duration.between(this.start, LocalDateTime.now()).toSeconds());
  }

  public void accept(Visitor v) {
    v.visitInterval(this);
  }

  /*
 This method is used to print the information of an Interval into console.
*/
  @Override
  public String toString() {
    return "Interval \t\t\t\t\t child of " + getFather().getName() + "\t\t\t"
        + getStart() + "\t\t\t" + getEnd() + "\t\t\t" + getElapsedTime().getSeconds();
  }
}