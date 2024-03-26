package org.bearluxury;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*Demo of some basic sql functions using jdbc for reservation. Doesn't have all of reservation included
it is just a demo to show how it works. It is not connected to a group port yet it is only connected to mine.
Will connect later. Only modify if watching the videos that I sent. Jar file is not included yet I will set up
with maven later
 */
public class JBDCDemo {
    public Connection connect_to_db(String dbname, String user, String pass){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" +dbname, user, pass);
            if(conn != null){
                System.out.println("Connection Established");
            }
            else{
                System.out.println("Connection Failed");
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return conn;

    }

    public void createTable(Connection conn, String tableName){
        Statement statement;
        try{
            String query = "create table " + tableName + "(empid SERIAL, name varchar(200), address varchar(200), primary key(empid));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Created");

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void insert_row(Connection conn, String table_name, String name, String address){
        Statement statement;
        try{
            String query = String.format("insert into %s(name, address) values('%s', '%s');", table_name, name, address );
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row inserted");

        }catch(Exception e){
            System.out.println(e);

        }
    }

    public void read_data(Connection conn, String table_name){
        Statement statement;
        ResultSet rs = null;
        try {
            String query = String.format("select * from %s", table_name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.print(rs.getString("empid") + " ");
                System.out.print(rs.getString("name") + " ");
                System.out.println(rs.getString("Address") + " ");

            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void update_name(Connection conn, String table_name, String old_name, String new_name){
        Statement statement;
        try{
            String query = String.format("update %s set name ='%s' where name='%s'", table_name, new_name, old_name);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data Updated");
        }
        catch(Exception e){
            System.out.println(e);

        }
    }

    public void search_by_name(Connection conn, String table_name, String name){
        Statement statement;
        ResultSet rs = null;
        try{
            String query = String.format("select * from %s where name= '%s", table_name, name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()){
                System.out.print(rs.getString("empid"));
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        JBDCDemo db = new JBDCDemo();
        Connection conn = db.connect_to_db("Reservations", "postgres", "Ninja123!");
        //db.createTable(conn, "Reservation");
        //db.insert_row(conn, "Reservation", "Derek", "3025 Jackson Drive");
        //db.insert_row(conn, "Reservation", "Harrison", "4300 Myers Lane");
        db.update_name(conn, "Reservation", "Derek", "Kirby");
        db.read_data(conn, "Reservation" );


    }
}


