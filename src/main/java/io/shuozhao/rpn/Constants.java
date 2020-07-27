package io.shuozhao.rpn;

import java.math.MathContext;
import java.math.RoundingMode;

public interface Constants {

    int CALULATION_DECIMAL_PLACES = 15;
    int DISPLAY_DECIMAL_PLACES = 10;
    RoundingMode ROUNDING_MODE = RoundingMode.DOWN;

    String INSUFFICIENT_PARAM_MSG_FMT = "operator %s (position: %s): insufficient parameters";
    String INVALID_PARAM_MSG_FMT = "parameter %s (position: %s): invalid parameters";
}
