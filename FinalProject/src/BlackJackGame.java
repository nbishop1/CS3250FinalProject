public class BlackJackGame {
    private CardDeck deck;
    private CardDealer dealer;
    private CardHand playerHand;
    private boolean inProgress;
    private String result;
    private int betAmount;
    private Player player;

    public BlackJackGame(Player player) {
        this.deck = new CardDeck();
        this.dealer = new CardDealer();
        this.player = player;
    }

    public CardDeck getDeck() { return deck; }
    public CardDealer getDealer() { return dealer; }
    public CardHand getPlayerHand() { return playerHand; }
    public CardHand getDealerHand() { return dealer.getHand(); }
    public boolean isInProgress() { return inProgress; }
    public String getGameResult() { return result; }
    public int getBetAmount() { return betAmount; }
    public void setPlayer(Player player) { this.player = player; }
    public int getPlayerCoins() { return player != null ? player.getSupplies().getCoin() : 0; }

    public boolean canPlaceBet(int bet) {
        return player != null && bet > 0 && bet <= player.getSupplies().getCoin();
    }

    public String getBetError(int bet) {
        if (bet <= 0) return "Bet must be greater than 0.";
        if (player == null || bet > player.getSupplies().getCoin()) return "You don't have enough coins for that bet.";
        return null;
    }

    public boolean placeBet(int bet) {
        String error = getBetError(bet);
        if (error != null) return false;
        betAmount = bet;
        player.getSupplies().spendCoin(bet);
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
        boolean playerBlackjack = (playerHand.getCards().size() == 2 && playerValue == 21);
        if (playerBust) {
            result = "You bust! Dealer wins.";
            // Bet already subtracted at start
        } else if (playerBlackjack && !dealerBust && dealerValue != 21) {
            result = "Blackjack! You win!";
            player.getSupplies().addCoin((int)(betAmount * 2.5)); // Blackjack pays 3:2
        } else if (dealerBust) {
            result = "Dealer busts! You win!";
            player.getSupplies().addCoin(betAmount * 2);
        } else if (playerValue > dealerValue) {
            result = "You win!";
            player.getSupplies().addCoin(betAmount * 2);
        } else if (playerValue < dealerValue) {
            result = "Dealer wins.";
            // Bet already subtracted at start
        } else {
            result = playerBlackjack ? "Push (tie) with Blackjack!" : "Push (tie).";
            player.getSupplies().addCoin(betAmount); // Return bet on tie
        }
    }
}