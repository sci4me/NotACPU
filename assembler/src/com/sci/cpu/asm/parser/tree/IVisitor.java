package com.sci.cpu.asm.parser.tree;

import com.sci.cpu.asm.parser.LabelST;
import com.sci.cpu.asm.parser.tree.directive.*;
import com.sci.cpu.asm.parser.tree.insn.*;

public interface IVisitor {
    void visitFile(final FileST st);

    void visitNop(final NopST st);
    void visitAdd(final AddST st);
    void visitAdc(final AdcST st);
    void visitSub(final SubST st);
    void visitSbc(final SbcST st);
    void visitAnd(final AndST st);
    void visitOr(final OrST st);
    void visitXor(final XorST st);
    void visitNot(final NotST st);
    void visitLsh(final LshST st);
    void visitRsh(final RshST st);
    void visitMov(final MovST st);
    void visitLd(final LdST st);
    void visitSt(final StST st);
    void visitJz(final JzST st);
    void visitJc(final JcST st);
    void visitJmp(final JmpST st);
    void visitSez(final SezST st);
    void visitSec(final SecST st);
    void visitClz(final ClzST st);
    void visitClc(final ClcST st);
    void visitLdi(final LdiST st);
    void visitOut(final OutST st);
    void visitIn(final InST st);
    void visitPoll(final PollST st);
    void visitPush(final PushST st);
    void visitPop(final PopST st);

    void visitLabel(final LabelST st);

    void visitDb(final DbST st);
    void visitDw(final DwST st);
    void visitResB(final ResBST st);
    void visitResW(final ResWST st);
    void visitOrg(final OrgST st);
}