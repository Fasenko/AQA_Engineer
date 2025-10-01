
public class Product1 {
    private String name;
    private String productionDate;
    private String manufacturer;
    private String countryOfOrigin;
    private double price;
    private boolean reservation;

    public Product1 (String name, String productionDate, String manufacturer, String countryOfOrigin, double price, boolean reservation ) {
        this.name = name;
        this.productionDate = productionDate;
        this.manufacturer = manufacturer;
        this.countryOfOrigin = countryOfOrigin;
        this.price = price;
        this.reservation = reservation;
    }

    public void info (){
        System.out.println("Name " + name);
        System.out.println("Date " + productionDate );
        System.out.println("manufacturer " + manufacturer);
        System.out.println("countryOfOrigin " + countryOfOrigin);
        System.out.println("price " + price);
        System.out.println("reservation" + reservation);
    }

    public static void main(String[] args) {
        Product1[] productsArray = new Product1[5];
        productsArray[0] = new Product1("Samsung ", "07.10.2025 ", "Samsung ", "Korea ", 15000, true);
        productsArray[1] = new Product1("Honor ", "10.11.2025 ", "Honor ", "China ", 15000, true);
        productsArray[2] = new Product1("Macbook ", "11.10.2025 ", "MacBook ", "USA ", 15000, true);
        productsArray[3] = new Product1("Redmi ", "12.10.2025 ", "Redmi ", "China ", 15000, true);
        productsArray[4] = new Product1("Xaomi ", "09.10.2025 ", "Xiaomi ", "China ", 15000, true);

        productsArray.info();

    }

}
