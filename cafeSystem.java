import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class Session{
    LocalDateTime start, end;
    static int sessionID;
    int id;
    long st, dur;
    String status, duration, cust_name;

    DateTimeFormatter formatted_time = DateTimeFormatter.ofPattern("DD/MM/YY | h:m:s a");

    public void startSession(){
        start = LocalDateTime.now();
        st = System.currentTimeMillis();
        sessionID +=1;
        id = sessionID;
        status = "active";
        // System.out.println(start.format(formatted_time));
    }

    public void end_session(){
        end = LocalDateTime.now();
        status="inactive";
        dur = System.currentTimeMillis()-st;
        long min = dur/(1000*60);
        long sec = (dur%(1000*60))/1000;
        duration = min+" min "+sec+" seconds";
    }
    
    void printSessInfo(){
        System.out.println("ID: "+id);
        System.out.println("Duration: "+duration);
    }

    void setName(String name){
        cust_name = name;    }
}

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

    void usePc(){
        if (curr_sess>=20){
            System.out.println("No more sessions left");
        }
        else{
            status = "active";
            sessions[curr_sess] = new Session();
            sessions[curr_sess].startSession();
        }
    }
    
    void releasePc(){
        status = "inactive";
        sessions[curr_sess].end_session();
        curr_sess += 1;
    }

    void printDetails(){
        System.out.println("Status: "+status);
        System.out.println("ID: "+id);
    }
}

public class cafeSystem {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        Computer pc = new Computer();
        while (true){
            pc.usePc();
            inp.nextLine();
            pc.releasePc();
            pc.sessions[pc.curr_sess-1].printSessInfo();
        }
    }
}