package service;

import java.sql.ResultSet;

import java.sql.SQLException;

import com.google.gson.JsonObject;

import dao.InsertMysql;
import dao.SelectMysql;

public class RegisterServer {
	private String id = "";
	private String password = "";
	private String returnJson = "";
	private JsonObject object = null;

	public RegisterServer(String id, String password) {
		this.id = id;
		this.password = password;

	}

	/**
	 * @return ��÷�������json��䣬 ����ֱ�ӷ��͸��ͻ���
	 */

	public String getReturnJson() {
		object = new JsonObject();
		String sql = "select * from user where userName = \"" + id + "\"";
		System.out.println(sql);
		// ��������� ����֮��Ҫ���ǹر���
		ResultSet rs = null;
		SelectMysql selectMysql = new SelectMysql(sql);
		rs = selectMysql.select();
		System.out.println(rs);
		try {
			if (!rs.next()) {
				// ����û��ύ���ֻ���û�б�ע�� ʵ��ע��
				sql = "insert into user values(\"" + id + "\",\"" + password + "\");";
				System.out.println(sql);
				int a = new InsertMysql(sql).insert();		//�����޸ĵ�����
				System.out.println(Integer.toString(a) + "~~~~~~~~~~~~~~~~~~~~~~~~~");
				if (a > 0) {
					sql = "CREATE TABLE " + id + "(ind VARCHAR(20) NOT NULL,"
							+ "color VARCHAR(10) DEFAULT \"\",size VARCHAR(20) DEFAULT \"\",texture VARCHAR(20) DEFAULT \"\","
							+ "season VARCHAR(10) DEFAULT \"\",style VARCHAR(10) DEFAULT \"\",location VARCHAR(200) DEFAULT \"\","
							+ "frequency INT DEFAULT 0"+",inChest INT DEFAULT 1);";
					System.out.println(sql);
					new InsertMysql(sql).insert();
					object.addProperty("Flag", "register");
					object.addProperty("Message", "Registered successfully");
					returnJson = object.toString();
					System.out.println("��ӳɹ�~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				} else {
					object.addProperty("Flag", "register");
					object.addProperty("Message", "Registered failed");
					returnJson = object.toString();
					System.out.println("���ʧ��~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				}
			} else {
				// ����û��ύ���ֻ����Ѿ���ע�� ����json���
				object.addProperty("Flag", "register");
				object.addProperty("Message", "The phone number has been registed");
				returnJson = object.toString();
				System.out.println("���ֻ����ѱ�ע��~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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
