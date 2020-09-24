package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.Register;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class PopST extends InstructionST {
    public final Register dest;

    public PopST(final Register dest) {
        this.dest = dest;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitPop(this);
    }
}