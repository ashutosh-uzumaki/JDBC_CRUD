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

            updateData(connection, 5, "AshutoshPritha", "6384137446", "ashutosh.dev18@gmail.com");
            try{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM customer WHERE id = 5");
                while(resultSet.next()){
                    System.out.println("---------UPDATED DATA IS AS FOLLOW--------------");
                    System.out.println("ID: "+resultSet.getInt("id")
                    +" NAME: "+resultSet.getString("name")
                    +" PHONE NUMBER: "+resultSet.getString("phone_number")
                    +" EMAIL ID: "+resultSet.getString("email_id"));
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            deleteData(connection, 1, "Pritha");
            System.out.println("------------- DATA AFTER DELETING PRITHA -------------------");
            try{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
                while(resultSet.next()){
                    System.out.println("---------UPDATED DATA IS AS FOLLOW--------------");
                    System.out.println("ID: "+resultSet.getInt("id")
                            +" NAME: "+resultSet.getString("name")
                            +" PHONE NUMBER: "+resultSet.getString("phone_number")
                            +" EMAIL ID: "+resultSet.getString("email_id"));
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

    private static void updateData(Connection connection, int id, String name, String phone_number, String email_id){
        String updateName = "UPDATE customer SET name = ? WHERE id = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateName);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            int rowsAffected  = preparedStatement.executeUpdate();
            System.out.println(rowsAffected+" row(s) were updated");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void deleteData(Connection connection, int id, String name){
        String deleteQuery = "DELETE FROM customer WHERE name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, name);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected+" row(s) were deleted");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
