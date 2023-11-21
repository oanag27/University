%3.
%a. Define a predicate to remove from a list all repetitive elements.
%Eg.: l=[1,2,1,4,1,3,4] => l=[2,3])
%b. Remove all occurrence of a maximum value from a list on integer numbers

% a.
is_member(E, [E|_]).
is_member(E, [_|T]) :-
    is_member(E, T).

eliminate_element(_, [], []).

eliminate_element(X, [X|T], L) :-
    eliminate_element(X, T, L).

eliminate_element(X, [H|T], [H|L]) :-
    X \= H,   
    eliminate_element(X, T, L).

eliminate_duplicate([], []).
% check if the head is in the tail, remove all occurences
eliminate_duplicate([H|T], Result) :-
    is_member(H, T),                 
    !,
    eliminate_element(H, T, NewTail),
    eliminate_duplicate(NewTail, Result).

eliminate_duplicate([H|T], [H|Result]) :-
    \+ is_member(H, T),            
    eliminate_element(H, T, NewTail),  
    eliminate_duplicate(NewTail, Result).


% b.
remove_max([], _, []).

remove_max([X|Tail], X, Result) :-
    remove_max(Tail, X, Result).

remove_max([X|Tail], Max, [X|Result]) :-
    X \= Max,
    remove_max(Tail, Max, Result).

remove_all_max(List, Result) :-
    max_list(List, Max),                % maximum value
    remove_max(List, Max, Result).
