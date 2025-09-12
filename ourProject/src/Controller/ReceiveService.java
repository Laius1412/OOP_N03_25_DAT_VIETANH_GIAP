package Controller;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Controller.interfaces.CRUDreceive;
import Model.ReceiveManagement;

public class ReceiveService implements CRUDreceive {
    private final Map<String, ReceiveManagement> idToReceive = new ConcurrentHashMap<>();

    @Override
    public boolean createReceive(String id, String name, float money, String type, String description, Date date) {
        if (id == null || id.isBlank()) return false;
        if (idToReceive.containsKey(id)) return false;
        ReceiveManagement rm = new ReceiveManagement();
        rm.setId(id);
        rm.setName(name);
        rm.setMoney(money);
        rm.setType(type);
        rm.setDescription(description);
        rm.setDate(date);
        idToReceive.put(id, rm);
        return true;
    }

    @Override
    public boolean deleteReceive(String id) {
        if (id == null) return false;
        return idToReceive.remove(id) != null;
    }

    @Override
    public boolean deleteReceiveByName(String name) {
        if (name == null || name.isBlank()) return false;
        String targetId = null;
        for (Map.Entry<String, ReceiveManagement> e : idToReceive.entrySet()) {
            if (name.equals(e.getValue().getName())) {
                targetId = e.getKey();
                break;
            }
        }
        if (targetId == null) return false;
        idToReceive.remove(targetId);
        return true;
    }

    @Override
    public List<ReceiveManagement> listAllReceives() {
        return new ArrayList<>(idToReceive.values());
    }
}


