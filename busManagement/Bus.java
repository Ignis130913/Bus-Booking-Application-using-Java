package busManagement;

import java.util.List;

public class Bus{
    int seatCapacity;
    int spr = 5;
    String name, start, dest;
    List<String> routeList;
    int seats [][];
    public Bus(String name, String start, String dest, List<String> routeList, int seatCapacity){
        this.name = name;
        this.start = start;
        this.dest = dest;
        this.routeList = routeList;
        this.seatCapacity = seatCapacity;
        createSeats();
    }
    public void createSeats(){
        seats = new int[seatCapacity / spr][spr];
    }
    public String getName(){
        return name;
    }
    public String getStarting(){
        return start;
    }
    public String getDestination(){
        return dest;
    }
    public List<String> getRouteList(){
        return routeList;
    }
    public int getSeatCapacity(){
        return seatCapacity;
    }
    public int[][] getSeats(){
        return seats;
    }
}
