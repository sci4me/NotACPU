package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.Register;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class MovST extends InstructionST {
    public final Register dest;
    public final Register src;

    public MovST(final Register dest, final Register src) {
        this.dest = dest;
        this.src = src;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitMov(this);
    }
}