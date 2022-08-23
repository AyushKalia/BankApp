package com.banking;

import java.sql.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException  {
		menu();
	}
	public static void menu() throws ClassNotFoundException, SQLException {
		System.out.print("1. Open new account\n2. Login to existing account\n3. Exit\nEnter your choice: ");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		performOp(choice);
	}
	public static void performOp(int choice) throws ClassNotFoundException, SQLException {
		switch(choice) {
		case 1:
			Operations.openAccount();
			afterOp();
			break;
		case 2:
			Operations.login();
			afterOp();
			break;
		case 3:
			return;
		default:
			System.out.println("Invalid choice");
		}
	}
	public static void afterOp() throws ClassNotFoundException, SQLException {
		System.out.println("Press enter key to continue");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		menu();
	}
}
