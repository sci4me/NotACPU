#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "types.h"
#include "lexer.h"
#include "emulator.h"

u32 assemble(u8 *file) {
    FILE *fp = fopen(file, "r");
    if(!fp) {
        printf("Couldn't open file '%s'\n", file);
        return 1;
    }

    lexer ll;
    lexer *l = &ll;
    lexer_init(l, fp);

    while(lexer_has_token(l)) {
        token t = lexer_get_token(l);
        printf("%s\n", t.data);
        free(t.data);
        lexer_eat_token(l);
    }

    fclose(fp);

    return 0;
}

u32 emulate(u8 *file) {
    return 0;
}

u32 main(u32 argc, u8 **argv) {
    if(argc != 3) {
        printf("Usage: cpu asm|emu <file>\n");
        return 1;
    }

    if(strcmp(argv[1], "asm") == 0) {
        return assemble(argv[2]);
    } else if(strcmp(argv[1], "emu") == 0) {
        return emulate(argv[2]);
    } else {
        printf("Invalid command '%s'\n", argv[1]);
        return 1;
    }
}