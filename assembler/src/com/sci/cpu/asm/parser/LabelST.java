package com.sci.cpu.asm.parser;

import com.sci.cpu.asm.parser.tree.IVisitor;
import com.sci.cpu.asm.parser.tree.ItemST;

public final class LabelST extends ItemST {
    public final String name;

    public LabelST(final String name) {
        this.name = name;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitLabel(this);
    }
}