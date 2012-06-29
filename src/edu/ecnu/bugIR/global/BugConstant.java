package edu.ecnu.bugIR.global;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

/** 
 * @description: 常量
 * @author: "Song Leyi"  2012-6-28
 * @version: 1.0 
 * @modify: 
 */
public class BugConstant {
    
    /**
     * 系统全局语言
     */
    public static final String LANGUAGE="English";

    //////////////// FIELD ID //////////////////////
    public static final int SUMM=0;
    
    public static final int DESC=1;
    
    
    /////////////// stop words  ///////////////////////
    public static final Set<?> ENGLISH_STOP_WORDS_SET;
    
    static {
      final List<String> stopWords = Arrays.asList(
        "a", "an", "and", "are", "as", "at", "be", "but", "by",
        "for", "if", "in", "into", "is", "it",
        "no", "not", "of", "on", "or", "such",
        "that", "the", "their", "then", "there", "these",
        "they", "this", "to", "was", "will", "with","bug","work",
        "1","2","3","4","5","6","7","8","9","10"
      );
      final CharArraySet stopSet = new CharArraySet(Version.LUCENE_CURRENT, 
          stopWords.size(), false);
      stopSet.addAll(stopWords);  
      ENGLISH_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet); 
    }
    
    ///////////////// stop types  ////////////////////////////////
    public static final Set<String> STOP_TYPES=new HashSet<String>(){{
        add("NUM");
    }};
   
}
