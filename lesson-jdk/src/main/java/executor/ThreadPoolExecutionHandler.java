package executor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author
 */
public class ThreadPoolExecutionHandler implements RejectedExecutionHandler {
    /**
     * 吃掉可能发生的异常
     * 避免拒绝策略异常，导致外部调用抛出异常，
     * 目前是用在降级场景，非降级场景勿用
     *
     * @param r
     * @param executor
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (null == r || null == executor) {
            return;
        }

        try {
            executor.submit(r);
            System.out.println("ThreadPoolExecutionHandler#rejectedExecution");

        } catch (Exception ignored) {}
    }
}
