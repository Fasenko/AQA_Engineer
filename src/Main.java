public class Main {
    public static void main(String[] args) {

        // 1. Создание массива товаров
        Product[] productsArray = new Product[5];

        productsArray[0] = new Product("Samsung S25 Ultra", "01.02.2025", "Samsung Corp.", "Корея", 5599.0, true);
        productsArray[1] = new Product("iPhone 16 Pro", "15.09.2024", "Apple Inc.", "США", 6999.0, false);
        productsArray[2] = new Product("Xiaomi 15", "20.10.2024", "Xiaomi Ltd.", "Китай", 3499.0, true);
        productsArray[3] = new Product("Sony WH-1000XM6", "05.03.2025", "Sony", "Япония", 899.0, false);
        productsArray[4] = new Product("Asus ROG Laptop", "12.12.2024", "ASUS", "Тайвань", 4999.0, true);

        // 2. Вывод информации о товарах
        System.out.println("=== Список товаров ===");
        for (Product p : productsArray) {
            p.printInfo();
        }

        // 3. Использование класса Park и внутреннего класса Attraction
        System.out.println("\n=== Аттракционы парка ===");
        Park park = new Park();
        Park.Attraction a1 = park.new Attraction("Американские горки", "10:00 - 20:00", 500);
        Park.Attraction a2 = park.new Attraction("Колесо обозрения", "09:00 - 22:00", 300);
        Park.Attraction a3 = park.new Attraction("Комната страха", "12:00 - 23:00", 400);

        a1.printAttractionInfo();
        a2.printAttractionInfo();
        a3.printAttractionInfo();
    }
}
