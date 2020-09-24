package com.sci.cpu.asm.lexer;

public enum TokenType {
    NOP,

    ADD,
    ADC,
    SUB,
    SBC,

    AND,
    OR,
    XOR,
    NOT,

    LSH,
    RSH,

    MOV,

    LD,
    ST,

    JZ,
    JC,
    JMP,

    SEZ,
    SEC,
    CLZ,
    CLC,

    LDI,

    OUT,
    IN,
    POLL,

    PUSH,
    POP,

    REGISTER_A,
    REGISTER_B,
    REGISTER_C,
    REGISTER_D,
    REGISTER_I,
    REGISTER_IL,
    REGISTER_IH,
    REGISTER_PC,
    REGISTER_SP,

    LABEL,
    IDENT,

    DECIMAL_INTEGER,
    BINARY_INTEGER,
    HEX_INTEGER,

    DIRECTIVE_DB,
    DIRECTIVE_DW,
    DIRECTIVE_RESB,
    DIRECTIVE_RESW,
    DIRECTIVE_ORG,

    COMMA
}