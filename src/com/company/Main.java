package com.company;


import Exceptions.MainNotFound;
import Parser.Lexer;
import Parser.Parser;
import Parser.Token;
import ast.Statements.BlockStatement;
import ast.Statements.BreakStatement;
import ast.Statements.FunctionStatement;
import ast.Statements.Statement;
import lib.Functions;
import lib.Values.Value;
import lib.Variable;
import lib.Variables;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(Path.of("C:\\Users\\artem\\Desktop\\asd.txt")), StandardCharsets.UTF_8);
        ArrayList<Token> tokens;
        Lexer lexer = new Lexer(text);
        tokens = lexer.Tokenize();

        /*for (Token token : tokens){
            System.out.println(token);
        }*/

        BlockStatement statements = new Parser(tokens).Parse();
        for (Statement statement : statements.statements){
            if(!(statement instanceof BlockStatement) && !(statement instanceof FunctionStatement)){
                throw new RuntimeException("никаких блоков кроме обьявления переменных и функций не должно тут существовать");
            }
            if(statement instanceof FunctionStatement){
                for (Statement statement1 : ((BlockStatement)((FunctionStatement)statement).getStatement()).statements){
                    if(statement1 instanceof FunctionStatement){
                        throw new RuntimeException("нельзя обьявить функию в функции");
                    }
                }
            }
        }
        statements.execute();
        if(Functions.isExists("main", new int[0])) {
            try {
                Functions.get("main", new int[0]).execute(new Value[0]);
            }
            catch (BreakStatement e){
                System.out.println("break не возможен в данном контексе");
            }
        }
        else{
            throw new MainNotFound();
        }
        /*for (Variable key : Variables.Variables){
            System.out.println(key.getName() + " " + key.getValue().asString());
        }*/
    }
}
