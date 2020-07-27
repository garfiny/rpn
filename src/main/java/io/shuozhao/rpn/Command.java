package io.shuozhao.rpn;

import java.util.Optional;

public enum Command {
    CLEAR("clear", new CommandHandler.ClearHandler()),
    UNDO("undo", new CommandHandler.UndoHandler());

    private String text;
    private CommandHandler handler;

    Command(String text, CommandHandler handler) {
        this.text = text;
        this.handler = handler;
    }

    public static Optional<Command> parse(String op) {
        if (CLEAR.text.equals(op)) {
            return Optional.of(CLEAR);
        } else if(UNDO.text.equals(op)) {
            return Optional.of(UNDO);
        } else {
            return Optional.empty();
        }
    }

    public String getText() {
        return text;
    }

    public CommandHandler getHandler() {
        return handler;
    }

}
