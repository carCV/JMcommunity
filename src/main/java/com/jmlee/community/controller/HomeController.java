package com.jmlee.community.controller;

import com.jmlee.community.entity.DiscussPost;
import com.jmlee.community.entity.Page;
import com.jmlee.community.entity.User;
import com.jmlee.community.service.DiscussPostService;
import com.jmlee.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        // controller方法调用前，SpringMVC会自动帮我们实例化Model和Page对象，并将Page注入给Model
        // 所以在 thymeleaf中可以直接访问Page对象中的数据
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPost(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();

        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post",post);

                User user = userService.findUserById(post.getUserId());
                map.put("user",user);

                discussPosts.add(map);
            }
        }

        model.addAttribute("discussPosts",discussPosts);

        // 当引入thymeleaf的jar包时，默认的静态根目录变成了templates
        return "/index";
    }
}
