package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.parser.tree.IVisitor;

public final class SezST extends InstructionST {
    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitSez(this);
    }
}