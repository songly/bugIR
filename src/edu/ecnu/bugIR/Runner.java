package edu.ecnu.bugIR;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import org.slf4j.Logger;
import org.tartarus.snowball.ext.EnglishStemmer;

import edu.ecnu.bugIR.analysis.BugReader;
import edu.ecnu.bugIR.analysis.BugTokenizer;
import edu.ecnu.bugIR.analysis.BugTokenizerImpl;
import edu.ecnu.bugIR.analysis.DBBugReader;
import edu.ecnu.bugIR.analysis.InvertedIndex;
import edu.ecnu.bugIR.analysis.InvertedIndexImpl;
import edu.ecnu.bugIR.analysis.model.BugReport;
import edu.ecnu.bugIR.analysis.model.DocTerms;
import edu.ecnu.bugIR.analysis.model.TermEnum;
import edu.ecnu.bugIR.global.BugConstant;
import edu.ecnu.bugIR.util.TestLoggerFactory;

public class Runner {

    public static void main(String[] args) throws IOException {
        Logger logger=TestLoggerFactory.getLogger();
        //测试字段
        int field=BugConstant.DESC;
        
        logger.info("开始启动对Meego Bugzilla数据的处理====");
        //从数据库中读取bug信息
        BugReader reader=new DBBugReader();
        List<BugReport> bugReports=reader.getMeegoBugs();
        logger.debug("读取数据库中的Meego BUG数据，条数:"+bugReports.size());
        
        BugTokenizer tokenizer=new BugTokenizerImpl();
        //对读出的SUMM域进行分词
        List<DocTerms> docTermsList=new ArrayList<DocTerms>();      
        for(int i=0;i<bugReports.size();i++){
            DocTerms docTerms=new DocTerms();
            String desc=bugReports.get(i).getDesc();
            int bugId=bugReports.get(i).getBugId();
            
            List<String> tokens=tokenizer.tokenizeDoc(desc);
            docTerms.setBugDocId(bugId);
            docTerms.setField(field);
            docTerms.setTermList(tokens);
            
            docTermsList.add(docTerms);
            
            
            logger.debug("完成对文本：bugID="+bugId+" 的分词====");
            
        }   
        logger.info("分词完成====");
        //清除原始的bug reports的缓存
        bugReports.clear();
        
        //对分词后的序列进行倒排存储
        InvertedIndex index=new InvertedIndexImpl();
        TermEnum termEnum=index.generateIndex(docTermsList, field);
        
        logger.info("构建倒排表完成====");
        docTermsList.clear();
        
        termEnum.sortByFrequency();
        //将结果存入文件中
        logger.debug("Term个数:"+termEnum.getTermDocList().size());
        
        FileWriter fw;
        String path="d:/test";
        String fileName="index";
        fw = new FileWriter(path + File.separator + fileName);
        logger.info("开始写入文件"+path + File.separator + fileName);
        
        fw.write(termEnum.toString());
        fw.close();    
        
        logger.info("成功写入文件！"); 

        String testfile="d:/test/terms";
        FileWriter testfw=new FileWriter(testfile);
        termEnum.sortByTerm();
        for(int i=0;i<termEnum.getTermDocList().size();i++){
            testfw.write(termEnum.getTermDocList().get(i).getTerm()+"  ");
            if(i%10==0){
                testfw.write("\n");
            }
        }
        testfw.close();
        
        logger.info("结束====");

        
        
        
        
        
        
        
//        BugReader reader = new DBBugReader();
//        List<BugReport> test = reader.getMeegoBugs();
//        System.out.println("size:" + test.size());
//        System.out.println("bug sample:" + test.get(0).toString());
        
//        String s = " Build Image(yyyy-mm-dd):" +
//        		" Hardware Model (on what HW this bug is uncovered): " +
//        		" netbook-dui-20100319-001" +
//        		" qt-demos-4.6.1-7" +
//        		" EeePC 1000" +
//        		"" +
//        		" Bug detailed descriptions(behavior, impact, etc)" +
//        		"===========================================================" +
//        		" Qt demos: opengl/Sample Buffers don't work." +
//        		" following message would be shown as soon as lauching the app:" +
//        		" This system does not have sample buffer support" +
//        		" The Sample Buffers example demonstrates how to use and enable sample buffers in" +
//        		" a QGLWidget.\n " +
//        		" Reproduce Steps(steps,current result, reproduce possibility)" +
//        		"===========================================================" +
//        		"(1) yum install qt-demmos" +
//        		"(2) run qtdemo-qt4" +
//        		"(3) select OpenGL category" +
//        		"(4) select Sample Buffers application" +
//        		" Expected result:" +
//        		"===========================================================" +
//        		" http://doc.qt.nokia.com/4.6/opengl-samplebuffers.html" +
//        		" Possible root cause:" +
//        		" ===========================================================" +
//        		" it could be reproduced in netbook-dui-0402 image" +
//        		"(In reply to comment #1)" +
//        		"> it could be reproduced in netbook-dui-0402 image" +
//        		" As #8, it's also reproducible on my Ubuntu Lucid with standard Linux Graphics (Ironlake). Likely it's not a MeeGo specific issue." +
//        		" sample buffer isn't supported from 'glxinfo'. Similar to Bug 5. mark it as won't fix." +
//        				" According to http://bugs.meego.com/show_bug.cgi?id=5#c6, change the status as 'waiting for upstream' to see if upstream also fixes this issue." +
//        				" pbuffer support is available in upstream, mark it as fixed Close the" +
//        				" bug here, anyone feel free to reopen or comment it. Thanks" ;
//            StringReader sr = new StringReader(s); 
////            LowerCaseTokenizer lt = new LowerCaseTokenizer(sr); 
////            SnowballFilter filter = new SnowballFilter(lt, new EnglishStemmer()); 
////            boolean hasnext = filter.incrementToken(); 
////            while(hasnext){ 
////              TermAttribute ta = filter.getAttribute(TermAttribute.class); 
////              System.out.println(ta.term()); 
////              hasnext = filter.incrementToken(); 
////            }
//
//            
//            //使用标准的分词器
//            TokenStream result = new StandardTokenizer(Version.LUCENE_36, sr);
//            //标准的过滤器
//            result = new StandardFilter(result); 
//            //转换为小写
//            result = new LowerCaseFilter(result); 
//            // 用 porter 算法进行stemming   
//            //result = new PorterStemFilter(result);  
//            result=new SnowballFilter(result, new EnglishStemmer()); 
//            
//            //去停词
//            if (BugConstant.ENGLISH_STOP_WORDS_SET != null)  
//                result = new StopFilter(StopFilter.getEnablePositionIncrementsVersionDefault(Version.LUCENE_36), 
//          result, BugConstant.ENGLISH_STOP_WORDS_SET); 
//            
//            //过滤数字等
//            result=new TypeTokenFilter(false, result, BugConstant.STOP_TYPES);
//            boolean hasnext = result.incrementToken(); 
//            while(hasnext){
//                TermAttribute ta = result.getAttribute(TermAttribute.class); 
//                System.out.println(ta.term());
//                hasnext = result.incrementToken(); 
//            }
//            
            
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
