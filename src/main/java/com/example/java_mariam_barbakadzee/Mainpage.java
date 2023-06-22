package com.example.java_mariam_barbakadzee;


//შექმენით javaFx აპლიკაცია.პროექტს დაარქვით Java_mariam_barbakadze.
//	აპლიკაცია განკუთვნილია მაღაზიაში პროდუქტების აღწერისთვის.
//	შექმენით Product მოდელ კლასი. ველები განუსაზღვრეთ სურვილისამებრ.
//	FX ფორმაში შექმენით თქვენთვის სასურველი layout, იმის მიხედვით თუ როგორ გსურთ კომპონენტების განლაგება და Product -ის შემოსატანად ამ layout-ში დაამატეთ შესაბამისი კომპონენტები.
//დამატების ღილაკზე დაჭერისას, MySQL-ის მონაცემთა ბაზაში, თქვენს მიერ წინასწარ შექმნილ ცხრილში- უნდა დაემატოს ახალი როდუცტ - ის ობიექტი(გამოიყენეთ მხოლოდ JDBC დრაივერი).
//
//		ამავე ფორმაზე დაამატეთ PieChart. ასევე შექმენით მეთოდი, რომელიც ბაზიდან წამოიღებს ყველა ჩანაწერს და Java Strea API - ს დახმარებით დააჯგუფებს პროდუქტებს - მათ რაოდენობასთან
//	(თვეში -10 ცალი,პური - 20 ცალი და ა.შ.)
//	აჩვენეთ ეს მონაცემები PieChart - ზე.
//
//		მონაცემთა ბაზა შეგიძლიათ შექმნათ XAMP- ის დახმარებით, ან გამოიყენეთინტერნეტში არსებული რომელიმე უფასო MYSQL მონაცემთა ბაზია სერვისი.


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.stream.Collectors;

public class Mainpage extends Application {

    private TableView<Product> table;
    private ObservableList<Product> products;
    private PieChart pieChart;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Product Description");

        // Create the form components
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();
        Button addButton = new Button("Add");

        // Create the table columns
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        TableColumn<Product, Number> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(param -> param.getValue().quantityProperty());

        // Create the table
        table = new TableView<>();
        table.getColumns().addAll(nameColumn, quantityColumn);
        products = FXCollections.observableArrayList();
        table.setItems(products);

        // Create the pie chart
        pieChart = new PieChart();

        // Handle the add button click event
        addButton.setOnAction(event -> {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            Product product = new Product(name, quantity);
            products.add(product);
            nameField.clear();
            quantityField.clear();
            insertProductIntoDatabase(product);
            updatePieChart();
        });

        // Create the form layout
        GridPane formLayout = new GridPane();
        formLayout.setHgap(10);
        formLayout.setVgap(10);
        formLayout.setPadding(new Insets(10));
        formLayout.add(nameLabel, 0, 0);
        formLayout.add(nameField, 1, 0);
        formLayout.add(quantityLabel, 0, 1);
        formLayout.add(quantityField, 1, 1);
        formLayout.add(addButton, 0, 2, 2, 1);

        // Create the main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(formLayout, table, pieChart);

        // Set up the scene
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load existing products from the database
        loadProductsFromDatabase();
        updatePieChart();
    }

    private void insertProductIntoDatabase(Product product) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name", "username", "password");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO products (name, quantity) VALUES (?, ?)")) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadProductsFromDatabase() {
        products.clear();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name", "username", "password");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM products")) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                Product product = new Product(name, quantity);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePieChart() {
        ObservableList<PieChart.Data> pieChartData = products.stream()
                .map(product -> new PieChart.Data(product.getName(), product.getQuantity()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        pieChart.setData(pieChartData);
    }
}