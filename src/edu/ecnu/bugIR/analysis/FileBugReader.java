package edu.ecnu.bugIR.analysis;

import java.util.ArrayList;
import java.util.List;

import edu.ecnu.bugIR.analysis.model.BugReport;

/** 
 * @description: ���ļ��ж�ȡBug��Ϣ
 * @author: "Song Leyi"  2012-6-29
 * @version: 1.0 
 * @modify: 
 */
public class FileBugReader implements BugReader {

    @Override
    public List<BugReport> getMeegoBugs() {
        //����
        return new ArrayList<BugReport>();
    }

}
