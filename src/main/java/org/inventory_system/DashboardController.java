package org.inventory_system;

import org.inventory_system.DAO.*;
import org.inventory_system.interfaces.*;
import org.inventory_system.model.*;
import org.inventory_system.config.Database;
import com.password4j.Password;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import org.inventory_system.excel.CrearExcel;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.inventory_system.config.ErrorMesajes;
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
    private Button user_btn;

    @FXML
    private Button invoice_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane user_pane;

    @FXML
    private AnchorPane product_pane;

    @FXML
    private AnchorPane new_inventory_pane;

    @FXML
    private AnchorPane invoice_pane;

    @FXML
    private AnchorPane dashboard_pane;

    @FXML
    private Button purchase_btn;

    @FXML
    private AnchorPane purchase_pane;

    @FXML
    private Label user;

    private Connection connection;

    @FXML
    private Button bill_add;

    @FXML
    private Button bill_clear;

    @FXML
    private DatePicker bill_date;

    @FXML
    private TextField bill_item;

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
    private TableView<Product> new_inventory_table;

    @FXML
    private TableColumn<?, ?> inventory_col_prod;

    @FXML
    private TableColumn<?, ?> inventory_col_price;

    @FXML
    private TableColumn<?, ?> inventory_col_unit;

    @FXML
    private TableColumn<?, ?> inventory_col_qty;

    @FXML
    private TableColumn<Product, Integer> inventory_col_real_qty;

    @FXML
    private TableColumn<?, ?> inventory_col_diff;

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
    private Button usr_btn_add;

    private boolean saleCreated = false;
    private boolean purchaseCreated = false;
    private boolean saleDeleted = false;
    private boolean inventoryCreated = false;

    @FXML
    private Button usr_btn_delete;

    @FXML
    private Button usr_btn_edit;

    @FXML
    private TableView<User> user_table;

    @FXML
    private TableColumn<?, ?> usr_col_username;

    @FXML
    private TableColumn<?, ?> usr_col_email;

    @FXML
    private TableColumn<?, ?> usr_col_rol;

    @FXML
    private TableColumn<?, ?> usr_col_phone;

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
    CategoryDAO comboCategory = new CategoryDAOImpl();
    LocationDAO comboLocation = new LocationDAOImpl();
    SupplierDAO comboSupplier = new SupplierDAOImpl();
    SalesDAO salesDAO = new SalesDAOImpl();
    UserDAO userDAO = new UserDAOImpl();
    InventoryDAO inventoryDAO = new InventoryDAOImpl();
    PurchaseDAO purchaseDAO = new PurchaseDAOImpl();
    ObservableList<Product> productsList = productDAO.getProductsList();

    ErrorMesajes error = new ErrorMesajes();

    public DashboardController() throws Exception {
    }

    public void onExit() {
        System.exit(0);
    }

    public void activateDashboard(){
        dashboard_pane.setVisible(true);
        billing_pane.setVisible(false);
        product_pane.setVisible(false);
        new_inventory_pane.setVisible(false);
        user_pane.setVisible(false);
        invoice_pane.setVisible(false);
        purchase_pane.setVisible(false);
        UserDAO userid = new UserDAOImpl();
        String hello = userid.getUserById();
        System.out.println(hello);
    }

    public void activateAnchorPane() {
        dashboard_btn.setOnMouseClicked(mouseEvent -> {
            dashboard_pane.setVisible(true);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(false);
            billing_pane.setVisible(false);
            user_pane.setVisible(false);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        billing_btn.setOnMouseClicked(mouseEvent -> {
            dashboard_pane.setVisible(false);
            billing_pane.setVisible(true);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(false);
            user_pane.setVisible(false);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        product_btn.setOnMouseClicked(mouseEvent -> {
            dashboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            product_pane.setVisible(true);
            new_inventory_pane.setVisible(false);
            user_pane.setVisible(false);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(false);
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        new_inventory_btn.setOnMouseClicked(mouseEvent -> {
            dashboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(true);
            user_pane.setVisible(false);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        user_btn.setOnMouseClicked(mouseEvent -> {
            dashboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(false);
            user_pane.setVisible(true);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        invoice_btn.setOnMouseClicked(mouseEvent -> {
            dashboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(false);
            user_pane.setVisible(false);
            invoice_pane.setVisible(true);
            purchase_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        });
        purchase_btn.setOnMouseClicked(mouseEvent -> {
            dashboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            product_pane.setVisible(false);
            new_inventory_pane.setVisible(false);
            user_pane.setVisible(false);
            invoice_pane.setVisible(false);
            purchase_pane.setVisible(true);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            invoice_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            new_inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            product_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
        });
    }

    public void setUsername() throws Exception {
        user.setText(userDAO.getUsername());
    }

    private void checkUserRole() {
        User loggedInUser = Session.getCurrentUser();
        if ("admin".equals(loggedInUser.getRol())) {
            usr_btn_add.setVisible(true);
            usr_btn_delete.setVisible(true);
            usr_btn_edit.setVisible(true);
        } else {
            usr_btn_add.setVisible(false);
            usr_btn_delete.setVisible(false);
            usr_btn_edit.setVisible(false);
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
        ComboBox<Supplier> supplierCombo = comboSupplier.getComboSupplier();
        Label lblLoc = new Label("LUGAR:");
        ComboBox<Location> locationCombo = comboLocation.getComboLocation();

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

    public void addProduct() throws Exception {

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
        ComboBox<Category> categoryList = comboCategory.getComboCategory();
        Label lblQty = new Label("Cantidad:");
        TextField prod_field_qty = new TextField();
        Label lblDate = new Label("Vencimiento:");
        DatePicker expDate = new DatePicker();
        Label lblSupp = new Label("Proveedor:");
        ComboBox<Supplier> supplierCombo = comboSupplier.getComboSupplier();
        Label lblLoc = new Label("Location:");
        ComboBox<Location> locationCombo = comboLocation.getComboLocation();

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
            try {
                productDAO.addProduct(
                        prod_field_name.getText(),
                        cat_id,
                        Integer.parseInt(prod_field_qty.getText()),
                        Double.parseDouble(prod_field_price.getText()),
                        date_exp,
                        unitSelected,
                        supp_id,
                        loc_id
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

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void createNewSale() throws SQLException {
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
        User loggedInUser = Session.getCurrentUser();
        int userId = loggedInUser.getId();
        LocalDate date = LocalDate.now();
        Date dateSale = Date.valueOf(date);
        salesDAO.insertNewSale(String.valueOf(dateSale), userId);
        saleCreated = true;
    }

    public int getSalesId() {
        int salesId = 0;
        if (saleCreated) {
            salesId = salesDAO.getSalesId();
        }
        return salesId;
    }

    private boolean existsProductId() {
        String input = bill_item.getText();
        int productId = Integer.parseInt(input);
        return productsList.stream().anyMatch(product -> product.getId() == productId);
    }

    public void addProductSales() throws SQLException {

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
                salesClearData();
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
            salesDAO.insertNewDetailsSales(salId, productId, quantity);
        } catch (Exception err) {
            err.printStackTrace();
        }
        salesClearData();
        showSalesData();
    }

    public void calculateFinalAmount() throws SQLException {
        int salId = getSalesId();
        final_amount.setText(String.valueOf(salesDAO.calculateFinalAmount(salId)));
    }

    public void showSalesData() throws SQLException {
        int salId = getSalesId();
        ObservableList<SalesDetails> salesList = salesDAO.getSalesList(salId);
        col_bill_item_num.setCellValueFactory(new PropertyValueFactory<>("productId"));
        col_bill_item_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_bill_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_bill_price.setCellValueFactory(new PropertyValueFactory<>("prodPrice"));
        col_bill_total_amt.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        billing_table.setItems(salesList);

        if (!salesList.isEmpty()) {
            calculateFinalAmount();
        } else {
            final_amount.setText("0.00");
        }
    }

    public void salesClearData() {
        bill_item.clear();
        sales_quantity.clear();
    }

    public void cancelSale() {
        int salId = getSalesId();
        if (salId != 0) {
            salesDAO.deleteSale(salId);
            salesClearData();
            billing_table.getItems().clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Aun no ha hecho una venta !!");
            alert.showAndWait();
        }
    }

    public void deleteSalesData() throws Exception {
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
        salesDAO.deleteSalesDetails(prodId, salId);
        saleDeleted = true;
        updateProductStock(prodId, qtyUpdate);
        showSalesData();
    }

    public void saleSave() {

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

    public void showUsersData() {
        ObservableList<User> userList = userDAO.getUsersList();
        usr_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        usr_col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        usr_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        usr_col_rol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        user_table.setItems(userList);
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
            if (!checkForUserAvailability(usr_field_username.getText()) && !checkForEmailAvailability(usr_field_email.getText())) {
                if (checkPassword(usr_field_pass1.getText(), usr_field_pass2.getText())) {
                    String hashedPassword = Password.hash(usr_field_pass1.getText()).withBcrypt().getResult();

                    try {
                        userDAO.createNewUser(
                                usr_field_username.getText(),
                                hashedPassword,
                                usr_field_email.getText(),
                                usr_field_phone.getText(),
                                rol
                                );
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("USUARIO CREADO CORRECTAMENTE.");
                    alert.showAndWait();
                    btnSave.getScene().getWindow().hide();
                    showUsersData();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Las password deben ser iguales.");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("USUARIO Y/O EMAIL YA EXISTEN.");
                alert.showAndWait();
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

    public boolean checkForUserAvailability(String username) {
        ObservableList<User> userList = userDAO.getUsersList();
        return userList.stream()
                .anyMatch(user -> user.getUsername().contains(username));
    }

    public boolean checkForEmailAvailability(String email) {
        ObservableList<User> userList = userDAO.getUsersList();
        return userList.stream()
                .anyMatch(user -> user.getEmail().contains(email));
    }

    public void updateUserData() {
        if (user_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecciona un usuario.");
            alert.showAndWait();
            return;
        }
        String user = user_table.getSelectionModel().getSelectedItem().getUsername();
        String email = user_table.getSelectionModel().getSelectedItem().getEmail();
        String phone = user_table.getSelectionModel().getSelectedItem().getPhone();
        String rol = user_table.getSelectionModel().getSelectedItem().getRol();

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
                if (checkPassword(usr_field_pass1.getText(), usr_field_pass2.getText())) {
                    String hashedPassword = Password.hash(usr_field_pass1.getText()).withBcrypt().getResult();
                    try {
                        userDAO.updateUser(
                                user,
                                usr_field_email.getText(),
                                usr_field_phone.getText(),
                                roln,
                                hashedPassword
                        );
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    btnSave.getScene().getWindow().hide();
                    showUsersData();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Las password deben ser iguales.");
                    alert.showAndWait();
                }
            btnSave.getScene().getWindow().hide();
            showUsersData();

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

    public void deleteUser() {
        if (user_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecciona un usuario.");
            alert.showAndWait();
            return;
        }
        String userName = user_table.getSelectionModel().getSelectedItem().getUsername();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Esta seguro de eliminar a este usuario ?");
        confirmationAlert.setContentText("usuario: " + userName.toUpperCase());
        Optional<ButtonType> result1 = confirmationAlert.showAndWait();

        if (result1.isPresent() && result1.get() == ButtonType.OK) {
            userDAO.deleteUser(userName);
            showUsersData();
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

    private void showInvoiceData() throws SQLException {
        ObservableList<Purchase> purchaseList = purchaseDAO.getPurchaseList();

        invoice_col_id.setCellValueFactory(new PropertyValueFactory<>("purchId"));
        invoice_col_username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        invoice_col_date.setCellValueFactory(new PropertyValueFactory<>("dateOfPurchase"));
        invoice_col_total.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        invoice_table.setItems(purchaseList);
    }

    //========================NEW INVENTORY METHODS============================
    private void showInventoryTable() throws SQLException {
        ObservableList<Product> inventoryList = inventoryDAO.getProductsList();
        for (Product prod : inventoryList) {
            int diff = prod.getQuantity();
            prod.setDiff(-diff);
            System.out.println(prod);
        }
        new_inventory_table.setEditable(true);
        inventory_col_prod.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        inventory_col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        inventory_col_real_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        inventory_col_diff.setCellValueFactory(new PropertyValueFactory<>("diff"));
        inventory_col_real_qty.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
        inventory_col_real_qty.setOnEditCommit(event -> {
            Product product = event.getRowValue();
            product.setQty(event.getNewValue());
            int actualQty = product.getQuantity();
            int realQty = product.getQty();
            product.setDiff(realQty-actualQty);
        });
        new_inventory_table.setItems(inventoryList);
    }
    public void newInventory() throws SQLException {

        if(!inventoryCreated){
            showInventoryTable();
            inventoryCreated = true;

        }else{
            error.getInfo("YA TIENE UNA SESION ABIERTA");
        }
    }

    public void cancelInventory(){
            if (inventoryCreated){
                inventoryCreated = false;
                new_inventory_table.getItems().clear();
            }
    }

    public void saveInventory(){
        if(inventoryCreated){
            Optional<ButtonType> result1 = error.getConfirm("ESTA SEGURO DE PROCESAR ESTE INVENTARIO ?");
            if (result1.isPresent() && result1.get() == ButtonType.OK) {
                inventoryDAO.createNewInventory();
                inventoryCreated = false;
                new_inventory_table.getItems().clear();
            }
        }
    }

    //========================PURCHASE METHODS============================

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

    public void showProductsToPurchase(boolean status) throws SQLException {
        search_prod_purchase.textProperty().addListener((observable, oldValue, newValue) -> filterProductsToPurchase(newValue));
        ObservableList<Product> purchaseList = productDAO.getProductsListPurchase();

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
                        try {
                            cancelPurchase();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
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
        int purchaseId = purchaseDAO.getPurchaseId();
        purchaseDAO.deletePurchaseItem(pId, purchaseId);
    }

    private void updatePurchaseItem(int pId, int qty) {
        int purchaseId = purchaseDAO.getPurchaseId();
        purchaseDAO.updatePurchaseItem(qty, pId, purchaseId);
        getTotalPurchaseAmount();
    }

    public void insertPurchaseItem(int pId, int qty) {

        int purchaseId = purchaseDAO.getPurchaseId();
        purchaseDAO.insertPurchaseItem(purchaseId, pId, qty);
        purchaseCreated = true;
        getTotalPurchaseAmount();
    }

    public void cancelPurchase() throws SQLException {
        int purchaseId = purchaseDAO.getPurchaseId();
        if (purchaseCreated) {

            purchaseDAO.cancelPurchase(purchaseId);
            purchaseCreated = false;
            showProductsToPurchase(false);
            search_prod_purchase.setEditable(false);
        }
    }

    public void newPurchase() throws SQLException {

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
        User loggedInUser = Session.getCurrentUser();
        int userId = loggedInUser.getId();
        LocalDate date = LocalDate.now();
        String datePurchase = String.valueOf(Date.valueOf(date));

        if (!purchaseCreated) {
            purchaseDAO.createPurchase(userId, datePurchase);
            purchaseCreated = true;
        }
    }

    private void getTotalPurchaseAmount() {
        int purchaseID = purchaseDAO.getPurchaseId();
        String result = purchaseDAO.getPurchaseAmount(purchaseID);
        if (Objects.equals(result, "")) {
            purchase_total_amount.setText("0.00");
        } else {
            purchase_total_amount.setText("S/. "+result);
        }
    }

    public void savePurchase() throws SQLException {
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
        String result  = String.valueOf(purchaseDAO.getTotalPurchase());
        dash_total_purchase.setText(result);
    }


    private void getTotalSales() {
        String totalSales = String.valueOf(salesDAO.getTotalSales());
        dash_total_sold.setText(totalSales);
    }

    private String getSpanishMonth(String month) {
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
        String monthSpanish = getSpanishMonth(monthEnglish);
        String total = String.valueOf(salesDAO.getSalesActualMonth(monthEnglish));
        dash_total_items_sold_this_month.setText(total);
        dash_total_sales_this_month_name.setText(monthSpanish);
    }


    private void showDashboardData(){
            getTotalPurchase();
            getTotalSales();
            //getTotalStocks();
            getSalesDetailsOfThisMonth();
    }

    public void signOut() {
        signout_btn.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml")));
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
        showUsersData();
//      PRODUCTS PANE
        try {
            showProductsData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//      INVOICE PANE
        try {
            showInvoiceData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}