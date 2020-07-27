package io.shuozhao.rpn;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.shuozhao.rpn.Constants.CALULATION_DECIMAL_PLACES;
import static io.shuozhao.rpn.Constants.ROUNDING_MODE;

public interface OperatorHandler {

    BigDecimal handle(BigDecimal op1, BigDecimal op2);

    class PlusOperatorHandler implements OperatorHandler {
        @Override
        public BigDecimal handle(BigDecimal op1, BigDecimal op2) {
            return op1
                    .add(op2)
                    .setScale(CALULATION_DECIMAL_PLACES, ROUNDING_MODE);
        }
    }

    class MinusOperatorHandler implements OperatorHandler {
        @Override
        public BigDecimal handle(BigDecimal leftOp, BigDecimal rightOp) {
            return leftOp
                    .add(rightOp.negate())
                    .setScale(CALULATION_DECIMAL_PLACES, ROUNDING_MODE);
        }
    }

    class MultiplyOperatorHandler implements OperatorHandler {
        @Override
        public BigDecimal handle(BigDecimal op1, BigDecimal op2) {
            return op1
                    .multiply(op2)
                    .setScale(CALULATION_DECIMAL_PLACES, ROUNDING_MODE);
        }
    }

    class DivisionOperatorHandler implements OperatorHandler {
        @Override
        public BigDecimal handle(BigDecimal leftOp, BigDecimal rightOp) {
            return leftOp
                    .divide(rightOp)
                    .setScale(CALULATION_DECIMAL_PLACES, ROUNDING_MODE);
        }
    }

    class SqrtOperatorHandler implements OperatorHandler {
        @Override
        public BigDecimal handle(BigDecimal leftOp, BigDecimal rightOp) {
            return leftOp
                    .sqrt(MathContext.DECIMAL128)
                    .setScale(CALULATION_DECIMAL_PLACES, ROUNDING_MODE);
        }
    }
}

