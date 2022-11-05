bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; x/2+100*(a+b)-3/(c+d)+e*e=1+900-1+9=909
    ; a,c-word; b,d-byte; e-doubleword; x-qword
    a dw 5
    b db 4
    c dw 2
    d db 1
    e dd 3
    x dq 2

; our code starts here
segment code use32 class=code
    start:
        ; convert byte to word
        mov al,[b]
        mov ah,0
        add ax,[a]
        mov bx,ax     ;bx=a+b
        
        mov ax,100
        mul bx       ;dx:ax=100*(a+b)
        push dx
        push ax
        pop eax
        mov ebx,eax
        ;convert byte to word
        
        mov ax,3
        mov dx,0
        mov cl,[d]
        mov ch,0
        add cx,[c]
        div cx ;    ax=3/(c+d)
        
        sub ebx,eax     ;ebx=100*(a+b)-3/(c+d)
        
        mov eax,[e]
        mov edx,0
        mul dword[e]    ;eax=e*e
        
        add ebx,eax     ;ebx=100*(a+b)-3/(c+d)+e*e
        
        mov eax,[x]
        mov cx,2
        div cx          ;eax=x/2
        
        add ebx,eax
        
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
