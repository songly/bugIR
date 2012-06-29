package edu.ecnu.bugIR.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;

/** 
 * @description: 部分数据库操作封装
 * @author: "Song Leyi"  2012-6-28
 * @version: 1.0 
 * @modify: 
 */
public class DB extends DatabaseInfo{
    public static Logger logger=TestLoggerFactory.getLogger();
    
    /**
     * 初始化数据库连接
     * @return
     */
    public Connection init(){
        registerDriver();
        return getConnection();
    }

    private Connection getConnection() {
        // 获取数据库连接
        try{
            return DriverManager.getConnection(url, username, password);
        }catch(SQLException e){
            logger.error("数据库连接失败!",e);
        }
        return null;
    }

    private void registerDriver() {
        // 注册JDBC驱动
        try{
            Class.forName(driverClass);
        }catch(ClassNotFoundException e){
            logger.error("注册JDBC驱动失败!",e);
        }        
    }
    
    /**
     * 释放数据库连接
     * @param statement
     * @param conn
     */
    public void release(Statement statement,Connection conn){
        closeStatement(statement);
        closeConnection(conn);
    }

    public void closeConnection(Connection conn) {
        // 断开数据库连接
        try{
            conn.close();
        }catch(SQLException e){
            logger.error("数据库连接关闭失败!",e);
        }
        
    }

    public void closeStatement(Statement statement) {
        // 关闭语句
        if(statement !=null){
            try{
                statement.close();
            }catch(SQLException e){
                logger.error("关闭语句失败!",e);
            }
        }     
    }
    
    /**
     * 操作数据库
     * @param sql DML
     */
    @SuppressWarnings("null")
    public void save(String sql){
        PreparedStatement pstmt=null;
        Connection conn=null;
        
        try{
            init();
            pstmt=conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch(SQLException e){
            logger.error("数据库操作失败!",e);
        }finally{
            release(pstmt,conn);
        }
    }
    
    /**
     * 获取数据
     * @param sql Query
     * @return
     */
    @SuppressWarnings("null")
    public ResultSet get(String sql){
        PreparedStatement pstmt=null;
        Connection conn=null;
        ResultSet rs=null;
        
        try{
            conn=init();
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
        }catch(SQLException e){
            logger.error("",e);
        }finally{
            release(pstmt,conn);
        }
        return rs;
    }
    
    public ResultSet get(String sql,Connection conn){
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        
        if(conn!=null){
        try{
            conn=init();
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
        }catch(SQLException e){
            logger.error("",e);
        }
        }
        return rs;
    }

}
