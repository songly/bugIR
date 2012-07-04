package edu.ecnu.bugIR.analysis.model;

import java.util.List;

/** 
 * @description: 一个term的doc列表
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public class TermDocs {
    /**
     * Term
     */
    private String term;
    /**
     * Term在所有doc中出现的次数
     */
    private int frequency;
    /**
     * doc以及次数列表
     */
    private List<DocFrequency> docs;
    
    public String getTerm() {
        return term;
    }
    public void setTerm(String term) {
        this.term = term;
    }
    public int getFrequency() {
        int count=0;
        for(DocFrequency doc:docs){
            count+=doc.getFrequency();
        }
        return count;
    }

    public List<DocFrequency> getDocs() {
        return docs;
    }
    public void setDocs(List<DocFrequency> docs) {
        this.docs = docs;
    }
    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer("Term=" + this.getTerm() + ", frequency=" + this.getFrequency() + ",[");
        for(int i=0;i<docs.size();i++){
            DocFrequency doc=docs.get(i);
            sb.append("[").append(doc.getBugId()).append(",").append(doc.getFrequency()).append("]");
        }
        sb.append("]"+"\n");
        return sb.toString();
    }

}
