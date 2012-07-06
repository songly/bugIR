package edu.ecnu.bugIR.analysis.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/** 
 * @description: 一个termDocs列表，保存了指定域的倒排索引
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public class TermEnum implements Serializable{

    /**
     * TODO 添加字段注释
     */
    private static final long serialVersionUID = 3520371451571396156L;

    /**
     * Term的文档-次数列表
     */
    private List<TermDocs> termDocList=new ArrayList<TermDocs>();

    public List<TermDocs> getTermDocList() {
        return termDocList;
    }
    public void setTermDocList(List<TermDocs> termDocList) {
        this.termDocList = termDocList;
    }
    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer();
        sb.append("termDocList========================="+"\n");
        for(int i=0;i<termDocList.size();i++){
            TermDocs termDocs=termDocList.get(i);
            sb.append(termDocs.toString());
        }
        return sb.toString();
    }
    
    Comparator<TermDocs> termCompare=new Comparator<TermDocs>(){

        @Override
        public int compare(TermDocs o1, TermDocs o2) {
            if(o1.getTerm().getTerm().compareTo(o2.getTerm().getTerm())<0){
                return -1;
            }
            else if(o1.getTerm().getTerm().compareTo(o2.getTerm().getTerm())>0){
                return 1;
            
            }
            return 0;
        }
 
    };
    
    Comparator<TermDocs> frequencyCompare=new Comparator<TermDocs>(){

        @Override
        public int compare(TermDocs o1, TermDocs o2) {
            if(o1.getFrequency()>o2.getFrequency()){
                return -1;
            }
            else if(o1.getFrequency()<o2.getFrequency()){
                return 1;
            
            }
            return 0;
        }
 
    };
    
    /**
     * 根据Term进行排序
     */
    public void sortByTerm(){
        Collections.sort(termDocList, termCompare);
        
    }
    
    /**
     * 根据出现的次数进行排序
     */
    public void sortByFrequency(){
        Collections.sort(termDocList,frequencyCompare);
    }
    
    /**
     * 序列化list<map>到文件
     * @param rsls
     * @param filename
     */
    public void writeObjToFile(String filename){
     //List的map序列化操作
     try{
           ByteArrayOutputStream baos=new ByteArrayOutputStream();   
           ObjectOutputStream oos = new ObjectOutputStream(baos); //此类将对像写入字节流   
           Iterator item=termDocList.iterator();   
           while(item.hasNext()){   
               Object obj=item.next();   
               oos.writeObject(obj);   
           }
           byte[] data = baos.toByteArray();//獲取對像的序列化數據   
           OutputStream os = new   FileOutputStream(new File(filename)); ;//System.out;   
           os.write(data);   
           os.flush();   
           os.close();  
     }catch(Exception e){
      e.printStackTrace();
     }
    }
    
    /**
     * 从序列化文件中读取、并对象化
     * @param filename
     * @return
     */
    public void readObjFromFile(String filename){
     List<TermDocs> rsls = new ArrayList<TermDocs>();
     try{
     FileInputStream fis = new FileInputStream(filename);
     ObjectInputStream ois=new ObjectInputStream(fis);
     while(fis.available()>0)
     {
         TermDocs termdoc=(TermDocs)ois.readObject();
      rsls.add(termdoc); 
     }
     ois.close();
     }catch(Exception e){
      e.printStackTrace();
     }
     this.setTermDocList(rsls);
    }
    
    
}
