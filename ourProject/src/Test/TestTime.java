package Test;

import Model.Time;

public class TestTime {
        public static void runTest() {
                System.out.println(" Test Time: hop le, chuyen tu AM thanh PM");
                Time t1 = new Time(13, 5, 9);
                t1.display(); // 1:05:09 PM

                System.out.println("\n Test Time: 0h0m0s chuyen thanh 12h0m0s");
                Time t2 = new Time().setTime(0, 0, 0);
                t2.display(); 

                System.out.println("\n Test Time: sát 24h, chuyen tu PM thanh AM");
                Time t3 = new Time().setHour(23).setMinute(59).setSecond(59);
                t3.display(); // 11:59:59 PM

                System.out.println("\n Test Time: gia tri khong hop le");
                Time t4 = new Time().setTime(25, -1, 61);
                t4.display(); // 12:00:00 AM

                System.out.println("\nTest Time: hien tai");
                Time now = Time.now();
                System.out.println("hien tai: " + now);
        }
}


