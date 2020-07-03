import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static CountDownLatch prepareStartRace = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch finalRace = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch startRace = new CountDownLatch(1);
    public static Semaphore accessTunnel = new Semaphore((int)(CARS_COUNT/2));
    public static ReentrantLock hasWinner = new ReentrantLock();

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            prepareStartRace.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            startRace.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            finalRace.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}