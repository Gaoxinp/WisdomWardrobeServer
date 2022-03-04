package service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import dao.SelectMysql;

public class SearchServer {
	private String userName;
	private String ind; // �·����
	private String sql;
	private String returnJson = "";
	private JsonObject object = null;
	// ��������� ����֮��Ҫ���ǹر���
	ResultSet rs = null;

	/**
	 * �·����
	 */
	private String color;
	private String size;
	private String texture;
	private String season;
	private String style;
	private String location;

	private String picString; // ��ͼƬת�����ַ���

	public SearchServer(String userName, String ind) {
		this.userName = userName;
		this.ind = ind;
	}

	public String getReturnJson(String flag) {

		if (flag.equals("getPic")) {

			sql = "select * from main_table where ind =" + ind + ";";
			System.out.println(sql);
			rs = new SelectMysql(sql).select();
			System.out.println(rs);
			try {
				if (rs.next()) {
					location = rs.getString("location");
					returnJson = location;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (flag.equals("getArg")) {
			sql = "select * from " + userName + " where ind =" + ind + ";";
			System.out.println(sql);
			rs = new SelectMysql(sql).select();
			System.out.println(rs);
			try {
				if (rs.next()) {
					color = rs.getString("color");
					size = rs.getString("size");
					texture = rs.getString("texture");
					season = rs.getString("season");
					style = rs.getString("style");
					location = rs.getString("location");
					if (color.equals("")) {
						color = "������";
					}
					if (size.equals("")) {
						size = "������";
					}
					if (texture.equals("")) {
						texture = "������";
					}
					if (season.equals("")) {
						season = "������";
					}
					if (style.equals("")) {
						style = "������";
					}

					object = new JsonObject();
					object.addProperty("Flag", "getArg");
					object.addProperty("hasClothes", true);

					object.addProperty("color", color);
					object.addProperty("size", size);
					object.addProperty("texture", texture);
					object.addProperty("season", season);
					object.addProperty("style", style);
					object.addProperty("location", location);
					returnJson = object.toString();

				} else {

					sql = "select * from main_table where ind =" + ind + ";";
					System.out.println(sql);
					rs = new SelectMysql(sql).select();
					System.out.println(rs);
					if (rs.next()) {
						color = rs.getString("color");
						size = rs.getString("size");
						texture = rs.getString("texture");
						season = rs.getString("season");
						style = rs.getString("style");
						location = rs.getString("location");
						if (color.equals("")) {
							color = "������";
						}
						if (size.equals("")) {
							size = "������";
						}
						if (texture.equals("")) {
							texture = "������";
						}
						if (season.equals("")) {
							season = "������";
						}
						if (style.equals("")) {
							style = "������";
						}
						System.out.println(color + size + texture + season + style + location);

						object = new JsonObject();
						object.addProperty("Flag", "getArg");
						object.addProperty("hasClothes", false);

						object.addProperty("color", color);
						object.addProperty("size", size);
						object.addProperty("texture", texture);
						object.addProperty("season", season);
						object.addProperty("style", style);
						object.addProperty("location", location);
						returnJson = object.toString();

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return returnJson;
	}

}
