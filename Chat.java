package com.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class Chat extends Thread {

	Socket socket;

	public Chat(Socket s) {
		this.socket = s;
	}

	public void out(String out) {
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(out + "\n");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("break connection(IO in Out Method)");
			Manager.getChatManager().remove(this);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		out("You have already connected with Sever. Your name would be User "+Manager.getChatManager().getUserID(this)+".");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				Manager.getChatManager().publish(this, line);
			}

			br.close();
			System.out.println("break connection(br.close)");
			Manager.getChatManager().remove(this);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("break connection(IOException,can't get InputStream)");
			Manager.getChatManager().remove(this);
			e.printStackTrace();
		}

	}
}