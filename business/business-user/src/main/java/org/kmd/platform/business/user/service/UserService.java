package org.kmd.platform.business.user.service;

import org.kmd.platform.business.user.entity.CurrentUser;
import org.kmd.platform.business.user.util.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.kmd.platform.business.user.entity.User;
import org.kmd.platform.business.user.mapper.UserMapper;


import javax.servlet.http.HttpServletRequest;
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
    public User getUserByNameAndPassword(String name,String password){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("password",password);
        return userMapper.getUserByNameAndPassword(map);
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

    public long getCurrentUserId(HttpServletRequest request){
        if (request.getSession().getAttribute("userId")==null){
            return 0;
        }else{
            return Integer.parseInt(request.getSession().getAttribute("userId").toString());
        }
    }
    public String getCurrentUserName(HttpServletRequest request){
        if (request.getSession().getAttribute("userName")==null){
            return null;
        }else{
            return request.getSession().getAttribute("userName").toString();
        }
    }
    public String getCurrentAgentName(HttpServletRequest request){
        if (request.getSession().getAttribute("agentName")==null){
            return null;
        }else{
            return request.getSession().getAttribute("agentName").toString();
        }
    }
    public long getCurrentAgentId(HttpServletRequest request){
        if (request.getSession().getAttribute("agentId")==null){
            return 0;
        }else{
            return Integer.parseInt(request.getSession().getAttribute("agentId").toString());
        }
    }
}
