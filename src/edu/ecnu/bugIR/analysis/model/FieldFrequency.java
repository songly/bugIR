package edu.ecnu.bugIR.analysis.model;

import java.io.Serializable;

/** 
 * @description: Term's occurrencr in specified field
 * @author: "Song Leyi"  2012-7-6
 * @version: 1.0 
 * @modify: 
 */
public class FieldFrequency implements Serializable {
    
    /**
     */
    private static final long serialVersionUID = 2765399180077081161L;

    /**
     * 文档中相应的Field
     */
    private int field;
    
    /**
     * Term出现的次数
     */
    private int frequency;

    public FieldFrequency(int field, int frequency) {
        super();
        this.field = field;
        this.frequency = frequency;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
