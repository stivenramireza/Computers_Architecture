// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.

// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
// While(True):
(LOOP_1)    // LOOP for Black Screen 	   
    @KBD		        
    D=M     // D = 24576
    @LOOP_2
    D;JEQ   // If D = 0 then goto LOOP_2
    @24575
    D=M
    @LOOP_2
    D;JGT   // If D < 0 then goto LOOP_2
    @i
    D=M     // D = i
    @SCREEN
    D = A+D
    A = D
	M = -1
    @i
    M=M+1
    @LOOP_1	   
    0;JMP   // Goto LOOP_1
(LOOP_2)    // LOOP for White Screen
    @KBD		     
    D=M     // D = 24576
    @LOOP_1	     
    D;JGT   // If D > 0 then goto LOOP_1
    @i
    D=M     // D = i
    @SCREEN		
    D=A+D   // D = Screen + i
    A=D
    M=0
    @i
    M=M-1
    @LOOP_2
    0;JMP   // Goto LOOP_2