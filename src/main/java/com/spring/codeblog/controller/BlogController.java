package com.spring.codeblog.controller;

import com.spring.codeblog.model.Post;
import com.spring.codeblog.service.CodeBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    CodeBlogService codeBlogService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView getDefault(){
        ModelAndView mv = new ModelAndView("posts");
        List<Post> post = codeBlogService.findAll();
        mv.addObject("posts",post);
        return mv;
    }

    @RequestMapping(value = "/posts",method = RequestMethod.GET)
    public ModelAndView getPosts(){
        ModelAndView mv = new ModelAndView("posts");
        List<Post> post = codeBlogService.findAll();
        mv.addObject("posts",post);
        return mv;
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ModelAndView getPostDetail(@PathVariable("id") long id){
        ModelAndView mv = new ModelAndView("postDetail");
        Post post = codeBlogService.findById(id);
        mv.addObject("post",post);
        return mv;
    }

    @RequestMapping(value = "/newPost",method = RequestMethod.GET)
    public String getPostForm(){
        return "postForm";
    }

    @RequestMapping(value = "/newPost", method = RequestMethod.POST)
    public String postSavePost(@Valid Post post, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("mensagemErro","Verifique se todos os campos foram preenchidos!");
            return "redirect:/newPost";
        }

        post.setData(LocalDate.now());

        codeBlogService.save(post);

        return "redirect:/posts";
    }
}
