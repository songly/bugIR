package edu.ecnu.bugIR.analysis.model;

/** 
 * @description: BugReport封装数据结构
 * @author: "Song Leyi"  2012-6-29
 * @version: 1.0 
 * @modify: 
 */
public class BugReport {
    
    /**
     * bug编号
     */
    private int bugId;
    
    //textual features
    private String summ;   
    private String desc;
    
    //non-textual features
    private int prod;
    private int comp; 
    private String version;  
    private String prio;

    public int getBugId() {
        return bugId;
    }

    public void setBugId(int bugId) {
        this.bugId = bugId;
    }

    public String getSumm() {
        return summ;
    }

    public void setSumm(String summ) {
        this.summ = summ;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getProd() {
        return prod;
    }

    public void setProd(int prod) {
        this.prod = prod;
    }

    public int getComp() {
        return comp;
    }

    public void setComp(int comp) {
        this.comp = comp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPrio() {
        return prio;
    }

    public void setPrio(String prio) {
        this.prio = prio;
    }

    @Override
    public String toString() {
        return "BugReport [bugId=" + bugId + ", summ=" + summ + ", desc=" + desc + ", prod=" + prod + ", comp=" + comp + ", version=" + version
                + ", prio=" + prio + "]";
    }
    
    

}
