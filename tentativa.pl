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

last([X],X) :-! .
last([_|Z],X) :- last(Z,X).

shortestPath(A,B,Shortest) :-pathList(A,B,[Head|Tail]),
                             last(Head,StartingShortest),
                             shortRec(StartingShortest,Tail,Shortest).

shortRec(CurrentShortest,[Head|Tail],Shortest) :-
                     last(Head,ShortestHead),
                     NewShortest is min(CurrentShortest,ShortestHead),
                     shortRec(NewShortest,Tail,Shortest).

shortRec(CurrentShortest,[Element],Shortest) :-
                     last(Element,Weight),
                     Shortest is min(CurrentShortest,Weight).

pathList(A,B,List) :- findall(X,path(A,B,X),List).
weight(A,B,Size) :- path(A,B,Caminho), calculateSizePath(Caminho,Size).
weightList(A,B,List) :- findall(X,weight(A,B,X),List).

path(A,B,Path) :-
       travel(A,B,[A],0,Q),
       reverse(Q,Path).


travel(A,B,P,Custo,[CustoTotal,B|P]) :-
       connected(A,B,Custo1),
       CustoTotal is Custo1 + Custo.

travel(A,B,Visited,SizeInicial,Path) :-
       connected(A,C,Size1), % Procurar um C que esteja conectado ao nodo A
       % write(A),write(' custa '),write(Size1),write(' para '),writeln(C),
       C \== B,
       \+member(C,Visited), %garantir caminho nao ciclico
       SizeTotal is Size1 + SizeInicial,
       travel(C,B,[C|Visited],SizeTotal,Path).  %Acrescentar o nodo C aos nodos Visitados
