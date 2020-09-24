package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.IAddress;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class JzST extends InstructionST {
    public final IAddress addr;

    public JzST() {
        this.addr = null;
    }

    public JzST(final IAddress addr) {
        this.addr = addr;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitJz(this);
    }
}