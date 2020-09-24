package com.sci.cpu.asm.assembler;

public final class AbsoluteAddress implements IAddress {
    private final int address;

    public AbsoluteAddress(final int address) {
        this.address = address;
    }

    @Override
    public boolean isResolved() {
        return true;
    }

    @Override
    public int address() {
        return this.address;
    }
}