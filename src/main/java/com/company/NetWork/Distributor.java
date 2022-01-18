package com.company.NetWork;

import com.google.gson.Gson;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class Distributor extends Thread{
    public List<DatagramPacket> jobs;//链表形式
    public HashMap<Integer,Call> calls;
    public Integer callNo;
    public Integer msgNo;
    private static final Distributor Instance;
    static {
        Instance=new Distributor();
    }
    public Distributor(){
        jobs=new LinkedList<>();
        calls=new HashMap<>();
    }

    public static Distributor getInstance() {
        return Instance;
    }

    @Override
    public void run(){
        try {
            while (true){
                if (jobs.size()>0){
                    Distribute(jobs.get(0));
                    jobs.remove(0);
                }
                else LockSupport.park();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //收到ACK，查看call是否alive，修改call状态，停止发送。
    //收到request：回复ack确认收到，分发到Controller，由Controller发送响应信息,收到响应确认;
    //收到response：回复ACK确认收到，调用callback处理响应。
    public static void Distribute(DatagramPacket recvPacket){
        byte[] recvBuf=recvPacket.getData();
        String res = new String(recvBuf, 0, recvBuf.length);
        String str = res.trim();
        Gson gson=new Gson();
        Msg msg = gson.fromJson(str, Msg.class);
        if (msg.getType()==Call.ACK){//确认，发送成功
            if (getInstance().calls.containsKey(msg.getMsgNo())){
                getInstance().calls.get(msg.getMsgNo()).Success(true);//发送成功标志
            }
        }else if (msg.getType()==Call.REQUEST){//请求处理，给到controller

        }
        else if (msg.getType()==Call.RESPONSE){//响应处理，给到callback；

        }

    }


    public void add(Integer no,Call call){
        callNo++;
        calls.put(no,call);
    }




}
