package Test;

import java.util.Date;
import java.util.Scanner;

import Controller.ReceiveService;
import Model.ReceiveManagement;

public class testReceive {
    public static void runTest() {
        ReceiveService service = new ReceiveService();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Menu Receive ===");
            System.out.println("1. them khoan thu");
            System.out.println("2. xoa khoan thu theo ten");
            System.out.println("3. in danh sach khoan thu");
            System.out.println("0. thoat");
            System.out.print("Chon: ");
            String choice = sc.nextLine();
            if ("1".equals(choice)) {
                System.out.print("Nhap id: ");
                String id = sc.nextLine();
                System.out.print("Nhap ten: ");
                String name = sc.nextLine();
                System.out.print("Nhap so tien: ");
                float money = 0f;
                try { money = Float.parseFloat(sc.nextLine()); } catch (Exception e) { System.out.println("So tien khong hop le!"); continue; }
                System.out.print("Nhap hang muc: ");
                String type = sc.nextLine();
                System.out.print("Nhap mo ta: ");
                String description = sc.nextLine();
                Date date = new Date();
                boolean ok = service.createReceive(id, name, money, type, description, date);
                System.out.println(ok ? "them thanh cong" : "them that bai (id trong/da ton tai)");
            } else if ("2".equals(choice)) {
                System.out.print("nhap ten can xoa: ");
                String name = sc.nextLine();
                boolean ok = service.deleteReceiveByName(name);
                System.out.println(ok ? "Xoa thanh cong" : "Khong tim thay theo ten");
            } else if ("3".equals(choice)) {
                System.out.println("\nDanh sach khoan thu:");
                for (ReceiveManagement r : service.listAllReceives()) {
                    System.out.println("- id=" + r.getId() + ", ten=" + r.getName() + ", tien=" + r.getMoney() + ", hang muc=" + r.getType() + ", ngay=" + r.getDate());
                }
            } else if ("0".equals(choice)) {
                break;
            } else {
                System.out.println("Lua chon khong hop le");
            }
        }
        
    }
}
