package com.jmlee.community.elasticsearch;

import com.jmlee.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @Description 帖子相关
 * @Author jmlee
 * @Date 2021/4/29 16:53
 * @Version 1.0
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {


}
