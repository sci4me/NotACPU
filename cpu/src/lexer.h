#ifndef LEXER_H
#define LEXER_H

#include "types.h"

typedef enum token_type {
    TOKEN_NOP,

    TOKEN_ADD,
    TOKEN_ADC,
    TOKEN_SUB,
    TOKEN_SBC,

    TOKEN_AND,
    TOKEN_OR,
    TOKEN_XOR,
    TOKEN_NOT,

    TOKEN_LSH,
    TOKEN_RSH,

    TOKEN_MOV,

    TOKEN_LD,
    TOKEN_ST,

    TOKEN_JZ,
    TOKEN_JC,
    TOKEN_JMP,

    TOKEN_SEZ,
    TOKEN_SEC,
    TOKEN_CLZ,
    TOKEN_CLC,

    TOKEN_HLT,

    REGISTER_A,
    REGISTER_B,
    REGISTER_C,
    REGISTER_D,
    REGISTER_I,
    REGISTER_PC,

    LABEL,
    IDENT,

    DECIMAL_INTEGER,
    HEX_INTEGER,
    BINARY_INTEGER,

    DIRECTIVE_DB,
    DIRECTIVE_DW,
    DIRECTIVE_RESB,
    DIRECTIVE_RESW
} token_type;

typedef struct token {
    token_type type;
    u32 line;
    u32 column;
    char *data;
} token;

typedef struct lexer {
    FILE *fp;
    u32 line;
    u32 column;
    u8 has_token;
    token t;
    u8 rescan;
} lexer;

void lexer_init(lexer *l, FILE *fp);

u8 lexer_has_token(lexer *l);

token lexer_get_token(lexer *l);

void lexer_eat_token(lexer *l);

#endif