#ifndef EMULATOR_H
#define EMULATOR_H

#include "types.h"

#define FLAG_Z           0x01
#define FLAG_C           0x02
#define FLAG_H           0x04

enum {
	NOP,

	ADD_A,
	ADD_B,
	ADD_C,
	ADD_D,
	ADC_A,
	ADC_B,
	ADC_C,
	ADC_D,
	SUB_A,
	SUB_B,
	SUB_C,
	SUB_D,
	SBC_A,
	SBC_B,
	SBC_C,
	SBC_D,

	AND_A,
	AND_B,
	AND_C,
	AND_D,
	OR_A,
	OR_B,
	OR_C,
	OR_D,
	XOR_A,
	XOR_B,
	XOR_C,
	XOR_D,
	NOT_A,
	NOT_B,
	NOT_C,
	NOT_D,

	LSH_A,
	LSH_B,
	LSH_C,
	LSH_D,
	RSH_A,
	RSH_B,
	RSH_C,
	RSH_D,

	MOV_A_B,
	MOV_A_C,
	MOV_A_D,
	MOV_B_A,
	MOV_B_C,
	MOV_B_D,
	MOV_C_A,
	MOV_C_B,
	MOV_C_D,
	MOV_D_A,
	MOV_D_B,
	MOV_D_C,
	MOV_IL_A,
	MOV_IL_B,
	MOV_IL_C,
	MOV_IL_D,
	MOV_IH_A,
	MOV_IH_B,
	MOV_IH_C,
	MOV_IH_D,
	MOV_A_IL,
	MOV_B_IL,
	MOV_C_IL,
	MOV_D_IL,
	MOV_A_IH,
	MOV_B_IH,
	MOV_C_IH,
	MOV_D_IH,
	MOV_PC_I,
	MOV_I_PC,

	LD_A_M,
	LD_B_M,
	LD_C_M,
	LD_D_M,
	ST_A_M,
	ST_B_M,
	ST_C_M,
	ST_D_M,
	LD_A_I,
	LD_B_I,
	LD_C_I,
	LD_D_I,
	ST_A_I,
	ST_B_I,
	ST_C_I,
	ST_D_I,
	LD_IL_M,
	LD_IH_M,
	ST_IL_M,
	ST_IH_M,

	JZ_M,
	JC_M,
	JMP_M,
	JZ_I,
	JC_I,
	JMP_I,

	SEZ,
	SEC,
	CLZ,
	CLC,

	HLT
};

typedef struct emulator {
    u8 *memory;

    u16 pc;
    u16 i;
    u8 a;
    u8 b;
    u8 c;
    u8 d;
    u8 flags;
} emulator;

void emulator_init(emulator *emu);

void emulator_free(emulator *emu);

void emulator_run(emulator *emu);

#endif