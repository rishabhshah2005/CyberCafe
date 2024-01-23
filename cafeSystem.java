import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

class Session{
    LocalDateTime start, end;
    static int sessionID;
    int id;
    long st, dur;
    String status, duration, cust_name;

    DateTimeFormatter formatted_time = DateTimeFormatter.ofPattern("DD/MM/YY | h:m:s a");

    public Session(){
        start = LocalDateTime.now();
        st = System.currentTimeMillis();
        sessionID +=1;
        id = sessionID;
        status = "active";
        System.out.println(start.format(formatted_time));
    }

    public void end_session(){
        end = LocalDateTime.now();
        status="inactive";
        dur = System.currentTimeMillis()-st;
        long min = dur/(1000*60);
        long sec = (dur%(1000*60))/1000;
        duration = min+" min "+sec+" seconds";
    }

}

public class cafeSystem {
    public static void main(String[] args) {
        Session s = new Session();
        System.out.println(s.start);
        // try {
        //     Thread.sleep(65000);
        // } catch (Exception e) {
        //     System.out.println(e);
        // }
        
        s.end_session();
        System.out.println(s.duration);
    }
}