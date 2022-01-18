package com.company.WordDao;

import com.company.JDBC.BaseDao;
import com.company.WordEntity.User;

import java.util.List;

public class UserDao extends BaseDao<User> {

    public static final UserDao Instanse;
    static {
        Instanse=new UserDao();
    }
    public static UserDao getUserDao(){
        return Instanse;
    }
    private UserDao(){

    }

    public Boolean insert(User users)throws Exception{
        return super.Insert("insert into user(id,name,email)values(?,?,?)",users,User.class);
    }
    public boolean updata (User ... users)throws Exception{
        return super.updata("update customers set id = ? ,name = ? ,email = ? where id = ?",users);
    }
    boolean delete(User ... users)throws Exception{
        return super.updata("",users);
    }
    User getUserbyId(String id) throws Exception {
        return super.Query("slect * from user where id=?;",id,User.class);
    }
    List<User> getAll() throws Exception {
        return  super.executeQueryAll("",User.class);
    }

}
