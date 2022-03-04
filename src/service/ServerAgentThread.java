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
 * ���Ƿ����������ͻ���ʹ�õĴ����࣬�Ǳ�д�����еĵڶ�����
 */

public class ServerAgentThread extends Thread {
	private Socket socket = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	// ���յ��� json ���
	private String mag = "";
	// ���յ��� mag �е� Flag �ַ���
	private String flag = "";
	// ��Ҫ���ͻ��˷��͵� json �ַ���
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
			// ��flag��ʶ��¼ע��Ȳ���ʱ�������Ӧ����
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
