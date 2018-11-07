package com.swapping.springcloud.ms.core.response;


import com.swapping.springcloud.ms.core.exception.MyException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 统一返回体
 *
 * 附加功能: 入参字段非空检查
 * @param <T>
 */
@Getter
@Setter
public class UniVerResponse<T> implements Serializable {

    private static final long serialVersionUID = 137671534756697880L;

    /**
     * 正确
     */
    public static final int SUCCESS_REQUEST = 200;
    /**
     * 参数错误返回码
     */
    public static final int ERROR_PARAMS = 100001;

    /**
     * 业务错误返回码
     */
    public static final int ERROR_BUSINESS = 200001;
    /**
     * 系统异常返回码
     */
    public static final int ERROR_SYS_EXCPTION = 500001;

    private boolean success;

    private String msg;

    private int code;

    private T obj;

    public  void beTrue(T obj){
        this.success = true;
        this.msg = "successful";
        this.code = SUCCESS_REQUEST;
        this.obj = obj;
    }



    public void  beFalse(String msg,int code){
        throw new MyException(msg,code,obj);
    }

    public void beFalse2(String msg,int code,T obj){
        this.obj = obj;
        beFalse(msg,code);
    }

    /**
     * 对象多字段判空检查
     * 例如simplCheckField(user,"userId","userName")
     * @param obj           被检查的对象
     * @param propertys     被检查对象中的字段 可多个
     * @throws MyException
     */
    public static void checkField(Object obj,String...propertys) throws MyException {

        if(obj != null && propertys != null && propertys.length > 0){
            //字节码
            Class<? extends Object> clazz = obj.getClass();

            //遍历所有属性
            for (int i = 0; i < propertys.length; i++) {
                String property = propertys[i];
                //内省机制获取属性信息
                PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz,property );
                if(pd != null){
                    //获取当前字段的javabean读方法
                    Method readMethod = pd.getReadMethod();
                    if(readMethod != null){

                        Object invoke = null;

                        try {
                            invoke = readMethod.invoke(obj);
                        } catch (Exception e) {
                            throw new MyException("方法 "+ readMethod.getName() +"无法执行",UniVerResponse.ERROR_SYS_EXCPTION);
                        }

                        if(invoke != null){
                            //String类型单独处理
                            Class<?> propertyType = pd.getPropertyType();
                            if("java.lang.String".equals(propertyType.getName())){

                                if(StringUtils.isBlank((String)invoke)){
                                    throw new MyException("错误 : [ " + property + " ] 不能为空!",UniVerResponse.ERROR_PARAMS);
                                }

                            }else if("java.util.List".equals(propertyType.getName())){
                                List list = (List)invoke;
                                if(list.size() == 0){
                                    throw new MyException("错误 : [ " + property + " ] 不能为空!",UniVerResponse.ERROR_PARAMS);
                                }
                            }
                        }else{
                            throw new MyException("错误 : [ " + property + " ] 不能为空!",UniVerResponse.ERROR_PARAMS);
                        }

                    }else{
                        //抛出异常
                        throw new MyException("在 " + clazz +"中 找不到"+"[ " + property + " ] 的 读方法",UniVerResponse.ERROR_SYS_EXCPTION);
                    }

                }else{
                    //抛出异常
                    throw new MyException("在 " + clazz +"中 找不到"+"[ " + property + " ] 属性",UniVerResponse.ERROR_SYS_EXCPTION);
                }
            }
        }
    }

    /**
     * 单一字段判空检查
     * 可检查对象的单个属性判空 例如simplCheckField(user,"userId")
     * 也可做某个变量的单独判空 例如simplCheckField(userId,"userId")
     * @param obj       被检查的对象
     * @param property  被检查的对象的字段
     * @throws MyException
     */
    public static void simplCheckField(Object obj,String property) throws MyException{

        if(obj instanceof String){
            if(StringUtils.isBlank((String)obj)){
                throw new MyException("错误 : [ " + property + " ] 不能为空!",UniVerResponse.ERROR_PARAMS);
            }
        }else if(obj instanceof List){
            List list = (List)obj;
            if(list.size() == 0){
                throw new MyException("错误 : [ " + property + " ] 不能为空!",UniVerResponse.ERROR_PARAMS);
            }
        }else{
            if(obj == null){
                throw new MyException("错误 : [ " + property + " ] 不能为空!",UniVerResponse.ERROR_PARAMS);
            }
        }
    }

}
