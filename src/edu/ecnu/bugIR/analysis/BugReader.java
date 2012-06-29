package edu.ecnu.bugIR.analysis;

import java.util.List;

import edu.ecnu.bugIR.analysis.model.BugReport;

/** 
 * @description: ��ȡbug���ݵ��ڴ��еĽӿ�
 * @author: "Song Leyi"  2012-6-29
 * @version: 1.0 
 * @modify: 
 */
public interface BugReader {
    
    /**
     * ��ȡmeego bugzilla����
     * @return
     */
    public List<BugReport> getMeegoBugs();

}
