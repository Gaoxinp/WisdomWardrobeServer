package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 对数据库进行 插入 操作
 */
public class InsertMysql {
	private String sql = "";
	/**
	 * 在数据库中被插入的内容条数
	 */
	private int rows = 0;

	public InsertMysql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return 进行插入操作，并返回插入了几条数据
	 */
	public int insert() {
		Connection connection = BaseConnection.getConnection();
		// sql执行器对象
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			// 该方法用于对数据库值进行改变时使用
			rows = ps.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (ps != null) {
				ps.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

}
