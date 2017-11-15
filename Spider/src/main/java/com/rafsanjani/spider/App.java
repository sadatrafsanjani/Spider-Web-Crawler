package com.rafsanjani.spider;


public class App {

    public static void main(String[] args) {

        String root = "https://www.rokomari.com/";
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        bfs.crawlSite(root);
    }
}
