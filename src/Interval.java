import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
    private LocalDateTime start;
    private LocalDateTime end;
    private Task father;
    private Duration elapsedTime = Duration.ZERO;
    private boolean actiu = true;

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

    public void update(Observable o, Object arg) {
        //this.end = ClockTimer.getInstance().getNow();
        this.elapsedTime = Duration.ofSeconds(Duration.between(this.start, ClockTimer.getInstance().getNow()).toSeconds());
        //update();
    }

    public boolean getActiu(){
        return actiu;
    }

    public void setNotActive(){
        this.actiu = false;
    }


    public void update() {
        //this.end = ClockTimer.getInstance().getNow();
        this.father.updateDates();
    }
    //This method is called when an Interval is ended. It updates its information such as the elapsedTime and the endDate.

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

    public LocalDateTime getActualDate(){
        return LocalDateTime.now();
    }

    public Duration getActualElapsedTime(){
        return Duration.ofSeconds(Duration.between(this.start, LocalDateTime.now()).toSeconds());
    }

    public void accept(Visitor v) {
        v.visitInterval(this);
    }

    @Override
    public String toString() {
        return "Interval \t\t\t\t\t child of " + getFather().getName() + "\t\t\t" + getStart() + "\t\t\t"
                + getEnd() + "\t\t\t" + getElapsedTime().getSeconds();
    }
    //This method is used to print the information of an Interval.
}