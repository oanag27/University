bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

;Being given two alphabetical ordered strings of characters, s1 and s2, build using merge sort the ordered string of bytes that contain all characters from s1 and s2.

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 'b', 'c', 'g'                     ;alphabetical ordered string
    l1 equ $-s1                             ;length of s1 string
    s2 db 'd', 'f', 'h', 'i'                ;alphabetical ordered string
    l2 equ $-s2                             ;length of s2 string
    d resb l1+l2                            ;destination string initialize it
    r dd 0
; our code starts here
segment code use32 class=code
    start:
        ;merge sort the destination string
        cld        ;set direction flag to 0(move from left to right)
        mov esi,s1  ;s1 in source
        mov edi,s2  ;s2 in destination
        mov ecx,l1+l2
        
        mov eax,0       ;count how many elements we have gone through s1
        mov ebx,0       ;count how many elements we have gone through s2
        mov edx,0       ;for the d string
        
        merge:
            cmpsb         ;compare elements from the 2 strings
            jb below      ;elem from s1 < elem from s2    
            jg greater    ;elem from s1 > elem from s2   
            je equals      ;elem from s1 = elem from s2   
     
        below:
            dec edi
            dec esi
            mov [r],eax
            lodsb               ; put in al the current value of esi
            mov [d + edx], al   ; put in the result on the position edx the value from al
            mov eax, [r]        ; reset the value from eax 
            inc edx ; incrementing length of the result
            inc eax  
            cmp eax,l1   ; if we are done with the elements in s1
            jge sf_prg ; jump to sf_prog
            loop merge; repeating the loop
            jecxz sf_prg ; if ecx = 00000000, we jump to sf_prog
            
        greater:
            dec edi 
            dec esi 
            mov [r], eax ; save the value of eax
            mov al,[edi]
            mov [d+edx], al
            mov eax, [r]
            inc edi ; incrementing eax(the index where we were in d)
            inc edx ; incrementing eax(the index where we were in d)
            inc ebx ; incrementing eax(the index where we were in s2) 
            cmp ebx, l2
            jge sf_prg ; jump to sf_prog
            loop merge ; repeating the loop
            jecxz sf_prg ; if ecx = 00000000, we jump to sf_prog
            
        equals:
            dec esi    
            mov [r], eax ; save the value of eax(the index where we were in s1   
            lodsb
            mov [d + edx], al
            mov eax, [r]
            inc edx ; incrementing length of the result
            inc ebx ; incrementing ebx   
            cmp ebx, l2 ; if we are done with the elements in s2
            jge sf_prg    
            inc eax ; incrementing eax (the index where we were in s1)
            cmp eax, l1 ; if we are done with the elements in s2    
            jge sf_prg ; jump to sf_prog
            loop merge  ; repeating the loop
            jecxz sf_prg ; if ecx = 00000000, we jump to sf_prog
        sf_prg:
        
        cmp eax, l1 ; comparing EAX with the length of s1, if EAX = len_s1 then we are done with the terms of s1 
        je biggest; if we have to add terms from s2(ebx bigger)
        jne smallest ; if EAX is not equal to len_s1 we are done with the terms of s2(ebx smaller)
        
        biggest: ; the remaining terms of s2
            mov ecx, l2
            sbb ecx, ebx
            do:
                mov al, [edi]
                mov [d + edx], al
                inc edx
                inc edi
            loop do
            jmp end_prg
        
        smallest:
            mov ecx, l1
            sub ecx, eax
            does:
                lodsb
                mov [d + edx], al
                inc edx
            loop does
        
        
        end_prg:
            
        
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
