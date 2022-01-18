package com.company.NetWork;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.locks.LockSupport;

public class UDPThread extends Thread {

    DatagramSocket socket;
    DatagramPacket packet;
    final Integer Port=8088;
    boolean flag = true;
    Gson gson;
    public static UDPThread Instance;

    static {
        try {
            Instance = new UDPThread();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public UDPThread() throws SocketException, UnknownHostException {
        socket = new DatagramSocket(Port);
        gson = new Gson();
    }

    public static UDPThread getInstance() {
        return Instance;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("***************服务器Udp监听已开启***************");
        try {
            while (flag) {
                byte[] recvBuf = new byte[1024];
                packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
                Distributor.getInstance().jobs.add(packet);
                if (Distributor.getInstance().isAlive()){
                    LockSupport.unpark(Distributor.getInstance());
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
