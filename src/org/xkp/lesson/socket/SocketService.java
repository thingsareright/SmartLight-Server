package org.xkp.lesson.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class SocketService {
	
	public static int ChangeLight(String leval) {
        CopyOnWriteArrayList<Socket> sockets=ConnSocket.getSockets();

		for (Socket socket:sockets) {
			if(socket != null && !socket.isClosed()) {
				try {
					OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
					writer.write(leval);
					writer.flush();
					//此处writer不能关闭，原因:如果关闭writer，则对应的socket也会被关闭

				} catch (IOException e) {
					// TODO Auto-generated catch block
					sockets.remove(socket);
					e.printStackTrace();
					return 0;
				}

			}else {
				sockets.remove(socket);
				return 0;
			}
		}
		return 1;
	}
	/**
	 * 通过Socket发送开灯或者关灯命令
	 * @param flag
	 * @return
	 */
	public static int openOrCloseLight(boolean flag) throws IOException {
        System.out.println("SocketService:***  1" );
        CopyOnWriteArrayList<Socket> sockets=ConnSocket.getSockets();
        System.out.println("SocketService:***  2  " + sockets.size());
        for(int i = 0; i<=100; i++){
            for (Socket socket:sockets) {
                if(socket != null && !socket.isClosed()) {
                    try {
                        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
                        if(flag) {
                            //开灯
                            writer.write("10");
                        }else {
                            //关灯
                            writer.write("11");
                        }
                        writer.flush();
                        //此处writer不能关闭，原因:如果关闭writer，则对应的socket也会被关闭

                    } catch (IOException e) {
                        System.out.println("Socket wrong! " + sockets.indexOf(socket));
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            throw new IOException(e1);
                        }
                        sockets.remove(socket);
                        e.printStackTrace();
                        return 0;
                    }
                }else {
                    sockets.remove(socket);
                    return 0;
                }
            }
        }

		return 1;
	}
}
