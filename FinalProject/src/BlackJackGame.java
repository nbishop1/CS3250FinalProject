public class BlackJackGame {
    private CardDeck deck;
    private CardDealer dealer;
    private CardHand playerHand;
    private boolean inProgress;
    private String result;
    private int betAmount;
    private int saloonLedger;
    private Player player;

    public BlackJackGame(int initialCoins) {
        this.deck = new CardDeck();
        this.dealer = new CardDealer();
        saloonLedger = initialCoins;
    }

    public BlackJackGame(Player player) {
        this(player.getSupplies().getCoin());
        this.player = player;
    }

    public CardDeck getDeck() { return deck; }
    public CardDealer getDealer() { return dealer; }
    public CardHand getPlayerHand() { return playerHand; }
    public CardHand getDealerHand() { return dealer.getHand(); }
    public boolean isInProgress() { return inProgress; }
    public String getGameResult() { return result; }
    public int getBetAmount() { return betAmount; }
    public int getSaloonLedger() { return saloonLedger; }
    public void setPlayer(Player player) { this.player = player; }
    public int getPlayerCoins() { return player != null ? player.getSupplies().getCoin() : saloonLedger; }

    public void playFor(Player player) {
        deck.reset();
        CardHand playerHand = new CardHand();
        int bet = placeBet(player, 1); // Always bet 1 coin for auto-play
        if (bet == 0) return;
        dealer.startDeal(deck, playerHand);
        while (playerHand.getBestValue() < 17 && !playerHand.isBust()) {
            playerHand.addCard(deck.draw());
        }
        dealer.dealersTurn(deck);
        roundEnd(player, playerHand, dealer.getHand(), bet);
    }

    public int placeBet(Player player, int amount) {
        if (player.getSupplies().getCoin() < amount) return 0;
        player.getSupplies().spendCoin(amount);
        return amount;
    }

    public void roundEnd(Player player, CardHand playerHand, CardHand dealerHand, int bet) {
        int playerValue = playerHand.getBestValue();
        int dealerValue = dealerHand.getBestValue();
        if (playerHand.isBust()) {
            return;
        } else if (dealerHand.isBust() || playerValue > dealerValue) {
            // Player wins: receives double the bet
            player.getSupplies().addCoin(bet * 2);
        } else if (playerValue == dealerValue) {
            // Tie: player gets bet back
            player.getSupplies().addCoin(bet);
        }
        // If dealer wins, player loses bet (already subtracted)
    }

    public void startGame(int bet) {
        deck.reset();
        playerHand = new CardHand();
        dealer.getHand().clear();
        betAmount = bet;
        saloonLedger -= betAmount;
        dealer.startDeal(deck, playerHand);
        inProgress = true;
        result = null;
    }

    public void playerHit() {
        if (!inProgress) return;
        playerHand.addCard(deck.draw());
        if (playerHand.isBust()) {
            endGame();
        }
    }

    public void playerStand() {
        if (!inProgress) return;
        dealer.dealersTurn(deck);
        endGame();
    }

    protected void endGame() {
        inProgress = false;
        int playerValue = playerHand.getBestValue();
        int dealerValue = dealer.getHand().getBestValue();
        boolean playerBust = playerHand.isBust();
        boolean dealerBust = dealer.getHand().isBust();
        if (playerBust) {
            result = "You bust! Dealer wins.";
        } else if (dealerBust) {
            result = "Dealer busts! You win!";
            saloonLedger += betAmount * 2;
            if (player != null) player.getSupplies().setCoin(saloonLedger);
        } else if (playerValue > dealerValue) {
            result = "You win!";
            saloonLedger += betAmount * 2;
            if (player != null) player.getSupplies().setCoin(saloonLedger);
        } else if (playerValue < dealerValue) {
            result = "Dealer wins.";
            if (player != null) player.getSupplies().setCoin(saloonLedger);
        } else {
            result = "Push (tie).";
            saloonLedger += betAmount;
            if (player != null) player.getSupplies().setCoin(saloonLedger);
        }
    }

    public boolean canPlaceBet(int bet) {
        return bet > 0 && bet <= saloonLedger;
    }

    public String getBetError(int bet) {
        if (bet <= 0) return "Bet must be greater than 0.";
        if (bet > saloonLedger) return "You don't have enough coins for that bet.";
        return null;
    }

    public boolean placeBet(int bet) {
        String error = getBetError(bet);
        if (error != null) return false;
        betAmount = bet;
        saloonLedger -= bet;
        return true;
    }

    public boolean startGameWithBet(int bet) {
        if (!canPlaceBet(bet)) return false;
        deck.reset();
        playerHand = new CardHand();
        dealer.getHand().clear();
        placeBet(bet);
        dealer.startDeal(deck, playerHand);
        inProgress = true;
        result = null;
        return true;
    }
}