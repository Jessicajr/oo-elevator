import com.oocourse.TimableOutput;

public class Elevator extends Thread {
    enum EleStatus {
        UP, DOWN, WAIT, OPENDOOR
    }
    
    private EleStatus status;
    private int cntFloor = 1;
    // private EleStatus lastStatus;
    // private boolean eof = false;
    
    public Elevator(int oriFloor, int direction) {
        if (direction == 2) {
            this.status = EleStatus.DOWN;
        } else if (direction == 1) {
            this.status = EleStatus.UP;
        } else {
            this.status = EleStatus.WAIT;
        }
        cntFloor = oriFloor;
    }
    
    public void setStatus(EleStatus nextStatus) {
        this.status = nextStatus;
    }
    
    public int getCntFloor() {
        return cntFloor;
    }
    
    public EleStatus getStatus() {
        return this.status;
    }
    
    public void openDoor() throws InterruptedException {
        // lastStatus = status;
        // status = EleStatus.OPENDOOR;
        TimableOutput.println(String.format("OPEN-%d", cntFloor));
        try {
            sleep(250);
        } catch (Exception e) {
            TimableOutput.println("openDoor exception");
        }
    }
    
    public void closeDoor() throws InterruptedException {
        try {
            sleep(250);
        } catch (Exception e) {
            TimableOutput.println("closeDoor exception");
        }
        TimableOutput.println(String.format("CLOSE-%d", cntFloor));
        // status = lastStatus;
    }
    
    public void move() throws InterruptedException {
        if (status != EleStatus.WAIT) {
            try {
                sleep(500);
            } catch (Exception e) {
                TimableOutput.println("move exception");
            }
            if (status == EleStatus.UP) {
                cntFloor++;
                // TimableOutput.println(String.format(
                // "move up from %d to %d", cntFloor - 1, cntFloor));
            } else if (status == EleStatus.DOWN) {
                cntFloor--;
                // TimableOutput.println(String.format(
                // "move down from %d to %d", cntFloor + 1, cntFloor));
            } else if (status == EleStatus.OPENDOOR) {
                TimableOutput.println("move while open door");
            }
        }
    }
    
}
