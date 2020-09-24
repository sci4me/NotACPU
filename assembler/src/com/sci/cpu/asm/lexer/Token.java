package com.sci.cpu.asm.lexer;

public final class Token {
    public final TokenType type;
    public final String text;
    public final int line;
    public final int column;

    public Token(final TokenType type, final String text, final int line, final int column) {
        this.type = type;
        this.text = text;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)@%d:%d", this.type.name(), this.text, this.line, this.column);
    }
}