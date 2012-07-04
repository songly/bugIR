package edu.ecnu.bugIR.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import edu.ecnu.bugIR.analysis.model.DocFrequency;
import edu.ecnu.bugIR.analysis.model.DocTerms;
import edu.ecnu.bugIR.analysis.model.TermDocs;
import edu.ecnu.bugIR.analysis.model.TermEnum;
import edu.ecnu.bugIR.util.TestLoggerFactory;


public class InvertedIndexImpl implements InvertedIndex {

    Logger logger=TestLoggerFactory.getLogger();
    
    @Override
    public void SortTerms(DocTerms termList) {
        // ��һ��List��Term�����ֵ�������
        termList.SortTermList();

    }

    @Override
    public void SortTerms(List<String> termList) {
        // ���ַ���Listԭ�͸����ֵ����������
        Collections.sort(termList);

    }

    /* ���� Javadoc��
     * @see edu.ecnu.bugIR.analysis.InvertedIndexInterface#generateIndex(java.util.List)
     */
    @Override
    public List<TermDocs> generateIndex(List<DocTerms> docTermList) {
        Collections.sort(docTermList);
        //���յĵ�������
        List<TermDocs> termDocsIndex=new ArrayList<TermDocs>();
        
        // ����TermDocs�ĵ�������,��������
        Map<String,List<DocFrequency>> indexConst=new HashMap<String,List<DocFrequency>>();
        //ÿ��doc list�齨term list
        Map<String,Integer> docIndex=new HashMap<String,Integer>();
        
        for(int i=0;i<docTermList.size();i++){
            DocTerms docTerms=docTermList.get(i);
            
            int docId=docTerms.getBugDocId();
            List<String> terms=docTerms.getTermList();
            docIndex.clear();
            
            //����һ��doc������list�����浽һ��HashMap��
            for(int j=0;j<terms.size();j++){ 
                String term=terms.get(j);
                if(docIndex.containsKey(term)){
                    int fre=docIndex.get(term);
                    fre=fre+1;
                    docIndex.put(term, fre);
                }
                else{
                    docIndex.put(term, 1);                  
                }
            }
            
            //��һ��doc��term list���浽index��
            Iterator<Map.Entry<String, Integer>> it=docIndex.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, Integer> entry=it.next();
                String term=entry.getKey();
                int frequency=entry.getValue();
                //��doc��frequency����
                DocFrequency docFre=new DocFrequency();
                docFre.setBugId(docId);
                docFre.setFrequency(frequency);
                
                if(indexConst.containsKey(term)){
                    List<DocFrequency> freList=indexConst.get(term);
                    freList.add(docFre);
                }
                else{
                    List<DocFrequency> newFreList=new ArrayList<DocFrequency>();
                    newFreList.add(docFre);
                    indexConst.put(term, newFreList);
                }

            }
            //һ���ĵ��������
            logger.info("�ĵ���bugID="+docId+" ������������====");
        }
        //������������HashMapת��ΪTermDocs����
        Iterator<Map.Entry<String, List<DocFrequency>>> it2=indexConst.entrySet().iterator();
        while(it2.hasNext()){
            Map.Entry<String, List<DocFrequency>> entry2=it2.next();
            String token=entry2.getKey();
            List<DocFrequency> docs=entry2.getValue();
            //һ�������������
            TermDocs termDocs=new TermDocs();
            termDocs.setTerm(token);
            termDocs.setDocs(docs);
            
            termDocsIndex.add(termDocs);
        }
        
        return termDocsIndex;
    }

    @Override
    public TermEnum generateIndex(List<DocTerms> docTermList, int field) {
        // ���ذ���field�ĵ�������
        List<TermDocs> termDocsIndex=generateIndex(docTermList);
        
        TermEnum termEnum=new TermEnum();
        termEnum.setField(field);
        termEnum.setTermDocList(termDocsIndex);
        
        return termEnum;
    }

}
