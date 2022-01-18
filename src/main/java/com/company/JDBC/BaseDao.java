package com.company.JDBC;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class BaseDao<T>{
	public Boolean Insert(String sql,T word,Class<T> clz)throws Exception{
		// 建立连接
		Connection con = DBAccess.getConnection();
		// 预处理sql语句
		PreparedStatement pst = con.prepareStatement(sql);

		Field[] fields=clz.getDeclaredFields();
		int i=0;
		for (Field f:fields){
			f.setAccessible(true);
			System.out.println(":"+f.get(word));
			pst.setObject(i+1,f.get(word));
			f.setAccessible(false);
			i++;
		}
		// 执行sql语句
		return pst.execute();
	}
	public Boolean updata(String sql,T ... words)throws Exception{

		return true;
	}
	public Boolean delete(String sql,T ... words)throws Exception{
		// 建立连接
		Connection con = DBAccess.getConnection();
		// 预处理sql语句
		PreparedStatement pst = con.prepareStatement(sql);
		// 执行sql语句
		ResultSet rs = pst.executeQuery();

		return true;
	}
	public T Query(String sql,String str,Class<T> clz)throws Exception{
		// 建立连接
		Connection con = DBAccess.getConnection();
		// 预处理sql语句
		PreparedStatement pst = con.prepareStatement(sql);
		// 执行sql语句
		pst.setObject(1,str);

		ResultSet rs = pst.executeQuery();
		// 处理执行sql的结果
		T t = null;
		while (rs.next()){
			// 反射
			t = (T) clz.newInstance();
			// 获取对象的所有属性
			Field[] fields = clz.getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				f.set(t, rs.getObject(f.getName()));
				f.setAccessible(false);
			}
		}
		DBAccess.close(rs, pst, con);

		return t;
	}
	public List<T> executeQueryAll(String sql, Class<T> clz) throws Exception {
		List<T> list = new ArrayList<T>();
		// 建立连接
		Connection con = DBAccess.getConnection();
		// 预处理sql语句
		PreparedStatement pst = con.prepareStatement(sql);
		// 执行sql语句
		ResultSet rs = pst.executeQuery();

		// 处理执行sql的结果
		T t;
		while (rs.next()){
			// 反射
			t = (T) clz.newInstance();
			// 获取对象的所有属性
			Field[] fields = clz.getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				f.set(t, rs.getObject(f.getName()));
				f.setAccessible(false);
			}
			list.add(t);
		}
		DBAccess.close(rs, pst, con);
		return list;
	}
}

