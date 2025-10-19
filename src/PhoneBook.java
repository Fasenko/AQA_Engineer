import java.util.*;

public class PhoneBook {
    private final Map<String, List<String>> phoneBook = new HashMap<>();
    private final Set<String> usedNumbers = new HashSet<>();

    public void add(String lastName, String phoneNumber) {
        if (usedNumbers.contains(phoneNumber)) {
            System.out.println("Ошибка: номер " + phoneNumber + " уже используется другим человеком!");
            return;
        }

        phoneBook.computeIfAbsent(lastName, k -> new ArrayList<>()).add(phoneNumber);
        usedNumbers.add(phoneNumber);
    }

    public List<String> get(String lastName) {
        return phoneBook.getOrDefault(lastName, Collections.emptyList());
    }
}
