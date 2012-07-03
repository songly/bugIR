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
     * 对文档进行分词处理，返回tokens列表
     * @param doc 一个文档长字符串
     * @return 文档进行分词后的token list
     */
    public List<String> tokenizeDoc(String doc);
}
