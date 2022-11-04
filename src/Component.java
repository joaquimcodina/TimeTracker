import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Component(String name, Project father, Duration elapsedTime, LocalDateTime startDate, LocalDateTime finalDate){
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
    public LocalDateTime getFinalDate() { return finalDate; } //inalDate
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
    public LocalDateTime getActualDate(){
        return LocalDateTime.now();
    }

    public Duration getActualElapsedTime() {
        Duration duration = this.elapsedTime;
        if(this.getIntervals() == null)
            return Duration.ofSeconds(Duration.between(this.startDate, LocalDateTime.now()).toSeconds());

        if (getIntervals() != null){
            for (Interval inter : getIntervals()){
                if (inter.getActiu())
                    duration = duration.plus(inter.getActualElapsedTime());
            }
        }

        return Duration.ofSeconds(duration.toSeconds());
    }

    //This function updates the current elapsed time based from the sub-trees of a node.
    abstract void accept(Visitor v);

    public String getFatherName() {
        return this.father.getName();
    }
}