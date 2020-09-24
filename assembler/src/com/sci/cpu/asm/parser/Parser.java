package com.sci.cpu.asm.parser;

import com.sci.cpu.asm.assembler.AbsoluteAddress;
import com.sci.cpu.asm.assembler.Label;
import com.sci.cpu.asm.assembler.Register;
import com.sci.cpu.asm.lexer.Token;
import com.sci.cpu.asm.lexer.TokenType;
import com.sci.cpu.asm.parser.tree.FileST;
import com.sci.cpu.asm.parser.tree.ItemST;
import com.sci.cpu.asm.parser.tree.directive.*;
import com.sci.cpu.asm.parser.tree.insn.*;

import java.util.List;

public final class Parser {
    private final List<Token> tokens;
    private int index;

    public Parser(final List<Token> tokens) {
        this.tokens = tokens;
    }

    private void next() {
        this.index++;
    }

    private Token current() {
        return this.tokens.get(this.index);
    }

    private boolean more() {
        return this.index < this.tokens.size();
    }

    private boolean accept(final TokenType... types) {
        if (!this.more()) {
            return false;
        }

        for (int i = 0; i < types.length; i++) {
            if (this.current().type == types[i]) {
                return true;
            }
        }

        return false;
    }

    private boolean acceptNext(final TokenType... types) {
        if (this.accept(types)) {
            this.next();
            return true;
        }
        return false;
    }

    private void expect(final TokenType... types) {
        if (!this.accept(types)) {
            final StringBuilder possibilities = new StringBuilder();

            for (final TokenType type : types) {
                possibilities.append(type);
                possibilities.append(" ");
            }

            throw new RuntimeException("Unexpected token: " + this.current() + " Possibilities: " + possibilities);
        }
    }

    private void expectNext(final TokenType... types) {
        this.expect(types);
        this.next();
    }

    private void expectNot(final TokenType... types) {
        if (this.accept(types)) {
            throw new RuntimeException("Unexpected token: " + this.current());
        }
    }

    private Register parseRegister() {
        final Register result;

        switch (this.current().type) {
            case REGISTER_A:
                result = Register.A;
                break;
            case REGISTER_B:
                result = Register.B;
                break;
            case REGISTER_C:
                result = Register.C;
                break;
            case REGISTER_D:
                result = Register.D;
                break;
            case REGISTER_I:
                result = Register.I;
                break;
            case REGISTER_IL:
                result = Register.Il;
                break;
            case REGISTER_IH:
                result = Register.Ih;
                break;
            case REGISTER_PC:
                result = Register.PC;
                break;
            case REGISTER_SP:
                result = Register.SP;
                break;
            default:
                throw new RuntimeException(this.current().toString());
        }

        this.next();

        return result;
    }

    private int parseInteger() {
        this.expect(TokenType.DECIMAL_INTEGER, TokenType.BINARY_INTEGER, TokenType.HEX_INTEGER);
        int result;

        if (this.accept(TokenType.DECIMAL_INTEGER)) result = Integer.parseInt(this.current().text, 10);
        else if (this.accept(TokenType.BINARY_INTEGER)) result = Integer.parseInt(this.current().text.substring(2), 2);
        else result = Integer.parseInt(this.current().text.substring(2), 16);

        this.next();

        return result;
    }

    private ItemST parseItem() {
        this.expect(TokenType.NOP, TokenType.ADD, TokenType.ADC, TokenType.SUB, TokenType.SBC, TokenType.AND,
                TokenType.OR, TokenType.XOR, TokenType.NOT, TokenType.LSH, TokenType.RSH, TokenType.MOV,
                TokenType.LD, TokenType.ST, TokenType.JZ, TokenType.JC, TokenType.JMP, TokenType.SEZ,
                TokenType.SEC, TokenType.CLZ, TokenType.CLC, TokenType.LDI, TokenType.OUT, TokenType.IN,
                TokenType.POLL, TokenType.PUSH, TokenType.POP, TokenType.LABEL, TokenType.DIRECTIVE_DB,
                TokenType.DIRECTIVE_DW, TokenType.DIRECTIVE_RESB, TokenType.DIRECTIVE_RESW, TokenType.DIRECTIVE_ORG);
        if (this.acceptNext(TokenType.NOP)) {
            return new NopST();
        } else if (this.acceptNext(TokenType.ADD)) {
            return new AddST(this.parseRegister());
        } else if (this.acceptNext(TokenType.ADC)) {
            return new AdcST(this.parseRegister());
        } else if (this.acceptNext(TokenType.SUB)) {
            return new SubST(this.parseRegister());
        } else if (this.acceptNext(TokenType.SBC)) {
            return new SbcST(this.parseRegister());
        } else if (this.acceptNext(TokenType.AND)) {
            return new AndST(this.parseRegister());
        } else if (this.acceptNext(TokenType.OR)) {
            return new OrST(this.parseRegister());
        } else if (this.acceptNext(TokenType.XOR)) {
            return new XorST(this.parseRegister());
        } else if (this.acceptNext(TokenType.NOT)) {
            return new NotST(this.parseRegister());
        } else if (this.acceptNext(TokenType.LSH)) {
            return new LshST(this.parseRegister());
        } else if (this.acceptNext(TokenType.RSH)) {
            return new RshST(this.parseRegister());
        } else if (this.acceptNext(TokenType.MOV)) {
            return new MovST(this.parseRegister(), this.parseRegister());
        } else if (this.acceptNext(TokenType.LD)) {
            final Register r = this.parseRegister();

            if (this.accept(TokenType.IDENT)) {
                final LdST st = new LdST(r, new Label(this.current().text));
                this.next();
                return st;
            } else if (this.accept(TokenType.DECIMAL_INTEGER, TokenType.BINARY_INTEGER, TokenType.HEX_INTEGER)) {
                return new LdST(r, new AbsoluteAddress(this.parseInteger()));
            } else {
                this.expectNext(TokenType.REGISTER_I);
                return new LdST(r);
            }
        } else if (this.acceptNext(TokenType.ST)) {
            final Register r = this.parseRegister();

            if (this.accept(TokenType.IDENT)) {
                final StST st = new StST(r, new Label(this.current().text));
                this.next();
                return st;
            } else if (this.accept(TokenType.DECIMAL_INTEGER, TokenType.BINARY_INTEGER, TokenType.HEX_INTEGER)) {
                return new StST(r, new AbsoluteAddress(this.parseInteger()));
            } else {
                this.expectNext(TokenType.REGISTER_I);
                return new StST(r);
            }
        } else if (this.acceptNext(TokenType.JZ)) {
            if (this.accept(TokenType.IDENT)) {
                final JzST st = new JzST(new Label(this.current().text));
                this.next();
                return st;
            } else if (this.accept(TokenType.DECIMAL_INTEGER, TokenType.BINARY_INTEGER, TokenType.HEX_INTEGER)) {
                return new JzST(new AbsoluteAddress(this.parseInteger()));
            } else {
                this.expectNext(TokenType.REGISTER_I);
                return new JzST();
            }
        } else if (this.acceptNext(TokenType.JC)) {
            if (this.accept(TokenType.IDENT)) {
                final JcST st = new JcST(new Label(this.current().text));
                this.next();
                return st;
            } else if (this.accept(TokenType.DECIMAL_INTEGER, TokenType.BINARY_INTEGER, TokenType.HEX_INTEGER)) {
                return new JcST(new AbsoluteAddress(this.parseInteger()));
            } else {
                this.expectNext(TokenType.REGISTER_I);
                return new JcST();
            }
        } else if (this.acceptNext(TokenType.JMP)) {
            if (this.accept(TokenType.IDENT)) {
                final JmpST st = new JmpST(new Label(this.current().text));
                this.next();
                return st;
            } else if (this.accept(TokenType.DECIMAL_INTEGER, TokenType.BINARY_INTEGER, TokenType.HEX_INTEGER)) {
                return new JmpST(new AbsoluteAddress(this.parseInteger()));
            } else {
                this.expectNext(TokenType.REGISTER_I);
                return new JmpST();
            }
        } else if (this.acceptNext(TokenType.SEZ)) {
            return new SezST();
        } else if (this.acceptNext(TokenType.SEC)) {
            return new SecST();
        } else if (this.acceptNext(TokenType.CLZ)) {
            return new ClzST();
        } else if (this.acceptNext(TokenType.CLC)) {
            return new ClcST();
        } else if (this.acceptNext(TokenType.LDI)) {
            return new LdiST(this.parseRegister(), this.parseInteger());
        } else if (this.acceptNext(TokenType.OUT)) {
            return new OutST(this.parseRegister());
        } else if (this.acceptNext(TokenType.IN)) {
            return new InST(this.parseRegister());
        } else if (this.acceptNext(TokenType.POLL)) {
            return new PollST(this.parseRegister());
        } else if (this.acceptNext(TokenType.PUSH)) {
            return new PushST(this.parseRegister());
        } else if (this.acceptNext(TokenType.POP)) {
            return new PopST(this.parseRegister());
        } else if (this.accept(TokenType.LABEL)) {
            final String name = this.current().text;
            final LabelST st = new LabelST(name.substring(0, name.length() - 1));
            this.next();
            return st;
        } else if (this.acceptNext(TokenType.DIRECTIVE_DB)) {
            final DbST st = new DbST();

            while(this.accept(TokenType.DECIMAL_INTEGER, TokenType.BINARY_INTEGER, TokenType.HEX_INTEGER)) {
                st.add(this.parseInteger());

                if(this.accept(TokenType.COMMA)) {
                    this.next();
                    this.expect(TokenType.DECIMAL_INTEGER, TokenType.BINARY_INTEGER, TokenType.HEX_INTEGER);
                }
            }

            return st;
        } else if (this.acceptNext(TokenType.DIRECTIVE_DW)) {
            final DwST st = new DwST();

            while(this.accept(TokenType.DECIMAL_INTEGER, TokenType.BINARY_INTEGER, TokenType.HEX_INTEGER)) {
                st.add(this.parseInteger());

                if(this.accept(TokenType.COMMA)) {
                    this.next();
                    this.expect(TokenType.DECIMAL_INTEGER, TokenType.BINARY_INTEGER, TokenType.HEX_INTEGER);
                }
            }

            return st;
        } else if (this.acceptNext(TokenType.DIRECTIVE_RESB)) {
            return new ResBST(this.parseInteger());
        } else if (this.acceptNext(TokenType.DIRECTIVE_RESW)) {
            return new ResWST(this.parseInteger());
        } else if (this.acceptNext(TokenType.DIRECTIVE_ORG)) {
            return new OrgST(this.parseInteger());
        }

        throw new RuntimeException(this.current().toString());
    }

    public FileST parse() {
        final FileST st = new FileST();

        while (this.more()) {
            st.addItem(this.parseItem());
        }

        return st;
    }
}