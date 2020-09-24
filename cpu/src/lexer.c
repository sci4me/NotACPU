#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

#include "lexer.h"

static inline u8 is_letter(u8 c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
}

static inline u8 is_letter_or_underscore(u8 c) {
    return is_letter(c) || c == '_';
}

static inline u8 is_decimal_digit(u8 c) {
    return c >= '0' && c <= '9';
}

static inline u8 is_ident(u8 c) {
    return is_letter_or_underscore(c) || is_decimal_digit(c);
}

static inline u8 is_hex_digit(u8 c) {
    return (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F') || is_decimal_digit(c);
}

static inline u8 is_binary_digit(u8 c) {
    return c == '0' || c == '1';
}

static u8 next(lexer *l) {
    l->column++;
    return fgetc(l->fp);
}

static u8 peek(lexer *l) {
    u8 c = fgetc(l->fp);
    ungetc(c, l->fp);
    return c;
}

static u8 accept(lexer *l, char *valid) {
    u8 c = peek(l);
    if(c == (u8)EOF) return 0;

    for(u32 i = 0; i < strlen(valid); i++) {
        if(valid[i] == c) {
            next(l);
            return 1;
        }
    }

    return 0;
}

static void accept_run(lexer *l, char *valid) {
    while(accept(l, valid));
}

static u8 accept_seq(lexer *l, char *seq) {
    u64 pos = ftell(l->fp);
    u32 col = l->column;

    for(u32 i = 0; i < strlen(seq); i++) {
        if(seq[i] != next(l)) {
            fseek(l->fp, pos, SEEK_SET);
            l->column = col;
            return 0;
        }
    }

    return 1;
}

static void skip_whitespace(lexer *l) {
    u8 r = 1;
    while(r) {
        u8 c = peek(l);
        switch(c) {
            case '\n':
                l->line++;
                l->column = 0;
            case '\t':
            case ' ':
                next(l);
                break;
            default:
                r = 0;
                break;
        }
    }
    l->has_token = 0;
}

static void emit(lexer *l, token_type type, char *data) {
    assert(!l->has_token);

    l->t.type = type;
    l->t.line = l->line;
    l->t.column = l->column;
    l->t.data = malloc(strlen(data) + 1);

    strcpy(l->t.data, data);

    l->has_token = 1;
}

static void find_next_token(lexer *l) {
    assert(!l->has_token);

    skip_whitespace(l);

    if(peek(l) == (u8)EOF) {
        return;
    }

    if(accept_seq(l, "nop")) {
        emit(l, TOKEN_NOP, "nop");
    } else if(accept_seq(l, "add")) {
        emit(l, TOKEN_ADD, "add");
    } else if(accept_seq(l, "adc")) {
        emit(l, TOKEN_ADC, "adc");
    } else if(accept_seq(l, "sub")) {
        emit(l, TOKEN_SUB, "sub");
    } else if(accept_seq(l, "sbc")) {
        emit(l, TOKEN_SBC, "sbc");
    } else if(accept_seq(l, "and")) {
        emit(l, TOKEN_AND, "and");
    } else if(accept_seq(l, "or")) {
        emit(l, TOKEN_OR, "or");
    } else if(accept_seq(l, "xor")) {
        emit(l, TOKEN_XOR, "xor");
    } else if(accept_seq(l, "not")) {
        emit(l, TOKEN_NOT, "not");
    } else if(accept_seq(l, "lsh")) {
        emit(l, TOKEN_LSH, "lsh");
    } else if(accept_seq(l, "rsh")) {
        emit(l, TOKEN_RSH, "rsh");
    } else if(accept_seq(l, "mov")) {
        emit(l, TOKEN_MOV, "mov");
    } else if(accept_seq(l, "ld")) {
        emit(l, TOKEN_LD, "ld");
    } else if(accept_seq(l, "st")) {
        emit(l, TOKEN_ST, "st");
    } else if(accept_seq(l, "jz")) {
        emit(l, TOKEN_JZ, "jz");
    } else if(accept_seq(l, "jc")) {
        emit(l, TOKEN_JC, "jc");
    } else if(accept_seq(l, "jmp")) {
        emit(l, TOKEN_JMP, "jmp");
    } else if(accept_seq(l, "sez")) {
        emit(l, TOKEN_SEZ, "sez");
    } else if(accept_seq(l, "sec")) {
        emit(l, TOKEN_SEC, "sec");
    } else if(accept_seq(l, "clz")) {
        emit(l, TOKEN_CLZ, "clz");
    } else if(accept_seq(l, "clc")) {
        emit(l, TOKEN_CLC, "clc");
    } else if(accept_seq(l, "hlt")) {
        emit(l, TOKEN_HLT, "hlt");
    } else if(accept(l, "a")) {
        emit(l, REGISTER_A, "a");
    } else if(accept(l, "b")) {
        emit(l, REGISTER_B, "b");
    } else if(accept(l, "c")) {
        emit(l, REGISTER_C, "c");
    } else if(accept(l, "d")) {
        emit(l, REGISTER_D, "d");
    } else if(accept(l, "i")) {
        emit(l, REGISTER_I, "i");
    } else if(accept_seq(l, "pc")) {
        emit(l, REGISTER_PC, "pc");
    } else if(accept_seq(l, ".db")) {
        emit(l, DIRECTIVE_DB, ".db");
    } else if(accept_seq(l, ".dw")) {
        emit(l, DIRECTIVE_DW, ".dw");
    } else if(accept_seq(l, ".resb")) {
        emit(l, DIRECTIVE_RESB, ".resb");
    } else if(accept_seq(l, ".resw")) {
        emit(l, DIRECTIVE_RESW, ".resw");
    } else if(is_letter_or_underscore(peek(l))) {
        u64 start = ftell(l->fp);

        while(is_ident(peek(l))) {
            next(l);
        }

        u64 len = ftell(l->fp) - start + 1;
        if(peek(l) == (u8)EOF) len++;

        fseek(l->fp, start, SEEK_SET);

        u8 *str = malloc(len);
        fgets(str, len, l->fp);

        if(accept(l, ":")) {
            emit(l, LABEL, str);
        } else {
            emit(l, IDENT, str);
        }
    } else {
        token_type type;

        if(accept_seq(l, "0b"))       type = BINARY_INTEGER;
        else if(accept_seq(l, "0x"))  type = HEX_INTEGER;
        else                          type = DECIMAL_INTEGER;

        u64 start = ftell(l->fp);

        if(type == BINARY_INTEGER)    accept_run(l, "01");
        else if(type == HEX_INTEGER)  accept_run(l, "0123456789ABCDEFabcdef");
        else                          accept_run(l, "0123456789");

        u64 len = ftell(l->fp) - start + 1;
        if(peek(l) == (u8)EOF) len++;

        fseek(l->fp, start, SEEK_SET);

        u8 *str = malloc(len);
        fgets(str, len, l->fp);

        emit(l, type, str);
    }
}

void lexer_init(lexer *l, FILE *fp) {
    l->fp = fp;
    l->line = 1;
    l->column = 1;
    l->has_token = 0;

    find_next_token(l);
}

u8 lexer_has_token(lexer *l) {
    return l->has_token;
}

token lexer_get_token(lexer *l) {
    assert(l->has_token);
    return l->t;
}

void lexer_eat_token(lexer *l) {
    assert(l->has_token);
    l->has_token = 0;
    find_next_token(l);
}