package com.example.blog.service;

import com.example.blog.MyException.NotFoundException;
import com.example.blog.bean.Type;
import com.example.blog.dao.typeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Emma
 * @create 2020 - 05 - 23 - 19:19
 */
@Service
public class typeServiceImpl implements typeService {

    @Autowired
    private typeRepository typeRepository;

    @Transactional  //放在事务里面
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Integer id) {
        //没有findone，本来想用getone，但是getone是懒加载；所以用findById。
        //findById(id).orElse(null)，这里表示，如果id存在则返回需要查找的信息，如果不存在，这里设置为返回null（推荐）
        return typeRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer index) {
        Pageable pageable = PageRequest.of(0,index,Sort.by(Sort.Direction.DESC, "blogs.size"));
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Integer id, Type type) {
        Type one = typeRepository.findById(id).orElse(null);
        if(one == null){
            //没有查询出来
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,one);//将type的内容复制到one中

        return typeRepository.save(one);
    }

    @Transactional
    @Override
    public void deleteType(Integer id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeBytypeName(String typeName) {
        return typeRepository.findByTypeName(typeName);
    }
}
