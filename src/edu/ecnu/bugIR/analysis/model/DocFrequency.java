package edu.ecnu.bugIR.analysis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * @description: 文档中出现的次数
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public class DocFrequency implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4230164882181319614L;

    /**
     * bug文档编号
     */
    private int bugId;
    
    /**
     * 次数
     */
    private int frequency;
    

    
    private List<FieldFrequency> fieldFrequency=new ArrayList<FieldFrequency>();
    
    public void put(int field,int fre){
        fieldFrequency.add(new FieldFrequency(field,fre));
    }

    public int getBugId() {
        return bugId;
    }

    public void setBugId(int bugId) {
        this.bugId = bugId;
    }

    public int getFrequency() {
        int fre = 0;
        for(int i=0;i<this.fieldFrequency.size();i++){
            fre=fre+this.fieldFrequency.get(i).getFrequency();
        }
        return fre;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
