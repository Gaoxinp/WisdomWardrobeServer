package service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * 服务器在这里启动
 */
public class ServerThread extends Thread {
	ServerSocket ss;

	@Override
	public void run() {
		try {
			ss = new ServerSocket(9000);
			System.out.println("Listening on 9000...");
			
			while (true) {
				Socket socket = ss.accept();
//				JOptionPane.showMessageDialog(null, "一个二货请求连接！");
				new ServerAgentThread(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		new ServerThread().start();
	}

}
