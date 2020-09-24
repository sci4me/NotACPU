package com.sci.cpu.asm;

import com.sci.cpu.asm.lexer.Lexer;
import com.sci.cpu.asm.lexer.Token;
import com.sci.cpu.asm.parser.Parser;
import com.sci.cpu.asm.parser.tree.FileST;

import java.io.*;
import java.util.List;

public final class Main {
    private static String readFile(final InputStream in) throws IOException {
        final StringBuilder sb = new StringBuilder();

        final BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }

        reader.close();

        return sb.toString();
    }

    public static void main(final String[] args) throws Throwable {
        if (args.length != 1) {
            System.out.println("Usage: asm <file>");
            return;
        }

        final String code = readFile(new FileInputStream(args[0]));
        final Lexer lexer = new Lexer(code);
        final Parser parser = new Parser(lexer.tokenize());
        final FileST st = parser.parse();
        final AssemblerASTVisitor visitor = new AssemblerASTVisitor();
        final byte[] memory = visitor.assemble(st);

        final PrintWriter writer = new PrintWriter(new FileWriter("out.txt"));
        writer.println("v2.0 raw");
        for (final byte b : memory) {
            writer.printf("%x ", b);
        }
        writer.flush();
        writer.close();
    }

    private Main() {
    }
}