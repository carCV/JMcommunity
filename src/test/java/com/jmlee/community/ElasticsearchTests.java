package com.jmlee.community;

import com.jmlee.community.dao.DiscussPostMapper;
import com.jmlee.community.elasticsearch.DiscussPostRepository;
import com.jmlee.community.entity.DiscussPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTests {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testInsert() {

        discussPostRepository.save(discussPostMapper.selectDiscussPostById(241));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(242));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(243));
    }


    @Test
    public void testInsertList() {
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPost(101, 0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPost(102, 0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPost(103, 0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPost(111, 0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPost(112, 0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPost(131, 0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPost(132, 0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPost(133, 0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPost(134, 0,100));
    }

    @Test
    public void testUpdate() {
        DiscussPost discussPost = discussPostMapper.selectDiscussPostById(231);
        discussPost.setContent("我是新人，使劲灌水");
        discussPostRepository.save(discussPost);
    }
}
