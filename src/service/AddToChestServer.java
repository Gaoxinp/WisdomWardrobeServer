package service;

import com.google.gson.JsonObject;

import dao.InsertMysql;

public class AddToChestServer {
	private String userName;
	private String ind;
	private String color;
	private String size;
	private String texture;
	private String season;
	private String style;
	private String location;

	public AddToChestServer(String userName, String ind, String color, String size, String texture, String season,
			String style, String location) {
		this.userName = userName;
		this.ind = ind;
		this.color = color;
		this.size = size;
		this.texture = texture;
		this.season = season;
		this.style = style;
		this.location = location;
	}

	public String getReturnJson() {
		JsonObject object = new JsonObject();
		object.addProperty("Flag", "addToChest");
		String sql = "insert into " + userName + " values(\"" + ind + "\",\"" + color + "\",\"" + size + "\",\""
				+ texture + "\",\"" + season + "\",\"" + style + "\",\"" + location + "\",0,1);";
		System.out.println(sql);
		int rows = new InsertMysql(sql).insert();
		System.out.println(Integer.toString(rows) + "~~~~~~~~~~~~~~~~~~~~~~~~~");
		if (rows > 0) {
			object.addProperty("result", "Succeed");
		} else {
			object.addProperty("result", "Failure");
		}

		return object.toString();
	}
}
