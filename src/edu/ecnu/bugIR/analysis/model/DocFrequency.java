package edu.ecnu.bugIR.analysis.model;

/** 
 * @description: 文档中出现的次数
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public class DocFrequency {
    /**
     * bug文档编号
     */
    private int bugId;
    
    /**
     * 次数
     */
    private int frequency;

    public int getBugId() {
        return bugId;
    }

    public void setBugId(int bugId) {
        this.bugId = bugId;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
