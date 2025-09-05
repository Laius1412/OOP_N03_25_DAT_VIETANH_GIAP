import Test.TestUser;
import Test.TestTime;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Bắt đầu chạy chương trình ===\n");
        TestTime.runTest();
        TestUser.runTest();
        System.out.println("\n=== Kết thúc chương trình ===");
    }
}
