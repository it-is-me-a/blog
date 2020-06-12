package com.example.blog.bean;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emma
 * @create 2020 - 05 - 22 - 19:39
 */
@Entity
@Table(name="t_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message="标签名称不能为空")  //后天检测message不为空，如果@NotBlank标红，就手动导入组件
    private String tagName;

    @ManyToMany(mappedBy ="tags") //被维护关系;fetch=FetchType.EAGER将懒加载改为false
    private List<Blog> blogs = new ArrayList<>();


    public Tag() {
    }

    public Tag(@NotBlank(message = "标签名称不能为空") String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
