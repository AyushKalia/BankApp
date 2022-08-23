package com.banking;

import java.sql.*;
import java.util.*;

public class Operations {
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech";
		String user = "scott";
		String pass = "tiger";
		Connection con = DriverManager.getConnection(url,user,pass);
		return con;
	}
	
	public static void openAccount() throws ClassNotFoundException, SQLException {
		String fname,lname,gender,mobile,dob,pin;
		Scanner sc = new Scanner(System.in);
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement("insert into customer values(?,?,?,?,?,accno_seq.nextval,'0',?)");
		System.out.print("Enter first name: ");
		fname = sc.nextLine();
		System.out.print("Enter last name: ");
		lname = sc.nextLine();
		System.out.print("Enter gender:(Male/Female/Other) ");
		gender = sc.nextLine();
		System.out.print("Enter mobile number: ");
		mobile = sc.nextLine();
		System.out.print("Enter date of birth:(DD/MM/YYYY) ");
		dob = sc.nextLine();
		System.out.print("Enter 4-digit pin: ");
		pin = sc.nextLine();
		stmt.setString(1,fname);
		stmt.setString(2,lname);
		stmt.setString(3,gender);
		stmt.setString(4,dob);
		stmt.setString(5,mobile);
		stmt.setString(6,pin);
		stmt.executeUpdate();
		stmt = con.prepareStatement("select account_no from customer where mobile_number=?");
		stmt.setString(1,mobile);
		ResultSet rs = stmt.executeQuery();
		System.out.println("Congatulations, Account created successfully");
		if(rs.next()) {
			System.out.println("Your account number is: "+rs.getString(1));
		}
		
	}
	
	public static void login() throws ClassNotFoundException, SQLException {
		String acc_no = checkAccNo();
		checkPin(acc_no);
	}
	
	public static String checkAccNo() throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter account number: ");
		String acc_no = sc.nextLine();		
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from customer where account_no=?");
		stmt.setString(1,acc_no);
		ResultSet rs = stmt.executeQuery();
		if(!rs.next()) {
			System.out.println("Account number not found.");
			checkAccNo();
		}
		return acc_no;
	}
	
	public static void checkPin(String acc_no) throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter 4-digit pin: ");
		String pin = sc.nextLine();
		PreparedStatement stmt = con.prepareStatement("select pin from customer where account_no=?");
		stmt.setString(1,acc_no);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			if(rs.getString(1).equals(pin)) {
				SecondMenu.display(acc_no);
			}
			else {
				System.out.println("Wrong pin.");
				checkPin(acc_no);
			}
		}
	}
	
	public static int checkBalance(String acc_no) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement("select balance from customer where account_no=?");
		stmt.setString(1,acc_no);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			return Integer.parseInt(rs.getString(1));
		}
		return 0;
	}
	
	public static void withdrawMoney(String acc_no) throws ClassNotFoundException, SQLException {
		System.out.print("Enter the amount you want to withdraw: ");
		Scanner sc = new Scanner(System.in);
		int amount = sc.nextInt();
		int balance = checkBalance(acc_no);		
		if(balance<amount) {
			System.out.println("Can't withdraw as current balance is: Rs. "+balance);
			withdrawMoney(acc_no);
		}
		else {
			Connection con = getConnection();
			balance -= amount;
			PreparedStatement stmt = con.prepareStatement("update customer set balance=? where account_no=?");
			stmt.setString(1,String.valueOf(balance));
			stmt.setString(2,acc_no);
			stmt.executeUpdate();
			System.out.println("Money withdrawn successfully.");
			System.out.println("Remaining balance: "+balance);
			
		}
	}
	
	public static void depositMoney(String acc_no) throws ClassNotFoundException, SQLException {
		System.out.print("Enter the amount you want to deposit: ");
		Scanner sc = new Scanner(System.in);
		int amount = sc.nextInt();
		int balance = checkBalance(acc_no);
		Connection con = getConnection();
		balance += amount;
		PreparedStatement stmt = con.prepareStatement("update customer set balance=? where account_no=?");
		stmt.setString(1,String.valueOf(balance));
		stmt.setString(2,acc_no);
		stmt.executeUpdate();
		System.out.println("Money deposited successfully.");
		System.out.println("Current balance: "+balance);
	}
}
