package Test;

import Model.Time;

public class TestTime {
	public static void runTest() {
		System.out.println("=== Kiem thu Time ===");
		Time t1 = new Time();
		System.out.println("Mac dinh (12h): " + t1.toString());

		Time t2 = new Time(13, 45, 30);
		System.out.println("Tuy chinh (12h): " + t2.toString());

		t2.setHour(23).setMinute(59).setSecond(58);
		System.out.println("Cap nhat (12h): " + t2.toString());
	}
}


