public class User {
  public static void main(String args[]) {
    Project pj1 = new Project("Project_1");
    Task tas1 = new Task("Tasca_1");
    Task tas2 = new Task("Tasca_2");
    tas2.initTask();

    pj1.addComponent(tas2);
    pj1.getSubComponents();
    pj1.getInitialDate();
    pj1.updateDates();
  }
}
