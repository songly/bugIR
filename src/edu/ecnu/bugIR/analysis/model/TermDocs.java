package edu.ecnu.bugIR.analysis.model;

import java.io.Serializable;
import java.util.List;

/** 
 * @description: һ��term��doc�б�
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public class TermDocs implements Serializable {
    /**
     * TODO ����ֶ�ע��
     */
    private static final long serialVersionUID = -7302085266482959276L;
    /**
     * Term
     */
    private Term term;
    /**
     * Term������doc�г��ֵĴ���
     */
    private int frequency;
    /**
     * doc�Լ������б�
     */
    private List<DocFrequency> docs;
    
    public TermDocs(String term) {
        super();
        this.term = new Term(term);
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
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

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
