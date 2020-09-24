package com.sci.cpu.asm.lexer;

import java.util.ArrayList;
import java.util.List;

public final class Lexer {
    private static boolean isDecimalDigit(final char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isBinaryDigit(final char c) {
        return c == '0' || c == '1';
    }

    private static boolean isHexDigit(final char c) {
        return Lexer.isDecimalDigit(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    private static boolean isLetter(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private static boolean isAlpha(final char c) {
        return Lexer.isDecimalDigit(c) || Lexer.isLetter(c);
    }

    private static boolean isIdent(final char c) {
        return Lexer.isAlpha(c) || c == '_';
    }

    private static boolean isWhitespace(final char c) {
        return c == ' ' || c == '\t' || c == '\r';
    }

    private final String code;
    private int start;
    private int pos;
    private int line;
    private int column;
    private List<Token> tokens;

    public Lexer(final String code) {
        this.code = code;
        this.tokens = new ArrayList<>();
    }

    private String current() {
        return this.code.substring(this.start, this.pos);
    }

    private void emit(final TokenType type) {
        this.tokens.add(new Token(type, this.current(), this.line + 1, this.column - (this.pos - this.start) + 1));
        this.start = this.pos;
    }

    private char next() {
        if (!this.more()) return 0;
        final char c = this.code.charAt(this.pos);
        this.pos++;
        this.column++;
        return c;
    }

    private void prev() {
        this.pos--;
        this.column--;
    }

    private char peek() {
        if (!this.more()) return 0;
        return this.code.charAt(this.pos);
    }

    private boolean more() {
        return this.pos < this.code.length();
    }

    private void ignore() {
        this.start = this.pos;
    }

    private boolean accept(final String valid) {
        if (!this.more()) {
            return false;
        }

        if (valid.contains(String.valueOf(this.next()))) {
            return true;
        }
        this.prev();
        return false;
    }

    private void acceptRun(final String valid) {
        while (this.more() && valid.contains(String.valueOf(this.peek()))) {
            this.next();
        }
    }

    private boolean acceptSeq(final String seq) {
        final int savedPos = this.pos;
        final int savedColumn = this.column;

        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) != this.next()) {
                this.pos = savedPos;
                this.column = savedColumn;
                return false;
            }
        }

        return true;
    }

    public List<Token> tokenize() {
        boolean running = true;
        while (running) {
            final char c = this.next();
            switch (c) {
                case 0:
                    running = false;
                    break;
                case '\n':
                    this.ignore();
                    this.line++;
                    this.column = 0;
                    break;
                case ';':
                    while (this.more() && this.peek() != '\n') this.next();
                    this.ignore();
                    break;
                case ',':
                    this.emit(TokenType.COMMA);
                    break;
                case '.':
                    while (this.more() && Lexer.isIdent(this.peek())) this.next();

                    final String directive = this.current().substring(1);
                    switch (directive) {
                        case "db":
                            this.emit(TokenType.DIRECTIVE_DB);
                            break;
                        case "dw":
                            this.emit(TokenType.DIRECTIVE_DW);
                            break;
                        case "resb":
                            this.emit(TokenType.DIRECTIVE_RESB);
                            break;
                        case "resw":
                            this.emit(TokenType.DIRECTIVE_RESW);
                            break;
                        case "org":
                            this.emit(TokenType.DIRECTIVE_ORG);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized directive: ." + directive);
                    }
                    break;
                default:
                    if (Lexer.isLetter(c) || c == '_') {
                        while (this.more() && Lexer.isIdent(this.peek())) this.next();

                        final String ident = this.current();
                        if (this.peek() == ':') {
                            if (ident.equals("a") || ident.equals("b") || ident.equals("c") || ident.equals("d") || ident.equals("i") || ident.equals("il") || ident.equals("ih") || ident.equals("pc")) {
                                throw new RuntimeException("Cannot create label with register name " + ident);
                            }

                            this.next();
                            this.emit(TokenType.LABEL);
                        } else {
                            switch (ident) {
                                case "nop":
                                    this.emit(TokenType.NOP);
                                    break;
                                case "add":
                                    this.emit(TokenType.ADD);
                                    break;
                                case "adc":
                                    this.emit(TokenType.ADC);
                                    break;
                                case "sub":
                                    this.emit(TokenType.SUB);
                                    break;
                                case "sbc":
                                    this.emit(TokenType.SBC);
                                    break;
                                case "and":
                                    this.emit(TokenType.AND);
                                    break;
                                case "or":
                                    this.emit(TokenType.OR);
                                    break;
                                case "xor":
                                    this.emit(TokenType.XOR);
                                    break;
                                case "not":
                                    this.emit(TokenType.NOT);
                                    break;
                                case "lsh":
                                    this.emit(TokenType.LSH);
                                    break;
                                case "rsh":
                                    this.emit(TokenType.RSH);
                                    break;
                                case "mov":
                                    this.emit(TokenType.MOV);
                                    break;
                                case "ld":
                                    this.emit(TokenType.LD);
                                    break;
                                case "st":
                                    this.emit(TokenType.ST);
                                    break;
                                case "jz":
                                    this.emit(TokenType.JZ);
                                    break;
                                case "jc":
                                    this.emit(TokenType.JC);
                                    break;
                                case "jmp":
                                    this.emit(TokenType.JMP);
                                    break;
                                case "sez":
                                    this.emit(TokenType.SEZ);
                                    break;
                                case "sec":
                                    this.emit(TokenType.SEC);
                                    break;
                                case "clz":
                                    this.emit(TokenType.CLZ);
                                    break;
                                case "clc":
                                    this.emit(TokenType.CLC);
                                    break;
                                case "ldi":
                                    this.emit(TokenType.LDI);
                                    break;
                                case "out":
                                    this.emit(TokenType.OUT);
                                    break;
                                case "in":
                                    this.emit(TokenType.IN);
                                    break;
                                case "poll":
                                    this.emit(TokenType.POLL);
                                    break;
                                case "push":
                                    this.emit(TokenType.PUSH);
                                    break;
                                case "pop":
                                    this.emit(TokenType.POP);
                                    break;
                                case "a":
                                    this.emit(TokenType.REGISTER_A);
                                    break;
                                case "b":
                                    this.emit(TokenType.REGISTER_B);
                                    break;
                                case "c":
                                    this.emit(TokenType.REGISTER_C);
                                    break;
                                case "d":
                                    this.emit(TokenType.REGISTER_D);
                                    break;
                                case "i":
                                    this.emit(TokenType.REGISTER_I);
                                    break;
                                case "il":
                                    this.emit(TokenType.REGISTER_IL);
                                    break;
                                case "ih":
                                    this.emit(TokenType.REGISTER_IH);
                                    break;
                                case "pc":
                                    this.emit(TokenType.REGISTER_PC);
                                    break;
                                case "sp":
                                    this.emit(TokenType.REGISTER_SP);
                                    break;
                                default:
                                    this.emit(TokenType.IDENT);
                                    break;
                            }
                        }
                    } else if (Lexer.isDecimalDigit(c)) {
                        final TokenType type;

                        if (this.accept("b")) {
                            type = TokenType.BINARY_INTEGER;
                            this.acceptRun("01");
                        } else if (this.accept("x")) {
                            type = TokenType.HEX_INTEGER;
                            this.acceptRun("0123456789abcdefABCDEF");
                        } else {
                            type = TokenType.DECIMAL_INTEGER;
                            this.acceptRun("0123456789");
                        }

                        this.emit(type);
                    } else if (Lexer.isWhitespace(c)) {
                        while (this.more() && Lexer.isWhitespace(this.peek())) this.next();
                        this.ignore();
                    } else {
                        throw new RuntimeException("Unrecognized character: '" + c + "'");
                    }
                    break;
            }
        }
        return this.tokens;
    }
}