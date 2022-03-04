package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteMysql {
	private String sql = "";
	/**
	 * 在数据库中被删除的内容条数
	 */
	private int rows = 0;
	public DeleteMysql(String sql) {
		this.sql = sql;	
	}
	
	/**
	 * @return 进行删除操作，并返回删除了几条数据
	 */
	public int delete() {
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
