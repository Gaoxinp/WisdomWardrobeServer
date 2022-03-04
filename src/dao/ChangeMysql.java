package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangeMysql {
	private String sql = "";
	/**
	 * �����ݿ��б��޸ĵ���������
	 */
	private int rows = 0;
	public ChangeMysql(String sql) {
		this.sql = sql;	
	}
	
	/**
	 * @return �����޸Ĳ������������޸��˼�������
	 */
	public int change() {
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
