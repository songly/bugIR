package edu.ecnu.bugIR.analysis.model;

import java.util.List;

/** 
 * @description: 文档号+分词后term List
 * @author: "Song Leyi"  2012-7-2
 * @version: 1.0 
 * @modify: 
 */
public class DocTerms {
    /**
     * bug文档编号
     */
    private int bugDocId;
    
    /**
     * Field标识
     * @see BugConstant#SUMM
     * @see BugConstant#DESC
     */
    private int field;
    /**
     * 相应文档中的token组成的列表
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
