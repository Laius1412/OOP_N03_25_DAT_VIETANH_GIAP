package Test;

import Model.User;

public class TestUser {
	public static void runTest() {
		User u = new User();
		u.create(u);
	}
}
