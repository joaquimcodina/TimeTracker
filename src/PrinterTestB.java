import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

/*
    This class implements the Visitor Design Pattern and the Observer Design Pattern.
     It is used to print the hierarchy of the Time Tracker and its changes.

    @version 2.0
    @since 2022-11-06
 */
public class PrinterTestB implements Visitor, Observer {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public PrinterTestB() {

  }

  @Override
  public void visitProject(Project project) {

    System.out.println("activity: \t\t" + project.getName() + "\t\t\t\t"
        + project.getStartDate().format(formatter)
        + "\t\t\t" + project.getActualDate().format(formatter) + "\t\t\t"
        + project.getActualElapsedTime().toSeconds());

    if (project.getFather() != null) {
      project.getFather().accept(this);
    } else {
      System.out.println();
    }
  }

  @Override
  public void visitTask(Task task) {
    System.out.println("activity: \t\t" + task.getName()
        + "\t\t\t" + task.getStartDate().format(formatter)
        + "\t\t\t" + task.getActualDate().format(formatter)
        + "\t\t\t" + task.getActualElapsedTime().toSeconds());
    if (task.getFather() != null) {
      task.getFather().accept(this);
    }
  }

  @Override
  public void visitInterval(Interval interval) {
    if (interval.getFather().isStopped()) {
      System.out.print("");
    }

    System.out.println("interval:  \t\t\t\t\t\t\t" + interval.getStart().format(formatter)
        + "\t\t\t" + interval.getActualDate().format(formatter) + "\t\t\t\t"
        + interval.getActualElapsedTime().toSeconds());

    interval.getFather().accept(this);
  }

  @Override
  public void update(Observable o, Object arg) { //print latest active interval
    Interval interval = ClockTimer.getInstance().getObservers()
        .get(ClockTimer.getInstance().getObservers().size() - 1);
    interval.accept(this);
  }

}
