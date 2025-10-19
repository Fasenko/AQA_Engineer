import java.util.*;

public class Decanat {

    public static void removeLowGrades(Set<Student> students) {
        students.removeIf(student -> student.getAverageGrade() < 3);
    }

    public static void promoteStudents(Set<Student> students) {
        for (Student s : students) {
            if (s.getAverageGrade() >= 3) {
                s.nextCourse();
            }
        }
    }

    public static void printStudents(Set<Student> students, int course) {
        System.out.println("Студенты курса " + course + ":");
        for (Student s : students) {
            if (s.getCourse() == course) {
                System.out.println("- " + s.getName());
            }
        }
    }
}

