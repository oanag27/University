; 3. Define a function to tests the membership of a node in a n-tree represented as (root
;    list_of_nodes_subtree1 ... list_of_nodes_subtreen)
;    Eg. tree is (a (b (c)) (d) (E (f))) and the node is "b" => true
(defun checkExistence(l elem)
  (cond
    ((and (atom l) (equal l elem)) t)
    ((atom l) nil)
    (t (some #'identity (mapcar #'(lambda (a) (checkExistence a elem)) l))) 
  )
)


(defun g(l)
  (mapcon #'list l)
)
