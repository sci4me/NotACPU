package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.Register;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class LshST extends InstructionST {
    public final Register result;

    public LshST(final Register result) {
        this.result = result;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitLsh(this);
    }
}