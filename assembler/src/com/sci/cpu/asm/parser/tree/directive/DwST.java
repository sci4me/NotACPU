package com.sci.cpu.asm.parser.tree.directive;

import java.util.*;

import com.sci.cpu.asm.parser.tree.IVisitor;

public final class DwST extends DirectiveST {
    private final List<Integer> values;

    public DwST() {
    	this.values = new ArrayList<>();
    }

    public void add(final int value) {
        if (value < 0 || value > 0xFFFF) throw new RuntimeException("Value out of range " + value);
    	this.values.add(value);
    }

    public List<Integer> getValues() {
        return this.values;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitDw(this);
    }
}