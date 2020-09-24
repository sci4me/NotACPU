package com.sci.cpu.asm.assembler;

public interface IAddress {
    boolean isResolved();

    int address();
}