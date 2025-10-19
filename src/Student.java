import java.util.*;

public class Student {
    private String name;
    private String group;
    private int course;
    private List<Integer> grades;

    public Student(String name, String group, int course, List<Integer> grades) {
        this.name = name;
        this.group = group;
        this.course = course;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public int getCourse() {
        return course;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public double getAverageGrade() {
        return grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public void nextCourse() {
        this.course++;
    }

    @Override
    public String toString() {
        return name + " (курс: " + course + ", ср. балл: " + String.format("%.2f", getAverageGrade()) + ")";
    }
}


