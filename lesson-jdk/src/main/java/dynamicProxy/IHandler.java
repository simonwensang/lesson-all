package dynamicProxy;


import annotation.RuntimeAnnotation;

/**
 * Created by sang on 2018/1/10.
 */
public interface IHandler {

    @RuntimeAnnotation(value = "2",name="handler")
    public void handlerData();

}
