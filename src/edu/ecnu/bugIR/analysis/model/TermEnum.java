package edu.ecnu.bugIR.analysis.model;

import java.util.List;

/** 
 * @description: 一个termDocs列表，保存了指定域的倒排索引
 * @author: "Song Leyi"  2012-7-3
 * @version: 1.0 
 * @modify: 
 */
public class TermEnum {
    /**
     * Term的文档-次数列表
     */
    private List<TermDocs> termDocList;
    /**
     * 域编号
     */
    private int field;

}
