import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Component {
  private String name;
  protected Duration elapsedTime;
  private LocalDateTime startDate;
  private LocalDateTime finalDate;
  private Project father;

  public Component(String name){
    this.name = name;
    this.father = null;
    this.elapsedTime = Duration.ZERO;
    this.startDate = null;
    this.finalDate = null;
  }

  public Component(String name, Project father){
    this.name = name;
    this.father = father;
    this.elapsedTime = Duration.ZERO;
    this.startDate = null;
    this.finalDate = null;
  }

  public abstract List<Component> getComponentList();
  public abstract List<Interval> getIntervals();

  public String getName() {
    return name;
  }
  public LocalDateTime getStartDate() {
    return startDate;
  }
  public LocalDateTime getFinalDate() { return finalDate; }
  public Duration getElapsedTime() { return elapsedTime; }
  public Project getFather() {
    return this.father;
  }

  protected void setElapsedTime(Duration elapsedTime) {
    this.elapsedTime = elapsedTime;
  }

  protected void sumElapsedTime(Duration elapsedTime) {
    this.elapsedTime = this.elapsedTime.plus(elapsedTime);
  }

  public void setStartDate(LocalDateTime startDate) {
    if (this.getStartDate() == null)
      this.startDate = startDate;
    if (this.father != null)
      father.setStartDate(startDate);
  }
  //This method updates the startDate of a component recursively.

  public void setFinalDate(LocalDateTime finalDate) {
    if (this.finalDate == null || this.finalDate.compareTo(finalDate) < 0)
      this.finalDate = finalDate;
    if (father != null)
      father.setFinalDate(finalDate);
  }
  //This method updates the finalDate of a component recursively.

  abstract void accept(Visitor v);

  protected void updateInitialDate(LocalDateTime dt) {
    this.startDate = dt;
  }
  protected void updateFinalDate(LocalDateTime dt) {
    this.finalDate = dt;
  }

}
