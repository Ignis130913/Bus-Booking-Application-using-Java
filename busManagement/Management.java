package busManagement;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Management {
    static Scanner sc = new Scanner(System.in);
    static List<Bus> Busses = new ArrayList<Bus>();
    static void checkAccess() {
        while (true) {
            System.out.println("Enter Your Role");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            int role = sc.nextInt();
            switch (role) {
                case 1:
                    if (verifyAccess()) {
                        System.out.println("Access Granted");
                        admin();
                    } else {
                        System.out.println("Access Denied");
                        System.out.println("Please Try Again");
                    }
                    break;
                case 2:
                    user();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice. Try Again.");
                    break;
            }
        }
    }

    @SuppressWarnings("resource")
    static boolean verifyAccess() {
        System.out.println("Enter Username");
        String username = sc.next();
        System.out.println("Enter Password");
        String password = sc.next();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("DB/admin.txt"));
            String line = reader.readLine();
            while (line != null) {
                String words[] = line.split(" ");
                if (username.equals(words[0]) && password.equals(words[1])) {
                    return true;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    static void admin() {
        while (true) {
            System.out.println("Admin Functionalities");
            System.out.println("1. Add Bus");
            System.out.println("2. View Bus");
            System.out.println("3. Go to Dashboard");
            System.out.println("4. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    createBus();
                    break;
                case 2:
                    viewBus();
                    break;
                case 3:
                    checkAccess();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice. Try Again.");
                    break;
            }
            System.out.println();
        }
    }

    static void createBus() {
        System.out.println("Creating Busses");
        while (true) {
            System.out.println("Enter Bus Name (-1 to exit)");
            String name = sc.next();
            if (name.equals("-1")) {
                break;
            }
            System.out.println("Enter Starting Point");
            String start = sc.next();
            System.out.println("Enter Destination Point");
            String dest = sc.next();
            List<String> routeList = new ArrayList<>();
            System.out.println("Enter Route (Include start and destination)");
            while (true) {
                String route = sc.next();
                routeList.add(route);
                if (route.equals(dest)) {
                    break;
                }
            }
            System.out.println("Enter Seat Capacity");
            int seatCapacity = sc.nextInt();
            Bus bus = new Bus(name, start, dest, routeList, seatCapacity);
            Busses.add(bus);
        }
    }

    static void viewBus() {
        System.out.println("Viewing Busses");
        try {
            for (Bus bus : Busses) {
                System.out.println("Bus Name: " + bus.getName());
                System.out.println("Starting Point: " + bus.getStarting());
                System.out.println("Destination: " + bus.getDestination());
                System.out.println("Route: " + bus.getRouteList());
                System.out.println("Seat Capacity: " + bus.getSeatCapacity());
            }
        } catch (Exception e) {
            System.out.println("No Busses Are Available To Show");
        }
    }

    static void user() {
        while (true) {
            System.out.println("User Functionalities");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Go to Dashboard");
            System.out.println("4. Check All Available Seats");
            System.out.println("5. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    bookTicket();
                    break;
                case 2:
                    cancelTicket();
                    break;
                case 3:
                    checkAccess();
                    break;
                case 4:
                    showBusses();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice. Try Again.");
                    break;
            }
            System.out.println();
        }
    }

    static void bookTicket() {
        System.out.println("Enter Starting Point");
        String start = sc.next();
        System.out.println("Enter Destination");
        String dest = sc.next();
        showRelevantBusses(start, dest);
        System.out.println("Enter Bus Choice");
        String busName = sc.next();
        System.out.println("Enter Seat Row Number");
        int seatRowNumber = sc.nextInt();
        System.out.println("Enter Seat Column Number");
        int seatColNumber = sc.nextInt();
        if (bookSeat(busName, seatRowNumber, seatColNumber)) {
            System.out.println("Seat Booked successfully");
        } else {
            System.out.println("Seat Booking Failed. Try Again.");
        }
    }

    static void cancelTicket() {
        System.out.println("Enter Bus Name");
        String busName = sc.next();
        System.out.println("Enter Seat Row Number");
        int seatRowNumber = sc.nextInt();
        System.out.println("Enter Seat Column Number");
        int seatColNumber = sc.nextInt();
        if (cancelSeat(busName, seatRowNumber, seatColNumber)) {
            System.out.println("Seat Cancelled successfully");
        } else {
            System.out.println("Seat Cancellation Failed. Try Again.");
        }
    }

    static boolean bookSeat(String name, int seatRowNumber, int seatColNumber) {
        for (Bus bus : Busses) {
            if (bus.getName().equals(name)) {
                int seats[][] = bus.getSeats();
                if(seatRowNumber <= -1 || seatColNumber <= -1 || seatRowNumber >= seats.length || seatColNumber >= seats[0].length){
                    System.out.println("Invalid Seat Number");
                    return false;
                }
                if (seats[seatRowNumber - 1][seatColNumber - 1] == 0) {
                    seats[seatRowNumber - 1][seatColNumber - 1] = 1;
                    return true;
                } else {
                    System.out.println("Seat Already Booked");
                    return false;
                }
            }
        }
        System.out.println("Error in Booking ticket, Cannot find Bus with name "+ name);
        return false;
    }

    static boolean cancelSeat(String name, int seatRowNumber, int seatColNumber) {
        for (Bus bus : Busses) {
            if (bus.getName().equals(name)) {
                int seats[][] = bus.getSeats();
                if(seatRowNumber <= -1 || seatColNumber <= -1 || seatRowNumber >= seats.length || seatColNumber >= seats[0].length){
                    System.out.println("Invalid Seat Number");
                    return false;
                }
                if (seats[seatRowNumber - 1][seatColNumber - 1] == 1) {
                    seats[seatRowNumber - 1][seatColNumber - 1] = 0;
                    return true;
                } else {
                    System.out.println("Seat Not Booked");
                    return false;
                }
            }
        }
        System.out.println("Error in Booking ticket, Cannot find Bus with name "+ name);
        return false;
    }

    static void showBusses() {
        try {
            for (Bus bus : Busses) {
                System.out.println("Bus Name: " + bus.getName());
                System.out.println("Starting Point: " + bus.getStarting());
                System.out.println("Destination: " + bus.getDestination());
                System.out.println("Route: " + bus.getRouteList());
                int seats[][] = bus.getSeats();
                for (int[] row : seats) {
                    for (int seat : row) {
                        System.out.print(seat + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("No Busses Are Available To Show");
        }
    }

    static void showRelevantBusses(String start, String dest) {
        for (Bus bus : Busses) {
            if (bus.getStarting().equals(start) && (bus.getDestination().equals(dest) || bus.getRouteList().contains(dest))) {
                try {
                    System.out.println("Bus Name: " + bus.getName());
                    System.out.println("Starting Point: " + bus.getStarting());
                    System.out.println("Destination: " + bus.getDestination());
                    System.out.println("Route: " + bus.getRouteList());
                    int seats[][] = bus.getSeats();
                    for (int[] row : seats) {
                        for (int seat : row) {
                            System.out.print(seat + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } catch (Exception e) {
                    System.out.println("No Busses Are Available To Show");
                }
            }
        }
    }
    public void callCheckAccess(){
        checkAccess();
    }
}
