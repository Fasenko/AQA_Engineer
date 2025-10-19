import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Student> students = new HashSet<>();
        students.add(new Student("Иванов Иван", "A-101", 1, Arrays.asList(4, 5, 3)));
        students.add(new Student("Петров Петр", "A-101", 2, Arrays.asList(2, 2, 3)));
        students.add(new Student("Сидоров Сидор", "B-201", 3, Arrays.asList(5, 4, 5)));
        students.add(new Student("Кузнецов Николай", "B-202", 1, Arrays.asList(3, 3, 3)));

        System.out.println("Исходный список студентов:");
        students.forEach(System.out::println);

        Decanat.removeLowGrades(students);
        Decanat.promoteStudents(students);

        System.out.println("\nПосле удаления и перевода:");
        students.forEach(System.out::println);

        System.out.println();
        Decanat.printStudents(students, 2);

        System.out.println("\n=== Телефонный справочник ===");
        PhoneBook pb = new PhoneBook();
        pb.add("Иванов", "+7-900-111-22-33");
        pb.add("Петров", "+7-900-222-33-44");
        pb.add("Иванов", "+7-900-555-66-77");
        pb.add("Сидоров", "+7-900-111-22-33"); // дубликат номера

        System.out.println("Телефоны Иванова: " + pb.get("Иванов"));
        System.out.println("Телефоны Петрова: " + pb.get("Петров"));
        System.out.println("Телефоны Сидорова: " + pb.get("Сидоров"));
    }
}

