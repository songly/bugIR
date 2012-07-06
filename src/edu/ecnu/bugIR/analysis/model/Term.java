package edu.ecnu.bugIR.analysis.model;

import java.io.Serializable;

/** 
 * @description:抽象出的Term，包含部分属性 
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public class Term  implements Serializable{

    private static final long serialVersionUID = -129895124087982321L;

    public Term(String term) {
        super();
        this.term = term;
    }

    /**
     * 可扩展的term
     */
    private String term;
    
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }


}
