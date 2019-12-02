package createBothWithObserverAndThred;

import java.util.ArrayList;
import java.util.List;

public class Subject {

	// -----------------------
	// ArrayList
	// -----------------------

	/*
	 * This is the list of observers that will get all notifications(updates)
	 */
	private List<Observer> observers = new ArrayList<Observer>();

	// -----------------------
	// Functions
	// -----------------------

	/*
	 * Adding new observers to the list
	 */
	public void attach(Observer observer) {
		observers.add(observer);
		System.out.println("Observer added\n");
	}

	/*
	 * Removing observers from the list
	 */
	public void remove(Observer observer) {
		observers.remove(observer);
	}

	/*
	 * Loop throw all observers and call their method update()
	 */
	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update();
			System.out.println("Notify all Observers from Subject class\n");
		}
	}

}