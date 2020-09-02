package  dynamicProxy;

import  annotation.RuntimeAnnotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by sang on 2018/1/10.
 */
public class RunTimeAnnotationProxyHandler implements InvocationHandler {

    private Object proxyObj;

    private RunTimeAnnotationProxyHandler(Object proxyObj) {
        this.proxyObj = proxyObj;
    }

    public static Object newInstance(Object object){
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),object.getClass().getInterfaces(),new RunTimeAnnotationProxyHandler(object) );
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null ;

        if(method.isAnnotationPresent(RuntimeAnnotation.class)){
            RuntimeAnnotation RuntimeAnnotation = method.getAnnotation(RuntimeAnnotation.class);
            System.out.println(RuntimeAnnotation.info()+";name="+RuntimeAnnotation.name() + ";value=" + RuntimeAnnotation.value());
            Long start =  System.currentTimeMillis();
            result = method.invoke(proxyObj,args);
            Long end =  System.currentTimeMillis();
            System.out.println("Totally used time: " + (end-start));
            return result;
        }
        return  method.invoke(proxyObj,args);

    }
}
