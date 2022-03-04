package service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import dao.SelectMysql;

public class LoginServer {
	private String userName = "";
	private String password = "";
	private String returnJson = "";
	private JsonObject object = null;

	public LoginServer(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/**
	 * @return ��÷�������json��䣬 ����ֱ�ӷ��͸��ͻ���
	 */
	public String getReturnJson() {
		object = new JsonObject();
		String sql = "select * from user where userName = \"" + userName + "\"";
		System.out.println(sql);
		// ��������� ����֮��Ҫ���ǹر���
		ResultSet rs = null;
		SelectMysql selectMysql = new SelectMysql(sql);
		rs = selectMysql.select();
		System.out.println(rs);
		try {
			if (rs.next()) {
				String pass = rs.getString("password");
				if (password.equals(pass)) {
					object.addProperty("Flag", "login");
					object.addProperty("Message", "Login successfully");
					returnJson = object.toString();
					System.out.println("��½�ɹ�~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				} else {
					object.addProperty("Flag", "login");
					object.addProperty("Message", "wrong passwordt");
					returnJson = object.toString();
					System.out.println("�������~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				}
			} else {
				object.addProperty("Flag", "login");
				object.addProperty("Message", "User name does not exist");
				returnJson = object.toString();
				System.out.println("�û���������~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			}

			// �رմ�����resultSet
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// ǧ��Ҫ���ǹر�connection��resultSet��preparedStatement
		selectMysql.close();
		return returnJson;
	}

}
