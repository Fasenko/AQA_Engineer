import java.util.*;

class PhoneBook {
    private Map<String, List<String>> phoneBook = new HashMap<>();

    public void add(String lastName, String phoneNumber) {
        phoneBook.computeIfAbsent(lastName, k -> new ArrayList<>()).add(phoneNumber);
    }

    public List<String> get(String lastName) {
        return phoneBook.getOrDefault(lastName, Collections.emptyList());
    }

    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();
        pb.add("Иванов", "+7-900-111-22-33");
        pb.add("Петров", "+7-900-222-33-44");
        pb.add("Иванов", "+7-900-555-66-77"); // однофамилец

        System.out.println("Телефоны Иванова: " + pb.get("Иванов"));
        System.out.println("Телефоны Петрова: " + pb.get("Петров"));
        System.out.println("Телефоны Сидорова: " + pb.get("Сидоров")); // нет в справочнике
    }
}


