package edu.ecnu.bugIR.analysis;

import java.util.List;

import edu.ecnu.bugIR.analysis.model.DocTerms;
import edu.ecnu.bugIR.analysis.model.TermDocs;
import edu.ecnu.bugIR.analysis.model.TermEnum;


public class InvertedIndexImpl implements InvertedIndexInterface {

    @Override
    public void SortTerms(DocTerms termList) {
        // TODO �Զ����ɷ������

    }

    @Override
    public void SortTerms(List<String> termList) {
        // TODO �Զ����ɷ������

    }

    @Override
    public List<TermDocs> generateIndex(List<DocTerms> docTermList) {
        // TODO �Զ����ɷ������
        return null;
    }

    @Override
    public TermEnum generateIndex(List<DocTerms> docTermList, int field) {
        // TODO �Զ����ɷ������
        return null;
    }

}
