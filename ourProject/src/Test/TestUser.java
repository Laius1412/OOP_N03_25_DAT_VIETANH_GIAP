package Test;

import Model.User;

public class TestUser {
	public static void runTest() {
		User u = new User();
		u.setUsername("alice");
		u.setPassword("secret");
		u.setRole("admin");

		System.out.println("Username: " + u.getUsername());
		System.out.println("Password: " + u.getPassword());
		System.out.println("Role: " + u.getRole());
	}

	public static void main(String[] args) {
		runTest();
	}
}
