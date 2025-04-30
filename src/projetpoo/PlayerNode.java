package projetpoo;

public class PlayerNode {
  Player player;
  PlayerNode next;
  PlayerNode previous;

  public PlayerNode(Player player) {
    this.player = player;
    this.next = null;
    this.previous = null;
  }

  public PlayerNode getNext() {
    return next;
  }

  public Player getPlayer() {
    return player;
  }
}
