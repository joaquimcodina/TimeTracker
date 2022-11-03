import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class PrinterTestB implements Visitor , Observer{

    private Interval interval;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /*public PrinterTestB(Interval interval) {
        this.interval = interval;
    }*/

    public PrinterTestB(){

    }

    @Override
    public void visitProject(Project project) {
        System.out.println("activity: \t\t" + project.getName() +"\t\t\t\t" + project.getStartDate().format(formatter)+
                "\t\t\t" + project.getActualDate().format(formatter) + "\t\t" + project.getActualElapsedTime().toSeconds());
        if (project.getFather() != null)
            project.getFather().accept(this);
        else
            System.out.println();
    }

    @Override
    public void visitTask(Task task) {
        System.out.println("activity: \t\t" + task.getName() +"\t\t\t" + task.getStartDate().format(formatter)+
                "\t\t\t" + task.getActualDate().format(formatter) + "\t\t" + task.getActualElapsedTime().toSeconds());
        if (task.getFather() != null)
            task.getFather().accept(this);
    }

    @Override
    public void visitInterval(Interval interval){
        if(interval.getEnd() == null)
            System.out.println("interval:  \t\t\t\t\t\t\t" + interval.getStart().format(formatter)+
                    "\t\t\t" + interval.getActualDate().format(formatter) + "\t\t" + interval.getActualElapsedTime().toSeconds());
        else
            System.out.println("interval:  \t\t\t\t\t\t\t" + interval.getStart().format(formatter)+
                    "\t\t\t" + interval.getActualDate().format(formatter) + "\t\t" + interval.getActualElapsedTime().toSeconds());
        interval.getFather().accept(this);
    }

    @Override
    public void update(Observable o, Object arg) { //IMPRIMIR ULTIM INTERVAL ACTIU
        interval = ClockTimer.getInstance().getObservers().get(ClockTimer.getInstance().getObservers().size()-1);
        interval.accept(this);
    }

}
