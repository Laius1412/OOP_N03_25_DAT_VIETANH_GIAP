package innerclass;

public class TestInnerClasses {
    public static void main(String[] args) {
        System.out.println("=== Test Public Inner Class ===");
        PublicOuter publicOuter = new PublicOuter();
        PublicOuter.PublicInner publicInner = publicOuter.new PublicInner();
        publicInner.showMessage();

        System.out.println("\n=== Test Private Inner Class ===");
        PrivateOuter privateOuter = new PrivateOuter();
        privateOuter.createInner();
    }
}
