package callback;

public class TestCallbacks {
    public static void main(String[] args) {
        Callee c = new Callee();
        c.increment();

        Caller caller = new Caller(c.getCallbackReference());

        // Gọi 10 lần
        for (int j = 1; j <= 10; j++) {
            caller.go();
        }
    }
}
