import com.oocourse.TimableOutput;
import com.oocourse.elevator1.PersonRequest;

import java.util.ArrayList;

public class Controller extends Thread {
    private Elevator elevator;
    private ArrayList<Person> personList = new ArrayList<>();
    private int maxReq = 0;
    private int minReq = 16;
    private int[] requiredList = new int[16];
    private boolean eof = false;
    
    public Controller() {
        elevator = new Elevator(1, 0);
    }
    
    // add person
    public void addReq(PersonRequest newReq) throws Exception {
        Person newPerson = new Person(newReq);
        personList.add(newPerson);
        // TimableOutput.println(String.format(this.getName() +
        //        " add new person (id: %d)", newPerson.getId()));
        if (newPerson.getOri() < minReq) {
            minReq = newPerson.getOri();
        }
        if (newPerson.getOri() > maxReq) {
            maxReq = newPerson.getOri();
        }
        requiredList[newPerson.getOri()] = 1;
    }
    
    public void getMaxRequiredFloor() {
        for (int i = 1; i < 16; i++) {
            if (requiredList[i] == 1) {
                maxReq = i;
            }
        }
    }
    
    public void getMinRequiredFloor() {
        for (int i = 15; i > 0; i--) {
            if (requiredList[i] == 1) {
                minReq = i;
            }
        }
    }
    
    @Override
    public void run() {
        // initialize elevator
        try {
            elevator = new Elevator(1, 1);
            elevator.start();
            while (true) {
                if (eof && personList.size() == 0) {
                    System.exit(0);
                }
                if (requiredList[elevator.getCntFloor()] == 1) {
                    //本层有人要上下
                    int cntFloor = elevator.getCntFloor();
                    elevator.openDoor();
                    personBehavior(cntFloor);
                    requiredList[elevator.getCntFloor()] = 0;
                    elevator.closeDoor();
                    getMaxRequiredFloor();
                    getMinRequiredFloor();
                } else {
                    // 本层没有人要上下
                    if (elevator.getStatus() == Elevator.EleStatus.UP) {
                        if (maxReq < elevator.getCntFloor()) {
                            elevator.setStatus(Elevator.EleStatus.DOWN);
                        }
                    } else if (elevator.getStatus() ==
                            Elevator.EleStatus.DOWN) {
                        if (minReq > elevator.getCntFloor()) {
                            elevator.setStatus(Elevator.EleStatus.UP);
                        }
                    } else if (elevator.getStatus() ==
                            Elevator.EleStatus.WAIT) {
                        if (maxReq > elevator.getCntFloor() ||
                                minReq > elevator.getCntFloor()) {
                            elevator.setStatus(Elevator.EleStatus.UP);
                        } else {
                            elevator.setStatus(Elevator.EleStatus.DOWN);
                        }
                    }
                    if (personList.size() == 0) {
                        elevator.sleep(100);
                        elevator.setStatus(Elevator.EleStatus.WAIT);
                    }
                    elevator.move();
                }
            } // while (true)
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    private void personBehavior(int cntFloor) {
        for (int i = 0; i < personList.size(); i++) {
            Person one = personList.get(i);
            if (one.getStatus() == Person.PersonStatus.IN
                    && one.getDst() == cntFloor) {
                personOut(i, cntFloor);
                i--;
            } else if (one.getStatus() == Person.PersonStatus.OUT
                    && one.getOri() == cntFloor) {
                personIn(i, cntFloor);
            }
        }
    }
    
    private void personOut(int i, int cntFloor) {
        TimableOutput.println(String.format("OUT-%d-%d",
                personList.get(i).getId(), cntFloor));
        personList.remove(i);
    }
    
    private void personIn(int i, int cntFloor) {
        TimableOutput.println(String.format("IN-%d-%d",
                personList.get(i).getId(), cntFloor));
        personList.get(i).setStatus(Person.PersonStatus.IN);
        requiredList[personList.get(i).getDst()] = 1;
    }
    
    public void sendeof() throws Exception {
        eof = true;
    }
    
    public Elevator.EleStatus getEleStatus() {
        return elevator.getStatus();
    }
    
    public int getCntFloor() {
        return elevator.getCntFloor();
    }
    
    public int getMaxReq() {
        return maxReq;
    }
    
    public int getMinReq() {
        return minReq;
    }
}
