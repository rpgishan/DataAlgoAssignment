/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataalgoassignment;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gishan-mac
 */
public class OrderConfirmation {
    //Queue array based implementation

    private int front, rear, nItems, maxSize, remove;
    private static int[] array;

    static Connection con;
    static Statement st;
    static String db = "tlmpdb", un = "un", pw = "pw";

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {

        ResultSet rs1 = getConnection("SELECT COUNT(OrderNo) FROM OrderToBeConfirmed");
        ResultSet rs2 = getConnection("SELECT OrderNo FROM OrderToBeConfirmed");

        int orders = 0;
        if (rs1.next()) {
            //Get no of orders to be confirmed
            orders = rs1.getInt(1);
        }
        
        OrderConfirmation oc = new OrderConfirmation(orders);
        
        //Insert orders to the queue
        while (rs2.next()) {
            oc.insert(rs2.getInt(1));
        }
        st.close();
        con.close();
        
        //Retrive order numbers from the queue
            System.out.println("Order numbers of orders to be confrimed in ascending order");
        while(!oc.isEmpty()){
            System.out.println(oc.remove());
        }
    }

    public OrderConfirmation(int s) {
        maxSize = s;
        array = new int[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }

    public void insert(int j) {
        if (isFull() == false) {
            array[++rear] = j;
        }
        nItems++;
    }

    public int remove() {
        if (isEmpty() == false) {
            remove = array[front++];
            nItems--;
        }
        return remove;
    }

    public int peekfront() {
        return array[front];
    }

    public boolean isEmpty() {
        return (nItems == 0);
    }

    public boolean isFull() {
        return (nItems == maxSize);
    }

    public int size() {
        return nItems;
    }

    static Connection dbConnection() throws SQLException {
        return (Connection) DriverManager.getConnection("jdbc:mysql://localhost/" + db, un, pw);

    }

    static ResultSet getConnection(String quary) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/" + db, un, pw);
        st = (Statement) con.createStatement();
        return st.executeQuery(quary);
    }

}
