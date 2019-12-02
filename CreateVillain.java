package createBothWithObserverAndThred;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CreateVillain implements Runnable {

	// Location of the file
	private static String filepath = "C:" + File.separator + "battle-zone-1";
	// Creating Lock Object
	private Object lock = new Object();

//  == TASK 2 == Create Herroes by implementing Runnable
	@Override
	public void run() {
		int time = 1000;
		Thread.currentThread().setName("CreateVillain Thread");
		try {
			for (int i = 0; i <= 3; i++) {
				if (i == 0) {
					System.out.println("Preparing process:\t" + Thread.currentThread().getName() + " " + i);
				} else {
					System.out.println("Running process:\t" + Thread.currentThread().getName() + " " + i);
					createObjects();
				}

				Thread.sleep(time);
				Thread.yield();
				Thread.currentThread().setPriority(5);
			}
			System.out.println(Thread.currentThread().getName() + " complete");

		} catch (Exception e) {
			System.out.println("Error:\t" + e.getMessage());
		}

		synchronized (lock) {
			
		// Notify All Observers
		Subject subject = new Subject();
		new DestroyVillain(subject);
		subject.notifyAllObservers();
		}
	}

	private enum badGuysNames {
		MAGNETO, JOKER, KINGPIN, LOKI, MYSTIQUE, VENOM, SHREDDER, DOCTOR_DOOM, NORMAN_OSBORN, RED_SKULL, GALACTUS,
		ULTRON, THANOS, APOCALYPSE, DOCTOR_OCTAPUS, ANNIHILUS, JUGGERNAUT;

		public static badGuysNames getRandomNames() {
			Random rand = new Random();
			return values()[rand.nextInt(values().length)];
		}
	}

	public CreateVillain() {
		createObjects();
	}

	private static synchronized void createObjects() {

		// I create Objects
		for (int i = 0; i <= 2; i++) {

			SuperThing evilPerson = new SuperThing();
			Person thing = evilPerson.createThing("villain");
			thing.setName(badGuysNames.getRandomNames().toString());
			thing.setType("villain");
			int random = (int) (Math.random() * 2);
			if (random == 1) {
				thing.setSuperPower("strong person");
			} else {
				thing.setSuperPower("flying person");
			}
			System.out.println(thing.toString());
			checkFileStatus(filepath, thing);
		}

	}

	// Create file
	private static void writeObjectToFile(Person serObj, String fileName) {
		BufferedWriter output = null;
		try {
			File file = new File(filepath + File.separator + fileName + ".txt");
			output = new BufferedWriter(new FileWriter(file));
			output.write(serObj.getName() + "\n" + serObj.getType() + "\n" + serObj.getSuperPower());
			if (output != null) {
				output.close();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// Check Object file exist or not
	private static void checkFileStatus(String filepath, Person obj) {

		System.out.println("Path:\t" + filepath + "\n");
		String fileName = obj.getName();
		// File tempFile = new File(filepath + "\\" + fileName + ".ser");
		new File(filepath + "\\" + fileName + ".txt");

		File theDir = new File(filepath);

// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (NoSuchFieldError e) {
				System.out.println(e.getMessage());
			}
			if (result) {
				System.out.println(theDir.getName() + " created");
			}
		} else {
			System.out.println("Directory:\t" + theDir.getName() + " already exists !!!");
		}
		writeObjectToFile(obj, fileName);
	}
}
