import java.io.FileWriter;
import java.io.PrintWriter;

public final class Main {
    private static final long STA = 1L << 0;
    private static final long LDA = 1L << 1;
    private static final long STB = 1L << 2;
    private static final long LDB = 1L << 3;
    private static final long STC = 1L << 4;
    private static final long LDC = 1L << 5;
    private static final long STD = 1L << 6;
    private static final long LDD = 1L << 7;
    private static final long STIA = 1L << 8;
    private static final long STIB = 1L << 9;
    private static final long STI = 1L << 10;
    private static final long LDIA = 1L << 11;
    private static final long LDIB = 1L << 12;
    private static final long LDI = 1L << 13;
    private static final long STPC = 1L << 14;
    private static final long LDPC = 1L << 15;
    private static final long PCCE = 1L << 16;
    private static final long STINSN = 1L << 17;
    private static final long URESET = 1L << 18;
    private static final long LDMAR = 1L << 19;
    private static final long STMARA = 1L << 20;
    private static final long STMARB = 1L << 21;
    private static final long CIN = 1L << 22;
    private static final long INVB = 1L << 23;
    private static final long STZF = 1L << 24;
    private static final long SEZF = 1L << 25;
    private static final long CLZF = 1L << 26;
    private static final long STCF = 1L << 27;
    private static final long SECF = 1L << 28;
    private static final long CLCF = 1L << 29;
    private static final long OE_ALU = 1L << 30;
    private static final long OE_NOT = 1L << 31;
    private static final long OE_XOR = 1L << 32;
    private static final long OE_OR = 1L << 33;
    private static final long OE_AND = 1L << 34;
    private static final long OE_LSH = 1L << 35;
    private static final long OE_RSH = 1L << 36;
    private static final long RAMREAD = 1L << 37;
    private static final long RAMWRITE = 1L << 38;
    private static final long IN = 1L << 39;
    private static final long OUT = 1L << 40;
    private static final long POLL = 1L << 41;
    private static final long LDSP = 1L << 42;
    private static final long STSP = 1L << 43;
    private static final long SPCE = 1L << 44;
    private static final long SPCD = 1L << 45;

    private static final long FETCH = LDPC | RAMREAD | STINSN;

    private static final int FLAGS_C0Z0 = 0;
    private static final int FLAGS_C0Z1 = 1;
    private static final int FLAGS_C1Z0 = 2;
    private static final int FLAGS_C1Z1 = 3;

    private static final int ADC_A = 0x07;
    private static final int ADC_B = 0x08;
    private static final int ADC_C = 0x09;
    private static final int ADC_D = 0x0A;
    private static final int ADC_IL = 0x0B;
    private static final int ADC_IH = 0x0C;
    private static final int SBC_A = 0x13;
    private static final int SBC_B = 0x14;
    private static final int SBC_C = 0x15;
    private static final int SBC_D = 0x16;
    private static final int SBC_IL = 0x17;
    private static final int SBC_IH = 0x18;
    private static final int JZ_M = 0x71;
    private static final int JC_M = 0x72;
    private static final int JZ_I = 0x74;
    private static final int JC_I = 0x75;

    private static final long[][] TEMPLATE = new long[][]{
            /* NOP      */ {FETCH, PCCE, URESET, 0, 0, 0, 0, 0},

            /* ADD A    */ {FETCH, PCCE | STZF | STCF | OE_ALU | STA, URESET, 0, 0, 0, 0, 0},
            /* ADD B    */ {FETCH, PCCE | STZF | STCF | OE_ALU | STB, URESET, 0, 0, 0, 0, 0},
            /* ADD C    */ {FETCH, PCCE | STZF | STCF | OE_ALU | STC, URESET, 0, 0, 0, 0, 0},
            /* ADD D    */ {FETCH, PCCE | STZF | STCF | OE_ALU | STD, URESET, 0, 0, 0, 0, 0},
            /* ADD Il   */ {FETCH, PCCE | STZF | STCF | OE_ALU | STIA, URESET, 0, 0, 0, 0, 0},
            /* ADD Ih   */ {FETCH, PCCE | STZF | STCF | OE_ALU | STIB, URESET, 0, 0, 0, 0, 0},
            /* ADC A    */ {FETCH, PCCE | STZF | STCF | OE_ALU | STA, URESET, 0, 0, 0, 0, 0},
            /* ADC B    */ {FETCH, PCCE | STZF | STCF | OE_ALU | STB, URESET, 0, 0, 0, 0, 0},
            /* ADC C    */ {FETCH, PCCE | STZF | STCF | OE_ALU | STC, URESET, 0, 0, 0, 0, 0},
            /* ADC D    */ {FETCH, PCCE | STZF | STCF | OE_ALU | STD, URESET, 0, 0, 0, 0, 0},
            /* ADC Il   */ {FETCH, PCCE | STZF | STCF | OE_ALU | STIA, URESET, 0, 0, 0, 0, 0},
            /* ADC Ih   */ {FETCH, PCCE | STZF | STCF | OE_ALU | STIB, URESET, 0, 0, 0, 0, 0},
            /* SUB A    */ {FETCH, PCCE | STZF | STCF | CIN | INVB | OE_ALU | STA, URESET, 0, 0, 0, 0, 0},
            /* SUB B    */ {FETCH, PCCE | STZF | STCF | CIN | INVB | OE_ALU | STB, URESET, 0, 0, 0, 0, 0},
            /* SUB C    */ {FETCH, PCCE | STZF | STCF | CIN | INVB | OE_ALU | STC, URESET, 0, 0, 0, 0, 0},
            /* SUB D    */ {FETCH, PCCE | STZF | STCF | CIN | INVB | OE_ALU | STD, URESET, 0, 0, 0, 0, 0},
            /* SUB Il   */ {FETCH, PCCE | STZF | STCF | CIN | INVB | OE_ALU | STIA, URESET, 0, 0, 0, 0, 0},
            /* SUB Il   */ {FETCH, PCCE | STZF | STCF | CIN | INVB | OE_ALU | STIB, URESET, 0, 0, 0, 0, 0},
            /* SBC A    */ {FETCH, PCCE | STZF | STCF | INVB | OE_ALU | STA, URESET, 0, 0, 0, 0, 0},
            /* SBC B    */ {FETCH, PCCE | STZF | STCF | INVB | OE_ALU | STB, URESET, 0, 0, 0, 0, 0},
            /* SBC C    */ {FETCH, PCCE | STZF | STCF | INVB | OE_ALU | STC, URESET, 0, 0, 0, 0, 0},
            /* SBC D    */ {FETCH, PCCE | STZF | STCF | INVB | OE_ALU | STD, URESET, 0, 0, 0, 0, 0},
            /* SBC Il   */ {FETCH, PCCE | STZF | STCF | INVB | OE_ALU | STIA, URESET, 0, 0, 0, 0, 0},
            /* SBC Ih   */ {FETCH, PCCE | STZF | STCF | INVB | OE_ALU | STIB, URESET, 0, 0, 0, 0, 0},

            /* AND A    */ {FETCH, PCCE | STZF | OE_AND | STA, URESET, 0, 0, 0, 0, 0},
            /* AND B    */ {FETCH, PCCE | STZF | OE_AND | STB, URESET, 0, 0, 0, 0, 0},
            /* AND C    */ {FETCH, PCCE | STZF | OE_AND | STC, URESET, 0, 0, 0, 0, 0},
            /* AND D    */ {FETCH, PCCE | STZF | OE_AND | STD, URESET, 0, 0, 0, 0, 0},
            /* AND Il   */ {FETCH, PCCE | STZF | OE_AND | STIA, URESET, 0, 0, 0, 0, 0},
            /* AND Ih   */ {FETCH, PCCE | STZF | OE_AND | STIB, URESET, 0, 0, 0, 0, 0},
            /* OR A     */ {FETCH, PCCE | STZF | OE_OR | STA, URESET, 0, 0, 0, 0, 0},
            /* OR B     */ {FETCH, PCCE | STZF | OE_OR | STB, URESET, 0, 0, 0, 0, 0},
            /* OR C     */ {FETCH, PCCE | STZF | OE_OR | STC, URESET, 0, 0, 0, 0, 0},
            /* OR D     */ {FETCH, PCCE | STZF | OE_OR | STD, URESET, 0, 0, 0, 0, 0},
            /* OR Il    */ {FETCH, PCCE | STZF | OE_OR | STIA, URESET, 0, 0, 0, 0, 0},
            /* OR Ih    */ {FETCH, PCCE | STZF | OE_OR | STIB, URESET, 0, 0, 0, 0, 0},
            /* XOR A    */ {FETCH, PCCE | STZF | OE_XOR | STA, URESET, 0, 0, 0, 0, 0},
            /* XOR B    */ {FETCH, PCCE | STZF | OE_XOR | STB, URESET, 0, 0, 0, 0, 0},
            /* XOR C    */ {FETCH, PCCE | STZF | OE_XOR | STC, URESET, 0, 0, 0, 0, 0},
            /* XOR D    */ {FETCH, PCCE | STZF | OE_XOR | STD, URESET, 0, 0, 0, 0, 0},
            /* XOR Il   */ {FETCH, PCCE | STZF | OE_XOR | STIA, URESET, 0, 0, 0, 0, 0},
            /* XOR Ih   */ {FETCH, PCCE | STZF | OE_XOR | STIB, URESET, 0, 0, 0, 0, 0},
            /* NOT A    */ {FETCH, PCCE | STZF | OE_NOT | STA, URESET, 0, 0, 0, 0, 0},
            /* NOT B    */ {FETCH, PCCE | STZF | OE_NOT | STB, URESET, 0, 0, 0, 0, 0},
            /* NOT C    */ {FETCH, PCCE | STZF | OE_NOT | STC, URESET, 0, 0, 0, 0, 0},
            /* NOT D    */ {FETCH, PCCE | STZF | OE_NOT | STD, URESET, 0, 0, 0, 0, 0},
            /* NOT Il   */ {FETCH, PCCE | STZF | OE_NOT | STIA, URESET, 0, 0, 0, 0, 0},
            /* NOT Ih   */ {FETCH, PCCE | STZF | OE_NOT | STIB, URESET, 0, 0, 0, 0, 0},

            /* LSH A    */ {FETCH, PCCE | STZF | STCF | OE_LSH | STA, URESET, 0, 0, 0, 0, 0},
            /* LSH B    */ {FETCH, PCCE | STZF | STCF | OE_LSH | STB, URESET, 0, 0, 0, 0, 0},
            /* LSH C    */ {FETCH, PCCE | STZF | STCF | OE_LSH | STC, URESET, 0, 0, 0, 0, 0},
            /* LSH D    */ {FETCH, PCCE | STZF | STCF | OE_LSH | STD, URESET, 0, 0, 0, 0, 0},
            /* LSH Il   */ {FETCH, PCCE | STZF | STCF | OE_LSH | STIA, URESET, 0, 0, 0, 0, 0},
            /* LSH Ih   */ {FETCH, PCCE | STZF | STCF | OE_LSH | STIB, URESET, 0, 0, 0, 0, 0},
            /* RSH A    */ {FETCH, PCCE | STZF | STCF | OE_RSH | STA, URESET, 0, 0, 0, 0, 0},
            /* RSH B    */ {FETCH, PCCE | STZF | STCF | OE_RSH | STB, URESET, 0, 0, 0, 0, 0},
            /* RSH C    */ {FETCH, PCCE | STZF | STCF | OE_RSH | STC, URESET, 0, 0, 0, 0, 0},
            /* RSH D    */ {FETCH, PCCE | STZF | STCF | OE_RSH | STD, URESET, 0, 0, 0, 0, 0},
            /* RSH Il   */ {FETCH, PCCE | STZF | STCF | OE_RSH | STIA, URESET, 0, 0, 0, 0, 0},
            /* RSH Ih   */ {FETCH, PCCE | STZF | STCF | OE_RSH | STIB, URESET, 0, 0, 0, 0, 0},

            /* MOV A B  */ {FETCH, PCCE | LDB | STA, URESET, 0, 0, 0, 0, 0},
            /* MOV A C  */ {FETCH, PCCE | LDC | STA, URESET, 0, 0, 0, 0, 0},
            /* MOV A D  */ {FETCH, PCCE | LDD | STA, URESET, 0, 0, 0, 0, 0},
            /* MOV B A  */ {FETCH, PCCE | LDA | STB, URESET, 0, 0, 0, 0, 0},
            /* MOV B C  */ {FETCH, PCCE | LDC | STB, URESET, 0, 0, 0, 0, 0},
            /* MOV B D  */ {FETCH, PCCE | LDD | STB, URESET, 0, 0, 0, 0, 0},
            /* MOV C A  */ {FETCH, PCCE | LDA | STC, URESET, 0, 0, 0, 0, 0},
            /* MOV C B  */ {FETCH, PCCE | LDB | STC, URESET, 0, 0, 0, 0, 0},
            /* MOV C D  */ {FETCH, PCCE | LDD | STC, URESET, 0, 0, 0, 0, 0},
            /* MOV D A  */ {FETCH, PCCE | LDA | STD, URESET, 0, 0, 0, 0, 0},
            /* MOV D B  */ {FETCH, PCCE | LDB | STD, URESET, 0, 0, 0, 0, 0},
            /* MOV D C  */ {FETCH, PCCE | LDC | STD, URESET, 0, 0, 0, 0, 0},

            /* MOV Il A */ {FETCH, PCCE | LDA | STIA, URESET, 0, 0, 0, 0, 0},
            /* MOV Il B */ {FETCH, PCCE | LDB | STIA, URESET, 0, 0, 0, 0, 0},
            /* MOV Il C */ {FETCH, PCCE | LDC | STIA, URESET, 0, 0, 0, 0, 0},
            /* MOV Il D */ {FETCH, PCCE | LDD | STIA, URESET, 0, 0, 0, 0, 0},
            /* MOV Ih A */ {FETCH, PCCE | LDA | STIB, URESET, 0, 0, 0, 0, 0},
            /* MOV Ih B */ {FETCH, PCCE | LDB | STIB, URESET, 0, 0, 0, 0, 0},
            /* MOV Ih C */ {FETCH, PCCE | LDC | STIB, URESET, 0, 0, 0, 0, 0},
            /* MOV Ih D */ {FETCH, PCCE | LDD | STIB, URESET, 0, 0, 0, 0, 0},
            /* MOV A Il */ {FETCH, PCCE | LDIA | STA, URESET, 0, 0, 0, 0, 0},
            /* MOV B Il */ {FETCH, PCCE | LDIA | STB, URESET, 0, 0, 0, 0, 0},
            /* MOV C Il */ {FETCH, PCCE | LDIA | STC, URESET, 0, 0, 0, 0, 0},
            /* MOV D Il */ {FETCH, PCCE | LDIA | STD, URESET, 0, 0, 0, 0, 0},
            /* MOV A Ih */ {FETCH, PCCE | LDIB | STA, URESET, 0, 0, 0, 0, 0},
            /* MOV B Ih */ {FETCH, PCCE | LDIB | STB, URESET, 0, 0, 0, 0, 0},
            /* MOV C Ih */ {FETCH, PCCE | LDIB | STC, URESET, 0, 0, 0, 0, 0},
            /* MOV D Ih */ {FETCH, PCCE | LDIB | STD, URESET, 0, 0, 0, 0, 0},
            /* MOV PC I */ {FETCH, STPC | LDI, URESET, 0, 0, 0, 0, 0},
            /* MOV I PC */ {FETCH, STI | LDPC, PCCE, URESET, 0, 0, 0, 0},
            /* MOV SP I */ {FETCH, PCCE | STSP | LDI, URESET, 0, 0, 0, 0, 0},
            /* MOV I SP */ {FETCH, PCCE | STI | LDSP, URESET, 0, 0, 0, 0, 0},

            /* LD A M   */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMREAD | STA, URESET, 0},
            /* LD B M   */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMREAD | STB, URESET, 0},
            /* LD C M   */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMREAD | STC, URESET, 0},
            /* LD D M   */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMREAD | STD, URESET, 0},
            /* ST A M   */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMWRITE | LDA, URESET, 0},
            /* ST B M   */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMWRITE | LDB, URESET, 0},
            /* ST C M   */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMWRITE | LDC, URESET, 0},
            /* ST D M   */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMWRITE | LDD, URESET, 0},
            /* LD A I   */ {FETCH, PCCE | LDI | RAMREAD | STA, URESET, 0, 0, 0, 0, 0},
            /* LD B I   */ {FETCH, PCCE | LDI | RAMREAD | STB, URESET, 0, 0, 0, 0, 0},
            /* LD C I   */ {FETCH, PCCE | LDI | RAMREAD | STC, URESET, 0, 0, 0, 0, 0},
            /* LD D I   */ {FETCH, PCCE | LDI | RAMREAD | STD, URESET, 0, 0, 0, 0, 0},
            /* ST A I   */ {FETCH, PCCE | LDI | RAMWRITE | LDA, URESET, 0, 0, 0, 0, 0},
            /* ST B I   */ {FETCH, PCCE | LDI | RAMWRITE | LDB, URESET, 0, 0, 0, 0, 0},
            /* ST C I   */ {FETCH, PCCE | LDI | RAMWRITE | LDC, URESET, 0, 0, 0, 0, 0},
            /* ST D I   */ {FETCH, PCCE | LDI | RAMWRITE | LDD, URESET, 0, 0, 0, 0, 0},
            /* LD Il M  */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMREAD | STIA, URESET, 0},
            /* LD Ih M  */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMREAD | STIB, URESET, 0},
            /* ST Il M  */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMWRITE | LDIA, URESET, 0},
            /* ST Ih M  */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, PCCE | LDMAR | RAMWRITE | LDIB, URESET, 0},

            /* JZ M     */ {FETCH, PCCE, PCCE, PCCE, URESET, 0, 0, 0},
            /* JC M     */ {FETCH, PCCE, PCCE, PCCE, URESET, 0, 0, 0},
            /* JMP M    */ {FETCH, PCCE, LDPC | RAMREAD | STMARB, PCCE, LDPC | RAMREAD | STMARA, LDMAR | STPC, URESET, 0},
            /* JZ I     */ {FETCH, PCCE, PCCE, PCCE, URESET, 0, 0, 0},
            /* JC I     */ {FETCH, PCCE, PCCE, PCCE, URESET, 0, 0, 0},
            /* JMP I    */ {FETCH, LDI | STPC, URESET, 0, 0, 0, 0, 0},

            /* SEZ      */ {FETCH, PCCE | SEZF, URESET, 0, 0, 0, 0, 0},
            /* SEC      */ {FETCH, PCCE | SECF, URESET, 0, 0, 0, 0, 0},
            /* CLZ      */ {FETCH, PCCE | CLZF, URESET, 0, 0, 0, 0, 0},
            /* CLC      */ {FETCH, PCCE | CLCF, URESET, 0, 0, 0, 0, 0},

            /* LDI A    */ {FETCH, PCCE, LDPC | RAMREAD | STA, PCCE, URESET, 0, 0, 0},
            /* LDI B    */ {FETCH, PCCE, LDPC | RAMREAD | STB, PCCE, URESET, 0, 0, 0},
            /* LDI C    */ {FETCH, PCCE, LDPC | RAMREAD | STC, PCCE, URESET, 0, 0, 0},
            /* LDI D    */ {FETCH, PCCE, LDPC | RAMREAD | STD, PCCE, URESET, 0, 0, 0},
            /* LDI Il   */ {FETCH, PCCE, LDPC | RAMREAD | STIA, PCCE, URESET, 0, 0, 0},
            /* LDI Ih   */ {FETCH, PCCE, LDPC | RAMREAD | STIB, PCCE, URESET, 0, 0, 0},
            /* LDI I    */ {FETCH, PCCE, LDPC | RAMREAD | STIB, PCCE, LDPC | RAMREAD | STIA, PCCE, URESET, 0},

            /* OUT A    */ {FETCH, PCCE | LDA | OUT, URESET, 0, 0, 0, 0, 0},
            /* OUT B    */ {FETCH, PCCE | LDB | OUT, URESET, 0, 0, 0, 0, 0},
            /* OUT C    */ {FETCH, PCCE | LDC | OUT, URESET, 0, 0, 0, 0, 0},
            /* OUT D    */ {FETCH, PCCE | LDD | OUT, URESET, 0, 0, 0, 0, 0},
            /* IN A     */ {FETCH, PCCE | IN | STA, URESET, 0, 0, 0, 0, 0},
            /* IN B     */ {FETCH, PCCE | IN | STB, URESET, 0, 0, 0, 0, 0},
            /* IN C     */ {FETCH, PCCE | IN | STC, URESET, 0, 0, 0, 0, 0},
            /* IN D     */ {FETCH, PCCE | IN | STD, URESET, 0, 0, 0, 0, 0},
            /* POLL A   */ {FETCH, PCCE | POLL | STA, URESET, 0, 0, 0, 0, 0},
            /* POLL B   */ {FETCH, PCCE | POLL | STB, URESET, 0, 0, 0, 0, 0},
            /* POLL C   */ {FETCH, PCCE | POLL | STC, URESET, 0, 0, 0, 0, 0},
            /* POLL D   */ {FETCH, PCCE | POLL | STD, URESET, 0, 0, 0, 0, 0},

            /* PUSH A   */ {FETCH, PCCE | LDA | LDSP | RAMWRITE, SPCE | SPCD, URESET, 0, 0, 0, 0},
            /* PUSH B   */ {FETCH, PCCE | LDB | LDSP | RAMWRITE, SPCE | SPCD, URESET, 0, 0, 0, 0},
            /* PUSH C   */ {FETCH, PCCE | LDC | LDSP | RAMWRITE, SPCE | SPCD, URESET, 0, 0, 0, 0},
            /* PUSH D   */ {FETCH, PCCE | LDD | LDSP | RAMWRITE, SPCE | SPCD, URESET, 0, 0, 0, 0},
            /* PUSH Il  */ {FETCH, PCCE | LDIA | LDSP | RAMWRITE, SPCE | SPCD, URESET, 0, 0, 0, 0},
            /* PUSH Ih  */ {FETCH, PCCE | LDIB | LDSP | RAMWRITE, SPCE | SPCD, URESET, 0, 0, 0, 0},
            /* POP A    */ {FETCH, PCCE | SPCE, STA | LDSP | RAMREAD, URESET, 0, 0, 0, 0},
            /* POP B    */ {FETCH, PCCE | SPCE, STB | LDSP | RAMREAD, URESET, 0, 0, 0, 0},
            /* POP C    */ {FETCH, PCCE | SPCE, STC | LDSP | RAMREAD, URESET, 0, 0, 0, 0},
            /* POP D    */ {FETCH, PCCE | SPCE, STD | LDSP | RAMREAD, URESET, 0, 0, 0, 0},
            /* POP Il   */ {FETCH, PCCE | SPCE, STIA | LDSP | RAMREAD, URESET, 0, 0, 0, 0},
            /* POP Ih   */ {FETCH, PCCE | SPCE, STIB | LDSP | RAMREAD, URESET, 0, 0, 0, 0},
    };

    private static void checkTemplate() {
        System.out.printf("0x00 - 0x%02x", TEMPLATE.length - 1);

        for (int i = 0; i < TEMPLATE.length; i++) {
            if (TEMPLATE[i].length != 8)
                throw new RuntimeException("Invalid template at index " + i + "; got " + TEMPLATE[i].length + " expected 8");
        }
    }

    private static void arraycopy(final long[][] src, final long[][] dest) {
        for (int i = 0; i < src.length; i++) {
            final long[] a = src[i];
            for (int j = 0; j < a.length; j++) {
                dest[i][j] = a[j];
            }
        }
    }

    private static long[][][] createMicrocode() {
        final long[][][] uCode = new long[4][256][8];

        // Z = 0, C = 0
        arraycopy(TEMPLATE, uCode[FLAGS_C0Z0]);

        // Z = 0, C = 1
        arraycopy(TEMPLATE, uCode[FLAGS_C1Z0]);
        uCode[FLAGS_C1Z0][JC_M][1] = PCCE;
        uCode[FLAGS_C1Z0][JC_M][2] = LDPC | RAMREAD | STMARB;
        uCode[FLAGS_C1Z0][JC_M][3] = PCCE;
        uCode[FLAGS_C1Z0][JC_M][4] = LDPC | RAMREAD | STMARA;
        uCode[FLAGS_C1Z0][JC_M][5] = LDMAR | STPC;
        uCode[FLAGS_C1Z0][JC_M][6] = URESET;

        uCode[FLAGS_C1Z0][JC_I][1] = LDI | STPC;
        uCode[FLAGS_C1Z0][JC_I][2] = URESET;

        uCode[FLAGS_C1Z0][ADC_A][1] |= CIN;
        uCode[FLAGS_C1Z0][ADC_B][1] |= CIN;
        uCode[FLAGS_C1Z0][ADC_C][1] |= CIN;
        uCode[FLAGS_C1Z0][ADC_D][1] |= CIN;
        uCode[FLAGS_C1Z0][ADC_IL][1] |= CIN;
        uCode[FLAGS_C1Z0][ADC_IH][1] |= CIN;
        uCode[FLAGS_C1Z0][SBC_A][1] |= CIN;
        uCode[FLAGS_C1Z0][SBC_B][1] |= CIN;
        uCode[FLAGS_C1Z0][SBC_C][1] |= CIN;
        uCode[FLAGS_C1Z0][SBC_D][1] |= CIN;
        uCode[FLAGS_C1Z0][SBC_IL][1] |= CIN;
        uCode[FLAGS_C1Z0][SBC_IH][1] |= CIN;

        // Z = 1, C = 0
        arraycopy(TEMPLATE, uCode[FLAGS_C0Z1]);
        uCode[FLAGS_C0Z1][JZ_M][1] = PCCE;
        uCode[FLAGS_C0Z1][JZ_M][2] = LDPC | RAMREAD | STMARB;
        uCode[FLAGS_C0Z1][JZ_M][3] = PCCE;
        uCode[FLAGS_C0Z1][JZ_M][4] = LDPC | RAMREAD | STMARA;
        uCode[FLAGS_C0Z1][JZ_M][5] = LDMAR | STPC;
        uCode[FLAGS_C0Z1][JZ_M][6] = URESET;

        uCode[FLAGS_C0Z1][JZ_I][1] = LDI | STPC;
        uCode[FLAGS_C0Z1][JZ_I][2] = URESET;

        // Z = 1, C = 1
        arraycopy(TEMPLATE, uCode[FLAGS_C1Z1]);
        uCode[FLAGS_C1Z1][JC_M][1] = PCCE;
        uCode[FLAGS_C1Z1][JC_M][2] = LDPC | RAMREAD | STMARB;
        uCode[FLAGS_C1Z1][JC_M][3] = PCCE;
        uCode[FLAGS_C1Z1][JC_M][4] = LDPC | RAMREAD | STMARA;
        uCode[FLAGS_C1Z1][JC_M][5] = LDMAR | STPC;
        uCode[FLAGS_C1Z1][JC_M][6] = URESET;

        uCode[FLAGS_C1Z1][JC_I][1] = LDI | STPC;
        uCode[FLAGS_C1Z1][JC_I][2] = URESET;

        uCode[FLAGS_C1Z1][JZ_M][1] = PCCE;
        uCode[FLAGS_C1Z1][JZ_M][2] = LDPC | RAMREAD | STMARB;
        uCode[FLAGS_C1Z1][JZ_M][3] = PCCE;
        uCode[FLAGS_C1Z1][JZ_M][4] = LDPC | RAMREAD | STMARA;
        uCode[FLAGS_C1Z1][JZ_M][5] = LDMAR | STPC;
        uCode[FLAGS_C1Z1][JZ_M][6] = URESET;

        uCode[FLAGS_C1Z1][JZ_I][1] = LDI | STPC;
        uCode[FLAGS_C1Z1][JZ_I][2] = URESET;

        uCode[FLAGS_C1Z1][ADC_A][1] |= CIN;
        uCode[FLAGS_C1Z1][ADC_B][1] |= CIN;
        uCode[FLAGS_C1Z1][ADC_C][1] |= CIN;
        uCode[FLAGS_C1Z1][ADC_D][1] |= CIN;
        uCode[FLAGS_C1Z0][ADC_IL][1] |= CIN;
        uCode[FLAGS_C1Z0][ADC_IH][1] |= CIN;
        uCode[FLAGS_C1Z1][SBC_A][1] |= CIN;
        uCode[FLAGS_C1Z1][SBC_B][1] |= CIN;
        uCode[FLAGS_C1Z1][SBC_C][1] |= CIN;
        uCode[FLAGS_C1Z1][SBC_D][1] |= CIN;
        uCode[FLAGS_C1Z0][SBC_IL][1] |= CIN;
        uCode[FLAGS_C1Z0][SBC_IH][1] |= CIN;

        return uCode;
    }

    public static void main(final String[] args) throws Throwable {
        checkTemplate();

        final PrintWriter[] outs = new PrintWriter[6];
        for (int i = 0; i < outs.length; i++) {
            outs[i] = new PrintWriter(new FileWriter("uCode" + i + ".dat"));
            outs[i].println("v2.0 raw");
        }

        final long[][][] uCode = createMicrocode();

        for (int i = 0; i < 0x2000; i++) {
            final int step = i & 7;
            final int flags = (i & 24) >> 3;
            final int insn = (i & 8160) >> 5;

            final long[][] fcodes = uCode[flags];

            if (insn < fcodes.length) {
                final long[] steps = fcodes[insn];
                final long signals = steps[step];

                for (int b = 0; b < outs.length; b++) outs[b].printf("%x ", (int) (signals >> (b << 3)) & 0xFF);
            } else {
                for (final PrintWriter out : outs) out.printf("0 ");
            }
        }

        for (final PrintWriter out : outs) {
            out.flush();
            out.close();
        }
    }

    private Main() {
    }
}