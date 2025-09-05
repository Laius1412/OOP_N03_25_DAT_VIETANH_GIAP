package Test;

import Model.User;

public class TestUser {
        public static void runTest() {
                User admin = new User("giap", "vogiap102", "ADMIN");
                User guest = new User();
                guest.setUsername("giapnhe");
                guest.setPassword("vogiap103");
                guest.setRole("GUEST");

                System.out.println("Thong tin admin");
                admin.display();
                System.out.println(admin);

                System.out.println("\n Thong tin Guest ");
                guest.display();
                System.out.println(guest);
        }
}


