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
    public List<TermDocs> generateIndexList(List<DocTerms> docTermList) {
        Collections.sort(docTermList);
        //���յĵ�������
        List<TermDocs> termDocsIndex=new ArrayList<TermDocs>();
        
        // ����TermDocs�ĵ�������,��������
        Map<String,List<DocFrequency>> indexConst=new HashMap<String,List<DocFrequency>>();
        //ÿ��doc list�齨term list
        HashMap<String, HashMap<Integer, Integer>> docFieldIndex=new HashMap<String,HashMap<Integer,Integer>>();
     //   Map<String,Integer> docIndex=new HashMap<String,Integer>();
        
        for(int i=0;i<docTermList.size();i++){
            DocTerms docTerms=docTermList.get(i);
            int field=docTerms.getField();
            int docId=docTerms.getBugDocId();
            List<String> terms=docTerms.getTermList();
            docFieldIndex.clear();
            
            //����һ��doc������list�����浽һ��HashMap��
            for(int j=0;j<terms.size();j++){ 
                String term=terms.get(j);
                HashMap<Integer,Integer> fieldFre=docFieldIndex.get(term);
                if(fieldFre!=null){
                    //����Term
                    Integer fre=fieldFre.get(field);
                    if(fre!=null){
                        //������ָ��field�ļ���
                        fre=fre+1;
                        fieldFre.put(field, fre);
                       // docFieldIndex.put(term, fieldFre);
                    }
                    else{
                        //������ָ��field�ļ���
                        fieldFre.put(field, new Integer(1));                      
                    }
                    
                }
                else{
                    fieldFre=new HashMap<Integer,Integer>();
                    fieldFre.put(field, new Integer(1));
                    docFieldIndex.put(term, fieldFre);
                }
//                if(docIndex.containsKey(term)){
//                    
//                    int fre=docIndex.get(term);
//                    fre=fre+1;
//                    docIndex.put(term, fre);
//                }
//                else{
//                    docIndex.put(term, 1);                  
//                }
            }
            
            //��һ��doc��term list���浽index��
           // Iterator<Map.Entry<String, Integer>> it=docIndex.entrySet().iterator();
            Iterator<Map.Entry<String, HashMap<Integer,Integer>>> it=docFieldIndex.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, HashMap<Integer,Integer>> entry=it.next();
                String term=entry.getKey();
                HashMap<Integer,Integer> fieldFreMap=entry.getValue();
                Iterator<Map.Entry<Integer, Integer>> it2=fieldFreMap.entrySet().iterator();
                while(it2.hasNext()){
                //int frequency=entry.getValue();
                //��doc��frequency����
                    Map.Entry<Integer, Integer> entry2=it2.next();
                    int f=entry2.getKey();
                    int occ=entry2.getValue();
                                
                DocFrequency docFre=new DocFrequency();
                docFre.setBugId(docId);
                docFre.put(f, occ);
                
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

            }
            //һ���ĵ��������
           // logger.info("�ĵ���bugID="+docId+" ������������====");
        }
        //������������HashMapת��ΪTermDocs����
        Iterator<Map.Entry<String, List<DocFrequency>>> it3=indexConst.entrySet().iterator();
        while(it3.hasNext()){
            Map.Entry<String, List<DocFrequency>> entry3=it3.next();
            String token=entry3.getKey();
            List<DocFrequency> docs=entry3.getValue();
            //һ�������������
            TermDocs termDocs=new TermDocs(token);
            termDocs.setDocs(docs);
            
            termDocsIndex.add(termDocs);
        }
        
        return termDocsIndex;
    }

    @Override
    public TermEnum generateIndex(List<DocTerms> docTermList) {
        // ���ذ���field�ĵ�������
        List<TermDocs> termDocsIndex=generateIndexList(docTermList);
        
        TermEnum termEnum=new TermEnum();
        termEnum.setTermDocList(termDocsIndex);
        
        return termEnum;
    }

}
