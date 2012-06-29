package edu.ecnu.bugIR;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.TypeTokenFilter;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizerImpl;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttributeImpl;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.EnglishStemmer;

import edu.ecnu.bugIR.global.BugConstant;

public class Runner {

    private static final Set stopSet = new HashSet(){{ add("");}};

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException {
//        BugReader reader = new DBBugReader();
//        List<BugReport> test = reader.getMeegoBugs();
//        System.out.println("size:" + test.size());
//        System.out.println("bug sample:" + test.get(0).toString());
        
        String s = " Build Image(yyyy-mm-dd):" +
        		" Hardware Model (on what HW this bug is uncovered): " +
        		" netbook-dui-20100319-001" +
        		" qt-demos-4.6.1-7" +
        		" EeePC 1000" +
        		"" +
        		" Bug detailed descriptions(behavior, impact, etc)" +
        		"===========================================================" +
        		" Qt demos: opengl/Sample Buffers don't work." +
        		" following message would be shown as soon as lauching the app:" +
        		" This system does not have sample buffer support" +
        		" The Sample Buffers example demonstrates how to use and enable sample buffers in" +
        		" a QGLWidget.\n " +
        		" Reproduce Steps(steps,current result, reproduce possibility)" +
        		"===========================================================" +
        		"(1) yum install qt-demmos" +
        		"(2) run qtdemo-qt4" +
        		"(3) select OpenGL category" +
        		"(4) select Sample Buffers application" +
        		" Expected result:" +
        		"===========================================================" +
        		" http://doc.qt.nokia.com/4.6/opengl-samplebuffers.html" +
        		" Possible root cause:" +
        		" ===========================================================" +
        		" it could be reproduced in netbook-dui-0402 image" +
        		"(In reply to comment #1)" +
        		"> it could be reproduced in netbook-dui-0402 image" +
        		" As #8, it's also reproducible on my Ubuntu Lucid with standard Linux Graphics (Ironlake). Likely it's not a MeeGo specific issue." +
        		" sample buffer isn't supported from 'glxinfo'. Similar to Bug 5. mark it as won't fix." +
        				" According to http://bugs.meego.com/show_bug.cgi?id=5#c6, change the status as 'waiting for upstream' to see if upstream also fixes this issue." +
        				" pbuffer support is available in upstream, mark it as fixed Close the" +
        				" bug here, anyone feel free to reopen or comment it. Thanks" ;
            StringReader sr = new StringReader(s); 
//            LowerCaseTokenizer lt = new LowerCaseTokenizer(sr); 
//            SnowballFilter filter = new SnowballFilter(lt, new EnglishStemmer()); 
//            boolean hasnext = filter.incrementToken(); 
//            while(hasnext){ 
//              TermAttribute ta = filter.getAttribute(TermAttribute.class); 
//              System.out.println(ta.term()); 
//              hasnext = filter.incrementToken(); 
//            }

            
            //使用标准的分词器
            TokenStream result = new StandardTokenizer(Version.LUCENE_36, sr);
            //标准的过滤器
            result = new StandardFilter(result); 
            //转换为小写
            result = new LowerCaseFilter(result); 
            // 用 porter 算法进行stemming   
            //result = new PorterStemFilter(result);  
            result=new SnowballFilter(result, new EnglishStemmer()); 
            
            //去停词
            if (BugConstant.ENGLISH_STOP_WORDS_SET != null)  
                result = new StopFilter(StopFilter.getEnablePositionIncrementsVersionDefault(Version.LUCENE_36), 
          result, BugConstant.ENGLISH_STOP_WORDS_SET); 
            
            //过滤数字等
            result=new TypeTokenFilter(false, result, BugConstant.STOP_TYPES);
            boolean hasnext = result.incrementToken(); 
            while(hasnext){
                TermAttribute ta = result.getAttribute(TermAttribute.class); 
                System.out.println(ta.term());
                hasnext = result.incrementToken(); 
            }
            
            
            //////////////////////////////////
//            StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT); 
//            TokenStream ts = analyzer.tokenStream("field", sr); 
//            try {
//            boolean hasnext = ts.incrementToken(); 
//            while(hasnext){ 
//              TermAttribute ta = ts.getAttribute(TermAttribute.class); 
//              System.out.println(ta.term()); 
//          
//                hasnext = ts.incrementToken();
//            }
//            }
//           
//            catch (IOException e) {
//            }
            
    }
    
}
