package service;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Decoder.BASE64Encoder;

/**
 * 这是服务器监听客户端使用的代理类，是编写过程中的第二个类
 */

public class ServerAgentThread extends Thread {
	private Socket socket = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	// 接收到的 json 语句
	private String mag = "";
	// 接收到的 mag 中的 Flag 字符串
	private String flag = "";
	// 将要给客户端发送的 json 字符串
	private String returnMag = "";

	public ServerAgentThread(Socket socket) {

		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			mag = in.readUTF();
			System.out.println(mag);
			JsonParser parser = new JsonParser();
			JsonObject object = parser.parse(mag).getAsJsonObject();
			flag = object.get("Flag").getAsString();
			switch (flag) {
			// 当flag标识登录注册等操作时，完成相应操作
			case "register":
				returnMag = new RegisterServer(object.get("id").getAsString(), object.get("password").getAsString())
						.getReturnJson();
				out.writeUTF(returnMag);
				break;
			case "login":
				returnMag = new LoginServer(object.get("userName").getAsString(), object.get("password").getAsString())
						.getReturnJson();
				out.writeUTF(returnMag);
				break;
			case "getPic":
				String location = new SearchServer(object.get("userName").getAsString(),
						object.get("ind").getAsString()).getReturnJson("getPic");
				System.out.println(location);
				FileInputStream fis = new FileInputStream(location);
				int size = fis.available();
				byte[] sendBytes = new byte[size];
				fis.read(sendBytes);
				out.writeInt(size);
				out.write(sendBytes);
				out.flush();
				fis.close();
				break;
			case "getSuggestClothes":
				String[] strReturn = new GetSuggestClothesServer(object.get("userName").getAsString()).getReturnJson();
				location = strReturn[0];
				int ind = Integer.parseInt(strReturn[1]); 
				
				if("null".equals(location)){
					out.writeInt(-1);
					out.flush();
				}else{
					fis = new FileInputStream(location);
					size = fis.available();
					sendBytes = new byte[size];
					fis.read(sendBytes);
					out.writeInt(size);
					out.writeShort(ind);
					out.write(sendBytes);
					out.flush();
					fis.close();
				}
	
				break;
			case "getArg":
				returnMag = new SearchServer(object.get("userName").getAsString(), object.get("ind").getAsString())
						.getReturnJson("getArg");
				out.writeUTF(returnMag);
				break;

			case "addToChest":
				System.out.println(object.toString());
				returnMag = new AddToChestServer(object.get("userName").getAsString(), object.get("ind").getAsString(),
						object.get("color").getAsString(), object.get("size").getAsString(),
						object.get("texture").getAsString(), object.get("season").getAsString(),
						object.get("style").getAsString(),object.get("location").getAsString()).getReturnJson();
				out.writeUTF(returnMag);
				break;
			case "takeAwayCloth":
				System.out.println(object.toString());
				returnMag = new TakeAwayCloth(object.get("userName").getAsString(), object.get("ind").getAsString()).getReturnJson();
				out.writeUTF(returnMag);
				break;
			default:
				break;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
