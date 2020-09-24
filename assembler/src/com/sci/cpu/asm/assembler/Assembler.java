package com.sci.cpu.asm.assembler;

import java.util.HashMap;
import java.util.Map;

public final class Assembler {
    private static int currentInsn = 0;

    private static final int NOP = currentInsn++;

    private static final int ADD_A = currentInsn++;
    private static final int ADD_B = currentInsn++;
    private static final int ADD_C = currentInsn++;
    private static final int ADD_D = currentInsn++;
    private static final int ADD_IL = currentInsn++;
    private static final int ADD_IH = currentInsn++;
    private static final int ADC_A = currentInsn++;
    private static final int ADC_B = currentInsn++;
    private static final int ADC_C = currentInsn++;
    private static final int ADC_D = currentInsn++;
    private static final int ADC_IL = currentInsn++;
    private static final int ADC_IH = currentInsn++;
    private static final int SUB_A = currentInsn++;
    private static final int SUB_B = currentInsn++;
    private static final int SUB_C = currentInsn++;
    private static final int SUB_D = currentInsn++;
    private static final int SUB_IL = currentInsn++;
    private static final int SUB_IH = currentInsn++;
    private static final int SBC_A = currentInsn++;
    private static final int SBC_B = currentInsn++;
    private static final int SBC_C = currentInsn++;
    private static final int SBC_D = currentInsn++;
    private static final int SBC_IL = currentInsn++;
    private static final int SBC_IH = currentInsn++;

    private static final int AND_A = currentInsn++;
    private static final int AND_B = currentInsn++;
    private static final int AND_C = currentInsn++;
    private static final int AND_D = currentInsn++;
    private static final int AND_IL = currentInsn++;
    private static final int AND_IH = currentInsn++;
    private static final int OR_A = currentInsn++;
    private static final int OR_B = currentInsn++;
    private static final int OR_C = currentInsn++;
    private static final int OR_D = currentInsn++;
    private static final int OR_IL = currentInsn++;
    private static final int OR_IH = currentInsn++;
    private static final int XOR_A = currentInsn++;
    private static final int XOR_B = currentInsn++;
    private static final int XOR_C = currentInsn++;
    private static final int XOR_D = currentInsn++;
    private static final int XOR_IL = currentInsn++;
    private static final int XOR_IH = currentInsn++;
    private static final int NOT_A = currentInsn++;
    private static final int NOT_B = currentInsn++;
    private static final int NOT_C = currentInsn++;
    private static final int NOT_D = currentInsn++;
    private static final int NOT_IL = currentInsn++;
    private static final int NOT_IH = currentInsn++;

    private static final int LSH_A = currentInsn++;
    private static final int LSH_B = currentInsn++;
    private static final int LSH_C = currentInsn++;
    private static final int LSH_D = currentInsn++;
    private static final int LSH_IL = currentInsn++;
    private static final int LSH_IH = currentInsn++;
    private static final int RSH_A = currentInsn++;
    private static final int RSH_B = currentInsn++;
    private static final int RSH_C = currentInsn++;
    private static final int RSH_D = currentInsn++;
    private static final int RSH_IL = currentInsn++;
    private static final int RSH_IH = currentInsn++;

    private static final int MOV_A_B = currentInsn++;
    private static final int MOV_A_C = currentInsn++;
    private static final int MOV_A_D = currentInsn++;
    private static final int MOV_B_A = currentInsn++;
    private static final int MOV_B_C = currentInsn++;
    private static final int MOV_B_D = currentInsn++;
    private static final int MOV_C_A = currentInsn++;
    private static final int MOV_C_B = currentInsn++;
    private static final int MOV_C_D = currentInsn++;
    private static final int MOV_D_A = currentInsn++;
    private static final int MOV_D_B = currentInsn++;
    private static final int MOV_D_C = currentInsn++;
    private static final int MOV_IL_A = currentInsn++;
    private static final int MOV_IL_B = currentInsn++;
    private static final int MOV_IL_C = currentInsn++;
    private static final int MOV_IL_D = currentInsn++;
    private static final int MOV_IH_A = currentInsn++;
    private static final int MOV_IH_B = currentInsn++;
    private static final int MOV_IH_C = currentInsn++;
    private static final int MOV_IH_D = currentInsn++;
    private static final int MOV_A_IL = currentInsn++;
    private static final int MOV_B_IL = currentInsn++;
    private static final int MOV_C_IL = currentInsn++;
    private static final int MOV_D_IL = currentInsn++;
    private static final int MOV_A_IH = currentInsn++;
    private static final int MOV_B_IH = currentInsn++;
    private static final int MOV_C_IH = currentInsn++;
    private static final int MOV_D_IH = currentInsn++;
    private static final int MOV_PC_I = currentInsn++;
    private static final int MOV_I_PC = currentInsn++;
    private static final int MOV_SP_I = currentInsn++;
    private static final int MOV_I_SP = currentInsn++;

    private static final int LD_A_M = currentInsn++;
    private static final int LD_B_M = currentInsn++;
    private static final int LD_C_M = currentInsn++;
    private static final int LD_D_M = currentInsn++;
    private static final int ST_A_M = currentInsn++;
    private static final int ST_B_M = currentInsn++;
    private static final int ST_C_M = currentInsn++;
    private static final int ST_D_M = currentInsn++;
    private static final int LD_A_I = currentInsn++;
    private static final int LD_B_I = currentInsn++;
    private static final int LD_C_I = currentInsn++;
    private static final int LD_D_I = currentInsn++;
    private static final int ST_A_I = currentInsn++;
    private static final int ST_B_I = currentInsn++;
    private static final int ST_C_I = currentInsn++;
    private static final int ST_D_I = currentInsn++;
    private static final int LD_IL_M = currentInsn++;
    private static final int LD_IH_M = currentInsn++;
    private static final int ST_IL_M = currentInsn++;
    private static final int ST_IH_M = currentInsn++;

    private static final int JZ_M = currentInsn++;
    private static final int JC_M = currentInsn++;
    private static final int JMP_M = currentInsn++;
    private static final int JZ_I = currentInsn++;
    private static final int JC_I = currentInsn++;
    private static final int JMP_I = currentInsn++;

    private static final int SEZ = currentInsn++;
    private static final int SEC = currentInsn++;
    private static final int CLZ = currentInsn++;
    private static final int CLC = currentInsn++;

    private static final int LDI_A = currentInsn++;
    private static final int LDI_B = currentInsn++;
    private static final int LDI_C = currentInsn++;
    private static final int LDI_D = currentInsn++;
    private static final int LDI_IL = currentInsn++;
    private static final int LDI_IH = currentInsn++;
    private static final int LDI_I = currentInsn++;

    private static final int OUT_A = currentInsn++;
    private static final int OUT_B = currentInsn++;
    private static final int OUT_C = currentInsn++;
    private static final int OUT_D = currentInsn++;
    private static final int IN_A = currentInsn++;
    private static final int IN_B = currentInsn++;
    private static final int IN_C = currentInsn++;
    private static final int IN_D = currentInsn++;
    private static final int POLL_A = currentInsn++;
    private static final int POLL_B = currentInsn++;
    private static final int POLL_C = currentInsn++;
    private static final int POLL_D = currentInsn++;

    private static final int PUSH_A = currentInsn++;
    private static final int PUSH_B = currentInsn++;
    private static final int PUSH_C = currentInsn++;
    private static final int PUSH_D = currentInsn++;
    private static final int PUSH_IL = currentInsn++;
    private static final int PUSH_IH = currentInsn++;
    private static final int POP_A = currentInsn++;
    private static final int POP_B = currentInsn++;
    private static final int POP_C = currentInsn++;
    private static final int POP_D = currentInsn++;
    private static final int POP_IL = currentInsn++;
    private static final int POP_IH = currentInsn++;

    private byte[] memory;
    private int pos;
    private Map<Integer, IAddress> patches;

    public Assembler() {
        this.memory = new byte[0xFFFF];
        this.patches = new HashMap<>();
    }

    private void emit(final int b) {
        if (b < 0 || b > 255) throw new RuntimeException(b + " out of range");
        this.memory[this.pos++] = (byte) b;
    }

    public int pos() {
        return this.pos;
    }

    public void org(final int address) {
        if (address < 0 || address > 0xFFFF) throw new RuntimeException("Address out of range" + address);
        this.pos = address;
    }

    public int db(final int value) {
        if (value < 0 || value > 0xFF) throw new RuntimeException("Value out of range " + value);
        this.emit(value);
        return this.pos - 1;
    }

    public int dw(final int value) {
        if (value < 0 || value > 0xFFFF) throw new RuntimeException("Value out of range " + value);
        this.emit((byte) ((value >> 8) & 0xFF));
        this.emit((byte) (value & 0xFF));
        return this.pos - 2;
    }

    public int resb() {
        this.pos++;
        return this.pos - 1;
    }

    public int resw() {
        this.pos += 2;
        return this.pos - 2;
    }

    public void setb(final int addr, final int b) {
        if (addr < 0 || addr > 0xFFFF) throw new RuntimeException("Address out of range " + addr);
        if (b < 0 || b > 0xFF) throw new RuntimeException("Value out of range " + b);
        this.memory[addr] = (byte) b;
    }

    public int mark(final Label label) {
        label.resolve(this.pos);
        return this.pos;
    }

    public void nop() {
        this.emit(NOP);
    }

    public void add(final Register r) {
        switch (r) {
            case A:
                this.emit(ADD_A);
                break;
            case B:
                this.emit(ADD_B);
                break;
            case C:
                this.emit(ADD_C);
                break;
            case D:
                this.emit(ADD_D);
                break;
            case Il:
                this.emit(ADD_IL);
                break;
            case Ih:
                this.emit(ADD_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void adc(final Register r) {
        switch (r) {
            case A:
                this.emit(ADC_A);
                break;
            case B:
                this.emit(ADC_B);
                break;
            case C:
                this.emit(ADC_C);
                break;
            case D:
                this.emit(ADC_D);
                break;
            case Il:
                this.emit(ADC_IL);
                break;
            case Ih:
                this.emit(ADC_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void sub(final Register r) {
        switch (r) {
            case A:
                this.emit(SUB_A);
                break;
            case B:
                this.emit(SUB_B);
                break;
            case C:
                this.emit(SUB_C);
                break;
            case D:
                this.emit(SUB_D);
                break;
            case Il:
                this.emit(SUB_IL);
                break;
            case Ih:
                this.emit(SUB_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void sbc(final Register r) {
        switch (r) {
            case A:
                this.emit(SBC_A);
                break;
            case B:
                this.emit(SBC_B);
                break;
            case C:
                this.emit(SBC_C);
                break;
            case D:
                this.emit(SBC_D);
                break;
            case Il:
                this.emit(SBC_IL);
                break;
            case Ih:
                this.emit(SBC_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void and(final Register r) {
        switch (r) {
            case A:
                this.emit(AND_A);
                break;
            case B:
                this.emit(AND_B);
                break;
            case C:
                this.emit(AND_C);
                break;
            case D:
                this.emit(AND_D);
                break;
            case Il:
                this.emit(AND_IL);
                break;
            case Ih:
                this.emit(AND_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void or(final Register r) {
        switch (r) {
            case A:
                this.emit(OR_A);
                break;
            case B:
                this.emit(OR_B);
                break;
            case C:
                this.emit(OR_C);
                break;
            case D:
                this.emit(OR_D);
                break;
            case Il:
                this.emit(OR_IL);
                break;
            case Ih:
                this.emit(OR_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void xor(final Register r) {
        switch (r) {
            case A:
                this.emit(XOR_A);
                break;
            case B:
                this.emit(XOR_B);
                break;
            case C:
                this.emit(XOR_C);
                break;
            case D:
                this.emit(XOR_D);
                break;
            case Il:
                this.emit(XOR_IL);
                break;
            case Ih:
                this.emit(XOR_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void not(final Register r) {
        switch (r) {
            case A:
                this.emit(NOT_A);
                break;
            case B:
                this.emit(NOT_B);
                break;
            case C:
                this.emit(NOT_C);
                break;
            case D:
                this.emit(NOT_D);
                break;
            case Il:
                this.emit(NOT_IL);
                break;
            case Ih:
                this.emit(NOT_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void lsh(final Register r) {
        switch (r) {
            case A:
                this.emit(LSH_A);
                break;
            case B:
                this.emit(LSH_B);
                break;
            case C:
                this.emit(LSH_C);
                break;
            case D:
                this.emit(LSH_D);
                break;
            case Il:
                this.emit(LSH_IL);
                break;
            case Ih:
                this.emit(LSH_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void rsh(final Register r) {
        switch (r) {
            case A:
                this.emit(RSH_A);
                break;
            case B:
                this.emit(RSH_B);
                break;
            case C:
                this.emit(RSH_C);
                break;
            case D:
                this.emit(RSH_D);
                break;
            case Il:
                this.emit(RSH_IL);
                break;
            case Ih:
                this.emit(RSH_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void mov(final Register dest, final Register src) {
        switch (dest) {
            case A:
                switch (src) {
                    case B:
                        this.emit(MOV_A_B);
                        break;
                    case C:
                        this.emit(MOV_A_C);
                        break;
                    case D:
                        this.emit(MOV_A_D);
                        break;
                    case Il:
                        this.emit(MOV_A_IL);
                        break;
                    case Ih:
                        this.emit(MOV_A_IH);
                        break;
                    default:
                        throw new RuntimeException("Invalid register " + src);
                }
                break;
            case B:
                switch (src) {
                    case A:
                        this.emit(MOV_B_A);
                        break;
                    case C:
                        this.emit(MOV_B_C);
                        break;
                    case D:
                        this.emit(MOV_B_D);
                        break;
                    case Il:
                        this.emit(MOV_B_IL);
                        break;
                    case Ih:
                        this.emit(MOV_B_IH);
                        break;
                    default:
                        throw new RuntimeException("Invalid register " + src);
                }
                break;
            case C:
                switch (src) {
                    case A:
                        this.emit(MOV_C_A);
                        break;
                    case B:
                        this.emit(MOV_C_B);
                        break;
                    case D:
                        this.emit(MOV_C_D);
                        break;
                    case Il:
                        this.emit(MOV_C_IL);
                        break;
                    case Ih:
                        this.emit(MOV_C_IH);
                        break;
                    default:
                        throw new RuntimeException("Invalid register " + src);
                }
                break;
            case D:
                switch (src) {
                    case A:
                        this.emit(MOV_D_A);
                        break;
                    case B:
                        this.emit(MOV_D_B);
                        break;
                    case C:
                        this.emit(MOV_D_C);
                        break;
                    case Il:
                        this.emit(MOV_D_IL);
                        break;
                    case Ih:
                        this.emit(MOV_D_IH);
                        break;
                    default:
                        throw new RuntimeException("Invalid register " + src);
                }
                break;
            case Il:
                switch (src) {
                    case A:
                        this.emit(MOV_IL_A);
                        break;
                    case B:
                        this.emit(MOV_IL_B);
                        break;
                    case C:
                        this.emit(MOV_IL_C);
                        break;
                    case D:
                        this.emit(MOV_IL_D);
                        break;
                    default:
                        throw new RuntimeException("Invalid register " + src);
                }
                break;
            case Ih:
                switch (src) {
                    case A:
                        this.emit(MOV_IH_A);
                        break;
                    case B:
                        this.emit(MOV_IH_B);
                        break;
                    case C:
                        this.emit(MOV_IH_C);
                        break;
                    case D:
                        this.emit(MOV_IH_D);
                        break;
                    default:
                        throw new RuntimeException("Invalid register " + src);
                }
                break;
            case PC:
                switch (src) {
                    case I:
                        this.emit(MOV_PC_I);
                        break;
                    default:
                        throw new RuntimeException("Invalid register " + src);
                }
                break;
            case I:
                switch (src) {
                    case PC:
                        this.emit(MOV_I_PC);
                        break;
                    case SP:
                        this.emit(MOV_I_SP);
                        break;
                    default:
                        throw new RuntimeException("Invalid register " + src);
                }
                break;
            case SP:
                switch (src) {
                    case I:
                        this.emit(MOV_SP_I);
                        break;
                    default:
                        throw new RuntimeException("Invalid register " + src);
                }
                break;
            default:
                throw new RuntimeException("Invalid register " + dest);
        }
    }

    public void ld(final Register r, final IAddress addr) {
        switch (r) {
            case A:
                this.emit(LD_A_M);
                break;
            case B:
                this.emit(LD_B_M);
                break;
            case C:
                this.emit(LD_C_M);
                break;
            case D:
                this.emit(LD_D_M);
                break;
            case Il:
                this.emit(LD_IL_M);
                break;
            case Ih:
                this.emit(LD_IH_M);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }

        final int pos = this.pos;
        this.pos += 2;
        this.patches.put(pos, addr);
    }

    public void st(final Register r, final IAddress addr) {
        switch (r) {
            case A:
                this.emit(ST_A_M);
                break;
            case B:
                this.emit(ST_B_M);
                break;
            case C:
                this.emit(ST_C_M);
                break;
            case D:
                this.emit(ST_D_M);
                break;
            case Il:
                this.emit(ST_IL_M);
                break;
            case Ih:
                this.emit(ST_IH_M);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }

        final int pos = this.pos;
        this.pos += 2;
        this.patches.put(pos, addr);
    }

    public void ld_i(final Register r) {
        switch (r) {
            case A:
                this.emit(LD_A_I);
                break;
            case B:
                this.emit(LD_B_I);
                break;
            case C:
                this.emit(LD_C_I);
                break;
            case D:
                this.emit(LD_D_I);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void st_i(final Register r) {
        switch (r) {
            case A:
                this.emit(ST_A_I);
                break;
            case B:
                this.emit(ST_B_I);
                break;
            case C:
                this.emit(ST_C_I);
                break;
            case D:
                this.emit(ST_D_I);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void jz(final IAddress addr) {
        this.emit(JZ_M);

        final int pos = this.pos;
        this.pos += 2;
        this.patches.put(pos, addr);
    }

    public void jc(final IAddress addr) {
        this.emit(JC_M);

        final int pos = this.pos;
        this.pos += 2;
        this.patches.put(pos, addr);
    }

    public void jmp(final IAddress addr) {
        this.emit(JMP_M);

        final int pos = this.pos;
        this.pos += 2;
        this.patches.put(pos, addr);
    }

    public void jz_i() {
        this.emit(JZ_I);
    }

    public void jc_i() {
        this.emit(JC_I);
    }

    public void jmp_i() {
        this.emit(JMP_I);
    }

    public void sez() {
        this.emit(SEZ);
    }

    public void sec() {
        this.emit(SEC);
    }

    public void clz() {
        this.emit(CLZ);
    }

    public void clc() {
        this.emit(CLC);
    }

    public void ldi(final Register r, final int value) {
        switch (r) {
            case A:
                this.emit(LDI_A);
                break;
            case B:
                this.emit(LDI_B);
                break;
            case C:
                this.emit(LDI_C);
                break;
            case D:
                this.emit(LDI_D);
                break;
            case Il:
                this.emit(LDI_IL);
                break;
            case Ih:
                this.emit(LDI_IH);
                break;
            case I:
                this.emit(LDI_I);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }

        if (r == Register.I) {
            if (value < 0 || value > 0xFFFF) throw new RuntimeException("Value out of range " + value);
            this.emit((value >> 8) & 0xFF);
            this.emit(value & 0xFF);
        } else {
            if (value < 0 || value > 0xFF) throw new RuntimeException("Value out of range " + value);
            this.emit(value);
        }
    }

    public void out(final Register r) {
        switch (r) {
            case A:
                this.emit(OUT_A);
                break;
            case B:
                this.emit(OUT_B);
                break;
            case C:
                this.emit(OUT_C);
                break;
            case D:
                this.emit(OUT_D);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void in(final Register r) {
        switch (r) {
            case A:
                this.emit(IN_A);
                break;
            case B:
                this.emit(IN_B);
                break;
            case C:
                this.emit(IN_C);
                break;
            case D:
                this.emit(IN_D);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void poll(final Register r) {
        switch (r) {
            case A:
                this.emit(POLL_A);
                break;
            case B:
                this.emit(POLL_B);
                break;
            case C:
                this.emit(POLL_C);
                break;
            case D:
                this.emit(POLL_D);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void push(final Register r) {
        switch (r) {
            case A:
                this.emit(PUSH_A);
                break;
            case B:
                this.emit(PUSH_B);
                break;
            case C:
                this.emit(PUSH_C);
                break;
            case D:
                this.emit(PUSH_D);
                break;
            case Il:
                this.emit(PUSH_IL);
                break;
            case Ih:
                this.emit(PUSH_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public void pop(final Register r) {
        switch (r) {
            case A:
                this.emit(POP_A);
                break;
            case B:
                this.emit(POP_B);
                break;
            case C:
                this.emit(POP_C);
                break;
            case D:
                this.emit(POP_D);
                break;
            case Il:
                this.emit(POP_IL);
                break;
            case Ih:
                this.emit(POP_IH);
                break;
            default:
                throw new RuntimeException("Invalid register " + r);
        }
    }

    public byte[] assemble() {
        for (final Map.Entry<Integer, IAddress> patch : this.patches.entrySet()) {
            final int pos = patch.getKey();
            final IAddress addr = patch.getValue();
            if (!addr.isResolved()) throw new RuntimeException("Unresolved patch at " + pos);

            final int v = addr.address();
            if (v < 0 || v > 0xFFFF) throw new RuntimeException("Address out of range " + v);

            this.memory[pos] = (byte) ((v >> 8) & 0xFF);
            this.memory[pos + 1] = (byte) (v & 0xFF);
        }

        return this.memory;
    }
}