/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.Function;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.IOException;

/**
 * 
 *           stmt = c.createStatement();
 *           String sql = "CREATE TABLE COMPANY " +
 *                        "(ID INT PRIMARY KEY     NOT NULL," +
 *                        " NAME           TEXT    NOT NULL, " + 
 *                        " AGE            INT     NOT NULL, " + 
 *                        " ADDRESS        CHAR(50), " + 
 *                        " SALARY         REAL)"; 
 *           stmt.executeUpdate(sql);
 *           
 *           stmt = c.createStatement();
 *           String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
 *                        "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
 *           stmt.executeUpdate(sql);
 *
 *           sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
 *                 "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
 *           stmt.executeUpdate(sql);
 *
 *           sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
 *                 "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
 *           stmt.executeUpdate(sql);
 *
 *           sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
 *                 "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
 *           stmt.executeUpdate(sql);
 *
 *           stmt = c.createStatement();
 *           
 *           ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
 *           while ( rs.next() ) {
 *              int id = rs.getInt("id");
 *              String  name = rs.getString("name");
 *              int age  = rs.getInt("age");
 *              String  address = rs.getString("address");
 *              float salary = rs.getFloat("salary");
 *              System.out.println( "ID = " + id );
 *              System.out.println( "NAME = " + name );
 *              System.out.println( "AGE = " + age );
 *              System.out.println( "ADDRESS = " + address );
 *              System.out.println( "SALARY = " + salary );
 *              System.out.println();
 *           }
 */

/**
 *
 * @author Kenystev
 */
public class JavaApplication3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:testApplication2.db");

            System.out.println("Opened database successfully");
            
            //Declare Functions
            Function.create(c, "ping", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    try {
                        String url = value_text(0);
                        new Socket().connect(new InetSocketAddress(url, 80), 5000);
                        result(1);
                    }
                    catch(IOException ex) {
                        result(0);
                    }
                }
            });
            
            Function.create(c, "PMT", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    double tasa = value_double(0);
                    int n_periodos = value_int(1);
                    double prestamo = value_double(2);
                    
                    result((prestamo*tasa) / (1 - Math.pow((1 + tasa),(-n_periodos))));
                }
            });
            
            Function.create(c, "BIN2DEC", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    String binary = value_text(0);
                    result(Integer.parseInt(binary, 2));
                }
            });
            
            Function.create(c, "DEC2BIN", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    int number = value_int(0);
                    result(Integer.toBinaryString(number));
                }
            });
            
            Function.create(c, "C2F", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    double celsius = value_double(0);
                    result((9*celsius/5) + 32);
                }
            });
            
            Function.create(c, "F2C", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    double fahrenheit = value_double(0);
                    result(((fahrenheit - 32)*5)/9);
                }
            });
            
            Function.create(c, "Factorial", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    int number = value_int(0);
                    int ret = 1;
                    for(int i = 1; i <= number; i++)
                        ret *= i;
                    result(ret);
                }
            });
            
            Function.create(c, "DEC2HEX", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    int number = value_int(0);
                    result(Integer.toHexString(number));
                }
            });
            
            Function.create(c, "HEX2DEC", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    String hexa = value_text(0);
                    result(Integer.toHexString(Integer.parseInt(hexa, 16)));
                }
            });
            
            Function.create(c, "CompareString", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    String str1 = value_text(0);
                    String str2 = value_text(1);
                    result(str1.compareTo(str2));
                }
            });
            
            Function.create(c, "Trim", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    String str = value_text(0);
                    char caracter = value_text(1).charAt(0);
                    
                    String temp = "";
                    String ret = "";
                    Boolean check = true;
                    
                    for(int i = 0; i < str.length(); i++){
                    if(check && str.charAt(i) == caracter)
                        continue;
                        temp += str.charAt(i);
                        check = false;
                    }
                    ret = temp;

                    for(int i = temp.length() -1; i >= 0; i--){
                        if(temp.charAt(i) == caracter){
                            ret = ret.substring(0, i);
                            continue;
                        }
                        break;
                    }
                    result(ret);
                }
            });
            
            Function.create(c, "Repeat", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    String repetir = value_text(0);
                    int cantidad = value_int(1);
                    String ret = "";
                    for(int i = 0; i < cantidad; i++)
                        ret += repetir;
                    result(ret);
                }
            });
            
            //Call Functions
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Trim('aaaaHolaaaaaaa','a') as PING;" );
            while ( rs.next() ) {
                String result = rs.getString("PING");
                System.out.println("result: "+result);
            }
            
            stmt.close();
            c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() + " -> " + e.getStackTrace());
          System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
    
}
