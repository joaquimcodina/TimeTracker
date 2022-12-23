package fitaun;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Copyright (C) 2003, 2004, 2005 by Object Mentor, Inc. All
// rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.

//This class reads a .JSON file, parses its information and saves it into the
//fitaun.Project in order to recover the data and maintain the persistence.
public class ReadJson {
  private List<Component> hierarchy;
  static Logger logger = LoggerFactory.getLogger("time.tracker.fita1");

  public ReadJson() throws FileNotFoundException {
    // If you want to get the first element of the hierarchy, in order to use it for anything
    // (like printing it into console):
    // - ReadJSON hierarchy = new ReadJSON();
    // - fitaun.Component comp = hierarchy.getRoot();

    logger.trace("New ReadJSON instance created");
    String path = "./data/data.json";
    logger.info("Loading json file: from " + path);
    InputStream is;
    is = new FileInputStream(path);
    JSONTokener token = new JSONTokener(is);
    JSONObject object = new JSONObject(token);
    JSONArray components = object.getJSONArray("components");
    List<Component> componentList = new LinkedList<>();

    for (int i = 0; i < components.length(); i++) {
      JSONObject jsonObject1 = components.getJSONObject(i);
      this.hierarchy = createObject(jsonObject1, componentList);
    }
    logger.info("File " + path + " successfully loaded");

  }

  public Component getRoot() {
    return hierarchy.get(0);
  }

  //This method creates a certain object depending on the parsed information of the .JSON file.
  @SuppressWarnings("checkstyle:Indentation")
  private List<Component> createObject(JSONObject component, List<Component> createdObjects) {
    LocalDateTime startDate = null;
    LocalDateTime finalDate = null;
    String type = component.getString("type");
    String fatherName = component.getString("father");

    if (!component.getString("start_date").equals("null")) {
      startDate = LocalDateTime.parse(component.getString("start_date"));
    }
    if (!component.getString("final_date").equals("null")) {
      finalDate = LocalDateTime.parse(component.getString("final_date"));
    }
    Duration elapsedTime = Duration.parse(component.getString("elapsed_time"));

    if (type.equals("fitaun.Interval")) {
      new Interval(12, startDate, finalDate,
        (Task) Objects.requireNonNull(searchFatherByName(fatherName,
          createdObjects)), elapsedTime);
    } else {
      String name = component.getString("name");

      if (type.equals("fitaun.Project")) {
        Project project = new Project(13, name,
          (Project) searchFatherByName(fatherName, createdObjects), elapsedTime, startDate, finalDate);
        createdObjects.add(project);
      } else {
        Task task = new Task(14, name,
          (Project) searchFatherByName(fatherName, createdObjects),
          elapsedTime, startDate, finalDate);
        createdObjects.add(task);
      }
    }

    //It returns the createdObject list plus the new created Object, if created.
    return createdObjects;
  }


  // This method searches the componentList parameter for the name of a fitaun.Component,
  // starting from its parent fitaun.Component.
  private Object searchFatherByName(String name, List<Component> componentList) {
    for (Component component : componentList) {
      String nameVar = component.getName();
      if (nameVar.equals(name)) {
        return component;
      }
    }
    return null;
  }
}