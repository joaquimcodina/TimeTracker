package fitaun;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

// This class implements the fitaun.Visitor (fitaun.Visitor.java) class,
// which implements the fitaun.Visitor Design Pattern.
// It is used to create a file and save the Time Tracker
// application's data into a .JSON file, which
// will be called "./data/data.json".
public class SaveJson implements Visitor {
  private JSONArray jsonArray = new JSONArray();
  static Logger logger = LoggerFactory.getLogger("time.tracker.fita1");

  // This constructor directly initializes the saving process
  // of the application into a .JSON file by calling other methods of this class.
  public SaveJson(Component root) throws IOException {
    JSONObject obj;
    FileWriter fileData;
    logger.info("Saving data in a .json file...");
    String path = "data/data.json";
    File file = new File(path);
    fileData = new FileWriter(file, false);
    if (root != null) {
      root.accept(this); // recursive method
    }
    try {
      logger.debug("Creating JSON object");
      obj = new JSONObject();
      logger.debug("Putting information into JSON object");
      obj.put("components", jsonArray);
      logger.debug("Writing JSON object into datafile");
      fileData.write(obj.toString());
      logger.debug("Closing datafile");
      fileData.close();

    } catch (IOException err) {
      logger.warn("Error saving the .json file");
      throw new RuntimeException(err);
    }
    logger.info(".json file successfully created!");
  }

  @Override
  public void visitProject(Project var1) {
    inputData(var1);
    for (Component component : var1.getComponentList()) {
      component.accept(this);
    }
  }

  // This method puts the information of a hierarchy's node into a JSON object, which is put into a
  // JSON array, which will be written into the file.
  private void inputData(Component var1) {
    JSONObject obj = new JSONObject();
    obj.put("name", var1.getName());
    if (var1.getFather() == null) {
      obj.put("father", "null");
    } else {
      obj.put("father", var1.getFatherName());
    }
    obj.put("type", var1.getClass().getName());

    if (var1.getStartDate() == null) {
      obj.put("start_date", "null");
    } else {
      obj.put("start_date", var1.getStartDate());
    }
    
    if (var1.getFinalDate() == null) {
      obj.put("final_date", "null");
    } else {
      obj.put("final_date", var1.getActualDate());
    }

    obj.put("elapsed_time", var1.getElapsedTime());

    this.jsonArray.put(obj);
  }

  // This method saves in a JSON Object the information to be written in a .json file.
  @Override
  public void visitTask(Task var1) {
    inputData(var1);
    for (Interval interval : var1.getIntervals()) {
      interval.accept(this);
    }
  }

  public void visitInterval(Interval interval) {
    JSONObject obj = new JSONObject();
    obj.put("father", interval.getFather().getName());
    if (interval.getStart() == null) {
      obj.put("start_date", "null");
    } else {
      obj.put("start_date", interval.getStart());
    }
    
    if (interval.getEnd() == null) {
      obj.put("final_date", "null");
    } else {
      obj.put("final_date", interval.getEnd());
    }
    obj.put("elapsed_time", interval.getElapsedTime());
    obj.put("type", interval.getClass().getName());
    this.jsonArray.put(obj);
  }
}