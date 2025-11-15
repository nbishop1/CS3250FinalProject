import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.HashMap;

public class GeneralStorePane extends BorderPane {
    public GeneralStorePane(GameJourney journey, Town town, Player player, Stage primaryStage) {
        setStyle("-fx-background-color: black;");
        GeneralStore store = town.getStore();
        Supplies supplies = player.getSupplies();

        // Title
        Label title = new Label("General Store");
        title.setFont(Font.font("Rockwell", 36));
        title.setTextFill(Color.LIMEGREEN);
        title.setAlignment(Pos.CENTER);
        VBox topBox = new VBox(title);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10, 0, 20, 0));
        setTop(topBox);

        // Item boxes
        GridPane itemGrid = new GridPane();
        itemGrid.setHgap(30);
        itemGrid.setVgap(20);
        itemGrid.setAlignment(Pos.CENTER);

        // Track selected quantities
        HashMap<String, Integer> selectedQty = new HashMap<>();
        HashMap<String, Label> stockLabels = new HashMap<>();
        HashMap<String, TextField> qtyFields = new HashMap<>();
        String[] itemOrder = {"Food", "Water", "Ammo", "Medicine", "SpareParts"};
        int col = 0, row = 0;
        for (String itemName : itemOrder) {
            Item item = store.getItem(itemName);
            int price = store.getPrice(itemName);
            int stock = item != null ? item.getQuantity() : 0;
            selectedQty.put(itemName, 0);

            VBox box = new VBox(12); // More vertical spacing
            box.setStyle("-fx-border-color: limegreen; -fx-border-width: 2; -fx-background-color: black;");
            box.setPadding(new Insets(18));
            box.setAlignment(Pos.CENTER);
            box.setPrefWidth(220);
            box.setPrefHeight(180);
            box.setMinWidth(220);
            box.setMinHeight(180);

            Label nameLabel = new Label(itemName.replace("SpareParts", "Spare Parts"));
            nameLabel.setFont(Font.font("Rockwell", 26));
            nameLabel.setTextFill(Color.LIMEGREEN);
            Label stockLabel = new Label("Stock: " + stock);
            stockLabel.setFont(Font.font("Rockwell", 20));
            stockLabel.setTextFill(Color.LIMEGREEN);
            stockLabels.put(itemName, stockLabel);
            Label priceLabel = new Label("Cost: ¢" + price);
            priceLabel.setFont(Font.font("Rockwell", 20));
            priceLabel.setTextFill(Color.LIMEGREEN);
            HBox qtyBox = new HBox(10);
            qtyBox.setAlignment(Pos.CENTER);
            Button minusBtn = new Button("-");
            minusBtn.setFont(Font.font("Rockwell", 20));
            minusBtn.setPrefWidth(40);
            minusBtn.setPrefHeight(40);
            minusBtn.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen;");
            TextField qtyField = new TextField("0");
            qtyField.setPrefWidth(50);
            qtyField.setFont(Font.font("Rockwell", 20));
            qtyField.setAlignment(Pos.CENTER);
            qtyField.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen;");
            qtyFields.put(itemName, qtyField);
            Button plusBtn = new Button("+");
            plusBtn.setFont(Font.font("Rockwell", 20));
            plusBtn.setPrefWidth(40);
            plusBtn.setPrefHeight(40);
            plusBtn.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen;");
            minusBtn.setOnAction(e -> {
                int val = Integer.parseInt(qtyField.getText());
                if (val > 0) {
                    qtyField.setText(String.valueOf(val - 1));
                    selectedQty.put(itemName, val - 1);
                }
            });
            plusBtn.setOnAction(e -> {
                int val = Integer.parseInt(qtyField.getText());
                if (val < stock) {
                    qtyField.setText(String.valueOf(val + 1));
                    selectedQty.put(itemName, val + 1);
                }
            });
            qtyBox.getChildren().addAll(minusBtn, qtyField, plusBtn);
            box.getChildren().addAll(nameLabel, stockLabel, priceLabel, qtyBox);
            itemGrid.add(box, col++, row);
            if (col == 3) { col = 0; row++; }
        }
        itemGrid.setMaxWidth(Double.MAX_VALUE);

        // Inventory table (now as a single row of VBoxes)
        Label invTitle = new Label("Current Inventory:");
        invTitle.setFont(Font.font("Rockwell", 26));
        invTitle.setTextFill(Color.LIMEGREEN);
        HBox invRow = new HBox(40); // spacing between items
        invRow.setAlignment(Pos.CENTER);
        String[] invNames = {"Food", "Water", "Ammo", "Medicine", "SpareParts"};
        HashMap<String, Label> invQtyLabels = new HashMap<>();
        for (String invName : invNames) {
            VBox itemBox = new VBox(8);
            itemBox.setAlignment(Pos.CENTER);
            Label name = new Label(invName.replace("SpareParts", "Spare Parts"));
            name.setFont(Font.font("Rockwell", 22));
            name.setTextFill(Color.LIMEGREEN);
            int val = 0;
            switch (invName) {
                case "Food": val = supplies.getFood(); break;
                case "Water": val = supplies.getWater(); break;
                case "Ammo": val = supplies.getAmmo(); break;
                case "Medicine": val = supplies.getMedicine(); break;
                case "SpareParts": val = supplies.getSpareParts(); break;
            }
            Label qty = new Label(String.valueOf(val));
            qty.setFont(Font.font("Rockwell", 26));
            qty.setTextFill(Color.LIMEGREEN);
            invQtyLabels.put(invName, qty);
            itemBox.getChildren().addAll(name, qty);
            invRow.getChildren().add(itemBox);
        }
        VBox invBox = new VBox(10, invTitle, invRow);
        invBox.setAlignment(Pos.CENTER);
        invBox.setPadding(new Insets(20, 0, 0, 0));

        // Controls row: Total cost, coins, confirm, exit
        Label totalCostLabel = new Label("Total Cost: ¢0");
        totalCostLabel.setFont(Font.font("Rockwell", 18));
        totalCostLabel.setTextFill(Color.LIMEGREEN);
        totalCostLabel.setAlignment(Pos.CENTER);
        Label coinsLabel = new Label("Coins: ¢" + supplies.getCoin());
        coinsLabel.setFont(Font.font("Rockwell", 18));
        coinsLabel.setTextFill(Color.LIMEGREEN);
        Button confirmBtn = new Button("Confirm Purchase");
        confirmBtn.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-font-size: 16px;");
        Button exitBtn = new Button("Exit");
        exitBtn.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-font-size: 16px;");
        HBox controlsBox = new HBox(40, totalCostLabel, coinsLabel, confirmBtn, exitBtn);
        controlsBox.setAlignment(Pos.CENTER);
        controlsBox.setPadding(new Insets(20, 0, 0, 0));

        VBox centerBox = new VBox(20, itemGrid, controlsBox);
        centerBox.setAlignment(Pos.CENTER);

        // Layout
        VBox allBox = new VBox(centerBox, invBox);
        allBox.setAlignment(Pos.CENTER);
        setCenter(allBox);

        // Update total cost when quantities change
        Runnable updateTotalCost = () -> {
            int total = 0;
            for (String itemName : itemOrder) {
                int qty = Integer.parseInt(qtyFields.get(itemName).getText());
                total += qty * store.getPrice(itemName);
            }
            totalCostLabel.setText("Total Cost: ¢" + total);
        };
        for (String itemName : itemOrder) {
            qtyFields.get(itemName).textProperty().addListener((obs, oldVal, newVal) -> updateTotalCost.run());
        }

        // Confirm purchase logic
        confirmBtn.setOnAction(e -> {
            int total = 0;
            boolean canBuy = true;
            for (String itemName : itemOrder) {
                int qty = Integer.parseInt(qtyFields.get(itemName).getText());
                int price = store.getPrice(itemName);
                Item item = store.getItem(itemName);
                if (qty > item.getQuantity()) canBuy = false;
                total += qty * price;
            }
            if (total > supplies.getCoin()) canBuy = false;
            if (canBuy && total > 0) {
                for (String itemName : itemOrder) {
                    int qty = Integer.parseInt(qtyFields.get(itemName).getText());
                    if (qty > 0) store.buy(player, itemName, qty);
                    qtyFields.get(itemName).setText("0");
                    stockLabels.get(itemName).setText("Stock: " + store.getStock(itemName));
                }
                coinsLabel.setText("Coins: ¢" + supplies.getCoin());
                // Update inventory row
                for (String invName : invNames) {
                    int val = 0;
                    switch (invName) {
                        case "Food": val = supplies.getFood(); break;
                        case "Water": val = supplies.getWater(); break;
                        case "Ammo": val = supplies.getAmmo(); break;
                        case "Medicine": val = supplies.getMedicine(); break;
                        case "SpareParts": val = supplies.getSpareParts(); break;
                    }
                    invQtyLabels.get(invName).setText(String.valueOf(val));
                }
                updateTotalCost.run();
            } else {
                totalCostLabel.setText("Total Cost: ¢" + total + " (Insufficient)");
            }
        });
        exitBtn.setOnAction(event -> primaryStage.getScene().setRoot(new TownPane(journey, primaryStage)));
    }
}