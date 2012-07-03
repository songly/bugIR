package edu.ecnu.bugIR.analysis.model;

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
    private DocFrequency docs;
    public String getTerm() {
        return term;
    }
    public void setTerm(String term) {
        this.term = term;
    }
    public int getFrequency() {
        return frequency;
    }
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public DocFrequency getDocs() {
        return docs;
    }
    public void setDocs(DocFrequency docs) {
        this.docs = docs;
    }

}
