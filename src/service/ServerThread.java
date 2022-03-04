package service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * ����������������
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
//				JOptionPane.showMessageDialog(null, "һ�������������ӣ�");
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
