package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import dao.SelectMysql;

public class GetSuggestClothesServer {

	private String userName;
	private String returnJson;
	private String ind;
	private String[] strReturn;
	
	private String sql;
	private ResultSet rs;

	public GetSuggestClothesServer(String userName) {
		super();
		this.userName = userName;
	}

	public String[] getReturnJson() {

		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		String season = "";
		if (month == 12 || month == 1 || month == 2) {
			season = "¶¬";
		} else if (month == 3 || month == 4 || month == 5) {
			season = "´º";
		} else if (month == 6 || month == 7 || month == 8) {
			season = "ÏÄ";
		} else if (month == 9 || month == 10 || month == 11) {
			season = "Çï";
		}

		sql = "select * from " + userName + " where season =\"" + season + "\" and inChest = 1;";
		System.out.println(sql);
		rs = new SelectMysql(sql).select();
		System.out.println(rs);

		try {
			if (rs.next()) {
				returnJson = rs.getString("location");
				ind = rs.getString("ind");
			} else {
				returnJson = "null";
				ind = "0";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		strReturn = new String[]{returnJson,ind};
		return strReturn;
	}

}
