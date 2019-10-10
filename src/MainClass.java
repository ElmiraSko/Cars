import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {

    public static final int CARS_COUNT = 4;
    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(CARS_COUNT); // добавили объект-счетчик самоблокировки для потока main
        Runnable barrierAction =   // действие, которое должно выполниться, когда со всех потоков снимется блокировка
                new Runnable() { public void run() {System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!"); }};
        CyclicBarrier barrier = new CyclicBarrier(CARS_COUNT, barrierAction); // объект синхронизации, устанавливает блокировку
        // для CARS_COUNT потоков до некоторой позиции в коде, вынуждая ждать друг друга

        Semaphore smp = new Semaphore(2, true); // ссылка на Semaphore передается в конструктор тоннеля

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(smp), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cdl, barrier); // еще в конструктор передаем cdl, barrier
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try{ //ставим блокировку у главного потока и ждем отсчета
            cdl.await();
        }catch(InterruptedException e){e.printStackTrace();}

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}