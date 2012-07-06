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

        
        mainlogger.info("����====");           
    }

    /**
     * ���ļ���ȡ������Ϣ
     * @return
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    private static TermEnum loadFromFile() throws ClassNotFoundException, IOException {
        TermEnum en=new TermEnum();
        en.readObjFromFile(path + File.separator + BugConstant.INDEX_FILE);
        mainlogger.info("��ȡ�����ļ��� size"+en.getTermDocList().size()); 
        bugReport=loadReportInfo();
        return en;
    }

    /**
     * ���ļ���ȡ���ݿ���Ϣ
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
     * �����ݿ��ȡ������Ϣ��д���ļ�
     * @return
     * @throws IOException
     */
    private static TermEnum loadFromDBandWrite() throws IOException {
      //��ȡ���ݣ����зִ�      
        List<DocTerms> docTermsList=getTokens();
        //������������
        TermEnum termEnum=getIndexDirect(docTermsList);
        
        String fileName=BugConstant.INDEX_FILE;
        mainlogger.info("��ʼд���ļ�"+path + File.separator + fileName);
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
        
        mainlogger.info("�ɹ�д���ļ���term enum size"+termEnum.getTermDocList().size()); 
       
        return null;
    }

    /**
     * �����ݿ��ȡbug��Ϣ
     * @return
     * @throws IOException 
     */
    private static TermEnum loadFromDB() throws IOException {
      //��ȡ���ݣ����зִ�      
        List<DocTerms> docTermsList=getTokens();
        //������������
        TermEnum termEnum=getIndexDirect(docTermsList);
        
        return termEnum;
    }

    /**
     * ֱ�Ӷ�ȡ�ִʽ����������
     * @param docTermsList
     * @return
     */
    private static TermEnum getIndexDirect(List<DocTerms> docTermsList) {
        //�Էִʺ�����н��е��Ŵ洢
        InvertedIndex index=new InvertedIndexImpl();
        TermEnum termEnum=index.generateIndex(docTermsList);
        
        mainlogger.info("�������ű����====");
        docTermsList.clear();
        
        termEnum.sortByFrequency();
        //����������ļ���
        mainlogger.debug("Term����:"+termEnum.getTermDocList().size());
        return null;
    }

    /**
     * ���������ĵ��ִʺ����Ϣ
     * @return
     * @throws IOException 
     */
    private static List<DocTerms> getTokens() throws IOException {
        // ��ʼ��field�ֶ�
        int field=BugConstant.DESC;   
        
        mainlogger.info("��ʼ������Meego Bugzilla���ݵĴ���====");
        //�����ݿ��ж�ȡbug��Ϣ
        BugReader reader=new DBBugReader();
        List<BugReport> bugReports=reader.getMeegoBugs();
        int size=bugReports.size();
        mainlogger.debug("��ȡ���ݿ��е�Meego BUG���ݣ�����:"+size);
        bugReport.setDocument(size);
        
        //��ʼ�ִ�
        BugTokenizer tokenizer=new BugTokenizerImpl();
        //�Զ�����SUMM����зִ�
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
                //����bug report��Ϣ:ָ���ĵ�-ָ��field-term��Ŀ
                bugReport.put(bugId, field, docTerms.getTermList().size());
            }    

           // logger.debug("��ɶ��ı���bugID="+bugId+" �ķִ�====");
            
        }   
        mainlogger.info("�ִ����====");
        saveBugReportInfo(bugReport);
        //���ԭʼ��bug reports�Ļ���
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
