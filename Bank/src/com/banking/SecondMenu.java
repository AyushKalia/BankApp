package com.banking;

import java.sql.*;
import java.util.*;

public class SecondMenu {
	public static void display(String acc_no) throws ClassNotFoundException, SQLException{
		System.out.println("1. Check Balance\n2. Deposit Money\n3. Withdraw Money\n4. Back\nEnter your choice: ");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		performOp(choice,acc_no);
	}
	public static void performOp(int choice,String acc_no) throws ClassNotFoundException, SQLException {
		switch(choice) {
		case 1:
			int balance = Operations.checkBalance(acc_no);
			System.out.println("Your current balance is: Rs. "+balance);
			afterOp(acc_no);
			break;
		case 2:
			Operations.depositMoney(acc_no);
			afterOp(acc_no);
			break;
		case 3:
			Operations.withdrawMoney(acc_no);
			afterOp(acc_no);
			break;
		case 4:
			Main.menu();
		default:
			System.out.println("Invalid choice.");
		}
	}
	public static void afterOp(String acc_no) throws ClassNotFoundException, SQLException {
		System.out.println("Press enter key to continue");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		display(acc_no);
	}
}
