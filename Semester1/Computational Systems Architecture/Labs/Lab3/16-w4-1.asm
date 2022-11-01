bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; c-a-(b+a)+c = 7-2-5+7 = 7
    ; a - byte, b - word, c - double word, d - qword - Unsigned representation
    a db 2
    b dw 3
    c dd 7

; our code starts here
segment code use32 class=code
    start:
        ;convert a to word
        mov al,[a]
        mov ah,0            ;al-ax
        add ax,[b]
        mov dx,0
        push dx
        push ax
        pop eax             ;transform from dw to dd
        mov ebx,[c]
        mov al,[a]
        mov ah,0
        mov bx,ax
        mov eax,0
        mov ax,bx
        sub ebx,ecx
        sub ebx,eax
        add ebx,[c]
        
        
        
    
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
