package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.Register;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class PushST extends InstructionST {
    public final Register src;

    public PushST(final Register src) {
        this.src = src;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitPush(this);
    }
}