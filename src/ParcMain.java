public class ParcMain {
    public static void main(String[] args) {

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
