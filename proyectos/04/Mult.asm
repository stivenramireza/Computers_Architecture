// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.
@R0
D=M   // D = R0
@a
M=D   // a = R0
@R1
D=M   // D = R1
@b 
M=D   // b = R1
@0
D=A   // D = 0
@R2
M=D   // R2 = 0
(LOOP)
    @a
    D=M   // D = a
    @0
    D=D-A   // D = a - 0
    @END
    D;JLE   // If a <= 0 then goto END
    @b
    D=M   // D = b
    @R2
    M=D+M   // R2 += b;
    @a
    M=M-1   // a--;
    @LOOP
    0;JMP   // Goto LOOP
(END)
    @END
    0;JMP   // Infinite loop