import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Component {
  private String name;
  private Duration elapsedTime = Duration.ZERO;
  private LocalDateTime startDate;
  private LocalDateTime finalDate;
  private Project father;

  public Component(String name){
    this.name = name;
    this.father = null;
  }

  public Component(String name, Project father){
    this.name = name;
    this.father = father;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public LocalDateTime getFinalDate() {
    return finalDate;
  }

  public Duration getElapsedTime() {
    return elapsedTime;
  }

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

  abstract void accept(Visitor v);

  public void updateElapsedTime(LocalDateTime finalDate) {
    this.elapsedTime = getElapsedTime().plusSeconds(ClockTimer.getFreq());
    if (this.getFather() != null) {
      this.getFather().updateElapsedTime(finalDate);
    }
  }
}