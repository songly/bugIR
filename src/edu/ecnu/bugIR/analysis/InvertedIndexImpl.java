package edu.ecnu.bugIR.analysis;

import java.util.List;

import edu.ecnu.bugIR.analysis.model.DocTerms;
import edu.ecnu.bugIR.analysis.model.TermDocs;
import edu.ecnu.bugIR.analysis.model.TermEnum;


public class InvertedIndexImpl implements InvertedIndexInterface {

    @Override
    public void SortTerms(DocTerms termList) {
        // TODO 自动生成方法存根

    }

    @Override
    public void SortTerms(List<String> termList) {
        // TODO 自动生成方法存根

    }

    @Override
    public List<TermDocs> generateIndex(List<DocTerms> docTermList) {
        // TODO 自动生成方法存根
        return null;
    }

    @Override
    public TermEnum generateIndex(List<DocTerms> docTermList, int field) {
        // TODO 自动生成方法存根
        return null;
    }

}
