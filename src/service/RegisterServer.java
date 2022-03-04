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
	 * @return 获得返回来的json语句， 用于直接发送给客户端
	 */

	public String getReturnJson() {
		object = new JsonObject();
		String sql = "select * from user where userName = \"" + id + "\"";
		System.out.println(sql);
		// 结果集对象 用完之后不要忘记关闭它
		ResultSet rs = null;
		SelectMysql selectMysql = new SelectMysql(sql);
		rs = selectMysql.select();
		System.out.println(rs);
		try {
			if (!rs.next()) {
				// 如果用户提交的手机号没有被注册 实现注册
				sql = "insert into user values(\"" + id + "\",\"" + password + "\");";
				System.out.println(sql);
				int a = new InsertMysql(sql).insert();		//返回修改的条数
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
					System.out.println("添加成功~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				} else {
					object.addProperty("Flag", "register");
					object.addProperty("Message", "Registered failed");
					returnJson = object.toString();
					System.out.println("添加失败~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				}
			} else {
				// 如果用户提交的手机号已经被注册 返回json语句
				object.addProperty("Flag", "register");
				object.addProperty("Message", "The phone number has been registed");
				returnJson = object.toString();
				System.out.println("此手机号已被注册~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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
