package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.IAddress;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class JmpST extends InstructionST {
    public final IAddress addr;

    public JmpST() {
        this.addr = null;
    }

    public JmpST(final IAddress addr) {
        this.addr = addr;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitJmp(this);
    }
}