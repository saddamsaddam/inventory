package com.os.inventory.common;

import java.util.Map;

public interface ActionInterface {
    Map executePreCondition(Map parameters);
    Map execute(Map previousResult);
    Map executePostCondition(Map previousResult);
    Map buildSuccessResult(Map executeResult);
    Map buildFailureResult(Map executeResult);
}
