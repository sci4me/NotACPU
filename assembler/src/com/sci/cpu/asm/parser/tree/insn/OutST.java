package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.Register;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class OutST extends InstructionST {
    public final Register src;

    public OutST(final Register src) {
        this.src = src;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitOut(this);
    }
}