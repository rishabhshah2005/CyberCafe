import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Session class: handles sessions 
class Session {
    LocalDateTime start, end;
    static int sessionID;
    int id;
    long st, dur;
    String duration, cust_name;

    DateTimeFormatter formatted_time = DateTimeFormatter.ofPattern("dd/MM/YY | hh:mm:ss a");

    // this command will start a new session. It will note the start time and create a new seesion ID
    public void startSession() {
        start = LocalDateTime.now();
        st = System.currentTimeMillis();
        sessionID += 1;
        id = sessionID;
        System.out.println("Session started at: " + start.format(formatted_time));
    }

    // This command will end the session, notes the ending time and calculates duration of session based on it
    public void end_session() {
        end = LocalDateTime.now();
        dur = System.currentTimeMillis() - st;
        long min = dur / (1000 * 60);
        long sec = (dur % (1000 * 60)) / 1000;
        duration = min + " min " + sec + " seconds";
    }

    // prints session information
    public void printSessInfo(int pc_id) {
        System.out.println("Session ID: " + id);
        System.out.println("PC ID: " + pc_id);
        System.out.println("Customer: " + cust_name);
        System.out.println("Duration: " + duration);
        System.out.println("Session Started at: " + start.format(formatted_time));
        if (end != null) {
            System.out.println("Session ended at: " + end.format(formatted_time));
        } else {
            System.out.println("Session still running");
        }
    }

    // just a simple function to se customer name
    public void setName(String name) {
        cust_name = name;
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// Computer Class
class Computer {
    static int id_cnt;
    int id;
    int curr_sess;
    String status = "inactive";

    // This class has a array "sessions" which stores the session info of that particular computer
    Session[] sessions = new Session[20];

    // This constructor gives the each computer a unique ID using a static variable each time a computer object is created
    public Computer() {
        id_cnt++;
        id = id_cnt;
    }

    // This function starts a session on a computer, it takes the customer's name as a argument.
    // It also adds a session object to the 'sessions' array and starts the session and marks the current computer as occupied
    public void usePc(String name) {
        if (curr_sess >= 20) {
            System.out.println("No more sessions left");
        } else {
            status = "active";
            sessions[curr_sess] = new Session();
            sessions[curr_sess].startSession();
            sessions[curr_sess].cust_name = name;
        }
    }

    // This functions releases a PC. It marks it unactive and ends the current running session.
    public void releasePc() {
        status = "inactive";
        sessions[curr_sess].end_session();
        System.out.println("Session ended for PC-" + id);
        curr_sess += 1;
    }

}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// Cyber Cafe class: The main Cafe class
class CyberCafe {
    final int no_of_comps = 30;

    // This class has a array of 30 computers 
    Computer[] computers = new Computer[30];

    // Just a function to create 30 computer objects in the array
    public void makeComputerObjs() {
        for (int i = 0; i < computers.length; i++) {
            computers[i] = new Computer();
        }
    }

    // Shows the PC id's of the PCs that have status set to Inactive
    public void showAvailablePCs() {
        System.out.println("Following PCs are available");
        System.out.print("| ");
        for (Computer computer : computers) {
            if (computer.status.equals("inactive")) {
                System.out.print(computer.id + " | ");
            }
        }
        System.out.println();
    }

    // Shows the PC's that are Acive(currently being used)
    public void showActivePCs() {
        System.out.println("Following PCs are in use");
        System.out.print("| ");

        for (Computer computer : computers) {
            if (computer.status.equals("active")) {
                System.out.print(computer.id + " | ");
            }
        }
        System.out.println();
    }

    // This function takes information about the PC a customer wants to use. And Starts a session on it
    public void startUsing(Scanner inp) {
        String name;
        inp.nextLine();
        System.out.print("Enter Name: ");
        name = inp.nextLine();
        System.out.print("Enter the ID of the PC you want to use: ");
        int pc_id = inp.nextInt();

        if (pc_id > 30) {
            System.out.println("Computer doesnt exist! Try again");
            startUsing(inp);
        }

        else if (computers[pc_id - 1].status.equals("active")) {
            System.out.println("That PC is taken. Try again!");
            startUsing(inp);
        }

        else {
            computers[pc_id - 1].usePc(name);
        }
    }

    // Ends a session. It takes ID of the computer whose session we want to end
    public void endSession(Scanner inp) {
        System.out.print("Enter the ID of the PC you want to end session of: ");
        int pc_id = inp.nextInt();

        if (pc_id > 30) {
            System.out.println("Computer doesnt exist! Try again");
            endSession(inp);
        }

        else if (computers[pc_id - 1].status.equals("inactive")) {
            System.out.println("That PC has no session running currently");
            endSession(inp);
        }

        else {
            computers[pc_id - 1].releasePc();
        }

    }

    // Prints the session info/history of a particular PC. It takes the PC id.
    public void computerHistory(Scanner inp) {
        System.out.print("Enter the id of PC you want to check history of: ");
        int pc_id = inp.nextInt();
        if (pc_id > 30) {
            System.out.println("Computer doesnt exist! Try again");
            computerHistory(inp);
        }
        Session[] arr = computers[pc_id - 1].sessions;
        if (arr[0] == null) {
            System.out.println("No usage history");
        } else {

            for (Session session : arr) {
                if (session == null) {
                    continue;
                }

                else {
                    System.out.println("#############################");
                    System.out.println();
                    session.printSessInfo(pc_id);
                    System.out.println();
                }
            }
        }
        System.out.println("#############################");
    }

    // This function searches all the session in each computer to check if the specified customer has used the computer or not
    // It prints out session info all the occurences. It takes the customer name, converts it into lowercase and compares it against
    // cust_name variable in each session object.
    void searchCustomer(Scanner inp) {
        System.out.print("Enter customer name: ");
        inp.nextLine();
        String cust = inp.nextLine();
        cust = cust.toLowerCase();
        boolean found = false;
        for (Computer computer : computers) {
            for (Session session : computer.sessions) {
                if (session == null) {
                    continue;
                }
                String s = session.cust_name.toLowerCase();
                if (s.equals(cust)) {
                    found = true;
                    System.out.println("#############################");
                    session.printSessInfo(computer.id);
                    System.out.println("#############################");
                    System.out.println();
                }

                else {
                    continue;
                }
            }
        }
        if (!found) {
            System.out.println("No such Customer found.");
        }
    }

    // prints out the help menu
    public void help() {
        System.out.println("1. Start a new session.");
        System.out.println("2. End a session.");
        System.out.println("3. Show Available PCs");
        System.out.println("4. Show Occupied PCs");
        System.out.println("5. Display Usage History");
        System.out.println("6. Search Usage for a specific customer");
        System.out.println("7. Display index menu");
        System.out.println("8. Exit");
    }

    // Main function that runs the system. It make this system a menu driven proogra.
    public void run(Scanner inp) {
        help();
        makeComputerObjs();
        while (true) {
            System.out.print("Enter index: ");
            int index = inp.nextInt();

            switch (index) {
                case 1:
                    startUsing(inp);
                    break;
                case 2:
                    endSession(inp);
                    break;
                case 3:
                    showAvailablePCs();
                    ;
                    break;
                case 4:
                    showActivePCs();
                    break;
                case 5:
                    computerHistory(inp);
                    break;
                case 6:
                    searchCustomer(inp);
                    break;
                case 7:
                    help();
                    break;
                case 8:
                    inp.close();
                    System.exit(0);

                default:
                    System.out.println("Enter correct index");
            }
        }
    }

}


public class cafeSystem {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);

        CyberCafe cafe = new CyberCafe();
        cafe.run(inp);
    }
}