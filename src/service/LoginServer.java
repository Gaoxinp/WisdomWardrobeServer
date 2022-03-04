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
	 * @return 获得返回来的json语句， 用于直接发送给客户端
	 */
	public String getReturnJson() {
		object = new JsonObject();
		String sql = "select * from user where userName = \"" + userName + "\"";
		System.out.println(sql);
		// 结果集对象 用完之后不要忘记关闭它
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
					System.out.println("登陆成功~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				} else {
					object.addProperty("Flag", "login");
					object.addProperty("Message", "wrong passwordt");
					returnJson = object.toString();
					System.out.println("密码错误~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				}
			} else {
				object.addProperty("Flag", "login");
				object.addProperty("Message", "User name does not exist");
				returnJson = object.toString();
				System.out.println("用户名不存在~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			}

			// 关闭创建的resultSet
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 千万不要忘记关闭connection，resultSet和preparedStatement
		selectMysql.close();
		return returnJson;
	}

}
