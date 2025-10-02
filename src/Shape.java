public interface Shape {
    double getPerimeter();
    double getArea();
    String getFillColor();
    String getBorderColor();
}


    class Circle implements Shape {
        private double radius;
        private String fillColor;
        private String borderColor;


        public Circle(double radius, String fillColor, String borderColor) {
            this.radius = radius;
            this.fillColor = fillColor;
            this.borderColor = borderColor;
        }

        public double getPerimeter() {
            return 2 * Math.PI * radius;
        }

        public double getArea() {
            return Math.PI * radius * radius;
        }

        public String getFillColor() {
            return fillColor;
        }

        public String getBorderColor() {
            return borderColor;
        }
    }

        class Rectangle implements Shape {
            private double width, height;
            private String fillColor;
            private String borderColor;


            public Rectangle(double width, double height, String fillColor, String borderColor) {
                this.width = width;
                this.height = height;
                this.fillColor = fillColor;
                this.borderColor = borderColor;
            }
            public double getPerimeter() {
                return 2 * (width + height);
            }
            public double getArea() {
                return width * height;
            }
            public String getFillColor() {
                return fillColor;
            }
            public String getBorderColor() {
                return borderColor;
            }
        }

        class Triangle implements Shape {
            private double a, b, c;
            private String fillColor;
            private String borderColor;

            public Triangle(double a, double b, double c, String fillColor, String borderColor) {
                this.a = a;
                this.b = b;
                this.c = c;
                this.fillColor = fillColor;
                this.borderColor = borderColor;
            }
            public double getPerimeter() {
                return a + b + c;
            }
            public double getArea() {
                double p = getPerimeter() / 2;
                return Math.sqrt(p * (p - a) * (p - b) * (p - c)); // Формула Герона
            }
            public String getFillColor() {
                return fillColor;
            }
            public String getBorderColor() {
                return borderColor;
            }
        }
        class Shapes {
            public static void main(String[] args) {
                Shape circle = new Circle(5, "Красный", "Черный");
                Shape rectangle = new Rectangle(4, 6, "Синий", "Зеленый");
                Shape triangle = new Triangle(3, 4, 5, "Желтый", "Фиолетовый");

                Shape[] shapes = {circle, rectangle, triangle};

                for (Shape s : shapes) {
                    System.out.println(s.getClass().getSimpleName() + " -> Периметр: " + s.getPerimeter() +
                            ", Площадь: " + s.getArea() +
                            ", Цвет заливки: " + s.getFillColor() +
                            ", Цвет границ: " + s.getBorderColor());
                }
            }
        }





