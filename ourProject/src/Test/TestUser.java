package Test;

import Model.User;
import Controller.UserController;

public class TestUser {
	public static void runTest() {
        UserController controller = new UserController();
        controller.create(new User());          
        controller.read("dat");    
        controller.update("dat", "1234", "qlsk");
		controller.read("dat");  
        controller.delete("dat");
	}
}