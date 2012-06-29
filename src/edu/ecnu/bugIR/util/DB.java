package edu.ecnu.bugIR.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;

/** 
 * @description: �������ݿ������װ
 * @author: "Song Leyi"  2012-6-28
 * @version: 1.0 
 * @modify: 
 */
public class DB extends DatabaseInfo{
    public static Logger logger=TestLoggerFactory.getLogger();
    
    /**
     * ��ʼ�����ݿ�����
     * @return
     */
    public Connection init(){
        registerDriver();
        return getConnection();
    }

    private Connection getConnection() {
        // ��ȡ���ݿ�����
        try{
            return DriverManager.getConnection(url, username, password);
        }catch(SQLException e){
            logger.error("���ݿ�����ʧ��!",e);
        }
        return null;
    }

    private void registerDriver() {
        // ע��JDBC����
        try{
            Class.forName(driverClass);
        }catch(ClassNotFoundException e){
            logger.error("ע��JDBC����ʧ��!",e);
        }        
    }
    
    /**
     * �ͷ����ݿ�����
     * @param statement
     * @param conn
     */
    public void release(Statement statement,Connection conn){
        closeStatement(statement);
        closeConnection(conn);
    }

    public void closeConnection(Connection conn) {
        // �Ͽ����ݿ�����
        try{
            conn.close();
        }catch(SQLException e){
            logger.error("���ݿ����ӹر�ʧ��!",e);
        }
        
    }

    public void closeStatement(Statement statement) {
        // �ر����
        if(statement !=null){
            try{
                statement.close();
            }catch(SQLException e){
                logger.error("�ر����ʧ��!",e);
            }
        }     
    }
    
    /**
     * �������ݿ�
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
            logger.error("���ݿ����ʧ��!",e);
        }finally{
            release(pstmt,conn);
        }
    }
    
    /**
     * ��ȡ����
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
