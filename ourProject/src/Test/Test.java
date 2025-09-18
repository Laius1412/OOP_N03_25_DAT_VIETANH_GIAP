package Test;

abstract class Actor {
    abstract void act();
}

class HappyActor extends Actor {
    
    public void act() {
        System.out.println("vui ve");
    }
}

class SadActor extends Actor {
    
    public void act() {
        System.out.println("buon");
    }
}

class Stage {
    private Actor a = new HappyActor();
    public void change() {
        a = new SadActor();
    }
    public void go() {
        a.act();
    }
}

public class Test {
    public static void main(String[] args) {
        Stage s = new Stage();
        s.go();
        s.change();
        s.go();
    }
}

 
