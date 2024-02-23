; a) Write a function that inserts in a linear list a given atom A after the 2nd, 4th, 6th, ... element.

(defun insertElem(l e pos)
  (cond
    ((null l) nil)
    ((equal (mod pos 2) 0) (cons (car l) (cons e (insertElem (cdr l) e (+ 1 pos)))))
    (t (cons (car l) (insertElem (cdr l) e (+ 1 pos))))
  )
)

(defun mainA(l e)
  (insertElem l e 1)
)


; b) Write a function to get from a given list the list of all atoms, on any
; level, but reverse order. Example:
; (((A B) C) (D E)) ==> (E D C B A)

(defun append(l1 l2)
  (cond
    ((null l1) l2)
    (t (cons (car l1) (append (cdr l1) l2)))
  )
)

(defun getAllAtms (l)
  (cond
    ((null l) nil)
    ((listp (car l)) (append (getAllAtms (cdr l)) (getAllAtms (car l))))
    (t (append (getAllAtms (cdr l)) (list (car l))))
  )
)    


;c) Write a function that returns the greatest common divisor of all numbers in a nonlinear list.

(defun GCD(a b)
  (cond
    ((not (numberp a)) b)
    ((not (numberp b)) a)
    ((= 0 b) a)
    (t (GCD b (mod a b)))
  )
)

;return NIL if there is no number and True otherwise

(defun lookForNr(l)
  (cond
    ((null l) nil)
    ((listp (car l)) (or (lookForNr (car l)) (lookForNr (cdr l))))
    ((numberp (car l)) T)
    (t (lookForNr (cdr l)))
  )
)

(defun gcdForAllNr(l)
  (cond
    ((null (cdr l)) (car l))
    ((listp (car l)) (GCD (gcdForAllNr (car l)) (gcdForAllNr (cdr l))))
    (t (GCD (car l) (gcdForAllNr (cdr l))))
  )
)

(defun main(l)
  (cond
    ((lookForNr l) (gcdForAllNr l))
    (t 1)   ;do not have nr in the list
  )
)


;d) Write a function that determines the number of occurrences of a given atom in a nonlinear list.

; nrOfOccurrences(l1l2...ln, c, elem) = 
; = c , if n = 0
; = nrOfOccurences(l1, 0, elem) + nrOfOccurences(l2...ln, c, elem) , if l1 is a list
; = nrOfOccurences(l2...ln, c + 1, elem) , if l1 = elem
; = nrOfOccurences(l2...ln, c, elem) , otherwise


(defun nrOfOccurences(l c elem)
  (cond
    ((null l) c)
    ((listp (car l)) (+ (nrOfOccurences (car l) 0 elem) (nrOfOccurences (cdr l) c elem)))
    ((equal (car l) elem) (nrOfOccurences (cdr l) (+ 1 c) elem))
    (t (nrOfOccurences (cdr l) c elem))
  )
)


(defun mainD(l elem)
  (nrOfOccurences l 0 elem)
)


