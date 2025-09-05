package Test;

import Model.Time;

public class TestTime {
	public static void runTest() {
		Time t = new Time();
		t.setTime(13, 5, 9);
		System.out.println("Expect 13: " + t.getHour());
		System.out.println("Expect 05: " + (t.getMinute() < 10 ? "0" : "") + t.getMinute());
		System.out.println("Expect 09: " + (t.getSecond() < 10 ? "0" : "") + t.getSecond());
		t.printTime();

		t.setTime(0, 60, -1);
		System.out.println("Expect 0 0 0: " + t.getHour() + " " + t.getMinute() + " " + t.getSecond());
	}
}
