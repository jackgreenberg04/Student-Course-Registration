package registration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Entry point for the registration system. Handles all user interaction.
 */
public class RegistrarSystem {
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Course> courses = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private final Admin admin = new Admin("admin", "password");

    public static void main(String[] args) {
        new RegistrarSystem().start();
    }

    private void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Registrar System ---");
            System.out.println("1. Student Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Select option: ");
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    studentLogin();
                    break;
                case "2":
                    adminLogin();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Goodbye!");
    }

    private void studentLogin() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = students.get(id);
        if (student == null) {
            System.out.print("Student not found. Enter name to create new account: ");
            String name = scanner.nextLine();
            student = new Student(id, name);
            students.put(id, student);
            System.out.println("Account created.\n");
        }
        studentMenu(student);
    }

    private void studentMenu(Student s) {
        boolean done = false;
        while (!done) {
            System.out.println("\n-- Student Menu --");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View Schedule");
            System.out.println("5. Logout");
            System.out.print("Select option: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1":
                    listCourses();
                    break;
                case "2":
                    registerCourse(s);
                    break;
                case "3":
                    dropCourse(s);
                    break;
                case "4":
                    showSchedule(s);
                    break;
                case "5":
                    done = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void adminLogin() {
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        if (admin.authenticate(user, pass)) {
            adminMenu();
        } else {
            System.out.println("Authentication failed.");
        }
    }

    private void adminMenu() {
        boolean done = false;
        while (!done) {
            System.out.println("\n-- Admin Menu --");
            System.out.println("1. Add Course");
            System.out.println("2. Remove Course");
            System.out.println("3. View Course Roster");
            System.out.println("4. List Courses");
            System.out.println("5. Logout");
            System.out.print("Select option: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1":
                    addCourse();
                    break;
                case "2":
                    removeCourse();
                    break;
                case "3":
                    viewRoster();
                    break;
                case "4":
                    listCourses();
                    break;
                case "5":
                    done = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void listCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        List<Course> courseList = new ArrayList<>(courses.values());
        if (courseList.size() > 1) {
            System.out.print("Sort by (1) Code or (2) Title? ");
            String choice = scanner.nextLine();
            bubbleSort(courseList, "2".equals(choice) ? Comparator.comparing(Course::getTitle)
                    : Comparator.comparing(Course::getCourseId));
        }
        for (Course c : courseList) {
            System.out.println(c + " | Enrolled: " + c.getEnrolledStudents().size()
                    + "/" + c.getMaxCapacity() + " | Waitlist: " + c.getWaitlist().size());
        }
    }

    /**
     * Simple bubble sort implementation for learning purposes.
     */
    private void bubbleSort(List<Course> list, Comparator<Course> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Course c1 = list.get(j);
                Course c2 = list.get(j + 1);
                if (comparator.compare(c1, c2) > 0) {
                    list.set(j, c2);
                    list.set(j + 1, c1);
                }
            }
        }
    }

    private void registerCourse(Student s) {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        Course c = courses.get(code);
        if (c == null) {
            System.out.println("Course not found.");
            return;
        }
        if (s.getRegisteredCourses().contains(c)) {
            System.out.println("Already registered for this course.");
            return;
        }
        boolean enrolled = c.addStudent(s);
        if (enrolled) {
            s.addCourse(c);
            System.out.println("Successfully enrolled in " + c.getTitle());
        } else {
            System.out.println("Course full. Added to waitlist.");
        }
    }

    private void dropCourse(Student s) {
        System.out.print("Enter course code to drop: ");
        String code = scanner.nextLine();
        Course c = courses.get(code);
        if (c == null || !s.getRegisteredCourses().contains(c)) {
            System.out.println("You are not enrolled in that course.");
            return;
        }
        c.removeStudent(s);
        s.removeCourse(c);
        System.out.println("Dropped " + c.getTitle());
    }

    private void showSchedule(Student s) {
        List<Course> courses = s.getRegisteredCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    private void addCourse() {
        System.out.print("Course code: ");
        String code = scanner.nextLine();
        if (courses.containsKey(code)) {
            System.out.println("Course already exists.");
            return;
        }
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Instructor: ");
        String instructor = scanner.nextLine();
        System.out.print("Max capacity: ");
        int cap = readInt();
        Course c = new Course(code, title, instructor, cap);
        courses.put(code, c);
        System.out.println("Course added.");
    }

    private void removeCourse() {
        System.out.print("Enter course code to remove: ");
        String code = scanner.nextLine();
        Course c = courses.remove(code);
        if (c == null) {
            System.out.println("Course not found.");
            return;
        }
        // Remove course from all students
        for (Student s : new ArrayList<>(c.getEnrolledStudents())) {
            s.removeCourse(c);
        }
        System.out.println("Course removed.");
    }

    private void viewRoster() {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        Course c = courses.get(code);
        if (c == null) {
            System.out.println("Course not found.");
            return;
        }
        System.out.println("Enrolled students for " + c.getTitle() + ":");
        for (Student s : c.getEnrolledStudents()) {
            System.out.println(s);
        }
        if (!c.getWaitlist().isEmpty()) {
            System.out.println("Waitlist:");
            for (Student s : c.getWaitlist()) {
                System.out.println("* " + s);
            }
        }
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }
}
