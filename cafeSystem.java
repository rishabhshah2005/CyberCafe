import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class Session{
    LocalDateTime start, end;
    static int sessionID;
    int id;
    long st, dur;
    String duration, cust_name;

    DateTimeFormatter formatted_time = DateTimeFormatter.ofPattern("DD/MM/YY | hh:mm:ss a");

    public void startSession(){
        start = LocalDateTime.now();
        st = System.currentTimeMillis();
        sessionID +=1;
        id = sessionID;
        System.out.println("Session started at: "+start.format(formatted_time));
    }

    public void end_session(){
        end = LocalDateTime.now();
        dur = System.currentTimeMillis()-st;
        long min = dur/(1000*60);
        long sec = (dur%(1000*60))/1000;
        duration = min+" min "+sec+" seconds";
    }
    
    void printSessInfo(){
        System.out.println("ID: "+id);
        System.out.println("Customer: "+cust_name);
        System.out.println("Duration: "+duration);
        System.out.println("Session ended at: "+end.format(formatted_time));
    }

    void setName(String name){
        cust_name = name;
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Computer{
    static int id_cnt;
    int id;
    int curr_sess;
    String status="inactive";

    Session[] sessions = new Session[20];

    Computer(){
        id_cnt++;
        id = id_cnt;
    }

    void usePc(String name){
        if (curr_sess>=20){
            System.out.println("No more sessions left");
        }
        else{
            status = "active";
            sessions[curr_sess] = new Session();
            sessions[curr_sess].startSession();
            sessions[curr_sess].cust_name = name;
        }
    }
    
    void releasePc(){
        status = "inactive";
        sessions[curr_sess].end_session();
        sessions[curr_sess].printSessInfo();
        curr_sess += 1;
    }

    void printDetails(){
        System.out.println("Status: "+status);
        System.out.println("ID: "+id);
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
class CyberCafe{
    final int no_of_comps = 30;
    Computer[] computers = new Computer[30];

    void makeComputerObjs(){
        for (int i=0; i < computers.length; i++) {
            computers[i] = new Computer();
        }
    }

    // Shows the PC id's of the PCs that have status set to Inactive
    void showAvailablePCs(){
        System.out.println("Following PCs are available");
        System.out.print("| ");
        for (Computer computer : computers) {
            if(computer.status.equals("inactive")){
                System.out.print(computer.id+" | ");
            }
        }
        System.out.println();
    }

    void showActivePCs(){
        System.out.println("Following PCs are in use");
        System.out.print("| ");

        for (Computer computer : computers) {
            if(computer.status.equals("active")){
                System.out.print(computer.id+" | ");
            }
        }
        System.out.println();
    }

    void startUsing(Scanner inp){
        String name;
        inp.nextLine();
        System.out.print("Enter Name: ");
        name = inp.nextLine();
        System.out.print("Enter the ID of the PC you want to use: ");
        int pc_id = inp.nextInt();

        if (pc_id>30) {
            System.out.println("Computer doesnt exist! Try again");
            startUsing(inp);
        }

        else if(computers[pc_id-1].status.equals("active")){
            System.out.println("That PC is taken. Try again!");
            startUsing(inp);
        }

        else{
            computers[pc_id-1].usePc(name);
        }
    }

    void endSession(Scanner inp){
        System.out.print("Enter the ID of the PC you want to end session of: ");
        int pc_id = inp.nextInt();

        if (pc_id>30) {
            System.out.println("Computer doesnt exist! Try again");
            endSession(inp);
        }

        else if(computers[pc_id-1].status.equals("inactive")){
            System.out.println("That PC has no session running currently");
            endSession(inp);
        }

        else{
            computers[pc_id-1].releasePc();
        }

    }

}

public class cafeSystem {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        
        CyberCafe cafe = new CyberCafe();
        cafe.makeComputerObjs();
        
        while (true) {
            int x = inp.nextInt();
            if (x==1) {
                cafe.showAvailablePCs();
                cafe.showActivePCs();
            }
            else if (x==2){
                cafe.startUsing(inp);
            }
            else{
                cafe.endSession(inp);
            }
        }
        
    }
}