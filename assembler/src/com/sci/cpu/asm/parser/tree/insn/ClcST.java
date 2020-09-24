package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.parser.tree.IVisitor;

public final class ClcST extends InstructionST {
    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitClc(this);
    }
}