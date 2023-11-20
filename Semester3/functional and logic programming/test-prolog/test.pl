% def a predicate that merges 2 sorted list of int numbers and removes duplicate values

% merge_remove_duplicates(A:List,B:List,R:List)
% flow model: merge_remove_duplicates(i,i,o)

% merge_remove_duplicates(l1,l2,...ln,q1,...qm)=>
% l1,..ln if m=0
% q1,..qm if n=0
% l1+merge_remove_duplicates(l2,..ln,q1,..qm) if l1<q1
% q1+merge_remove_duplicates(l1,..ln,q2,..qm) if q1<l1
% merge_remove_duplicates(l2,..ln,q1,..qm)  if l1=q1

% Base cases:
merge_remove_duplicates([],[],[]).
merge_remove_duplicates(A,[],A).
merge_remove_duplicates([],B,B).

merge_remove_duplicates([H1|T1],[H2|T2],[H1|Result]):-
    H1=H2,
    merge_remove_duplicates(T1,T2,Result).

merge_remove_duplicates([H1|T1],[H2|T2],[H1|Result]):-
    H1<H2,
    merge_remove_duplicates(T1,[H2|T2],Result).

merge_remove_duplicates(L1,[H2|T2],[H2|Result]):-
    [H1|_] = L1,
    H1>H2,
    merge_remove_duplicates(L1,T2,Result).