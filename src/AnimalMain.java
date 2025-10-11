public class AnimalMain {
    public static void main(String[] args) {
        Dog dog = new Dog("Шарик");
        dog.run(300);
        dog.swim(5);

        Cat[] cats = {
                new Cat("Мурзик"),
                new Cat("Барсик"),
                new Cat("Пушок")
        };

        Bowl bowl = new Bowl(25);

        for (Cat cat : cats) {
            cat.eat(bowl, 10);
        }

        System.out.println("\nОставшаяся еда в миске: " + bowl.getFoodAmount());
        System.out.println("\nСытость котов:");
        for (Cat cat : cats) {
            System.out.println(cat.name + " — " + (cat.isSatiety() ? "сыт" : "голоден"));
        }

        System.out.println("\nВсего животных: " + Animal.getAnimalCount());
        System.out.println("Котов: " + Cat.getCatCount());
        System.out.println("Собак: " + Dog.getDogCount());
    }
}

