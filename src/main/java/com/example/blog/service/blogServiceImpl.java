package com.example.blog.service;

import com.example.blog.MyException.NotFoundException;
import com.example.blog.bean.Blog;
import com.example.blog.bean.BlogQuery;
import com.example.blog.bean.Type;
import com.example.blog.dao.blogRepository;
import com.example.blog.util.MarkdownUtils;
import com.example.blog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.Year;
import java.util.*;

/**
 * @author Emma
 * @create 2020 - 05 - 26 - 0:00
 */
@Service
public class blogServiceImpl implements blogService {

    @Autowired
    private blogRepository blogRepository;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null){
            blog.setCreatTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else{
            //不是新增
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }


    @Override
    public Blog getBlog(Integer id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Integer id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if(blog == null){
            throw new NotFoundException("博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);/*复制的原因是为了不改变原有的blog在数据库中的内容*/
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blogRepository.updateViews(id);/*增加一次浏览量*/

        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        /*因为分页查询的时候，可能会传入type，tag的信息，所以要想实现动态的组合查询
        * 首先blogRepository得继承JpaSpecificationExecutor<Blog>
        * 因为它里面有继承的动态的组合查询方法*/
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if( !"".equals(blog.getTitle()) && blog.getTitle() != null ){
                    //title有值
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() != null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    //为true 代表推荐
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }

                cq.where(predicates.toArray(new Predicate[predicates.size()]));  //数组里面是一个array
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Integer tagId, Pageable pageable) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Join join = root.join("tags");/*关联操作*/
                return cb.equal(join.get("id"),tagId);/*构建操作*/
            }
        },pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer index) {
        Pageable pageable = PageRequest.of(0,index, Sort.by(Sort.Direction.DESC,"views"));/*按照views的倒序进行排列*/
        return blogRepository.findTop(pageable);
    }

    @Override
    public List<Blog> listRecommendBlogNew(Integer index) {
        Pageable pageable = PageRequest.of(0,index, Sort.by(Sort.Direction.DESC,"creatTime"));/*按照creatTime的倒序进行排列*/
        return blogRepository.findTop(pageable);
    }

    @Override
    public List<String> blogCreateTime() {
        return blogRepository.findGroupYear();
    }

    /*查出对应日期的博客，这里的日期也是从数据库查出来的*/
    @Override
    public Map<String, List<Blog>> archivesBlog() {
        /*先查出所有的日期*/
        List<String> years = blogRepository.findGroupYear();

        Map<String,List<Blog>> map = new HashMap<>();
        for(String year : years){
            map.put(year,blogRepository.findByYear(year));/*根据日期将对应的博客查出来*/
        }
        return map;
    }

    /*查出对应日期的博客，这里的日期是自己输入的*/
    @Override
    public List<Blog> archivesBlog(String time) {
        return blogRepository.findByYear(time);
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog updateBlog(Integer id, Blog blog) {
        Blog blog1 = blogRepository.findById(id).orElse(null);
        if(blog1 == null){
            //放置一个错误信息
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));//MyBeanUtils.getNullPropertyNames(blog)过滤掉属性值为空
        blog1.setUpdateTime(new Date());
        return blogRepository.save(blog1);
    }

    @Transactional
    @Override
    public void deleteBlog(Integer id) {
        blogRepository.deleteById(id);
    }
}
