package com.simon.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 标题：订单状态流转校验
 *
 * 题目描述：
 * 给定某个订单的状态流转列表 statusList 和订单号 orderId，判断该订单状态流转是否合法。
 *
 * 状态列表：
 * 创建 created
 * 已支付 paid
 * 已发货 shipped
 * 已完成 completed
 * 已取消 Cancelled
 *
 * 扩展设计：
 * 1. 把订单状态流转看成一张有向图，状态是节点，合法流转是边。
 * 2. 校验流程时，只需要检查每两个相邻状态之间是否存在一条边。
 * 3. 查找全部流程时，从起始状态 created 开始深度优先搜索，直到 completed 或 Cancelled 终态。
 * 4. 后续增加新状态或新流转，只需要在 buildStatusFlowMap 方法中注册规则。
 */
public class OrderStatusFlowValidator {

    private static final String CREATED = "created";
    private static final String PAID = "paid";
    private static final String SHIPPED = "shipped";
    private static final String COMPLETED = "completed";
    private static final String CANCELLED = "Cancelled";

    private static final String START_STATUS = CREATED;

    private static final Set<String> END_STATUS_SET = new LinkedHashSet<>(Arrays.asList(COMPLETED, CANCELLED));

    private static final Map<String, Set<String>> STATUS_FLOW_MAP = buildStatusFlowMap();

    private static Map<String, Set<String>> buildStatusFlowMap() {
        // 使用 LinkedHashMap 保持规则注册顺序，方便打印全部流程时结果稳定。
        Map<String, Set<String>> statusFlowMap = new LinkedHashMap<>();

        // 先注册所有状态，避免某个状态没有后续节点时被误认为未知状态。
        addStatus(statusFlowMap, CREATED);
        addStatus(statusFlowMap, PAID);
        addStatus(statusFlowMap, SHIPPED);
        addStatus(statusFlowMap, COMPLETED);
        addStatus(statusFlowMap, CANCELLED);

        // 注册创建后的合法流转：可以支付，也可以取消。
        addStatusFlow(statusFlowMap, CREATED, PAID);
        addStatusFlow(statusFlowMap, CREATED, CANCELLED);

        // 注册已支付后的合法流转：可以发货，也可以取消。
        addStatusFlow(statusFlowMap, PAID, SHIPPED);
        addStatusFlow(statusFlowMap, PAID, CANCELLED);

        // 注册已发货后的合法流转：只能完成。
        addStatusFlow(statusFlowMap, SHIPPED, COMPLETED);

        // 返回只读规则表，避免运行时被业务代码意外修改。
        return Collections.unmodifiableMap(statusFlowMap);
    }

    private static void addStatus(Map<String, Set<String>> statusFlowMap, String status) {
        // 如果状态已经存在，不覆盖原来的后续流转集合。
        if (!statusFlowMap.containsKey(status)) {
            statusFlowMap.put(status, new LinkedHashSet<String>());
        }
    }

    private static void addStatusFlow(Map<String, Set<String>> statusFlowMap, String fromStatus, String toStatus) {
        // 注册流转前，确保起始状态和目标状态都已经存在。
        addStatus(statusFlowMap, fromStatus);
        addStatus(statusFlowMap, toStatus);

        // 在有向图中加入一条 fromStatus -> toStatus 的边。
        statusFlowMap.get(fromStatus).add(toStatus);
    }

    public boolean isValidStatusFlow(String[] statusList, String orderId) {
        // 订单号为空时，无法确认要校验哪个订单。
        if (orderId == null || orderId.length() == 0) {
            return false;
        }

        // 状态列表为空时，没有任何可校验的状态流转。
        if (statusList == null || statusList.length == 0) {
            return false;
        }

        // 第一个状态必须是 created，订单流转必须从创建开始。
        if (!START_STATUS.equals(statusList[0])) {
            return false;
        }

        // 第一个状态也必须是已知状态。
        if (!STATUS_FLOW_MAP.containsKey(statusList[0])) {
            return false;
        }

        // 逐个检查相邻两个状态之间的流转是否合法。
        for (int i = 1; i < statusList.length; i++) {
            // previousStatus 是上一个状态。
            String previousStatus = statusList[i - 1];

            // currentStatus 是当前要流转到的状态。
            String currentStatus = statusList[i];

            // 当前状态必须是已知状态。
            if (!STATUS_FLOW_MAP.containsKey(currentStatus)) {
                return false;
            }

            // 上一个状态必须存在，并且必须允许流转到当前状态。
            if (!canTransfer(previousStatus, currentStatus)) {
                return false;
            }
        }

        // 所有相邻状态都符合规则，说明整条状态流转合法。
        return true;
    }

    public List<List<String>> findAllStatusFlows() {
        // allFlows 用来保存从 created 到终态的所有完整流程。
        List<List<String>> allFlows = new ArrayList<>();

        // currentFlow 保存当前正在搜索的一条流程。
        List<String> currentFlow = new ArrayList<>();

        // visitedStatusSet 防止规则配置成环后导致递归死循环。
        Set<String> visitedStatusSet = new LinkedHashSet<>();

        // 从 created 开始搜索全部合法流程。
        findAllStatusFlows(START_STATUS, currentFlow, visitedStatusSet, allFlows);

        // 返回搜索到的全部完整流程。
        return allFlows;
    }

    private void findAllStatusFlows(String currentStatus,
                                    List<String> currentFlow,
                                    Set<String> visitedStatusSet,
                                    List<List<String>> allFlows) {
        // 将当前状态加入正在搜索的流程。
        currentFlow.add(currentStatus);

        // 标记当前状态已经访问过，避免当前路径里重复进入同一个状态。
        visitedStatusSet.add(currentStatus);

        // 如果当前状态是终态，说明找到了一条完整流程。
        if (END_STATUS_SET.contains(currentStatus)) {
            allFlows.add(new ArrayList<>(currentFlow));
        } else {
            // 取出当前状态可以流转到的所有下一个状态。
            Set<String> nextStatusSet = STATUS_FLOW_MAP.get(currentStatus);

            // 逐个尝试下一个状态，继续深度优先搜索。
            for (String nextStatus : nextStatusSet) {
                // 当前路径已经访问过的状态不再进入，避免环形规则导致无限递归。
                if (!visitedStatusSet.contains(nextStatus)) {
                    findAllStatusFlows(nextStatus, currentFlow, visitedStatusSet, allFlows);
                }
            }
        }

        // 回溯：移除当前状态，让上一层继续尝试其他分支。
        currentFlow.remove(currentFlow.size() - 1);

        // 回溯：取消当前状态的访问标记。
        visitedStatusSet.remove(currentStatus);
    }

    private boolean canTransfer(String fromStatus, String toStatus) {
        // 根据起始状态取出所有允许到达的下一个状态。
        Set<String> nextStatusSet = STATUS_FLOW_MAP.get(fromStatus);

        // 如果起始状态未知，或者目标状态不在允许集合内，表示不能流转。
        return nextStatusSet != null && nextStatusSet.contains(toStatus);
    }

    public static void main(String[] args) {
        OrderStatusFlowValidator validator = new OrderStatusFlowValidator();

        // 示例 1：完整正常流程，合法。
        System.out.println(validator.isValidStatusFlow(new String[]{CREATED, PAID, SHIPPED, COMPLETED}, "order001"));

        // 示例 2：创建后直接取消，合法。
        System.out.println(validator.isValidStatusFlow(new String[]{CREATED, CANCELLED}, "order002"));

        // 示例 3：创建后直接发货，非法。
        System.out.println(validator.isValidStatusFlow(new String[]{CREATED, SHIPPED}, "order003"));

        // 打印当前规则下的全部完整流程。
        System.out.println(validator.findAllStatusFlows());
    }
}
