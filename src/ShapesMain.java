public class ShapesMain {
    public static void main(String[] args) {
        Shape circle = new Circle(5, "Красный", "Черный");
        Shape rectangle = new Rectangle(4, 6, "Синий", "Зеленый");
        Shape triangle = new Triangle(3, 4, 5, "Желтый", "Фиолетовый");

        Shape[] shapes = {circle, rectangle, triangle};

        for (Shape shape : shapes) {
            shape.printInfo();
        }
    }
}

