package service;

import com.google.gson.JsonObject;

import dao.ChangeMysql;
import dao.SelectMysql;

public class TakeAwayCloth {
	private String returnJson;
	private String userName;
	private String ind;
	private String sql;

	public TakeAwayCloth(String userName, String ind) {

		this.userName = userName;
		this.ind = ind;
	}

	public String getReturnJson() {
		sql = "update " + userName + " set inChest = 0 where ind = " + ind + ";";
		System.out.println(sql);
		int a = new ChangeMysql(sql).change();
		System.out.println(a);

		JsonObject object = new JsonObject();
		if (a == 0) {
			object.addProperty("Flag", "takeAwayCloth");
			object.addProperty("Message", "Take Wrong");
			returnJson = object.toString();
		} else if (a == 1) {
			object.addProperty("Flag", "takeAwayCloth");
			object.addProperty("Message", "Take OK");
			returnJson = object.toString();
		}

		return returnJson;
	}

}
