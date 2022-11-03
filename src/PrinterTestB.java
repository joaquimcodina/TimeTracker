import java.util.Observable;
import java.util.Observer;

public class PrinterTestB implements Visitor, Observer {

    private Interval interval;
    private Task task;
    private Project project;

    public PrinterTestB(Interval interval) {
        this.interval = interval;
    }

    @Override
    public void visitProject(Project project) {
        System.out.println("activity: \t" + project.getName() +"\t" + project.getStartDate()+ "\t" + project.getFinalDate() + "\t" + project.elapsedTime.toSeconds());
        if (project.getFather() != null)
            project.getFather().accept(this);
    }

    @Override
    public void visitTask(Task task) {
        System.out.println("activity: \t" + task.getName() +"\t" + task.getStartDate()+ "\t" + task.getFinalDate() + "\t" + task.elapsedTime.toSeconds());
        if (task.getFather() != null)
            task.getFather().accept(this);
    }

    @Override
    public void visitInterval(Interval interval){
        System.out.println("interval:  \t" + interval.getStart()+ "\t" + interval.getEnd() + "\t" + interval.getElapsedTime().toSeconds());
        interval.getFather().accept(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        interval.accept(this);
    }
}
