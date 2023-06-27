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
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        ProductUtil.createTable();

        Text mainText = new Text("Enter Product Information");

        TextField name = new TextField();
        name.setPromptText("Name");

        TextField price = new TextField();
        price.setPromptText("Price");

        Button insertButton = new Button("Insert");

        Text finalText = new Text();

        Button getChartButton = new Button("Get Chart");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(mainText, name, price, insertButton, finalText, getChartButton);

        Group root = new Group(vbox);

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
                pieChart.setPrefSize(300, 300);
                pieChart.setLayoutX(300);
                pieChart.setLayoutY(20);
                root.getChildren().add(pieChart);
            }
        });

        Scene scene = new Scene(root, 800, 400); // Adjust the scene size accordingly
        stage.setTitle("Product Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
