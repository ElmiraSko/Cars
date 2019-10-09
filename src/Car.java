import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private int stages_count = 0; // количество этапов пути
    static int countsReadyCars;
    static {
        CARS_COUNT = 0;
        countsReadyCars = 0;
    }
    private Race race;
    private int speed;
    private String name;
    private CountDownLatch count; // переменная для хранения ссылки на счетчик-блокировки
    private CyclicBarrier barrier;

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CountDownLatch count, CyclicBarrier barrier) { //у каждой машины есть скорость и массив этапов гонки
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.count = count;
        this.barrier = barrier;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            countsReadyCars = CARS_COUNT;
            barrier.await();  //
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
            stages_count++;
            if (stages_count == race.getStages().size()){ // если все этапы пройдены
               count.countDown(); // уменьшаем счетчик сомоблокировки
            }
        }
    }
}