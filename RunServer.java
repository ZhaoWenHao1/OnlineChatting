package com.Chat;

public class RunServer {

	public static void main(String[] args) {
		new ServerListener().start();
		System.out.println("Server is running...");
	}

}