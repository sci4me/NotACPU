package com.sci.cpu.asm.parser.tree.directive;

import com.sci.cpu.asm.parser.tree.IVisitor;

public final class ResBST extends DirectiveST {
    public final int value;

    public ResBST(final int value) {
        this.value = value;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitResB(this);
    }
}