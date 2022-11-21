bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

;Two character strings S1 and S2 are given. Obtain the string D by concatenating the elements found on odd positions in S2 and the elements found on even positions in S1.
;Example:
;S1: 'a', 'b', 'c', 'b', 'e', 'f'
;S2: '1', '2', '3', '4', '5'
;D: '2', '4', 'a', 'c', 'e'
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 'a', 'b', 'c', 'b', 'e', 'f'
    l1 equ $-s1     ;length of s1 string
    s2 db '1', '2', '3', '4', '5'
    l2 equ $-s2     ;length of s2 string
    d resb l1+l2    ;destination string initialize it

; our code starts here
segment code use32 class=code
    start:
        mov ecx,l2              ;store l2 to loop ecx times
        mov esi,0               ;index
        mov edx,0
        jecxz end_loop          ;when ecx is set to zero exit loop
        started:
            test esi,1           ;number is odd(check if the lower bit is set)
            jz ending            ;jump if even(lowest bit is zero)
                mov al,[s2+esi]  ;extract the element on the odd position
                mov [d+edx],al   ;add to string d
                inc edx          ;numbers of elements added in d
            ending:
            inc esi              ;increment esi
        loop started             ;continue loop
        end_loop:
  
        ;string s1
        mov ecx,l1              ;store l2 to loop ecx times
        mov esi,0               ;index
        jecxz end_l             ;exit the loop
        starting:
            test esi,1          ;number is odd
            jnz done            ;jump if odd
                mov al,[s1+esi] ;extract the element on the even position
                mov [d+edx],al  ;add to string d
                inc edx         ;increment position in d
            done:
            inc esi             ;move to the next byte in the string s2
        loop starting
        end_l:                  ;end of program
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
