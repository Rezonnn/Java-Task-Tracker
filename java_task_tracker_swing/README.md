# 📝 Task Tracker – Java Swing Desktop App

A small **Java desktop application** built with **Swing** that lets you manage a simple task list with persistence to a local file.

This is great for your GitHub as a **Java desktop GUI project**, demonstrating:

- Java + Swing UI
- Custom model class (`Task`)
- File I/O with `java.nio.file`
- A clean, single-purpose desktop app

---

## ✨ Features

- Add new tasks
- See tasks in a scrollable list
- Mark tasks as **done / not done**
- Delete a selected task
- Clear all completed tasks
- All tasks are saved to a local `tasks.txt` file (simple text serialization)

Completed tasks are shown in **gray**, pending tasks in **blue**.

---

## 🧱 Tech Stack

- **Language**: Java
- **UI Toolkit**: Swing
- **Build Tool**: Maven
- **Requires**: Java 17+ (configurable in `pom.xml`)

---

## ▶️ How to Run

### 1. Ensure Java & Maven are installed

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -v
```

---

### 2. Build the project

From the project root:

```bash
mvn package
```

This will create a JAR in:

```text
target/task-tracker-swing-1.0-SNAPSHOT.jar
```

---

### 3. Run the desktop app

```bash
java -jar target/task-tracker-swing-1.0-SNAPSHOT.jar
```

A window titled **"Task Tracker – Java Swing"** will appear.

You can then:

- Type a task name into the text field
- Click **Add**
- Select tasks and:
  - Use **Toggle Done**
  - Use **Delete**
  - Use **Clear Completed**

Data is stored in a file called `tasks.txt` in the working directory.

---

## 📂 Project Structure

```text
java_task_tracker_swing/
├─ pom.xml
└─ src/
   └─ main/
      └─ java/
         └─ com/
            └─ example/
               └─ tasktracker/
                  ├─ Task.java
                  ├─ TaskRepository.java
                  └─ TaskTrackerApp.java
```

---

## 🌟 Why this is a good portfolio project

This project shows that you can:

- Build a **desktop GUI** in Java
- Structure code into models, a repository, and a UI layer
- Use **Swing** components: `JFrame`, `JList`, `JTextField`, `JButton`, `JScrollPane`, `JLabel`
- Handle basic persistence with **file I/O** and simple serialization

You can extend it later with:

- Editing task titles
- Due dates and priorities
- Sorting and filtering
- Export/import to JSON
