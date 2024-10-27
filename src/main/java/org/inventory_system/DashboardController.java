package org.inventory_system;

import org.inventory_system.DAO.CategoryDAOImpl;
import org.inventory_system.DAO.ProductDAOImpl;
import org.inventory_system.interfaces.CategoryDAO;
import org.inventory_system.interfaces.ProductDAO;
import org.inventory_system.model.*;
import org.inventory_system.config.Database;
import com.password4j.Password;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import org.inventory_system.interfaces.UserDAO;
import org.inventory_system.DAO.UserDAOImpl;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import static org.burningwave.core.assembler.StaticComponentContainer.Modules;

public class DashboardController implements Initializable {

    public FontAwesomeIconView product_print;

    private double x;
    private double y;

    @FXML
    private Button billing_btn;

    @FXML
    private Button product_btn;

    @FXML
    private Button new_inventory_btn;

    @FXML
    private AnchorPane billing_pane;

    @FXML
    private Button customer_btn;

    @FXML
    private Button invoice_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane customer_pane;

    @FXML
    private AnchorPane product_pane;

    @FXML
    private AnchorPane new_inventory_pane;

    @FXML
    private AnchorPane invoice_pane;

    @FXML
    private AnchorPane dasboard_pane;

    @FXML
    private Button purchase_btn;

    @FXML
    private AnchorPane purchase_pane;

    @FXML
    private Label user;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;

    @FXML
    private Button bill_add;

    @FXML
    private Button bill_clear;

    @FXML
    private DatePicker bill_date;

    @FXML
    private TextField bill_item;

    @FXML
    private TextField bill_name;

    @FXML
    private TextField bill_phone;

    @FXML
    private TextField bill_price;

    @FXML
    private Button bill_print;

    @FXML
    private TextField sales_quantity;

    @FXML
    private ComboBox<?> prod_category;

    @FXML
    private Button bill_save;

    @FXML
    private TextField bill_total_amount;

    @FXML
    private TableView<SalesDetails> billing_table;

    @FXML
    private TextField billing_table_search;

    @FXML
    private Label final_amount;

    private final String[] unitList = {"Kg", "Ltr", "Gr", "Und"};
    private final String[] rolList = {"admin", "user"};

    private final List<Integer> productIdList = new ArrayList<>();

    @FXML
    private TableColumn<?, ?> col_bill_item_num;

    @FXML
    private TableColumn<?, ?> col_bill_item_name;

    @FXML
    private TableColumn<?, ?> col_bill_price;

    @FXML
    private TableColumn<?, ?> col_bill_quantity;

    @FXML
    private TableColumn<?, ?> col_bill_total_amt;

    @FXML
    private Button cust_btn_add;

    private boolean saleCreated = false;
    private boolean purchaseCreated = false;
    private boolean saleDeleted = false;

    @FXML
    private Button cust_btn_delete;

    @FXML
    private Button cust_btn_edit;

    @FXML
    private TableView<User> customer_table;

    @FXML
    private TableColumn<?, ?> cust_col_id;

    @FXML
    private TableColumn<?, ?> cust_col_username;

    @FXML
    private TableColumn<?, ?> cust_col_email;

    @FXML
    private TableColumn<?, ?> cust_col_rol;

    @FXML
    private TableColumn<?, ?> cust_col_phone;

    @FXML
    private TableView<Product> product_table;

    @FXML
    private TableColumn<?, ?> prod_col_id;

    @FXML
    private TableColumn<?, ?> prod_col_name;

    @FXML
    private TableColumn<?, ?> prod_col_qty;

    @FXML
    private TableColumn<?, ?> prod_col_pre;

    @FXML
    private TableColumn<?, ?> prod_col_uni;

    @FXML
    private TableColumn<?, ?> prod_col_cat_name;

    @FXML
    private TableColumn<?, ?> prod_col_date;

    @FXML
    private TextField prod_field_search;

    @FXML
    private Button prod_btn_print;

    @FXML
    private Button prod_btn_search;

    @FXML
    private Button prod_btn_delete;

    @FXML
    private Button prod_btn_update;

    @FXML
    private Button prod_btn_add;

    @FXML
    private TableColumn<?, ?> invoice_col_id;

    @FXML
    private TableColumn<?, ?> invoice_col_username;

    @FXML
    private TableColumn<?, ?> invoice_col_date;

    @FXML
    private TableColumn<?, ?> invoice_col_total;

    @FXML
    private TableView<Purchase> invoice_table;

    @FXML
    private Label invoice_total_amount;

    @FXML
    private Button purchase_btn_add;

    @FXML
    private Button purchase_btn_cancel;

    @FXML
    private Button purchase_btn_new;

    @FXML
    private Button purchase_btn_save;

    @FXML
    private Button purchase_btn_print;

    @FXML
    private Button invoice_btn_print;
    @FXML
    private Label purchase_total_amount;

    @FXML
    private TextField search_prod_purchase;

    @FXML
    private TableColumn<?, ?> purchase_col_prod;
    @FXML
    private TableColumn<?, ?> purchase_col_unit;

    @FXML
    private TableColumn<?, ?> purchase_col_supplier;

    @FXML
    private TableColumn<?, ?> purchase_col_price;

    @FXML
    private TableColumn<?, ?> purchase_col_total;

    @FXML
    private TableColumn<Product, Integer> purchase_col_qty;

    @FXML
    private TableView<Product> purchase_table;

    @FXML
    private Label dash_total_items_sold_this_month;

    @FXML
    private Label dash_total_purchase;

    @FXML
    private Label dash_total_sales_items_this_month_name;

    @FXML
    private Label dash_total_sales_this_month;

    @FXML
    private Label dash_total_sales_this_month_name;

    @FXML
    private Label dash_total_sold;

    @FXML
    private Label dash_total_stocks;

    @FXML
    private Button signout_btn;

    ProductDAO productDAO = new ProductDAOImpl();
    ObservableList<Product> productsList = productDAO.getProductsList();
    UserDAO loggedInUser = new UserDAOImpl();

    public DashboardController() throws Exception {
    }


    public void onExit() {
        System.exit(0);
    }

    public void activateDashboard(){
        dasboard_pane.setVisible(true);
        billing_pane.setVisible(false);
        product_pane.setVisible(false);
        new_inventory_pane.setVisible(false);
        customer_pane.setVisible(false);
        invoice_pane.setVisible(false);
        purchase_pane.setVisible(false);
        UserDAO userid = new UserDAOImpl();
        String hello = userid.getUserById();
        System.out.println(hello);
    }

    public void activateAnchorPane() {
        dashboard_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(true);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        billing_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(true);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(false);
            customer_pane.setVisible(false);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        product_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            product_pane.setVisible(true);
            new_inventory_pane.setVisible(false);
            customer_pane.setVisible(false);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(false);
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        new_inventory_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(true);
            customer_pane.setVisible(false);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        customer_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(false);
            customer_pane.setVisible(true);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        invoice_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(false);
            customer_pane.setVisible(false);
            invoice_pane.setVisible(true);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        purchase_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(false);
            customer_pane.setVisible(false);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(true);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
        });
    }

    public void setUsername() throws Exception {
        user.setText(loggedInUser.getUsername());
    }

    private void checkUserRole() {
        User loggedInUser = Session.getCurrentUser();
        if ("admin".equals(loggedInUser.getRol())) {
            cust_btn_add.setVisible(true);
            cust_btn_delete.setVisible(true);
            cust_btn_edit.setVisible(true);
        } else {
            cust_btn_add.setVisible(false);
            cust_btn_delete.setVisible(false);
            cust_btn_edit.setVisible(false);
        }
    }
//==================PRODUCTS METHODS================================

    public void filterProducts(String searchText) {
        ObservableList<Product> filteredList = FXCollections.observableArrayList();

        if (searchText == null || searchText.isEmpty()) {
            product_table.setItems(productsList);
        } else {
            for (Product product : productsList) {
                if (product.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                        product.getCat_name().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredList.add(product);
                }
            }
            product_table.setItems(filteredList);
        }
    }

    public void showProductsData() throws Exception {
        prod_field_search.textProperty().addListener((observable, oldValue, newValue) -> filterProducts(newValue));
        ObservableList<Product> productsList = productDAO.getProductsList();
        prod_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        prod_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        prod_col_pre.setCellValueFactory(new PropertyValueFactory<>("price"));
        prod_col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        prod_col_uni.setCellValueFactory(new PropertyValueFactory<>("unit"));
        prod_col_cat_name.setCellValueFactory(new PropertyValueFactory<>("cat_name"));
        prod_col_date.setCellValueFactory(new PropertyValueFactory<>("exp_date"));
        product_table.setItems(productsList);
    }

    public void printProductsDetails() {
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM products";
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/products.jrxml"));
            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void searchProductsLocation() {
        if (product_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor seleccione un producto.");
            alert.showAndWait();
            return;
        }
        String productLocation = product_table.getSelectionModel().getSelectedItem().getLoc_name();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Producto encontrado");
        alert.setHeaderText(null);
        alert.setContentText("El producto esta en: " + productLocation.toUpperCase());
        alert.showAndWait();
    }

    public void editProduct() throws Exception {
        CategoryDAO comboCategory = new CategoryDAOImpl();
        if (product_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecciona un producto.");
            alert.showAndWait();
            return;
        }
        String name = product_table.getSelectionModel().getSelectedItem().getName();
        int prodID = product_table.getSelectionModel().getSelectedItem().getId();
        String unit = product_table.getSelectionModel().getSelectedItem().getUnit();
        double price = product_table.getSelectionModel().getSelectedItem().getPrice();
        int quantity = product_table.getSelectionModel().getSelectedItem().getQuantity();
        LocalDate exp_date = product_table.getSelectionModel().getSelectedItem().getExp_date();

        Stage popup_window = new Stage();
        popup_window.initModality(Modality.APPLICATION_MODAL);
        popup_window.setTitle("EDITAR PRODUCTO");

        Label lblName = new Label("PRODUCTO:");
        Label prod_field_name = new Label();
        prod_field_name.setText(name.toUpperCase());
        Label lblUnit = new Label("UNIDAD:");
        ComboBox<String> comboBoxUnit = comboBoxUnit();
        comboBoxUnit.setValue(unit);
        comboBoxUnit.setEditable(false);
        Label lblPrice = new Label("PRECIO:");
        TextField prod_field_price = new TextField();
        prod_field_price.setText(String.valueOf(price));
        Label lblCat = new Label("CATEGORIA:");
        ComboBox<Category> categoryList = comboCategory.getComboCategory();
        categoryList.setDisable(true);
        Label lblQty = new Label("CANTIDAD:");
        TextField prod_field_qty = new TextField();
        prod_field_qty.setText(String.valueOf(quantity));
        Label lblDate = new Label("VENCIMIENTO:");
        DatePicker expDate = new DatePicker();
        expDate.setValue(exp_date);
        Label lblSupp = new Label("PROVEEDOR:");
        ComboBox<Supplier> supplierCombo = comboSupplierData();
        Label lblLoc = new Label("LUGAR:");
        ComboBox<Location> locationCombo = comboLocationData();

        Button btnSave = new Button("ACTUALIZAR");
        btnSave.getStyleClass().add("print");
        btnSave.autosize();
        btnSave.setOnAction(e -> {

            Supplier supplierSelected = supplierCombo.getValue();
            int supp_id = supplierSelected.getSupp_id();

            Location locationSelected = locationCombo.getValue();
            int loc_id = locationSelected.getLoc_id();

            LocalDate dateSelected = expDate.getValue();
            String date_exp = dateSelected.toString();
            try {
                productDAO.updateProduct(
                        Double.parseDouble(prod_field_price.getText()),
                        Integer.parseInt(prod_field_qty.getText()),
                        date_exp,
                        supp_id,
                        loc_id,
                        prodID
                );

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                showProductsData();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            popup_window.close();
        });
        GridPane layout = new GridPane();

        layout.setPadding(new Insets(20));
        layout.setVgap(10);
        layout.setHgap(10);
        layout.add(lblName, 0, 0);
        layout.add(prod_field_name, 1, 0);
        layout.add(lblPrice, 0, 1);
        layout.add(prod_field_price, 1, 1);
        layout.add(lblCat, 0, 2);
        layout.add(categoryList, 1, 2);
        layout.add(lblQty, 0, 3);
        layout.add(prod_field_qty, 1, 3);
        layout.add(lblDate, 0, 4);
        layout.add(expDate, 1, 4);
        layout.add(lblUnit, 0, 5);
        layout.add(comboBoxUnit, 1, 5);
        layout.add(lblSupp, 0, 6);
        layout.add(supplierCombo, 1, 6);
        layout.add(lblLoc, 0, 7);
        layout.add(locationCombo, 1, 7);
        layout.add(btnSave, 1, 9);

        Scene scene = new Scene(layout, 300, 400);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
        popup_window.setScene(scene);

        popup_window.showAndWait();
    }

    public void deleteProduct() throws Exception {
        if (product_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecciona aun producto.");
            alert.showAndWait();
            return;
        }
        String productName = product_table.getSelectionModel().getSelectedItem().getName();

        Alert confirmationAlert = new Alert(Alert.AlertType.WARNING);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Esta  seguro de eliminar a este producto?");
        confirmationAlert.setContentText("Producto: " + productName.toUpperCase());

        Optional<ButtonType> result1 = confirmationAlert.showAndWait();

        if (result1.isPresent() && result1.get() == ButtonType.OK) {
            int prodId = product_table.getSelectionModel().getSelectedItem().getId();
            productDAO.deleteProduct(prodId);
            showProductsData();
        }
    }

    public void updateProductList() throws Exception {
        showProductsData();
    }

    private int calculateNewStock(int productId, int qtyUpdate){
        int actualQty = 0;
        int newQty;
        for (Product product : productsList) {
            if (product.getId() == productId) {
                actualQty = product.getQuantity();
            }
        }
        if(saleDeleted || purchaseCreated){
            newQty = actualQty + qtyUpdate;
            saleDeleted = false;
        }else{
            newQty = actualQty - qtyUpdate;
        }

        return newQty;
    }

    private void updateProductStock(int productId, int quantity) throws Exception {
        productDAO.getProductsList();
        int newQty = calculateNewStock(productId, quantity);
        productDAO.updateProductStock(productId, newQty);
    }
    public ComboBox<String> comboBoxUnit() {
        ComboBox<String> comboUnit = new ComboBox<>();
        for (String unit : unitList) {
            comboUnit.getItems().add(unit);
        }
        return comboUnit;
    }

    public ComboBox<Category> comboCategoryData() {
        ComboBox<Category> categoryCombo = new ComboBox<>();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM category";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            Category categoryData;
            while (resultSet.next()) {
                categoryData = new Category(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("cat_name"));
                categoryCombo.getItems().add(categoryData);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }
        return categoryCombo;
    }

    public ComboBox<Supplier> comboSupplierData() {
        ComboBox<Supplier> supplierCombo = new ComboBox<>();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM supplier";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            Supplier supplierData;
            while (resultSet.next()) {
                supplierData = new Supplier(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("supp_name"),
                        resultSet.getString("phone"));
                System.out.println(supplierData);
                supplierCombo.getItems().add(supplierData);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }

        return supplierCombo;
    }

    public ComboBox<Location> comboLocationData() {
        ComboBox<Location> locationCombo = new ComboBox<>();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM location";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            Location locationData;
            while (resultSet.next()) {
                locationData = new Location(
                        Integer.parseInt(resultSet.getString("loc_id")),
                        resultSet.getString("loc_name"));

                System.out.println(locationData);
                locationCombo.getItems().add(locationData);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }

        return locationCombo;
    }

    public void addProduct() {

        Stage popup_window = new Stage();
        popup_window.initModality(Modality.APPLICATION_MODAL);
        popup_window.setTitle("Agregar nuevo producto");

        Label lblName = new Label("Producto:");
        TextField prod_field_name = new TextField();
        Label lblUnit = new Label("Unidad:");
        ComboBox<String> comboBoxUnit = comboBoxUnit();
        Label lblPrice = new Label("Precio:");
        TextField prod_field_price = new TextField();
        prod_field_price.getStyleClass().add("textfield");
        Label lblCat = new Label("Categoria:");
        ComboBox<Category> categoryList = comboCategoryData();
        Label lblQty = new Label("Cantidad:");
        TextField prod_field_qty = new TextField();
        Label lblDate = new Label("Vencimiento:");
        DatePicker expDate = new DatePicker();
        Label lblSupp = new Label("Proveedor:");
        ComboBox<Supplier> supplierCombo = comboSupplierData();
        Label lblLoc = new Label("Location:");
        ComboBox<Location> locationCombo = comboLocationData();

        Button btnSave = new Button("GUARDAR");
        btnSave.getStyleClass().add("print");
        btnSave.autosize();
        btnSave.setOnAction(e -> {

            Category categorySelected = categoryList.getValue();
            int cat_id = categorySelected.getId();

            Supplier supplierSelected = supplierCombo.getValue();
            int supp_id = supplierSelected.getSupp_id();

            Location locationSelected = locationCombo.getValue();
            int loc_id = locationSelected.getLoc_id();

            LocalDate dateSelected = expDate.getValue();
            String date_exp = dateSelected.toString();

            String unitSelected = comboBoxUnit.getValue();

            connection = Database.getInstance().connectDB();
            String sql = "INSERT INTO products(name, cat_id,quantity,price,exp_date,unit,supp_id,loc_id)VALUES(?,?,?,?,?,?,?,?)";
            try {

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, prod_field_name.getText());
                preparedStatement.setInt(2, cat_id);
                preparedStatement.setInt(3, Integer.parseInt(prod_field_qty.getText()));
                preparedStatement.setDouble(4, Double.parseDouble(prod_field_price.getText()));
                preparedStatement.setString(5, date_exp);
                preparedStatement.setString(6, unitSelected);
                preparedStatement.setInt(7, supp_id);
                preparedStatement.setInt(8, loc_id);

                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    showCustomerData();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill the mandatory data such as name and price.");
                    alert.showAndWait();
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            popup_window.close();
        });
        GridPane layout = new GridPane();

        layout.setPadding(new Insets(20));
        layout.setVgap(10);
        layout.setHgap(10);
        layout.add(lblName, 0, 0);
        layout.add(prod_field_name, 1, 0);
        layout.add(lblPrice, 0, 1);
        layout.add(prod_field_price, 1, 1);
        layout.add(lblCat, 0, 2);
        layout.add(categoryList, 1, 2);
        layout.add(lblQty, 0, 3);
        layout.add(prod_field_qty, 1, 3);
        layout.add(lblDate, 0, 4);
        layout.add(expDate, 1, 4);
        layout.add(lblUnit, 0, 5);
        layout.add(comboBoxUnit, 1, 5);
        layout.add(lblSupp, 0, 6);
        layout.add(supplierCombo, 1, 6);
        layout.add(lblLoc, 0, 7);
        layout.add(locationCombo, 1, 7);
        layout.add(btnSave, 1, 9);

        Scene scene = new Scene(layout, 300, 400);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
        popup_window.setScene(scene);
        popup_window.showAndWait();
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void createNewSale() {
        if (bill_item.getText().isBlank() || sales_quantity.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Por Favor ingrese codigo de producto o cantidad");
            alert.showAndWait();
            return;
        } else if (!isInteger(bill_item.getText()) || !isInteger(sales_quantity.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Por Favor ingrese un ID o cantidad valida");
            alert.showAndWait();
            return;
        } else if (!existsProductId()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("PRODUCT ID NO EXISTE !!");
            alert.showAndWait();
            return;
        }
        connection = Database.getInstance().connectDB();
        String sql = "INSERT INTO sales(date,user_id)VALUES(?,?)";

        try {
            User loggedInUser = Session.getCurrentUser();
            int userId = loggedInUser.getId();
            LocalDate date = LocalDate.now();
            Date dateSale = Date.valueOf(date);

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, dateSale);
            preparedStatement.setInt(2, userId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                saleCreated = true;
                System.out.println("heeeree");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill the mandatory data such as name and price.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void insertNewProductDetailsSales(int salesId, int productId, int quantity) {
        connection = Database.getInstance().connectDB();
        String sql = "INSERT INTO details_sales(sales_id,quantity,product_id)VALUES(?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, salesId);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, productId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("heeeree in details");
                updateProductStock(productId, quantity);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill the mandatory data such as name and price.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public int getSalesId() {
        int salesId = 0;
        if (saleCreated) {
            connection = Database.getInstance().connectDB();
            String sql = "SELECT MAX(sales_id) AS sales_id FROM sales";
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    salesId = resultSet.getInt("sales_id");
                }

            } catch (Exception err) {
                err.printStackTrace();
            }
        }

        return salesId;
    }

    private boolean existsProductId() {
        String input = bill_item.getText();
        int productId = Integer.parseInt(input);
        return productsList.stream().anyMatch(product -> product.getId() == productId);
    }

    public void addProductBilling() {

        int productId = Integer.parseInt(bill_item.getText());
        int quantity = Integer.parseInt(sales_quantity.getText());
        int actualStock = calculateNewStock(productId,quantity);

        try {
            if (!saleCreated && existsProductId() && actualStock > 0) {
                createNewSale();
            } else if (!existsProductId()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("PRODUCT ID INVALIDO");
                alert.showAndWait();
                billClearData();
                return;
            } else if (actualStock <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("NO QUEDA STOCK SUFICIENTE ");
                    alert.showAndWait();
                    return;
            }
            int salId = getSalesId();
            insertNewProductDetailsSales(salId, productId, quantity);


        } catch (Exception err) {
            err.printStackTrace();
        }
        billClearData();
        showBillingData();
    }

    public ObservableList<SalesDetails> listBillingData(int salesId) {
        ObservableList<SalesDetails> billingList = FXCollections.observableArrayList();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT p.id,ds.quantity,p.name, p.price,(p.price*ds.quantity) AS subtotal " +
                "FROM details_sales AS ds " +
                "JOIN products AS p ON ds.product_id=p.id\n" +
                "WHERE ds.sales_id=?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, salesId);
            resultSet = preparedStatement.executeQuery();

            SalesDetails billingData;
            while(resultSet.next()) {
                billingData = new SalesDetails(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("name"),
                        Integer.parseInt(resultSet.getString("quantity")),
                        Double.parseDouble(resultSet.getString("price")),
                        Double.parseDouble(resultSet.getString("subtotal")));
                billingList.addAll(billingData);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

        return billingList;
    }

    public void calculateFinalAmount() {
        int salId = getSalesId();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT ds.sales_id,SUM(ds.quantity*p.price) AS final_amount " +
                "FROM details_sales AS ds JOIN products AS p\n" +
                "WHERE ds.product_id=p.id and ds.sales_id=?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, salId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final_amount.setText(resultSet.getString("final_amount"));
            }

        } catch (Exception err) {
            err.printStackTrace();
        }

    }

    public void showBillingData(){
        int salId = getSalesId();
        ObservableList<SalesDetails> billingList = listBillingData(salId);
        col_bill_item_num.setCellValueFactory(new PropertyValueFactory<>("productId"));
        col_bill_item_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_bill_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_bill_price.setCellValueFactory(new PropertyValueFactory<>("prodPrice"));
        col_bill_total_amt.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        billing_table.setItems(billingList);

        if (!billingList.isEmpty()) {
            calculateFinalAmount();
        } else {
            final_amount.setText("0.00");
        }
    }

    public void billClearData() {
        bill_item.clear();
        sales_quantity.clear();
    }

    public void billCancelSale() {
        int salId = getSalesId();
        if (salId != 0) {
            connection = Database.getInstance().connectDB();
            String sql = "DELETE FROM sales WHERE sales_id=?";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, salId);
                int result = preparedStatement.executeUpdate();

                if (result > 0) {
                    billClearData();
                    billing_table.getItems().clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("No hay datos en la Tabla.");
                    alert.showAndWait();
                }
            } catch (Exception err) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeight(500);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(err.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Aun no ha hecho una venta !!");
            alert.showAndWait();
        }
    }

    public void deleteBillingData() throws Exception {
        if (billing_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor seleccione un producto.");
            alert.showAndWait();
            return;
        }
        int prodId = billing_table.getSelectionModel().getSelectedItem().getProductId();
        int qtyUpdate = billing_table.getSelectionModel().getSelectedItem().getQuantity();

        int salId = getSalesId();
        billing_table.getSelectionModel().getSelectedItem().setQuantity(400);
        connection = Database.getInstance().connectDB();
        String sql = "DELETE FROM details_sales WHERE product_id=? and sales_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, billing_table.getSelectionModel().getSelectedItem().getProductId());
            preparedStatement.setInt(2, salId);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                showCustomerData();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("No hay datos en la Tabla.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
        saleDeleted = true;
        updateProductStock(prodId, qtyUpdate);
        showBillingData();
    }

    public void billSave() {

        if (!billing_table.getItems().isEmpty()) {
            saleCreated = false;
            billing_table.getItems().clear();
            final_amount.setText("0.00");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Venta Realizada");
            alert.setHeaderText(null);
            alert.setContentText("Venta realizada con exito");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Agregue productos para realizar la venta");
            alert.showAndWait();
        }

    }

    public void showListSales() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sales.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            root.setOnMousePressed((event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged((event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    public ObservableList<User> listCustomerData() {
        ObservableList<User> customersList = FXCollections.observableArrayList();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM users";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            User customer;
            while (resultSet.next()) {
                customer = new User(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("rol"));
                customersList.add(customer);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return customersList;
    }

    public void showCustomerData() {
        ObservableList<User> customerList = listCustomerData();

        cust_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        cust_col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cust_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        cust_col_rol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        customer_table.setItems(customerList);
    }

    private ComboBox<String> comboRol() {
        ComboBox<String> comboRol = new ComboBox<>();
        for (String unit : rolList) {
            comboRol.getItems().add(unit);
        }
        return comboRol;
    }

    public void setNewUser() {
        Stage popup_window = new Stage();
        popup_window.initModality(Modality.APPLICATION_MODAL);
        popup_window.setTitle("CREAR NUEVO USUARIO");

        Label lblName = new Label("USUARIO:");
        TextField usr_field_username = new TextField("as");
        Label lblEmail = new Label("EMAIL:");
        TextField usr_field_email = new TextField();
        Label lblPhone = new Label("TELEFONO:");
        TextField usr_field_phone = new TextField();
        Label lblRol = new Label("ROL:");
        ComboBox<String> rolList = comboRol();
        Label lblP1 = new Label("PASSWORD1:");
        PasswordField usr_field_pass1 = new PasswordField();
        Label lblP2 = new Label("REESCRIBIR PASSW:");
        PasswordField usr_field_pass2 = new PasswordField();

        Button btnSave = new Button("GUARDAR");
        btnSave.getStyleClass().add("print");
        btnSave.setOnAction(e -> {

            String rol = rolList.getValue();
            if (checkForUserAvailability(usr_field_username.getText()) && checkForEmailAvailability(usr_field_email.getText())) {
                if (checkPassword(usr_field_pass1.getText(), usr_field_pass2.getText())) {
                    String hashedPassword = Password.hash(usr_field_pass1.getText()).withBcrypt().getResult();
                    Connection connection = Database.getInstance().connectDB();
                    String sql = "INSERT INTO users(username,password,email,phone,rol) VALUES(?,?,?,?,?)";
                    try {
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, usr_field_username.getText());
                        preparedStatement.setString(2, hashedPassword);
                        preparedStatement.setString(3, usr_field_email.getText());
                        preparedStatement.setString(4, usr_field_phone.getText());
                        preparedStatement.setString(5, rol);
                        int result = preparedStatement.executeUpdate();
                        if (result > 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message");
                            alert.setHeaderText(null);
                            alert.setContentText("USUARIO CREADO CORRECTAMENTE.");
                            alert.showAndWait();
                            btnSave.getScene().getWindow().hide();
                            showCustomerData();
                        }
                    } catch (Exception err) {
                        err.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText(null);
                        alert.setContentText(err.getMessage());
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Las password deben ser iguales.");
                    alert.showAndWait();
                }
            }
        });

        GridPane layout = new GridPane();
        layout.getStyleClass().add("grid-pane");
        layout.setPadding(new Insets(20));
        layout.setVgap(10);
        layout.setHgap(10);
        layout.add(lblName, 0, 2);
        layout.add(usr_field_username, 1, 2);
        layout.add(lblEmail, 0, 3);
        layout.add(usr_field_email, 1, 3);
        layout.add(lblPhone, 0, 4);
        layout.add(usr_field_phone, 1, 4);
        layout.add(lblRol, 0, 5);
        layout.add(rolList, 1, 5);
        layout.add(lblP1, 0, 6);
        layout.add(usr_field_pass1, 1, 6);
        layout.add(lblP2, 0, 7);
        layout.add(usr_field_pass2, 1, 7);
        layout.add(btnSave, 1, 9);

        Scene scene = new Scene(layout, 300, 400);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
        popup_window.setScene(scene);
        popup_window.showAndWait();
    }

    public boolean checkPassword(String usr_field_pass1, String usr_field_pass2) {
        return usr_field_pass1.equals(usr_field_pass2);
    }

    public boolean checkForUserAvailability(String usr_field_username) {
        Connection connection = Database.getInstance().connectDB();
        String sql = "SELECT username FROM users WHERE username=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usr_field_username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Nombre de usuario ya registrado.");
                alert.showAndWait();
                return false;
            } else {
                return true;
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }

    public boolean checkForEmailAvailability(String usr_field_email) {
        Connection connection = Database.getInstance().connectDB();
        String sql = "SELECT email FROM users WHERE email=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usr_field_email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Email ya esta registrado.");
                alert.showAndWait();
                return false;
            } else {
                return true;
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }

    public void updateUserData() {
        if (customer_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecciona un usuario.");
            alert.showAndWait();
            return;
        }
        String user = customer_table.getSelectionModel().getSelectedItem().getUsername();
        String email = customer_table.getSelectionModel().getSelectedItem().getEmail();
        String phone = customer_table.getSelectionModel().getSelectedItem().getPhone();
        String rol = customer_table.getSelectionModel().getSelectedItem().getRol();

        Stage popup_window = new Stage();
        popup_window.initModality(Modality.APPLICATION_MODAL);
        popup_window.setTitle("ACTUALIZAR USUARIO");

        Label lblName = new Label("USUARIO:");
        TextField usr_field_username = new TextField();
        usr_field_username.setEditable(false);
        usr_field_username.setText(user);
        Label lblEmail = new Label("EMAIL:");
        TextField usr_field_email = new TextField();
        usr_field_email.setText(email);
        Label lblPhone = new Label("TELEFONO:");
        TextField usr_field_phone = new TextField();
        usr_field_phone.setText(phone);
        Label lblRol = new Label("ROL:");
        ComboBox<String> rolList = comboRol();
        rolList.setValue(rol);
        Label lblP1 = new Label("PASSWORD1:");
        PasswordField usr_field_pass1 = new PasswordField();
        Label lblP2 = new Label("REESCRIBIR PASSW:");
        PasswordField usr_field_pass2 = new PasswordField();

        Button btnSave = new Button("ACTUALIZAR");
        btnSave.getStyleClass().add("print");
        btnSave.setOnAction(e -> {
            String roln = rolList.getValue();
            Connection connection = Database.getInstance().connectDB();
            String sql = "UPDATE users SET email=? ,phone=?, rol=? ,password=? WHERE username='" + user + "'";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, usr_field_email.getText());
                preparedStatement.setString(2, usr_field_phone.getText());
                preparedStatement.setString(3, roln);
                preparedStatement.setString(4, usr_field_pass1.getText());
                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("USUARIO ACTUALIZADO CORRECTAMENTE.");
                    alert.showAndWait();
                    btnSave.getScene().getWindow().hide();
                    showCustomerData();
                }
            } catch (Exception err) {
                err.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText(err.getMessage());
                alert.showAndWait();
            }
        });
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20));
        layout.setVgap(10);
        layout.setHgap(10);
        layout.add(lblName, 0, 2);
        layout.add(usr_field_username, 1, 2);
        layout.add(lblEmail, 0, 3);
        layout.add(usr_field_email, 1, 3);
        layout.add(lblPhone, 0, 4);
        layout.add(usr_field_phone, 1, 4);
        layout.add(lblRol, 0, 5);
        layout.add(rolList, 1, 5);
        layout.add(lblP1, 0, 6);
        layout.add(usr_field_pass1, 1, 6);
        layout.add(lblP2, 0, 7);
        layout.add(usr_field_pass2, 1, 7);
        layout.add(btnSave, 1, 9);

        Scene scene = new Scene(layout, 300, 400);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
        popup_window.setScene(scene);
        popup_window.showAndWait();
    }

    public void deleteCustomerData() {
        if (customer_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecciona un usuario.");
            alert.showAndWait();
            return;
        }
        String customerName = customer_table.getSelectionModel().getSelectedItem().getUsername();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Esta  seguro de eliminar a este usuario?");
        confirmationAlert.setContentText("usuario: " + customerName.toUpperCase());

        Optional<ButtonType> result1 = confirmationAlert.showAndWait();

        if (result1.isPresent() && result1.get() == ButtonType.OK) {
            connection = Database.getInstance().connectDB();
            String sql = "DELETE FROM users WHERE username=?";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, customer_table.getSelectionModel().getSelectedItem().getUsername());
                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    showCustomerData();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("No hay datos en la Tabla.");
                    alert.showAndWait();
                }
            } catch (Exception err) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeight(500);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(err.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void printCustomersDetails() {
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM users";
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/customers.jrxml"));
            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    //------------------------INVOICE METHODS-------------------------

    private ObservableList<Purchase> getListInvoice() {
        ObservableList<Purchase> invoiceList = FXCollections.observableArrayList();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT pu.purchase_id,pu.date,u.username ,SUM(p.purch_price*dp.quantity) AS total FROM purchases pu\n" +
                "INNER JOIN details_purchases AS dp ON pu.purchase_id=dp.purchase_id\n" +
                "INNER JOIN products AS p ON dp.product_id=p.id\n" +
                "INNER JOIN users AS u ON pu.user_id=u.id\n" +
                "GROUP BY pu.purchase_id";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            Purchase invoice;
            while (resultSet.next()) {
                invoice = new Purchase(
                        resultSet.getInt("purchase_id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("username"),
                        resultSet.getDouble("total"));
                invoiceList.add(invoice);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return invoiceList;
    }

    private void showInvoiceData() {
        ObservableList<Purchase> invoiceList = getListInvoice();

        invoice_col_id.setCellValueFactory(new PropertyValueFactory<>("purchId"));
        invoice_col_username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        invoice_col_date.setCellValueFactory(new PropertyValueFactory<>("dateOfPurchase"));
        invoice_col_total.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        invoice_table.setItems(invoiceList);
    }

    public void filterProductsToPurchase(String searchText) {
        ObservableList<Product> filteredList = FXCollections.observableArrayList();

        if (searchText == null || searchText.isEmpty()) {
            purchase_table.setItems(productsList);
        } else {
            for (Product product : productsList) {
                if (product.getName().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredList.add(product);
                }
            }
            purchase_table.setItems(filteredList);
        }
    }

    public ObservableList<Product> listProductsToPurchase() {

        ObservableList<Product> purchaseList = FXCollections.observableArrayList();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT p.id,p.name,sp.supp_name,p.purch_price,p.unit\n" +
                "FROM products AS p\n" +
                "INNER JOIN supplier AS sp ON p.supp_id=sp.id";
        try {
            int qty = 0;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            Product productPurchase;
            while (resultSet.next()) {
                productPurchase = new Product(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("name"),
                        resultSet.getString("supp_name"),
                        Double.parseDouble(resultSet.getString("purch_price")),
                        resultSet.getString("unit"),
                        qty);
                purchaseList.addAll(productPurchase);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        search_prod_purchase.textProperty().addListener((observable, oldValue, newValue) -> filterProductsToPurchase(newValue));
        return purchaseList;
    }
    //========================PURCHASE METHODS============================
    public void showProductsToPurchase(boolean status) {
        ObservableList<Product> purchaseList = listProductsToPurchase();

        if (status) {
            purchase_table.setEditable(true);
            purchase_col_prod.setCellValueFactory(new PropertyValueFactory<>("name"));
            purchase_col_supplier.setCellValueFactory(new PropertyValueFactory<>("suppName"));
            purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("pricePur"));
            purchase_col_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
            purchase_col_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));

            purchase_col_qty.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
            purchase_col_qty.setOnEditCommit(event -> {

                Product product = event.getRowValue();
                product.setQty(event.getNewValue());
                int qty = product.getQty();
                int pId = product.getId();

                if (qty > 0 && !productIdList.contains(pId)) {
                    productIdList.add(pId);
                    insertPurchaseItem(pId, qty);
                    try {
                        updateProductStock(pId,qty);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (qty == 0 && productIdList.contains(pId)) {
                    deletePurchaseItem(pId);
                    if (productIdList.isEmpty()) {
                        cancelPurchase();
                    }
                } else {
                    updatePurchaseItem(pId, qty);
                }
            });
            purchase_table.setItems(purchaseList);

        } else {
            purchase_table.getItems().clear();
        }
    }

    private void deletePurchaseItem(int pId) {
        int purchaseId = getPurchaseId();
        connection = Database.getInstance().connectDB();
        String sql = "DELETE FROM details_purchases WHERE product_id=? and purchase_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pId);
            preparedStatement.setInt(2, purchaseId);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private void updatePurchaseItem(int pId, int qty) {
        int purchaseId = getPurchaseId();
        connection = Database.getInstance().connectDB();
        String sql = "UPDATE details_purchases SET quantity=? WHERE product_id=? and purchase_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, qty);
            preparedStatement.setInt(2, pId);
            preparedStatement.setInt(3, purchaseId);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            err.printStackTrace();
        }
        getTotalPurchaseAmount();
    }

    public void insertPurchaseItem(int pId, int qty) {

        System.out.println("nuevo purchase " + pId + " " + "cantidad " + qty);
        int purchaseId = getPurchaseId();
        connection = Database.getInstance().connectDB();
        String sql = "INSERT INTO details_purchases(purchase_id,quantity,product_id) VALUES(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, purchaseId);
            preparedStatement.setInt(2, qty);
            preparedStatement.setInt(3, pId);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                purchaseCreated = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo crear la compra");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        getTotalPurchaseAmount();
    }

    private int getPurchaseId() {
        int purchaseId = 0;
        if (purchaseCreated) {
            connection = Database.getInstance().connectDB();
            String sql = "SELECT MAX(purchase_id) AS purchase_id FROM purchases";
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    purchaseId = resultSet.getInt("purchase_id");
                }

            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        return purchaseId;
    }

    public void cancelPurchase() {
        if (purchaseCreated) {

            connection = Database.getInstance().connectDB();
            String sql = "DELETE FROM purchases WHERE purchase_id=?";
            try {
                int purchaseId = getPurchaseId();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, purchaseId);

                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    purchaseCreated = false;
                    showProductsToPurchase(false);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("No hay datos en la Tabla.");
                    alert.showAndWait();
                }
            } catch (Exception err) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeight(500);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(err.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void newPurchase() {

        if (!purchaseCreated) {
            showProductsToPurchase(true);
            search_prod_purchase.setEditable(true);
            createNewPurchase();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Usted ya tiene una COMPRA creada !!");
            alert.showAndWait();
        }
    }

    public void createNewPurchase() {
        if (!purchaseCreated) {
            connection = Database.getInstance().connectDB();
            String sql = "INSERT INTO purchases(date,user_id)VALUES(?,?)";

            try {
                User loggedInUser = Session.getCurrentUser();
                int userId = loggedInUser.getId();
                LocalDate date = LocalDate.now();
                Date datePurchase = Date.valueOf(date);

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDate(1, datePurchase);
                preparedStatement.setInt(2, userId);

                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    purchaseCreated = true;
                    System.out.println("heeeree");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Nose pudo crear la compra");
                    alert.showAndWait();
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    private void getTotalPurchaseAmount() {
        int purchID = getPurchaseId();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT SUM(dp.quantity * p.purch_price) AS total_amount\n" +
                "FROM details_purchases AS dp\n" +
                "INNER JOIN products AS p ON p.id = dp.product_id\n" +
                "WHERE dp.purchase_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, purchID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String result = resultSet.getString("total_amount");
                System.out.println("aquiii " + result);
                if (result == null) {
                    purchase_total_amount.setText("0.00");
                } else {
                    purchase_total_amount.setText("S/. "+result);
                }
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    public void savePurchase() {
        if (productIdList.isEmpty()) {
            cancelPurchase();
        }
        if (purchaseCreated) {
            purchase_total_amount.setText("S/. 0.00");
            search_prod_purchase.setText("");
            search_prod_purchase.setEditable(false);
            purchaseCreated = false;
            purchase_table.getItems().clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeight(500);
            alert.setTitle("MENSAJE");
            alert.setHeaderText(null);
            alert.setContentText("COMPRA REGISTRADA");
            alert.showAndWait();

        }
    }

    public void getTotalPurchase() {
        connection = Database.getInstance().connectDB();
        String sql = "SELECT COUNT(purchase_id) as total_purchase FROM purchases";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String result = resultSet.getString("total_purchase");
                if (result == null) {
                    dash_total_purchase.setText("S/. 0.00");
                } else {
                    dash_total_purchase.setText(result);
                }
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    public void getTotalStocks() {

        int totalPurchase = Integer.parseInt(dash_total_purchase.getText());
        int total_sold = Integer.parseInt(dash_total_sold.getText());
        int totalStockLeft = totalPurchase - total_sold;
        dash_total_stocks.setText(String.valueOf(totalStockLeft));
    }

    private void getTotalSales() {

        connection = Database.getInstance().connectDB();
        String sql = "SELECT COUNT(sales_id) AS total_sale FROM sales";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String result = resultSet.getString("total_sale");
                if (result == null) {
                    dash_total_sold.setText("0");
                } else {
                    dash_total_sold.setText(result);
                }
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    private String setMonth(String month) {
        HashMap<String, String> months = new HashMap<>();

        months.put("JANUARY", "ENERO");
        months.put("FEBRUARY", "FEBRERO");
        months.put("MARCH", "MARZO");
        months.put("APRIL", "ABRIL");
        months.put("MAY", "MAYO");
        months.put("JUNE", "JUNIO");
        months.put("JULY", "JULIO");
        months.put("AUGUST", "AGOSTO");
        months.put("SEPTEMBER", "SEPTIEMBRE");
        months.put("OCTOBER", "OCTUBRE");
        months.put("NOVEMBER", "NOVIEMBRE");
        months.put("DECEMBER", "DICIEMBRE");

        return months.get(month);
    }

    public void getSalesDetailsOfThisMonth() {
        LocalDate date = LocalDate.now();
        String monthEnglish = date.getMonth().toString();
        String monthName = setMonth(monthEnglish);

        connection = Database.getInstance().connectDB();
        String sql = "SELECT SUM(ds.quantity * p.price) AS total_sales_this_month\n" +
                "FROM sales AS s\n" +
                "INNER JOIN details_sales AS ds ON s.sales_id = ds.sales_id\n" +
                "INNER JOIN products AS p ON ds.product_id = p.id\n" +
                "WHERE DATE_FORMAT(s.date, '%M') = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, monthEnglish);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String result = resultSet.getString("total_sales_this_month");
                if (result == null) {
                    dash_total_sales_this_month.setText("0.00");
                } else {
                    dash_total_sales_this_month.setText(result);
                }
                dash_total_sales_this_month_name.setText(monthName);
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    public void getItemSoldThisMonth() {
        LocalDate date = LocalDate.now();
        String monthEnglish = date.getMonth().toString();
        String monthName = setMonth(monthEnglish);

        connection = Database.getInstance().connectDB();
        String sql = "SELECT COUNT(sales_id) AS total FROM sales WHERE DATE_FORMAT(date, '%M')=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, monthEnglish);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String result = resultSet.getString("total");
                if (result == null) {
                    dash_total_items_sold_this_month.setText("0");
                } else {
                    dash_total_items_sold_this_month.setText(result);
                }

                dash_total_sales_items_this_month_name.setText(monthName);
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }
    private void showDashboardData(){
            getTotalPurchase();
            getTotalSales();
            getTotalStocks();
            getSalesDetailsOfThisMonth();
            getItemSoldThisMonth();
    }

    public void signOut() {
        signout_btn.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            root.setOnMousePressed((event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged((event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Exports all modules to other modules
        Modules.exportAllToAll();

        //     DASHBOARD PANE
        showDashboardData();
        activateDashboard();
        try {
            setUsername();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        search_prod_purchase.setEditable(false);


//      CUSTOMER PANE
        checkUserRole();
        showCustomerData();
//       PRODUCTS PANE
        try {
            showProductsData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//      INVOICE PANE
        showInvoiceData();
    }
}