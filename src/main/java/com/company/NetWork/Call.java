package com.company.NetWork;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.locks.LockSupport;

public class Call extends Thread {
    public static final int REQUEST = 0x01;//请求
    public static final int ACK = 0x02;//收到确认
    public static final int RESPONSE = 0x03;//响应

    int try_times;
    Boolean onResponsed;
    Integer msgNo;
    Msg msg;
    Client client;

    CallBack callBack;

    @Override
    public void run() {
        try {
            this.try_times = 0;
            if (msg.getType() == Call.ACK) {
                this.onResponsed = true;
            }else if (msg.getType()==Call.REQUEST){
                this.onResponsed = false;
                Distributor.getInstance().msgNo++;
                msgNo= Distributor.getInstance().msgNo;
                msg.setMsgNo(msgNo);
            }
            do{
                InetAddress address = InetAddress.getByName(client.getIp());
                byte[] bufBytes = msg.getData().getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(bufBytes, bufBytes.length, address, client.getPort());
                DatagramSocket socket = null;
                socket = new DatagramSocket();
                socket.send(datagramPacket);
                LockSupport.parkUntil(System.currentTimeMillis()+1000);
                this.try_times++;
            } while (this.try_times < 3 && !onResponsed);
            if (!onResponsed) {
                //send udp and no response
                Distributor.getInstance().calls.remove(msgNo);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Call(Client client, Msg msg) {


        this.start();
    }

    public void callBack(CallBack callBack){
        this.callBack=callBack;
    }
    public void Success(Boolean flag){
        this.onResponsed=flag;
    }

}
