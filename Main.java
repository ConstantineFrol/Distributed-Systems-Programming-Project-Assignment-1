package createBothWithObserverAndThred;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		final int MAX_THREADS = 3;

		// == TASK 1 == Create Villains using Threads + yield(), join(), priority()

		Thread cv1 = new Thread(new CreateVillain());
		Thread cv2 = new Thread(new CreateVillain());

		cv1.start();
		try {
			System.out.println("Current Thread: " + Thread.currentThread().getName());
			cv1.join();
		} catch (Exception ex) {
			System.out.println("Error:\tThread " + Thread.currentThread().getName() + " was interrupted by:\t " + ex);
		}

		cv2.start();
		try {
			System.out.println("Current Thread: " + Thread.currentThread().getName());
			cv2.join();
		} catch (Exception ex) {
			System.out.println("Error:\tThread " + Thread.currentThread().getName() + " was interrupted by:\t " + ex);
		}

		System.out.println("Starting Fixed Thread Pool");

		Runnable rThread1 = new CreateVillain();
		Runnable rThread2 = new CreateVillain();
		Runnable rThread3 = new CreateVillain();

		// Fixed Pool
		ExecutorService fixpool = Executors.newFixedThreadPool(MAX_THREADS);

		fixpool.execute(rThread1);
		fixpool.execute(rThread2);
		fixpool.execute(rThread3);

		fixpool.shutdown();

		// Cached Pool
		System.out.println("Starting Cached Thread Pool");
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 1; i <= 3; ++i) {
			executor.execute(new CreateVillain());
		}
		executor.shutdown();

		// System.out.println("=================== Done with Villains
		// ===================");

		System.out.println("===================   StartUsing Observer   ===================");
		System.out.println("is there any active threads?\t\t" + Thread.currentThread().isAlive());

	}
}
