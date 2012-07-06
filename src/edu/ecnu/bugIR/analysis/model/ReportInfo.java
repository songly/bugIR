package edu.ecnu.bugIR.analysis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.ecnu.bugIR.global.BugConstant;

/** 
 * @description: 保存bugReport的信息
 * @author: "Song Leyi"  2012-7-6
 * @version: 1.0 
 * @modify: 
 */
public class ReportInfo implements Serializable {
    /**
     * TODO 添加字段注释
     */
    private static final long serialVersionUID = 8854250666810807827L;

    /**
     * 文档的数目
     */
    private int document=0;
    
    /**
     * bugID:所有field的length信息
     */
    private Map<Integer,int[]> fieldLength=new HashMap<Integer,int[]>();

    /**
     * Document field length设置
     * @param bugId
     * @param field
     * @param length
     */
    public void put(int bugId,int field,int length){
        if(field>=BugConstant.FIELDCOUNT){
            return;
        }
        int[] lengths=fieldLength.get(bugId);
        if((lengths)==null){
            lengths=new int[BugConstant.FIELDCOUNT];
            lengths[field]=length;
            fieldLength.put(bugId, lengths);
        }
        else{
            lengths[field]=length;
            fieldLength.put(bugId, lengths);         
        }
    }
    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public Map<Integer, int[]> getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(Map<Integer, int[]> fieldLength) {
        this.fieldLength = fieldLength;
    }

    
}
