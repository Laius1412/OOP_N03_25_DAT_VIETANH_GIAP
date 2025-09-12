import Test.testPerson;
import Test.testReceive;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Bat dau chay chuong trinh ===\n");
        // testPerson.runTest();
        System.out.println("\n=== Kiem thu Receive ===");
        testReceive.runTest();
        System.out.println("\n=== ket thuc chuong trinh ===");
    }
}
