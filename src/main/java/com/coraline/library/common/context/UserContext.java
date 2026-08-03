package com.coraline.library.common.context;


public class UserContext {


    private static final ThreadLocal<Long> USER_ID =
            new ThreadLocal<>();


    private static final ThreadLocal<String> ROLE =
            new ThreadLocal<>();



    /**
     * 保存用户id
     */
    public static void setUserId(Long userId){

        USER_ID.set(userId);

    }



    /**
     * 获取用户id
     */
    public static Long getUserId(){

        return USER_ID.get();

    }



    /**
     * 保存角色
     */
    public static void setRole(String role){

        ROLE.set(role);

    }



    /**
     * 获取角色
     */
    public static String getRole(){

        return ROLE.get();

    }



    /**
     * 清除
     */
    public static void clear(){

        USER_ID.remove();

        ROLE.remove();

    }

}
