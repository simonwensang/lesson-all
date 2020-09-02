package dynamicProxy;

/**
 * Created by sang on 2018/1/10.
 */
public class TestHandler {

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
