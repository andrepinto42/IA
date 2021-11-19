edge(braga,espinho,5).
edge(braga,porto,10).
edge(braga,lisboa,50).
edge(espinho,lisboa,40).
edge(espinho,faro,80).
edge(lisboa,porto,45).
edge(lisboa,faro,20).
edge(porto,faro,70).

veiculo(_) :- bicicleta(_,_).
veiculo(_) :- mota(_,_).
veiculo(_) :- carro(_,_).
bicicleta(5,10).
mota(20,35).
carro(100,25).

connected(X,Y) :- edge(X,Y).
connected(X,Y) :- edge(Y,X).

connected(X,Y,Custo) :- edge(X,Y,Custo).
connected(X,Y,Custo) :- edge(Y,X,Custo).



calculateSizePath([Node1 ,Node2 | Rest],Size) :- 
                                                 connected(Node1,Node2,Size1) , 
                                                 calculateSizePath([Node2 | Rest],Size2),
                                                 Size is Size1 +Size2.

calculateSizePath([Node1 , Node2 | [] ],Size) :- connected(Node1,Node2,Size).

% sizePaths(A,B,List) :- pathList(A,B,List1), calculateSizePath().

pathList(A,B,List) :- findall(X,path(A,B,X),List).
weight(A,B,Size) :- path(A,B,Caminho), calculateSizePath(Caminho,Size).
weightList(A,B,List) :- findall(X,weight(A,B,X),List).

path(A,B,Path) :-
       travel(A,B,[A],Q), 
       reverse(Q,Path).

travel(A,B,P,[B|P]) :- 
       connected(A,B,_).

travel(A,B,Visited,Path) :-
       connected(A,C,_), % Procurar um C que esteja conectado ao nodo A
       C \== B,
       \+member(C,Visited), %garantir caminho nao ciclico
       travel(C,B,[C|Visited],Path).  %Acrescentar o nodo C aos nodos Visitados


shortest(A,B,Path,Length) :-
   setof([P,L],path(A,B,P,L),Set),
   Set = [_|_], % fail if no path 
   minimal(Set,[Path,Length]).


list_min([L|Ls], Min) :-
    list_min(Ls, L, Min).

list_min([], Min, Min).
list_min([L|Ls], Min0, Min) :-
    Min1 is min(L, Min0),
    list_min(Ls, Min1, Min).

% main :- foreach(X,[1,2,3]) do writeln(X).