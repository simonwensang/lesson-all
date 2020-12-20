package  dynamicProxy;

/**
 * Created by sang on 2018/1/10.
 */
public class MyHandler implements  IHandler{
    @Override
    public void handlerData() {
        System.out.println("handler start!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("handler done!");
    }

}
