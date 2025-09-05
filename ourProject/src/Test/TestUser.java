package Test;

import Model.User;

public class TestUser {
	public static void runTest() {
		System.out.println("=== Test User ===");

		User user = new User();
		user.setUsername("vietanh");
		user.setPassword("123456789");
		user.setRole("admin");

		System.out.println("Username: " + user.getUsername());
		System.out.println("Password: " + user.getPassword());
		System.out.println("Role:     " + user.getRole());
	}
}
