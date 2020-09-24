package com.sci.cpu.asm.parser.tree.insn;

import com.sci.cpu.asm.assembler.IAddress;
import com.sci.cpu.asm.assembler.Register;
import com.sci.cpu.asm.parser.tree.IVisitor;

public final class StST extends InstructionST {
    public final Register src;
    public final IAddress addr;

    public StST(final Register src) {
        this.src = src;
        this.addr = null;
    }

    public StST(final Register src, final IAddress addr) {
        this.src = src;
        this.addr = addr;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitSt(this);
    }
}