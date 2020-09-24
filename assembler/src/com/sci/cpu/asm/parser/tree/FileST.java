package com.sci.cpu.asm.parser.tree;

import java.util.ArrayList;
import java.util.List;

public final class FileST extends SyntaxTree {
    private final List<ItemST> items;

    public FileST() {
        this.items = new ArrayList<>();
    }

    public void addItem(final ItemST st) {
        this.items.add(st);
    }

    public List<ItemST> getItems() {
        return this.items;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visitFile(this);
    }
}