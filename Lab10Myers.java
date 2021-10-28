import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.util.ArrayList;

public class Lab10Myers{

    private Queries queries;
    private ConsoleInterface ci; 
    private static Lab10Myers lab10; 
    private Connection con; 
    private int mid = 0;
 

    public static void main(String[] args) {
        lab10 = new Lab10Myers();
    }
     
    public Lab10Myers(){
        queries = new Queries();
        ci = new ConsoleInterface();
        run();
        memberID();
    }
    public void run(){ 

        try {
            // Register the JDBC driver for MySQL.
            Class.forName("com.mysql.jdbc.Driver");
            String url ="jdbc:mysql://faure/etham";
            con = DriverManager.getConnection(url,"etham", "831927272");  
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Error");
        }

    }

    public void memberID(){ 
        mid = ci.MemberIDInterface();
        int isMember = queries.memberSearch(mid);
        int maxid=queries.newID(); 

        switch (isMember){
            case 1:
                startBooksearch();
                break;

            case 0:
                System.out.println("YEE");
                addMember(); 
        }
    }

    private void startBooksearch(){
        String searchFlag = ci.bookSearchInterface();
        if(searchFlag.equals("ISBN")){
            String isbn = ci.seachByISBNInterface();
            if(!isbn.isEmpty()){
                queries.searchByISBN(isbn);
            }

        }else if(searchFlag.equals("Name")){
            
        }else if(searchFlag.equals("Author")){

        }else{
            System.exit(0);
        }
    }

    private void addMember(){
        int newid=0;
        Member m = ci.addMemberInterface();
        //System.out.println(m.toString());
        newid = queries.newID();
        m.setMemberID(newid);
        queries.addMember(m);
        System.out.println("Member Added!\n" + m.toString());
        memberID();
        //ci.bookSearchInterface();

    }
    //class for member table
    public static class Member{
        String first=""; 
        String last="";
        String dob=""; 
        int mid; 
        public Member(String first,String last,String dob, int mid){
            this.first=first;
            this.last=last;
            this.dob=dob;
            this.mid=mid;
        };
        public Member(){
        };
        public String getLast(){return last;}
        public String getFirst(){return first;}
        public String getDOB(){return dob;}
        public int getMemberID(){return mid;}
        public void setMemberID(int mid){this.mid = mid;}
        @Override
        public String toString(){
            return "Member:"+
                    "\nFirst name: " + first + 
                    "\nLast name: " + last + 
                    "\nMemberID: " + mid;
        }
    }

    public class ConsoleInterface{
        Scanner sc = new Scanner(System.in);

        public String seachByISBNInterface(){
            String isbn="";
            System.out.println("======================================");
            System.out.println("Enter book ISBN: ");
            isbn = sc.next();
            return isbn;
        }

        public int MemberIDInterface(){
            System.out.println("\n======================================");
            System.out.println("Lab10 Console Interface\nEnter 666 to exit program ");
            System.out.println("\nEnter MemberID: ");
             mid = sc.nextInt();
            //System.out.println("\n======================================");
           
            if(mid==666){
                System.exit(0);
            }          
            return mid;
        }
        public String bookSearchInterface(){
            System.out.println("======================================");
            System.out.println("Please type a search option\n");
            System.out.println("Seach Options: \"ISBN\" \"Name\" \"Author\" ");
            String option = sc.next();
            return option;
        }

        public Member addMemberInterface(){
          
            //System.out.println("Enter \"Lab10 exit anytime\" to exit the program");
            System.out.println("======================================");

            System.out.println("Enter first name: ");
            String first = sc.next();

            if(first.equals("Lab10 exit")){
                System.out.println("Exiting program: Lab10Myers.java");
                System.out.println("Exiting");
                System.exit(0);
            }

            System.out.println("Enter last name: ");
            String last = sc.next();
            if(last.equals("Lab10 exit")){
                System.out.println("Exiting program: Lab10Myers.java");
                System.exit(0);
            }

            System.out.println("Enter dob in xx/xx/xxx format: ");
            String dob = sc.next();
            if(dob.equals("Lab10 exit")){
                System.out.println("Exiting program: Lab10Myers.java");
                System.exit(0);
            }

            if(first.isEmpty() || last.isEmpty() || dob.isEmpty()){
            System.out.println("Empty value in a field");
            System.out.println("Enter 1 re-enter or 0 to exit the program");
            int ex = sc.nextInt();           
            //need to restart? 
                if(ex==1){
                    addMemberInterface();
                }
               

            }else{
                System.out.println("======================================");
            }
                        
            Member m = new Member();
            m = new Member(first,last,dob,0);
            return m;
        }
   
    }
    public class Queries{

        
        private void searchByISBN(String isbn){
            //check if bok exists 
            //check if it ahs copeis to be out 
            int copiesAvailable=0;
            ResultSet rs;
            String title="";
            ArrayList<String> libraries = new ArrayList<String>(); 
            ArrayList<Integer> shelfs = new ArrayList<Integer>(); 
         
            String output="";
            try{            
                PreparedStatement stmt = con.prepareStatement("Select * from Book Where ISBN=?");  
                stmt.setString(1, isbn);
                rs = stmt.executeQuery();
                //check if book is not in library (e)
                if(!rs.next()){
                    System.out.println("Book is not in stock at either library");
                    memberID();
                }else{ //check for copies 
                  
                    stmt = con.prepareStatement("Select CopiesNotOut from LocatedAt where ISBN=? GROUP BY ISBN"); 
                    stmt.setString(1, isbn);
                    rs = stmt.executeQuery();
                    if(rs.next()){copiesAvailable = rs.getInt("CopiesNotOut");}
                    //System.out.println(copiesAvailable);
                    if(copiesAvailable==0){
                        System.out.println("No Copies Available");
                        System.out.println("======================================");
                        memberID();
                    }else{
                    // while(rs.next()){
                    //     copiesAvailable=rs.getInt("CopiesNotOut");
                    // }
                    // rs = stmt.executeQuery("Select Title From Book where ISBN=?");
                    // stmt.setString(1, isbn);
                    // if(rs.next()){
                    //     title = rs.getString("Title");
                    // }
                    stmt = con.prepareStatement("Select Name From LocatedAt where ISBN=?");
                    stmt.setString(1, isbn);
                    rs = stmt.executeQuery();
                    while(rs.next()){
                        libraries.add(rs.getString("Name"));
                    }
                    stmt = con.prepareStatement("Select Title From Book where ISBN=?");
                    stmt.setString(1, isbn);
                    rs = stmt.executeQuery();
                    while(rs.next()){
                        title = rs.getString("Title");
                    }
                    //System.out.println(libraries.toString());
                    stmt = con.prepareStatement("Select Shelf From LocatedAt where ISBN=?");
                    stmt.setString(1, isbn);
                    rs = stmt.executeQuery();
                    while(rs.next()){
                        shelfs.add(rs.getInt("Shelf"));
                    }
                    System.out.println("The Book \""+title+"\" is aviable from libary(s): "+libraries.toString()+" " +" on shelfs: "+shelfs.toString());
                }  
                } 
            }catch (Exception e){
                System.out.println(e.getMessage());              
            }  

            // try{            
            //     PreparedStatement stmt = con.prepareStatement("Select * from Book Where ISBN=?");  
            //     stmt.setInt(1, isbn);
            //     ResultSet rs = stmt.executeQuery();   
            // }catch (Exception e){
            //     System.out.println(e.getMessage());              
            // }             
            
        }
            
        private int addMember(Member m){
             try{            
                PreparedStatement stmt = con.prepareStatement("Insert into Member Values(?,?,?,?)");  
                stmt.setInt(1, m.getMemberID());
                stmt.setString(2, m.getLast());
                stmt.setString(3, m.getFirst());
                stmt.setString(4, m.getDOB());
                stmt.executeUpdate(); 
               
            }catch (Exception e){
                System.out.println(e.getMessage());    
            }
            
            return 0;
        }

        private int memberSearch(int mid){
            try{            
                PreparedStatement stmt = con.prepareStatement("Select MemberID from Member Where MemberID = ?");  
                stmt.setInt(1, mid);
                
                ResultSet rs =  stmt.executeQuery(); 
                if(!rs.next()){
                    return 0;
                }else{
                    return 1;
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                
            }
            return 0;
        }

        private int newID(){
            int newid = 0;
            try{            
                PreparedStatement stmt = con.prepareStatement("Select MAX(MemberID) from Member");    
                ResultSet rs =  stmt.executeQuery(); 
                    if(!rs.next()){
                        return 0;
                    }else{
                        newid = rs.getInt("MAX(MemberID)") + 1;
                    }
                }catch (Exception e){
                System.out.println(e.getMessage());
                
                }
            return newid;
        }

        }

            
    }

