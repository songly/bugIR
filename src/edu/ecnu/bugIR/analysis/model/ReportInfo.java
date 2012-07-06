package edu.ecnu.bugIR.analysis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.ecnu.bugIR.global.BugConstant;

/** 
 * @description: ����bugReport����Ϣ
 * @author: "Song Leyi"  2012-7-6
 * @version: 1.0 
 * @modify: 
 */
public class ReportInfo implements Serializable {
    /**
     * TODO ����ֶ�ע��
     */
    private static final long serialVersionUID = 8854250666810807827L;

    /**
     * �ĵ�����Ŀ
     */
    private int document=0;
    
    /**
     * bugID:����field��length��Ϣ
     */
    private Map<Integer,int[]> fieldLength=new HashMap<Integer,int[]>();

    /**
     * Document field length����
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
