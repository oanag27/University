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
    b db 1
    c db 5
    d db 7

; our code starts here
; a,b,c,d - byte
; a+13-c+d-7+b = 2+13-5+7-7+1= 11
segment code use32 class=code
    start:
        mov al,[a]  ;al=a
        mov bl,13   ;bl=13
        add al,bl   ;ax=a+13
        sub al,[c]  ;al=a+13-c
        mov bl,[d]  ;bl=d
        mov cl,7
        sub bl,cl   
        add bl,[b] 
        sub ax,bx
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
