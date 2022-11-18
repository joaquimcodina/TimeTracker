//============================================================
// Class Visitor.java
//============================================================

/*
    This superclass Implements the Visitor Design Pattern. It has three uses in this project:
        1. PrinterTestA.
        2. PrinterTestB.
        3. SaveJSON.

    @version 2.0
    @since 2022-11-06
*/
public interface Visitor {
  void visitProject(Project var1);

  void visitTask(Task var1);

  void visitInterval(Interval interval);

}
