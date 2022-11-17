import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
    This class is a superclass. It has the following attributes:
        · startDate : LocalDateTime
        · finalDate : LocalDateTime
        · name : String
        · elapsedTime : Duration
        · father : Project

    @version 3.0
    @since 2022-11-06
 */
public abstract class Component {
  private final String name;
  protected Duration elapsedTime;
  private LocalDateTime startDate;
  private LocalDateTime finalDate;
  private final Project father;

  public Component(String name) {
    this.name = name;
    this.father = null;
    this.elapsedTime = Duration.ZERO;
    this.startDate = null;
    this.finalDate = null;
  }

  public Component(String name, Project father) {
    this.name = name;
    this.father = father;
    this.elapsedTime = Duration.ZERO;
    this.startDate = null;
    this.finalDate = null;
  }

  /*
This Constructor is used by the ReadJSON.java class in order to rebuild the hierarchy.
It will be not be able to the Users.
It basically initializes every single attribute a Component has.
@return Component
*/
  public Component(String name, Project father, Duration elapsedTime,
                   LocalDateTime startDate, LocalDateTime finalDate) {
    this.name = name;
    this.father = father;
    this.elapsedTime = elapsedTime;
    this.startDate = startDate;
    this.finalDate = finalDate;
  }

  public abstract ArrayList<Component> getComponentList();

  public abstract List<Interval> getIntervals();

  public String getName() {
    return name;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  //initialDate
  public LocalDateTime getFinalDate() {
    return finalDate;
  }

  public Duration getElapsedTime() {
    return elapsedTime;
  }

  public Project getFather() {
    return this.father;
  }

  protected void setElapsedTime(Duration elapsedTime) {
    this.elapsedTime = elapsedTime;
  }

  protected void sumElapsedTime(Duration elapsedTime) {
    this.elapsedTime = this.elapsedTime.plus(elapsedTime);
  }

  /*
This function updates its own startDate and expands recursively the inner changes to the
upper nodes in the hierarchy, if exists.
*/
  public void setStartDate(LocalDateTime startDate) {
    if (this.getStartDate() == null) {
      this.startDate = startDate;
    }
    if (this.father != null) {
      father.setStartDate(startDate);
    }
  }

  /*
This function updates its own finalDate and expands recursively the inner changes to the
upper nodes in the hierarchy, if exists.
*/
  public void setFinalDate(LocalDateTime finalDate) {
    if (this.finalDate == null || this.finalDate.compareTo(finalDate) < 0) {
      this.finalDate = finalDate;
    }
    if (father != null) {
      father.setFinalDate(finalDate);
    }
  }

  public LocalDateTime getActualDate() {
    return LocalDateTime.now();
  }

  public Duration getActualElapsedTime() {
    Duration duration = this.elapsedTime;

    if (this.getIntervals() == null) {
      return Duration.ofSeconds(Duration.between(this.startDate, LocalDateTime.now()).toSeconds());
    }

    if (getIntervals() != null) {
      for (Interval inter : getIntervals()) {
        if (inter.getActive()) {
          duration = duration.plus(inter.getActualElapsedTime());
        }
      }
    }

    return Duration.ofSeconds(duration.toSeconds());
  }

  abstract void accept(Visitor v);

  public String getFatherName() {
    assert this.father != null;
    return this.father.getName();
  }
}