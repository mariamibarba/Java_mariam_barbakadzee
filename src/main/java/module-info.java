module com.example.java_mariam_barbakadzee {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;




    opens com.example.java_mariam_barbakadzee to javafx.fxml;
    exports com.example.java_mariam_barbakadzee;
}