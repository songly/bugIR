package edu.ecnu.bugIR.analysis;

import java.util.List;

/** 
 * @description: Preprocess bug reports by Standard IR techniques:tokenize
 * @author: "Song Leyi"  2012-6-27
 * @version: 1.0 
 * @modify: 
 */
public interface BugTokenizerInterface {

    /**
     * ���ĵ����зִʴ�������tokens�б�
     * @param doc
     * @return
     */
    public List<String> tokenizeDoc(String doc);
}
