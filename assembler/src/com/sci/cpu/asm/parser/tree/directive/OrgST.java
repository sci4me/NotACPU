package com.sci.cpu.asm.parser.tree.directive;

import com.sci.cpu.asm.parser.tree.IVisitor;

public final class OrgST extends DirectiveST {
    public final int address;

    public OrgST(final int address) {
        this.address = address;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitOrg(this);
    }
}