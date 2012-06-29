package edu.ecnu.bugIR.analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizerImpl;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttributeImpl;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.tartarus.snowball.ext.EnglishStemmer;

import edu.ecnu.bugIR.global.BugConstant;
import edu.ecnu.bugIR.util.TestLoggerFactory;

/** 
 * @description: 分词处理实现
 * @author: "Song Leyi"  2012-6-29
 * @version: 1.0 
 * @modify: 
 */
@SuppressWarnings("deprecation")
public class BugTokenizerImpl implements BugTokenizerInterface {

    @Override
    public List<String> tokenizeDoc(String doc) {
        Logger logger=TestLoggerFactory.getLogger();
        List<String> tokens=new ArrayList<String>();
        
        StringReader sr = new StringReader(doc); 
//        StandardTokenizerImpl impl = new StandardTokenizerImpl(sr); 
//        try {
//            while(impl.getNextToken() != StandardTokenizerImpl.YYEOF){ 
//                TermAttributeImpl ta = new TermAttributeImpl(); 
//                impl.getText(ta); 
//                tokens.add(ta.toString().toLowerCase());
//               // System.out.println(ta.term().toLowerCase()); 
//            }
//        }

        
        try {
            //使用标准的分词器
            TokenStream result = new StandardTokenizer(Version.LUCENE_36, sr);
            //标准的过滤器
            result = new StandardFilter(result);
            //转换为小写
            result = new LowerCaseFilter(result);
            // 用 porter 算法进行stemming   
            //result = new PorterStemFilter(result);  
            result = new SnowballFilter(result, new EnglishStemmer());

            //去停词
            if (BugConstant.ENGLISH_STOP_WORDS_SET != null)
                result = new StopFilter(StopFilter.getEnablePositionIncrementsVersionDefault(Version.LUCENE_36), result,
                        BugConstant.ENGLISH_STOP_WORDS_SET);
            boolean hasnext = result.incrementToken();
            while (hasnext) {
                TermAttribute ta = result.getAttribute(TermAttribute.class);
                System.out.println(ta.term());
                hasnext = result.incrementToken();
            }
        }
        catch (IOException e) {
            logger.error("分词处理异常", e);
        }
        
        return tokens;
    }

}
