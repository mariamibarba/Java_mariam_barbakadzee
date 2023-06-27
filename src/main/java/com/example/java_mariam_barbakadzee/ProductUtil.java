package com.example.java_mariam_barbakadzee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductUtil {

    private ProductUtil() {

    }

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS PRODUCTS(" +
            "NAME VARCHAR(30)," +
            "PRICE DOUBLE," +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT)";


    public static void createTable() {
        try {
            JDBCConfig.getStatement().executeUpdate(CREATE);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static String insert(Product product){

        String INSERT_TABLE = "INSERT INTO PRODUCTS(NAME, PRICE) VALUES(" +
                "'"+ product.getName() + "', " +
                product.getPrice() + ")";

        try {
            JDBCConfig.getStatement().executeUpdate(INSERT_TABLE);
            return "Product inserted successfully";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<PieChart.Data> readData(){

        String SELECT = "SELECT NAME, count(*) as Count FROM PRODUCTS GROUP BY NAME";

        ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList();

        try{
            ResultSet result = JDBCConfig.getStatement().executeQuery(SELECT);

            while (result.next()){
                observableList.add(new PieChart.Data(result.getString("NAME"), result.getInt("COUNT")));
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return observableList;

    }

    public static String deleteItem(int id){

        String DELETE = "DELETE FROM PRODUCTS WHERE ID = " + id;

        try {
            JDBCConfig.getStatement().executeUpdate(DELETE);
            return "Product deleted!";
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public static String clearData(){
        String DELETE = "DELETE FROM PRODUCTS";

        try {
            JDBCConfig.getStatement().executeUpdate(DELETE);
            return "Data deleted";
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}