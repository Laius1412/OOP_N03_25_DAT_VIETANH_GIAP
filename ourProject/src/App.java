import Test.TestUser;
import Test.TestTime;
import Test.TestRecursion;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Bat dau chuong trinh ===\n");
        TestTime.runTest();
        TestUser.runTest();
        TestRecursion.runTest();
        System.out.println("\n=== Ket thuc chuong trinh ===");
    }
}
