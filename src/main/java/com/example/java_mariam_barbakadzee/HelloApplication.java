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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        ProductUtil.createTable();

        Text mainText = new Text("Enter Product Information");
        mainText.setLayoutX(100);
        mainText.setLayoutY(20);

        TextField name = new TextField();
        name.setLayoutX(100);
        name.setLayoutY(40);
        name.setPromptText("Name");

        TextField price = new TextField();
        price.setLayoutX(100);
        price.setLayoutY(80);
        price.setPromptText("Price");

        Button insertButton = new Button("Insert");
        insertButton.setLayoutX(100);
        insertButton.setLayoutY(120);

        Text finalText = new Text();
        finalText.setLayoutX(100);
        finalText.setLayoutY(160);

        Button getChartButton = new Button("Get Chart");
        getChartButton.setLayoutX(100);
        getChartButton.setLayoutY(200);

        Group root = new Group();

        root.getChildren().add(mainText);
        root.getChildren().add(name);
        root.getChildren().add(price);
        root.getChildren().add(insertButton);
        root.getChildren().add(finalText);
        root.getChildren().add(getChartButton);


        insertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String productName = name.getText();
                int productPrice = Integer.parseInt(price.getText());

                String result = ProductUtil.insert(new Product(productName, productPrice));
                finalText.setText(result);

                name.clear();
                price.clear();
            }
        });

        getChartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PieChart pieChart = new PieChart();
                pieChart.setData(ProductUtil.readData());
                pieChart.setLayoutX(300);
                pieChart.setLayoutY(20);
                root.getChildren().add(pieChart);
            }
        });

        Scene scene = new Scene(root, 500, 300);
        stage.setTitle("Product Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}