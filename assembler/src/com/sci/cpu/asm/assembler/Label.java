package com.sci.cpu.asm.assembler;

public final class Label implements IAddress {
    public final String name;

    private boolean resolved;
    private int address;

    public Label(final String name) {
        this.name = name;
    }

    void resolve(final int address) {
        this.resolved = true;
        this.address = address;
    }

    public void resolveTo(final Label label) {
        this.resolved = true;
        this.address = label.address;
    }

    @Override
    public boolean isResolved() {
        return this.resolved;
    }

    @Override
    public int address() {
        return this.address;
    }
}