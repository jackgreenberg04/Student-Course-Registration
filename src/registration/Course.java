package registration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Represents a course offered by the college.
 */
public class Course {
    private final String courseId;
    private String title;
    private String instructor;
    private int maxCapacity;
    private final List<Student> enrolledStudents;
    private final Queue<Student> waitlist;

    public Course(String courseId, String title, String instructor, int maxCapacity) {
        this.courseId = courseId;
        this.title = title;
        this.instructor = instructor;
        this.maxCapacity = maxCapacity;
        this.enrolledStudents = new ArrayList<>();
        this.waitlist = new LinkedList<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        // Adjust waitlist if necessary when capacity increases
        while (enrolledStudents.size() < maxCapacity && !waitlist.isEmpty()) {
            Student s = waitlist.poll();
            enrolledStudents.add(s);
            s.addCourse(this);
        }
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Queue<Student> getWaitlist() {
        return waitlist;
    }

    /**
     * Attempts to register the student for the course.
     * @return true if the student was enrolled, false if added to waitlist.
     */
    public boolean addStudent(Student s) {
        if (enrolledStudents.contains(s)) {
            return true;
        }
        if (enrolledStudents.size() < maxCapacity) {
            enrolledStudents.add(s);
            return true;
        }
        waitlist.offer(s);
        return false;
    }

    /**
     * Removes a student from the course and promotes next waitlisted student.
     */
    public void removeStudent(Student s) {
        if (enrolledStudents.remove(s)) {
            if (!waitlist.isEmpty()) {
                Student promoted = waitlist.poll();
                enrolledStudents.add(promoted);
                promoted.addCourse(this);
            }
        } else {
            waitlist.remove(s);
        }
    }

    @Override
    public String toString() {
        return courseId + ": " + title + " (" + instructor + ")";
    }
}
