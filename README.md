-- Author: Natalee Bishop
-- CS3250 Final Project
-- Project Name: Hit, Partner
-- Concept: Oregon Trail style game where you have to play Blackjack in to get money.

How it should work:

On launch, the start menu appears with the game name, a "New Game", "Continue" and "Settings" buttons, and a textbox for the player their name.

  If "New Game" is selected, the New game scene will appear with an intro to the game with something like
    "You and your family are traveling to the west in search of riches in the form of gold. You have no skills,
    no talents, and hardly any money, so you resort to luck. Every three days, you will reach a new town
    where you will gamble to earn coins to fund your travels.... Or lose it all and starve to death."

  Starting inventory:
  Coin: 20
  Food: 10
  Water: 15
  Ammunition: 2

  The first day begins on the path to the next town. It will take 4 more days to get there. 

  Everyday, the player can delegate their food, water, or medicine depending on the needs of the family. Only after an event occurs, someone can get sick/injured. If needs for a family member are not met after 3 days, they die. 
    Player: Player. Requires 1 food every 4 days, 1 water every 3 days.
    Mary: Wife. Requires 1 food every 3 days, 1 water every 2 days. 
    Uncle: Uncle. Requires 1 food every 3 days, 1 water every 2 days. 
    Luke: Son. Requires 1 food every day, 1 water every day. 
    Jessie: Daughter. Requires 1 food every day, 1 water every day. 
  
  Once the player decides to progress to the next day, there is a chance a single event will happen. These events can be "Caught by Outlaws", "I Broke the Gosh-dang Wheel", "Snake Bite", "Hello, Stranger". 
    If the player has gear in their inventory that can help them immediatly clear the event, the item will be forfeited in exchange for their safety.
   
    "Caught by Outlaws": "A posy of outlaws have circled the wagon!" There is a 5% chance of this event happening once within the 5 day stretch between towns. If the player has 2 ammo (scare them off) || 5 food (give food) || 4 water (give water) || 10 coins (bribe them to go away), the player can choose to forfeit the item in exchange for skipping any event penalties. If the player does not have any required item, there is a chance nothing happens (player talks it out) (50% chance), a family member gets injured (grazed by a bullet)(45% chance), or a very small chance the player is killed ending the game instantly(5% chance).
      
    "I Broke the Gosh-dang Wheel": "The wheel of the wagon broke clean off!" There is a 10% chance of this event happening once within the 5 day stretch between towns. If the player has 4 coins (buy a wheel off someone) || 1 spare part (make the repair), the event penalties can be skipped in exchange. If the player does not have ether item, there is a chance nothing happens (a passerby helps)(75% chance), a family member gets sick (sleeps on the ground while waiting for help)(25% chance).
      
    "Snake Bite": "A rattlesnake was found in the wagon!" There is a 10% chance of this event happening once within the 5 day stretch between towns. If the player has 1 medicine (heal who gets bit instantly) || 2 food (lead it away) || 1 ammo (kill the snake), the event penalties will be skipped. If the player does not have any items or chooses not to use any, there is a chance nothing happens (the snake lost interest)(50% chance), or the player / a family member gets injured (got bit by a rattlesnake) (50% chance).

    "Hello, Stranger": "A mysterious caravan playing soft music on a gramophone sits on the path's edge... Approach?" There is a 5% chance of this event happening once within the 5 day stretch between towns. If the player decides to approach the merchant, the general store menu pops up. Appon exiting the store menu, the day progresses. If the player chooses to pass, nothing happens.

  If the player successfully reaches the next town (every third day), they have the options to:
    1. Enter the Saloon to begin a new game of Blackjack.
    2. Enter the General Store to browse purchasable items. 
    3. Continue Journey to move on to the next day.

  If the player selects Saloon, the Blackjack table scene will launch. The player begins by using a textbox to bet. Once a bet is confirmed, the game will begin with single hand
    Blackjack logic. Once the player decides to cash out or goes bankrupt, the player returns to the town option menu: "General Store" or "Continue Journey".

  If the player selects "General Store", the shop menu scene will launch. The player can (depending on their coin) select items and the quantity they would like to purchase. The player can make a purchase or
    click "Exit Store" to return to the town option menu: "General Store" or "Continue Journey". 

  If the player selects "Continue Journey", another set of 5 days will occur.

  If the player dies on their journey from lack of food, water, or was killed by outlaws, the "Game Over" scene will launch with options to start a new game or exit to the start menu.

  After surviving 30 days successfully, the player will reach the 10th town which is the destination. The "Game Over" scene will launch with an additional message:
    "You have made it to your destination with " it will then list all living family members. If any family members died, a small "Rest in Peace" list with those who perished will appear as well. 


WHATS LEFT:
- Create the events and event pane
- Create the different game over panes
- Create the save/continue game logic using a csv file
- If I have time:
    - Add sound effects and music depending on the pane

      
