package com.sci.cpu.asm.parser.tree.directive;

import com.sci.cpu.asm.parser.tree.IVisitor;

public final class ResWST extends DirectiveST {
    public final int value;

    public ResWST(final int value) {
        this.value = value;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitResW(this);
    }
}