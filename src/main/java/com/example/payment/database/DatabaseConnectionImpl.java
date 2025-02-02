package com.example.payment.database;

public class DatabaseConnectionImpl implements DatabaseConnection {
    @Override
    public void executeUpdate(String query) {
        System.out.println("Executed query: " + query);
    }
}
