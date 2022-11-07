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
    a db 10001100b          ;008Ch
    b dw 0100101000111001b  ;4A39h
    c dd 0  
    

; our code starts here
segment code use32 class=code
    start:
                ;the bits 0-7 of C have the value 1
        mov eax,0
        or ax,0000000011111111b
        ;the bits 8-11 of C are the same as the bits 4-7 of A
        mov bx,[a]          ;convert a from byte to word
        mov cx,bx
        mov ebx,0
        mov bx,cx
        and ebx,0000000011110000b
        shl ebx,4
        or eax,ebx
        
       ;put everything in c
       ;the bits 12-19 are the same as the bits 2-9 of B
        mov bx,[b]
        mov cx,bx
        mov ebx,0
        mov bx,cx
        mov edx,0
        and ebx,1111111100b
        shl ebx,10
        or eax,ebx
       ;the bits 20-23 are the same as the bits 0-3 of A
        mov bx,[a]
        mov bh,0
        mov cx,bx
        mov ebx,0
        mov bx,cx       ;dword
        mov edx,0
        and ebx,0000000000001111b
        shl ebx,20
        or eax,ebx
        ;the bits 24-31 are the same as the high byte of B(8-15)
        mov bx,[b]
        mov cx,bx
        mov ebx,0
        mov bx,cx
        mov edx,0
        and ebx,1111111100000000b
        shl ebx,16
        or eax,ebx
        mov dword[c],eax
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
