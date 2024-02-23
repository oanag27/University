; 3. Return the number of levels of a tree of type (1).

(defun do_st (l nrNoduri nrMuchii)
  (cond
    ((null l) nil)
    ((= nrNoduri ( + 1 nrMuchii)) nil)
    (t (cons (car l) (cons (cadr l) (do_st (cddr l) (+ 1 nrNoduri) (+ (cadr l) nrMuchii)))))
  )
)


(defun do_dr (l nrNoduri nrMuchii)
  (cond
    ((null l) nil)
    ((= nrNoduri (+ 1 nrMuchii)) l)
    (t (do_dr (cddr l) (+ 1 nrNoduri) (+ (cadr l) nrMuchii)))
  )
)


(defun stang(l)
  (do_st (cddr l) 0 0)
)


(defun drept(l)
  (parcurg_dr (cddr l) 0 0)
)

(defun append(l p)
  (cond
    ((null l) p)
    (t (cons (car l) (append (cdr l) p)))
  )
)

(defun max(a b)
  (cond
    ((> a b) a)
    (t b)
  )
)

(defun levels(l counter)
  (cond
    ((null l) counter)
    (t (max (levels (stang l) (+ 1 counter)) (levels (drept l) (+ 1 counter))))
  )
)

(defun main(l)
  (levels l -1)
)