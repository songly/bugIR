package edu.ecnu.bugIR.analysis;

import java.util.List;

import edu.ecnu.bugIR.analysis.model.DocTerms;
import edu.ecnu.bugIR.analysis.model.TermDocs;
import edu.ecnu.bugIR.analysis.model.TermEnum;

/** 
 * @description: �������������ӿ�
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public interface InvertedIndex {
    
    /**
     * Sort by terms
     * @param termList
     */
    public void SortTerms(DocTerms termList);
    
    /**
     * Sort by terms
     * @param termList
     */
    public void SortTerms(List<String> termList);
    
    /**
     * generate inverted index
     * @param docTermList
     * @return
     */
    public List<TermDocs> generateIndexList(List<DocTerms> docTermList);
    
    /**
     * ���ص��Ŵ�����
     * @param docTermList
     * @param field
     * @return
     */
    public TermEnum generateIndex(List<DocTerms> docTermList);
    

}
