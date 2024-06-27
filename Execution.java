import busManagement.Management;

public class Execution{
    public static void main(String[] args){
        //Main
        System.out.println("Welcome to Bus Booking Application");
        System.out.println("----------------------------------------------------");
        System.out.println("--------------------------------");
        // Application bApplication = new Application();
        Management lManagement = new Management();
        lManagement.callCheckAccess();
    }
}