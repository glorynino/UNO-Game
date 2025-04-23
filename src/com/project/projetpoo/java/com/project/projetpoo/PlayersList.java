package com.project.projetpoo.java.com.project.projetpoo;

public class PlayersList {
  private PlayerNode currentPlayerNode = null;

    // Add a player to the list
    public void addPlayer(Player player) {
        PlayerNode newNode = new PlayerNode(player);
        if (currentPlayerNode == null) { // Empty list
            currentPlayerNode = newNode;
            currentPlayerNode.next = currentPlayerNode;
            currentPlayerNode.previous = currentPlayerNode;
        } else {
            PlayerNode lastNode = currentPlayerNode.previous;
            lastNode.next = newNode;
            newNode.previous = lastNode;
            newNode.next = currentPlayerNode;
            currentPlayerNode.previous = newNode;
        }
    }

    // Display all players
    public void displayPlayers() {
        if (currentPlayerNode == null) {
            System.out.println("No players in the game.");
            return;
        }
        PlayerNode temp = currentPlayerNode;
        do {
            System.out.print(temp.player.getNom() + " ");
            temp = temp.next;
        } while (temp != currentPlayerNode);
        System.out.println();
    }

    // Get the current player's turn
    public Player getCurrentPlayer() {
        if (currentPlayerNode == null) {
            System.out.println("No players in the game.");
            return null;
        }
        return currentPlayerNode.player;
    }

    public Player getPreviousPlayer() {
        if (currentPlayerNode == null) {
            System.out.println("No players in the game.");
            return null;
        }
        return currentPlayerNode.previous.player;
    }

    // Move to the next player's turn
    public void nextPlayer() {
        if (currentPlayerNode != null) {
            currentPlayerNode = currentPlayerNode.next;
        }
    }

    // Move to the previous player's turn (for reverse turn)
    public void previousPlayer() {
        if (currentPlayerNode != null) {
            currentPlayerNode = currentPlayerNode.previous;
        }
    }

    // Remove a player
    public void removePlayer(String playerName) {
        if (currentPlayerNode == null) {
            System.out.println("No players to remove.");
            return;
        }

        PlayerNode temp = currentPlayerNode;
        do {
            if (temp.player.getNom().equals(playerName)) {
                if (temp == currentPlayerNode && temp.next == currentPlayerNode) { // Only one player
                    currentPlayerNode = null;
                } else {
                    temp.previous.next = temp.next;
                    temp.next.previous = temp.previous;
                    if (temp == currentPlayerNode) {
                        currentPlayerNode = temp.next; // Move to the next player
                    }
                }
                System.out.println("Removed player: " + playerName);
                return;
            }
            temp = temp.next;
        } while (temp != currentPlayerNode);

        System.out.println("Player not found: " + playerName);
    }

    public Player getNextPlayer() {
        if (currentPlayerNode == null) {
            System.out.println("No players in the game.");
            return null;
        }
        return currentPlayerNode.next.player;
      
    }

    public PlayerNode getFirstNode() {
        return currentPlayerNode;
    }
}

