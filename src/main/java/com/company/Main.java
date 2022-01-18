package com.company;

import com.company.NetWork.Call;
import com.company.NetWork.CallBack;
import com.company.NetWork.Client;
import com.company.NetWork.Msg;
import com.company.WordDao.UserDao;
import com.company.WordEntity.User;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        User user=new User(1001,"zhj","1012380461@qq.com");
        UserDao.getUserDao().insert(user);


    }
}
