public interface Shape {
    double getPerimeter();
    double getArea();
    String getFillColor();
    String getBorderColor();

    default void printInfo() {
        System.out.println(getClass().getSimpleName() + " -> " +
                "Периметр: " + getPerimeter() +
                ", Площадь: " + getArea() +
                ", Цвет заливки: " + getFillColor() +
                ", Цвет границ: " + getBorderColor());
    }
}
