// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

// This class implements the Visitor Design Pattern.
// It is used to print the hierarchy of the Time Tracker.
public class PrinterTestA implements Visitor {

  public void visitProject(Project project) {
    System.out.println("Project " + project);
    for (Component component : project.getComponentList()) {
      component.accept(this);
    }
    if (project.getName().equals("root")) {
      System.out.println();
    }
  }

  public void visitTask(Task task) {
    System.out.println("Task " + task);
    if (task.getIntervals().size() != 0) {
      for (int pos = 0; pos < task.getIntervals().size(); pos++) {
        System.out.println("   " + task.getIntervalPos(pos));
      }
    }
  }

  public void visitInterval(Interval interval) {
    System.out.print("");
  }
}
