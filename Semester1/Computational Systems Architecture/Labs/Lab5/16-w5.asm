bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; Given the byte A and the word B, compute the doubleword C as follows:
    ;1.the bits 0-7 of C have the value 1
    ;2.the bits 8-11 of C are the same as the bits 4-7 of A
    ;3.the bits 12-19 are the same as the bits 2-9 of B
    ;4.the bits 20-23 are the same as the bits 0-3 of A
    ;5.the bits 24-31 are the same as the high byte of B
    a db 10001100b
    b dw 0100101000111001b
    c dd 0
    

; our code starts here
segment code use32 class=code
    start:
        
        ;the bits 0-7 of C have the value 1
        mov eax,0
        mov ax,[b]
        or ax,0000000011111111b
        ;the bits 8-11 of C are the same as the bits 4-7 of A
        mov bx,[a]          ;convert a from byte to word
        mov bh,0
        mov ebx,0
        and bx,0000000011110000b
        shl bx,4
        ;put everything in c
        or ax,bx
        ;the bits 12-19 are the same as the bits 2-9 of B
        mov bx,[b]
        mov cx,bx
        mov ebx,0
        mov bx,cx
        and bx,1111111100b
        shl bx,10
        or ax,bx
        ;the bits 20-23 are the same as the bits 0-3 of A
        mov bx,[a]
        mov bh,0
        mov cx,bx
        mov ebx,0
        mov bx,cx       ;dword
        and bx,0000000000001111b
        shl bx,20
        or ax,bx
        ;the bits 24-31 are the same as the high byte of B(8-15)
        mov bx,[b]
        mov cx,bx
        mov ebx,0
        mov bx,cx
        and bx,1111111100000000b
        shl bx,16
        
       
        
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
