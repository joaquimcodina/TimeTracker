import java.time.Duration;
import java.time.LocalDateTime;

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

  public String getName() {
    return name;
  }
  public LocalDateTime getStartDate() {
    return startDate;
  }
  public LocalDateTime getFinalDate() { return finalDate; }
  public Duration getElapsedTime() { return elapsedTime; }
  public Project getFather() {
    return father;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
    if (father != null && father.getStartDate() == null) {
      father.setStartDate(startDate);
    }
  }

  public void setFinalDate(LocalDateTime finalDate) {
    this.finalDate = finalDate;
    if (father != null) {
      father.setFinalDate(finalDate);
    }
  }

  public void updateElapsedTime(LocalDateTime finalDate) {
    this.elapsedTime = Duration.ofSeconds(Duration.between(this.startDate,finalDate).toSeconds());
    Component actual = this;
    while (actual.getFather() != null) {
      actual.getFather().updateElapsedTime(finalDate);
      actual = actual.getFather();
    }
    actual.updateDates();
  }
  abstract void accept(Visitor v);

  protected void updateInitialDate(LocalDateTime dt) {
    this.startDate = dt;
  }
  protected void updateFinalDate(LocalDateTime dt) {
    this.finalDate = dt;
  }

  abstract void updateDates();

  protected void notifyFather() {
    if (this.father != null) {
      if (this.startDate != null) {
        if (this.father.getStartDate() == null || this.startDate.compareTo(this.father.getStartDate()) < 0)
          this.father.updateInitialDate(this.startDate);
      }
      if (this.finalDate != null) {
        if (this.father.getFinalDate() == null || this.finalDate.compareTo(this.father.getFinalDate()) > 0)
          this.father.updateFinalDate(this.finalDate);
      }
      this.father.notifyFather();
    }
  }
  /*This function updates its father's:
   *   - initialDate: if father's initialDate is newer than object's one.
   *   - finalDate: if father's finalDate is older that object's one.
   * Duration attribute cannot be updated with this function. It is updated manually in <<updateDates>>
   * */

}