package edu.ecnu.bugIR.analysis.model;

/** 
 * @description: �ĵ��г��ֵĴ���
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public class DocFrequency {
    /**
     * bug�ĵ����
     */
    private int bugId;
    
    /**
     * ����
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
