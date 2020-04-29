package com.jmlee.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词过滤工具类（前缀树数据结构）
 */
@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    // 敏感词默认替换符：***
    private static final String DEFAULT_REPLACEMENT = "***";

    // 初始化前缀树-根节点
    private TrieNode rootNode = new TrieNode();

    // 实例构造完后初始化敏感词前缀树
    @PostConstruct
    public void init() {
        try (
                // 从类路径下获取文件
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                // 添加到前缀树
                this.addKeyword(keyword);
            }

        } catch (IOException e) {
            logger.error("加载敏感词文件失败：" + e.getMessage());
        }
    }


    // 将一个敏感词添加到前缀树中
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;

        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null) {
                // 初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }

            // 指向子节点，进入下一轮循环
            tempNode = subNode;

            // 设置结束标识
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }


    /**
     * 过滤敏感词核心算法
     *
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        // 指针1
        TrieNode tempNode = rootNode;

        // 指针2
        int begin = 0;

        // 指针3
        int position = 0;

        // 输出结果(变长字符串)
        StringBuilder sb = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            // 跳过特殊符号
            if (isSymbol(c)) {
                // 若指针1处于根节点，将此符号计入结果，让指针2向下走一步
                if (tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                // 无论符号在开头或中间，指针3都向下走一步
                position++;
                continue;
            }

            // 检查下级节点
            tempNode = tempNode.getSubNode(c);

            if (tempNode == null) {
                // 以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                position = ++begin;
                // 重新指向根节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                // 发现敏感词，将begin~position字符串替换掉
                sb.append(DEFAULT_REPLACEMENT);
                // 进入下一个位置
                begin = ++position;
                tempNode = rootNode;
            } else {
                // 检查下一个字符
                position++;
            }
        }

        // 将最后一批字符计入结果
        sb.append(text.substring(begin));

        return sb.toString();
    }

    /**
     * 判断是否为特殊符号
     * @param c
     * @return true表示为特殊字符，false表示为普通字符
     */
    private boolean isSymbol(Character c) {
        // 0x2E80~0x9FFF 为东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }




    /************************************************
     * 前缀树（又名字典树，单词查找树），是一种多路树结构
     * 示例敏感词：abc，bf，be
     * 存储结构：
     *           Root
     *          a    b
     *         b    f e
     *        c
     ************************************************/
    private class TrieNode {
        // 关键词字符结束标识
        private boolean isKeywordEnd = false;

        // 子节点 (key是下级字符，value是下级节点)
        private Map<Character, TrieNode> subNodes = new HashMap<>();


        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c,node);
        }

        // 获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }





}
