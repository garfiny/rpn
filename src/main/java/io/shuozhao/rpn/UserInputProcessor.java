package io.shuozhao.rpn;

import static io.shuozhao.rpn.Constants.INSUFFICIENT_PARAM_MSG_FMT;
import static io.shuozhao.rpn.Constants.INVALID_PARAM_MSG_FMT;

public class UserInputProcessor {

    private Calculator calculator;

    public UserInputProcessor(Calculator calculator) {
        this.calculator = calculator;
    }

    public ProcessResult process(String input) {
        String[] params = input.split(" ");
        ProcessResult result = new ProcessResult();
        int currentPosition = 1;
        for (String p : params) {
            if (p.isBlank()) { // handle multiple spaces between input
                currentPosition += 1;
                continue;
            }
            try {
                Item item = Item.parseParameter(p);
                if (item.isOperand()) {
                    calculator.addOperand(item);
                } else if (item.isOperator()) {
                    calculator.doCalculate(item);
                } else if (item.isCommand()) {
                    calculator.executeCommand(item);
                }
            } catch (InvalidParameterException ux) {
                result.setError(String.format(INVALID_PARAM_MSG_FMT, p, currentPosition));
                break;
            } catch (InsufficientParameterException ix) {
                result.setError(String.format(INSUFFICIENT_PARAM_MSG_FMT, p, currentPosition));
                break;
            }
            currentPosition += p.length() + 1;
        }
        result.setResult(calculator.currentResult());
        return result;
    }
}
