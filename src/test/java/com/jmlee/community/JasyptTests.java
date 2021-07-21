package com.jmlee.community;

import com.jmlee.community.util.JasyptUtil;
import org.junit.Test;

/**
 * <ul>
 * <li>文件名称: JasyptTests</li>
 * <li>文件描述: </li>
 * <li>版权所有: 版权所有(C) 2020</li>
 * <li>公   司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要: </li>
 * <li>其他说明: </li>
 * <li>创建日期: 2021年07月07日 15时43分</li>
 * <li>编辑工具: IntelliJ IDEA</li>
 * </ul>
 * <ul>
 * <li>修改记录: </li>
 * <li>版 本 号: </li>
 * <li>修改日期: </li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author jmlee
 * @version 1.0.0
 */
public class JasyptTests {

    @Test
    public void encode() {
        //NQgRb22ZcwLfvSZFJNuT1A==
        System.out.println(JasyptUtil.encode("123456"));
        System.out.println(JasyptUtil.decode("/ND6+2HL7chivf0BuFLQUw=="));
        System.out.println(JasyptUtil.encode("root"));

        System.out.println(JasyptUtil.encode("2607687789@qq.com"));
        System.out.println(JasyptUtil.encode("vjzleujgzfhmeceg"));

    }
}
