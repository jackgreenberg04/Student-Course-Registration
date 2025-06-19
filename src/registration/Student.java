package registration;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student with an id, name and list of registered courses.
 */
public class Student {
    private final String id;
    private final String name;
    private final List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    /**
     * Adds a course to the student's schedule.
     */
    public void addCourse(Course c) {
        if (!registeredCourses.contains(c)) {
            registeredCourses.add(c);
        }
    }

    /**
     * Removes a course from the student's schedule.
     */
    public void removeCourse(Course c) {
        registeredCourses.remove(c);
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
