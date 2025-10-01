public class Animals {
    private String name;

    public Animals(String name) {
        this.name = ("Name " + name);
    }


    class Dog extends Animals {


        private int maxRun = 500;
        private int minRun = 0;

        private int swim = 10;

        public Dog(String name, int maxRun, int minRun, int swim) {
            super(name);
            this.maxRun = (maxRun);
            this.minRun = (minRun);
            this.swim = (swim);
        }

        public void run(int distance) {
            if (distance <= maxRun || distance >= minRun) {
                System.out.println(name + " пробежал ");
            } else {
                System.out.println(name + "прошел ");
            }
        }

        public void setSwim(int distance) {
            if (distance <= swim) {
                System.out.println(name + " проплыл ");
            } else {
                System.out.println(name + "утонул");
            }
        }
    }


    class Cat extends Animals {

        private int maxRun = 200;
        private int minRun = 0;

        private int satiety = 10;

        public Cat(String name, int maxRun, int minRun, int satiety) {
            super(name);
            this.maxRun = (maxRun);
            this.minRun = (minRun);
            this.satiety = (satiety);
        }

        public void run(int distance) {
            if (distance <= maxRun && distance >= minRun) {
                System.out.println(name + " пробежал ");
            } else {
                System.out.println(name + " не смог пробежать ");
            }
        }

        public void eat(int hungry) {
            if (hungry >= 5 && hungry <= 10) {
                System.out.println(name + " Не голодный ");
            } else {
                System.out.println(name + " Голодный ");
            }
        }
    }


    public static void main(String[] args) {
        Animals animals = new Animals("Name");
        Dog dog1 = animals.new Dog("Шарик", 500, 0, 10);
        dog1.run(50);
        dog1.setSwim(5);

        Cat cat1 = animals.new Cat("Мурзик", 200, 0, 7);
        cat1.eat(7);
        cat1.run(100);

    }
}
