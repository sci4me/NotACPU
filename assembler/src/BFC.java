import com.sci.cpu.asm.assembler.Assembler;
import com.sci.cpu.asm.assembler.Label;
import com.sci.cpu.asm.assembler.Register;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class BFC {
    public static final int ADD = 0;
    public static final int SUB = 1;
    public static final int RIGHT = 2;
    public static final int LEFT = 3;
    public static final int OUT = 4;
    public static final int IN = 5;
    public static final int OPEN = 6;
    public static final int CLOSE = 7;
    public static final int CLEAR = 8;

    private static class Insn {
        public final int opcode;
        public final int operand;

        public Insn(final int opcode, final int operand) {
            this.opcode = opcode;
            this.operand = operand;
        }
    }

    private static int toOpcode(final char c) {
        switch (c) {
            case '+':
                return ADD;
            case '-':
                return SUB;
            case '>':
                return RIGHT;
            case '<':
                return LEFT;
            case '.':
                return OUT;
            case ',':
                return IN;
            case '[':
                return OPEN;
            case ']':
                return CLOSE;
        }
        throw new RuntimeException();
    }

    private static List<Insn> parse(final String file) throws Throwable {
        final String code = new String(Files.readAllBytes(Paths.get(file)));
        final List<Insn> insns = new ArrayList<>();

        int index = 0;
        while (index < code.length()) {
            final char c = code.charAt(index);

            if (c != '+' && c != '-' && c != '>' && c != '<' && c != '.' && c != ',' && c != '[' && c != ']') {
                index++;
                continue;
            }

            if (c == '+' || c == '-' || c == '>' || c == '<' || c == '.') {
                int count = 1;

                while (index + count < code.length() && code.charAt(index + count) == c && count < 255) {
                    count++;
                }

                insns.add(new Insn(toOpcode(c), count));
                index += count;
            } else if (c == '[' && index + 2 < code.length() && code.charAt(index + 1) == '-' && code.charAt(index + 2) == ']') {
                insns.add(new Insn(CLEAR, 1));
                index += 3;
            } else {
                insns.add(new Insn(toOpcode(c), 1));
                index++;
            }
        }

        return insns;
    }

    public static void main(final String[] args) throws Throwable {
        if (args.length != 1) {
            System.out.println("Usage: bf <file>");
            return;
        }

        final List<Insn> insns = parse(args[0]);

        final Stack<Label> loopStarts = new Stack<>();
        final Stack<Label> loopEnds = new Stack<>();

        final Assembler asm = new Assembler();

        asm.resb();
        asm.resw();

        for (int i = 0; i < insns.size(); i++) {
            final Insn insn = insns.get(i);
            switch (insn.opcode) {
                case ADD: {
                    asm.ld_i(Register.A);
                    asm.ldi(Register.B, insn.operand);
                    asm.add(Register.C);
                    asm.st_i(Register.C);
                    break;
                }
                case SUB: {
                    asm.ld_i(Register.A);
                    asm.ldi(Register.B, insn.operand);
                    asm.sub(Register.C);
                    asm.st_i(Register.C);
                    break;
                }
                case RIGHT: {
                    asm.mov(Register.A, Register.Il);
                    asm.ldi(Register.B, insn.operand & 0xFF);
                    asm.add(Register.Il);

                    asm.mov(Register.A, Register.Ih);
                    asm.ldi(Register.B, (insn.operand >> 8) & 0xFF);
                    asm.adc(Register.Ih);
                    break;
                }
                case LEFT: {
                    asm.mov(Register.A, Register.Il);
                    asm.ldi(Register.B, insn.operand & 0xFF);
                    asm.sub(Register.Il);

                    asm.mov(Register.A, Register.Ih);
                    asm.ldi(Register.B, (insn.operand >> 8) & 0xFF);
                    asm.sbc(Register.Ih);
                    break;
                }
                case OUT: {
                    asm.ld_i(Register.D);
                    for (int j = 0; j < insn.operand; j++) asm.out(Register.D);
                    break;
                }
                case IN: {
                    asm.in(Register.A);
                    asm.st_i(Register.A);
                    break;
                }
                case OPEN: {
                    final Label loopStart = new Label("loopStart");
                    final Label loopEnd = new Label("loopEnd");
                    loopStarts.push(loopStart);
                    loopEnds.push(loopEnd);

                    asm.ld_i(Register.A);
                    asm.ldi(Register.B, 0);
                    asm.sub(Register.C);
                    asm.jz(loopEnd);

                    asm.mark(loopStart);
                    break;
                }
                case CLOSE: {
                    final Label loopStart = loopStarts.pop();
                    final Label loopEnd = loopEnds.pop();

                    asm.ld_i(Register.A);
                    asm.ldi(Register.B, 0);
                    asm.sub(Register.C);
                    asm.jz(loopEnd);
                    asm.jmp(loopStart);

                    asm.mark(loopEnd);
                    break;
                }
                case CLEAR: {
                    asm.ldi(Register.A, 0);
                    asm.st_i(Register.A);
                    break;
                }
            }
        }

        final Label loop = new Label("loop");
        asm.mark(loop);
        asm.jmp(loop);

        final int data = asm.pos();
        asm.org(0);
        asm.ldi(Register.I, data);

        final byte[] memory = asm.assemble();
        final PrintWriter writer = new PrintWriter(new FileWriter("out.txt"));
        writer.println("v2.0 raw");
        for (final byte b : memory) {
            writer.printf("%x ", b);
        }
        writer.flush();
        writer.close();
    }

    private BFC() {
    }
}