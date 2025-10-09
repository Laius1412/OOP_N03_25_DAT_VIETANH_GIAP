package callback;

public class Callee extends MyIncrement{
    private int i = 0;

    private void incr() {
        i++;
        System.out.println("Callee " + i + " incremented to " + i);
    }

    private class Closure implements Incrementable {
        public void increment() {
            incr();
        }
    }

    Incrementable getCallbackReference() {
        return new Closure();
    }
}
