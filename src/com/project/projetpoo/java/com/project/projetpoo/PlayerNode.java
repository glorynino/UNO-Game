package com.project.projetpoo.java.com.project.projetpoo;

public class PlayerNode {
  Player player;
  PlayerNode next;
  PlayerNode previous;

  public PlayerNode(Player player) {
    this.player = player;
    this.next = null;
    this.previous = null;
  }
}
