# Student Course Registration System

This project implements a simple console based system that allows students to register for courses and an admin to manage those courses. It is written entirely in Java using common data structures like `ArrayList`, `HashMap` and `Queue` to demonstrate object oriented programming concepts.

## Features

* Students can create an account, view available courses, register or drop courses and see their current schedule.
* An admin can add or remove courses and view which students are enrolled or waitlisted for a course.
* Waitlists are managed automatically with a `Queue` so that when a seat opens the next student is enrolled.
* Courses can be listed sorted by course code or by title using a simple bubble sort implementation.

## Project Structure

```
src/registration
├── Admin.java          # Admin authentication
├── Course.java         # Course model with enrollment and waitlist logic
├── RegistrarSystem.java# Main class and command line interface
└── Student.java        # Student model
```

### Data Structures

* `HashMap` maps student IDs to `Student` objects and course codes to `Course` objects.
* Each `Course` uses an `ArrayList` for enrolled students and a `Queue` for the waitlist.
* Student schedules are stored in an `ArrayList` of courses.

## Compiling and Running

Use the JDK to compile all Java files from the project root:

```bash
javac src/registration/*.java
```

Then start the application:

```bash
java -cp src registration.RegistrarSystem
```

You will be presented with a menu where you can log in as a student or admin. The default admin credentials are:

* **Username:** `admin`
* **Password:** `password`

## Example Usage

```
--- Registrar System ---
1. Student Login
2. Admin Login
3. Exit
Select option: 1
Enter student ID: s100
Student not found. Enter name to create new account: Alice
Account created.

-- Student Menu --
1. View Available Courses
2. Register for Course
3. Drop Course
4. View Schedule
5. Logout
```

Courses and student data live only for the duration of the program, but the layout is designed to be extended with file saving/loading if desired.

## Optional Enhancements

A future improvement would be to persist course and student information using simple text files or a database. This example keeps everything in memory for clarity.
