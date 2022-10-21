import java.time.LocalDateTime;

public class Task extends Component {
  private Interval interval;

  public Task(String name, Project father){
    super(name, father);
    father.addTask(this);
  }

  public void start(LocalDateTime now) {
    this.interval = new Interval(now, this);
    ClockTimer.getInstance().addObserver(this.interval); //Afegim l'observer
    // Comprobem si tenen una data d'inici existent per a no sobreescriure-la
    if (this.getStartDate() == null) {
      //this.setStartDate(LocalDateTime.now());
      this.setStartDate(now);
    }
    if (this.getFather().getStartDate() == null) {
      //this.getFather().setStartDate(LocalDateTime.now());
      this.getFather().setStartDate(now);
    }
  }

  //Aquest métode para la tasca quan "l'usuari" ho demana
  public void stop() {
    this.setFinalDate(LocalDateTime.now());
    this.getFather().setFinalDate(LocalDateTime.now());
    //this.interval.stopClock();
  }

  public void accept(Visitor v){
    v.visitTask(this);
  }

  public Interval getInterval() { return interval; }

  @Override
  public String toString() {
    return getName() + "          child of " + getFather().getName() + "      " +getStartDate() + "       " + getFinalDate() + "     " + getElapsedTime().getSeconds();
  }
}
