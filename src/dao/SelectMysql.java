package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 *�����ݿ���� ���� ����
 */
/**
 * 
 *������������������������������������������������������ע������಻�ܹر�connection���رպ�����connection֮�ϵ�resultSetҲ���Զ��رգ�������������������������������
 */
public class SelectMysql {
	private String sql = "";
	/**
	 * ���ҵ������ݴ����ResultSet��
	 */
	private ResultSet resultSet = null;
	// sqlִ��������
	private PreparedStatement ps = null;
	private Connection connection = null;

	public SelectMysql(String sql) {

		this.sql = sql;
	}

	/**
	 * @return ���в��Ҳ����������ز��ҽ��
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
	 * �����ر������connection��
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
