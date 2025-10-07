import java.util.*;

class Student {
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

    // ✅ Метод вывода студентов по курсу
    public static void printStudents(Set<Student> students, int course) {
        System.out.println("Студенты курса " + course + ":");
        for (Student s : students) {
            if (s.getCourse() == course) {
                System.out.println("- " + s.getName());
            }
        }
    }

    public static void main(String[] args) {
        Set<Student> students = new HashSet<>();
        students.add(new Student("Иванов Иван", "A-101", 1, Arrays.asList(4, 5, 3)));
        students.add(new Student("Петров Петр", "A-101", 2, Arrays.asList(2, 2, 3)));
        students.add(new Student("Сидоров Сидор", "B-201", 3, Arrays.asList(5, 4, 5)));
        students.add(new Student("Кузнецов Николай", "B-202", 1, Arrays.asList(3, 3, 3)));

        System.out.println("Исходный список студентов:");
        students.forEach(System.out::println);

        removeLowGrades(students);
        promoteStudents(students);

        System.out.println("\nПосле удаления и перевода:");
        students.forEach(System.out::println);

        System.out.println();
        printStudents(students, 2);
    }


}

