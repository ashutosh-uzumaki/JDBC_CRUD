package org.ashutosh;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private static  final String url = "jdbc:mysql://localhost:3306/test";
    private static final String userName = "root";
    private static final String password = "root";
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        try{
            Connection connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Connected to DB!!!");
            insertToDB(connection, "Pritha", "984321746", "prithavasudev@gmail.com");
            insertToDB(connection, "Virat", "1234567890", "virat.kohli@gmail.com");

            try{
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM customer");
                while(result.next()){
                    System.out.println("----------------------------------------------------------------");
                    System.out.print("Customer data =====>> ");
                    System.out.println("Id "+result.getInt("id")+" Name "+result.getString("name")+" Phone Number "+result.getString("phone_number")
                            +" Email Address "+result.getString("email_id"));
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }

        }catch(SQLException e){
            System.out.println("Failed to connect to DB!!!!");
        }
    }

    private static void insertToDB(Connection connection, String name, String phone_number, String email_id){
        String insertQuery = "INSERT INTO CUSTOMER (name, phone_number, email_id) values (?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone_number);
            preparedStatement.setString(3, email_id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected+" inserted into db");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
