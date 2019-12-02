package createBothWithObserverAndThred;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DestroyVillain extends Observer {
	private static String searchFolderName = "battle-zone-1";
	private static String filepath;

	private enum goodGuysNames {
		SUPERMAN, BATMAN, SPIDER_MAN, THOR, CAPTAIN_AMERICA, WONDER_WOMAN, HULK;

		public static goodGuysNames getRandomNames() {
			Random rand = new Random();
			return values()[rand.nextInt(values().length)];
		}
	}

	public DestroyVillain(Subject subject) {
		this.subject = subject;
		this.subject.attach(this);
	}

	public DestroyVillain() {
	}

	// Create file
	public static void writeObjectToFile(Person serObj, String fileName) {
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

	private static void createHero(String superPower) {
		SuperThing evilPerson = new SuperThing();
		Person thing = evilPerson.createThing("hero");
		thing.setName(goodGuysNames.getRandomNames().toString());
		thing.setType("hero");
		if (superPower.equals("strong person")) {
			thing.setSuperPower("strong person");
		} else {
			thing.setSuperPower("flying person");
		}
		System.out.println(thing.toString());
		writeObjectToFile(thing, thing.getName());
	}

//	private static void identifyObj(List<Person> list) {
//
//		for (Person obj : list) {
//
//			if (obj.getType().equals("villain")) {
//				System.out.println("\nVillain detected !!!" + obj.toString());
//				createHero(obj.getSuperPower());
//				moveToPrison(obj);
//			}
//		}
//	}

	private synchronized static void identifyObj(Person obj) {

//		for (Person obj : list) {

		if (obj.getType().equals("villain")) {
			System.out.println("\nVillain detected !!!" + obj.toString());
			createHero(obj.getSuperPower());
			moveToPrison(obj);
		}
//		}
	}

	private synchronized static void moveToPrison(Person obj) {
		Random rand = new Random();
		int counter = rand.nextInt(100000);
		String path = "C:" + File.separator + "Saved_the_world_again";
		System.out.println("Path:\t" + path + "\n");
		File tempFile = new File(path);

		System.out.println("Moving:\t" + filepath + "\\" + obj.getName() + ".txt");

		File theDir = tempFile;

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
		Path temp = null;
		try {
			temp = Files.move(Paths.get(filepath + File.separator + obj.getName() + ".txt"), Paths.get("C:"
					+ File.separator + theDir.getName() + File.separator + obj.getName() + "_" + counter + ".txt"));
		} catch (IOException e) {
			System.out.println("Error:\t" + e.getMessage());
		}

		if (temp != null) {
			System.out.println("File moved successfully");
		} else {
			System.out.println("Failed to move the file");
		}
	}

	public synchronized static void readObj(String dir) {

		Person obj = null;

		try {
			File objDir = new File(dir);
			File[] files = objDir.listFiles();
			if (files.length > 0) {
				System.out.println("Detected " + files.length + " file(s)");
			} else {
				System.out.println("Empty Folder...\nno file(s) detected");
			}
			for (File file : files) {

				String fileType = file.getName();
				int dotIndex = fileType.lastIndexOf('.');
				// Person obj;
				if (fileType.substring(dotIndex + 1).equalsIgnoreCase("txt")) {

					BufferedReader bf = new BufferedReader(new FileReader(file));
					String str;
					List<String> list = new ArrayList<>();
					while ((str = bf.readLine()) != null) {
						list.add(str);
					}
					String[] stringArr = list.toArray(new String[2]);
					// System.out.println("Content:\t"+Arrays.toString(stringArr));
					obj = new Person();
					for (int i = 0; i <= stringArr.length; i++) {
						obj.setName(stringArr[0]);
						obj.setType(stringArr[1]);
						obj.setSuperPower(stringArr[2]);
					}
					if (bf != null) {
						bf.close();
					}
				} else {
					continue;
				}
				// list.add(obj);
				identifyObj(obj);

			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public synchronized static void findPath() {

		boolean success = false;
		System.out.println("Location:");
		File dir = new File("C:");
		System.out.println(dir.getPath());
		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.getName().equals(searchFolderName)) {
				filepath = file.getAbsolutePath();
				System.out.println("The folder is bean detected\n" + "Folder path:\t" + file.getAbsolutePath());
				readObj(filepath);
				success = true;
			}
		}
		if (!success) {
			System.out.println("No such Folder");
		}

	}

	// Create Herro using Thread Class

//  == TASK 1 == Create Herroes using Threads + yield(), join(), priority()
//  == TASK 2 == Create Villains by extending Threads

	@Override
	public void update() {
		ObserveVillain obs1 = new ObserveVillain("\tVillain Scanner 1");
		ObserveVillain obs2 = new ObserveVillain("\tVillain Scanner 2");

		obs1.start();
		try {
			System.out.println("Starting thread named: " + Thread.currentThread().getName());
			obs1.join();
		} catch (Exception ex) {
			System.out.println("Error:\tThread " + Thread.currentThread().getName() + " was interrupted by:\t " + ex);
		}
		obs2.start();
		try {
			System.out.println("Starting thread named: " + Thread.currentThread().getName());
			obs2.join();
		} catch (Exception ex) {
			System.out.println("Error:\tThread " + Thread.currentThread().getName() + " was interrupted by:\t " + ex);
		}

	}

}

// Thread Class
class ObserveVillain extends Thread {
	private Thread trd;
	private String threadName;

	ObserveVillain(String name) {
		threadName = name;
	}

	public void run() {
		System.out.println("Updating .....");
		DestroyVillain.findPath();
		System.out.println("Thread " + threadName + " is running:\t" + Thread.currentThread().isAlive());
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (trd == null) {
			trd = new Thread(this, threadName);
			trd.setPriority(Thread.NORM_PRIORITY);
			trd.setName(threadName);
			trd.start();
		}
	}
}

