package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 *对数据库进行 查找 操作
 */
/**
 * 
 *！！！！！！！！！！！！！！！！！！！！！！！！！！！注意这个类不能关闭connection，关闭后建立在connection之上的resultSet也会自动关闭！！！！！！！！！！！！！！！！
 */
public class SelectMysql {
	private String sql = "";
	/**
	 * 查找到的内容存放在ResultSet中
	 */
	private ResultSet resultSet = null;
	// sql执行器对象
	private PreparedStatement ps = null;
	private Connection connection = null;

	public SelectMysql(String sql) {

		this.sql = sql;
	}

	/**
	 * @return 进行查找操作，并返回查找结果
	 */
	public ResultSet select() {
		connection = BaseConnection.getConnection();
		
		try {
			ps = connection.prepareStatement(sql);
			resultSet = ps.executeQuery();
			System.out.println(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}
	/**
	 * 用来关闭用完的connection等
	 */
	public void close() {
			try {
				if(resultSet != null){
				resultSet.close();
				}
				if(ps != null){
					ps.close();
				}
				if(connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
