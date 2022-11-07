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
    c dw 2
    b db 4
    d db 1
    e dd -3
    x dq 2

; our code starts here
segment code use32 class=code
    start:
        mov eax,dword[x]
        mov edx,dword[x+4]
        mov ecx, 2
        idiv ecx
        
        mov ebx,eax         ;ebx=x/2
        
        
        mov al,[b]
        cbw
        add ax, word[a]
        mov cx,ax       ;cx=a+b
        
        mov al,100
        cbw
        mul cx          ;dx:ax=100*(a+b)
        
        push dx
        push ax
        pop eax
        add ebx,eax
        
        mov al,[d]
        cbw
        add ax,[c]      ;ax=c+d
        mov cx,ax
        mov al,3
        cbw
        cwd
        idiv cx         ;ax=3/(c+d)
        cwde            ;eax=3/(c+d)
        
        sub ebx,eax     ;ebx=x/2+100*(a+b)-3/(c+d)
        mov eax,ebx
        cdq

        mov ebx,eax
        mov ecx,edx     ;ecx:ebx=x/2+100*(a+b)-3/(c+d)
        
        mov eax,[e]
        imul dword[e]           ;edx:eax=e*e
        
        add ebx,eax
        adc ecx,edx            ;the result will be stored in ebx:ecx
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
