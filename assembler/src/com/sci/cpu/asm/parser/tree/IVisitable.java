package com.sci.cpu.asm.parser.tree;

public interface IVisitable {
    void accept(final IVisitor visitor);
}