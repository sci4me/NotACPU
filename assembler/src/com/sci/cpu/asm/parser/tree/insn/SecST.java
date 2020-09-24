package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.parser.tree.IVisitor;

public final class SecST extends InstructionST {
    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitSec(this);
    }
}