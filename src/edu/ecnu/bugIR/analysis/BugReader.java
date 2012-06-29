package edu.ecnu.bugIR.analysis;

import java.util.List;

import edu.ecnu.bugIR.analysis.model.BugReport;

/** 
 * @description: 获取bug数据到内存中的接口
 * @author: "Song Leyi"  2012-6-29
 * @version: 1.0 
 * @modify: 
 */
public interface BugReader {
    
    /**
     * 读取meego bugzilla数据
     * @return
     */
    public List<BugReport> getMeegoBugs();

}
