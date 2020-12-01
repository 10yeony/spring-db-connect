package kr.com.inspect.java;

import java.lang.*;

public class test000 {
	public static void main(String[] args) {
		CompilerClass geek = new CompilerClass();

		// Use of enable() :
		Compiler.enable();

		// class CompilerDemo
		Class c = geek.getClass();
		System.out.println(c);

		// Use of command() :
		Object g = Compiler.command("javac CompilerClass");
		System.out.println("Value : " + g);

		// Use of compileClass :
		// Since it is not a subclass so there is no compiler for it
		boolean check = Compiler.compileClass(c);
		System.out.println("\nIs compilation successful ? : " + check);

		String str = "CompilerClass";
		boolean check1 = Compiler.compileClasses(str);

		System.out.println("\nIs compilation successful using str ? : " + check1);

		// Use of disable() :
		Compiler.disable();
	}

	private static class CompilerClass {
		public CompilerClass() {
		}
	}
}