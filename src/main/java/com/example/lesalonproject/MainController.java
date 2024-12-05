package com.example.lesalonproject;

import com.example.lesalonproject.service.MNBArfolyamServiceSoap;
import com.example.lesalonproject.service.MNBArfolyamServiceSoapImpl;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeCloseResponse;
import com.oanda.v20.trade.TradeSpecifier;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class MainController {

    private Context ctx = new Context(Config.URL, Config.TOKEN);

    @FXML
    private LineChart<String, Number> realTimeChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    private XYChart.Series<String, Number> series;
    @FXML
    private TableView<MarketPosition> positionTableView;
    @FXML
    private TableColumn<MarketPosition, String> currencyColumn;
    @FXML
    private TableColumn<MarketPosition, Integer> quantityColumn;
    @FXML
    private TableColumn<MarketPosition, String> statusColumn;

    @FXML
    private StackPane contentPane;
    public void handleParallels(){
        loadView("parallelProgrammingView.fxml");
    }

    public void handleRelationships(){
        loadView("RelationshipView.fxml");
    }

    public void handleOsDelete(){
        loadView("opsystemDeleteView.fxml");
    }

    public void handleOsUpdate(){
        loadView("opsystemUpdateView.fxml");
    }
    public void handleOsCreate(){
        loadView("opsystemCreateView.fxml");
    }
    public void handleOsRead(){
        loadView("opsystemReadView.fxml");
    }

    public void handleProcessorDelete(){
        loadView("processDeleteView.fxml");
    }

    public void handleProcessorUpdate(){
        loadView("processorUpdateView.fxml");
    }


    public void handleProcessorCreate(){
        loadView("processorCreateView.fxml");
    }

    public void handleProcessorRead(){
        loadView("processorReadView.fxml");
    }

    public void handleRead(){
        loadView("read_view.fxml");
    }

    public void handleRead2(){
        loadView("read2_view.fxml");
    }

    public void handleWrite(){
        loadView("write_view.fxml");
    }

    public void handleChange(){
        loadView("change_view.fxml");
    }

    public void handleDelete(){
        loadView("delete_view.fxml");
    }

    private void loadView(String fxmlFile){
        try{
            Node view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            contentPane.getChildren().setAll(view);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    // SOAP Client
    @FXML
    // This method is called when the "Download" menu is clicked
    private void openDownloadForm(ActionEvent actionEvent) {
        // Create a new window
        Stage downloadStage = new Stage();
        downloadStage.setTitle("Download SOAP Data");

        // Create input fields
        Label startDateLabel = new Label("Start Date:");
        DatePicker startDatePicker = new DatePicker(); // Use DatePicker for start date

        Label endDateLabel = new Label("End Date:");
        DatePicker endDatePicker = new DatePicker(); // Use DatePicker for end date

        Label currencyLabel = new Label("Currency:");
        ComboBox<String> currencyComboBox = new ComboBox<>();
        currencyComboBox.getItems().addAll("USD", "EUR", "HUF"); // Available currencies

        // Create a ProgressBar
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(200);

        // Create a button to start the download
        Button downloadButton = new Button("Download Data");
        downloadButton.setOnAction(e -> {
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            String selectedCurrency = currencyComboBox.getValue();

            if (startDate == null || endDate == null || selectedCurrency == null) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            // Validate that the start date is not after the end date
            if (startDate.isAfter(endDate)) {
                showAlert("Error", "Start date cannot be after the end date.");
                return;
            }

            // Start the download in a new thread to update the ProgressBar
            Thread downloadThread = new Thread(() -> {
                try {
                    // Simulate progress updates
                    for (int i = 0; i <= 100; i++) {
                        double progress = i / 100.0;
                        Thread.sleep(10); // Simulate progress delay
                        final int currentProgress = i;

                        // Update ProgressBar in the JavaFX Application Thread
                        Platform.runLater(() -> progressBar.setProgress(progress));
                    }

                    // Perform the actual download
                    String exchangeRates = downloadSoapData(startDate.toString(), endDate.toString(), selectedCurrency);

                    // Save the data to a file
                    saveDataToFile(exchangeRates);

                    // Show success alert (must run on JavaFX Application Thread)
                    Platform.runLater(() ->
                            showAlert("Download Successful", "All data has been downloaded to bank.txt.")
                    );

                } catch (Exception ex) {
                    Platform.runLater(() ->
                            showAlert("Error", "An error occurred during the download: " + ex.getMessage())
                    );
                }
            });

            downloadThread.start();
        });

        // Add components to the new window
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                startDateLabel, startDatePicker,
                endDateLabel, endDatePicker,
                currencyLabel, currencyComboBox,
                progressBar, downloadButton
        );

        // Configure the scene and show the new window
        Scene scene = new Scene(vbox, 300, 350);
        downloadStage.setScene(scene);
        downloadStage.show();
    }

    // Method to call the SOAP service and fetch exchange rates
    private String downloadSoapData(String startDate, String endDate, String selectedCurrency) throws Exception {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

        // Call the SOAP service to get exchange rates
        return service.getExchangeRates(startDate, endDate, selectedCurrency);
    }

    // Method to save the downloaded data directly to the project directory
    private void saveDataToFile(String data) {
        // Get the current project directory
        String projectDirectory = System.getProperty("user.dir");

        // Create the file path inside the project directory
        File file = new File(projectDirectory + File.separator + "bank.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            // Write the data to the file (overwrites the file if it already exists)
            writer.write(data);
        } catch (IOException e) {
            // Show error if unable to save the file
            showAlert("Error", "Failed to save the file: " + e.getMessage());
        }
    }

    @FXML
    private void downloadFilteredSoapData() {
        // Create a new stage for the form
        Stage filterStage = new Stage();
        filterStage.setTitle("Download Filtered SOAP Data");

        // Create input components
        Label startDateLabel = new Label("Start Date:");
        DatePicker startDatePicker = new DatePicker();

        Label endDateLabel = new Label("End Date:");
        DatePicker endDatePicker = new DatePicker();

        Label currencyLabel = new Label("Currency:");
        ComboBox<String> currencyComboBox = new ComboBox<>();
        currencyComboBox.getItems().addAll("USD", "EUR", "HUF");

        Label optionsLabel = new Label("Additional Options:");
        CheckBox includeMetaDataCheckbox = new CheckBox("Include Metadata");
        RadioButton detailedDataRadio = new RadioButton("Detailed Data");
        RadioButton summaryDataRadio = new RadioButton("Summary Data");

        // Group radio buttons
        ToggleGroup dataOptionsGroup = new ToggleGroup();
        detailedDataRadio.setToggleGroup(dataOptionsGroup);
        summaryDataRadio.setToggleGroup(dataOptionsGroup);

        // Add a ProgressBar
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        progressBar.setVisible(false); // Hidden initially

        // Create download button
        Button downloadButton = new Button("Download Data");
        downloadButton.setOnAction(e -> {
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            String selectedCurrency = currencyComboBox.getValue();
            boolean includeMetaData = includeMetaDataCheckbox.isSelected();
            RadioButton selectedDataOption = (RadioButton) dataOptionsGroup.getSelectedToggle();

            if (startDate == null || endDate == null || selectedCurrency == null || selectedDataOption == null) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            if (startDate.isAfter(endDate)) {
                showAlert("Error", "Start date cannot be after end date.");
                return;
            }

            String dataOption = selectedDataOption.getText();

            // Start the SOAP data download
            progressBar.setVisible(true);
            downloadFilteredData(startDate.toString(), endDate.toString(), selectedCurrency, includeMetaData, dataOption, progressBar);
        });

        // Arrange components in a layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                startDateLabel, startDatePicker,
                endDateLabel, endDatePicker,
                currencyLabel, currencyComboBox,
                optionsLabel, includeMetaDataCheckbox, detailedDataRadio, summaryDataRadio,
                progressBar, downloadButton
        );

        // Set the scene and show the stage
        Scene scene = new Scene(vbox, 350, 400);
        filterStage.setScene(scene);
        filterStage.show();
    }

    // Method to handle SOAP data download based on filters
    private void downloadFilteredData(String startDate, String endDate, String currency, boolean includeMetaData, String dataOption, ProgressBar progressBar) {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

        try {
            // Simulate data download progress
            for (int i = 0; i <= 100; i += 10) {
                final int progress = i;
                Thread.sleep(50); // Simulate processing delay
                progressBar.setProgress(progress / 100.0);
            }

            // Call SOAP service to get filtered data
            String exchangeRates = service.getExchangeRates(startDate, endDate, currency);

            // Add metadata or modify data if required
            if (includeMetaData) {
                exchangeRates += "\nMetadata: Data generated with option " + dataOption;
            }

            // Save data to Bank.txt
            saveDataToFile(exchangeRates);

            showAlert("Download Successful", "Filtered data has been saved to Bank.txt.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred during the download: " + e.getMessage());
        }
    }

    @FXML
    // Method to graph the data
    public void graphSoapData(ActionEvent actionEvent) {
        // Create a new window for the graph form
        Stage graphStage = new Stage();
        graphStage.setTitle("Graph SOAP Data");

        // Input fields and controls
        Label startDateLabel = new Label("Start Date:");
        DatePicker startDatePicker = new DatePicker();

        Label endDateLabel = new Label("End Date:");
        DatePicker endDatePicker = new DatePicker();

        Label currencyLabel = new Label("Currency:");
        ComboBox<String> currencyComboBox = new ComboBox<>();
        currencyComboBox.getItems().addAll("USD", "EUR", "HUF");

        CheckBox includeMetaDataCheckBox = new CheckBox("Include Metadata");

        Button generateGraphButton = new Button("Generate Graph");

        // Graph container
        VBox graphContainer = new VBox(10);

        generateGraphButton.setOnAction(e -> {
            String startDate = startDatePicker.getValue() != null ? startDatePicker.getValue().toString() : "";
            String endDate = endDatePicker.getValue() != null ? endDatePicker.getValue().toString() : "";
            String selectedCurrency = currencyComboBox.getValue();

            if (startDate.isEmpty() || endDate.isEmpty() || selectedCurrency == null) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }

            // Clear previous graph, if any
            graphContainer.getChildren().clear();

            // Generate a new graph
            LineChart<String, Number> lineChart = generateGraph(startDate, endDate, selectedCurrency, includeMetaDataCheckBox.isSelected());
            graphContainer.getChildren().add(lineChart);
        });

        // Layout setup
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                startDateLabel, startDatePicker,
                endDateLabel, endDatePicker,
                currencyLabel, currencyComboBox,
                includeMetaDataCheckBox,
                generateGraphButton,
                graphContainer
        );

        Scene scene = new Scene(vbox, 800, 600);
        graphStage.setScene(scene);
        graphStage.show();
    }

    private LineChart<String, Number> generateGraph(String startDate, String endDate, String currency, boolean includeMetaData) {
        // Simulated data fetching and processing
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();

        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            data.add(new XYChart.Data<>("Day " + i, random.nextDouble() * 100)); // Simulated exchange rate
        }

        // Graph series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Exchange Rate for " + currency + " (" + startDate + " to " + endDate + ")");
        series.setData(data);

        // Include metadata, if selected
        if (includeMetaData) {
            series.setName(series.getName() + " - Metadata Included");
        }

        // Line chart setup
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Exchange Rate");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.getData().add(series);
        lineChart.setTitle("Currency Exchange Rates");

        return lineChart;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Oanda API
    public void accountInformationAction(ActionEvent actionEvent) {
        // Crie um contexto com a URL e Token fornecidos
        Context ctx = new Context("https://api-fxpractice.oanda.com", Config.TOKEN);

        // Janela de progresso
        Stage progressStage = new Stage();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        Label progressLabel = new Label("Loading account information...");
        GridPane progressPane = new GridPane();
        progressPane.setHgap(10);
        progressPane.setVgap(10);
        progressPane.add(progressIndicator, 0, 0);
        progressPane.add(progressLabel, 0, 1);
        Scene progressScene = new Scene(progressPane, 300, 200);
        progressStage.setScene(progressScene);
        progressStage.setTitle("Please Wait");
        progressStage.show();

        // Tarefa para buscar dados em segundo plano
        Task<AccountSummary> task = new Task<>() {
            @Override
            protected AccountSummary call() throws Exception {
                // Solicita um resumo da conta usando o AccountID
                return ctx.account.summary(new AccountID(Config.ACCOUNTID)).getAccount();
            }
        };

        // Quando a tarefa for concluída com sucesso
        task.setOnSucceeded(workerStateEvent -> {
            progressStage.close(); // Fecha o indicador de progresso
            AccountSummary summary = task.getValue();

            // Cria uma nova janela para exibir as informações
            Stage infoStage = new Stage();
            GridPane infoPane = new GridPane();
            infoPane.setHgap(10);
            infoPane.setVgap(10);

            // Adiciona as informações formatadas
            infoPane.add(new Label("Account ID:"), 0, 0);
            infoPane.add(new Label(summary.getId().toString()), 1, 0);

            infoPane.add(new Label("Alias:"), 0, 1);
            infoPane.add(new Label(summary.getAlias()), 1, 1);

            infoPane.add(new Label("Currency:"), 0, 2);
            infoPane.add(new Label(summary.getCurrency().toString()), 1, 2);

            infoPane.add(new Label("Balance:"), 0, 3);
            infoPane.add(new Label(summary.getBalance().toString()), 1, 3);

            infoPane.add(new Label("NAV:"), 0, 4);
            infoPane.add(new Label(summary.getNAV().toString()), 1, 4);

            infoPane.add(new Label("Unrealized P/L:"), 0, 5);
            infoPane.add(new Label(summary.getUnrealizedPL().toString()), 1, 5);

            infoPane.add(new Label("Margin Rate:"), 0, 6);
            infoPane.add(new Label(summary.getMarginRate().toString()), 1, 6);

            infoPane.add(new Label("Open Trades:"), 0, 7);
            infoPane.add(new Label(String.valueOf(summary.getOpenTradeCount())), 1, 7);

            // Cria a cena da janela de informações
            Scene infoScene = new Scene(infoPane, 400, 300);
            infoStage.setScene(infoScene);
            infoStage.setTitle("Account Information");
            infoStage.show();
        });

        // Caso ocorra uma falha no carregamento
        task.setOnFailed(workerStateEvent -> {
            progressStage.close(); // Fecha o indicador de progresso
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Load Account Information");
            alert.setContentText(task.getException().getMessage());
            alert.showAndWait();
        });

        // Executa a tarefa em uma nova thread
        new Thread(task).start();
    }

    public void currentPricesAction(ActionEvent actionEvent) {
        // Create a new stage for the "Current Prices" submenu
        Stage priceStage = new Stage();
        priceStage.setTitle("Current Prices");

        // Main layout with centered elements
        VBox mainLayout = new VBox(15); // 15px spacing between elements
        mainLayout.setAlignment(Pos.CENTER); // Center-align all elements
        mainLayout.setPadding(new Insets(20)); // Internal padding of 20px

        // Section title
        Label titleLabel = new Label("Check Current Prices");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Dropdown for available currency pairs
        ComboBox<String> currencyPairDropdown = new ComboBox<>();
        currencyPairDropdown.getItems().addAll("EUR/USD", "USD/JPY", "GBP/USD", "AUD/USD", "USD/CHF");
        currencyPairDropdown.setPromptText("Select a currency pair");
        currencyPairDropdown.setPrefWidth(200);

        // Button to fetch the current price
        Button fetchPriceButton = new Button("Get Current Price");
        fetchPriceButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px;");

        // Label to display the current price
        Label priceLabel = new Label();
        priceLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333; -fx-alignment: center;");

        // Loading indicator (spinner)
        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setVisible(false); // Initially hidden

        // Add all elements to the main layout
        mainLayout.getChildren().addAll(titleLabel, currencyPairDropdown, fetchPriceButton, loadingIndicator, priceLabel);

        // Set up the scene and display the stage
        Scene scene = new Scene(mainLayout, 400, 300); // Width: 400px, Height: 300px
        priceStage.setScene(scene);
        priceStage.show();

        // Configure the button to fetch prices
        fetchPriceButton.setOnAction(e -> {
            String selectedPair = currencyPairDropdown.getValue();

            if (selectedPair == null) {
                priceLabel.setText("Please select a currency pair.");
                return;
            }

            // Show the loading indicator while fetching data
            loadingIndicator.setVisible(true);
            priceLabel.setText("");

            // Connect to the OANDA API to fetch the price
            Context ctx = new Context("https://api-fxpractice.oanda.com", Config.TOKEN);

            try {
                // Create a request to get current prices
                PricingGetRequest request = new PricingGetRequest(
                        new AccountID(Config.ACCOUNTID),
                        List.of(selectedPair.replace("/", "_"))
                );

                // Execute the request and get the response
                PricingGetResponse pricingResponse = ctx.pricing.get(request);

                // Get the price for the selected currency pair
                // Get the price for the selected currency pair
                ClientPrice clientPrice = pricingResponse.getPrices().get(0);
                String bid = clientPrice.getBids().get(0).getPrice().toString();
                String ask = clientPrice.getAsks().get(0).getPrice().toString();

// Display the prices on the interface
                priceLabel.setText(String.format("Bid: %s | Ask: %s", bid, ask));


                // Display the prices on the interface
                priceLabel.setText(String.format("Bid: %s | Ask: %s", bid, ask));
            } catch (Exception ex) {
                priceLabel.setText("Failed to fetch price.");
                ex.printStackTrace();
            } finally {
                // Hide the loading indicator
                loadingIndicator.setVisible(false);
            }
        });
    }

    public void historicalPricesAction(ActionEvent actionEvent) {
        // Create a new stage for the "Historical Prices" submenu
        Stage historicalStage = new Stage();
        historicalStage.setTitle("Historical Prices");

        // Main layout with centered elements
        VBox mainLayout = new VBox(15); // 15px spacing between elements
        mainLayout.setAlignment(Pos.CENTER); // Center-align all elements
        mainLayout.setPadding(new Insets(20)); // Internal padding of 20px

        // Section title
        Label titleLabel = new Label("Check Historical Prices");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Dropdown for available currency pairs
        ComboBox<String> currencyPairDropdown = new ComboBox<>();
        currencyPairDropdown.getItems().addAll("EUR/USD", "USD/JPY", "GBP/USD", "AUD/USD", "USD/CHF");
        currencyPairDropdown.setPromptText("Select a currency pair");
        currencyPairDropdown.setPrefWidth(200);

        // Date pickers for start and end date
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Start Date");

        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("End Date");

        // Button to fetch historical prices
        Button fetchPriceButton = new Button("Get Historical Prices");
        fetchPriceButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px;");

        // Table to display historical prices
        TableView<PriceData> priceTable = new TableView<>();
        TableColumn<PriceData, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        TableColumn<PriceData, String> bidColumn = new TableColumn<>("Bid");
        bidColumn.setCellValueFactory(cellData -> cellData.getValue().bidProperty());
        TableColumn<PriceData, String> askColumn = new TableColumn<>("Ask");
        askColumn.setCellValueFactory(cellData -> cellData.getValue().askProperty());

        priceTable.getColumns().addAll(dateColumn, bidColumn, askColumn);

        // Add all elements to the main layout
        mainLayout.getChildren().addAll(titleLabel, currencyPairDropdown, startDatePicker, endDatePicker, fetchPriceButton, priceTable);

        // Set up the scene and display the stage
        Scene scene = new Scene(mainLayout, 600, 400); // Width: 600px, Height: 400px
        historicalStage.setScene(scene);
        historicalStage.show();

        // Configure the button to fetch historical prices
        fetchPriceButton.setOnAction(e -> {
            String selectedPair = currencyPairDropdown.getValue();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            if (selectedPair == null || startDate == null || endDate == null) {
                // Check if any field is empty
                showError("Please select a currency pair and dates.");
                return;
            }

            // Connect to the OANDA API to fetch historical prices
            Context ctx = new Context("https://api-fxpractice.oanda.com", Config.TOKEN);

            try {
                // Create a request to get historical prices
                PricingGetRequest request = new PricingGetRequest(
                        new AccountID(Config.ACCOUNTID),
                        List.of(selectedPair.replace("/", "_"))
                );

                // Execute the request and get the response
                PricingGetResponse pricingResponse = ctx.pricing.get(request);

                // Process the response to get prices between the selected dates
                List<PriceData> historicalPrices = new ArrayList<>();
                for (ClientPrice clientPrice : pricingResponse.getPrices()) {
                    // Assuming the historical data is available in the ClientPrice object
                    String bid = clientPrice.getBids().get(0).getPrice().toString();
                    String ask = clientPrice.getAsks().get(0).getPrice().toString();
                    // Populate the list with the historical data
                    historicalPrices.add(new PriceData(clientPrice.getTime().toString(), bid, ask));
                }

                // Update the table with the historical prices
                priceTable.getItems().setAll(historicalPrices);
            } catch (Exception ex) {
                showError("Failed to fetch historical prices.");
                ex.printStackTrace();
            }
        });
    }

    public void positionOpeningAction(ActionEvent actionEvent) {

        Stage positionStage = new Stage();
        positionStage.setTitle("Open Position");

        // Layout principal com elementos centralizados
        VBox mainLayout = new VBox(15); // 15px de espaçamento entre os elementos
        mainLayout.setAlignment(Pos.CENTER); // Centraliza os elementos
        mainLayout.setPadding(new Insets(20)); // Padding interno de 20px

        // Título da seção
        Label titleLabel = new Label("Open a Position");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Dropdown para pares de moedas disponíveis
        ComboBox<String> currencyPairDropdown = new ComboBox<>();
        currencyPairDropdown.getItems().addAll("EUR/USD", "USD/JPY", "GBP/USD", "AUD/USD", "USD/CHF");
        currencyPairDropdown.setPromptText("Select a currency pair");
        currencyPairDropdown.setPrefWidth(200);

        // Campo de quantidade (Quantia de moedas)
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
        quantityField.setPrefWidth(200);

        // Dropdown para direção (Buy/Sell)
        ComboBox<String> directionDropdown = new ComboBox<>();
        directionDropdown.getItems().addAll("Buy", "Sell");
        directionDropdown.setPromptText("Select direction");
        directionDropdown.setPrefWidth(200);

        // Botão para abrir a posição
        Button openPositionButton = new Button("Open Position");
        openPositionButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px;");

        // Label para exibir o resultado da ação
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333; -fx-alignment: center;");

        // Adiciona todos os elementos no layout principal
        mainLayout.getChildren().addAll(titleLabel, currencyPairDropdown, quantityField, directionDropdown, openPositionButton, resultLabel);

        // Configura a cena e exibe a janela
        Scene scene = new Scene(mainLayout, 400, 300); // Largura: 400px, Altura: 300px
        positionStage.setScene(scene);
        positionStage.show();

        // Configura o evento do botão para abrir a posição
        openPositionButton.setOnAction(e -> {
            String selectedPair = currencyPairDropdown.getValue();
            String quantityText = quantityField.getText();
            String direction = directionDropdown.getValue();

            // Verifica se os campos necessários foram preenchidos
            if (selectedPair == null || quantityText.isEmpty() || direction == null) {
                resultLabel.setText("Please fill in all fields.");
                return;
            }

            // Tenta converter a quantidade para um número
            double quantity;
            try {
                quantity = Double.parseDouble(quantityText);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid quantity.");
                return;
            }

            // Exemplo de como você pode implementar a lógica para abrir a posição
            // A lógica de abrir a posição pode incluir chamadas à API de negociação, como a OANDA
            try {
                // Aqui você pode adicionar o código para abrir a posição no servidor, por exemplo:
                String action = (direction.equals("Buy")) ? "Buying" : "Selling";
                resultLabel.setText(String.format("Opening %s position for %s with quantity %.2f", action, selectedPair, quantity));

                // Simulação de uma chamada de API para abrir a posição
                // Por exemplo: OANDA API para abrir uma posição (esse é apenas um exemplo)
                // openPositionOnServer(selectedPair, quantity, direction);

            } catch (Exception ex) {
                resultLabel.setText("Failed to open position.");
                ex.printStackTrace();
            }
        });
    }

    public void positionClosingAction(ActionEvent actionEvent) {
        // Create a new stage for the "Position Closing" submenu
        Stage closingStage = new Stage();
        closingStage.setTitle("Close Position");

        // Main layout with centered elements
        VBox mainLayout = new VBox(15); // 15px spacing between elements
        mainLayout.setAlignment(Pos.CENTER); // Center-align all elements
        mainLayout.setPadding(new Insets(20)); // Internal padding of 20px

        // Section title
        Label titleLabel = new Label("Close Position");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Label for Position ID input
        Label positionIdLabel = new Label("Enter Position ID:");

        // Text field to enter the position ID
        TextField positionIdField = new TextField();
        positionIdField.setPromptText("Position ID");

        // Button to close the position
        Button closePositionButton = new Button("Close Position");
        closePositionButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px;");

        // Label to display the result of closing the position
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333; -fx-alignment: center;");

        // Add all elements to the main layout
        mainLayout.getChildren().addAll(titleLabel, positionIdLabel, positionIdField, closePositionButton, resultLabel);

        // Set up the scene and display the stage
        Scene scene = new Scene(mainLayout, 400, 300); // Width: 400px, Height: 300px
        closingStage.setScene(scene);
        closingStage.show();

        // Configure the button to close the position
        closePositionButton.setOnAction(e -> {
            String positionId = positionIdField.getText().trim();

            if (positionId.isEmpty()) {
                resultLabel.setText("Please enter a position ID.");
                return;
            }



            // Connect to the OANDA API
            Context ctx = new Context("https://api-fxpractice.oanda.com", Config.TOKEN);

            try {
                // Use TradeSpecifier to specify the position (TradeID)
                TradeSpecifier tradeSpecifier = new TradeSpecifier(positionId); // Use o ID da posição

                // Create the close request with the AccountID and TradeSpecifier
                TradeCloseRequest closeRequest = new TradeCloseRequest(Config.ACCOUNTID, tradeSpecifier);

                // Execute the request to close the position
                TradeCloseResponse closeResponse = ctx.trade.close(closeRequest);

                // If successful, update the result label
                if (closeResponse != null) {
                    resultLabel.setText("Position closed successfully!");
                } else {
                    resultLabel.setText("Failed to close position.");
                }
            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    public void openedPositionsAction(ActionEvent actionEvent) {
        // Cria uma nova janela para o submenu "Opened Positions"
        Stage openedPositionsStage = new Stage();
        openedPositionsStage.setTitle("Opened Positions");

        // Layout principal com elementos centralizados
        VBox mainLayout = new VBox(15); // 15px de espaçamento entre os elementos
        mainLayout.setAlignment(Pos.CENTER); // Centraliza os elementos
        mainLayout.setPadding(new Insets(20)); // Padding interno de 20px

        // Título da seção
        Label titleLabel = new Label("Opened Positions");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Tabela para exibir as posições abertas
        TableView<Position> positionsTable = new TableView<>();
        positionsTable.setPrefWidth(400);

        // Colunas da tabela
        TableColumn<Position, String> currencyPairColumn = new TableColumn<>("Currency Pair");
        currencyPairColumn.setCellValueFactory(new PropertyValueFactory<>("currencyPair"));

        TableColumn<Position, String> directionColumn = new TableColumn<>("Direction");
        directionColumn.setCellValueFactory(new PropertyValueFactory<>("direction"));

        TableColumn<Position, Double> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Adiciona as colunas na tabela
        positionsTable.getColumns().addAll(currencyPairColumn, directionColumn, quantityColumn);

        // Simula a obtenção das posições abertas (pode ser modificado para buscar dados de uma API ou banco de dados)
        ObservableList<Position> openedPositions = FXCollections.observableArrayList(
                new Position("EUR/USD", "Buy", 1000),
                new Position("GBP/USD", "Sell", 1500),
                new Position("USD/JPY", "Buy", 2000)
        );

        // Adiciona as posições na tabela
        positionsTable.setItems(openedPositions);

        // Adiciona todos os elementos no layout principal
        mainLayout.getChildren().addAll(titleLabel, positionsTable);

        // Configura a cena e exibe a janela
        Scene scene = new Scene(mainLayout, 500, 350); // Largura: 500px, Altura: 350px
        openedPositionsStage.setScene(scene);
        openedPositionsStage.show();
    }
}
