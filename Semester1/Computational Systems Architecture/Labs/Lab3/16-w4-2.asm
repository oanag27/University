bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    
    ; (d-a)-(a-c)-d = (100-5)-(5-4)-100=95-1-100=-6
    ; a - byte, b - word, c - double word, d - qword - Signed representation
    a db 5
    c dd 4
    d dq 100

; our code starts here
segment code use32 class=code
    start:
        mov al,[a]
        neg al; al=-a
        cbw ; ax=-a
        cwde; eax=-a
        cdq; edx:eax=-a
        add eax,[d]
        adc edx,[d+4]; edx:eax= -a+d=d-a
        mov ebx,eax
        mov ecx,edx; ecx:ebx=d-a
        mov al,[a]
        cbw; ax=a
        cwde; eax=a
        sub eax,[c]; eax=a-c
        cdq; edx:eax=a-c
        sub ebx,eax
        sbb ecx,edx; ecx:ebx=(d-a)-(a-c)
        sub ebx,[d]
        sbb ecx,[d+4]; ecx:ebx=(d-a)-(a-c)-d
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program