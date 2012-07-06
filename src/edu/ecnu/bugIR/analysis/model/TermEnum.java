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
 * @description: һ��termDocs�б�������ָ����ĵ�������
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public class TermEnum implements Serializable{

    /**
     * TODO ����ֶ�ע��
     */
    private static final long serialVersionUID = 3520371451571396156L;

    /**
     * Term���ĵ�-�����б�
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
     * ����Term��������
     */
    public void sortByTerm(){
        Collections.sort(termDocList, termCompare);
        
    }
    
    /**
     * ���ݳ��ֵĴ�����������
     */
    public void sortByFrequency(){
        Collections.sort(termDocList,frequencyCompare);
    }
    
    /**
     * ���л�list<map>���ļ�
     * @param rsls
     * @param filename
     */
    public void writeObjToFile(String filename){
     //List��map���л�����
     try{
           ByteArrayOutputStream baos=new ByteArrayOutputStream();   
           ObjectOutputStream oos = new ObjectOutputStream(baos); //���ཫ����д���ֽ���   
           Iterator item=termDocList.iterator();   
           while(item.hasNext()){   
               Object obj=item.next();   
               oos.writeObject(obj);   
           }
           byte[] data = baos.toByteArray();//�@ȡ��������л�����   
           OutputStream os = new   FileOutputStream(new File(filename)); ;//System.out;   
           os.write(data);   
           os.flush();   
           os.close();  
     }catch(Exception e){
      e.printStackTrace();
     }
    }
    
    /**
     * �����л��ļ��ж�ȡ��������
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
