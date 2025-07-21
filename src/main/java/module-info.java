module com.example.cnpjalfanumerico {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.slf4j;



    opens com.example.cnpjalfanumerico to javafx.fxml;
    exports com.example.cnpjalfanumerico;


}

