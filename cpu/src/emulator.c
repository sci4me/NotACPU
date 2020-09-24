#include <stdlib.h>

#include "emulator.h"

static u8 write_back_8(emulator *emu, u8 insn, u8 result) {
    switch(insn) {
        case ADD_A:
        case ADC_A:
        case SUB_A:
        case SBC_A:
        case AND_A:
        case OR_A:
        case XOR_A:
        case NOT_A:
        case LSH_A:
        case RSH_A:
            emu->a = result;
            break;
        case ADD_B:
        case ADC_B:
        case SUB_B:
        case SBC_B:
        case AND_B:
        case OR_B:
        case XOR_B:
        case NOT_B:
        case LSH_B:
        case RSH_B:
            emu->b = result;
            break;
        case ADD_C:
        case ADC_C:
        case SUB_C:
        case SBC_C:
        case AND_C:
        case OR_C:
        case XOR_C:
        case NOT_C:
        case LSH_C:
        case RSH_C:
            emu->c = result;
            break;
        case ADD_D:
        case ADC_D:
        case SUB_D:
        case SBC_D:
        case AND_D:
        case OR_D:
        case XOR_D:
        case NOT_D:
        case LSH_D:
        case RSH_D:
            emu->d = result;
            break;
    }
    return result;
}

static void set_flag(emulator *emu, u8 flag, u8 value) {
    if(value) emu->flags &= ~flag;
    else      emu->flags |= flag;
}

void emulator_init(emulator *emu) {
    emu->memory = malloc(0xFFFF);
}

void emulator_free(emulator *emu) {
    free(emu->memory);
}

void emulator_run(emulator *emu) {
    while(!(emu->flags & FLAG_H)) {
        u8 insn = emu->memory[emu->pc++];
        switch(insn) {
        	case NOP: {
				break;
			}
			case ADD_A:
			case ADD_B:
			case ADD_C:
			case ADD_D:
            case ADC_A:
            case ADC_B:
            case ADC_C:
            case ADC_D: {
                u16 result = emu->a + emu->b;
                if((insn == ADC_A || insn == ADC_B || insn == ADC_C || insn == ADC_D) && (emu->flags & FLAG_C)) result += 1;
                u8 w = write_back_8(emu, insn, result & 0xFF);
                set_flag(emu, FLAG_C, result > 0xFF);
                set_flag(emu, FLAG_Z, w == 0);
                break;
			}
			case SUB_A:
			case SUB_B:
			case SUB_C:
			case SUB_D:
			case SBC_A:
			case SBC_B:
			case SBC_C:
			case SBC_D: {
                u16 result = emu->a - emu->b;
                if((insn == SBC_A || insn == SBC_B || insn == SBC_C || insn == SBC_D) && !(emu->flags & FLAG_C)) result -= 1;
                u8 w = write_back_8(emu, insn, result & 0xFF);
                set_flag(emu, FLAG_C, result > 0xFF);
                set_flag(emu, FLAG_Z, w == 0);
                break;
			}
			case AND_A:
			case AND_B:
			case AND_C:
			case AND_D: {
                set_flag(emu, FLAG_Z, write_back_8(emu, insn, emu->a & emu->b));
				break;
			}
			case OR_A:
			case OR_B:
			case OR_C:
			case OR_D: {
                set_flag(emu, FLAG_Z, write_back_8(emu, insn, emu->a | emu->b));
				break;
			}
			case XOR_A:
			case XOR_B:
			case XOR_C:
			case XOR_D: {
                set_flag(emu, FLAG_Z, write_back_8(emu, insn, emu->a ^ emu->b));
				break;
			}
			case NOT_A:
			case NOT_B:
			case NOT_C:
			case NOT_D: {
                set_flag(emu, FLAG_Z, write_back_8(emu, insn, ~emu->a) == 0);
				break;
			}
			case LSH_A:
			case LSH_B:
			case LSH_C:
			case LSH_D: {
                u16 result = emu->a << 1;
                u8 w = write_back_8(emu, insn, result & 0xFF);
                set_flag(emu, FLAG_C, result > 0xFF);
                set_flag(emu, FLAG_Z, w == 0);
				break;
			}
			case RSH_A:
			case RSH_B:
			case RSH_C:
			case RSH_D: {
				u16 result = emu->a >> 1;
                u8 w = write_back_8(emu, insn, result & 0xFF);
                set_flag(emu, FLAG_C, result > 0xFF);
                set_flag(emu, FLAG_Z, w == 0);
                break;
			}
			case MOV_A_B: {
				emu->a = emu->b;
                break;
			}
			case MOV_A_C: {
                emu->a = emu->c;
				break;
			}
			case MOV_A_D: {
                emu->a = emu->d;
				break;
			}
			case MOV_B_A: {
                emu->b = emu->a;
				break;
			}
			case MOV_B_C: {
                emu->b = emu->c;
				break;
			}
			case MOV_B_D: {
                emu->b = emu->d;
				break;
			}
			case MOV_C_A: {
                emu->c = emu->a;
				break;
			}
			case MOV_C_B: {
                emu->c = emu->b;
				break;
			}
			case MOV_C_D: {
                emu->c = emu->d;
                break;
			}
			case MOV_D_A: {
                emu->d = emu->a;
				break;
			}
			case MOV_D_B: {
                emu->d = emu->b;
				break;
			}
			case MOV_D_C: {
                emu->d = emu->c;
				break;
			}
			case MOV_IL_A: {
                emu->i = (emu->i & 0xFF00) | emu->a;
				break;
			}
			case MOV_IL_B: {
                emu->i = (emu->i & 0xFF00) | emu->b;
				break;
			}
			case MOV_IL_C: {
                emu->i = (emu->i & 0xFF00) | emu->c;
				break;
			}
			case MOV_IL_D: {
                emu->i = (emu->i & 0xFF00) | emu->d;
				break;
			}
			case MOV_IH_A: {
                emu->i = (emu->i & 0x00FF) | (emu->a << 8);
				break;
			}
			case MOV_IH_B: {
                emu->i = (emu->i & 0x00FF) | (emu->b << 8);
				break;
			}
			case MOV_IH_C: {
                emu->i = (emu->i & 0x00FF) | (emu->c << 8);
				break;
			}
			case MOV_IH_D: {
                emu->i = (emu->i & 0x00FF) | (emu->d << 8);
				break;
			}
			case MOV_A_IL: {
                emu->a = emu->i & 0xFF;
				break;
			}
			case MOV_B_IL: {
                emu->b = emu->i & 0xFF;
				break;
			}
			case MOV_C_IL: {
                emu->c = emu->i & 0xFF;
				break;
			}
			case MOV_D_IL: {
                emu->d = emu->i & 0xFF;
				break;
			}
			case MOV_A_IH: {
                emu->a = (emu->i >> 8) & 0xFF;
				break;
			}
			case MOV_B_IH: {
                emu->b = (emu->i >> 8) & 0xFF;
				break;
			}
			case MOV_C_IH: {
                emu->c = (emu->i >> 8) & 0xFF;
				break;
			}
			case MOV_D_IH: {
                emu->d = (emu->i >> 8) & 0xFF;
				break;
			}
			case MOV_PC_I: {
                emu->pc = emu->i;
				break;
			}
			case MOV_I_PC: {
                emu->i = emu->pc;
				break;
			}
			case LD_A_M: {
                u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->a = emu->memory[addr];
                break;
			}
			case LD_B_M: {
				u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->b = emu->memory[addr];
                break;
			}
			case LD_C_M: {
				u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->c = emu->memory[addr];
                break;
			}
			case LD_D_M: {
                u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->d = emu->memory[addr];
				break;
			}
			case ST_A_M: {
                u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->memory[addr] = emu->a;
				break;
			}
			case ST_B_M: {
				u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->memory[addr] = emu->b;
                break;
			}
			case ST_C_M: {
                u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->memory[addr] = emu->c;
				break;
			}
			case ST_D_M: {
                u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->memory[addr] = emu->d;
				break;
			}
			case LD_A_I: {
                emu->a = emu->memory[emu->i];
				break;
			}
			case LD_B_I: {
                emu->b = emu->memory[emu->i];
				break;
			}
			case LD_C_I: {
                emu->c = emu->memory[emu->i];
				break;
			}
			case LD_D_I: {
                emu->d = emu->memory[emu->i];
				break;
			}
			case ST_A_I: {
                emu->memory[emu->i] = emu->a;
                break;
			}
			case ST_B_I: {
                emu->memory[emu->i] = emu->b;
				break;
			}
			case ST_C_I: {
                emu->memory[emu->i] = emu->c;
				break;
			}
			case ST_D_I: {
                emu->memory[emu->i] = emu->d;
				break;
			}
			case LD_IL_M: {
                u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->i = (emu->i & 0xFF00) | emu->memory[addr];
				break;
			}
			case LD_IH_M: {
                u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->i = (emu->i & 0x00FF) | (emu->memory[addr] << 8);
				break;
			}
			case ST_IL_M: {
                u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->memory[addr] = emu->i & 0xFF;
				break;
			}
			case ST_IH_M: {
                u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                emu->memory[addr] = (emu->i >> 8) & 0xFF;
				break;
			}
			case JZ_M: {
                u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                if(emu->flags & FLAG_Z) {
                    emu->pc = addr;
                }
				break;
			}
			case JC_M: {
				u16 addr = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
                if(emu->flags & FLAG_C) {
                    emu->pc = addr;
                }
                break;
			}
			case JMP_M: {
                emu->pc = (emu->memory[emu->pc++] << 8) | emu->memory[emu->pc++];
				break;
			}
			case JZ_I: {
                if(emu->flags & FLAG_Z) {
                    emu->pc = emu->i;
                }
				break;
			}
			case JC_I: {
                if(emu->flags & FLAG_C) {
                    emu->pc = emu->i;
                }
				break;
			}
			case JMP_I: {
                emu->pc = emu->i;
				break;
			}
            case SEZ: {
                emu->flags |= FLAG_Z;
                break;
            }
            case SEC: {
                emu->flags |= FLAG_C;
                break;
            }
            case CLZ: {
                emu->flags &= ~FLAG_Z;
                break;
            }
            case CLC: {
                emu->flags &= ~FLAG_C;
                break;
            }
            case HLT: {
                emu->flags |= FLAG_H;
                break;
            }
        }
    }
}