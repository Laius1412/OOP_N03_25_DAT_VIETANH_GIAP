package innerclass;

public class PrivateOuter {
    private class PrivateInner {
        public void showMessage() {
            System.out.println("Day là Private Inner Class");
        }
    }

    public void createInner() {
        PrivateInner inner = new PrivateInner();
        inner.showMessage();
    }
}
