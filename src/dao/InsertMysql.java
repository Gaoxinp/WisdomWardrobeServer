package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * �����ݿ���� ���� ����
 */
public class InsertMysql {
	private String sql = "";
	/**
	 * �����ݿ��б��������������
	 */
	private int rows = 0;

	public InsertMysql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return ���в�������������ز����˼�������
	 */
	public int insert() {
		Connection connection = BaseConnection.getConnection();
		// sqlִ��������
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			// �÷������ڶ����ݿ�ֵ���иı�ʱʹ��
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
