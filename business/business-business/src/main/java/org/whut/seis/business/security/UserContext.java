package org.whut.seis.business.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created with IntelliJ IDEA.
 * User: xiaozhujun
 * Date: 14-5-25
 * Time: 上午12:49
 * To change this template use File | Settings | File Templates.
 */
public class UserContext {
    public static MyUserDetail  currentUser() throws Exception {
        Object credential = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if(credential instanceof UserDetails){
            return (MyUserDetail)credential;
        }else{
            throw new Exception("用户初始化异常");
        }
    }

    public static String  currentUserName() throws Exception {
        MyUserDetail myUserDetail = currentUser();
        if(myUserDetail!=null){
            return myUserDetail.getUserName();
        }
        return null;
    }

    public static Long  currentUserId() throws Exception {
        MyUserDetail myUserDetail = currentUser();
        if(myUserDetail!=null){
            return myUserDetail.getId();
        }
        return null;
    }

    public static Long  currentUserAppId() throws Exception {
        MyUserDetail myUserDetail = currentUser();
        if(myUserDetail!=null){
            return myUserDetail.getAppId();
        }
        return null;
    }
}
