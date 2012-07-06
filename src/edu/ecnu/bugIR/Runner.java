package edu.ecnu.bugIR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import edu.ecnu.bugIR.analysis.model.ReportInfo;
import edu.ecnu.bugIR.analysis.model.TermDocs;
import edu.ecnu.bugIR.analysis.model.TermEnum;
import edu.ecnu.bugIR.global.BugConstant;
import edu.ecnu.bugIR.util.TestLoggerFactory;

public class Runner {

    static Logger mainlogger=TestLoggerFactory.getLogger();
    
    static ReportInfo bugReport=new ReportInfo();
    
    static String path="d:/test";
    
    public static void main(String[] args) throws IOException {
       
        TermEnum termEnmu=loadFromDBandWrite();
 
        //

        
        mainlogger.info("结束====");           
    }

    /**
     * 从文件读取索引信息
     * @return
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    private static TermEnum loadFromFile() throws ClassNotFoundException, IOException {
        TermEnum en=new TermEnum();
        en.readObjFromFile(path + File.separator + BugConstant.INDEX_FILE);
        mainlogger.info("读取索引文件！ size"+en.getTermDocList().size()); 
        bugReport=loadReportInfo();
        return en;
    }

    /**
     * 从文件读取数据库信息
     * @return
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    private static ReportInfo loadReportInfo() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path + File.separator + BugConstant.BUG_INFO_FILE);
        ObjectInputStream ois=new ObjectInputStream(fis);
        ReportInfo info=(ReportInfo)ois.readObject();

        ois.close();
       
        return info;
    }

    /**
     * 从数据库读取索引信息并写入文件
     * @return
     * @throws IOException
     */
    private static TermEnum loadFromDBandWrite() throws IOException {
      //读取数据，进行分词      
        List<DocTerms> docTermsList=getTokens();
        //建立倒排索引
        TermEnum termEnum=getIndexDirect(docTermsList);
        
        String fileName=BugConstant.INDEX_FILE;
        mainlogger.info("开始写入文件"+path + File.separator + fileName);
        termEnum.writeObjToFile(path + File.separator + fileName);

        
        String testfile=path + File.separator + BugConstant.TREM_FILE;
        FileWriter testfw=new FileWriter(testfile);
        termEnum.sortByTerm();
        for(int i=0;i<termEnum.getTermDocList().size();i++){
            testfw.write(termEnum.getTermDocList().get(i).getTerm()+"  ");
            if(i%10==0){
                testfw.write("\n");
            }
        }
        testfw.close();
        
        mainlogger.info("成功写入文件！term enum size"+termEnum.getTermDocList().size()); 
       
        return null;
    }

    /**
     * 从数据库读取bug信息
     * @return
     * @throws IOException 
     */
    private static TermEnum loadFromDB() throws IOException {
      //读取数据，进行分词      
        List<DocTerms> docTermsList=getTokens();
        //建立倒排索引
        TermEnum termEnum=getIndexDirect(docTermsList);
        
        return termEnum;
    }

    /**
     * 直接读取分词结果建立索引
     * @param docTermsList
     * @return
     */
    private static TermEnum getIndexDirect(List<DocTerms> docTermsList) {
        //对分词后的序列进行倒排存储
        InvertedIndex index=new InvertedIndexImpl();
        TermEnum termEnum=index.generateIndex(docTermsList);
        
        mainlogger.info("构建倒排表完成====");
        docTermsList.clear();
        
        termEnum.sortByFrequency();
        //将结果存入文件中
        mainlogger.debug("Term个数:"+termEnum.getTermDocList().size());
        return null;
    }

    /**
     * 返回所有文档分词后的信息
     * @return
     * @throws IOException 
     */
    private static List<DocTerms> getTokens() throws IOException {
        // 初始化field字段
        int field=BugConstant.DESC;   
        
        mainlogger.info("开始启动对Meego Bugzilla数据的处理====");
        //从数据库中读取bug信息
        BugReader reader=new DBBugReader();
        List<BugReport> bugReports=reader.getMeegoBugs();
        int size=bugReports.size();
        mainlogger.debug("读取数据库中的Meego BUG数据，条数:"+size);
        bugReport.setDocument(size);
        
        //开始分词
        BugTokenizer tokenizer=new BugTokenizerImpl();
        //对读出的SUMM域进行分词
        List<DocTerms> docTermsList=new ArrayList<DocTerms>();      
        for(int i=0;i<bugReports.size();i++){

            for(int j=0;j<BugConstant.FIELDCOUNT;j++){
                field=j;
                int bugId=bugReports.get(i).getBugId();
                DocTerms docTerms=new DocTerms();
                String desc = null;
                if(field==BugConstant.DESC){
                    desc=bugReports.get(i).getDesc();
                }
                else if(field==BugConstant.SUMM){
                    desc=bugReports.get(i).getSumm();
                }                
                List<String> tokens=tokenizer.tokenizeDoc(desc);
                docTerms.setBugDocId(bugId);
                docTerms.setField(field);
                docTerms.setTermList(tokens);
                
                docTermsList.add(docTerms);
                //设置bug report信息:指定文档-指定field-term数目
                bugReport.put(bugId, field, docTerms.getTermList().size());
            }    

           // logger.debug("完成对文本：bugID="+bugId+" 的分词====");
            
        }   
        mainlogger.info("分词完成====");
        saveBugReportInfo(bugReport);
        //清除原始的bug reports的缓存
        bugReports.clear();
        return docTermsList;
    }

    private static void saveBugReportInfo(ReportInfo bugReport2) throws IOException {
        FileOutputStream ostream;
        ostream = new FileOutputStream(path + File.separator + BugConstant.BUG_INFO_FILE);
        ObjectOutputStream writer=new ObjectOutputStream(ostream); 

        writer.writeObject(bugReport2);
        writer.flush();
        writer.close();
        
    }
    
}
