package Controller.interfaces;

import java.util.Date;
import java.util.List;

import Model.ReceiveManagement;

public interface CRUDreceive {
    boolean createReceive(String id, String name, float money, String type, String description, Date date);
    boolean deleteReceive(String id);
    boolean deleteReceiveByName(String name);
    List<ReceiveManagement> listAllReceives();
}
