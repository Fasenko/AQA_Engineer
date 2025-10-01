

public class Park {
    private String name;

    public Park (String name){
        this.name = name;
    }

    public static class Attraction {

        private String name;
        private String openingHours;
        private double price;

        public Attraction(String name, String openingHours, double price) {
            this.name = name;
            this.openingHours = openingHours;
            this.price = price;
        }

        public void info() {
            System.out.println("Name " + name);
            System.out.println("openingHours " + openingHours);
            System.out.println("Price " + price);
        }

    }
    public static void main(String[] args) {
        Park park = new Park("Apple");
        System.out.println(park);
        Park.Attraction a1 = new Park.Attraction("Wheel", "08:30", 150);
        a1.info();
    }

}



