package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.IAddress;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class JcST extends InstructionST {
    public final IAddress addr;

    public JcST() {
        this.addr = null;
    }

    public JcST(final IAddress addr) {
        this.addr = addr;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitJc(this);
    }
}