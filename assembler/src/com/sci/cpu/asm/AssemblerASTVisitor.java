package com.sci.cpu.asm;

import com.sci.cpu.asm.assembler.Assembler;
import com.sci.cpu.asm.assembler.IAddress;
import com.sci.cpu.asm.assembler.Label;
import com.sci.cpu.asm.parser.LabelST;
import com.sci.cpu.asm.parser.tree.FileST;
import com.sci.cpu.asm.parser.tree.IVisitor;
import com.sci.cpu.asm.parser.tree.directive.*;
import com.sci.cpu.asm.parser.tree.insn.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AssemblerASTVisitor implements IVisitor {
    private Assembler asm;
    private Map<String, Label> labels;
    private List<Label> toResolve;

    public AssemblerASTVisitor() {
        this.asm = new Assembler();
        this.labels = new HashMap<>();
        this.toResolve = new ArrayList<>();
    }

    public byte[] assemble(final FileST st) {
        this.visitFile(st);

        for (final Label label : this.toResolve) {
            label.resolveTo(this.labels.get(label.name));
        }

        return this.asm.assemble();
    }

    private void resolveIfNeeded(final IAddress addr) {
        if (addr instanceof Label) {
            final Label label = (Label) addr;
            if (!label.isResolved()) this.toResolve.add(label);
        }
    }

    @Override
    public void visitFile(final FileST st) {
        st.getItems().forEach(i -> i.accept(this));
    }

    @Override
    public void visitNop(final NopST st) {
        this.asm.nop();
    }

    @Override
    public void visitAdd(final AddST st) {
        this.asm.add(st.result);
    }

    @Override
    public void visitAdc(final AdcST st) {
        this.asm.adc(st.result);
    }

    @Override
    public void visitSub(final SubST st) {
        this.asm.sub(st.result);
    }

    @Override
    public void visitSbc(final SbcST st) {
        this.asm.sbc(st.result);
    }

    @Override
    public void visitAnd(final AndST st) {
        this.asm.and(st.result);
    }

    @Override
    public void visitOr(final OrST st) {
        this.asm.or(st.result);
    }

    @Override
    public void visitXor(final XorST st) {
        this.asm.xor(st.result);
    }

    @Override
    public void visitNot(final NotST st) {
        this.asm.not(st.result);
    }

    @Override
    public void visitLsh(final LshST st) {
        this.asm.lsh(st.result);
    }

    @Override
    public void visitRsh(final RshST st) {
        this.asm.rsh(st.result);
    }

    @Override
    public void visitMov(final MovST st) {
        this.asm.mov(st.dest, st.src);
    }

    @Override
    public void visitLd(final LdST st) {
        if (st.addr == null) this.asm.ld_i(st.dest);
        else this.asm.ld(st.dest, st.addr);
        this.resolveIfNeeded(st.addr);
    }

    @Override
    public void visitSt(final StST st) {
        if (st.addr == null) this.asm.st_i(st.src);
        else this.asm.st(st.src, st.addr);
        this.resolveIfNeeded(st.addr);
    }

    @Override
    public void visitJz(final JzST st) {
        if (st.addr == null) this.asm.jz_i();
        else this.asm.jz(st.addr);
        this.resolveIfNeeded(st.addr);
    }

    @Override
    public void visitJc(final JcST st) {
        if (st.addr == null) this.asm.jc_i();
        else this.asm.jc(st.addr);
        this.resolveIfNeeded(st.addr);
    }

    @Override
    public void visitJmp(final JmpST st) {
        if (st.addr == null) this.asm.jmp_i();
        else this.asm.jmp(st.addr);
        this.resolveIfNeeded(st.addr);
    }

    @Override
    public void visitSez(final SezST st) {
        this.asm.sez();
    }

    @Override
    public void visitSec(final SecST st) {
        this.asm.sec();
    }

    @Override
    public void visitClz(final ClzST st) {
        this.asm.clz();
    }

    @Override
    public void visitClc(final ClcST st) {
        this.asm.clc();
    }

    @Override
    public void visitLdi(final LdiST st) {
        this.asm.ldi(st.dest, st.value);
    }

    @Override
    public void visitOut(final OutST st) {
        this.asm.out(st.src);
    }

    @Override
    public void visitIn(final InST st) {
        this.asm.in(st.dest);
    }

    @Override
    public void visitPoll(final PollST st) {
        this.asm.poll(st.dest);
    }

    @Override
    public void visitPush(final PushST st) {
        this.asm.push(st.src);
    }

    @Override
    public void visitPop(final PopST st) {
        this.asm.pop(st.dest);
    }

    @Override
    public void visitLabel(final LabelST st) {
        Label label = this.labels.get(st.name);
        if (label == null) {
            label = new Label(st.name);
            this.labels.put(st.name, label);
        }

        this.asm.mark(label);
    }

    @Override
    public void visitDb(final DbST st) {
        for(final int i : st.getValues()) {
            this.asm.db(i);
        }
    }

    @Override
    public void visitDw(final DwST st) {
        for(final int i : st.getValues()) {
            this.asm.dw(i);
        }
    }

    @Override
    public void visitResB(final ResBST st) {
        for (int i = 0; i < st.value; i++) {
            this.asm.resb();
        }
    }

    @Override
    public void visitResW(final ResWST st) {
        for (int i = 0; i < st.value; i++) {
            this.asm.resw();
        }
    }

    @Override
    public void visitOrg(final OrgST st) {
        this.asm.org(st.address);
    }
}