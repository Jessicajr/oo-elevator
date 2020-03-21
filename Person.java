import com.oocourse.elevator1.PersonRequest;

public class Person {
    enum PersonStatus {
        IN, OUT
    }
    
    private int id;
    private int ori;
    private int dst;
    private PersonStatus status;
    
    public Person(PersonRequest req) throws Exception {
        this.id = req.getPersonId();
        this.ori = req.getFromFloor();
        this.dst = req.getToFloor();
        this.status = PersonStatus.OUT;
    }
    
    public int getId() {
        return id;
    }
    
    public int getOri() {
        return ori;
    }
    
    public int getDst() {
        return dst;
    }
    
    public PersonStatus getStatus() {
        return status;
    }
    
    public void setStatus(PersonStatus personBehavior) {
        this.status = personBehavior;
    }
}
