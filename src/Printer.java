//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Iterator;

public class Printer implements Visitor {
    private Project project;

    public Printer(Project project) {
        this.project = project;
    }

    public void visitProject(Project project) {
        System.out.println("Project " + project);
        Iterator var2 = project.getComponentList().iterator();

        while(var2.hasNext()) {
            Component component = (Component)var2.next();
            component.accept(this);
        }

    }

    public void visitTask(Task task) {
        System.out.println("Task " + task);
        if (task.getIntervals() != null) {
            System.out.println(task.getIntervals());
        }

    }

    public void visitInterval(Interval interval) {
        if (interval != null) {
            System.out.println("Interval " + interval);
            System.out.println(interval);
        }
    }
}
