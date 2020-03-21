import com.oocourse.TimableOutput;
import com.oocourse.elevator1.ElevatorInput;
import com.oocourse.elevator1.PersonRequest;

public class MainClass {
    private static final int max = 1;
    private static Controller[] controllers = new Controller[max];
    // private static int circle = 0;
    
    public static void main(String[] args) throws Exception {
        TimableOutput.initStartTimestamp();
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        for (int i = 0; i < max; i++) {
            controllers[i] = new Controller();
            controllers[i].start();
        }
        while (true) {
            PersonRequest request = elevatorInput.nextPersonRequest();
            // when request == null
            // it means there are no more lines in stdin
            if (request == null) {
                break;
            } else {
                // which elevator to convey the passenger
                // boolean assign = true;
                controllers[0].addReq(request);
            }
        } // while (true)
        elevatorInput.close();
        controllers[0].sendeof();
    }
    
    private static boolean func_1(PersonRequest request)
            throws Exception {
        for (Controller controller : controllers) {
            if (controller.getEleStatus() == Elevator.EleStatus.WAIT) {
                controller.addReq(request);
                return true;
            }
        }
        return false;
    }
    
    private static boolean func_2(PersonRequest request)
            throws Exception {
        for (Controller controller : controllers) {
            if (controller.getEleStatus() == Elevator.EleStatus.UP &&
                    request.getFromFloor() > controller.getCntFloor() ||
                    controller.getEleStatus() == Elevator.EleStatus.DOWN &&
                            request.getFromFloor() < controller.getCntFloor()) {
                if (controller.getMaxReq() >= request.getFromFloor() &&
                        controller.getMinReq() <= request.getFromFloor()) {
                    controller.addReq(request);
                    // System.out.println("2");
                    return true;
                }
            }
        }
        return false;
        // controller.addReq(request);
    }
    
    private static boolean func_3(PersonRequest request)
            throws Exception {
        for (Controller controller : controllers) {
            if ((controller.getEleStatus() == Elevator.EleStatus.UP &&
                    request.getFromFloor() >= controller.getCntFloor()) ||
                    (controller.getEleStatus() == Elevator.EleStatus.DOWN &&
                            request.getFromFloor() <=
                                    controller.getCntFloor())) {
                controller.addReq(request);
                // System.out.println("3");
                return true;
            }
        }
        return false;
    }
    
    private static boolean func_4(PersonRequest request)
            throws Exception {
        for (Controller controller : controllers) {
            if (controller.getMaxReq() >= request.getFromFloor() &&
                    controller.getMinReq() <= request.getFromFloor()) {
                controller.addReq(request);
                // System.out.println("4");
                return true;
            }
        }
        return false;
    }
    
}