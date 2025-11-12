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

            VBox box = new VBox(5);
            box.setStyle("-fx-border-color: limegreen; -fx-border-width: 2; -fx-background-color: black;");
            box.setPadding(new Insets(10));
            box.setAlignment(Pos.CENTER);
            Label nameLabel = new Label(itemName.replace("SpareParts", "Spare Parts"));
            nameLabel.setFont(Font.font("Rockwell", 20));
            nameLabel.setTextFill(Color.LIMEGREEN);
            Label stockLabel = new Label("Stock: " + stock);
            stockLabel.setTextFill(Color.LIMEGREEN);
            stockLabels.put(itemName, stockLabel);
            Label priceLabel = new Label("Cost: ¢" + price);
            priceLabel.setTextFill(Color.LIMEGREEN);
            HBox qtyBox = new HBox(5);
            qtyBox.setAlignment(Pos.CENTER);
            Button minusBtn = new Button("-");
            minusBtn.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen;");
            TextField qtyField = new TextField("0");
            qtyField.setPrefWidth(30);
            qtyField.setAlignment(Pos.CENTER);
            qtyField.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen;");
            qtyFields.put(itemName, qtyField);
            Button plusBtn = new Button("+");
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

        // Coins, confirm purchase, exit
        Label coinsLabel = new Label("Coins: ¢" + supplies.getCoin());
        coinsLabel.setFont(Font.font("Rockwell", 18));
        coinsLabel.setTextFill(Color.LIMEGREEN);
        Button confirmBtn = new Button("Confirm Purchase");
        confirmBtn.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-font-size: 16px;");
        Button exitBtn = new Button("Exit");
        exitBtn.setStyle("-fx-background-color: black; -fx-text-fill: limegreen; -fx-border-color: limegreen; -fx-font-size: 16px;");
        VBox rightBox = new VBox(15, coinsLabel, confirmBtn, exitBtn);
        rightBox.setAlignment(Pos.CENTER_LEFT);
        rightBox.setPadding(new Insets(10, 0, 0, 30));

        // Total cost
        Label totalCostLabel = new Label("Total Cost: ¢0");
        totalCostLabel.setFont(Font.font("Rockwell", 18));
        totalCostLabel.setTextFill(Color.LIMEGREEN);
        totalCostLabel.setAlignment(Pos.CENTER);
        VBox centerBox = new VBox(10, itemGrid, totalCostLabel);
        centerBox.setAlignment(Pos.CENTER);

        // Inventory table
        GridPane invGrid = new GridPane();
        invGrid.setStyle("-fx-border-color: limegreen; -fx-border-width: 2; -fx-background-color: black;");
        invGrid.setAlignment(Pos.CENTER);
        invGrid.setPadding(new Insets(10));
        invGrid.setHgap(20);
        invGrid.setVgap(5);
        Label invTitle = new Label("Current Inventory");
        invTitle.setFont(Font.font("Rockwell", 18));
        invTitle.setTextFill(Color.LIMEGREEN);
        invGrid.add(invTitle, 0, 0, 5, 1);
        String[] invNames = {"Food", "Water", "Ammo", "Medicine", "SpareParts"};
        HashMap<String, Label> invQtyLabels = new HashMap<>();
        for (int i = 0; i < invNames.length; i++) {
            Label name = new Label(invNames[i].replace("SpareParts", "Spare Parts"));
            name.setTextFill(Color.LIMEGREEN);
            invGrid.add(name, i, 1);
        }
        for (int i = 0; i < invNames.length; i++) {
            int val = 0;
            switch (invNames[i]) {
                case "Food": val = supplies.getFood(); break;
                case "Water": val = supplies.getWater(); break;
                case "Ammo": val = supplies.getAmmo(); break;
                case "Medicine": val = supplies.getMedicine(); break;
                case "SpareParts": val = supplies.getSpareParts(); break;
            }
            Label qty = new Label(String.valueOf(val));
            qty.setTextFill(Color.LIMEGREEN);
            invGrid.add(qty, i, 2);
            invQtyLabels.put(invNames[i], qty);
        }
        VBox invBox = new VBox(invGrid);
        invBox.setAlignment(Pos.CENTER);
        invBox.setPadding(new Insets(20, 0, 0, 0));

        // Layout
        HBox mainBox = new HBox(40, centerBox, rightBox);
        mainBox.setAlignment(Pos.CENTER);
        VBox allBox = new VBox(mainBox, invBox);
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
                // Update inventory table
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