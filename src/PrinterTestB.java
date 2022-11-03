import java.util.Observable;
import java.util.Observer;

public class PrinterTestB implements Visitor , Observer{

    private Interval interval;

    /*public PrinterTestB(Interval interval) {
        this.interval = interval;
    }*/

    public PrinterTestB(){

    }

    @Override
    public void visitProject(Project project) {
        System.out.println("activity: \t\t\t" + project.getName() +"\t" + project.getStartDate()+ "\t" + project.getActualDate() + "\t" + project.getActualElapsedTime().toSeconds());
        if (project.getFather() != null)
            project.getFather().accept(this);
    }

    @Override
    public void visitTask(Task task) {
        System.out.println("activity: \t\t\t" + task.getName() +"\t" + task.getStartDate()+ "\t" + task.getActualDate() + "\t" + task.getActualElapsedTime().toSeconds());
        if (task.getFather() != null)
            task.getFather().accept(this);
    }

    @Override
    public void visitInterval(Interval interval){
        System.out.println("interval:  \t\t\t" + interval.getStart()+ "\t" + interval.getEnd() + "\t" + interval.getElapsedTime().toSeconds());
        interval.getFather().accept(this);
    }

    @Override
    public void update(Observable o, Object arg) { //IMPRIMIR ULTIM INTERVAL ACTIU
        interval = ClockTimer.getInstance().getObservers().get(ClockTimer.getInstance().getObservers().size()-1);
        interval.accept(this);
    }

}
