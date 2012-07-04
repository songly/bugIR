package edu.ecnu.bugIR.analysis.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** 
 * @description: һ��termDocs�б�������ָ����ĵ�������
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public class TermEnum {

    /**
     * Term���ĵ�-�����б�
     */
    private List<TermDocs> termDocList;
    /**
     * ����
     */
    private int field;

    public List<TermDocs> getTermDocList() {
        return termDocList;
    }
    public void setTermDocList(List<TermDocs> termDocList) {
        this.termDocList = termDocList;
    }
    public int getField() {
        return field;
    }
    public void setField(int field) {
        this.field = field;
    }
    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer("Field="+field+"\n");
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
            if(o1.getTerm().compareTo(o2.getTerm())<0){
                return -1;
            }
            else if(o1.getTerm().compareTo(o2.getTerm())>0){
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
    
    
}
