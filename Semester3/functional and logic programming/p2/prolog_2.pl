% 3.
% a. Merge two sorted lists with removing the double values.
myMerge([],[],[]):-!.
myMerge([],P,P):-!.
myMerge(L,[],L):-!.

myMerge([H1|T1],[H2|T2],[H1|R]):-
    H1<H2,
    myMerge(T1,[H2|T2],R).

myMerge([H1|T1],[H2|T2],[H2|R]):-
    H1>H2,
    myMerge([H1|T1],T2,R).
    
myMerge([H1|T1],[H2|T2],R):-
    H1=H2,
    myMerge([H1|T1],T2,R). 

% b. For a heterogeneous list, formed from integer numbers and list of numbers, merge all sublists with removing
%    the double values.
% [1, [2, 3], 4, 5, [1, 4, 6], 3, [1, 3, 7, 9, 10], 5, [1, 1, 11], 8] =>
% [1, 2, 3, 4, 6, 7, 9, 10, 11].

heterList([],[]).
heterList([H|T],R1):-
    is_list(H),
    !,
    heterList(T,R),
    myMerge(H,R,R1).
heterList([_|T],R):-
    heterList(T,R).