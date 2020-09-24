package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.Register;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class LdiST extends InstructionST {
    public final Register dest;
    public final int value;

    public LdiST(final Register dest, final int value) {
        this.dest = dest;
        this.value = value;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitLdi(this);
    }
}