# 🚀 Kaiburr Task 1 - Spring Boot Task Management API

A RESTful Spring Boot API to manage tasks and execute shell commands, with data persistence in MongoDB Atlas.

---

## 📌 Features

- ✅ Add a new task
- ✅ Get all tasks or a task by ID
- ✅ Search tasks by name
- ✅ Execute safe shell commands (e.g., `echo`)
- ✅ Store execution history
- ✅ Block unsafe commands (e.g., `rm`, `sudo`, `shutdown`)
- ✅ MongoDB Atlas integration

---

## 🛠️ Technologies Used

- Java 17 / 18
- Spring Boot 3.x
- MongoDB Atlas
- Maven
- Postman (for testing)
- VS Code

---

## 📂 Project Structure

com.kaiburr.taskapi
├── controller
│ └── TaskController.java
├── model
│ ├── Task.java
│ └── TaskExecution.java
├── repository
│ └── TaskRepository.java
├── service
│ └── TaskService.java
└── TaskapiApplication.java
-----------------------------------------------------

API test points

Get All Tasks
URL: http://localhost:8081/tasks

Get Task by ID
URL: http://localhost:8081/tasks?id=123


Search by Name
URL:http://localhost:8081/tasks/search?name=print

Delete a Task
URL:http://localhost:8081/tasks?id=123
