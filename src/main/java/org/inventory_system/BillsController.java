package org.inventory_system;

import org.inventory_system.config.Database;
import org.inventory_system.model.Sales;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class BillsController implements Initializable {

    @FXML
    private Button bills_btn_close;
    @FXML
    private AnchorPane bills_print_anchor_pane;

    @FXML
    public TableView<Sales> sales_tableview;

    @FXML
    public TableColumn<?, ?> sale_col_id;

    @FXML
    public TableColumn<?, ?> sale_col_date;

    @FXML
    public TableColumn<?, ?> sale_col_total;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;


    public void onExit(){
        bills_btn_close.getScene().getWindow().hide();
    }

    public ObservableList<Sales> getSalesList(){

        ObservableList<Sales> salesList = FXCollections.observableArrayList();
        connection= Database.getInstance().connectDB();
        String sql="SELECT s.sales_id ,s.date,SUM(ds.quantity*p.price) AS total " +
                "FROM sales AS s JOIN details_sales AS ds JOIN products AS p\n" +
                "WHERE s.sales_id=ds.sales_id and ds.product_id=p.id GROUP BY s.sales_id";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            Sales sale;
            while (resultSet.next()){
                sale = new Sales(
                        Integer.parseInt(resultSet.getString("sales_id")),
                        resultSet.getDate("date").toLocalDate(),
                        Double.parseDouble(resultSet.getString("total")));
                salesList.add(sale);
            }
        }catch (Exception err){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
        return salesList;
    }

    public void showSalesData(){
        ObservableList<Sales> salesList=getSalesList();
        sale_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        sale_col_date.setCellValueFactory(new PropertyValueFactory<>("dateSale"));
        sale_col_total.setCellValueFactory(new PropertyValueFactory<>("totalSale"));
        sales_tableview.setItems(salesList);
    }

    public void searchAndPrintBillDetails(){
        connection= Database.getInstance().connectDB();
        String sql="SELECT s.sales_id ,s.date,SUM(ds.quantity*p.price) AS total " +
                "FROM sales AS s JOIN details_sales AS ds JOIN products AS p\n" +
                "WHERE s.sales_id=ds.sales_id and ds.product_id=p.id GROUP BY s.sales_id";
        try{
            JasperDesign jasperDesign= JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/salesList.jrxml"));
            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jasperPrint ,false);
        }catch (Exception err){
            System.out.println(err.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
            err.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showSalesData();
    }
}
