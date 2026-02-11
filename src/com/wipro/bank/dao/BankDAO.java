package com.wipro.bank.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.util.DBUtil;

public class BankDAO {
	public int generateSequenceNumber() {
	    String query = "SELECT TRANSACTIONID_SEQ.NEXTVAL FROM dual";

	    try (
	    	Connection con = DBUtil.getDBConnection();
	         PreparedStatement ps = con.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    throw new RuntimeException("Unable to generate transaction ID");
	}

public boolean validateAccount(String accountNumber) {

   try {

       Connection con = DBUtil.getDBConnection();

       PreparedStatement ps = con.prepareStatement("SELECT 1 FROM ACCOUNT_TBL WHERE Account_Number = ?");

       ps.setString(1, accountNumber);

       ResultSet rs = ps.executeQuery();

       return rs.next();

   }

   catch (Exception e) {

       e.printStackTrace();
       return false;

   }

}

public float findBalance(String accountNumber) {

if(validateAccount(accountNumber)) {

try {

Connection con=DBUtil.getDBConnection();

PreparedStatement ps=con.prepareStatement("SELECT Balance FROM ACCOUNT_TBL WHERE Account_Number=?");

ps.setString(1, accountNumber);

ResultSet rs=ps.executeQuery();

if(rs.next()) {

return rs.getFloat(1);

}

}catch(Exception e) {

e.printStackTrace();

}

}

return -1;

}

public boolean updateBalance(String accountNumber,float newBalance) {

try {

Connection con=DBUtil.getDBConnection();

PreparedStatement ps=con.prepareStatement("UPDATE ACCOUNT_TBL SET Balance=? WHERE Account_Number=?");

ps.setFloat(1, newBalance);

ps.setString(2,accountNumber);

int update=ps.executeUpdate();

if(update>0) {

return true;

}

}catch(Exception e) {

e.printStackTrace();

}

return false;

}
public boolean transferMoney(TransferBean transferBean) {
    try {
        transferBean.setTransactionID(generateSequenceNumber());

        Connection con = DBUtil.getDBConnection();
        String query = "INSERT INTO TRANSFER_TBL VALUES (?,?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, transferBean.getTransactionID());
        ps.setString(2, transferBean.getFromAccountNumber());
        ps.setString(3, transferBean.getToAccountNumber());
        ps.setDate(4, new java.sql.Date(transferBean.getDateOfTransaction().getTime()));
        ps.setFloat(5, transferBean.getAmount());

        int rows = ps.executeUpdate();
        return rows > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}