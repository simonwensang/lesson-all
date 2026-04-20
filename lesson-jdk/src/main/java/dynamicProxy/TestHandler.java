package dynamicProxy;

/**
 * Created by sang on 2018/1/10.
 */
public class TestHandler {

    //-verbose:class 加载类 详情命令
    public static void main(String[] args) {

        IHandler handler =  new MyHandler();

        IHandler handlerPoxy =  (IHandler) RunTimeAnnotationProxyHandler.newInstance(handler);
        Long start =  System.currentTimeMillis();
        handler.handlerData();
        Long end =  System.currentTimeMillis();
        System.out.println("Totally used time: " + (end-start));

        Long startProxy =  System.currentTimeMillis();
        handlerPoxy.handlerData();
        Long endProxy  =  System.currentTimeMillis();
        System.out.println("Proxy  Totally used time: " + (endProxy -startProxy ));


    }
}
