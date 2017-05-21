package org.kmd.platform.business.user.service;

import org.kmd.platform.business.user.util.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.kmd.platform.business.user.entity.User;
import org.kmd.platform.business.user.mapper.UserMapper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tuguangquan
 * Date: 14-1-26
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void add(User user){
        if(user.getPassword()!=null && !user.getPassword().equals("")){
            user.setPassword(MD5Encoder.GetMD5Code(user.getPassword()));
        }
        userMapper.add(user);
    }

    public Long getIdByName(String name,long appId){
        return userMapper.getIdByName(name,appId);
    }
    public int update(User user){
        if(user.getPassword()!=null && !user.getPassword().equals("")){
            user.setPassword(MD5Encoder.GetMD5Code(user.getPassword()));
        }
        return userMapper.update(user);
    }

    public int delete(User user){
        return userMapper.delete(user);
    }

    public List<User> list(long agentId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId",agentId);
        return userMapper.findByCondition(map);
    }
    public User findByName(String name){
        if(name==null || name.trim().equals("")){
            return null;
        }
        List<User> list = userMapper.findByName(name);
        if(list.size()>=1){
            return list.get(0);
        }
        return null;
    }

    public User getById(long id)
    {
        return userMapper.getById(id);
    }


    public void updateUserImage(User user){
        userMapper.updateUserImage(user);
    }
}
