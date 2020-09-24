package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.IAddress;
import com.sci.cpu.asm.assembler.Register;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class LdST extends InstructionST {
    public final Register dest;
    public final IAddress addr;

    public LdST(final Register dest) {
        this.dest = dest;
        this.addr = null;
    }

    public LdST(final Register dest, final IAddress addr) {
        this.dest = dest;
        this.addr = addr;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitLd(this);
    }
}