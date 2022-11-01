bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 2
    b db 4
    c db 1
;(a+b)/2 + (10-a/c)+b/4
; a,b,c - byte, d - word
; our code starts here
segment code use32 class=code
    start:
        mov al,[a]
        add al,[b]
        mov ah,0
        mov bl,2
        div bl              ;al=(a+b)/2
        mov bl,al
        mov cl,10
        mov al,[a]
        mov ah,0
        div byte[c]
        sub cl,al
        add bl,cl           ;bl=(a+b)/2+(10-a/c)
        mov al,[b]
        mov ah,0
        mov cl,4
        div cl
        add bl,al
        mov al,bl
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
