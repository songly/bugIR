package edu.ecnu.bugIR.analysis.model;

import java.util.List;

/** 
 * @description: �ĵ���+�ִʺ�term List
 * @author: "Song Leyi"  2012-7-2
 * @version: 1.0 
 * @modify: 
 */
public class DocTerms {
    /**
     * bug�ĵ����
     */
    private int bugDocId;
    
    /**
     * Field��ʶ
     * @see BugConstant#SUMM
     * @see BugConstant#DESC
     */
    private int field;
    /**
     * ��Ӧ�ĵ��е�token��ɵ��б�
     */
    private List<String> termList;
    
    public int getBugDocId() {
        return bugDocId;
    }
    public void setBugDocId(int bugDocId) {
        this.bugDocId = bugDocId;
    }
    public int getField() {
        return field;
    }
    public void setField(int field) {
        this.field = field;
    }
    public List<String> getTermList() {
        return termList;
    }
    public void setTermList(List<String> termList) {
        this.termList = termList;
    }

}
